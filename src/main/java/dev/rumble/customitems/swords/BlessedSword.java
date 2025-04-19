package dev.rumble.customitems.swords;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomSword;
import dev.rumble.customitems.stereotype.SpecialCustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BlessedSword extends SpecialCustomItem implements CustomSword{

    public BlessedSword(Customitems plugin) {
        super(10002, plugin);
    }

    @Override
    public ItemStack getItem(){
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null){
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("Espada bendecida por PyRumble");
            lore.add("Se dice que su portador obtiene más poder");
            meta.setDisplayName("§e§lBlessed sword");
            meta.setLore(lore);
            sword.setItemMeta(meta);
        }
        return sword;
    }
    @Override
    public void registerRecipe(Plugin plugin){

        NamespacedKey key = new NamespacedKey(plugin, "blessed_sword");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                " D ",
                "EGE",
                " S "
        );
        recipe.setIngredient('E', Material.EMERALD_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);

    }
    @Override
    public void removeRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "blessed_sword");
        Bukkit.removeRecipe(key);
    }

    @Override
    public void onDamage(Player damager, LivingEntity entity) {
        // None
    }

    @Override
    public void onSpecialSkill(Player user) {
        if(getPlugin().skillsManager.isOnCooldown(user.getUniqueId(),getCustomModelDataId())){
            long remainingTime = getPlugin().skillsManager.getRemainingTime(user.getUniqueId(),getCustomModelDataId());
            user.playSound(user, Sound.ENTITY_ENDER_DRAGON_HURT,1,1);
            user.sendMessage("§cDebes esperar " + remainingTime + " segundos para usar esta habilidad de nuevo.");
            return;
        }
        getPlugin().skillsManager.setCooldown(user.getUniqueId(),getCustomModelDataId(), 30*1000);
        getPlugin().skillsManager.setActiveSkill(user.getUniqueId(),getCustomModelDataId(),Boolean.TRUE);
        user.sendMessage("§eHabilidad especial §aactivada.\n§eAhora puedes volar por unos segundos!");
        user.setAllowFlight(true);
        user.setFlying(true);


        // Desactivar vuelo después de 3 segundos
        new BukkitRunnable() {
            @Override
            public void run() {
                getPlugin().skillsManager.setActiveSkill(user.getUniqueId(),getCustomModelDataId(),Boolean.FALSE);
                getPlugin().itemManager.removeAllSwordEffects(user);
                if ((getPlugin().itemManager.getItemInstance(user) instanceof BlessedSword)) setEffects(user);
                user.setFlying(false);
                user.setAllowFlight(false);
                user.sendMessage("§eLa habilidad especial de §e§lBlessed sword§e ha §cterminado.");
            }
        }.runTaskLater(getPlugin(), 100L);
    }

    @Override
    public void setEffects(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE,1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE,0));
    }
    @Override
    public void removeEffects(Player player){
        player.removePotionEffect(PotionEffectType.STRENGTH);
        player.removePotionEffect(PotionEffectType.SPEED);
    }

}
