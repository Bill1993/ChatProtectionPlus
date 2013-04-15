package net.willhastings.ChatProtectionPlus;

import java.io.IOException;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;
import net.willhastings.ChatProtectionPlus.Listeners.ChatListener;
import net.willhastings.ChatProtectionPlus.Listeners.CommandListener;
import net.willhastings.ChatProtectionPlus.Listeners.JoinLeaveListener;
import net.willhastings.ChatProtectionPlus.util.Config;
import net.willhastings.ChatProtectionPlus.util.MessageHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

public class ChatProtectionPlus extends JavaPlugin
{
	private static Logger log = Logger.getLogger("Minecraft");
	
	public static JoinLeaveListener joinleavelistener = null;
	public static ChatListener chatlistener = null;
	public static CommandListener commandlistener = null;
	
	public static Permission permission = null;

	private static ChatProtectionPlus plugin;
	
	public static MessageHandler messageHandler;
	
	public void onEnable() 
	{
		plugin = this;
		
		log.info("[ChatProtection+] Loading configuration file!");
		Config.loadConfig(this);
		
		if(this.getServer().getPluginManager().getPlugin("Vault") != null) setupPermissions();
		else log.info("[ChatProtection+] is using Bukkit perm instead of [Vault]");
		
		log.info("[ChatProtection+] Loading messages from messages.properties file!");
		messageHandler = new MessageHandler(this);
		
		log.info("[ChatProtection+] Loading listeners...");
		joinleavelistener = new JoinLeaveListener(this);
		log.info("[ChatProtection+] Player Join/Leave Listener loaded!");
		chatlistener = new ChatListener(this);
		log.info("[ChatProtection+] Chat Listener loaded!");
		commandlistener = new CommandListener(this);
		log.info("[ChatProtection+] Command Listener loaded!");
		
		getCommand("cpp").setExecutor(new CPPCommand(this));
		
		Player[] player = this.getServer().getOnlinePlayers();
		for(Player i : player)
		{
			CPPFunction.addUser(i);
		}

		log.info("[ChatProtection+] Attempting to send statis to MCStats!");
		try 
		{
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} 
		catch (IOException e) 
		{
			log.info("[ChatProtection+] Failed to send stats to MCStats, stats will not be send >:C " + e.toString());
		}
		
		log.info("[ChatProtection+] is now Loaded!");
	}
	
	public void onDisable()  
	{
		log.info("[ChatProtection+] is now Un-Loaded!");
	}
	
	public static boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) 
        {
            permission = permissionProvider.getProvider();
            log.info("[ChatProtection+] is using [Vault] for permissions!");
        }
        return (permission != null);
    }
	
	public static ChatProtectionPlus getPlugin()
	{
		return plugin;
	}
}
