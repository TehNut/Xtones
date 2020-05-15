package tehnut.xtones.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import tehnut.xtones.Tone;
import tehnut.xtones.config.XtonesConfig;
import tehnut.xtones.item.XtoneBlockItem;

final class XtoneCycleHandler implements IMessageHandler<XtoneCycleMessage, IMessage> {
    static final XtoneCycleHandler INSTANCE = new XtoneCycleHandler();

    private XtoneCycleHandler() {
    }

    private static void tryCycle(final EntityPlayer player, final XtoneCycleMessage cycle) {
        if (XtonesConfig.disableScrollCycling) {
            final ITextComponent msg = new TextComponentTranslation("chat.xtones.scroll.disable");
            msg.getStyle().setColor(TextFormatting.RED);
            player.sendStatusMessage(msg, true);
            return;
        }
        final ItemStack held = player.getHeldItem(cycle.getHand());
        if (held.getItem() instanceof XtoneBlockItem) {
            held.setItemDamage(held.getItemDamage() + cycle.getOffset() & (Tone.VARIANTS - 1));
        }
    }

    @Override
    @Nullable
    public IMessage onMessage(final XtoneCycleMessage cycle, final MessageContext context) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
            tryCycle(context.getServerHandler().player, cycle);
        });
        return null;
    }
}
