package tehnut.xtones.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockEnum<E extends Enum<E> & IStringSerializable> extends Block {

    private final E[] types;
    private final PropertyEnum<E> property;
    private final BlockStateContainer realStateContainer;

    public BlockEnum(Material material, Class<E> enumClass, String propName) {
        super(material);

        this.types = enumClass.getEnumConstants();
        this.property = PropertyEnum.create(propName, enumClass);
        this.realStateContainer = createStateContainer();
        setDefaultState(getBlockState().getBaseState());
    }

    public BlockEnum(Material material, Class<E> enumClass) {
        this(material, enumClass, "type");
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).build(); // Blank to avoid crashes
    }

    @Override @Nonnull
    public final BlockStateContainer getBlockState() {
        return realStateContainer;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(property, types[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(property).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override @Nonnull
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, damageDropped(state));
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subBlocks) {
        for (E type : types)
            subBlocks.add(new ItemStack(this, 1, type.ordinal()));
    }

    protected BlockStateContainer createStateContainer() {
        return new BlockStateContainer.Builder(this).add(property).build();
    }

    public E[] getTypes() {
        return types;
    }

    public PropertyEnum<E> getProperty() {
        return property;
    }

    public BlockStateContainer getRealStateContainer() {
        return realStateContainer;
    }
}
