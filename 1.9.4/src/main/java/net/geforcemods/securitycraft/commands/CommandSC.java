package net.geforcemods.securitycraft.commands;

import java.util.ArrayList;
import java.util.List;

import net.geforcemods.securitycraft.gui.GuiHandler;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.util.PlayerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class CommandSC extends CommandBase implements ICommand{
	
	private List<String> nicknames;
	
	public CommandSC(){
		this.nicknames = new ArrayList<String>();
		this.nicknames.add("sc");
	}
	
	/**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
    
	public String getCommandName() {
		return "sc";
	}
	
	public List<String> getCommandAliases() {
		return this.nicknames;
	}

	public String getCommandUsage(ICommandSender icommandsender) {
		return I18n.translateToLocal("messages.command.sc.usage");
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender icommandsender) {
		return true;
	}

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0){
			throw new WrongUsageException(I18n.translateToLocal("messages.command.sc.usage"));
		}
		
		if((args[0].matches("connect") || args[0].matches("disconnect") || args[0].matches("contact") || args[0].matches("bug")) && !mod_SecurityCraft.configHandler.isIrcBotEnabled){
			PlayerUtils.sendMessageToPlayer(sender, "IRC", I18n.translateToLocal("messages.irc.botDisabled"), TextFormatting.RED);
			return;
		}

		if(args.length == 1){
			if(args[0].matches("connect")){
				EntityPlayer p = PlayerUtils.getPlayerFromName(sender.getName());
				
				p.openGui(mod_SecurityCraft.instance, GuiHandler.IRC_INFORMATION, p.worldObj, p.chunkCoordX, p.chunkCoordY, p.chunkCoordZ);
				
				try{
					mod_SecurityCraft.instance.getIrcBot(sender.getName()).connectToChannel();
				}catch(Exception e){
					e.printStackTrace();
					PlayerUtils.sendMessageToPlayer(sender, "IRC", I18n.translateToLocal("messages.irc.error"), TextFormatting.RED);
					return;
				}
				
				PlayerUtils.sendMessageToPlayer(sender, "IRC", I18n.translateToLocal("messages.irc.connected"), TextFormatting.GREEN);
			}else if(args[0].matches("disconnect")){
				if(mod_SecurityCraft.instance.getIrcBot(sender.getName()) != null){
					mod_SecurityCraft.instance.getIrcBot(sender.getName()).disconnect();
				}
					
				PlayerUtils.sendMessageToPlayer(sender, "IRC", I18n.translateToLocal("messages.irc.disconnected"), TextFormatting.RED);
			}else if(args[0].matches("help")){
				getCommandSenderAsPlayer(sender).inventory.addItemStackToInventory(new ItemStack(mod_SecurityCraft.scManual));
			}
			else if(args[0].matches("bug"))
				PlayerUtils.sendMessageEndingWithLink(sender, "SecurityCraft", I18n.translateToLocal("messages.bugReport"), "http://goo.gl/forms/kfRpvvQzfl", TextFormatting.GOLD);
			else if(args[0].equals("resume"))
				mod_SecurityCraft.instance.getIrcBot(sender.getName()).setMessageMode(false, sender);
			else if(args[0].matches("contact")){
				if(mod_SecurityCraft.instance.getIrcBot(sender.getName()) != null){
					mod_SecurityCraft.instance.getIrcBot(sender.getName()).setMessageMode(true, sender);
				}else{
					PlayerUtils.sendMessageToPlayer(sender, "IRC", I18n.translateToLocal("messages.irc.notConnected"), TextFormatting.RED);
				}
			}
		}else if(args.length >= 2){
			if(args[0].matches("contact")){
				if(mod_SecurityCraft.instance.getIrcBot(sender.getName()) != null){
					mod_SecurityCraft.instance.getIrcBot(sender.getName()).setMessageMode(true, sender);
				}else{
					PlayerUtils.sendMessageToPlayer(sender, "IRC", I18n.translateToLocal("messages.irc.notConnected"), TextFormatting.RED);
				}
			}
			else if(args[0].matches("bug"))
				PlayerUtils.sendMessageEndingWithLink(sender, "SecurityCraft", I18n.translateToLocal("messages.bugReport"), "http://goo.gl/forms/kfRpvvQzfl", TextFormatting.GOLD);
			else if(args[0].equals("resume"))
				mod_SecurityCraft.instance.getIrcBot(sender.getName()).setMessageMode(false, sender);
		}else{
			throw new WrongUsageException(I18n.translateToLocal("messages.command.sc.usage"));
		}
	}

//	private static String getMessageFromArray(String[] par1String, int index) {
//		String startingString = "";
//		for(int i = index; i < par1String.length; i++){
//			startingString += (i == index ? "" : " ") + par1String[i];
//		}
//		
//		return startingString;
//	}
//
//	private void sendMessageToPlayer(String par1, ICommandSender par2) throws PlayerNotFoundException{
//		ChatComponentText chatcomponenttext = new ChatComponentText(par1);
//		getPlayer(par2, par2.getName()).addChatComponentMessage(chatcomponenttext);
//	}
}