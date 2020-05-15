package info.tehnut.xtones.support;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import info.tehnut.xtones.Tone;
import info.tehnut.xtones.Xtones;

import java.util.Objects;

public final class ChiselsAndBitsSupport {
    private static final String CHISELS_AND_BITS = "chiselsandbits";
    private static final String IGNORE_BLOCK_LOGIC = "ignoreblocklogic";

    private ChiselsAndBitsSupport() {
    }

    public static void init() {
        for (Tone tone : Tone.values()) {
            if (tone.isGlassLike()) {
                ignoreBlockLogic(Xtones.block(tone));
            }
        }
    }

    private static void ignoreBlockLogic(final Block block) {
        final ResourceLocation name = Objects.requireNonNull(block.getRegistryName());
        FMLInterModComms.sendMessage(CHISELS_AND_BITS, IGNORE_BLOCK_LOGIC, name);
    }
}
