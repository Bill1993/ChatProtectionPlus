package net.willhastings.ChatProtectionPlus.Listeners;

import net.willhastings.ChatProtectionPlus.CPPFunction;
import net.willhastings.ChatProtectionPlus.ChatProtectionPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener 
{
	
	public JoinLeaveListener(ChatProtectionPlus plgin) 
	{
		plgin.getServer().getPluginManager().registerEvents(this, plgin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinEvent(PlayerJoinEvent event) 
	{
		CPPFunction.addUser(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuitEvent(PlayerQuitEvent event) 
	{
		CPPFunction.removeUser(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerKickEvent(PlayerKickEvent event) 
	{
		CPPFunction.removeUser(event.getPlayer());
	}

}
