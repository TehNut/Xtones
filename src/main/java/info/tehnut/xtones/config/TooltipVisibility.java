package info.tehnut.xtones.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum TooltipVisibility {
    /**
     * The tooltip will always be visible
     */
    visible,

    /**
     * The tooltip will only be visible when shift is held
     */
    shift,

    /**
     * The tooltip will not be visible
     */
    hidden;

    @SideOnly(Side.CLIENT)
    public final boolean isVisible() {
        return this == visible || this == shift && GuiScreen.isShiftKeyDown();
    }
}
