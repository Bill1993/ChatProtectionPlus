package net.willhastings.ChatProtectionPlus;

import java.util.HashMap;

import net.willhastings.ChatProtectionPlus.util.User;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CustomFunction 
{
	private static HashMap<Player, User> users = new HashMap<Player, User>();
	private static boolean chatMuted = false;
	
	public static int capitalCount(String string)
	{
		char ch = (char)string.charAt(0);
		int upperCaseCount = 0;
		for(int i = 0; i < string.length(); i++)
		{
			ch = (char)string.charAt(i);
	        if (Character.isAlphabetic(ch) && Character.isUpperCase(ch))
	        {
	        	upperCaseCount++;
	        }
		}
		return upperCaseCount;
	}	
	public static void addUser(Player player)
	{		
		users.put(player, new User(player));
	}	
	
	public static void removeUser(Player player)
	{		
		users.remove(player);
	}	
	
	public static User getUser(Player player)
	{
		return users.get(player);
	}
	
	public static void NotifyAdministrators(String msg) 
	{
		Player[] player = ChatProtectionPlus.getPlugin().getServer().getOnlinePlayers();
		for(Player i : player) 
		{
			if(hasPermission(i, "cpp.admin")) 
			{
				i.sendMessage(msg);
			}
		}
	}
	
	public static boolean hasPermission(Player player, String perm)
	{
		if (player.isOp()) return true;	
		else if(ChatProtectionPlus.permission != null) 
		{
			if (ChatProtectionPlus.permission.has(player, "*")) return true;
			else if (ChatProtectionPlus.permission.has(player, perm)) return true;
		}
		else
		{
			if (player.hasPermission("*")) return true;
			else if (player.hasPermission(perm)) return true;
		}
		return false;
	}
	
	public static void toggleChat() 
	{
		if(chatMuted) 
		{
			chatMuted = false;
		}
		else
		{
			chatMuted = true;
		}
	}
	
	public static boolean isChatMuted() 
	{
		
		return chatMuted;
	}
	
	public static Player findPlayer(String name, Plugin plugin) 
	{
		name = name.toLowerCase();
		Player[] player = plugin.getServer().getOnlinePlayers();
		for(Player i : player) 
		{
			if(i.getDisplayName().toLowerCase().contains(name)) 
			{
				return i;
			}
		}
		return null;
	}
}
