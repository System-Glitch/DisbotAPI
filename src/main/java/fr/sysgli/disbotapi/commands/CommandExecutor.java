package fr.sysgli.disbotapi.commands;

import java.util.List;

import fr.sysgli.disbotapi.Disbot;
import fr.sysgli.disbotapi.user.DiscordUser;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

/**
 * <p>Abstract class modeling every command execution.</p>
 * <p>Each Command needs a CommandExecutor in order to do something when called.</p>
 * @author Jeremy LAMBERT
 *
 */
public abstract class CommandExecutor {

	private Disbot disbot;
	private Command command;
	
	public CommandExecutor(Disbot disbot) {
		this.disbot = disbot;
	}
	
	/**
	 * Returns the Disbot which this Command is associated with.
	 * @return a Disbot instance
	 * @see Disbot
	 * @see Command
	 * @see CommandManager
	 */
	public Disbot getDisbot() {
		return disbot;
	}
	
	/**
	 * Called whenever the command is used.
	 * @param user who used the command, <b>be careful : null if the sender is the console !</b>
	 * @param guild from which the command was sent
	 * @param channel from which the command was used
	 * @param args (arguments)
	 * @param mentionedUsers a list of all mentioned users in this message
	 * @return false if the command is not correctly used
	 */
	public abstract boolean onCommand(DiscordUser user, Guild guild, MessageChannel channel , String[] args , List<User> mentionedUsers);

	protected void setCommand(Command command) {
		this.command = command;
	}
	
	/**
	 * <p>Returns the Command containing this CommandExecutor.</p>
	 * <p>Useful to get extra informations such as the permission message.</p>
	 * @return Command command
	 * @see Command
	 */
	public Command getCommand() {
		return command;
	}
	
}
