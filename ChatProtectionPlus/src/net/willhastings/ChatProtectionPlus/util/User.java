package net.willhastings.ChatProtectionPlus.util;

public class User 
{
	String prevMessage;
	int chatInfractions, commandInfractions;
	Long prevMessageTime, prevCommandTime;

	public User()
	{
		this.setPrevMessage("");
		this.setChatInfractions(0);
		this.setCommandInfractions(0);
		this.setPrevMessageTime(System.currentTimeMillis());
		this.setPrevCommandTime(System.currentTimeMillis());
	}
	
	public void setChatInfractions(int amt)
	{
		chatInfractions = amt;
	}
	
	public void giveChatInfraction(int amt)
	{
		chatInfractions += amt;
	}
	
	public int getChatInfactions()
	{
		return chatInfractions;
	}
	
	public void setCommandInfractions(int amt)
	{
		commandInfractions = amt;
	}
	
	public void giveCommandInfractions(int amt)
	{
		commandInfractions += amt;
	}
	
	public int getCommandInfactions() 
	{
		return commandInfractions;
	}
	
	public void setPrevMessage(String message)
	{
		prevMessage = message;
	}
	
	public String getPrevMessage()
	{
		return prevMessage;
	}
	
	public void setPrevMessageTime(Long time)
	{
		prevMessageTime = time;
	}
	
	public Long getPrevMessageTime()
	{
		return prevMessageTime;
	}
	
	public void setPrevCommandTime(Long time)
	{
		prevCommandTime = time;
	}
	
	public Long getPrevCommandTime()
	{
		return prevCommandTime;
	}
}
