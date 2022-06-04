package net.jacg.stop_that.config;

public class Config {
    public AllowWhen isLogStrippingAllowed = AllowWhen.SNEAKING;
    public AllowWhen isBerryPlacingAllowed = AllowWhen.SNEAKING;
    public AllowWhen swordHitGrassAllowed = AllowWhen.SNEAKING;
    public AllowWhen blockBreakingWrongToolAllowed = AllowWhen.SNEAKING;

    public enum AllowWhen {
        ALWAYS,
        NEVER,
        SNEAKING
    }
}