package dev.rumble.customitems.armors.tga;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class TrueGoldenChestplate extends CustomItem {

    public TrueGoldenChestplate(Customitems plugin) {
        super(11002, plugin);
    }

    @Override
    public ItemStack getItem(){
        ItemStack item = new ItemStack(Material.GOLDEN_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null){
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("El verdadedo poder de la pechera de oro");
            lore.add("Se dice que su portador obtiene efectos OP");
            lore.add("Al tener la armadura completa");
            meta.setDisplayName("§6§lTrue golden chestplate");
            meta.setLore(lore);
            meta.addEnchant(Enchantment.UNBREAKING,5,true);
            meta.addEnchant(Enchantment.MENDING,1,true);
            item.setItemMeta(meta);
        }
        return item;
    }


    @Override
    public void registerRecipe(Plugin plugin){

        NamespacedKey key = new NamespacedKey(plugin, "true_golden_chestplate");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                "G G",
                "GGG",
                "GGG"
        );

        recipe.setIngredient('G', Material.GOLD_BLOCK);

        Bukkit.addRecipe(recipe);

    }
    @Override
    public void removeRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "true_golden_chestplate");
        Bukkit.removeRecipe(key);
    }
}
