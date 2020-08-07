package info.tehnut.xtones.network;

import info.tehnut.xtones.Xtones;
import info.tehnut.xtones.config.XtonesConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class XtonesNetwork {
    private static final SimpleNetworkWrapper WRAPPER = new SimpleNetworkWrapper(Xtones.ID);

    private static final int XTONE_CYCLE = 0;
    private static final int CONFIG_SYNC = 1;

    private XtonesNetwork() {
    }

    public static void init() {
        WRAPPER.registerMessage(XtoneCycleHandler.INSTANCE, XtoneCycleMessage.class, XTONE_CYCLE, Side.SERVER);
        WRAPPER.registerMessage(ConfigSyncHandler.INSTANCE, ConfigSyncMessage.class, CONFIG_SYNC, Side.CLIENT);
    }

    public static void cycleXtone(final EntityPlayer player, final EnumHand hand, final int scroll) {
        WRAPPER.sendToServer(new XtoneCycleMessage(player, hand, scroll));
    }

    public static void syncConfig(final EntityPlayerMP player) {
        WRAPPER.sendTo(new ConfigSyncMessage(XtonesConfig.hasXtoneCycling()), player);
    }
}
