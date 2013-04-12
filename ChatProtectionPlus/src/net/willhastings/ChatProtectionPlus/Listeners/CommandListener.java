package net.willhastings.ChatProtectionPlus.Listeners;

import net.willhastings.ChatProtectionPlus.ChatProtectionPlus;
import net.willhastings.ChatProtectionPlus.CustomFunction;
import net.willhastings.ChatProtectionPlus.util.Config;
import net.willhastings.ChatProtectionPlus.util.MessageHandler;
import net.willhastings.ChatProtectionPlus.util.User;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener 
{
	private MessageHandler messageHandler = ChatProtectionPlus.messageHandler;
	
	public CommandListener(ChatProtectionPlus plgin) 
	{
		plgin.getServer().getPluginManager().registerEvents(this, plgin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) 
	{
		Player player = event.getPlayer();
		
		if(event.isCancelled()) return;
		
		if(!Config.USE_ANTICAPS && !Config.USE_ANTICHAT_SPAM) return;
		else if(CustomFunction.hasPermission(player, "cpp.ignore.commandspam") || CustomFunction.hasPermission(player, "cpp.ignore.*"));
		
		if(!player.isOnline()) return;
		
		User user = CustomFunction.getUser(player);
		
		if(Config.USE_ANTICOMMAND_SPAM) 
		{
			Long currTime = System.currentTimeMillis();
			Long timeDif = currTime - user.getPrevCommandTime();
			if(timeDif < Config.MILIS_BETWEEN_MESSAGES_ALLOWED) 
			{
				if(Config.NOTIFY_USER) 
				{
					event.getPlayer().sendMessage(messageHandler.getFormatedMessage("user.spam.command", true, user.getCommandInfactions(), Config.MAX_ALLOWED_COMMANDSPAM_INFRACTION));
					if(Config.NOTIFY_ADMIN) 
					{
						CustomFunction.NotifyAdministrators(messageHandler.getFormatedMessage("admin.spam.command", true, player.getDisplayName(), timeDif));
					}
				}
				if(user.getCommandInfactions() >= Config.MAX_ALLOWED_COMMANDSPAM_INFRACTION) 
				{
					if(Config.BAN_FOR_COMMAND_SPAMING) 
					{
						Bukkit.broadcastMessage(messageHandler.getFormatedMessage("spam.ban.command", true, player.getDisplayName()));
						Bukkit.banIP(player.getAddress().getAddress().getHostAddress());
						player.kickPlayer(messageHandler.getMessage("user.ban.command", false));
						return;
					} 
					else 
					{
						Bukkit.broadcastMessage(messageHandler.getFormatedMessage("spam.kick.command", true, player.getDisplayName()));
						player.kickPlayer(messageHandler.getMessage("user.kick.command", false));
						return;
					}
				}
				user.giveChatInfraction(1);
				event.setCancelled(true);
				return;
			} 
			else 
			{
				user.setPrevCommandTime(currTime);
			}
		}
	}

}
