package tehnut.xtones.block.item;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.xtones.ClientHandler;
import tehnut.xtones.block.BlockXtone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockXtone extends ItemBlock {

    public ItemBlockXtone(Block block) {
        super(block);

        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    @Nonnull
    public String getHighlightTip(ItemStack stack, @Nonnull String displayName) {
        return super.getHighlightTip(stack, displayName) + " (" + ((stack.getItemDamage() & 15) + 1) + ")";
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        if (!isInCreativeTab(tab))
            return;

        for (BlockXtone.Variant variant : BlockXtone.Variant.values())
            subItems.add(new ItemStack(this, 1, variant.ordinal()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flag) {
        tooltip.add(I18n.format("tooltip.xtones.type", ((stack.getItemDamage() & 15) + 1)));
        tooltip.add(I18n.format("tooltip.xtones.cycle", ClientHandler.SCROLL_CATALYST.getDisplayName()));
        super.addInformation(stack, world, tooltip, flag);
    }
}
