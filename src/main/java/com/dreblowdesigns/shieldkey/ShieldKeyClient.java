package com.dreblowdesigns.shieldkey;

import com.dreblowdesigns.shieldkey.input.ShieldKeyBinding;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShieldKeyClient implements ClientModInitializer {

    public static final String MOD_ID = "shieldkey";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ShieldKeyBinding.initialize();

        LOGGER.info("ShieldKey initialized.");
    }
}