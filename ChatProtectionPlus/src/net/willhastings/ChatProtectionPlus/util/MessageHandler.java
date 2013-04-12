package net.willhastings.ChatProtectionPlus.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import net.willhastings.ChatProtectionPlus.ChatProtectionPlus;

public class MessageHandler 
{	
	private HashMap<String, String> msg = new HashMap<String, String>();
	private Plugin plugin;
	private String FILE_PATH; 
	private String FILE_NAME;
	
	public MessageHandler()
	{
		try
		{
			plugin = ChatProtectionPlus.getPlugin();
			
			FILE_PATH = plugin.getDataFolder().getPath();
			FILE_NAME = File.separator + "messages.properties";
			
			if(!new File(FILE_PATH).exists())
			{
				new File(FILE_PATH).mkdir();
			}
			
			if(!new File(FILE_PATH + FILE_NAME).exists())
			{
				BufferedWriter out = new BufferedWriter(new FileWriter(FILE_PATH + FILE_NAME));
				out.write("");
				out.close();
			}
			
			BufferedReader in = new BufferedReader(new FileReader(FILE_PATH + FILE_NAME));
			String line;
			while((line = in.readLine()) != null)
			{
				if(!line.equals(""))
				{
					String[] parse = line.split("=");
					msg.put(parse[0], parse[1]);
				}
			}
			in.close();
		}
		catch (Exception e)
		{
			System.out.println("[ChatProtection+] Has failed to load the 'messages.properties' file, and is now disabling itself!");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
	}
	
	public String getMessage(String key, boolean prefix)
	{
		String temp;
		if(msg.containsKey(key))
		{
			temp = ChatColor.translateAlternateColorCodes('&', msg.get(key));
			if(prefix) temp = ChatColor.translateAlternateColorCodes('&', msg.get("server.prefix")) + " " + temp;
		}
		else 
		{
			temp = "Missing message <" + key + "> in messages.properties!";
		}
		return temp;
	}
	
	public String getFormatedMessage(String key, boolean prefix, Object ... params)
	{
		String temp;
		if(msg.containsKey(key))
		{
			temp = ChatColor.translateAlternateColorCodes('&', msg.get(key));
			if(prefix) temp = ChatColor.translateAlternateColorCodes('&', msg.get("server.prefix")) + " " + temp;
			temp = MessageFormat.format(temp, params);
		}
		else 
		{
			temp = "Missing message <" + key + "> in messages.properties!";
		}
		return temp;
	}
	
	public boolean reload()
	{
		// to-do
		return false;
	}
}
