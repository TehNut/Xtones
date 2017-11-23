package tehnut.xtones.compat;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tehnut.xtones.ConfigHandler;
import tehnut.xtones.RegistrarXtones;
import tehnut.xtones.Xtones;
import tehnut.xtones.block.BlockXtone;

// ChiselTones makes a return!
public class CompatChisel {

    public static void sendIMC() {
        switch (ConfigHandler.chiselMode) {
            case 0: {
                for (BlockXtone xtone : RegistrarXtones.BLOCKS) {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setString("group", Xtones.ID);
                    tag.setString("block", xtone.getRegistryName().toString());
                    FMLInterModComms.sendMessage("chisel", "add_variation", tag);
                }
                break;
            }
            case 1: {
                for (BlockXtone xtone : RegistrarXtones.BLOCKS)
                    for (int i = 0; i < BlockXtone.XtoneType.values().length; i++) {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setString("group", xtone.getName());
                        tag.setString("block", xtone.getRegistryName().toString());
                        tag.setInteger("meta", i);
                        FMLInterModComms.sendMessage("chisel", "add_variation", tag);
                    }
                break;
            }
            case 2: {
                break;
            }
        }
    }
}
