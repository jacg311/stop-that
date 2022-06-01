package net.jacg.stop_that.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jacg.stop_that.StopThatClient.CONFIG;
import static net.jacg.stop_that.config.Config.AllowWhen;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void stop_that$cancelBlockStripping(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();

        if (player != null && (CONFIG.isLogStrippingAllowed == AllowWhen.SNEAKING) ? !player.isSneaking() : CONFIG.isLogStrippingAllowed == AllowWhen.NEVER) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
