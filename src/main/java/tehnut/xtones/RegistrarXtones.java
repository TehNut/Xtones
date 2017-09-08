package tehnut.xtones;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
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
import java.util.List;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = Xtones.ID)
@GameRegistry.ObjectHolder(Xtones.ID)
public class RegistrarXtones {

    public static final Block BASE = Blocks.AIR;
    public static final Block LAMP_FLAT = Blocks.AIR;

    public static List<BlockXtone> BLOCKS;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockEnum<>(Material.ROCK, BaseType.class)
                .setUnlocalizedName(Xtones.ID + ".base")
                .setCreativeTab(Xtones.TAB)
                .setResistance(3.0F)
                .setHardness(3.0F)
                .setRegistryName("base")
        );

        event.getRegistry().register(new BlockLamp(BlockLamp.LampShape.FLAT).setRegistryName("lamp_flat"));

        BLOCKS = Lists.newArrayList(
                new BlockXtone("agon"),
                new BlockXtone("azur"),
                new BlockXtone("bitt"),
                new BlockXtone("cray"),
                new BlockXtone("fort"),
                new BlockXtone("glaxx").setSeeThrough(),
                new BlockXtone("iszm"),
                new BlockXtone("jelt"),
                new BlockXtone("korp"),
                new BlockXtone("kryp"),
                new BlockXtone("lair"),
                new BlockXtone("lave"),
                new BlockXtone("mint"),
                new BlockXtone("myst"),
                new BlockXtone("reds"),
                new BlockXtone("reed"),
                new BlockXtone("roen"),
                new BlockXtone("sols"),
                new BlockXtone("sync"),
                new BlockXtone("tank"),
                new BlockXtone("vect"),
                new BlockXtone("vena"),
                new BlockXtone("zane"),
                new BlockXtone("zech"),
                new BlockXtone("zest"),
                new BlockXtone("zeta"),
                new BlockXtone("zion"),
                new BlockXtone("zkul"),
                new BlockXtone("zoea"),
                new BlockXtone("zome"),
                new BlockXtone("zone"),
                new BlockXtone("zorg"),
                new BlockXtone("ztyl"),
                new BlockXtone("zyth")
        );

        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(BASE) {
            public @Override
            @Nonnull
            String getUnlocalizedName(ItemStack stack) {
                return super.getUnlocalizedName(stack) + "." + BaseType.values()[MathHelper.clamp(stack.getItemDamage(), 0, BaseType.values().length)].getName();
            }

            public @Override
            int getMetadata(int damage) {
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

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
