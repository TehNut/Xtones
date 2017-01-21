package tehnut.xtones.compat;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import tehnut.xtones.block.BlockXtone;

import java.util.List;

public class CompatWaila {

    public static void callback(IWailaRegistrar registrar) {
        registrar.registerHeadProvider(new DataProviderXtones(), BlockXtone.class);
    }

    public static class DataProviderXtones implements IWailaDataProvider {
        @Override
        public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return null;
        }

        @Override
        public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            currenttip.add(I18n.translateToLocalFormatted("tooltip.xtones.type", itemStack.getItemDamage() + 1));
            return currenttip;
        }

        @Override
        public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return null;
        }

        @Override
        public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            return null;
        }

        @Override
        public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
            return null;
        }
    }
}
