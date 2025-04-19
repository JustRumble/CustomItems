package dev.rumble.customitems;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillsManager {
    private final Map<UUID, Map<Integer, Long>> cooldowns = new HashMap<>();
    private final Map<UUID, Map<Integer, Boolean>> skills = new HashMap<>();
    public void setCooldown(UUID playerId, Integer customModelDataId, long cooldownTime) {
        cooldowns.putIfAbsent(playerId, new HashMap<>());
        cooldowns.get(playerId).put(customModelDataId, System.currentTimeMillis() + cooldownTime);
    }
    public boolean hasActiveSkill(UUID playerId, Integer customModelDataId){
        skills.putIfAbsent(playerId, new HashMap<>());
        return skills.get(playerId).getOrDefault(customModelDataId,Boolean.FALSE);
    }
    public boolean hasAnyActiveSkill(UUID playerId) {
        skills.putIfAbsent(playerId, new HashMap<>());
        return skills.get(playerId).values().stream().anyMatch(active -> active);
    }
    public void setActiveSkill(UUID playerId, Integer customModelDataId, Boolean state){
        skills.putIfAbsent(playerId, new HashMap<>());
        skills.get(playerId).put(customModelDataId,state);
    }
    public boolean isOnCooldown(UUID playerId, Integer customModelDataId) {
        return cooldowns.containsKey(playerId) &&
                cooldowns.get(playerId).containsKey(customModelDataId) &&
                cooldowns.get(playerId).get(customModelDataId) > System.currentTimeMillis();
    }

    public long getRemainingTime(UUID playerId, Integer customModelDataId) {
        if (!isOnCooldown(playerId, customModelDataId)) return 0;
        return (cooldowns.get(playerId).get(customModelDataId) - System.currentTimeMillis()) / 1000;
    }
}
