package fr.sysgli.disbotapi.commands;

import java.util.List;

import fr.sysgli.disbotapi.Disbot;
import fr.sysgli.disbotapi.user.DiscordUser;
import fr.sysgli.disbotapi.utils.DisbotUtils;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class PermissionsExecutor extends CommandExecutor {

	public PermissionsExecutor(Disbot disbot) {
		super(disbot);
	}

	@Override
	public boolean onCommand(DiscordUser user, Guild guild, MessageChannel channel, String[] args , List<User> mentionedUsers) {

		if(args.length < 2 || args.length > 3)		
			return false;

		if(mentionedUsers.isEmpty()) {
			channel.sendMessage("Le deuxième argument doit être une mention.");
			return true;
		} else if(mentionedUsers.size() > 1) {
			channel.sendMessage("Une seule personne doit être mentionnée.");
			return true;
		}

		DiscordUser targetUser = getDisbot().getUserManager().getDiscordUser(guild, mentionedUsers.get(0));
		String name = DisbotUtils.getNameForUser(guild, targetUser.getUser());

		if(args[1].replaceFirst("@", "").equals(name)) {
			switch(args[0]) {
			case "add":
				if(args.length < 3)
					return false;
				if(!targetUser.hasPermission(args[2])) {
					targetUser.addPermission(args[2]);
					channel.sendMessage("Permission \"" + args[2] + "\" ajoutée à " + name + ".");
				} else
					channel.sendMessage(name + " possède déjà la permission \"" + args[2] + "\".");
				break;
			case "remove":
				if(args.length < 3)
					return false;
				if(targetUser.hasPermission(args[2])) {
					targetUser.removePermission(args[2]);
					channel.sendMessage("Permission \"" + args[2] + "\" retirée à " + name + ".");
				} else
					channel.sendMessage(name + " ne possède pas la permission \"" + args[2] + "\".");
				break;
			case "clear":
				targetUser.clearPermissions();
				channel.sendMessage("Permission de " + name + " vidées.");
				break;
			default:
				return false;
			}
		}

		return true;
	}

}
