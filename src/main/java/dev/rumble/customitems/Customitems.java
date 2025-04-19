package dev.rumble.customitems;

;
import dev.rumble.customitems.armors.copperarmor.CopperArmor;
import dev.rumble.customitems.stereotype.*;


import dev.rumble.customitems.swords.BlessedSword;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;


public final class Customitems extends JavaPlugin implements Listener {
    public final ItemManager itemManager = new ItemManager(this);
    public final  SkillsManager skillsManager = new SkillsManager();
    private BukkitTask checkCustomItems;
    @Override
    public void onEnable() {
        itemManager.registerRecipes();
        getServer().getPluginManager().registerEvents(this, this);
        checkCustomItems = Bukkit.getScheduler().runTaskTimer(this, () ->{
            for (Player p: getServer().getOnlinePlayers()){
                CustomArmor armor = itemManager.getCustomArmorInstance(p);
                if (armor != null && !armor.playerHasEffects(p)){
                    armor.setEffects(p);
                }else if(armor == null) itemManager.removeAllArmorEffects(p);
            }
        },0L,20L);
        getLogger().info("El plugin se inicio correctamente");
    }

    @Override
    public void onDisable() {
        itemManager.removeRecipes();
        checkCustomItems.cancel();
        getLogger().info("Se desactivó el plugin correctamemte");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if (meta == null || event.getEntity().isDead() || !item.hasItemMeta()) return;
            if (!meta.hasCustomModelData()) return;

            int modelData = meta.getCustomModelData();
            LivingEntity entity = (LivingEntity) event.getEntity();

            CustomItem sword = itemManager.getItemInstance(modelData);
            if (sword instanceof CustomSword && !entity.isDead()) {
                ((CustomSword) sword).onDamage(player, entity);
            }

        } else if (event.getEntity() instanceof Player player) {
            CustomArmor armor = itemManager.getCustomArmorInstance(player);
            if (armor != null) {
                armor.onDamage(event);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        switch (event.getCause()){
            case LIGHTNING -> {
                if (event.getEntity() instanceof Player player
                        && itemManager.getCustomArmorInstance(player) instanceof CopperArmor) {
                    event.setCancelled(true);
                }
            }
            case FALL -> {
                if (!(event.getEntity() instanceof Player player)) return;
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();

                if (meta == null || event.getEntity().isDead() || !item.hasItemMeta()) return;
                if (!meta.hasCustomModelData()) return;
                if (
                        itemManager.getItemInstance(meta.getCustomModelData()) instanceof BlessedSword) {
                    event.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

        // Si el slot está vacío, simplemente eliminar efectos y salir
        if (newItem == null || !newItem.hasItemMeta()) {
            if (!skillsManager.hasAnyActiveSkill(player.getUniqueId())) {
                itemManager.removeAllSwordEffects(player);
            }
            return;
        }

        ItemMeta meta = newItem.getItemMeta();

        // Verificamos si tiene CustomModelData antes de obtenerlo
        if (!meta.hasCustomModelData()) {
            if (!skillsManager.hasAnyActiveSkill(player.getUniqueId())) {
                itemManager.removeAllSwordEffects(player);
            }
            return;
        }

        // Obtener el CustomModelData
        int modelData = meta.getCustomModelData();
        CustomItem sword = itemManager.getItemInstance(modelData);

        // Si es una CustomSword, aplicar efectos
        if (sword instanceof CustomSword && !skillsManager.hasAnyActiveSkill(player.getUniqueId())) {
            itemManager.removeAllSwordEffects(player);
            ((CustomSword) sword).setEffects(player);
        } else if (!skillsManager.hasAnyActiveSkill(player.getUniqueId())) {
            itemManager.removeAllSwordEffects(player);
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if (meta == null || !item.hasItemMeta()) return;
        if (!meta.hasCustomModelData()) return;

        CustomItem tool = itemManager.getItemInstance(meta.getCustomModelData());
        if (tool instanceof CustomTool){
            ((CustomTool) tool).onBlockBreak(event);
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        p.sendMessage("§c§l[CustomItems] §r§6Este servidor requiere de Optifine para que puedas ver las texturas custom. Si no lo tienes, instalalo.");
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (meta == null || !item.hasItemMeta()) return;
        if (!meta.hasCustomModelData()) return;
        CustomItem sword = itemManager.getItemInstance(meta.getCustomModelData());
        if (sword != null && sword.itemHasSpecialSkills()) ((SpecialCustomItem)sword).onSpecialSkill(p);
    }
}

