package net.silentchaos512.gear.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeType;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.config.Config;
import net.silentchaos512.gear.item.*;
import net.silentchaos512.gear.item.blueprint.GearBlueprintItem;
import net.silentchaos512.gear.item.blueprint.PartBlueprintItem;
import net.silentchaos512.gear.item.blueprint.book.BlueprintBookItem;
import net.silentchaos512.gear.item.gear.*;
import net.silentchaos512.lib.registry.ItemRegistryObject;
import net.silentchaos512.lib.util.TimeUtils;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "OverlyCoupledClass"})
public final class ModItems {
    public static final ItemRegistryObject<GuideBookItem> GUIDE_BOOK = register("guide_book", () ->
            new GuideBookItem(unstackableProps()));

    public static final ItemRegistryObject<BlueprintPackageItem> BLUEPRINT_PACKAGE = register("blueprint_package", () ->
            new BlueprintPackageItem(SilentGear.getId("starter_blueprints")));

    public static final ItemRegistryObject<Item> MOD_KIT = register("mod_kit", () ->
            new ModKitItem(unstackableProps().rarity(Rarity.UNCOMMON)));

    // Repair Kits
    public static final ItemRegistryObject<Item> VERY_CRUDE_REPAIR_KIT = register("very_crude_repair_kit", () -> new RepairKitItem(
            Config.Common.repairKitVeryCrudeCapacity::get,
            Config.Common.repairKitVeryCrudeEfficiency::get,
            unstackableProps().rarity(Rarity.COMMON)));
    public static final ItemRegistryObject<Item> CRUDE_REPAIR_KIT = register("crude_repair_kit", () -> new RepairKitItem(
            Config.Common.repairKitCrudeCapacity::get,
            Config.Common.repairKitCrudeEfficiency::get,
            unstackableProps().rarity(Rarity.COMMON)));
    public static final ItemRegistryObject<Item> STURDY_REPAIR_KIT = register("sturdy_repair_kit", () -> new RepairKitItem(
            Config.Common.repairKitSturdyCapacity::get,
            Config.Common.repairKitSturdyEfficiency::get,
            unstackableProps().rarity(Rarity.UNCOMMON)));
    public static final ItemRegistryObject<Item> CRIMSON_REPAIR_KIT = register("crimson_repair_kit", () -> new RepairKitItem(
            Config.Common.repairKitCrimsonCapacity::get,
            Config.Common.repairKitCrimsonEfficiency::get,
            unstackableProps().rarity(Rarity.RARE)));
    public static final ItemRegistryObject<Item> AZURE_REPAIR_KIT = register("azure_repair_kit", () -> new RepairKitItem(
            Config.Common.repairKitAzureCapacity::get,
            Config.Common.repairKitAzureEfficiency::get,
            unstackableProps().rarity(Rarity.EPIC)));

