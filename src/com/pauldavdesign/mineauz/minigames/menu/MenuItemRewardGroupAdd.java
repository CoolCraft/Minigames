package com.pauldavdesign.mineauz.minigames.menu;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.pauldavdesign.mineauz.minigames.MinigamePlayer;
import com.pauldavdesign.mineauz.minigames.minigame.reward.RewardGroup;
import com.pauldavdesign.mineauz.minigames.minigame.reward.RewardRarity;
import com.pauldavdesign.mineauz.minigames.minigame.reward.Rewards;

public class MenuItemRewardGroupAdd extends MenuItem{
	
	private Rewards rewards;

	public MenuItemRewardGroupAdd(String name, Material displayItem, Rewards rewards) {
		super(name, displayItem);
		this.rewards = rewards;
	}

	public MenuItemRewardGroupAdd(String name, List<String> description, Material displayItem, Rewards rewards) {
		super(name, description, displayItem);
		this.rewards = rewards;
	}
	
	@Override
	public ItemStack onClick(){
		MinigamePlayer ply = getContainer().getViewer();
		ply.setNoClose(true);
		ply.getPlayer().closeInventory();
		ply.sendMessage("Enter reward group name into chat, the menu will automatically reopen in 30s if nothing is entered.", null);
		ply.setManualEntry(this);

		getContainer().startReopenTimer(30);
		return null;
	}
	
	@Override
	public void checkValidEntry(String entry){
		entry = entry.replace(" ", "_");
		for(RewardGroup group : rewards.getGroups()){
			if(group.getName().equals(entry)){
				getContainer().getViewer().sendMessage("A reward group already exists by the name \"" + entry + "\"!", "error");
				getContainer().cancelReopenTimer();
				getContainer().displayMenu(getContainer().getViewer());
				return;
			}
		}
		
		RewardGroup group = rewards.addGroup(entry, RewardRarity.NORMAL);
		
		MenuItemRewardGroup mrg = new MenuItemRewardGroup(entry + " Group", Material.CHEST, group, rewards);
		getContainer().addItem(mrg);
		
		getContainer().cancelReopenTimer();
		getContainer().displayMenu(getContainer().getViewer());
	}

}
