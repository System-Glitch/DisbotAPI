package fr.sysgli.disbotapi.events;

import java.util.Collections;

import fr.sysgli.disbotapi.Disbot;
import fr.sysgli.disbotapi.commands.Command;
import fr.sysgli.disbotapi.commands.CommandExecutor;
import fr.sysgli.disbotapi.utils.DisbotUtils;
import fr.sysgli.disbotapi.utils.Logger_;
import fr.sysgli.disbotapi.utils.Logger_.LogStatus;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 * Handle commands and redistribute to executors.
 * @author Jeremy LAMBERT
 *
 */
public class CommandEvent extends ListenerAdapter {

	private Disbot bot;

	public CommandEvent(Disbot bot) {
		Logger_.log(LogStatus.INFO, "Loading command event...");
		this.bot = bot;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContent();
		MessageChannel channel = event.getChannel();
		
		if(message.startsWith(bot.getCommandManager().getCommandPrefix()) && !event.getAuthor().getId().equals(bot.getJDA().getSelfInfo().getId())) {
			int index = message.indexOf(" ");
			if(index <= 0)
				index = message.length();
			String commandName = message.substring(1 , index);

			Command command = bot.getCommandManager().getCommand(commandName);				

			if(command != null) {
				
				CommandExecutor executor = command.getExecutor();

				if(executor != null) {

					if(bot.getUserManager().getDiscordUser(event.getGuild(), event.getAuthor()).hasPermission(command.getPermission()) || bot.isSuperUser(event.getAuthor())) {
						String[] args = new String[0];
						if(index != message.length())
							args = message.substring(index + 1).split(" ");	

						String argsDisp = "";
						if(args.length > 0) {
							for(String s : args)
								argsDisp += s + " ";
							argsDisp = argsDisp.substring(0, argsDisp.length()-1);
						}
						Logger_.log(LogStatus.INFO, DisbotUtils.getNameForUser(event.getGuild(), event.getMessage().getAuthor()) + " performed command " + command.getName() + " " + argsDisp);
						if(!executor.onCommand(bot.getUserManager().getDiscordUser(event.getGuild(), event.getMessage().getAuthor()), event.getGuild(), channel , args, Collections.unmodifiableList(event.getMessage().getMentionedUsers())))
							channel.sendMessage(command.getUsageMessage().replace("<!cmd>" , command.getName()));

					} else
						channel.sendMessage(command.getPermissionMessage().replace("<!cmd>" , command.getName()).replace("<!perm>", command.getPermission()));
				} else
					Logger_.warning("A command with no executor has been used ! (" + command.getName() + ")");
			} else
				channel.sendMessage(bot.getCommandManager().getUnknownCommandMessage().replace("<!cmd>", commandName));

			event.getMessage().deleteMessage();
		}		
	}
}
