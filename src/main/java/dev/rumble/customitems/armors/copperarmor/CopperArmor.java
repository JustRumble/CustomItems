package dev.rumble.customitems.armors.copperarmor;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomArmor;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class CopperArmor extends CustomArmor {
    public CopperArmor(Customitems pl) {
        super(new CopperArmorHelmet(pl), new CopperArmorChestplate(pl), new CopperArmorLeggings(pl), new CopperArmorBoots(pl));
    }

    @Override
    public void setEffects(Player user) {
        user.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE,0));
        double currentMaxHealth = user.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        user.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(currentMaxHealth + 10);
        user.setHealth(Math.min(user.getHealth(), user.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
    }

    @Override
    public void removeEffects(Player user) {
        user.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        user.setHealth(Math.min(user.getHealth(), 20));
        user.removePotionEffect(PotionEffectType.RESISTANCE);
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Random random = new Random();
        if (random.nextInt(100) < 24) { // 25%
            var entity = event.getDamager();
            if (entity instanceof Projectile p && p.getShooter() instanceof LivingEntity) entity = (LivingEntity) p.getShooter();
            else if (!(entity instanceof LivingEntity)) return; // avoid lightning strike loop lol

            World world = player.getWorld();
            world.strikeLightning(entity.getLocation());


        }
    }

    @Override
    public boolean playerHasEffects(Player user) {
        return user.hasPotionEffect(PotionEffectType.RESISTANCE);
    }
}
