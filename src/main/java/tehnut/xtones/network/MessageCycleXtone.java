package tehnut.xtones.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tehnut.xtones.ConfigHandler;
import tehnut.xtones.block.item.ItemBlockXtone;

public class MessageCycleXtone implements IMessage {

    private EnumHand hand;
    private boolean increment;

    public MessageCycleXtone() {

    }

    public MessageCycleXtone(EnumHand hand, boolean increment) {
        this.hand = hand;
        this.increment = increment;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        increment = buf.readBoolean();
        hand = buf.isReadable(Byte.BYTES) ? EnumHand.values()[buf.readByte()] : EnumHand.MAIN_HAND;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(increment);
        buf.writeByte(hand.ordinal());
    }

    public static class Handler implements IMessageHandler<MessageCycleXtone, IMessage> {
        @Override
        public IMessage onMessage(MessageCycleXtone message, MessageContext ctx) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
                if (ConfigHandler.disableScrollCycling) {
                    ctx.getServerHandler().player.sendStatusMessage(new TextComponentTranslation("chat.xtones.scroll.disable").setStyle(new Style().setColor(TextFormatting.RED)), true);
                    return;
                }

                ItemStack held = ctx.getServerHandler().player.getHeldItem(message.hand);
                if (held.getItem() instanceof ItemBlockXtone) {
                    held.setItemDamage((held.getItemDamage() + (message.increment ? 1 : -1)) & 15);
                }
            });
            return null;
        }
    }
}
