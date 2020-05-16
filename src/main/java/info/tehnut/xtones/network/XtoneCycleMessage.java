package info.tehnut.xtones.network;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

public final class XtoneCycleMessage implements IMessage {
    private static final int SIZE_BYTES = Byte.BYTES + Byte.BYTES + Short.BYTES;
    private static final EnumHand[] HANDS = EnumHand.values();

    private static final int ABSENT = 0;
    private static final int NEXT = 1;
    private static final int PREV = -1;

    private static final int INVALID_SLOT = -2;
    private static final int OFF_HAND_SLOT = -1;

    private @MonotonicNonNull EnumHand hand = null;
    private int offset = ABSENT;
    private int slot = INVALID_SLOT;

    @Deprecated
    public XtoneCycleMessage() {
    }

    @SideOnly(Side.CLIENT)
    XtoneCycleMessage(final EntityPlayerSP player, final EnumHand hand, final int scroll) {
        this.hand = hand;
        this.offset = scroll >= 0 ? NEXT : PREV;
        this.slot = this.hand == EnumHand.MAIN_HAND
            ? this.checkSlot(player.inventory.currentItem)
            : OFF_HAND_SLOT;
    }

    private int checkSlot(final int slot) {
        final boolean hotbar = this.hand == EnumHand.MAIN_HAND && InventoryPlayer.isHotbar(slot);
        Preconditions.checkArgument(hotbar || slot == OFF_HAND_SLOT, slot);
        return slot;
    }

    EnumHand getHand() {
        Preconditions.checkState(this.hand != null);
        return this.hand;
    }

    int getOffset() {
        Preconditions.checkState(this.offset != ABSENT);
        return this.offset;
    }

    int getExpectedSlot() {
        Preconditions.checkState(this.slot != INVALID_SLOT);
        return this.slot;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        Preconditions.checkArgument(buf.isReadable(SIZE_BYTES), buf);
        this.offset = buf.readBoolean() ? NEXT : PREV;
        this.hand = HANDS[buf.readByte()];
        this.slot = this.checkSlot(buf.readShort());
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        Preconditions.checkArgument(buf.isWritable(SIZE_BYTES), buf);
        buf.writeBoolean(this.offset == NEXT);
        buf.writeByte(this.hand.ordinal());
        buf.writeShort(this.slot);
    }
}
