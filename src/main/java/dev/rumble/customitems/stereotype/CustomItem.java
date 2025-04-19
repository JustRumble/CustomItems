package dev.rumble.customitems.stereotype;

import dev.rumble.customitems.Customitems;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class CustomItem{
    private final int customModelDataId;
    private final Customitems plugin;
    protected CustomItem(int customModelDataId, Customitems plugin){
        this.customModelDataId = customModelDataId;
        this.plugin = plugin;
    }
    public int getCustomModelDataId(){
        return this.customModelDataId;
    }
    public boolean itemHasSpecialSkills(){
        return false;}
    protected Customitems getPlugin() {return this.plugin;}
    public abstract ItemStack getItem();
    public abstract void registerRecipe(Plugin plugin);
    public abstract void removeRecipe(Plugin plugin);
}