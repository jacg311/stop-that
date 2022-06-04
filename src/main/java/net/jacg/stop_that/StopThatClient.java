package net.jacg.stop_that;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.jacg.stop_that.config.Config;
import net.jacg.stop_that.config.Util;
import net.minecraft.block.Blocks;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
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
            Item item = player.getStackInHand(hand).getItem();
            if (item instanceof AxeItem && Util.checkCondition(CONFIG.isLogStrippingAllowed, player)) {
                return ActionResult.FAIL;
            }
            if ((item == Items.SWEET_BERRIES || item == Items.GLOW_BERRIES)
                && Util.checkCondition(CONFIG.isBerryPlacingAllowed, player)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackBlockCallback.EVENT.register( (player, world, hand, pos, direction) ->  {
            if (!Util.checkCondition(CONFIG.blockBreakingWrongToolAllowed, player)) {
                return ActionResult.PASS;
            }
            Item item = player.getStackInHand(hand).getItem();
            if (item != null && item != Items.AIR && !item.isSuitableFor(world.getBlockState(pos))) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
}
