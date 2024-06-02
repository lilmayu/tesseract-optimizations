package dev.mayuna.tesseract_optimizations;

import dev.mayuna.tesseract_optimizations.config.TesseractOptimizationsConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = TesseractOptimizations.MODID,
        name = TesseractOptimizations.NAME,
        version = TesseractOptimizations.VERSION
)
public class TesseractOptimizations {

    public static final String MODID = "tesseract_optimizations";
    public static final String NAME = "Tesseract Optimizations";
    public static final String VERSION = "1.0";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent preinit) {
        LOGGER.info("Optimizing your magic 4D cubes... {}", TesseractOptimizationsConfig.rainbow ? "(With rainbow!)" : "");
    }
}
