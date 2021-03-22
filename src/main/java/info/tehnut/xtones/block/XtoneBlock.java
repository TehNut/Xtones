package info.tehnut.xtones.block;

import info.tehnut.xtones.Tone;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public final class XtoneBlock extends Block {
    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

    private final boolean glassLike;
    private final BlockRenderLayer renderLayer;

    public XtoneBlock(final Tone tone) {
        super(tone.isGlassLike() ? Material.GLASS : Material.ROCK);
        this.glassLike = tone.isGlassLike();
        if (this.glassLike) {
            this.renderLayer = BlockRenderLayer.TRANSLUCENT;
            this.setSoundType(SoundType.GLASS);
            this.setLightOpacity(0);
        } else {
            this.renderLayer = BlockRenderLayer.SOLID;
        }
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
                return neighbor.getValue(VARIANT) != state.getValue(VARIANT);
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
        return this.getDefaultState().withProperty(VARIANT, Variant.VARIANTS[meta & (Tone.VARIANTS - 1)]);
    }

    @Override
    public int getMetaFromState(final IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public int damageDropped(final IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    public enum Variant implements IStringSerializable {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        ELEVEN,
        TWELVE,
        THIRTEEN,
        FOURTEEN,
        FIFTEEN,
        SIXTEEN,
        ;

        private static final Variant[] VARIANTS = values();

        @Override
        public final String getName() {
            return this.toString();
        }

        @Override
        public final String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
