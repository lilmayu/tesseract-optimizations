package dev.mayuna.tesseract_optimizations.mixin;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

/**
 * Mixin loader for Tesseract Optimizations
 */
public class MixinLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("tesseract_optimizations.mixins.json");
    }
}
