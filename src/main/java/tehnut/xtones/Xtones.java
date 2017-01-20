package tehnut.xtones;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tehnut.xtones.compat.CompatChisel;
import tehnut.xtones.network.MessageCycleXtone;

@Mod(modid = Xtones.ID, name = Xtones.NAME, version = "@VERSION@")
public class Xtones {

    // TODO - Implement JEI support so that looking up any variant will show the recipe
    // TODO - Implement Chisel support to allow chiseling

    public static final String ID = "xtones";
    public static final String NAME = "Xtones";
    public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(ID);

    public static final CreativeTabs TAB = new CreativeTabs(ID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModObjects.BASE);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NETWORK_WRAPPER.registerMessage(MessageCycleXtone.Handler.class, MessageCycleXtone.class, 0, Side.SERVER);

        ModObjects.initBlocks();
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(new ClientHandler());
            ModObjects.initRenders();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModObjects.initRecipes();

        if (Loader.isModLoaded("chisel"))
            CompatChisel.init();
    }
}
