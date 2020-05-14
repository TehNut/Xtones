package tehnut.xtones.compat;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.xtones.ConfigHandler;
import tehnut.xtones.RegistrarXtones;
import tehnut.xtones.Xtones;
import tehnut.xtones.block.BlockXtone;


// ChiselTones makes a return!
public class CompatChisel {

    public static void sendIMC() {
        if (ConfigHandler.chiselMode == 2)
            return;
        for (BlockXtone block : RegistrarXtones.BLOCKS) {
            if (ConfigHandler.chiselMode == 0) {
                add(variation(block, Xtones.ID));
            } else {
                for (int meta = 0; meta < Xtones.TONES.size(); meta++) {
                    add(variation(block, meta));
                }
            }
        }
    }

    private static NBTTagCompound variation(Block block, String group) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("group", group);
        tag.setString("block", block.getRegistryName().toString());
        return tag;
    }

    private static NBTTagCompound variation(Block block, int meta) {
        NBTTagCompound tag = variation(block, Xtones.TONES.get(meta));
        tag.setInteger("meta", meta);
        return tag;
    }

    private static void add(NBTTagCompound variation) {
        FMLInterModComms.sendMessage("chisel", "add_variation", variation);
    }
}
