package info.tehnut.xtones.network;

import info.tehnut.xtones.client.XtonesClient;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.checkerframework.checker.nullness.qual.Nullable;

final class ConfigSyncHandler implements IMessageHandler<ConfigSyncMessage, IMessage> {
    static final ConfigSyncHandler INSTANCE = new ConfigSyncHandler();

    private ConfigSyncHandler() {
    }

    @SideOnly(Side.CLIENT)
    private static void readConfig(final ConfigSyncMessage config) {
        XtonesClient.setServerXtoneCycling(config.hasXtoneCycling());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public @Nullable IMessage onMessage(final ConfigSyncMessage config, final MessageContext context) {
        FMLClientHandler.instance().getClient().addScheduledTask(() -> readConfig(config));
        return null;
    }
}
