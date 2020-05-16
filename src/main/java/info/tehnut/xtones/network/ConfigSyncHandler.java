package info.tehnut.xtones.network;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import info.tehnut.xtones.client.XtonesClient;

final class ConfigSyncHandler implements IMessageHandler<ConfigSyncMessage, IMessage> {
    static final ConfigSyncHandler INSTANCE = new ConfigSyncHandler();

    private ConfigSyncHandler() {
    }

    private static void readConfig(final ConfigSyncMessage config) {
        XtonesClient.setServerXtoneCycling(config.hasXtoneCycling());
    }

    @Override
    @Nullable
    public IMessage onMessage(final ConfigSyncMessage config, final MessageContext context) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> readConfig(config));
        return null;
    }
}
