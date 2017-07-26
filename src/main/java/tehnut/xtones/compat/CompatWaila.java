package tehnut.xtones.compat;

import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
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
        public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return accessor.getStack();
        }

        @Override @Nonnull
        public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            currenttip.add(I18n.translateToLocalFormatted("tooltip.xtones.type", itemStack.getItemDamage() + 1));
            return currenttip;
        }

        @Override @Nonnull
        public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return currenttip;
        }

        @Override @Nonnull
        public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return currenttip;
        }

        @Override @Nonnull
        public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
            return tag;
        }
    }
}
