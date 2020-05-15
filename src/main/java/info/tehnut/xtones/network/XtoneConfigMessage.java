package info.tehnut.xtones.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public final class XtoneConfigMessage implements IMessage {
    private boolean xtoneCycling = false;

    @Deprecated
    public XtoneConfigMessage() {
    }

    XtoneConfigMessage(final boolean xtoneCycling) {
        this.xtoneCycling = xtoneCycling;
    }

    public boolean hasXtoneCycling() {
        return this.xtoneCycling;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        this.xtoneCycling = buf.readBoolean();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeBoolean(this.xtoneCycling);
    }
}
