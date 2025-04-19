package dev.rumble.customitems.armors.copperarmor;

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

public class CopperArmorBoots extends CustomItem {
    public CopperArmorBoots(Customitems plugin) {
        super(11012,plugin);
    }

    public ItemStack getItem(){
        ItemStack item = new ItemStack(Material.IRON_BOOTS);
        ItemMeta meta = item.getItemMeta();
        if (meta != null){
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("Unas botas de cobre");
            lore.add("Se dice que su portador obtiene efectos OP");
            lore.add("Al tener la armadura completa");
            meta.setDisplayName("§6Copper boots");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void registerRecipe(Plugin plugin){

        NamespacedKey key = new NamespacedKey(plugin, "copper_boots");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                "C C",
                "C C",
                "   "
        );

        recipe.setIngredient('C', Material.COPPER_INGOT);


        Bukkit.addRecipe(recipe);

    }
    @Override
    public void removeRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "copper_boots");
        Bukkit.removeRecipe(key);
    }
}
