package info.tehnut.xtones.network;

import info.tehnut.xtones.client.XtonesClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.checkerframework.checker.nullness.qual.Nullable;

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
        Minecraft.getMinecraft().addScheduledTask(() -> readConfig(config));
        return null;
    }
}
