package dev.rumble.customitems;


import dev.rumble.customitems.stereotype.CustomItem;
import dev.rumble.customitems.stereotype.CustomSword;
import org.bukkit.Bukkit;
import  dev.rumble.customitems.stereotype.CustomArmor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EffectsManager {
    private ItemManager itemManager;
    private final Map<UUID, Map<Integer, Boolean>> itemEffects = new HashMap<>();
    private final Map<UUID, Map<CustomArmor, Boolean>> armorEffects = new HashMap<>();
    public EffectsManager(ItemManager itemManager){
        this.itemManager = itemManager;
    }
    public boolean hasActiveEffects(UUID playerId, Integer customModelDataId){
        itemEffects.putIfAbsent(playerId, new HashMap<>());
        return itemEffects.get(playerId).getOrDefault(customModelDataId,Boolean.FALSE);
    }
    public boolean hasAnyActiveEffects(UUID playerId) {
        itemEffects.putIfAbsent(playerId, new HashMap<>());
        return itemEffects.get(playerId).values().stream().anyMatch(active -> active);
    }
    public void setActiveItemEffect(UUID playerId, Integer customModelDataId){
        Player player = Bukkit.getPlayer(playerId);
        itemEffects.putIfAbsent(playerId, new HashMap<>());
        itemEffects.get(playerId).put(customModelDataId,true);
        CustomItem item = itemManager.getItemInstance(customModelDataId);
        if (item instanceof CustomSword){
            ((CustomSword) item).setEffects(player);
        }

    }
    public void setActiveArmorEffect(UUID playerId){
        Player player = Bukkit.getPlayer(playerId);
        armorEffects.putIfAbsent(playerId, new HashMap<>());
        CustomArmor armor = itemManager.getCustomArmorInstance(player);
        if (armor != null){
            armor.setEffects(player);
        }
    }
    public void removeActiveEffect(UUID playerId, Integer customModelDataId){
        Player player = Bukkit.getPlayer(playerId);
        itemEffects.putIfAbsent(playerId, new HashMap<>());
        itemEffects.get(playerId).remove(customModelDataId,false);
        CustomItem item = itemManager.getItemInstance(customModelDataId);
        if (item instanceof CustomSword){
            ((CustomSword) item).removeEffects(player);
        }
    }

}