package tehnut.xtones;


import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import tehnut.xtones.block.ItemBlockXtone;
import tehnut.xtones.network.MessageCycleXtone;

@SideOnly(Side.CLIENT)
public class ClientHandler {

    public static final KeyBinding SCROLL_CATALYST = new KeyBinding("key." + Xtones.ID + ".scroll", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_LSHIFT, Xtones.NAME);

    static {
        ClientRegistry.registerKeyBinding(SCROLL_CATALYST);
    }

    @SubscribeEvent
    public void onMouseInput(MouseEvent event) {
        if (ConfigHandler.disableScrollCycling)
            return;

        if (Minecraft.getMinecraft().currentScreen == null) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
            if (event.getDwheel() != 0 && SCROLL_CATALYST.isKeyDown() && held.getItem() instanceof ItemBlockXtone) {
                Xtones.NETWORK_WRAPPER.sendToServer(new MessageCycleXtone(event.getDwheel() > 0));
                event.setCanceled(true);
            }
        }
    }
}
