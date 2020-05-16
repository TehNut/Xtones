package info.tehnut.xtones.network;

import com.mojang.authlib.GameProfile;
import info.tehnut.xtones.Tone;
import info.tehnut.xtones.config.XtonesConfig;
import info.tehnut.xtones.item.XtoneBlockItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

final class XtoneCycleHandler implements IMessageHandler<XtoneCycleMessage, IMessage> {
    static final XtoneCycleHandler INSTANCE = new XtoneCycleHandler();

    private static final Logger LOGGER = LogManager.getLogger();

    private XtoneCycleHandler() {
    }

    private static void tryCycle(final EntityPlayer player, final XtoneCycleMessage cycle) {
        if (XtonesConfig.disableScrollCycling) {
            final GameProfile profile = player.getGameProfile();
            LOGGER.warn("{} ({}) tried to cycle when cycling is disabled", profile.getName(), profile.getId());
            return;
        }
        if ((cycle.getHand() == EnumHand.OFF_HAND ? -1 : player.inventory.currentItem) == cycle.getExpectedSlot()) {
            final ItemStack held = player.getHeldItem(cycle.getHand());
            if (held.getItem() instanceof XtoneBlockItem) {
                held.setItemDamage(held.getItemDamage() + cycle.getOffset() & (Tone.VARIANTS - 1));
            } else {
                final GameProfile profile = player.getGameProfile();
                final String item = Objects.toString(held.getItem().getRegistryName(), "an unregistered item");
                LOGGER.warn("{} ({}) tried to cycle {}", profile.getName(), profile.getId(), item);
            }
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
