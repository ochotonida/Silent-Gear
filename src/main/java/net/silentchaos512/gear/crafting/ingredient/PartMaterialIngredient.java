package net.silentchaos512.gear.crafting.ingredient;

import com.google.gson.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.material.IMaterial;
import net.silentchaos512.gear.api.material.IMaterialCategory;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.api.util.PartGearKey;
import net.silentchaos512.gear.gear.material.MaterialCategories;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.gear.material.MaterialManager;
import net.silentchaos512.gear.util.TextUtil;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PartMaterialIngredient extends Ingredient implements IGearIngredient {
    private final PartType partType;
    private final GearType gearType;
    private final int minTier;
    private final int maxTier;
    private final Set<IMaterialCategory> categories = new LinkedHashSet<>();

    private PartMaterialIngredient(PartType partType, GearType gearType, int minTier, int maxTier) {
        super(Stream.of());
        this.partType = partType;
        this.gearType = gearType;
        this.minTier = minTier;
        this.maxTier = maxTier;
    }

    public static PartMaterialIngredient of(PartType partType) {
        return of(partType, GearType.TOOL);
    }

    public static PartMaterialIngredient of(PartType partType, IMaterialCategory... categories) {
        return of(partType, GearType.TOOL, categories);
    }

    public static PartMaterialIngredient of(PartType partType, GearType gearType) {
        return new PartMaterialIngredient(partType, gearType, 0, Integer.MAX_VALUE);
    }

    public static PartMaterialIngredient of(PartType partType, GearType gearType, IMaterialCategory... categories) {
        return of(partType, gearType, 0, Integer.MAX_VALUE, categories);
    }

    public static PartMaterialIngredient of(PartType partType, GearType gearType, int minTier, int maxTier) {
        return new PartMaterialIngredient(partType, gearType, minTier, maxTier);
    }

    public static PartMaterialIngredient of(PartType partType, GearType gearType, int minTier, int maxTier, IMaterialCategory... categories) {
        PartMaterialIngredient ret = new PartMaterialIngredient(partType, gearType, minTier, maxTier);
        ret.categories.addAll(Arrays.asList(categories));
        return ret;
    }

    @Override
    public PartType getPartType() {
        return partType;
    }

    @Override
    public GearType getGearType() {
        return gearType;
    }

    @Override
    public Optional<Component> getJeiHint() {
        MutableComponent text;
        if (!this.categories.isEmpty()) {
            MutableComponent cats = TextUtil.separatedList(categories.stream()
                    .map(IMaterialCategory::getDisplayName)
                    .collect(Collectors.toList())
            );
            text = TextUtil.withColor(cats, Color.INDIANRED);
        } else {
            MutableComponent any = TextUtil.translate("material.category", "any");
            text = TextUtil.withColor(any, Color.LIGHTGREEN);
        }

        PartGearKey key = PartGearKey.of(this.gearType, this.partType);
        text.append(TextUtil.misc("spaceBrackets", key.getDisplayName()).withStyle(ChatFormatting.GRAY));

        return Optional.of(TextUtil.translate("jei", "materialType", text));
    }

    public Set<IMaterialCategory> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        MaterialInstance material = MaterialInstance.from(stack);
        if (material == null) return false;

        int tier = material.getTier(this.partType);
        return material.get().isCraftingAllowed(material, partType, gearType)
                && (categories.isEmpty() || material.hasAnyCategory(categories))
                && tierMatches(tier);
    }

    private boolean tierMatches(int tier) {
        return tier >= this.minTier && tier <= this.maxTier;
    }

    @Override
    public ItemStack[] getItems() {
        Collection<IMaterial> materials = MaterialManager.getValues();
        if (!materials.isEmpty()) {
            return materials.stream()
                    .map(MaterialInstance::of)
                    .filter(mat -> mat.get().isCraftingAllowed(mat, partType, gearType))
                    .filter(mat -> categories.isEmpty() || mat.hasAnyCategory(categories))
                    .filter(mat -> tierMatches(mat.getTier(this.partType)))
                    .flatMap(mat -> Stream.of(mat.get().getIngredient().getItems()))
                    .filter(stack -> !stack.isEmpty())
                    .toArray(ItemStack[]::new);
        }
        return super.getItems();
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", Serializer.NAME.toString());
        json.addProperty("part_type", this.partType.getName().toString());
        if (this.gearType != GearType.TOOL) {
            json.addProperty("gear_type", this.gearType.getName());
        }
        if (!this.categories.isEmpty()) {
            JsonArray array = new JsonArray();
            this.categories.forEach(cat -> array.add(cat.getName()));
            json.add("categories", array);
        }
        if (this.minTier > 0) {
            json.addProperty("min_tier", this.minTier);
        }
        if (this.maxTier < Integer.MAX_VALUE) {
            json.addProperty("max_tier", this.maxTier);
        }
        return json;
    }

    public static final class Serializer implements IIngredientSerializer<PartMaterialIngredient> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation NAME = SilentGear.getId("material");

        private Serializer() {}

        @Override
        public PartMaterialIngredient parse(FriendlyByteBuf buffer) {
            ResourceLocation typeName = buffer.readResourceLocation();
            PartType partType = PartType.get(typeName);
            if (partType == null) {
                throw new JsonParseException("Unknown part type: " + typeName);
            }

            GearType gearType = GearType.get(buffer.readUtf());
            if (gearType.isInvalid()) {
                throw new JsonParseException("Unknown gear type: " + typeName);
            }

            int categoryCount = buffer.readByte();
            IMaterialCategory[] categories = new IMaterialCategory[categoryCount];
            for (int i = 0; i < categoryCount; ++i) {
                categories[i] = MaterialCategories.get(buffer.readUtf());
            }


            int minTier = buffer.readVarInt();
            int maxTier = buffer.readVarInt();

            return of(partType, gearType, minTier, maxTier, categories);
        }

        @Override
        public PartMaterialIngredient parse(JsonObject json) {
            String typeName = GsonHelper.getAsString(json, "part_type", "");
            if (typeName.isEmpty()) {
                throw new JsonSyntaxException("'part_type' is missing");
            }

            PartType type = PartType.get(Objects.requireNonNull(SilentGear.getIdWithDefaultNamespace(typeName)));
            if (type == null) {
                throw new JsonSyntaxException("part_type " + typeName + " does not exist");
            }

            String gearTypeName = GsonHelper.getAsString(json, "gear_type", "tool");
            GearType gearType = GearType.get(gearTypeName);
            if (gearType.isInvalid()) {
                throw new JsonSyntaxException("gear_type " + gearTypeName + " does not exist");
            }

            Collection<IMaterialCategory> categories = new ArrayList<>();
            if (json.has("categories")) {
                JsonArray array = json.getAsJsonArray("categories");
                for (JsonElement element : array) {
                    categories.add(MaterialCategories.get(element.getAsString()));
                }
            }

            int minTier = GsonHelper.getAsInt(json, "min_tier", 0);
            int maxTier = GsonHelper.getAsInt(json, "max_tier", Integer.MAX_VALUE);

            PartMaterialIngredient ret = of(type, gearType, minTier, maxTier);
            ret.categories.addAll(categories);
            return ret;
        }

        @Override
        public void write(FriendlyByteBuf buffer, PartMaterialIngredient ingredient) {
            buffer.writeResourceLocation(ingredient.partType.getName());
            buffer.writeUtf(ingredient.gearType.getName());
            buffer.writeByte(ingredient.categories.size());
            ingredient.categories.forEach(cat -> buffer.writeUtf(cat.getName()));
            buffer.writeVarInt(ingredient.minTier);
            buffer.writeVarInt(ingredient.maxTier);
        }
    }
}
