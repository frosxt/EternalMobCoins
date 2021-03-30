package me.frost.mobcoins.inventories;

import com.google.common.base.Strings;
import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CoinsShop implements InventoryHolder {
    private final Player player;
    private final Inventory inventory;

    private ItemStack createItem(String name, Material material, List<String> lore, Short data) {
        ItemStack item = new ItemStack(material, 1, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createItemGlass() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    public CoinsShop(Player player) {
        this.player = player;
        inventory = Bukkit.createInventory(this, MobCoins.configFile.getConfig().getInt("inventory.inventory-size"), Formatting.colorize("&8Viewing the &8&nMobCoins Shop"));
        init();
    }

    private void init() {
        ItemStack item;

        // Automatically fills unused inventory slots
        for (int i = 1; i <= MobCoins.configFile.getConfig().getInt("inventory.inventory-size"); i++) {
            item = createItemGlass();
            inventory.setItem(inventory.firstEmpty(), item);
        }

        for (String section : MobCoins.configFile.getConfig().getConfigurationSection("inventory.menu").getKeys(false)) {
            List<String> lore = new ArrayList<>();
            for (String lines : MobCoins.configFile.getConfig().getStringList("inventory.menu." + section + ".lore")) {
                lore.add(Formatting.colorize(lines).replaceAll("%balance%", String.valueOf(MobCoins.dataFile.getConfig().getInt("balance." + getPlayer().getUniqueId().toString()))));
            }

            item = createItem(Formatting.colorize(MobCoins.configFile.getConfig().getString("inventory.menu." + section + ".display-name")), Material.getMaterial(MobCoins.configFile.getConfig().getString("inventory.menu." + section + ".material.name").toUpperCase()), lore, (short) MobCoins.configFile.getConfig().getInt("inventory.menu." + section + ".material.durability"));
            inventory.setItem(MobCoins.configFile.getConfig().getInt("inventory.menu." + section + ".slot"), item);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private Player getPlayer() {
        return this.player;
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + Formatting.colorize(completedColor) + symbol, progressBars) + Strings.repeat("" + Formatting.colorize(notCompletedColor) + symbol, totalBars - progressBars);
    }
}
