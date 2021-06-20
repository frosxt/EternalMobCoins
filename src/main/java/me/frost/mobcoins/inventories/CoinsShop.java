package me.frost.mobcoins.inventories;

import com.google.common.base.Strings;
import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.utils.GeneralUtils;
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

    private ItemStack createItem(final String name, final Material material, final List<String> lore, final Short data) {
        final ItemStack item = new ItemStack(material, 1, data);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createItemGlass() {
        final ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    public CoinsShop(final Player player) {
        this.player = player;
        inventory = Bukkit.createInventory(this, MobCoins.getInstance().configFile.getConfig().getInt("inventory.inventory-size"), GeneralUtils.colorize("&8Viewing the &8&nMobCoins Shop"));
        init();
    }

    private void init() {
        ItemStack item;

        // Automatically fills unused inventory slots
        for (int i = 1; i <= MobCoins.getInstance().configFile.getConfig().getInt("inventory.inventory-size"); i++) {
            item = createItemGlass();
            inventory.setItem(inventory.firstEmpty(), item);
        }

        // Adds purchasable items from the config to the GUI
        for (final String section : MobCoins.getInstance().configFile.getConfig().getConfigurationSection("inventory.menu").getKeys(false)) {
            final List<String> lore = new ArrayList<>();
            for (final String lines : MobCoins.getInstance().configFile.getConfig().getStringList("inventory.menu." + section + ".lore")) {
                lore.add(GeneralUtils.colorize(lines).replaceAll("%balance%", String.valueOf(MobCoins.getInstance().dataFile.getConfig().getInt("balance." + getPlayer().getUniqueId().toString()))));
            }

            item = createItem(GeneralUtils.colorize(MobCoins.getInstance().configFile.getConfig().getString("inventory.menu." + section + ".display-name")), Material.getMaterial(MobCoins.getInstance().configFile.getConfig().getString("inventory.menu." + section + ".material.name").toUpperCase()), lore, (short) MobCoins.getInstance().configFile.getConfig().getInt("inventory.menu." + section + ".material.durability"));
            inventory.setItem(MobCoins.getInstance().configFile.getConfig().getInt("inventory.menu." + section + ".slot"), item);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private Player getPlayer() {
        return this.player;
    }

    public static String getProgressBar(final int current, final int max, final int totalBars, final char symbol, final String completedColor, final String notCompletedColor) {
        final float percent = (float) current / max;
        final int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + GeneralUtils.colorize(completedColor) + symbol, progressBars) + Strings.repeat("" + GeneralUtils.colorize(notCompletedColor) + symbol, totalBars - progressBars);
    }
}
