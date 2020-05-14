package tehnut.xtones.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public class BlockXtone extends Block {

    public static final IProperty<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

    private final boolean seeThrough;

    public BlockXtone(String name) {
        super(Material.ROCK);

        seeThrough = "glaxx".equals(name);
    }

    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return seeThrough ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public int getLightOpacity(IBlockState state) {
        return seeThrough ? 0 : super.getLightOpacity(state);
    }

    @Override
    @Nonnull
    public Material getMaterial(IBlockState state) {
        return seeThrough ? Material.GLASS : super.getMaterial(state);
    }

    @Override
    @Nonnull
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return seeThrough ? SoundType.GLASS : super.getSoundType(state, world, pos, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (!seeThrough)
            return super.shouldSideBeRendered(state, world, pos, side);

        IBlockState neighbor = world.getBlockState(pos.offset(side));
        if (neighbor.getBlock() != this)
            return true;

        if (neighbor.getValue(VARIANT) != state.getValue(VARIANT))
            return true;

        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return !seeThrough;
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, Variant.VARIANTS[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subBlocks) {
        for (Variant variant : Variant.VARIANTS)
            subBlocks.add(new ItemStack(this, 1, variant.ordinal()));
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
        SIXTEEN;

        private static final Variant[] VARIANTS = values();

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
