package tehnut.xtones;

import com.google.common.collect.ImmutableList;
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

import javax.annotation.Nonnull;

@Mod(modid = Xtones.ID, name = Xtones.NAME, version = "@VERSION@")
public class Xtones {

    // TODO - Implement JEI support so that looking up any variant will show the recipe

    public static final String ID = "xtones";
    public static final String NAME = "Xtones";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(ID);

    public static final ImmutableList<String> TONES = ImmutableList.of(
        "agon", "azur", "bitt", "cray", "fort", "glaxx", "iszm",
        "jelt", "korp", "kryp", "lair", "lave", "mint", "myst",
        "reds", "reed", "roen", "sols", "sync", "tank", "vect",
        "vena", "zane", "zech", "zest", "zeta", "zion", "zkul",
        "zoea", "zome", "zone", "zorg", "ztyl", "zyth"
    );

    public static final CreativeTabs TAB = new CreativeTabs(ID) {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(RegistrarXtones.baseItem);
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
    }
}
