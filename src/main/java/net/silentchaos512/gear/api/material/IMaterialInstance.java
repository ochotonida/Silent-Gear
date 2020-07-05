package net.silentchaos512.gear.api.material;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.silentchaos512.gear.api.parts.MaterialGrade;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.api.stats.ItemStat;
import net.silentchaos512.gear.util.TextUtil;

import javax.annotation.Nullable;

public interface IMaterialInstance {
    ResourceLocation getMaterialId();

    @Nullable
    IMaterial getMaterial();

    MaterialGrade getGrade();

    ItemStack getItem();

    int getTier(PartType partType);

    default float getStat(ItemStat stat, PartType partType) {
        return getStat(stat, partType, ItemStack.EMPTY);
    }

    float getStat(ItemStat stat, PartType partType, ItemStack gear);

    CompoundNBT write(CompoundNBT nbt);

    int getColor(PartType partType, ItemStack gear);

    default int getColor(PartType partType) {
        return getColor(partType, ItemStack.EMPTY);
    }

    ITextComponent getDisplayName(PartType partType, ItemStack gear);

    default ITextComponent getDisplayName(PartType partType) {
        return getDisplayName(partType, ItemStack.EMPTY);
    }

    default ITextComponent getDisplayNameWithGrade(PartType partType) {
        ITextComponent gradeSuffix = TextUtil.translate("misc", "spaceBrackets", getGrade().getDisplayName());
        return getDisplayName(partType, ItemStack.EMPTY).appendSibling(gradeSuffix);
    }
}
