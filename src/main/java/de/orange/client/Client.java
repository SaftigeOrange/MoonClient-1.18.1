package de.orange.client;

import de.orange.client.gui.overlays.OverlayManager;
import de.orange.client.gui.overlays.overs.FPSOverlay;
import de.orange.client.gui.overlays.overs.XOverlay;
import de.orange.client.gui.overlays.overs.YOverlay;
import de.orange.client.gui.overlays.overs.ZOverlay;
import net.minecraft.client.Minecraft;

public class Client {

    public static Minecraft minecraft;

    private static boolean initialize = false;

    public static OverlayManager overlayManager;

    public static void init(Minecraft minecraft) {
        Client.minecraft = minecraft;
        overlayManager = new OverlayManager();
        initialize = true;
    }

    public static String websiteUrlMain = "https://craria.net/";
    public static String websiteUrlDiscord = "https://craria.net/moonclient/discord";

    public static  void tick() {
        if(minecraft.level != null && !minecraft.level.isDebug()) {
            overlayManager.showOverlay(FPSOverlay.class);
            overlayManager.showOverlay(XOverlay.class);
            overlayManager.showOverlay(YOverlay.class);
            overlayManager.showOverlay(ZOverlay.class);
        } else {
            overlayManager.hideOverlay(FPSOverlay.class);
            overlayManager.hideOverlay(XOverlay.class);
            overlayManager.hideOverlay(YOverlay.class);
            overlayManager.hideOverlay(ZOverlay.class);
        }
    }

    public static boolean isInitialize() {
        return initialize;
    }
}
