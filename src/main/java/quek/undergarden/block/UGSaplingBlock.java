package quek.undergarden.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class UGSaplingBlock extends SaplingBlock {

    public UGSaplingBlock(Tree tree) {
        super(tree, Properties.create(Material.PLANTS)
                .hardnessAndResistance(0F)
                .tickRandomly()
                .sound(SoundType.PLANT)
                .notSolid()
                .doesNotBlockMovement()
        );
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.randomTick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        this.placeTree(worldIn, pos, state, rand);
    }
}