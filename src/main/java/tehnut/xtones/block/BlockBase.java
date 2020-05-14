package tehnut.xtones.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

import java.util.Locale;

public class BlockBase extends Block {

    public static final IProperty<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockBase() {
        super(Material.ROCK);
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, Type.TYPES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subBlocks) {
        for (Type type : Type.TYPES)
            subBlocks.add(new ItemStack(this, 1, type.ordinal()));
    }

    public enum Type implements IStringSerializable {
        TILE,
        ;

        private static final Type[] TYPES = values();

        public static String getName(ItemStack stack) {
            return TYPES[stack.getItemDamage() & TYPES.length - 1].getName();
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
