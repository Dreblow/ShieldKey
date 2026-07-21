package com.dreblowdesigns.shieldkey.shield;

import com.dreblowdesigns.shieldkey.ShieldKeyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;

public final class ShieldController {

    private static boolean shieldRequested = false;

    private ShieldController() {
    }

    public static void setShieldRequested(boolean requested) {
        if (shieldRequested == requested) {
            return;
        }

        shieldRequested = requested;

        Minecraft client = Minecraft.getInstance();

        if (client.player == null || client.gameMode == null) {
            return;
        }

        if (shieldRequested) {
            if (client.player.getOffhandItem().is(Items.SHIELD)) {
                ShieldKeyClient.LOGGER.info("Shield key pressed. Starting shield use.");
                client.options.keyUse.setDown(true);
            } else {
                ShieldKeyClient.LOGGER.info("Shield key pressed, but no shield in offhand.");
            }
        } else {
            ShieldKeyClient.LOGGER.info("Shield key released. Stopping shield use.");
            client.options.keyUse.setDown(false);
        }
    }

    public static boolean isShieldRequested() {
        return shieldRequested;
    }
}