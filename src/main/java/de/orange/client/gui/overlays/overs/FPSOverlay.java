package de.orange.client.gui.overlays.overs;

import com.mojang.blaze3d.vertex.PoseStack;
import de.orange.client.gui.overlays.IOverlay;
import net.minecraft.client.gui.Font;
import net.optifine.Config;

public class FPSOverlay implements IOverlay {
    @Override
    public void render(PoseStack pPostStack, Font font, int pMouseX, int pMouseY, float pPartialTick) {
        String fps = "FPS: " + Config.getFpsString();
        font.draw(pPostStack, fps, 2.5F, 2.5F, -2039584);
    }

}
