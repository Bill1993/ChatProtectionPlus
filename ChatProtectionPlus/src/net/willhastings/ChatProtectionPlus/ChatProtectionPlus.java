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
	Logger log = Logger.getLogger("Minecraft");
	
	public static JoinLeaveListener joinleavelistener = null;
	public static ChatListener chatlistener = null;
	public static CommandListener commandlistener = null;
	
	public static Permission permission = null;

	private static ChatProtectionPlus plugin;
	
	public static MessageHandler messageHandler;
	
	public void onEnable() 
	{
		plugin = this;
		Config.loadConfig(this);
		messageHandler = new MessageHandler();
		
		joinleavelistener = new JoinLeaveListener(this);
		chatlistener = new ChatListener(this);
		commandlistener = new CommandListener(this);
		getCommand("cpp").setExecutor(new CPPCommand(this));
		
		Player[] player = this.getServer().getOnlinePlayers();
		for(Player i : player)
		{
			CustomFunction.addUser(i);
		}
		
		setupPermissions(); 

		try 
		{
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} 
		catch (IOException e) 
		{
		    // Failed to load metrics
		}
		
		log.info("[ChatProtection+] is now Loaded!");
	}
	
	public void onDisable()  
	{
		log.info("[ChatProtection+] is now Un-Loaded!");
	}
	
	public void onReload() 
	{
		
	}
	
	public static boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) 
        {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	
	public static ChatProtectionPlus getPlugin()
	{
		return plugin;
	}
}
