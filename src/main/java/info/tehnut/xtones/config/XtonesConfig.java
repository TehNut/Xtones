package info.tehnut.xtones.config;

import info.tehnut.xtones.network.XtonesNetwork;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import info.tehnut.xtones.Xtones;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Config(modid = Xtones.ID)
@EventBusSubscriber(modid = Xtones.ID)
public final class XtonesConfig {
    @Comment({
        "How chiseling Xtones blocks should function. [Requires \"Chisel\" by the Chisel Team to be installed]",
        "0 - Chisel the Stone Tile into the first variant of each registered Xtone block. This makes the individual recipes useless.",
        "1 - Chisel between each variant of the Xtones blocks. This makes the cycling useless.",
        "2 - Disable Chisel compatibility altogether.",
        "Example images can be found on the ChiselTones CurseForge page. Functionality is exactly the same."
    })
    @RangeInt(min = ChiselMode.MIN, max = ChiselMode.MAX)
    public static int chiselMode = ChiselMode.DEFAULT;

    @Comment({
        "Disables all recipes except the one for the Stone Tile.",
        "Mainly for use with Chisel installed and chiselMode being set to 0."
    })
    public static boolean disableXtoneRecipes = false;

    @Comment({
        "Disables the ability to cycle between variants via keybind.",
        "Mainly for use with Chisel installed and chiselMode being set to 1."
    })
    public static boolean disableScrollCycling = false;

    private XtonesConfig() {
    }

    public static boolean hasXtoneRecipes() {
        return !disableXtoneRecipes;
    }

    public static boolean hasXtoneCycling() {
        return !disableScrollCycling;
    }

    @SubscribeEvent
    static void configChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (Xtones.ID.equals(event.getModID())) {
            ConfigManager.sync(Xtones.ID, Config.Type.INSTANCE);
        }
    }

    @SubscribeEvent
    static void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        XtonesNetwork.syncConfig((EntityPlayerMP) event.player);
    }
}
