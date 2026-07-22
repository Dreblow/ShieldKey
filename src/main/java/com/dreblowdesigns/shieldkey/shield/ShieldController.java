package com.dreblowdesigns.shieldkey.shield;

import com.dreblowdesigns.shieldkey.ShieldKeyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

public final class ShieldController {

    private enum ShieldState {
        IDLE,
        ACTIVE,
        SUSPENDED
    }

    private static ShieldState state = ShieldState.IDLE;

    private static boolean lastShieldKeyHeld;
    private static boolean lastRightMouseHeld;
    private static boolean restoreAfterRightClickRelease;

    private ShieldController() {
    }

    public static void update(Minecraft client, boolean shieldKeyHeld) {
        boolean rightMouseHeld = isRightMouseHeld(client);
        boolean shieldKeyPressed = shieldKeyHeld && !lastShieldKeyHeld;
        boolean shieldKeyReleased = !shieldKeyHeld && lastShieldKeyHeld;
        boolean rightMousePressed = rightMouseHeld && !lastRightMouseHeld;
        boolean rightMouseReleased = !rightMouseHeld && lastRightMouseHeld;

        if (shieldKeyHeld
                && rightMouseReleased
                && restoreAfterRightClickRelease
                && state == ShieldState.ACTIVE) {

            client.options.keyUse.setDown(true);
            restoreAfterRightClickRelease = false;

            ShieldKeyClient.LOGGER.info("[ShieldKey] Restored UseKey after right-click release");
        }

        if (shieldKeyPressed) {
            activateShield(client);
        }

        if (shieldKeyReleased) {
            deactivateShield(client);
        }

        if (shieldKeyHeld && rightMousePressed) {
            handleRightClickPressed(client);
        }

        lastShieldKeyHeld = shieldKeyHeld;
        lastRightMouseHeld = rightMouseHeld;
    }

    private static void activateShield(Minecraft client) {

        if (!hasOffhandShield(client)) {
            state = ShieldState.IDLE;
            return;
        }

        client.options.keyUse.setDown(true);
        state = ShieldState.ACTIVE;

        ShieldKeyClient.LOGGER.info(
                "[ShieldKey] State = ACTIVE"
        );
    }

    private static void handleRightClickPressed(Minecraft client) {
        switch (state) {
            case ACTIVE -> {
                client.options.keyUse.setDown(false);
                state = ShieldState.SUSPENDED;

                ShieldKeyClient.LOGGER.info("[ShieldKey] State = SUSPENDED");
            }

            case SUSPENDED -> {
                client.options.keyUse.setDown(true);
                state = ShieldState.ACTIVE;
                restoreAfterRightClickRelease = true;

                ShieldKeyClient.LOGGER.info("[ShieldKey] State = ACTIVE");
            }

            case IDLE -> {
                // Shield mode is not active.
            }
        }
    }

    private static void resumeShield(Minecraft client) {

        if (!hasOffhandShield(client)) {
            deactivateShield(client);
            return;
        }

        client.options.keyUse.setDown(true);
        state = ShieldState.ACTIVE;

        ShieldKeyClient.LOGGER.info(
                "[ShieldKey] State = ACTIVE"
        );
    }

    private static void deactivateShield(Minecraft client) {
        client.options.keyUse.setDown(false);
        restoreAfterRightClickRelease = false;
        state = ShieldState.IDLE;

        ShieldKeyClient.LOGGER.info("[ShieldKey] State = IDLE");
    }

    private static boolean hasOffhandShield(Minecraft client) {
        return client.player != null
                && client.player.getOffhandItem().is(Items.SHIELD);
    }

    private static boolean isRightMouseHeld(Minecraft client) {

        long windowHandle = client.getWindow().handle();

        return GLFW.glfwGetMouseButton(
                windowHandle,
                GLFW.GLFW_MOUSE_BUTTON_RIGHT
        ) == GLFW.GLFW_PRESS;
    }
}