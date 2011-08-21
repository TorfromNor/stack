package lut.wg.stack;


import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.blocks.ItemType;

public class Stack extends JavaPlugin {
	public static Stack plugin;
	public final Logger logger = Logger.getLogger("Minecraft");	
	
	
	@Override 
	public void onDisable(){
			this.logger.info("Stack disabled");
		}
	@Override	
	public void onEnable(){
			PluginDescriptionFile pdfFile = this.getDescription();
			this.logger.info( pdfFile.getName() + " Version " + pdfFile.getVersion() + "is enabled!");
		}

	public boolean onCommand(CommandSender sender, Command cmd,	String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("stack")
				|| commandLabel.equalsIgnoreCase("Stack"))
		{
			stack((Player) sender);
	}
	return false; 
}

/////////////////////////////
	private void stack(Player player) {
		 

		    boolean ignoreMax = false;//true = stacks all tools too   // boolean ignoreMax = plugin.hasPermission(player, "worldguard.stack.illegitimate");
		    ItemStack[] items = player.getInventory().getContents();
	        int len = items.length;

	        int affected = 0;
	        
	        for (int i = 0; i < len; i++) {
	            ItemStack item = items[i];

	            // Avoid infinite stacks and stacks with durability
	            if (item == null || item.getAmount() <= 0
	                    || (!ignoreMax && itemAllowed(item))) //&& item.getMaxStackSize() == 1)) /// item get max 
	            {
	                continue;
	            }
//	            int max = ignoreMax ? 64 : item.getMaxStackSize();
	            int max = 64;

	            if (item.getAmount() < max) {
	                int needed = max - item.getAmount(); // Number of needed items until max

	                // Find another stack of the same type
	                for (int j = i + 1; j < len; j++) {
	                    ItemStack item2 = items[j];

	                    // Avoid infinite stacks and stacks with durability
	                    if (item2 == null || item2.getAmount() <= 0
	                            || (!ignoreMax && itemAllowed(item))) {
	                        continue;
	                    }

	                    // Same type?
	                    // Blocks store their color in the damage value
	                    if (item2.getTypeId() == item.getTypeId() &&
	                            (!ItemType.usesDamageValue(item.getTypeId())
	                                    || item.getDurability() == item2.getDurability())) {
	                        // This stack won't fit in the parent stack
	                        if (item2.getAmount() > needed) {
	                            item.setAmount(64);
	                            item2.setAmount(item2.getAmount() - needed);
	                            break;
	                        // This stack will
	                        } else {
	                            items[j] = null;
	                            item.setAmount(item.getAmount() + item2.getAmount());
	                            needed = 64 - item.getAmount();
	                        }

	                        affected++;
	                    }
	                }
	            }
	        }

	        if (affected > 0) {
	            player.getInventory().setContents(items);
	        }

	        player.sendMessage(ChatColor.YELLOW + "Items compacted into stacks!");
	    }
	
private boolean itemAllowed(ItemStack item) {
	if((item.getMaxStackSize() == 1)&&(!itemOverRide(item)))
		return true;
	
	return false;
}

private boolean itemOverRide(ItemStack item) {
	// TODO Auto-generated method stub
	if( // Item id for stuff that is wanted to be stacked
			(item.getTypeId() == 297)
		||	(item.getTypeId() == 281)
		||	(item.getTypeId() == 320)
		||	(item.getTypeId() == 321)
		||	(item.getTypeId() == 322)
		||	(item.getTypeId() == 323)
		||	(item.getTypeId() == 324)
		||	(item.getTypeId() == 325)
		||	(item.getTypeId() == 328)
		||	(item.getTypeId() == 330)
		||	(item.getTypeId() == 332)
		||	(item.getTypeId() == 333)
		||	(item.getTypeId() == 345)
		||	(item.getTypeId() == 350)
		||	(item.getTypeId() == 354)
		||	(item.getTypeId() == 355)
		||	(item.getTypeId() == 357)
	){
		return true;
	}
	return false;
}		
	}	
	

