package net.jacg.stop_that;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.jacg.stop_that.config.Config;
import net.jacg.stop_that.config.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class StopThatClient implements ClientModInitializer {
    public static String MOD_ID = "stop_that";
    public static Config CONFIG;
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitializeClient() {
        CONFIG = Util.readConfigOrDefault();

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player.getStackInHand(hand).getItem() == Items.SWEET_BERRIES
                && (CONFIG.isBerryPlacingAllowed == Config.AllowWhen.SNEAKING ? !player.isSneaking() : CONFIG.isBerryPlacingAllowed == Config.AllowWhen.NEVER)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
}
