package tehnut.xtones.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
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

    public boolean increment;

    public MessageCycleXtone() {

    }

    public MessageCycleXtone(boolean increment) {
        this.increment = increment;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.increment = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(increment);
    }

    public static class Handler implements IMessageHandler<MessageCycleXtone, IMessage> {
        @Override
        public IMessage onMessage(MessageCycleXtone message, MessageContext ctx) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
                if (ConfigHandler.disableScrollCycling) {
                    ctx.getServerHandler().player.sendStatusMessage(new TextComponentTranslation("chat.xtones.scroll.disable").setStyle(new Style().setColor(TextFormatting.RED)), true);
                    return;
                }

                ItemStack held = ctx.getServerHandler().player.getHeldItemMainhand();
                if (held.getItem() instanceof ItemBlockXtone) {
                    int damage = held.getItemDamage();
                    held.setItemDamage(MathHelper.clamp(damage + (message.increment ? -1 : 1), 0, 15));
                    if (damage == held.getItemDamage()) {
                        if (held.getItemDamage() == 0)
                            held.setItemDamage(15);
                        else if (held.getItemDamage() == 15)
                            held.setItemDamage(0);
                    }
                }
            });
            return null;
        }
    }
}
