package dev.rumble.customitems.tools;

import dev.rumble.customitems.Customitems;
import dev.rumble.customitems.stereotype.CustomItem;
import dev.rumble.customitems.stereotype.CustomTool;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SuperPickaxe extends CustomItem implements CustomTool {

    public SuperPickaxe(Customitems plugin) {
        super(12001,plugin);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null){
            List<String> lore = new ArrayList<>();
            meta.setCustomModelData(getCustomModelDataId());
            lore.add("Un pico fuera de lo común");
            lore.add("Puede ayudarte a minar más rápido");
            meta.setDisplayName("§b§lSuper pickaxe");
            meta.addEnchant(Enchantment.UNBREAKING,3 ,true);
            meta.addEnchant(Enchantment.MENDING,1,true);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void registerRecipe(Plugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "super_pickaxe");

        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape(
                "DDD",
                " S ",
                " S "
        );
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);
    }

    @Override
    public void removeRecipe(Plugin plugin) {
        Bukkit.removeRecipe(new NamespacedKey(plugin, "super_pickaxe"));
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        mineArea(event.getBlock(),player);
    }
    private void mineArea(Block brokenBlock, Player player) {
        Set<Block> blocksToBreak = new HashSet<>();

        // Obtener la cara del bloque que el jugador golpeó
        RayTraceResult result = player.rayTraceBlocks(5);
        if (result == null || result.getHitBlockFace() == null) return;

        BlockFace face = result.getHitBlockFace();

        if (face == BlockFace.UP || face == BlockFace.DOWN) {
            // Mina en el plano X-Z (horizontal)
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    Block targetBlock = brokenBlock.getRelative(dx, 0, dz); // Usamos brokenBlock en lugar de centerBlock
                    if (isMineable(targetBlock.getType())) {
                        blocksToBreak.add(targetBlock);
                    }
                }
            }
        } else if (face == BlockFace.EAST || face == BlockFace.WEST) {
            // Mina en el plano Y-Z (vertical)
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    Block targetBlock = brokenBlock.getRelative(0, dy, dz);
                    if (isMineable(targetBlock.getType())) {
                        blocksToBreak.add(targetBlock);
                    }
                }
            }
        } else if (face == BlockFace.NORTH || face == BlockFace.SOUTH) {
            // Mina en el plano X-Y (vertical)
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    Block targetBlock = brokenBlock.getRelative(dx, dy, 0);
                    if (isMineable(targetBlock.getType())) {
                        blocksToBreak.add(targetBlock);
                    }
                }
            }
        }

        // Romper los bloques correctamente
        for (Block block : blocksToBreak) {
            block.breakNaturally(player.getInventory().getItemInMainHand());
        }
    }
    private boolean isMineable(Material material){
        return Tag.MINEABLE_PICKAXE.isTagged(material);
    }
}
