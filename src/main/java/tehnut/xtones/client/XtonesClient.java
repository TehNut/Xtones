package tehnut.xtones.client;


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
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.lwjgl.input.Keyboard;
import tehnut.xtones.Tone;
import tehnut.xtones.Xtones;
import tehnut.xtones.config.XtonesConfig;
import tehnut.xtones.item.XtoneBlockItem;
import tehnut.xtones.network.XtonesNetwork;

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

    private static @MonotonicNonNull Boolean serverXtoneCycling = null;

    private XtonesClient() {
    }

    public static String getScrollModifierName() {
        return SCROLL_MODIFIER.getDisplayName();
    }


    public static void setServerXtoneCycling(final boolean state) {
        if (serverXtoneCycling != null) {
            throw new IllegalStateException();
        }
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
        if (hasXtoneCycling() && event.getDwheel() != 0 && SCROLL_MODIFIER.isKeyDown()) {
            final Minecraft minecraft = Minecraft.getMinecraft();
            final EntityPlayer player = minecraft.player;
            if (player != null && minecraft.currentScreen == null) {
                EnumHand hand = EnumHand.MAIN_HAND;
                if (!isXtone(player.getHeldItem(hand))) {
                    hand = EnumHand.OFF_HAND;
                }
                if (isXtone(player.getHeldItem(hand))) {
                    XtonesNetwork.cycleXtone(hand, event);
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

    private static boolean hasXtoneCycling() {
        return XtonesConfig.hasXtoneCycling() && serverXtoneCycling != null && serverXtoneCycling;
    }

    private static boolean isXtone(final ItemStack stack) {
        return stack.getItem() instanceof XtoneBlockItem;
    }
}
