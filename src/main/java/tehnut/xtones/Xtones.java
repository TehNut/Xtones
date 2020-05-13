package tehnut.xtones;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tehnut.xtones.compat.CompatChisel;
import tehnut.xtones.compat.CompatChiselsAndBits;
import tehnut.xtones.network.MessageCycleXtone;

@Mod(modid = Xtones.ID, name = Xtones.NAME, version = "@VERSION@")
public class Xtones {

    // TODO - Implement JEI support so that looking up any variant will show the recipe

    public static final String ID = "xtones";
    public static final String NAME = "Xtones";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(ID);

    public static final CreativeTabs TAB = new CreativeTabs(ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(RegistrarXtones.BASE);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImageName("item_search.png");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NETWORK_WRAPPER.registerMessage(MessageCycleXtone.Handler.class, MessageCycleXtone.class, 0, Side.SERVER);

        if (Loader.isModLoaded("chisel"))
            CompatChisel.sendIMC();
        if (Loader.isModLoaded("chiselsandbits"))
            CompatChiselsAndBits.sendIMC();

        LOGGER.info("Xtones has loaded with {} unique blocks. If you are struggling to keep within 4096 IDs, you may want to disable some of these.", RegistrarXtones.BLOCKS.size() + 2); // +2 because lamp and base
    }
}
