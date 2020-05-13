package tehnut.xtones.compat;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CompatChiselsAndBits {

    public static void sendIMC() {
        FMLInterModComms.sendMessage("chiselsandbits", "ignoreblocklogic", "xtones:glaxx");
    }
}
