package net.willhastings.ChatProtectionPlus.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
	public static boolean NOTIFY_USER;
	public static boolean NOTIFY_ADMIN;
	
	public static boolean USE_ANTICAPS;
	public static int ALLOWED_CAPITALS_PERCENT;
	
	public static boolean USE_ANTICHAT_SPAM;
	public static boolean USE_ANTICOMMAND_SPAM;
	
	public static boolean BAN_FOR_CHAT_SPAMING;
	public static boolean BAN_FOR_COMMAND_SPAMING;
	
	public static int MILIS_BETWEEN_MESSAGES_ALLOWED;
	public static int MILIS_BETWEEN_COMMANDS_ALLOWED;
	public static int MAX_ALLOWED_CHATSPAM_INFRACTION;
	public static int MAX_ALLOWED_COMMANDSPAM_INFRACTION;

	public static void loadConfig(Plugin plugin)
	{
		FileConfiguration config = plugin.getConfig();
		
		config.options().copyDefaults(true);
		plugin.saveConfig();
		
		NOTIFY_USER = config.getBoolean("Options.NotifyUser");
		NOTIFY_ADMIN = config.getBoolean("Options.NotifyAdmin");
		
		USE_ANTICAPS = config.getBoolean("Toggle.AntiCaps");
		USE_ANTICHAT_SPAM = config.getBoolean("Toggle.AntiChatSpam");
		USE_ANTICOMMAND_SPAM = config.getBoolean("Toggle.AntiCommandSpam");
		
		ALLOWED_CAPITALS_PERCENT = config.getInt("AntiCaps.AllowedCapitalsPercent");
		
		BAN_FOR_CHAT_SPAMING = config.getBoolean("AntiChatSpam.BanForSpam");
		MILIS_BETWEEN_MESSAGES_ALLOWED = config.getInt("AntiChatSpam.MillisecondsAllowedBetweenMessages");
		MAX_ALLOWED_CHATSPAM_INFRACTION = config.getInt("AntiChatSpam.AllowedSpamInfractions");
		
		BAN_FOR_COMMAND_SPAMING = config.getBoolean("AntiCommandSpam.BanForSpam");
		MILIS_BETWEEN_COMMANDS_ALLOWED = config.getInt("AntiCommandSpam.MillisecondsAllowedBetweenCommands");
		MAX_ALLOWED_COMMANDSPAM_INFRACTION = config.getInt("AntiCommandSpam.AllowedSpamInfractions");
		plugin.saveConfig();
	}
}
