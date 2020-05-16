package info.tehnut.xtones.item;

import info.tehnut.xtones.Tone;
import info.tehnut.xtones.client.XtonesClient;
import info.tehnut.xtones.config.XtonesConfig;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public final class XtoneBlockItem extends ItemMultiTexture {
    public XtoneBlockItem(final Block block) {
        super(block, Blocks.AIR, XtoneBlockItem::getTranslationKeySuffix);
    }

    private static String getTranslationKeySuffix(final ItemStack stack) {
        return Integer.toString(stack.getItemDamage() & (Tone.VARIANTS - 1));
    }

    @Override
    public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> stacks) {
        if (this.isInCreativeTab(tab)) {
            for (int variant = 0; variant < Tone.VARIANTS; ++variant) {
                stacks.add(new ItemStack(this, 1, variant));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack stack, final @Nullable World world, final List<String> tooltip, final ITooltipFlag flag) {
        if (XtonesConfig.hasXtoneCycling()) {
            if (!XtonesClient.canCycleXtones()) {
                tooltip.add(new TextComponentTranslation("tooltip.xtones.cycle_disabled")
                    .setStyle(new Style().setColor(TextFormatting.RED))
                    .getFormattedText());
            } else if (XtonesConfig.cyclingTooltip.isVisible()) {
                tooltip.add(I18n.format("tooltip.xtones.cycle", XtonesClient.getScrollModifierName()));
            }
        }
    }
}
