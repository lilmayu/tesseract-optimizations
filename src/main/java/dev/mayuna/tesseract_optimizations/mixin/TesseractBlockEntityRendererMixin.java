package dev.mayuna.tesseract_optimizations.mixin;

import com.supermartijn642.tesseract.TesseractBlockEntity;
import com.supermartijn642.tesseract.TesseractBlockEntityRenderer;
import dev.mayuna.tesseract_optimizations.config.TesseractOptimizationsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/**
 * Optimized Tesseract block rendering
 */
@Mixin(TesseractBlockEntityRenderer.class)
public abstract class TesseractBlockEntityRendererMixin {

    @Unique
    private static final double TWO_PI_OVER_THREE = 2 * Math.PI / 3;

    @Unique
    private static final double FOUR_PI_OVER_THREE = 4 * Math.PI / 3;

    @Unique
    private static long tesseract_optimizations$lastRainbowUpdate = 0;

    @Unique
    private static float tesseract_optimizations$red = 0;

    @Unique
    private static float tesseract_optimizations$green = 0;

    @Unique
    private static float tesseract_optimizations$blue = 0;

    /**
     * Updates the rainbow color every 10ms and sets the color to the GL state
     */
    @Unique
    private static void tesseract_optimizations$doRainbowColor() {
        // Get the current time in milliseconds
        final long timeMillis = System.currentTimeMillis();

        // Update the rainbow color every 10ms
        if (timeMillis - tesseract_optimizations$lastRainbowUpdate > 10) {
            final double timeSeconds = timeMillis / 1000.0;

            tesseract_optimizations$red = (float) (0.5 * (1 + Math.sin(timeSeconds)));
            tesseract_optimizations$green = (float) (0.5 * (1 + Math.sin(timeSeconds + TWO_PI_OVER_THREE)));
            tesseract_optimizations$blue = (float) (0.5 * (1 + Math.sin(timeSeconds + FOUR_PI_OVER_THREE)));

            tesseract_optimizations$lastRainbowUpdate = timeMillis;
        }

        GlStateManager.color(tesseract_optimizations$red, tesseract_optimizations$green, tesseract_optimizations$blue);
    }

    @Shadow(remap = false)
    protected abstract void renderEnderFaces();

    /**
     * @author mayuna
     * @reason Optimized TesseractBlockEntityRenderer#render method
     */
    @Overwrite(remap = false)
    public void render(TesseractBlockEntity entity, float partialTicks, int combinedOverlay, float alpha) {
        // Don't render if the Tesseract is disabled
        if (!entity.renderOn()) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5, 0.5, 0.5);
        GlStateManager.scale(0.65, 0.65, 0.65);
        GlStateManager.translate(-0.5, -0.5, -0.5);

        if (TesseractOptimizationsConfig.enabled) {
            tesseract_optimizations$renderBlackFaces(entity);
        } else {
            this.renderEnderFaces();
        }

        GlStateManager.popMatrix();
    }

    /**
     * Renders the black faces of a Tesseract block
     *
     * @param blockEntity The Tesseract block entity
     */
    @Unique
    private void tesseract_optimizations$renderBlackFaces(TesseractBlockEntity blockEntity) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.disableTexture2D();

        // Disable lighting to prevent light glitches + moar FPS
        GlStateManager.disableLighting();

        if (TesseractOptimizationsConfig.rainbow) {
            // Rainbow
            tesseract_optimizations$doRainbowColor();
        } else {
            // Black
            GlStateManager.color(0, 0, 0);
        }

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);

        if (tesseract_optimizations$isSideVisible(blockEntity, EnumFacing.DOWN)) {
            bufferbuilder.pos(0, 0, 0).endVertex();
            bufferbuilder.pos(1, 0, 0).endVertex();
            bufferbuilder.pos(1, 0, 1).endVertex();
            bufferbuilder.pos(0, 0, 1).endVertex();
        }

        if (tesseract_optimizations$isSideVisible(blockEntity, EnumFacing.UP)) {
            bufferbuilder.pos(0, 1, 1).endVertex();
            bufferbuilder.pos(1, 1, 1).endVertex();
            bufferbuilder.pos(1, 1, 0).endVertex();
            bufferbuilder.pos(0, 1, 0).endVertex();
        }

        if (tesseract_optimizations$isSideVisible(blockEntity, EnumFacing.NORTH)) {
            bufferbuilder.pos(0, 1, 0).endVertex();
            bufferbuilder.pos(1, 1, 0).endVertex();
            bufferbuilder.pos(1, 0, 0).endVertex();
            bufferbuilder.pos(0, 0, 0).endVertex();
        }

        if (tesseract_optimizations$isSideVisible(blockEntity, EnumFacing.SOUTH)) {
            bufferbuilder.pos(0, 0, 1).endVertex();
            bufferbuilder.pos(1, 0, 1).endVertex();
            bufferbuilder.pos(1, 1, 1).endVertex();
            bufferbuilder.pos(0, 1, 1).endVertex();
        }

        if (tesseract_optimizations$isSideVisible(blockEntity, EnumFacing.WEST)) {
            bufferbuilder.pos(0, 0, 0).endVertex();
            bufferbuilder.pos(0, 0, 1).endVertex();
            bufferbuilder.pos(0, 1, 1).endVertex();
            bufferbuilder.pos(0, 1, 0).endVertex();
        }

        if (tesseract_optimizations$isSideVisible(blockEntity, EnumFacing.EAST)) {
            bufferbuilder.pos(1, 1, 0).endVertex();
            bufferbuilder.pos(1, 1, 1).endVertex();
            bufferbuilder.pos(1, 0, 1).endVertex();
            bufferbuilder.pos(1, 0, 0).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
    }

    /**
     * Determines if a face of a Tesseract block is visible to the player
     *
     * @param blockEntity The Tesseract block entity
     * @param face        The face to check
     *
     * @return True if the face is visible, false otherwise
     */
    @Unique
    private boolean tesseract_optimizations$isSideVisible(TesseractBlockEntity blockEntity, EnumFacing face) {
        // Get the block's position
        BlockPos pos = blockEntity.getPos();

        // Get the player's position
        EntityPlayer player = Minecraft.getMinecraft().player;
        Vec3d playerPos = player.getPositionEyes(1.0F);

        // Calculate the direction from the player to the block
        Vec3d blockPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        Vec3d playerToBlock = blockPos.subtract(playerPos).normalize();

        // Check if the face is in the player's line of sight
        Vec3d faceDirection = new Vec3d(face.getDirectionVec());
        return playerToBlock.dotProduct(faceDirection) < 0;
    }
}