    //region Blueprints and templates
    public static final ItemRegistryObject<BlueprintBookItem> BLUEPRINT_BOOK = register("blueprint_book", () ->
            new BlueprintBookItem(unstackableProps().rarity(Rarity.UNCOMMON)));
    // Blueprints
    public static final ItemRegistryObject<PartBlueprintItem> JEWELER_TOOLS = register("jeweler_tools", () ->
            new JewelerKitItem(PartType.ADORNMENT, false, unstackableProps()));
    public static final ItemRegistryObject<PartBlueprintItem> ROD_BLUEPRINT = registerPartBlueprint(PartType.ROD, false);
    public static final ItemRegistryObject<PartBlueprintItem> TIP_BLUEPRINT = registerPartBlueprint(PartType.TIP, false);
    public static final ItemRegistryObject<PartBlueprintItem> COATING_BLUEPRINT = registerPartBlueprint(PartType.COATING, false);
    public static final ItemRegistryObject<PartBlueprintItem> GRIP_BLUEPRINT = registerPartBlueprint(PartType.GRIP, false);
    public static final ItemRegistryObject<PartBlueprintItem> BINDING_BLUEPRINT = registerPartBlueprint(PartType.BINDING, false);
    public static final ItemRegistryObject<PartBlueprintItem> LINING_BLUEPRINT = registerPartBlueprint(PartType.LINING, false);
    public static final ItemRegistryObject<PartBlueprintItem> BOWSTRING_BLUEPRINT = registerPartBlueprint(PartType.BOWSTRING, false);
    public static final ItemRegistryObject<PartBlueprintItem> FLETCHING_BLUEPRINT = registerPartBlueprint(PartType.FLETCHING, false);
    public static final ItemRegistryObject<GearBlueprintItem> SWORD_BLUEPRINT = registerGearBlueprint(GearType.SWORD, false);
    public static final ItemRegistryObject<GearBlueprintItem> KATANA_BLUEPRINT = registerGearBlueprint(GearType.KATANA, false);
    public static final ItemRegistryObject<GearBlueprintItem> MACHETE_BLUEPRINT = registerGearBlueprint(GearType.MACHETE, false);
    public static final ItemRegistryObject<GearBlueprintItem> SPEAR_BLUEPRINT = registerGearBlueprint(GearType.SPEAR, false);
    public static final ItemRegistryObject<GearBlueprintItem> KNIFE_BLUEPRINT = registerGearBlueprint(GearType.KNIFE, false);
    public static final ItemRegistryObject<GearBlueprintItem> DAGGER_BLUEPRINT = registerGearBlueprint(GearType.DAGGER, false);
    public static final ItemRegistryObject<GearBlueprintItem> PICKAXE_BLUEPRINT = registerGearBlueprint(GearType.PICKAXE, false);
    public static final ItemRegistryObject<GearBlueprintItem> SHOVEL_BLUEPRINT = registerGearBlueprint(GearType.SHOVEL, false);
    public static final ItemRegistryObject<GearBlueprintItem> AXE_BLUEPRINT = registerGearBlueprint(GearType.AXE, false);
    public static final ItemRegistryObject<GearBlueprintItem> PAXEL_BLUEPRINT = registerGearBlueprint(GearType.PAXEL, false);
    public static final ItemRegistryObject<GearBlueprintItem> HAMMER_BLUEPRINT = registerGearBlueprint(GearType.HAMMER, false);
    public static final ItemRegistryObject<GearBlueprintItem> EXCAVATOR_BLUEPRINT = registerGearBlueprint(GearType.EXCAVATOR, false);
    public static final ItemRegistryObject<GearBlueprintItem> SAW_BLUEPRINT = registerGearBlueprint(GearType.SAW, false);
    public static final ItemRegistryObject<GearBlueprintItem> MATTOCK_BLUEPRINT = registerGearBlueprint(GearType.MATTOCK, false);
    public static final ItemRegistryObject<GearBlueprintItem> PROSPECTOR_HAMMER_BLUEPRINT = registerGearBlueprint(GearType.PROSPECTOR_HAMMER, false);
    public static final ItemRegistryObject<GearBlueprintItem> SICKLE_BLUEPRINT = registerGearBlueprint(GearType.SICKLE, false);
    public static final ItemRegistryObject<GearBlueprintItem> SHEARS_BLUEPRINT = registerGearBlueprint(GearType.SHEARS, false);
    public static final ItemRegistryObject<GearBlueprintItem> FISHING_ROD_BLUEPRINT = registerGearBlueprint(GearType.FISHING_ROD, false);
    public static final ItemRegistryObject<GearBlueprintItem> BOW_BLUEPRINT = registerGearBlueprint(GearType.BOW, false);
    public static final ItemRegistryObject<GearBlueprintItem> CROSSBOW_BLUEPRINT = registerGearBlueprint(GearType.CROSSBOW, false);
    public static final ItemRegistryObject<GearBlueprintItem> SLINGSHOT_BLUEPRINT = registerGearBlueprint(GearType.SLINGSHOT, false);
    public static final ItemRegistryObject<GearBlueprintItem> SHIELD_BLUEPRINT = registerGearBlueprint(GearType.SHIELD, false);
    public static final ItemRegistryObject<GearBlueprintItem> HELMET_BLUEPRINT = registerGearBlueprint(GearType.HELMET, false);
    public static final ItemRegistryObject<GearBlueprintItem> CHESTPLATE_BLUEPRINT = registerGearBlueprint(GearType.CHESTPLATE, false);
    public static final ItemRegistryObject<GearBlueprintItem> LEGGINGS_BLUEPRINT = registerGearBlueprint(GearType.LEGGINGS, false);
    public static final ItemRegistryObject<GearBlueprintItem> BOOTS_BLUEPRINT = registerGearBlueprint(GearType.BOOTS, false);
    public static final ItemRegistryObject<GearBlueprintItem> ELYTRA_BLUEPRINT = registerGearBlueprint(GearType.ELYTRA, false);
    public static final ItemRegistryObject<GearBlueprintItem> ARROW_BLUEPRINT = registerGearBlueprint(GearType.ARROW, false);
    public static final ItemRegistryObject<GearBlueprintItem> RING_BLUEPRINT = registerGearBlueprint(GearType.RING, false);
    public static final ItemRegistryObject<GearBlueprintItem> BRACELET_BLUEPRINT = registerGearBlueprint(GearType.BRACELET, false);
    // Templates
    public static final ItemRegistryObject<PartBlueprintItem> ROD_TEMPLATE = registerPartBlueprint(PartType.ROD, true);
    public static final ItemRegistryObject<PartBlueprintItem> TIP_TEMPLATE = registerPartBlueprint(PartType.TIP, true);
    public static final ItemRegistryObject<PartBlueprintItem> COATING_TEMPLATE = registerPartBlueprint(PartType.COATING, true);
    public static final ItemRegistryObject<PartBlueprintItem> GRIP_TEMPLATE = registerPartBlueprint(PartType.GRIP, true);
    public static final ItemRegistryObject<PartBlueprintItem> BINDING_TEMPLATE = registerPartBlueprint(PartType.BINDING, true);
    public static final ItemRegistryObject<PartBlueprintItem> LINING_TEMPLATE = registerPartBlueprint(PartType.LINING, true);
    public static final ItemRegistryObject<PartBlueprintItem> BOWSTRING_TEMPLATE = registerPartBlueprint(PartType.BOWSTRING, true);
    public static final ItemRegistryObject<PartBlueprintItem> FLETCHING_TEMPLATE = registerPartBlueprint(PartType.FLETCHING, true);
    public static final ItemRegistryObject<GearBlueprintItem> SWORD_TEMPLATE = registerGearBlueprint(GearType.SWORD, true);
    public static final ItemRegistryObject<GearBlueprintItem> KATANA_TEMPLATE = registerGearBlueprint(GearType.KATANA, true);
    public static final ItemRegistryObject<GearBlueprintItem> MACHETE_TEMPLATE = registerGearBlueprint(GearType.MACHETE, true);
    public static final ItemRegistryObject<GearBlueprintItem> SPEAR_TEMPLATE = registerGearBlueprint(GearType.SPEAR, true);
    public static final ItemRegistryObject<GearBlueprintItem> KNIFE_TEMPLATE = registerGearBlueprint(GearType.KNIFE, true);
    public static final ItemRegistryObject<GearBlueprintItem> DAGGER_TEMPLATE = registerGearBlueprint(GearType.DAGGER, true);
    public static final ItemRegistryObject<GearBlueprintItem> PICKAXE_TEMPLATE = registerGearBlueprint(GearType.PICKAXE, true);
    public static final ItemRegistryObject<GearBlueprintItem> SHOVEL_TEMPLATE = registerGearBlueprint(GearType.SHOVEL, true);
    public static final ItemRegistryObject<GearBlueprintItem> AXE_TEMPLATE = registerGearBlueprint(GearType.AXE, true);
    public static final ItemRegistryObject<GearBlueprintItem> PAXEL_TEMPLATE = registerGearBlueprint(GearType.PAXEL, true);
    public static final ItemRegistryObject<GearBlueprintItem> HAMMER_TEMPLATE = registerGearBlueprint(GearType.HAMMER, true);
    public static final ItemRegistryObject<GearBlueprintItem> EXCAVATOR_TEMPLATE = registerGearBlueprint(GearType.EXCAVATOR, true);
    public static final ItemRegistryObject<GearBlueprintItem> SAW_TEMPLATE = registerGearBlueprint(GearType.SAW, true);
    public static final ItemRegistryObject<GearBlueprintItem> MATTOCK_TEMPLATE = registerGearBlueprint(GearType.MATTOCK, true);
    public static final ItemRegistryObject<GearBlueprintItem> PROSPECTOR_HAMMER_TEMPLATE = registerGearBlueprint(GearType.PROSPECTOR_HAMMER, true);
    public static final ItemRegistryObject<GearBlueprintItem> SICKLE_TEMPLATE = registerGearBlueprint(GearType.SICKLE, true);
    public static final ItemRegistryObject<GearBlueprintItem> SHEARS_TEMPLATE = registerGearBlueprint(GearType.SHEARS, true);
    public static final ItemRegistryObject<GearBlueprintItem> FISHING_ROD_TEMPLATE = registerGearBlueprint(GearType.FISHING_ROD, true);
    public static final ItemRegistryObject<GearBlueprintItem> BOW_TEMPLATE = registerGearBlueprint(GearType.BOW, true);
    public static final ItemRegistryObject<GearBlueprintItem> CROSSBOW_TEMPLATE = registerGearBlueprint(GearType.CROSSBOW, true);
    public static final ItemRegistryObject<GearBlueprintItem> SLINGSHOT_TEMPLATE = registerGearBlueprint(GearType.SLINGSHOT, true);
    public static final ItemRegistryObject<GearBlueprintItem> SHIELD_TEMPLATE = registerGearBlueprint(GearType.SHIELD, true);
    public static final ItemRegistryObject<GearBlueprintItem> HELMET_TEMPLATE = registerGearBlueprint(GearType.HELMET, true);
    public static final ItemRegistryObject<GearBlueprintItem> CHESTPLATE_TEMPLATE = registerGearBlueprint(GearType.CHESTPLATE, true);
    public static final ItemRegistryObject<GearBlueprintItem> LEGGINGS_TEMPLATE = registerGearBlueprint(GearType.LEGGINGS, true);
    public static final ItemRegistryObject<GearBlueprintItem> BOOTS_TEMPLATE = registerGearBlueprint(GearType.BOOTS, true);
    public static final ItemRegistryObject<GearBlueprintItem> ELYTRA_TEMPLATE = registerGearBlueprint(GearType.ELYTRA, true);
    public static final ItemRegistryObject<GearBlueprintItem> ARROW_TEMPLATE = registerGearBlueprint(GearType.ARROW, true);
    public static final ItemRegistryObject<GearBlueprintItem> RING_TEMPLATE = registerGearBlueprint(GearType.RING, true);
    public static final ItemRegistryObject<GearBlueprintItem> BRACELET_TEMPLATE = registerGearBlueprint(GearType.BRACELET, true);
    //endregion

