package dev.rumble.customitems.stereotype;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface CustomSword{
    void setEffects(Player user);
    void removeEffects(Player user);
    void onDamage(Player damager, LivingEntity entity);
}
