package tehnut.xtones;

import com.google.common.collect.Maps;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tehnut.xtones.block.BlockEnum;
import tehnut.xtones.block.BlockXtone;
import tehnut.xtones.block.ItemBlockXtone;

import java.util.Locale;
import java.util.Map;

public class ModObjects {

    public static final Map<String, BlockXtone> BLOCKS = Maps.newHashMap();
    public static final BlockEnum<BaseType> BASE = (BlockEnum<BaseType>) new BlockEnum<BaseType>(Material.ROCK, BaseType.class).setUnlocalizedName(Xtones.ID + ".base").setCreativeTab(Xtones.TAB).setResistance(3.0F).setHardness(3.0F);

    public static void initBlocks() {
        GameRegistry.register(BASE.setRegistryName("base"));
        GameRegistry.register(new ItemBlock(BASE).setRegistryName(BASE.getRegistryName()));
        // Literally all of these recipe items are cancer and need to be redone by somebody who cares
        // Except for Glaxx
        // *goes back to using Chisel*
        register(new BlockXtone("agon", new ItemStack(Items.APPLE)));
        register(new BlockXtone("azur", new ItemStack(Items.DYE, 1, 4)));
        register(new BlockXtone("bitt", new ItemStack(Blocks.WOOL)));
        register(new BlockXtone("cray", new ItemStack(Blocks.HARDENED_CLAY)));
        register(new BlockXtone("fort", new ItemStack(Items.WHEAT)));
        register(new BlockXtone("glaxx", new ItemStack(Blocks.GLASS)).setSeeThrough());
        register(new BlockXtone("iszm", new ItemStack(Items.IRON_INGOT)));
        register(new BlockXtone("jelt", new ItemStack(Items.GOLD_INGOT)));
        register(new BlockXtone("korp", new ItemStack(Items.BEEF)));
        register(new BlockXtone("kryp", new ItemStack(Items.CLAY_BALL)));
        register(new BlockXtone("lair", new ItemStack(Items.STRING)));
        register(new BlockXtone("lave", new ItemStack(Blocks.NETHERRACK)));
        register(new BlockXtone("mint", new ItemStack(Items.SNOWBALL)));
        register(new BlockXtone("myst", new ItemStack(Blocks.SNOW)));
        register(new BlockXtone("reds", new ItemStack(Blocks.RED_NETHER_BRICK)));
        register(new BlockXtone("reed", new ItemStack(Items.REEDS)));
        register(new BlockXtone("roen", new ItemStack(Items.BONE)));
        register(new BlockXtone("sols", new ItemStack(Blocks.NETHER_WART)));
        register(new BlockXtone("sync", new ItemStack(Items.SUGAR)));
        register(new BlockXtone("tank", new ItemStack(Blocks.IRON_BLOCK)));
        register(new BlockXtone("vect", new ItemStack(Items.ARROW)));
        register(new BlockXtone("vena", new ItemStack(Items.REDSTONE)));
        register(new BlockXtone("zane", new ItemStack(Blocks.CLAY)));
        register(new BlockXtone("zech", new ItemStack(Items.CARROT)));
        register(new BlockXtone("zest", new ItemStack(Blocks.TORCH)));
        register(new BlockXtone("zeta", new ItemStack(Items.EGG)));
        register(new BlockXtone("zion", new ItemStack(Blocks.VINE)));
        register(new BlockXtone("zkul", new ItemStack(Items.SKULL)));
        register(new BlockXtone("zoea", new ItemStack(Blocks.REDSTONE_BLOCK)));
        register(new BlockXtone("zome", new ItemStack(Blocks.QUARTZ_BLOCK)));
        register(new BlockXtone("zone", new ItemStack(Items.QUARTZ)));
        register(new BlockXtone("zorg", new ItemStack(Items.DYE, 1, 3)));
        register(new BlockXtone("ztyl", new ItemStack(Items.DYE, 1, 6)));
        register(new BlockXtone("zyth", new ItemStack(Items.DYE, 1, 9)));
    }

    public static void initRecipes() {
        if (!ConfigHandler.disableXtoneRecipes)
            for (BlockXtone block : BLOCKS.values())
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 8), "BBB", "BCB", "BBB", 'B', BASE, 'C', block.getCraftStack()));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BASE, 8), "SS ", "SBS", " SS", 'S', new ItemStack(Blocks.STONE_SLAB), 'B', "stone"));
    }

    @SideOnly(Side.CLIENT)
    public static void initRenders() {
        for (BlockXtone xtone : BLOCKS.values()) {
            Item itemBlock = Item.getItemFromBlock(xtone);
            for (BlockXtone.XtoneType type : xtone.getTypes())
                ModelLoader.setCustomModelResourceLocation(itemBlock, type.ordinal(), new ModelResourceLocation(xtone.getRegistryName(), "variant=" + type.getName()));
        }

        for (BaseType type : BaseType.values())
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BASE), type.ordinal(), new ModelResourceLocation(BASE.getRegistryName(), "type=" + type.getName()));
    }

    private static void register(BlockXtone block) {
        GameRegistry.register(block.setRegistryName(block.getName()));
        GameRegistry.register(new ItemBlockXtone(block).setRegistryName(block.getName()));
        BLOCKS.put(block.getName(), block);
    }
    
    public enum BaseType implements IStringSerializable {
        TILE,
        ;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
