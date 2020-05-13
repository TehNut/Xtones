package tehnut.xtones;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Xtones.ID)
@Mod.EventBusSubscriber(modid = Xtones.ID)
public class ConfigHandler {

    @Config.Comment({
            "How chiseling Xtones blocks should function. [Requires \"Chisel\" by the Chisel Team to be installed]",
            "0 - Chisel the Stone Tile into the first variant of each registered Xtone block. This makes the individual recipes useless.",
            "1 - Chisel between each variant of the Xtones blocks. This makes the cycling useless.",
            "2 - Disable Chisel compatibility altogether.",
            "Example images can be found on the ChiselTones CurseForge page. Functionality is exactly the same."
    })
    @Config.RangeInt(min = 0, max = 2)
    public static int chiselMode = 0; // TODO - Change this to an enum?

    @Config.Comment({
            "Disables all recipes except the one for the Stone Tile.",
            "Mainly for use with Chisel installed and chiselMode being set to 0."
    })
    public static boolean disableXtoneRecipes = false;
    @Config.Comment({
            "Disables the ability to cycle between variants via keybind.",
            "Mainly for use with Chisel installed and chiselMode being set to 1."
    })
    public static boolean disableScrollCycling = false;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (Xtones.ID.equals(event.getModID())) ConfigManager.sync(Xtones.ID, Config.Type.INSTANCE);
    }
}
