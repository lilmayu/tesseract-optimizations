# [Tesseract Optimizations](https://www.curseforge.com/minecraft/mc-mods/tesseract-optimizations)

*Optimizing Tesseracts for better performance*

This mod optimizes the rendering of [SuperMartijn642's Tesseract mod](https://www.curseforge.com/minecraft/mc-mods/tesseract)
by rendering just black faces instead of the end portal effect. The FPS improvement is significant, especially when rendering multiple tesseracts.

The end portal effect *is pretty*, but useless for more technic-oriented modpacks.

See below for comparison screenshots.

## Installation
- Minecraft 1.12.2
- [SuperMartijn642's Tesseract mod](https://www.curseforge.com/minecraft/mc-mods/tesseract)
- [MixinBooter](https://www.curseforge.com/minecraft/mc-mods/mixin-booter)

## Configuration
`config/Tesseract Optimizations.cfg`
```toml
# Configuration file

general {
    # Enable or disable Tesseract Optimizations
    B:Enabled=true

    # Enable or disable rainbow mode
    B:"Rainbow Mode"=false
}
```

## Screenshots (1024 tesseracts)
![Optimized Implementation](https://i.imgur.com/lpxfRYy.png)
![Original Implementation](https://i.imgur.com/AQ9oyVx.png)

### Rainbow effect

![Rainbow Effect](https://i.imgur.com/1JLMyoH.gif)

## Screenshots from average modded environment (circa 10 tesseracts)
![Optimized Implementation](https://i.imgur.com/YdRNX6h.png)
![Original Implementation](https://i.imgur.com/ywJGai9.png)