package tehnut.xtones.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.xtones.Tone;

public final class XtoneBlock extends Block {
    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, Tone.VARIANTS - 1);

    private final boolean glassLike;
    private final BlockRenderLayer renderLayer;

    public XtoneBlock(final Tone tone) {
        super(tone.isGlassLike() ? Material.GLASS : Material.ROCK);
        if (this.glassLike = tone.isGlassLike()) {
            this.renderLayer = BlockRenderLayer.TRANSLUCENT;
            this.setSoundType(SoundType.GLASS);
            this.setLightOpacity(0);
        } else {
            this.renderLayer = BlockRenderLayer.SOLID;
        }
    }

    public boolean isGlassLike() {
        return this.glassLike;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return this.renderLayer;
    }

    @Override
    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        if (this.glassLike) {
            final IBlockState neighbor = world.getBlockState(pos.offset(side));
            if (state.getBlock() == neighbor.getBlock()) {
                return !neighbor.getValue(VARIANT).equals(state.getValue(VARIANT));
            }
        }
        return super.shouldSideBeRendered(state, world, pos, side);
    }

    @Override
    @Deprecated
    public boolean isFullCube(final IBlockState state) {
        return !this.glassLike;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(final IBlockState state) {
        return !this.glassLike;
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(VARIANT, meta & (Tone.VARIANTS - 1));
    }

    @Override
    public int getMetaFromState(final IBlockState state) {
        return state.getValue(VARIANT);
    }

    @Override
    public int damageDropped(final IBlockState state) {
        return state.getValue(VARIANT);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }
}
