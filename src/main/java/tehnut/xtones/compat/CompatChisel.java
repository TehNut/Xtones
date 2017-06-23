package tehnut.xtones.compat;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.xtones.ConfigHandler;
import tehnut.xtones.RegistrarXtones;
import tehnut.xtones.Xtones;
import tehnut.xtones.block.BlockXtone;

import java.util.Map;

// ChiselTones makes a return!
public class CompatChisel {

    public static void sendIMC() {
        switch (ConfigHandler.chiselMode) {
            case 0: {
                for (Map.Entry<String, BlockXtone> entry : RegistrarXtones.BLOCKS.entrySet())
                    for (int i = 0; i < BlockXtone.XtoneType.values().length; i++)
                        FMLInterModComms.sendMessage("chisel", "variation:add", entry.getKey() + "|" + entry.getValue().getRegistryName() + "|" + i);
                break;
            }
            case 1: {
                for (Map.Entry<String, BlockXtone> entry : RegistrarXtones.BLOCKS.entrySet())
                    FMLInterModComms.sendMessage("chisel", "variation:add", Xtones.ID + "|" + entry.getValue().getRegistryName() + "|0");
                break;
            }
        }
    }
}
