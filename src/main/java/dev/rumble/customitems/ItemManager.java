package dev.rumble.customitems;

import dev.rumble.customitems.armors.copperarmor.CopperArmor;
import dev.rumble.customitems.armors.emeraldarmor.EmeraldArmor;
import dev.rumble.customitems.armors.tga.TrueGoldenArmor;
import dev.rumble.customitems.stereotype.CustomArmor;
import dev.rumble.customitems.stereotype.CustomItem;
import dev.rumble.customitems.stereotype.CustomSword;
import dev.rumble.customitems.stereotype.CustomTool;
import dev.rumble.customitems.swords.BlessedSword;
import dev.rumble.customitems.swords.CursedSword;
import dev.rumble.customitems.swords.NeutralSword;
import dev.rumble.customitems.swords.VenomSword;
import dev.rumble.customitems.tools.SuperPickaxe;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ItemManager {
    private final Customitems plugin;
    private final List<CustomItem> customItems;
    private final ConcurrentMap<Integer, CustomItem> itemMap;
    private final List<CustomArmor> customArmors;
    public ItemManager(Customitems plugin) {
        this.plugin = plugin;
        this.customItems = List.copyOf(Arrays.asList(new VenomSword(plugin), new CursedSword(plugin), new BlessedSword(plugin), new SuperPickaxe(plugin), new NeutralSword(plugin)));
        this.customArmors = List.of(new EmeraldArmor(plugin), new TrueGoldenArmor(plugin), new CopperArmor(plugin));
        this.itemMap = new ConcurrentHashMap<>();
        for (CustomItem sword : customItems) {
            itemMap.put(sword.getCustomModelDataId(), sword);
        }
    }

    @Nullable
    public CustomItem getItemInstance(int customModelDataId) {
        return itemMap.get(customModelDataId);
    }
    @Nullable
    public CustomItem getItemInstance(Player user){
        ItemStack item = user.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR || !item.hasItemMeta()) return  null;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasCustomModelData()) return null;
        return getItemInstance(meta.getCustomModelData());

    }
    @Nullable
    public CustomArmor getCustomArmorInstance(Player player){
        for (CustomArmor a : customArmors) {
            if (a.hasFullCustomArmor(player)) {
                return a;
            }
        }
        return  null;
    }
    public void registerRecipes() {
        customItems.forEach(s -> s.registerRecipe(plugin));
        customArmors.forEach(a -> a.registerRecipes(plugin));
    }

    public void removeRecipes() {
        customItems.forEach(s -> s.removeRecipe(plugin));
        customArmors.forEach(a -> a.removeRecipes(plugin));
    }

    public void removeAllSwordEffects(Player user) {
        customItems.forEach(s -> {
            if (s instanceof CustomSword) ((CustomSword) s).removeEffects(user);
        });
    }
    public void removeAllArmorEffects(Player user){
        customArmors.forEach(a -> a.removeEffects(user));
    }

}
