package com.dreblowdesigns.shieldkey.shield;

import com.dreblowdesigns.shieldkey.ShieldKeyClient;

public final class ShieldController {

    private static boolean shieldRequested = false;

    private ShieldController() {
    }

    public static void setShieldRequested(boolean requested) {
        if (shieldRequested == requested) {
            return;
        }

        shieldRequested = requested;

        if (shieldRequested) {
            ShieldKeyClient.LOGGER.info("Shield key pressed.");
        } else {
            ShieldKeyClient.LOGGER.info("Shield key released.");
        }
    }

    public static boolean isShieldRequested() {
        return shieldRequested;
    }
}