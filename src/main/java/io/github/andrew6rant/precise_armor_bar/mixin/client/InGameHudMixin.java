package io.github.andrew6rant.precise_armor_bar.mixin.client;

import io.github.andrew6rant.precise_armor_bar.ArmorRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    // Remove Vanilla's armor bar
    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getArmor()I"))
    private int dontRenderStatusBars(PlayerEntity instance) {
        return 0;
    }
    // Render precise armor bar
    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V"))
    private void newArmorBars(MatrixStack matrices, CallbackInfo ci) {
        ArmorRenderer.INSTANCE.init(matrices, getCameraPlayer());
    }
}
