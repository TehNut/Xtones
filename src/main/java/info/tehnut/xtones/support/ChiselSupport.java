package info.tehnut.xtones.support;

import info.tehnut.xtones.Tone;
import info.tehnut.xtones.Xtones;
import info.tehnut.xtones.config.XtonesConfig;
import info.tehnut.xtones.config.ChiselMode;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.Objects;

public final class ChiselSupport {
    public static final String CHISEL = "chisel";

    private static final String ADD_VARIATION = "add_variation";

    private static final String GROUP = "group";
    private static final String BLOCK = "block";
    private static final String META = "meta";

    private ChiselSupport() {
    }

    public static void init() {
        if (XtonesConfig.chiselMode != ChiselMode.NO_CARVING) {
            if (XtonesConfig.chiselMode == ChiselMode.BASE_CARVING) {
                add(variation(Xtones.baseBlock(), Xtones.ID));
            }
            Xtones.blocks().forEach(block -> {
                if (XtonesConfig.chiselMode == ChiselMode.VARIANT_CARVING) {
                    for (final Tone tone : Tone.values()) {
                        add(variation(block, tone));
                    }
                } else {
                    add(variation(block, Xtones.ID));
                }
            });
        }
    }

    private static NBTTagCompound variation(final Block block, final String group) {
        final String name = Objects.requireNonNull(block.getRegistryName()).toString();
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setString(GROUP, group);
        tag.setString(BLOCK, name);
        return tag;
    }

    private static NBTTagCompound variation(final Block block, final Tone tone) {
        final NBTTagCompound tag = variation(block, tone.toString());
        tag.setInteger(META, tone.ordinal());
        return tag;
    }

    private static void add(final NBTTagCompound variation) {
        FMLInterModComms.sendMessage(CHISEL, ADD_VARIATION, variation);
    }
}
