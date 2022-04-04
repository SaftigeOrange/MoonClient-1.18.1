package de.orange.client.gui.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import de.orange.client.Client;
import net.minecraft.client.gui.Font;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class OverlayManager {

    private final ConcurrentHashMap<Class<?>, IOverlay> overlays = new ConcurrentHashMap<>();

    public void showOverlay(Class<?> clazz) {
        try {
            overlays.put(clazz, Objects.requireNonNull(touch(clazz)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideOverlay(Class<?> clazz) {
        try {
            overlays.remove(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        for(Class<?> clazz : overlays.keySet())
            overlays.get(clazz).render(pPoseStack, Client.minecraft.font, pMouseX, pMouseY, pPartialTick);
    }

    private IOverlay touch(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            return (IOverlay) clazz.getDeclaredConstructor().newInstance();
    }

}
