package dev.mayuna.tesseract_optimizations.config;

import dev.mayuna.tesseract_optimizations.TesseractOptimizations;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Tesseract Optimizations config
 */
@Config(modid = TesseractOptimizations.MODID, name = "Tesseract Optimizations")
public class TesseractOptimizationsConfig {

    @Config.Name("Enabled")
    @Config.Comment("Enable or disable Tesseract Optimizations")
    public static boolean enabled = true;

    @Config.Name("Rainbow Mode")
    @Config.Comment("Enable or disable rainbow mode")
    public static boolean rainbow = false;

    /**
     * Handles the config changed event
     */
    @Mod.EventBusSubscriber(modid = TesseractOptimizations.MODID)
    private static class EventHandler {

        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(TesseractOptimizations.MODID)) {
                ConfigManager.sync(TesseractOptimizations.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
