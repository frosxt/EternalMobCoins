package me.frost.mobcoins.inventories;

import me.frost.mobcoins.MobCoins;
import me.frost.mobcoins.MobCoinsAPI;
import me.frost.mobcoins.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CoinsShop implements InventoryHolder {
    private final Player player;
    private final Inventory inventory;
    private final JavaPlugin plugin = MobCoins.getProvidingPlugin(MobCoins.class);

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
        inventory = Bukkit.createInventory(this, plugin.getConfig().getInt("inventory.inventory-size"), GeneralUtils.colorize("&8Viewing the &8&nMobCoins Shop"));
        init();
    }

    private void init() {
        ItemStack item;

        // Automatically fills unused inventory slots
        for (int i = 1; i <= plugin.getConfig().getInt("inventory.inventory-size"); i++) {
            item = createItemGlass();
            inventory.setItem(inventory.firstEmpty(), item);
        }

        // Adds purchasable items from the config to the GUI
        for (final String section : plugin.getConfig().getConfigurationSection("inventory.menu").getKeys(false)) {
            final List<String> lore = new ArrayList<>();
            for (final String lines : plugin.getConfig().getStringList("inventory.menu." + section + ".lore")) {
                lore.add(GeneralUtils.colorize(lines).replaceAll("%balance%", String.valueOf(MobCoinsAPI.getMobCoins(player))));
            }

            item = createItem(GeneralUtils.colorize(plugin.getConfig().getString("inventory.menu." + section + ".display-name")), Material.getMaterial(plugin.getConfig().getString("inventory.menu." + section + ".material.name").toUpperCase()), lore, (short) plugin.getConfig().getInt("inventory.menu." + section + ".material.durability"));
            inventory.setItem(plugin.getConfig().getInt("inventory.menu." + section + ".slot"), item);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private Player getPlayer() {
        return this.player;
    }
}