    //region Compound Parts and Tool Heads
    // Tool Heads
    public static final ItemRegistryObject<ToolHeadItem> SWORD_BLADE = registerCompoundPart("sword_blade", () ->
            new ToolHeadItem(GearType.SWORD, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> KATANA_BLADE = registerCompoundPart("katana_blade", () ->
            new ToolHeadItem(GearType.KATANA, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> MACHETE_BLADE = registerCompoundPart("machete_blade", () ->
            new ToolHeadItem(GearType.MACHETE, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> SPEAR_TIP = registerCompoundPart("spear_tip", () ->
            new ToolHeadItem(GearType.SPEAR, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> KNIFE_BLADE = registerCompoundPart("knife_blade", () ->
            new ToolHeadItem(GearType.KNIFE, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> DAGGER_BLADE = registerCompoundPart("dagger_blade", () ->
            new ToolHeadItem(GearType.DAGGER, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> PICKAXE_HEAD = registerCompoundPart("pickaxe_head", () ->
            new ToolHeadItem(GearType.PICKAXE, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> SHOVEL_HEAD = registerCompoundPart("shovel_head", () ->
            new ToolHeadItem(GearType.SHOVEL, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> AXE_HEAD = registerCompoundPart("axe_head", () ->
            new ToolHeadItem(GearType.AXE, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> PAXEL_HEAD = registerCompoundPart("paxel_head", () ->
            new ToolHeadItem(GearType.PAXEL, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> HAMMER_HEAD = registerCompoundPart("hammer_head", () ->
            new ToolHeadItem(GearType.HAMMER, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> EXCAVATOR_HEAD = registerCompoundPart("excavator_head", () ->
            new ToolHeadItem(GearType.EXCAVATOR, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> SAW_BLADE = registerCompoundPart("saw_blade", () ->
            new ToolHeadItem(GearType.SAW, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> MATTOCK_HEAD = registerCompoundPart("mattock_head", () ->
            new ToolHeadItem(GearType.MATTOCK, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> PROSPECTOR_HAMMER_HEAD = registerCompoundPart("prospector_hammer_head", () ->
            new ToolHeadItem(GearType.PROSPECTOR_HAMMER, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> SICKLE_BLADE = registerCompoundPart("sickle_blade", () ->
            new ToolHeadItem(GearType.SICKLE, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> SHEARS_BLADES = registerCompoundPart("shears_blades", () ->
            new ToolHeadItem(GearType.SHEARS, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> FISHING_REEL_AND_HOOK = registerCompoundPart("fishing_reel_and_hook", () ->
            new ToolHeadItem(GearType.FISHING_ROD, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> BOW_LIMBS = registerCompoundPart("bow_limbs", () ->
            new ToolHeadItem(GearType.BOW, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> CROSSBOW_LIMBS = registerCompoundPart("crossbow_limbs", () ->
            new ToolHeadItem(GearType.CROSSBOW, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> SLINGSHOT_LIMBS = registerCompoundPart("slingshot_limbs", () ->
            new ToolHeadItem(GearType.SLINGSHOT, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> SHIELD_PLATE = registerCompoundPart("shield_plate", () ->
            new ToolHeadItem(GearType.SHIELD, new Item.Properties().stacksTo(1)));
    @Deprecated public static final ItemRegistryObject<ToolHeadItem> ARMOR_BODY = registerCompoundPart("armor_body", () ->
            new ToolHeadItem(GearType.ARMOR, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> HELMET_PLATES = registerCompoundPart("helmet_plates", () ->
            new ToolHeadItem(GearType.HELMET, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> CHESTPLATE_PLATES = registerCompoundPart("chestplate_plates", () ->
            new ToolHeadItem(GearType.CHESTPLATE, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> LEGGING_PLATES = registerCompoundPart("legging_plates", () ->
            new ToolHeadItem(GearType.LEGGINGS, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> BOOT_PLATES = registerCompoundPart("boot_plates", () ->
            new ToolHeadItem(GearType.BOOTS, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> ELYTRA_WINGS = registerCompoundPart("elytra_wings", () ->
            new ToolHeadItem(GearType.ELYTRA, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> ARROW_HEADS = registerCompoundPart("arrow_heads", () ->
            new ToolHeadItem(GearType.ARROW, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> RING_SHANK = registerCompoundPart("ring_shank", () ->
            new ToolHeadItem(GearType.RING, unstackableProps()));
    public static final ItemRegistryObject<ToolHeadItem> BRACELET_BAND = registerCompoundPart("bracelet_band", () ->
            new ToolHeadItem(GearType.BRACELET, unstackableProps()));
    // Compound Parts
    public static final ItemRegistryObject<CompoundPartItem> ROD = registerCompoundPart("rod", () ->
            new CompoundPartItem(PartType.ROD, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> LONG_ROD = registerCompoundPart("long_rod", () ->
            new CompoundPartItem(PartType.ROD, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> TIP = registerCompoundPart("tip", () ->
            new CompoundPartItem(PartType.TIP, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> COATING = registerCompoundPart("coating", () ->
            new CompoundPartItem(PartType.COATING, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> GRIP = registerCompoundPart("grip", () ->
            new CompoundPartItem(PartType.GRIP, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> BINDING = registerCompoundPart("binding", () ->
            new CompoundPartItem(PartType.BINDING, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> LINING = registerCompoundPart("lining", () ->
            new CompoundPartItem(PartType.LINING, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> BOWSTRING = registerCompoundPart("bowstring", () ->
            new CompoundPartItem(PartType.BOWSTRING, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> FLETCHING = registerCompoundPart("fletching", () ->
            new CompoundPartItem(PartType.FLETCHING, baseProps()));
    public static final ItemRegistryObject<CompoundPartItem> ADORNMENT = registerCompoundPart("adornment", () ->
            new CompoundPartItem(PartType.ADORNMENT, baseProps()));
    //endregion

    // Compound materials
    public static final ItemRegistryObject<CompoundMaterialItem> ALLOY_INGOT = register("alloy_ingot", () ->
            new CompoundMaterialItem(baseProps()));
    public static final ItemRegistryObject<CompoundMaterialItem> HYBRID_GEM = register("hybrid_gem", () ->
            new CompoundMaterialItem(baseProps()));
    public static final ItemRegistryObject<CompoundMaterialItem> MIXED_FABRIC = register("mixed_fabric", () ->
            new CompoundMaterialItem(baseProps()));

    // Custom materials
    public static final ItemRegistryObject<CustomMaterialItem> CUSTOM_INGOT = register("custom_ingot", () ->
            new CustomMaterialItem(baseProps()));
    public static final ItemRegistryObject<CustomMaterialItem> CUSTOM_GEM = register("custom_gem", () ->
            new CustomMaterialItem(baseProps()));

    public static final ItemRegistryObject<CraftedMaterialItem> SHEET_METAL = register("sheet_metal", () ->
            new CraftedMaterialItem(baseProps()));

    static {
        CraftingItems.register(Registration.ITEMS);
    }

    public static final ItemRegistryObject<FragmentItem> FRAGMENT = register("fragment", () -> new FragmentItem(baseProps()));

    public static final ItemRegistryObject<Item> PEBBLE = register("pebble", () -> new SlingshotAmmoItem(baseProps()));

    public static final ItemRegistryObject<ItemNameBlockItem> FLAX_SEEDS = register("flax_seeds", () ->
            new SeedItem(ModBlocks.FLAX_PLANT.get(), baseProps()));
    public static final ItemRegistryObject<ItemNameBlockItem> FLUFFY_SEEDS = register("fluffy_seeds", () ->
            new SeedItem(ModBlocks.FLUFFY_PLANT.get(), baseProps()));

    public static final ItemRegistryObject<Item> NETHER_BANANA = register("nether_banana", () ->
            new Item(baseProps()
                    .food(new FoodProperties.Builder().nutrition(5).saturationMod(0.4f).build())));
    public static final ItemRegistryObject<Item> GOLDEN_NETHER_BANANA = register("golden_nether_banana", () ->
            new Item(baseProps()
                    .food(new FoodProperties.Builder().nutrition(10).saturationMod(1.0f)
                            .alwaysEat()
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, TimeUtils.ticksFromMinutes(10)), 1f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, TimeUtils.ticksFromMinutes(5)), 1f)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, TimeUtils.ticksFromSeconds(10)), 1f)
                            .build())));
    public static final ItemRegistryObject<Item> NETHERWOOD_CHARCOAL = register("netherwood_charcoal", () ->
            new Item(baseProps()) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return Config.Common.netherwoodCharcoalBurnTime.get();
                }
            });

    public static final ItemRegistryObject<GearSwordItem> SWORD = register("sword", () -> new GearSwordItem(GearType.SWORD));
    public static final ItemRegistryObject<GearSwordItem> KATANA = register("katana", () -> new GearSwordItem(GearType.KATANA));
    public static final ItemRegistryObject<GearMacheteItem> MACHETE = register("machete", () -> new GearMacheteItem(GearType.MACHETE));
    public static final ItemRegistryObject<GearSwordItem> SPEAR = register("spear", () -> new GearSwordItem(GearType.SPEAR));
    public static final ItemRegistryObject<GearDaggerItem> KNIFE = register("knife", () -> new GearDaggerItem(GearType.KNIFE));
    public static final ItemRegistryObject<GearDaggerItem> DAGGER = register("dagger", () -> new GearDaggerItem(GearType.DAGGER));
    public static final ItemRegistryObject<GearPickaxeItem> PICKAXE = register("pickaxe", () -> new GearPickaxeItem(GearType.PICKAXE));
    public static final ItemRegistryObject<GearShovelItem> SHOVEL = register("shovel", () -> new GearShovelItem(GearType.SHOVEL));
    public static final ItemRegistryObject<GearAxeItem> AXE = register("axe", () -> new GearAxeItem(GearType.AXE));
    public static final ItemRegistryObject<GearPaxelItem> PAXEL = register("paxel", () -> new GearPaxelItem(GearType.PAXEL));
    public static final ItemRegistryObject<GearHammerItem> HAMMER = register("hammer", () -> new GearHammerItem(GearType.HAMMER));
    public static final ItemRegistryObject<GearExcavatorItem> EXCAVATOR = register("excavator", () -> new GearExcavatorItem(GearType.EXCAVATOR));
    public static final ItemRegistryObject<GearSawItem> SAW = register("saw", () -> new GearSawItem(GearType.SAW));
    public static final ItemRegistryObject<GearProspectorHammerItem> PROSPECTOR_HAMMER = register("prospector_hammer", () -> new GearProspectorHammerItem(GearType.PROSPECTOR_HAMMER));
    public static final ItemRegistryObject<GearMattockItem> MATTOCK = register("mattock", () -> new GearMattockItem());
    public static final ItemRegistryObject<GearSickleItem> SICKLE = register("sickle", () -> new GearSickleItem(GearType.SICKLE));
    public static final ItemRegistryObject<GearShearsItem> SHEARS = register("shears", () -> new GearShearsItem());
    public static final ItemRegistryObject<GearFishingRod> FISHING_ROD = register("fishing_rod", GearFishingRod::new);
    public static final ItemRegistryObject<GearBowItem> BOW = register("bow", () -> new GearBowItem());
    public static final ItemRegistryObject<GearCrossbowItem> CROSSBOW = register("crossbow", () -> new GearCrossbowItem());
    public static final ItemRegistryObject<GearSlingshot> SLINGSHOT = register("slingshot", () -> new GearSlingshot());
    public static final ItemRegistryObject<GearShield> SHIELD = register("shield", () -> new GearShield());
    public static final ItemRegistryObject<GearArrowItem> ARROW = register("arrow", () -> new GearArrowItem(unstackableProps()));

    public static final ItemRegistryObject<GearArmorItem> HELMET = register("helmet", () -> new GearArmorItem(EquipmentSlot.HEAD));
    public static final ItemRegistryObject<GearArmorItem> CHESTPLATE = register("chestplate", () -> new GearArmorItem(EquipmentSlot.CHEST));
    public static final ItemRegistryObject<GearArmorItem> LEGGINGS = register("leggings", () -> new GearArmorItem(EquipmentSlot.LEGS));
    public static final ItemRegistryObject<GearArmorItem> BOOTS = register("boots", () -> new GearArmorItem(EquipmentSlot.FEET));

    public static final ItemRegistryObject<GearElytraItem> ELYTRA = register("elytra", () -> new GearElytraItem(unstackableProps()));

    public static final ItemRegistryObject<GearCurioItem> RING = register("ring", () ->
            new GearCurioItem(GearType.RING, "ring", unstackableProps()));
    public static final ItemRegistryObject<GearCurioItem> BRACELET = register("bracelet", () ->
            new GearCurioItem(GearType.BRACELET, "bracelet", unstackableProps()));

    private ModItems() {}

    static void register() {}

    private static Item.Properties baseProps() {
        return new Item.Properties().tab(SilentGear.ITEM_GROUP);
    }

    private static Item.Properties unstackableProps() {
        return baseProps().stacksTo(1);
    }

    private static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> item) {
        return new ItemRegistryObject<>(Registration.ITEMS.register(name, item));
    }

    private static <T extends CompoundPartItem> ItemRegistryObject<T> registerCompoundPart(String name, Supplier<T> item) {
        return register(name, item);
    }

    private static ItemRegistryObject<GearBlueprintItem> registerGearBlueprint(GearType gearType, boolean singleUse) {
        String name = gearType.getName() + "_" + (singleUse ? "template" : "blueprint");
        return register(name, () -> new GearBlueprintItem(gearType, singleUse, baseProps()));
    }

    private static ItemRegistryObject<PartBlueprintItem> registerPartBlueprint(PartType partType, boolean singleUse) {
        String name = partType.getName().getPath() + "_" + (singleUse ? "template" : "blueprint");
        return register(name, () -> new PartBlueprintItem(partType, singleUse, baseProps()));
    }
}
