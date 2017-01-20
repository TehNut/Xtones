package tehnut.xtones.compat;

import team.chisel.api.carving.CarvingUtils;
import tehnut.xtones.ConfigHandler;
import tehnut.xtones.ModObjects;
import tehnut.xtones.Xtones;
import tehnut.xtones.block.BlockXtone;

import java.util.Map;

// ChiselTones makes a return!
public class CompatChisel {

    public static void init() {
        if (ConfigHandler.chiselMode == 1) {
            for (Map.Entry<String, BlockXtone> entry : ModObjects.BLOCKS.entrySet()) {
                CarvingUtils.chisel.addGroup(CarvingUtils.getDefaultGroupFor(entry.getKey()));
                for (int i = 0; i < BlockXtone.XtoneType.values().length; i++)
                    CarvingUtils.chisel.addVariation(entry.getKey(), entry.getValue().getStateFromMeta(i), i);
            }
        } else if (ConfigHandler.chiselMode == 0) {
            CarvingUtils.chisel.addGroup(CarvingUtils.getDefaultGroupFor(Xtones.ID));
            int value = 0;
            CarvingUtils.chisel.addVariation(Xtones.ID, ModObjects.BASE.getStateFromMeta(ModObjects.BaseType.TILE.ordinal()), value++);
            for (BlockXtone blockXtone : ModObjects.BLOCKS.values())
                CarvingUtils.chisel.addVariation(Xtones.ID, blockXtone.getStateFromMeta(0), value++);
        }
    }
}
