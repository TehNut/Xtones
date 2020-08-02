package info.tehnut.xtones.support;

import info.tehnut.xtones.Tone;
import info.tehnut.xtones.Xtones;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.Objects;

public final class ChiselsAndBitsSupport {
    public static final String CHISELS_AND_BITS = "chiselsandbits";

    private static final String IGNORE_BLOCK_LOGIC = "ignoreblocklogic";

    private ChiselsAndBitsSupport() {
    }

    public static void init() {
        for (final Tone tone : Tone.values()) {
            if (tone.isGlassLike()) {
                ignoreBlockLogic(Xtones.block(tone));
            }
        }
    }

    private static void ignoreBlockLogic(final Block block) {
        FMLInterModComms.sendMessage(CHISELS_AND_BITS, IGNORE_BLOCK_LOGIC,
            Objects.requireNonNull(block.getRegistryName())
        );
    }
}
