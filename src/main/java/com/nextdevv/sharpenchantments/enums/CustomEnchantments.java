package com.nextdevv.sharpenchantments.enums;

public enum CustomEnchantments {
    EXPLOSION("Explosion"),
    ANTI_EXPLOSION("Anti Explosion"),
    VIPER("Viper"),
    INQUISITIVE("Inquisitive"),
    MINER("Miner"),
    LUMBERJACK("Lumberjack"),
    BRUTE("Brute"),
    LEGEND("Legend"),
    BUNNY("Bunny"),
    SPEED("Speed"),
    OVERLOAD("Overload"),
    HUNGER_LESS("Hunger Less"),
    AUTO_SMELT("Auto Smelt"),
    IMPLANTS("Implants");

    CustomEnchantments(String explosion) {

    }

    public String getEnchantName() {
        switch (this) {
            case EXPLOSION:
                return "Explosion";
            case ANTI_EXPLOSION:
                return "Anti Explosion";
            case VIPER:
                return "Viper";
            case INQUISITIVE:
                return "Inquisitive";
            case MINER:
                return "Miner";
            case LUMBERJACK:
                return "Lumberjack";
            case BRUTE:
                return "Brute";
            case LEGEND:
                return "Legend";
            case BUNNY:
                return "Bunny";
            case SPEED:
                return "Speed";
            case OVERLOAD:
                return "Overload";
            case HUNGER_LESS:
                return "Hunger Less";
            case AUTO_SMELT:
                return "Auto Smelt";
            case IMPLANTS:
                return "Implants";

            default:
                return "Unknown";
        }
    }
}
