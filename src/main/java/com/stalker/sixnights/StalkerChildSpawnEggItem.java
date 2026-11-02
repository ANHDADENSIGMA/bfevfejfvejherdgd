package com.stalker.sixnights;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;

/** Spawn egg that forces one of the three child PNG variants. */
public final class StalkerChildSpawnEggItem extends ForgeSpawnEggItem {
    private final int variant;
    public StalkerChildSpawnEggItem(int variant, int primary, int secondary, Item.Properties properties) {
        super(() -> StalkerRegistry.CHILD.get(), primary, secondary, properties);
        this.variant = variant;
    }
    @Override public net.minecraft.world.InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            var pos = context.getClickedPos().relative(context.getClickedFace());
            var entity = StalkerRegistry.CHILD.get().create(level);
            if (entity != null) {
                entity.setVariant(variant);
                entity.moveTo(pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D, 0.0F, 0.0F);
                entity.finalizeSpawn((net.minecraft.server.level.ServerLevel) level, level.getCurrentDifficultyAt(pos), MobSpawnType.SPAWN_EGG, null, null);
                entity.setVariant(variant);
                level.addFreshEntity(entity);
                if (!context.getPlayer().getAbilities().instabuild) context.getItemInHand().shrink(1);
            }
        }
        return net.minecraft.world.InteractionResult.sidedSuccess(level.isClientSide);
    }
}
