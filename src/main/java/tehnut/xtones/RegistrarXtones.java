package tehnut.xtones;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.xtones.block.BlockEnum;
import tehnut.xtones.block.BlockLamp;
import tehnut.xtones.block.BlockXtone;
import tehnut.xtones.block.item.ItemBlockLamp;
import tehnut.xtones.block.item.ItemBlockXtone;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = Xtones.ID)
@GameRegistry.ObjectHolder(Xtones.ID)
public class RegistrarXtones {

    public static final Block BASE = Blocks.AIR;
    public static final Block LAMP_FLAT = Blocks.AIR;

    private static final List<BlockXtone> BLOCKS = new ArrayList<>(Xtones.TONES.size());

    public static List<BlockXtone> getBlocks() {
        return BLOCKS;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockEnum<>(Material.ROCK, BaseType.class)
                .setTranslationKey(Xtones.ID + ".base")
                .setCreativeTab(Xtones.TAB)
                .setResistance(3.0F)
                .setHardness(3.0F)
                .setRegistryName("base")
        );

        event.getRegistry().register(new BlockLamp(BlockLamp.LampShape.FLAT).setRegistryName("lamp_flat"));

        for (String tone : Xtones.TONES) {
            BlockXtone block = new BlockXtone(tone);
            event.getRegistry().register(block);
            BLOCKS.add(block);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(BASE) {
            @Override
            @Nonnull
            public String getTranslationKey(ItemStack stack) {
                return super.getTranslationKey(stack) + "." + BaseType.getName(stack);
            }

            @Override
            public int getMetadata(int damage) {
                return damage;
            }
        }.setRegistryName(BASE.getRegistryName()));

        event.getRegistry().register(new ItemBlockLamp(LAMP_FLAT).setRegistryName(LAMP_FLAT.getRegistryName()));

        for (BlockXtone xtone : BLOCKS)
            event.getRegistry().register(new ItemBlockXtone(xtone).setRegistryName(xtone.getName()));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (BlockXtone xtone : BLOCKS) {
            Item itemBlock = Item.getItemFromBlock(xtone);
            for (BlockXtone.XtoneType type : xtone.getTypes())
                ModelLoader.setCustomModelResourceLocation(itemBlock, type.ordinal(), new ModelResourceLocation(xtone.getRegistryName(), "variant=" + type.getName()));
        }

        for (BaseType type : BaseType.values())
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BASE), type.ordinal(), new ModelResourceLocation(BASE.getRegistryName(), "type=" + type.getName()));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LAMP_FLAT), 0, new ModelResourceLocation(LAMP_FLAT.getRegistryName(), "active=false,facing=up"));
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
