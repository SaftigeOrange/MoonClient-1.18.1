package de.orange.client.gui.overlays.overs;

import com.mojang.blaze3d.vertex.PoseStack;
import de.orange.client.Client;
import de.orange.client.gui.overlays.IOverlay;
import net.minecraft.client.gui.Font;

public class ZOverlay implements IOverlay {

    @Override
    public void render(PoseStack pPostStack, Font font, int pMouseX, int pMouseY, float pPartialTick) {
        String locX = "Z: " + String.format("%,.3f", Client.minecraft.player.getZ()).replace(",", ".");
        font.draw(pPostStack, locX, 2.5F, 26.5F, -2039584);
    }

}
