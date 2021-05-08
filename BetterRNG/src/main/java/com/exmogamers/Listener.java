package com.exmogamers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        ItemStack itemHeld = player.getInventory().getItemInMainHand();
        List<Material> affectedBlocked = new ArrayList<Material>();
        affectedBlocked.add(Material.DIAMOND_ORE);
        affectedBlocked.add(Material.COAL_ORE);
        affectedBlocked.add(Material.EMERALD_ORE);
        affectedBlocked.add(Material.NETHER_QUARTZ_ORE);
        affectedBlocked.add(Material.NETHER_GOLD_ORE);
        affectedBlocked.add(Material.REDSTONE_ORE);
        affectedBlocked.add(Material.LAPIS_ORE);

        if(affectedBlocked.contains(event.getBlock().getType()) && event.isDropItems()){
            if(itemHeld.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                Material materialToDrop = null;
                int multiplier = 1;

                // I know this is an ugly solution, I couldn't find a way to get resulting itemstack from this event
                if(event.getBlock().getType() == Material.COAL_ORE) materialToDrop = Material.COAL;
                if(event.getBlock().getType() == Material.DIAMOND_ORE) materialToDrop = Material.DIAMOND;
                if(event.getBlock().getType() == Material.EMERALD_ORE) materialToDrop = Material.EMERALD;
                if(event.getBlock().getType() == Material.NETHER_QUARTZ_ORE){
                    materialToDrop = Material.QUARTZ;
                }
                if(event.getBlock().getType() == Material.NETHER_GOLD_ORE){
                    materialToDrop = Material.GOLD_NUGGET;
                    multiplier = 6;
                }
                if(event.getBlock().getType() == Material.REDSTONE_ORE){
                    materialToDrop = Material.REDSTONE;
                    multiplier = 6;
                }
                if(event.getBlock().getType() == Material.LAPIS_ORE){
                    materialToDrop = Material.LAPIS_LAZULI;
                    multiplier = 9;
                }


                if(materialToDrop != null) {
                    event.setDropItems(false);
                    ItemStack itemStack = new ItemStack(materialToDrop);
                    if(multiplier != 1){
                        itemStack.setAmount(((itemHeld.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * multiplier)));
                    } else {
                        itemStack.setAmount(1 + ((itemHeld.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS))));

                    }
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), itemStack);
                }
            }
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent event){
        // Looting drops stays the same according to level

        if(event.getEntity().getKiller() instanceof Player) {
            Player player = (Player) event.getEntity().getKiller();
            Inventory inventory = player.getInventory();
            ItemStack itemHeld = player.getInventory().getItemInMainHand();

            for (ItemStack droppedItem : event.getDrops()){
                droppedItem.setAmount(1);
            }

            if(itemHeld.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)){
                for (ItemStack droppedItem : event.getDrops()){
                    if(droppedItem.getType() != Material.BOW) {
                        droppedItem.setAmount(droppedItem.getAmount() + itemHeld.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS));
                    }
                }
            }
        }
    }
}
