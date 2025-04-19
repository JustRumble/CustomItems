package dev.rumble.customitems.swords;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomSword;
import dev.rumble.customitems.stereotype.SpecialCustomItem;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeutralSword extends SpecialCustomItem implements CustomSword {

    public NeutralSword(Customitems plugin) {
        super(10004, plugin);
    }

    @Override
    public ItemStack getItem(){
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null){
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("§6§l--------------------------");
            lore.add("§r§fHibrido de las dos espadas OP");
            lore.add("Además, esta espada tiene");
            lore.add("poderes adicionales");
            lore.add("§c§l--------------------------");
            meta.setDisplayName("§f§lNeutral sword");
            meta.setLore(lore);
            sword.setItemMeta(meta);
        }
        return sword;
    }
    @Override
    public void registerRecipe(Plugin plugin){

        NamespacedKey key = new NamespacedKey(plugin, "neutral_sword");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                "GAW",
                "GAR",
                "GAW"
        );
        recipe.setIngredient('A', Material.AMETHYST_SHARD);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('W', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('R', Material.REDSTONE_BLOCK);

        Bukkit.addRecipe(recipe);

    }
    @Override
    public void removeRecipe(Plugin plugin){
        NamespacedKey key = new NamespacedKey(plugin, "neutral_sword");
        Bukkit.removeRecipe(key);
    }

    @Override
    public void onDamage(Player damager, LivingEntity entity) {
        doLifeSteal(damager, entity);
        doLightningStrike(entity);
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
        user.sendMessage("§eHabilidad especial §aactivada.\n§eEfectos potenciados + fuerza 2 y 150% de vida robada al pegarle a una entidad por 10 segundos!");
        user.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE,4));
        user.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE,3));
        user.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE,1));

        new BukkitRunnable() {
            @Override
            public void run() {
                getPlugin().skillsManager.setActiveSkill(user.getUniqueId(),getCustomModelDataId(),Boolean.FALSE);
                getPlugin().itemManager.removeAllSwordEffects(user);
                if ((getPlugin().itemManager.getItemInstance(user) instanceof NeutralSword))    setEffects(user);
                user.sendMessage("§eLa habilidad especial de §f§lNeutral sword§e ha §cterminado.");
            }
        }.runTaskLater(getPlugin(), 200L);
    }


    @Override
    public void setEffects(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE,2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE,1));
    }
    @Override
    public void removeEffects(Player player){
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.JUMP_BOOST);
    }
    private void doLifeSteal(Player damager, LivingEntity entity) {
        if (damager == null || entity == null) return;

        double damage = entity.getLastDamage();
        if (damage <= 0) return;
        double multipler = getPlugin().skillsManager.hasActiveSkill(damager.getUniqueId(),getCustomModelDataId()) ? 1.5 : 0.5;
        double newHealth = Math.min(damager.getHealth() + (damage*multipler), damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        damager.setHealth(newHealth);
    }
    private void doLightningStrike(Entity e){
        Random random = new Random();
        if (random.nextInt(100) < 19){
            if (e instanceof Projectile p && p.getShooter() instanceof LivingEntity) e = (LivingEntity) p.getShooter();

            World world = e.getWorld();
            world.strikeLightning(e.getLocation());
        }
    }

}
