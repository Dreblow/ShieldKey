package com.dreblowdesigns.shieldkey.input;

import com.dreblowdesigns.shieldkey.ShieldKeyClient;
import com.dreblowdesigns.shieldkey.shield.ShieldController;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class ShieldKeyBinding {

    private static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(ShieldKeyClient.MOD_ID, "controls"));

    private static KeyMapping shieldKey;
    private static boolean wasPressed = false;

    private ShieldKeyBinding() {
    }

    public static void initialize() {
        shieldKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.shieldkey.raise_shield",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_SHIFT,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean isPressed = shieldKey.isDown();

            if (isPressed != wasPressed) {
                ShieldController.setShieldRequested(isPressed);
                wasPressed = isPressed;
            }
        });

        ShieldKeyClient.LOGGER.info("Shield keybind registered.");
    }
}