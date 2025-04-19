package dev.rumble.customitems.armors.emeraldarmor;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class EmeraldArmorHelmet extends CustomItem {

    public EmeraldArmorHelmet(Customitems plugin) {
        super(11005, plugin);
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.IRON_HELMET);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("Un casco de esmeralda");
            lore.add("Se dice que su portador obtiene efectos OP");
            lore.add("Al tener la armadura completa");
            meta.setDisplayName("§aEmerald helmet");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void registerRecipe(Plugin plugin) {

        NamespacedKey key = new NamespacedKey(plugin, "emerald_helmet");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                "EEE",
                "E E",
                "   "
        );

        recipe.setIngredient('E', Material.EMERALD);
        Bukkit.addRecipe(recipe);

    }

    @Override
    public void removeRecipe(Plugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "emerald_helmet");
        Bukkit.removeRecipe(key);
    }
}
