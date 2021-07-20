package me.frost.mobcoins.managers;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BalanceManager {
    private static final Map<UUID, Integer> PLAYER_DATA = new ConcurrentHashMap<>();

    private BalanceManager() {
        // Private constructor so you cannot instantiate the class
    }

    public static Map<UUID, Integer> getBalances() {
        return PLAYER_DATA;
    }
}
