package info.tehnut.xtones.network;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import info.tehnut.xtones.client.XtonesClient;

final class XtoneConfigHandler implements IMessageHandler<XtoneConfigMessage, IMessage> {
    static final XtoneConfigHandler INSTANCE = new XtoneConfigHandler();

    private XtoneConfigHandler() {
    }

    private static void readConfig(final XtoneConfigMessage config) {
        XtonesClient.setServerXtoneCycling(config.hasXtoneCycling());
    }

    @Override
    @Nullable
    public IMessage onMessage(final XtoneConfigMessage config, final MessageContext context) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> readConfig(config));
        return null;
    }
}
