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
            for (int i = 0; i < armorLevel/2; ++i) {
                x = m + i * 8;
                drawTexture(matrices, x, y, 10, 0, 9, 9, 32, 32);
            }
            double armorRemainder = armor%2;
            int xOffset = ((armorLevel/2) * 8);
            int armorOffset = (int) Math.round((armorRemainder/2)*9)-1;
            // draw background of armor remainder
            drawTexture(matrices, m + xOffset, y, 0, 0, 9, 9, 32, 32);
            // draw armor remainder
            drawTexture(matrices, m + xOffset, y, 10, 0, armorOffset, 9, 32, 32);
            // draw armor remainder separator
            drawTexture(matrices, m + xOffset + armorOffset, y, armorOffset, 10, 1, 9, 32, 32);

            for (int i = 1; i < 10 - (armorLevel/2); ++i) {
                x = m + ((i + armorLevel/2) * 8);
                drawTexture(matrices, x, y, 0, 0, 9, 9, 32, 32);
            }
        }
    }
}