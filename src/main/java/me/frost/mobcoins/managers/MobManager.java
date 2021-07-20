package me.frost.mobcoins.managers;

import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class MobManager {
    private static final Map<EntityType, Integer> MOB_CHANCES = new HashMap<>();

    private MobManager() {
        // Private constructor so you cannot instantiate the class
    }

    public static Map<EntityType, Integer> getMobChances() {
        return MOB_CHANCES;
    }
}
