/*
*
* This class was made by HyChrod
* All rights reserved, 2017
*
*/
package de.HyChrod.Friends.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.HyChrod.Friends.FileManager;
import de.HyChrod.Friends.Friends;
import de.HyChrod.Friends.Listeners.BlockedEditInventoryListener;
import de.HyChrod.Friends.Listeners.EditInventoryListener;
import de.HyChrod.Friends.Listeners.RequestEditInventoryListener;

public class InventoryBuilder {

	public static Inventory INVENTORY(Friends plugin, Player p, InventoryTypes type, boolean open) {
		return new InventoryPage(plugin, p, 0, new PlayerUtilities(p), type).open(open);
	}

	public static Inventory OPTIONS_INVENTORY(Player p, boolean open) {
		Inventory inv = Bukkit.createInventory(null,
				FileManager.ConfigCfg.getInt("Friends.GUI.OptionsInv.InventorySize"),
				ChatColor.translateAlternateColorCodes('&',
						FileManager.ConfigCfg.getString("Friends.GUI.OptionsInv.Title")));

		PlayerUtilities pu = new PlayerUtilities(p);
		for (String placeholder : FileManager.ConfigCfg
				.getStringList("Friends.GUI.OptionsInv.PlaceholderItems.InventorySlots")) {
			inv.setItem(Integer.valueOf(placeholder) - 1, ItemStacks.OPTIONS_PLACEHOLDER.getItem());
		}
		inv.setItem(ItemStacks.OPTIONS_BACK.getInvSlot() - 1, ItemStacks.OPTIONS_BACK.getItem());
		inv.setItem(ItemStacks.OPTIONS_CHAT.getInvSlot() - 1, ItemStacks.OPTIONS_CHAT.getItem());
		inv.setItem(ItemStacks.OPTIONS_REQUESTS.getInvSlot() - 1, ItemStacks.OPTIONS_REQUESTS.getItem());
		if (FileManager.ConfigCfg.getBoolean("Friends.FriendChat.FriendMSG")) {
			inv.setItem(ItemStacks.OPTIONS_PRIVATEMESSAGES.getInvSlot() - 1,
					ItemStacks.OPTIONS_PRIVATEMESSAGES.getItem());
			inv.setItem(FileManager.ConfigCfg.getInt("Friends.GUI.OptionsInv.OptionPrivateMessages.ButtonInventorySlot")
					- 1, new UtilitieItems().OPTIONSBUTTON(pu.get(3, false), "option_noMsg", "�d"));
		}
		if(FileManager.ConfigCfg.getBoolean("Friends.GUI.OptionsInv.StatusItem.Enable")) {
			List<String> preLore = new ArrayList<>(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "//"+FileManager.ConfigCfg.getString("Friends.GUI.OptionsInv.StatusItem.NoStatusLore")).split("//")));
			if(pu.getStatus() != null && pu.getStatus().length() >= 1) {
				preLore = splitStatus(pu.getStatus());
			}		
			inv.setItem(ItemStacks.OPTIONS_STATUSITEM.getInvSlot()-1, ItemStacks.getItem(ItemStacks.OPTIONS_STATUSITEM.getName(),
					preLore, ItemStacks.OPTIONS_STATUSITEM.getItemID(), 1));
		}
		if (FileManager.ConfigCfg.getBoolean("Friends.PartySystem.Enable")) {
			inv.setItem(ItemStacks.OPTIONS_PARTYINVITES.getInvSlot() - 1, ItemStacks.OPTIONS_PARTYINVITES.getItem());
			inv.setItem(FileManager.ConfigCfg.getInt("Friends.GUI.OptionsInv.OptionsPartyInvites.ButtonInventorySlot") - 1, 
					new UtilitieItems().OPTIONSBUTTON(pu.get(3, false), "option_noParty", "�e"));
		}
		if (FileManager.ConfigCfg.getBoolean("Friends.Options.EnableJumping")) {
			inv.setItem(ItemStacks.OPTIONS_JUMPING.getInvSlot() - 1, ItemStacks.OPTIONS_JUMPING.getItem());
			inv.setItem(FileManager.ConfigCfg.getInt("Friends.GUI.OptionsInv.OptionsJumping.ButtonInventorySlot") - 1,
					new UtilitieItems().OPTIONSBUTTON(pu.get(3, false), "option_noJumping", "�c"));
		}

		inv.setItem(FileManager.ConfigCfg.getInt("Friends.GUI.OptionsInv.OptionsRequestsItems.ButtonInventorySlot") - 1,
				new UtilitieItems().OPTIONSBUTTON(pu.get(3, false), "option_noRequests", "�a"));
		inv.setItem(FileManager.ConfigCfg.getInt("Friends.GUI.OptionsInv.OptionsMessagesItems.ButtonInventorySlot") - 1,
				new UtilitieItems().OPTIONSBUTTON(pu.get(3, false), "option_noChat", "�b"));

		if (open)
			p.openInventory(inv);
		return inv;
	}

	public static Inventory EDIT_INVENTORY(Player p, boolean open) {
		Inventory inv = Bukkit.createInventory(null,
				FileManager.ConfigCfg.getInt("Friends.GUI.FriendEditInv.InventorySize"),
				ChatColor.translateAlternateColorCodes('&',
						FileManager.ConfigCfg.getString("Friends.GUI.FriendEditInv.Title").replace("%FRIEND%",
								EditInventoryListener.editing.get(p).getName())));

		for (String placeholder : FileManager.ConfigCfg
				.getStringList("Friends.GUI.FriendEditInv.PlaceholderItems.InventorySlots")) {
			inv.setItem(Integer.valueOf(placeholder) - 1, ItemStacks.EDIT_PLACEHOLDER.getItem());
		}

		inv.setItem(ItemStacks.EDIT_REMOVE.getInvSlot() - 1, ItemStacks.EDIT_REMOVE.getItem());
		if (FileManager.ConfigCfg.getBoolean("Friends.Options.EnableJumping")) {
			inv.setItem(ItemStacks.EDIT_JUMP.getInvSlot() - 1, ItemStacks.EDIT_JUMP.getItem());
		}
		if(FileManager.ConfigCfg.getBoolean("Friends.PartySystem.Enable")) {
			inv.setItem(ItemStacks.EDIT_PARTY.getInvSlot() - 1, ItemStacks.EDIT_PARTY.getItem());
		}
		inv.setItem(ItemStacks.EDIT_BACK.getInvSlot() - 1, ItemStacks.EDIT_BACK.getItem());
		if (open)
			p.openInventory(inv);
		return inv;
	}

	public static Inventory REMOVE_VERIFICATION_INVENTORY(Player p, boolean open) {
		Inventory inv = Bukkit.createInventory(null,
				FileManager.ConfigCfg.getInt("Friends.GUI.RemoveVerificationInv.InventorySize"),
				ChatColor.translateAlternateColorCodes('&',
						FileManager.ConfigCfg.getString("Friends.GUI.RemoveVerificationInv.Title")));

		for (String placeholder : FileManager.ConfigCfg
				.getStringList("Friends.GUI.RemoveVerificationInv.PlaceholderItem.ItemID")) {
			inv.setItem(Integer.valueOf(placeholder) - 1, ItemStacks.REMOVEVERIFICATION_PLACEHOLDER.getItem());
		}
		inv.setItem(ItemStacks.REMOVEVERIFICATION_CANCLE.getInvSlot() - 1,
				ItemStacks.REMOVEVERIFICATION_CANCLE.getItem());
		inv.setItem(ItemStacks.REMOVEVERIFICATION_CONFIRM.getInvSlot() - 1,
				ItemStacks.REMOVEVERIFICATION_CONFIRM.getItem());
		if (open)
			p.openInventory(inv);
		return inv;
	}

	public static Inventory REQUESTEDIT_INVENTORY(Player p, boolean open) {
		Inventory inv = Bukkit.createInventory(null,
				FileManager.ConfigCfg.getInt("Friends.GUI.RequestEditInv.InventorySize"),
				ChatColor.translateAlternateColorCodes('&',
						FileManager.ConfigCfg.getString("Friends.GUI.RequestEditInv.Title").replace("%PLAYER%",
								RequestEditInventoryListener.editing.get(p).getName())));

		for (String placeholder : FileManager.ConfigCfg.getStringList("Friends.GUI.RequestEditInv.PlaceholderItems.InventorySlots")) {
			inv.setItem(Integer.valueOf(placeholder) - 1, ItemStacks.REQUESTS_EDIT_PLACEHOLDER.getItem());
		}
		inv.setItem(ItemStacks.REQUEST_EDIT_ACCEPT.getInvSlot() - 1, ItemStacks.REQUEST_EDIT_ACCEPT.getItem());
		inv.setItem(ItemStacks.REQUEST_EDIT_DENY.getInvSlot() - 1, ItemStacks.REQUEST_EDIT_DENY.getItem());
		inv.setItem(ItemStacks.REQUEST_EDIT_BLOCK.getInvSlot() - 1, ItemStacks.REQUEST_EDIT_BLOCK.getItem());
		inv.setItem(ItemStacks.REQUEST_EDIT_BACK.getInvSlot() - 1, ItemStacks.REQUEST_EDIT_BACK.getItem());
		if (open)
			p.openInventory(inv);
		return inv;
	}

	public static Inventory BLOCKEDEDIT_INVENOTRY(Player p, boolean open) {
		Inventory inv = Bukkit.createInventory(null,
				FileManager.ConfigCfg.getInt("Friends.GUI.BlockedEditInv.InventorySize"),
				ChatColor.translateAlternateColorCodes('&',
						FileManager.ConfigCfg.getString("Friends.GUI.BlockedEditInv.Title").replace("%PLAYER%",
								BlockedEditInventoryListener.editing.get(p).getName())));

		for (String placeholder : FileManager.ConfigCfg
				.getStringList("Friends.GUI.BlockedEditInv.PlaceholderItem.InventorySlots")) {
			inv.setItem(Integer.valueOf(placeholder) - 1, ItemStacks.BLOCKED_EDIT_PLACEHOLDER.getItem());
		}
		inv.setItem(ItemStacks.BLOCKED_EDIT_UNBLOCK.getInvSlot() - 1, ItemStacks.BLOCKED_EDIT_UNBLOCK.getItem());
		inv.setItem(ItemStacks.BLOCKED_EDIT_BACK.getInvSlot() - 1, ItemStacks.BLOCKED_EDIT_BACK.getItem());
		if (open)
			p.openInventory(inv);
		return inv;
	}
	
	private static List<String> splitStatus(String s) {
		List<String> splitted = new ArrayList<>();
		splitted.add("");
		String substring = "�e�o''";
		int counter = 0;
		for(int i = 0; i < s.length(); i++) {
			substring = substring + s.charAt(i);
			counter++;
			if(counter >= 30 && !Character.isAlphabetic(s.charAt(i)) || counter >= 45) {
				counter = 0;
				splitted.add(substring);
				substring = "�e";
			}
		}
		splitted.add(substring + "''");
		return splitted;
	}

	public static void openInv(final Player p, final Inventory inv) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Friends.getInstance(), new Runnable() {

			@Override
			public void run() {
				p.closeInventory();
				p.openInventory(inv);
			}
		}, 2);
	}

}
