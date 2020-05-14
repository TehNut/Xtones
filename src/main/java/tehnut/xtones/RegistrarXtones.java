package tehnut.xtones;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import tehnut.xtones.block.BlockBase;
import tehnut.xtones.block.BlockLampFlat;
import tehnut.xtones.block.BlockXtone;
import tehnut.xtones.block.item.ItemBlockBase;
import tehnut.xtones.block.item.ItemBlockLamp;
import tehnut.xtones.block.item.ItemBlockXtone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = Xtones.ID)
public class RegistrarXtones {

    private static final List<Block> BLOCKS = new ArrayList<>(Xtones.TONES.size());
    private static final List<Item> ITEMS = new ArrayList<>(Xtones.TONES.size());

    static Block baseBlock, lampBlock;
    static Item baseItem, lampItem;

    public static List<Block> getBlocks() {
        return Collections.unmodifiableList(BLOCKS);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(baseBlock = new BlockBase()
            .setRegistryName(Xtones.ID, "base")
            .setTranslationKey(Xtones.ID + ".base")
            .setCreativeTab(Xtones.TAB)
            .setResistance(3.0F)
            .setHardness(3.0F)
        );

        registry.register(lampBlock = new BlockLampFlat()
            .setRegistryName(Xtones.ID, "lamp_flat")
            .setTranslationKey(Xtones.ID + ".lamp.flat")
            .setCreativeTab(Xtones.TAB)
            .setHardness(0.5F)
        );

        for (String tone : Xtones.TONES) {
            BlockXtone block = new BlockXtone(tone);
            block.setRegistryName(Xtones.ID, tone);
            block.setTranslationKey(Xtones.ID + '.' + tone);
            block.setCreativeTab(Xtones.TAB);
            block.setResistance(3.0F);
            block.setHardness(3.0F);
            registry.register(block);
            BLOCKS.add(block);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(baseItem = new ItemBlockBase(baseBlock).setRegistryName(baseBlock.getRegistryName()));

        registry.register(lampItem = new ItemBlockLamp(lampBlock)
            .setRegistryName(lampBlock.getRegistryName())
        );

        for (Block block : BLOCKS) {
            Item item = new ItemBlockXtone(block);
            item.setRegistryName(block.getRegistryName());
            registry.register(item);
            ITEMS.add(item);
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : ITEMS) {
            for (BlockXtone.Variant variant : BlockXtone.Variant.values()) {
                setCustomModelResourceLocation(item, variant.ordinal(), "variant=" + variant.getName());
            }
        }

        for (BlockBase.Type type : BlockBase.Type.values()) {
            setCustomModelResourceLocation(baseItem, type.ordinal(), "type=" + type.getName());
        }

        setCustomModelResourceLocation(lampItem, 0, "active=false,facing=up");
    }

    private static void setCustomModelResourceLocation(Item item, int meta, String variant) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), variant));
    }
}
