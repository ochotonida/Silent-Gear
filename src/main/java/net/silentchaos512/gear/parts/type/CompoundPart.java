package net.silentchaos512.gear.parts.type;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.event.GetStatModifierEvent;
import net.silentchaos512.gear.api.material.IMaterial;
import net.silentchaos512.gear.api.parts.IPartPosition;
import net.silentchaos512.gear.api.parts.IPartSerializer;
import net.silentchaos512.gear.api.parts.PartTraitInstance;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.api.stats.ItemStat;
import net.silentchaos512.gear.api.stats.StatInstance;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.gear.material.MaterialManager;
import net.silentchaos512.gear.item.CompoundPartItem;
import net.silentchaos512.gear.parts.AbstractGearPart;
import net.silentchaos512.gear.parts.PartData;
import net.silentchaos512.gear.parts.PartPositions;
import net.silentchaos512.gear.parts.PartTextureType;
import net.silentchaos512.gear.util.SynergyUtils;
import net.silentchaos512.gear.util.TraitHelper;
import net.silentchaos512.utils.EnumUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompoundPart extends AbstractGearPart {
    public static final Serializer SERIALIZER = new Serializer(SilentGear.getId("compound_part"), CompoundPart::new);

    private PartType partType;
    private IPartPosition partPosition;

    public CompoundPart(ResourceLocation name) {
        super(name);
    }

    @Override
    public PartType getType() {
        return this.partType;
    }

    @Override
    public IPartPosition getPartPosition() {
        return this.partPosition;
    }

    @Override
    public IPartSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public PartTextureType getLiteTexture(PartData part, ItemStack gear) {
        MaterialInstance material = CompoundPartItem.getPrimaryMaterial(part.getCraftingItem());
        if (material != null) {
            return material.getMaterial().getTexture(partType, gear);
        }
        return super.getLiteTexture(part, gear);
    }

    @Override
    public int getColor(PartData part, ItemStack gear, int animationFrame) {
        return CompoundPartItem.getColor(part.getCraftingItem());
    }

    @Override
    public ITextComponent getDisplayName(@Nullable PartData part, ItemStack gear) {
        if (part != null) {
            return part.getCraftingItem().getDisplayName();
        }
        return super.getDisplayName(null, gear);
    }

    @Override
    public ITextComponent getMaterialName(@Nullable PartData part, ItemStack gear) {
        if (part != null) {
            MaterialInstance material = CompoundPartItem.getPrimaryMaterial(part.getCraftingItem());
            if (material != null) {
                return material.getDisplayName(partType, gear);
            }
        }
        return super.getMaterialName(null, gear);
    }

    @Override
    public Collection<StatInstance> getStatModifiers(ItemStack gear, ItemStat stat, PartData part) {
        // Get any base modifiers for this part (should be none, normally?)
        List<StatInstance> baseStats = new ArrayList<>(this.stats.get(stat));
        ItemStack stack = part.getCraftingItem();
        if (!(stack.getItem() instanceof CompoundPartItem)) {
            return baseStats;
        }

        // Get the materials and all the stat modifiers they provide for this stat
        List<MaterialInstance> materials = CompoundPartItem.getMaterials(stack);
        Collection<StatInstance> statMods = materials.stream()
                .flatMap(m -> m.getStatModifiers(stat, this.partType, gear).stream())
                .collect(Collectors.toList());

        if (statMods.isEmpty()) {
            // No modifiers for this stat, so doing anything else is pointless
            return statMods;
        }

        // Synergy
        if (stat.doesSynergyApply()) {
            float synergy = SynergyUtils.getSynergy(partType, materials, getTraits(gear, part));
            statMods.add(new StatInstance(synergy - 1, StatInstance.Operation.MUL2));
        }

        GetStatModifierEvent event = new GetStatModifierEvent(part, stat, baseStats);
        MinecraftForge.EVENT_BUS.post(event);

        // Average together all modifiers of the same op. This makes things like rods with varying
        // numbers of materials more "sane".
        List<StatInstance> ret = new ArrayList<>(event.getModifiers());
        for (StatInstance.Operation op : StatInstance.Operation.values()) {
            Collection<StatInstance> modsForOp = statMods.stream().filter(s -> s.getOp() == op).collect(Collectors.toList());
            if (!modsForOp.isEmpty()) {
                StatInstance mod = StatInstance.getWeightedAverageMod(modsForOp, op);
                ret.add(mod);
            }
        }

        return ret;
    }

    @Override
    public List<PartTraitInstance> getTraits(ItemStack gear, PartData part) {
        List<PartTraitInstance> ret = new ArrayList<>(super.getTraits(gear, part));
        TraitHelper.getTraits(CompoundPartItem.getMaterials(part.getCraftingItem()), this.partType, gear).forEach((trait, level) ->
                ret.add(new PartTraitInstance(trait, level, Collections.emptyList())));
        return ret;
    }

    @Override
    public PartData randomizeData(int tier) {
        for (ItemStack stack : getMaterials().getNormal().getMatchingStacks()) {
            if (stack.getItem() instanceof CompoundPartItem) {
                int materialCount = (int) Math.sqrt(1 + SilentGear.random.nextInt(10));
                List<MaterialInstance> materials = getRandomMaterials(materialCount, tier);
                ItemStack craftingItem = ((CompoundPartItem) stack.getItem()).create(materials);
                return PartData.of(this, craftingItem);
            }
        }
        return super.randomizeData(tier);
    }

    private List<MaterialInstance> getRandomMaterials(int count, int tier) {
        List<IMaterial> matsOfTier = MaterialManager.getValues().stream()
                .filter(m -> tier < 0 || tier == m.getTier(this.partType))
                .collect(Collectors.toList());
        if (!matsOfTier.isEmpty()) {
            List<MaterialInstance> ret = new ArrayList<>();
            for (int i = 0; i < count; ++i) {
                ret.add(MaterialInstance.of(matsOfTier.get(SilentGear.random.nextInt(matsOfTier.size()))));
            }
            return ret;
        }
        return getRandomMaterials(count, -1);
    }

    public static class Serializer extends AbstractGearPart.Serializer<CompoundPart> {
        Serializer(ResourceLocation serializerId, Function<ResourceLocation, CompoundPart> function) {
            super(serializerId, function);
        }

        @Override
        public CompoundPart read(ResourceLocation id, JsonObject json) {
            CompoundPart part = super.read(id, json, false);
            part.partType = PartType.get(new ResourceLocation(JSONUtils.getString(json, "part_type")));
            // FIXME
            part.partPosition = EnumUtils.byName(JSONUtils.getString(json, "part_position"), PartPositions.ANY);
            return part;
        }

        @Override
        public CompoundPart read(ResourceLocation id, PacketBuffer buffer) {
            CompoundPart part = super.read(id, buffer);
            part.partType = PartType.get(buffer.readResourceLocation());
            // FIXME
            part.partPosition = EnumUtils.byName(buffer.readString(), PartPositions.ANY);
            return part;
        }

        @Override
        public void write(PacketBuffer buffer, CompoundPart part) {
            super.write(buffer, part);
            buffer.writeResourceLocation(part.partType.getName());
            // FIXME
            for (PartPositions pos : PartPositions.values()) {
                if (pos == part.partPosition) {
                    buffer.writeString(pos.name());
                    break;
                }
            }
        }
    }
}
