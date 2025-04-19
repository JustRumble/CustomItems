package dev.rumble.customitems.swords;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomItem;
import dev.rumble.customitems.stereotype.CustomSword;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
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

public class CursedSword extends CustomItem implements CustomSword {

    public CursedSword(Customitems plugin) {
        super(10003, plugin);
    }

    @Override
    public ItemStack getItem(){
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null){
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("Espada maldecida por donPAPELUCHO54");
            lore.add("El portador tendra efectos positivos y negativos");
            meta.setDisplayName("§c§lCursed sword");
            meta.setLore(lore);
            sword.setItemMeta(meta);
        }
        return sword;
    }
    @Override
    public void registerRecipe(Plugin plugin){

        NamespacedKey key = new NamespacedKey(plugin, "cursed_sword");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                " R ",
                "RNR",
                " S "
        );
        recipe.setIngredient('R', Material.REDSTONE_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);

    }
    @Override
    public void removeRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "cursed_sword");
        Bukkit.removeRecipe(key);
    }

    @Override
    public void onDamage(Player damager, LivingEntity entity) {
        doLifeSteal(damager,entity);
    }


    @Override
    public void setEffects(Player user){
        user.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE,1));
        user.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, Integer.MAX_VALUE,2));
    }

    @Override
    public void removeEffects(Player user){
        user.removePotionEffect(PotionEffectType.SLOWNESS);
        user.removePotionEffect(PotionEffectType.DARKNESS);
    }
    private void doLifeSteal(Player damager, LivingEntity entity) {
        if (damager == null || entity == null) return;

        double damage = entity.getLastDamage();
        if (damage <= 0) return;

        double newHealth = Math.min(damager.getHealth() + damage, damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        damager.setHealth(newHealth);
    }
}
