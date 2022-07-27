package io.github.andrew6rant.precise_damage.mixin;

import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Mutable
    @Shadow
    @Final
    private final AttributeContainer attributes;

    @Shadow
    public double getAttributeValue(EntityAttribute attribute) {
        return this.getAttributes().getValue(attribute);
    }

    @Shadow
    public AttributeContainer getAttributes() {
        return this.attributes;
    }

    public LivingEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
        this.attributes = new AttributeContainer(DefaultAttributeRegistry.get(type));
    }

    // get raw armor value instead of MathHelper.floor()'s int value
    @Redirect(method = "applyArmorToDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(FFF)F"))
    private float applyArmorToDamage(float damage, float armor, float armorToughness) {
        return DamageUtil.getDamageLeft(damage, (float) this.getAttributeValue(EntityAttributes.GENERIC_ARMOR), (float) this.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
    }
}
