package dev.rumble.customitems.stereotype;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public abstract class CustomArmor {
    protected final CustomItem helmet;
    protected final CustomItem chestplate;
    protected final CustomItem leggings;
    protected final CustomItem boots;

    protected CustomArmor(CustomItem helmet, CustomItem chestplate, CustomItem leggings, CustomItem boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }


    public boolean hasFullCustomArmor(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        return isCustomArmor(helmet, this.helmet.getCustomModelDataId() ) &&
                isCustomArmor(chestplate, this.chestplate.getCustomModelDataId()) &&
                isCustomArmor(leggings, this.leggings.getCustomModelDataId()) &&
                isCustomArmor(boots, this.boots.getCustomModelDataId());
    }


    public boolean isCustomArmor(ItemStack item, int expectedModelData) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasCustomModelData() && meta.getCustomModelData() == expectedModelData;
    }


    public void registerRecipes(Plugin plugin) {
        helmet.registerRecipe(plugin);
        chestplate.registerRecipe(plugin);
        leggings.registerRecipe(plugin);
        boots.registerRecipe(plugin);
    }

    public void removeRecipes(Plugin plugin) {
        helmet.removeRecipe(plugin);
        chestplate.removeRecipe(plugin);
        leggings.removeRecipe(plugin);
        boots.removeRecipe(plugin);
    }
    public abstract void setEffects(Player user);
    public abstract void removeEffects(Player user);
    public void onDamage(EntityDamageByEntityEvent event){
        // No implemented
    }
    public abstract boolean playerHasEffects(Player user);
}
