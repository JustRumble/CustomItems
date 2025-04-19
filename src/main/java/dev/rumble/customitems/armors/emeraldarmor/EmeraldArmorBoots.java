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

public class EmeraldArmorBoots extends CustomItem {
    public EmeraldArmorBoots(Customitems plugin) {
        super(11008, plugin);
    }

    public ItemStack getItem(){
        ItemStack item = new ItemStack(Material.IRON_BOOTS);
        ItemMeta meta = item.getItemMeta();
        if (meta != null){
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("Unas botas de esmeralda");
            lore.add("Se dice que su portador obtiene efectos OP");
            lore.add("Al tener la armadura completa");
            meta.setDisplayName("§aEmerald boots");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void registerRecipe(Plugin plugin){

        NamespacedKey key = new NamespacedKey(plugin, "emerald_boots");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                "E E",
                "E E",
                "   "
        );

        recipe.setIngredient('E', Material.EMERALD);


        Bukkit.addRecipe(recipe);

    }
    @Override
    public void removeRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "emerald_boots");
        Bukkit.removeRecipe(key);
    }

}
