package tehnut.xtones.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import tehnut.xtones.Xtones;

import java.util.Locale;

public class BlockXtone extends BlockEnum<BlockXtone.XtoneType> {

    private final String name;
    private final ItemStack craftStack;

    private boolean seeThrough;

    public BlockXtone(String name, ItemStack craftStack) {
        super(Material.ROCK, XtoneType.class, "variant");

        this.name = name;
        this.craftStack = craftStack;

        setUnlocalizedName(Xtones.ID + "." + name);
        setCreativeTab(Xtones.TAB);
        setResistance(3.0F);
        setHardness(3.0F);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return seeThrough ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID;
    }

    @Override
    public boolean isFullyOpaque(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !seeThrough;
    }

    @Override
    public boolean causesSuffocation() {
        return !seeThrough;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return !seeThrough;
    }

    public BlockXtone setSeeThrough() {
        this.seeThrough = true;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemStack getCraftStack() {
        return craftStack;
    }

    public enum XtoneType implements IStringSerializable {
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

        private final int displayIndex;

        XtoneType() {
            this.displayIndex = ordinal() + 1;
        }

        public int getDisplayIndex() {
            return displayIndex;
        }

        @Override
        public String getName() {
            return toString().toLowerCase(Locale.ENGLISH);
        }
    }
}
