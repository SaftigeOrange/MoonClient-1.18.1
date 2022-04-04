package de.orange.client.gui.overlays.overs;

import com.mojang.blaze3d.vertex.PoseStack;
import de.orange.client.Client;
import de.orange.client.gui.overlays.IOverlay;
import net.minecraft.client.gui.Font;

public class XOverlay implements IOverlay {

    @Override
    public void render(PoseStack pPostStack, Font font, int pMouseX, int pMouseY, float pPartialTick) {
        String locX = "X: " + String.format("%,.3f", Client.minecraft.player.getX()).replace(",", ".");
        font.draw(pPostStack, locX, 2.5F, 10.5F, -2039584);
    }

}
