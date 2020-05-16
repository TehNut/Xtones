package info.tehnut.xtones;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import info.tehnut.xtones.block.FlatLampBlock;
import info.tehnut.xtones.block.XtoneBlock;
import info.tehnut.xtones.item.XtoneBlockItem;
import info.tehnut.xtones.network.XtonesNetwork;
import info.tehnut.xtones.support.ChiselSupport;
import info.tehnut.xtones.support.ChiselsAndBitsSupport;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

@Mod(modid = Xtones.ID, useMetadata = true)
@EventBusSubscriber(modid = Xtones.ID)
public final class Xtones {
    public static final String ID = "xtones";
    public static final String NAME = "Xtones";

    private static final CreativeTabs CREATIVE_TAB = new CreativeTabs(ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(baseItem());
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImageName("item_search.png");

    private static final Map<Tone, Block> BLOCKS = new EnumMap<>(Tone.class);
    private static final Map<Tone, Item> ITEMS = new EnumMap<>(Tone.class);

    private static @MonotonicNonNull Block baseBlock;
    private static @MonotonicNonNull Block lampBlock;
    private static @MonotonicNonNull Item baseItem;
    private static @MonotonicNonNull Item lampItem;

    public static Stream<Block> blocks() {
        Preconditions.checkState(!BLOCKS.isEmpty());
        return BLOCKS.values().stream();
    }

    public static Stream<Item> items() {
        Preconditions.checkState(!ITEMS.isEmpty());
        return ITEMS.values().stream();
    }

    public static Block block(final Tone tone) {
        Preconditions.checkState(!BLOCKS.isEmpty());
        return BLOCKS.get(tone);
    }

    public static Item item(final Tone tone) {
        Preconditions.checkState(!ITEMS.isEmpty());
        return ITEMS.get(tone);
    }

    public static Block baseBlock() {
        Preconditions.checkState(baseBlock != null);
        return baseBlock;
    }

    public static Block lampBlock() {
        Preconditions.checkState(lampBlock != null);
        return lampBlock;
    }

    public static Item baseItem() {
        Preconditions.checkState(baseItem != null);
        return baseItem;
    }

    public static Item lampItem() {
        Preconditions.checkState(lampItem != null);
        return lampItem;
    }

    @EventHandler
    static void init(final FMLInitializationEvent event) {
        XtonesNetwork.init();
        if (Loader.isModLoaded("chisel")) {
            ChiselSupport.init();
        }
        if (Loader.isModLoaded("chiselsandbits")) {
            ChiselsAndBitsSupport.init();
        }
    }

    @SubscribeEvent
    static void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(baseBlock = new Block(Material.ROCK)
            .setRegistryName(ID, "base")
            .setTranslationKey(ID + ".base")
            .setCreativeTab(CREATIVE_TAB)
            .setResistance(3.0F)
            .setHardness(3.0F));

        registry.register(lampBlock = new FlatLampBlock()
            .setRegistryName(ID, "lamp_flat")
            .setTranslationKey(ID + ".lamp_flat")
            .setCreativeTab(CREATIVE_TAB)
            .setHardness(0.5F));

        for (final Tone tone : Tone.values()) {
            final Block block = new XtoneBlock(tone)
                .setRegistryName(ID, tone.toString())
                .setTranslationKey(ID + '.' + tone)
                .setCreativeTab(CREATIVE_TAB)
                .setResistance(3.0F)
                .setHardness(3.0F);
            registry.register(block);
            BLOCKS.put(tone, block);
        }
    }

    @SubscribeEvent
    static void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(baseItem = new ItemBlock(baseBlock()).setRegistryName(ID, "base"));
        registry.register(lampItem = new ItemBlock(lampBlock()).setRegistryName(ID, "lamp_flat"));

        for (final Tone tone : Tone.values()) {
            final Item item = new XtoneBlockItem(block(tone)).setRegistryName(ID, tone.toString());
            registry.register(item);
            ITEMS.put(tone, item);
        }
    }
}
