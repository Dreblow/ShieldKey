package com.dreblowdesigns.shieldkey;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShieldKeyClient implements ClientModInitializer {

    public static final String MOD_ID = "shieldkey";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "controls"));

    public static KeyMapping shieldKey;

    @Override
    public void onInitializeClient() {
        shieldKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.shieldkey.raise_shield",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_SHIFT,
                CATEGORY
        ));

        LOGGER.info("ShieldKey initialized. Keybind registered.");
    }
}