package com.dreblowdesigns.shieldkey.utilities;

import com.dreblowdesigns.shieldkey.ShieldKeyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class InputDiagnostics {

    private static boolean lastShift;
    private static boolean lastUseKey;
    private static boolean lastUsingItem;
    private static ItemStack lastOffhand = ItemStack.EMPTY;

    private InputDiagnostics() {
    }

    public static void update(Minecraft client, boolean shiftHeld) {

        //
        // ShieldKey input
        //
        if (shiftHeld != lastShift) {
            ShieldKeyClient.LOGGER.info(
                    "[ShieldKey] Shift = {}",
                    shiftHeld ? "DOWN" : "UP"
            );

            lastShift = shiftHeld;
        }

        //
        // Vanilla Use Item mapping
        //
        boolean useKey = client.options.keyUse.isDown();

        if (useKey != lastUseKey) {
            ShieldKeyClient.LOGGER.info(
                    "[ShieldKey] UseKey = {}",
                    useKey ? "DOWN" : "UP"
            );

            lastUseKey = useKey;
        }

        if (client.player == null) {
            return;
        }

        //
        // Offhand item
        //
        ItemStack offhand = client.player.getOffhandItem();

        if (!ItemStack.matches(offhand, lastOffhand)) {
            ShieldKeyClient.LOGGER.info(
                    "[ShieldKey] Offhand = {}",
                    offhand.isEmpty() ? "EMPTY" : offhand.getItem()
            );

            lastOffhand = offhand.copy();
        }

        //
        // Actual player item-use state
        //
        boolean usingItem = client.player.isUsingItem();

        if (usingItem == lastUsingItem) {
            return;
        }

        if (usingItem) {
            ItemStack stack = client.player.getUseItem();

            if (stack.is(Items.SHIELD)) {
                ShieldKeyClient.LOGGER.info(
                        "[ShieldKey] Player started using SHIELD"
                );
            } else {
                ShieldKeyClient.LOGGER.info(
                        "[ShieldKey] Player started using {}",
                        stack.getItem()
                );
            }
        } else {
            ShieldKeyClient.LOGGER.info(
                    "[ShieldKey] Player stopped using item"
            );
        }

        lastUsingItem = usingItem;
    }
}