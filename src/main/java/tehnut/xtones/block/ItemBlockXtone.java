package tehnut.xtones.block;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.xtones.ClientHandler;

import java.util.List;

public class ItemBlockXtone extends ItemBlock {

    private final BlockXtone xtone;

    public ItemBlockXtone(BlockXtone block) {
        super(block);

        this.xtone = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getHighlightTip(ItemStack stack, String displayName) {
        return super.getHighlightTip(stack, displayName) + " (" + xtone.getTypes()[MathHelper.clamp(stack.getItemDamage(), 0, 15)].getDisplayIndex() + ")";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (BlockXtone.XtoneType type : xtone.getTypes())
            subItems.add(new ItemStack(this, 1, type.ordinal()));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tooltip.xtones.type", stack.getItemDamage() + 1));
        tooltip.add(I18n.format("tooltip.xtones.cycle", ClientHandler.SCROLL_CATALYST.getDisplayName()));
        super.addInformation(stack, playerIn, tooltip, advanced);
    }
}
