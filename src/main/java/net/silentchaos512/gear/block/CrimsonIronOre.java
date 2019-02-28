package net.silentchaos512.gear.block;

import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class CrimsonIronOre extends BlockOre {
    public CrimsonIronOre() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(4, 10)
        );
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 2;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(IBlockState state) {
        return ToolType.PICKAXE;
    }
}
