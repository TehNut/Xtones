package tehnut.xtones;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tehnut.xtones.block.BlockEnum;
import tehnut.xtones.block.BlockLamp;
import tehnut.xtones.block.BlockXtone;
import tehnut.xtones.block.item.ItemBlockLamp;
import tehnut.xtones.block.item.ItemBlockXtone;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Xtones.ID)
@GameRegistry.ObjectHolder(Xtones.ID)
public class RegistrarXtones {

    public static Map<String, BlockXtone> BLOCKS = Maps.newHashMap();
    public static final BlockEnum<BaseType> BASE = BlockEnum.dummy(BaseType.class);
    public static final BlockLamp LAMP_FLAT = new BlockLamp(BlockLamp.LampShape.FLAT);
    
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

        event.getRegistry().registerAll(
                newXtone("agon", new ItemStack(Items.APPLE)),
                newXtone("azur", new ItemStack(Items.DYE, 1, 4)),
                newXtone("bitt", new ItemStack(Blocks.WOOL)),
                newXtone("cray", new ItemStack(Blocks.HARDENED_CLAY)),
                newXtone("fort", new ItemStack(Items.WHEAT)),
                newXtone("glaxx", new ItemStack(Blocks.GLASS)).setSeeThrough(),
                newXtone("iszm", new ItemStack(Items.IRON_INGOT)),
                newXtone("jelt", new ItemStack(Items.GOLD_INGOT)),
                newXtone("korp", new ItemStack(Items.BEEF)),
                newXtone("kryp", new ItemStack(Items.CLAY_BALL)),
                newXtone("lair", new ItemStack(Items.STRING)),
                newXtone("lave", new ItemStack(Blocks.NETHERRACK)),
                newXtone("mint", new ItemStack(Items.SNOWBALL)),
                newXtone("myst", new ItemStack(Blocks.SNOW)),
                newXtone("reds", new ItemStack(Blocks.RED_NETHER_BRICK)),
                newXtone("reed", new ItemStack(Items.REEDS)),
                newXtone("roen", new ItemStack(Items.BONE)),
                newXtone("sols", new ItemStack(Items.NETHER_WART)),
                newXtone("sync", new ItemStack(Items.SUGAR)),
                newXtone("tank", new ItemStack(Blocks.IRON_BLOCK)),
                newXtone("vect", new ItemStack(Items.ARROW)),
                newXtone("vena", new ItemStack(Items.REDSTONE)),
                newXtone("zane", new ItemStack(Blocks.CLAY)),
                newXtone("zech", new ItemStack(Items.CARROT)),
                newXtone("zest", new ItemStack(Blocks.TORCH)),
                newXtone("zeta", new ItemStack(Items.EGG)),
                newXtone("zion", new ItemStack(Blocks.VINE)),
                newXtone("zkul", new ItemStack(Items.SKULL)),
                newXtone("zoea", new ItemStack(Blocks.REDSTONE_BLOCK)),
                newXtone("zome", new ItemStack(Blocks.QUARTZ_BLOCK)),
                newXtone("zone", new ItemStack(Items.QUARTZ)),
                newXtone("zorg", new ItemStack(Items.DYE, 1, 3)),
                newXtone("ztyl", new ItemStack(Items.DYE, 1, 6)),
                newXtone("zyth", new ItemStack(Items.DYE, 1, 9))
        );
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(BASE) {
            public @Override @Nonnull String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName(stack) + "." + BaseType.values()[MathHelper.clamp(stack.getItemDamage(), 0, BaseType.values().length)].getName(); }
            public @Override int getMetadata(int damage) { return damage; }
        }.setRegistryName(BASE.getRegistryName()));

        event.getRegistry().register(new ItemBlockLamp(LAMP_FLAT).setRegistryName(LAMP_FLAT.getRegistryName()));

        for (Map.Entry<String, BlockXtone> entry : BLOCKS.entrySet())
            event.getRegistry().register(new ItemBlockXtone(entry.getValue()).setRegistryName(entry.getKey()));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        if (!ConfigHandler.disableXtoneRecipes) {
            for (BlockXtone block : BLOCKS.values()) {
                ResourceLocation id = new ResourceLocation(Xtones.ID, block.getName());
                IRecipe recipe = new ShapedOreRecipe(id, new ItemStack(block, 8), CraftingHelper.parseShaped("BBB", "BCB", "BBB", 'B', BASE, 'C', block.getCraftStack()));
                event.getRegistry().register(recipe.setRegistryName(id));
            }
        }

        ResourceLocation id = new ResourceLocation(Xtones.ID, "base");
        IRecipe recipe = new ShapedOreRecipe(id, new ItemStack(BASE, 8), CraftingHelper.parseShaped("SS ", "SBS", " SS", 'S', new ItemStack(Blocks.STONE_SLAB), 'B', "stone"));
        event.getRegistry().register(recipe.setRegistryName(id));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (BlockXtone xtone : BLOCKS.values()) {
            Item itemBlock = Item.getItemFromBlock(xtone);
            for (BlockXtone.XtoneType type : xtone.getTypes())
                ModelLoader.setCustomModelResourceLocation(itemBlock, type.ordinal(), new ModelResourceLocation(xtone.getRegistryName(), "variant=" + type.getName()));
        }

        for (BaseType type : BaseType.values())
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BASE), type.ordinal(), new ModelResourceLocation(BASE.getRegistryName(), "type=" + type.getName()));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LAMP_FLAT), 0, new ModelResourceLocation(LAMP_FLAT.getRegistryName(), "active=false,facing=up"));
    }
    
    private static BlockXtone newXtone(String name, ItemStack craftStack) {
        BlockXtone xtone = new BlockXtone(name, craftStack);
        xtone.setRegistryName(name);
        BLOCKS.put(name, xtone);
        return xtone;
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
