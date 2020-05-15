package tehnut.xtones.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tehnut.xtones.Xtones;
import tehnut.xtones.config.XtonesConfig;

@EventBusSubscriber(modid = Xtones.ID)
public final class XtonesNetwork {
    private static final SimpleNetworkWrapper WRAPPER = new SimpleNetworkWrapper(Xtones.ID);
    private static final Logger LOGGER = LogManager.getLogger();

    private XtonesNetwork() {
    }

    public static void init() {
        WRAPPER.registerMessage(XtoneCycleHandler.INSTANCE, XtoneCycleMessage.class, 0, Side.SERVER);
        WRAPPER.registerMessage(XtoneConfigHandler.INSTANCE, XtoneConfigMessage.class, 1, Side.CLIENT);
    }

    public static void cycleXtone(final EnumHand hand, final MouseEvent event) {
        WRAPPER.sendToServer(new XtoneCycleMessage(hand, event.getDwheel()));
    }

    public static void syncConfig(final EntityPlayerMP player) {
        WRAPPER.sendTo(new XtoneConfigMessage(XtonesConfig.hasXtoneCycling()), player);
    }

    @SubscribeEvent
    static void playerLoggedIn(final PlayerLoggedInEvent event) {
        syncConfig((EntityPlayerMP) event.player);
    }
}
