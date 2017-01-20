package tehnut.xtones;

import net.minecraftforge.common.config.Config;

@Config(modid = Xtones.ID)
public class ConfigHandler {

    @Config.Comment({
            "How chiseling XTones blocks should function.",
            "0 - Chisel the Stone Tile into the first variant of each registered XTone block. This makes the individual recipes useless.",
            "1 - Chisel between each variant of the XTones blocks. This makes the cycling useless.",
            "Example images can be found on the ChiselTones CurseForge page. Functionality is exactly the same."
    })
    public static int chiselMode = 0;

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
}
