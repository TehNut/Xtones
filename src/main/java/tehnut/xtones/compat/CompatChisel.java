package tehnut.xtones.compat;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.xtones.ConfigHandler;
import tehnut.xtones.RegistrarXtones;
import tehnut.xtones.Xtones;
import tehnut.xtones.block.BlockXtone;

// ChiselTones makes a return!
public class CompatChisel {

    public static void sendIMC() {
        switch (ConfigHandler.chiselMode) {
            case 0: {
                for (BlockXtone xtone : RegistrarXtones.BLOCKS)
                    FMLInterModComms.sendMessage("chisel", "variation:add", Xtones.ID + "|" + xtone.getRegistryName() + "|0");
                break;
            }
            case 1: {
                for (BlockXtone xtone : RegistrarXtones.BLOCKS)
                    for (int i = 0; i < BlockXtone.XtoneType.values().length; i++)
                        FMLInterModComms.sendMessage("chisel", "variation:add", xtone.getName() + "|" + xtone.getRegistryName() + "|" + i);
                break;
            }
        }
    }
}
