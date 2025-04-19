package dev.rumble.customitems.swords;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomItem;
import dev.rumble.customitems.stereotype.CustomSword;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class VenomSword extends CustomItem implements CustomSword {
    public VenomSword(Customitems plugin) {
        super(10001, plugin);
    }

    @Override
    public  ItemStack getItem(){
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null){
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("Espada venenosa");
            lore.add("Forjada por los toxicos");
            meta.setDisplayName("§2§lVenom sword");
            meta.setLore(lore);
            sword.setItemMeta(meta);
        }
        return sword;
    }

    @Override
    public void setEffects(Player user) {
        // None
    }

    @Override
    public void removeEffects(Player user) {
        // None
    }

    @Override
    public void registerRecipe(Plugin plugin){

        NamespacedKey key = new NamespacedKey(plugin, "venom_sword");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                " I ",
                "EIE",
                " S "
        );
        recipe.setIngredient('E', Material.SPIDER_EYE);
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);

    }
    @Override
    public void removeRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "venom_sword");
        Bukkit.removeRecipe(key);

    }

    @Override
    public void onDamage(Player damager, LivingEntity entity) {
        entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120,1));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA,120,1));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 140, 0));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 140, 1));
    }

}
