package info.tehnut.xtones.network;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

public final class XtoneCycleMessage implements IMessage {
    private static final int ABSENT = 0;
    private static final int NEXT = 1;
    private static final int PREV = -1;

    private @MonotonicNonNull EnumHand hand = null;
    private int offset = ABSENT;

    @Deprecated
    public XtoneCycleMessage() {
    }

    XtoneCycleMessage(final EnumHand hand, final int scroll) {
        this.hand = hand;
        this.offset = scroll >= 0 ? NEXT : PREV;
    }

    public EnumHand getHand() {
        Preconditions.checkState(this.hand != null);
        return this.hand;
    }

    public int getOffset() {
        Preconditions.checkState(this.offset != ABSENT);
        return this.offset;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        this.offset = buf.readBoolean() ? NEXT : PREV;
        this.hand = EnumHand.values()[buf.readByte()];
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeBoolean(this.offset == NEXT);
        buf.writeByte(this.hand.ordinal());
    }
}
