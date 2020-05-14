package tehnut.xtones;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import tehnut.xtones.block.BlockEnum;
import tehnut.xtones.block.BlockLamp;
import tehnut.xtones.block.BlockXtone;
import tehnut.xtones.block.item.ItemBlockLamp;
import tehnut.xtones.block.item.ItemBlockXtone;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = Xtones.ID)
public class RegistrarXtones {

    private static final List<BlockXtone> BLOCKS = new ArrayList<>(Xtones.TONES.size());
    private static final List<ItemBlockXtone> ITEMS = new ArrayList<>(Xtones.TONES.size());

    static Block baseBlock, lampBlock;
    static Item baseItem, lampItem;

    public static List<BlockXtone> getBlocks() {
        return Collections.unmodifiableList(BLOCKS);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(baseBlock = new BlockEnum<>(Material.ROCK, BaseType.class)
            .setRegistryName(Xtones.ID, "base")
            .setTranslationKey(Xtones.ID + ".base")
            .setCreativeTab(Xtones.TAB)
            .setResistance(3.0F)
            .setHardness(3.0F)
        );

        for (BlockLamp.LampShape shape : BlockLamp.LampShape.values()) {
            registry.register(lampBlock = new BlockLamp(shape)
                .setRegistryName(Xtones.ID, "lamp_" + shape.getName())
                .setTranslationKey(Xtones.ID + ".lamp." + shape.getName())
                .setCreativeTab(Xtones.TAB)
                .setHardness(0.5F)
            );
        }

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

        registry.register(baseItem = new ItemBlock(baseBlock) {
            @Override
            @Nonnull
            public String getTranslationKey(ItemStack stack) {
                return super.getTranslationKey(stack) + '.' + BaseType.getName(stack);
            }

            @Override
            public int getMetadata(int damage) {
                return damage;
            }
        }.setRegistryName(baseBlock.getRegistryName()));

        registry.register(lampItem = new ItemBlockLamp(lampBlock)
            .setRegistryName(lampBlock.getRegistryName())
        );

        for (BlockXtone block : BLOCKS) {
            ItemBlockXtone item = new ItemBlockXtone(block);
            item.setRegistryName(block.getRegistryName());
            registry.register(item);
            ITEMS.add(item);
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (int tone = 0; tone < Xtones.TONES.size(); tone++) {
            ItemBlockXtone item = ITEMS.get(tone);
            for (BlockXtone.XtoneType type : BLOCKS.get(tone).getTypes()) {
                setCustomModelResourceLocation(item, type.ordinal(), "variant=" + type.getName());
            }
        }

        for (BaseType type : BaseType.values()) {
            setCustomModelResourceLocation(baseItem, type.ordinal(), "type=" + type.getName());
        }

        setCustomModelResourceLocation(lampItem, 0, "active=false,facing=up");
    }

    private static void setCustomModelResourceLocation(Item item, int meta, String variant) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    public enum BaseType implements IStringSerializable {
        TILE,
        ;

        private static final BaseType[] TYPES = values();

        public static String getName(ItemStack stack) {
            return TYPES[stack.getItemDamage() & TYPES.length - 1].getName();
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
