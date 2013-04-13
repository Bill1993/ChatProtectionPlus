package net.willhastings.ChatProtectionPlus;

import net.willhastings.ChatProtectionPlus.util.MessageHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CPPCommand implements CommandExecutor 
{

	private Plugin plugin;
	private MessageHandler messageHandler = ChatProtectionPlus.messageHandler;
	
	public CPPCommand(ChatProtectionPlus chatProtectionPlus) 
	{
		plugin = chatProtectionPlus;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) 
	{
		if(!(sender instanceof Player)) 
		{
			sender.sendMessage("You can not execute this command from console!");
			return true;
		}

		if(!CPPFunction.hasPermission((Player) sender, "cpp.admin")) return false;
		
		if(args.length == 0) 
		{
			sender.sendMessage(messageHandler.getMessage("server.prefix", false) + "/CPP Reload" + ChatColor.YELLOW + "\n- Reload the configuration file.");
			sender.sendMessage(messageHandler.getMessage("server.prefix", false) + "/CPP Chat {Clear|Lock}" + ChatColor.YELLOW + "\n- Clear/Lock the chat.");
			return true;
		}		
		
		try 
		{
			switch(SubCommands.valueOf(args[0].toString().toUpperCase())) 
			{
				case RELOAD: 
				{
					sender.sendMessage(messageHandler.getMessage("server.prefix", false) + "Reloading Configuration File...");
					plugin.reloadConfig();
					sender.sendMessage(messageHandler.getMessage("server.prefix", false) + "Configuration File Reloaded!");
					break;
				}
				case CHAT: 
				{
					if(args.length < 2) 
					{
						sender.sendMessage(messageHandler.getMessage("server.prefix", false) + "{Clear|Lock}");
					} 
					else 
					{
						if(args[1].equalsIgnoreCase("clear")) 
						{
							for(int i = 0; i < 100; i++) 
							{
								Bukkit.broadcastMessage(" ");
								if(i == 97)
								{
									Bukkit.broadcastMessage(ChatColor.DARK_RED + "|----------------------------------------------------|");
									Bukkit.broadcastMessage(ChatColor.AQUA + "*** " + messageHandler.getFormatedMessage("server.chat.lock", true, sender.getName()) + ChatColor.AQUA + " ***");
									Bukkit.broadcastMessage(ChatColor.DARK_RED + "|----------------------------------------------------|");
								}
							}		
						}
						else if(args[1].equalsIgnoreCase("lock")) 
						{
							CPPFunction.toggleChat();
							if(CPPFunction.isChatMuted()) 
							{
								sender.sendMessage(messageHandler.getMessage("server.prefix", false) + "The chat has now been locked!");
								Bukkit.broadcastMessage(ChatColor.DARK_RED + "|----------------------------------------------------|");
								Bukkit.broadcastMessage(ChatColor.AQUA + "*** " + messageHandler.getFormatedMessage("server.chat.unlock", true, sender.getName()) + ChatColor.AQUA + " ***");
								Bukkit.broadcastMessage(ChatColor.DARK_RED + "|----------------------------------------------------|");
							}
							else
							{
								Bukkit.broadcastMessage(ChatColor.DARK_RED + "|----------------------------------------------------|");
								Bukkit.broadcastMessage(ChatColor.AQUA + "*** " + messageHandler.getMessage("server.prefix", false) + "Chat Un-Locked by " + ChatColor.YELLOW + sender.getName() 
										+ ChatColor.WHITE +"!" + ChatColor.AQUA + " ***");
								Bukkit.broadcastMessage(ChatColor.DARK_RED + "|----------------------------------------------------|");
								sender.sendMessage(messageHandler.getMessage("server.prefix", false) + "The chat has now been un-locked!");
							}
						}
					}
					break;
				}
			}
		}
		catch(IllegalArgumentException e) 
		{
			return false;
		}
		return true;
	}
	
	private enum SubCommands 
	{
		RELOAD,
		CHAT
	}

}
