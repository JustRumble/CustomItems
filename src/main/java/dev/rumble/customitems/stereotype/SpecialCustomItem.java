package dev.rumble.customitems.stereotype;

import dev.rumble.customitems.Customitems;
import org.bukkit.entity.Player;


public abstract class SpecialCustomItem extends CustomItem{

    protected SpecialCustomItem(int customModelDataId, Customitems plugin) {
        super(customModelDataId, plugin);
    }
    public abstract void onSpecialSkill(Player user);

    @Override
    public boolean itemHasSpecialSkills() {
        return true;
    }
}
