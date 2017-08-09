package tehnut.xtones.compat;

import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import tehnut.xtones.block.BlockXtone;

import javax.annotation.Nonnull;
import java.util.List;

@WailaPlugin
public class CompatWaila implements IWailaPlugin {

    @Override
    public void register(IWailaRegistrar registrar) {
        registrar.registerHeadProvider(new DataProviderXtones(), BlockXtone.class);
    }

    public static class DataProviderXtones implements IWailaDataProvider {
        @Override @Nonnull
        public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            currenttip.add(I18n.translateToLocalFormatted("tooltip.xtones.type", itemStack.getItemDamage() + 1));
            return currenttip;
        }
    }
}
