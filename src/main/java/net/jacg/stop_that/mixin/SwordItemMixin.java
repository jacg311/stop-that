package net.jacg.stop_that.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jacg.stop_that.StopThatClient.CONFIG;
import static net.jacg.stop_that.config.Config.AllowWhen;

@Mixin(SwordItem.class)
public class SwordItemMixin {
    @Inject(method = "canMine", at = @At("HEAD"), cancellable = true)
    private void stop_that$cancelSwordBreakingGrass(BlockState state, World world, BlockPos pos, PlayerEntity miner, CallbackInfoReturnable<Boolean> cir) {
        if (CONFIG.swordHitGrassAllowed == AllowWhen.SNEAKING ? !miner.isSneaking() : CONFIG.swordHitGrassAllowed == AllowWhen.NEVER) {
            cir.setReturnValue(false);
        }
    }
}
