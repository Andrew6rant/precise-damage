package io.github.andrew6rant.precise_armor_bar;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ArmorRenderer extends DrawableHelper {
    public static final ArmorRenderer INSTANCE = new ArmorRenderer();
    public static final Identifier ARMOR_ICONS = new Identifier("precise_armor_bar", "textures/gui/precise_armor.png");

    public void init(MatrixStack matrices, PlayerEntity player) {
        double armor = player.getAttributes().getValue(EntityAttributes.GENERIC_ARMOR);
        int armorLevel = (int) Math.floor(armor);
        MinecraftClient minecraft = MinecraftClient.getInstance();
        int scaledWidth = minecraft.getWindow().getScaledWidth();
        int scaledHeight = minecraft.getWindow().getScaledHeight();

        // calculate armor offset variables (modified from InGameHud.class)
        int j = MathHelper.ceil(player.getHealth());
        int p = MathHelper.ceil(player.getAbsorptionAmount());
        float f = Math.max((float)player.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH), j);
        int m = scaledWidth / 2 - 91;
        int o = scaledHeight - 39;
        int q = MathHelper.ceil((f + (float)p) / 2.0F / 10.0F);
        int r = Math.max(10 - (q - 2), 3);

        int x;
        int y = o - (q - 1) * r - 10;

        if (armor > 0) {
            RenderSystem.setShaderTexture(0, ARMOR_ICONS);
            for(int i = 0; i < armorLevel/2; ++i) {
                x = m + i * 8;
                drawTexture(matrices, x, y, 20, 20, 9, 9, 32, 32);
            }
            double armorRemainder = armor%2;

            // doubles don't work with switch statements :(
            if (armorRemainder > 0 && armorRemainder < 0.29) {
                drawTexture(matrices, m + ((armorLevel/2) * 8), y, 10, 0, 9, 9, 32, 32);
            } else if (armorRemainder > 0.29 && armorRemainder < 0.57) {
                drawTexture(matrices, m + ((armorLevel/2) * 8), y, 20, 0, 9, 9, 32, 32);
            } else if (armorRemainder > 0.57 && armorRemainder < 0.86) {
                drawTexture(matrices, m + ((armorLevel/2) * 8), y, 0, 10, 9, 9, 32, 32);
            } else if (armorRemainder > 0.86 && armorRemainder < 1.14) {
                drawTexture(matrices, m + ((armorLevel/2) * 8), y, 10, 10, 9, 9, 32, 32);
            } else if (armorRemainder > 1.14 && armorRemainder < 1.43) {
                drawTexture(matrices, m + ((armorLevel/2) * 8), y, 20, 10, 9, 9, 32, 32);
            } else if (armorRemainder > 1.43 && armorRemainder < 1.71) {
                drawTexture(matrices, m + ((armorLevel/2) * 8), y, 0, 20, 9, 9, 32, 32);
            } else if (armorRemainder > 1.71 && armorRemainder < 2) {
                drawTexture(matrices, m + ((armorLevel/2) * 8), y, 10, 20, 9, 9, 32, 32);
            } else {
                if (armor != 20) { // if armor is 20, don't draw the last icon (this is temporary until I get >20 armor offsets working)
                    drawTexture(matrices, m + ((armorLevel/2) * 8), y, 0, 0, 9, 9, 32, 32);
                }
            }
            for(int i = 1; i < 10 - (armorLevel/2); ++i) {
                x = m + ((i + armorLevel/2) * 8);
                drawTexture(matrices, x, y, 0, 0, 9, 9, 32, 32);
            }
        }
    }
}