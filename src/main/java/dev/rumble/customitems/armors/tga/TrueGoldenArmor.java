package dev.rumble.customitems.armors.tga;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomArmor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TrueGoldenArmor extends CustomArmor {
    public TrueGoldenArmor(Customitems pl) {
        super(new TrueGoldenHelmet(pl),new TrueGoldenChestplate(pl), new TrueGoldenLeggings(pl), new TrueGoldenBoots(pl));
    }

    @Override
    public void setEffects(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE,Integer.MAX_VALUE,2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE,0));
    }
    @Override
    public void removeEffects(Player player){
        player.removePotionEffect(PotionEffectType.RESISTANCE);
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }

    @Override
    public boolean playerHasEffects(Player user) {
        return user.hasPotionEffect(PotionEffectType.RESISTANCE)
                && user.hasPotionEffect(PotionEffectType.NIGHT_VISION);
    }


}
