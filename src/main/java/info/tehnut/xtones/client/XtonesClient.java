package info.tehnut.xtones.client;


import info.tehnut.xtones.Tone;
import info.tehnut.xtones.Xtones;
import info.tehnut.xtones.config.XtonesConfig;
import info.tehnut.xtones.item.XtoneBlockItem;
import info.tehnut.xtones.network.XtonesNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(value = Side.CLIENT, modid = Xtones.ID)
public final class XtonesClient {
    private static final KeyBinding SCROLL_MODIFIER = new KeyBinding(
        "key." + Xtones.ID + ".scroll", KeyConflictContext.IN_GAME, Keyboard.KEY_LSHIFT, Xtones.NAME
    );

    static {
        ClientRegistry.registerKeyBinding(SCROLL_MODIFIER);
    }

    private static boolean serverXtoneCycling = false;

    private XtonesClient() {
    }

    public static String getScrollModifierName() {
        return SCROLL_MODIFIER.getDisplayName();
    }

    public static boolean canCycleXtones() {
        return XtonesConfig.hasXtoneCycling() && serverXtoneCycling;
    }

    public static void setServerXtoneCycling(final boolean state) {
        serverXtoneCycling = state;
    }

    @SubscribeEvent
    static void registerModels(final ModelRegistryEvent event) {
        Xtones.items().forEach(item -> {
            for (int variant = 0; variant < Tone.VARIANTS; ++variant) {
                setCustomModelResourceLocation(item, variant, "variant=" + variant);
            }
        });
        setCustomModelResourceLocation(Xtones.baseItem(), 0, "normal");
        setCustomModelResourceLocation(Xtones.lampItem(), 0, "active=false,facing=up");
    }

    @SubscribeEvent
    static void mousePolled(final MouseEvent event) {
        final int scroll = event.getDwheel();
        if ((scroll != 0) && canCycleXtones() && SCROLL_MODIFIER.isKeyDown()) {
            final Minecraft minecraft = Minecraft.getMinecraft();
            final @Nullable EntityPlayer player = minecraft.player;
            if ((player != null) && (minecraft.currentScreen == null)) {
                EnumHand hand = EnumHand.MAIN_HAND;
                if (!isXtone(player.getHeldItem(hand))) {
                    hand = EnumHand.OFF_HAND;
                }
                if (isXtone(player.getHeldItem(hand))) {
                    XtonesNetwork.cycleXtone(player, hand, scroll);
                    event.setCanceled(true);
                }
            }
        }
    }

    private static void setCustomModelResourceLocation(final Item item, final int meta, final String variant) {
        final ResourceLocation name = Objects.requireNonNull(item.getRegistryName());
        final ModelResourceLocation model = new ModelResourceLocation(name, variant);
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }

    private static boolean isXtone(final ItemStack stack) {
        return stack.getItem() instanceof XtoneBlockItem;
    }
}
