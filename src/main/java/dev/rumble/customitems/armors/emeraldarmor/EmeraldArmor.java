package dev.rumble.customitems.armors.emeraldarmor;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomArmor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmeraldArmor extends CustomArmor {

    public EmeraldArmor(Customitems pl) {
        super(new EmeraldArmorHelmet(pl), new EmeraldArmorChestplate(pl), new EmeraldArmorLeggings(pl), new EmeraldArmorBoots(pl));
    }

    @Override
    public void setEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, Integer.MAX_VALUE, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 0));
    }

    @Override
    public void removeEffects(Player player) {
        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.removePotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE);
        player.removePotionEffect(PotionEffectType.RESISTANCE);
    }

    @Override
    public boolean playerHasEffects(Player user) {
        return user.hasPotionEffect(PotionEffectType.RESISTANCE)
                && user.hasPotionEffect(PotionEffectType.REGENERATION)
                && user.hasPotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE);
    }
}
