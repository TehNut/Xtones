package tehnut.xtones.compat;

import mcp.mobius.waila.api.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import tehnut.xtones.block.BlockXtone;

import javax.annotation.Nonnull;
import java.util.List;

@WailaPlugin
public class CompatWaila implements IWailaPlugin {

    @Override
    public void register(IWailaRegistrar registrar) {
        registrar.registerHeadProvider(new IWailaDataProvider() {
            @Nonnull
            @Override
            public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
                currenttip.add(I18n.format("tooltip.xtones.type", itemStack.getItemDamage() + 1));
                return currenttip;
            }
        }, BlockXtone.class);
    }
}
