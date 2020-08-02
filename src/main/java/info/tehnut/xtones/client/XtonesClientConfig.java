package info.tehnut.xtones.client;

import info.tehnut.xtones.Xtones;
import info.tehnut.xtones.config.TooltipVisibility;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;

@Config(modid = Xtones.ID, name = Xtones.ID + "-client")
@EventBusSubscriber(value = Side.CLIENT, modid = Xtones.ID)
public final class XtonesClientConfig {
    @Config.Comment("Enables the search bar on the Xtones creative tab")
    public static boolean searchableCreativeTab = true;

    @Config.Comment({
        "Visibility of the cycling tooltip on Xtone items",
        "   visible - The tooltip will always be visible",
        "   shift - The tooltip will only be visible when shift is held",
        "   hidden - The tooltip will not be visible"
    })
    public static TooltipVisibility cyclingTooltip = TooltipVisibility.visible;

    private XtonesClientConfig() {
    }
}
