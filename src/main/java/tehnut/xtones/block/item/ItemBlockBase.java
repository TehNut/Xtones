package tehnut.xtones.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import tehnut.xtones.block.BlockBase;

import javax.annotation.Nonnull;

public class ItemBlockBase extends ItemBlock {
    public ItemBlockBase(Block block) {
        super(block);
    }

    @Override
    @Nonnull
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack) + '.' + BlockBase.Type.getName(stack);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
