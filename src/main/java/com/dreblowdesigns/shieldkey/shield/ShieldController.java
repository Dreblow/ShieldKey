package com.dreblowdesigns.shieldkey.shield;

import com.dreblowdesigns.shieldkey.ShieldKeyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

public final class ShieldController {

    private enum ShieldState {
        IDLE,
        ACTIVE,
        SUSPENDED,
        ATTACKING
    }

    private static ShieldState state = ShieldState.IDLE;

    private static boolean lastShieldKeyHeld;
    private static boolean lastRightMouseHeld;
    private static boolean lastAttackHeld;
    private static boolean restoreAfterRightClickRelease;

    private ShieldController() {
    }

    public static void update(Minecraft client, boolean shieldKeyHeld) {

        boolean rightMouseHeld = isRightMouseHeld(client);
        boolean attackHeld = isLeftMouseHeld(client);
        boolean shieldKeyPressed = shieldKeyHeld && !lastShieldKeyHeld;
        boolean shieldKeyReleased = !shieldKeyHeld && lastShieldKeyHeld;
        boolean rightMousePressed = rightMouseHeld && !lastRightMouseHeld;
        boolean rightMouseReleased = !rightMouseHeld && lastRightMouseHeld;
        boolean attackPressed = attackHeld && !lastAttackHeld;
        boolean attackReleased = !attackHeld && lastAttackHeld;

        //
        // Shield key pressed
        //
        if (shieldKeyPressed) {
            if (attackHeld) {
                state = ShieldState.ATTACKING;

                ShieldKeyClient.LOGGER.debug("[ShieldKey] State = ATTACKING");
            }
            else {
                activateShield(client);
            }
        }

        //
        // Shield key released
        //
        if (shieldKeyReleased) {
            deactivateShield(client);
        }

        //
        // Attack started while shield is active
        //
        if (shieldKeyHeld
                && attackPressed
                && state == ShieldState.ACTIVE) {

            client.options.keyUse.setDown(false);
            state = ShieldState.ATTACKING;

            ShieldKeyClient.LOGGER.debug("[ShieldKey] State = ATTACKING");
        }

        //
        // Attack finished while ShieldKey remains held
        //
        if (shieldKeyHeld
                && attackReleased
                && state == ShieldState.ATTACKING) {

            activateShield(client);
        }

        //
        // Right-click toggles active/suspended shield mode
        //
        if (shieldKeyHeld && rightMousePressed) {
            handleRightClickPressed(client);
        }

        //
        // Minecraft clears UseKey when the resumed
        // right-click is released, so restore it once.
        //
        if (shieldKeyHeld
                && rightMouseReleased
                && restoreAfterRightClickRelease
                && state == ShieldState.ACTIVE) {

            client.options.keyUse.setDown(true);
            restoreAfterRightClickRelease = false;

            ShieldKeyClient.LOGGER.debug("[ShieldKey] Restored UseKey after right-click release");
        }

        lastShieldKeyHeld = shieldKeyHeld;
        lastRightMouseHeld = rightMouseHeld;
        lastAttackHeld = attackHeld;
    }

    private static void activateShield(Minecraft client) {

        if (!hasOffhandShield(client)) {
            state = ShieldState.IDLE;
            return;
        }

        client.options.keyUse.setDown(true);
        state = ShieldState.ACTIVE;

        ShieldKeyClient.LOGGER.debug("[ShieldKey] State = ACTIVE");
    }

    private static void handleRightClickPressed(Minecraft client) {

        switch (state) {

            case ACTIVE -> {
                client.options.keyUse.setDown(false);
                state = ShieldState.SUSPENDED;

                ShieldKeyClient.LOGGER.debug("[ShieldKey] State = SUSPENDED");
            }

            case SUSPENDED -> {
                client.options.keyUse.setDown(true);
                restoreAfterRightClickRelease = true;
                state = ShieldState.ACTIVE;

                ShieldKeyClient.LOGGER.debug("[ShieldKey] State = ACTIVE");
            }

            case IDLE, ATTACKING -> {
                // Do nothing.
            }
        }
    }

    private static void deactivateShield(Minecraft client) {
        client.options.keyUse.setDown(false);

        restoreAfterRightClickRelease = false;
        state = ShieldState.IDLE;

        ShieldKeyClient.LOGGER.debug("[ShieldKey] State = IDLE");
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

    private static boolean isLeftMouseHeld(Minecraft client) {
        long windowHandle = client.getWindow().handle();

        return GLFW.glfwGetMouseButton(
                windowHandle,
                GLFW.GLFW_MOUSE_BUTTON_LEFT
        ) == GLFW.GLFW_PRESS;
    }
}