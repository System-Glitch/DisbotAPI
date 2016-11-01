package fr.sysgli.disbotapi.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.sysgli.disbotapi.utils.Logger_;

/**
 * This class manages all commands. It allows to register, unregister commands or change the command prefix dynamically.
 * @author Jérémy LAMBERT
 *
 */
public final class CommandManager {

	private static final String DEFAULT_COMMAND_PREFIX = "&";
	private static final String DEFAULT_UNKNOWN_COMMAND_MESSAGE = "**<!cmd> :** Commande inconnue.";
	protected static final String DEFAULT_PERMISSION_MESSAGE = "**Permission requise :** <!perm>";
	protected static final String DEFAULT_USAGE_MESSAGE = "**Utilisation :** <!cmd>";
	
	private HashMap<String , Command> commands;
	private String commandPrefix;
	private String unknownCommandMessage;
	
	public CommandManager() {
		commands = new HashMap<String , Command>();
		commandPrefix = DEFAULT_COMMAND_PREFIX;
		unknownCommandMessage = DEFAULT_UNKNOWN_COMMAND_MESSAGE;
	}
	
	/**
	 * <p>Registers a command which will be automatically executed on call by a user.</p>
	 * <p><b>Warning : </b> the command will not be registered if a command with the same name already exists. 
	 * Moreover, an error message will be printed in the logs to let you fix it easily.</p>
	 * @param command
	 * @see Command
	 */
	public void registerCommand(Command command) {
		if(commands.containsKey(command.getName()))
			Logger_.error("Duplicated command name ! \"" + command.getName() + "\" is already registered! Skipping.");
		else
			commands.put(command.getName(), command);
	}


	/**
	 * Removes a command.
	 * @param command
	 * @return the deleted Command, null if not found.
	 */
	public Command unregisterCommand(String command) {
		return commands.remove(command);
	}

	/**
	 * Returns the instance of the CommandExecutor associated with this command
	 * @param command
	 * @return the CommandExecutor for the specified command or null if not found
	 * @see Command
	 */
	public Command getCommand(String command) {
		return commands.get(command);
	}
	
	/**
	 * Returns the Map containing all commands and their executor. <b>Read only.</b>
	 * @return Map<String , Command> commands
	 */
	public Map<String , Command> getCommands() {
		return Collections.unmodifiableMap(commands);
	}
	
	/**
	 * <p>Returns the command prefix.</p>
	 * <p>For example, if the prefix is '/', a command named "command" will be called like this : "/command".</p>
	 * <p><i>The default prefix is "&".</i></p>
	 * @return String commandPrefix
	 */
	public String getCommandPrefix() {
		return commandPrefix;
	}
	
	/**
	 * <p>Sets the command prefix.</p>
	 * <p>A command named "command" will be called if a user sends a message <b>starting with</b> the prefix.<br>
	 * Example : if the prefix is '/', a command named "command" will be called like this : "/command".</p>
	 * <p><b><u>Note :</u></b> the prefix can be a String but is limited to <b>16 characters</b>! 
	 * An IllegalArgumentException is thrown if this limit is exceeded.</p>
	 * <p><i>The default prefix is "&".</i></p>
	 * @param prefix, limited to 16 characters
	 */
	public void setCommandPrefix(String prefix) {
		if(prefix.length() > 16)
			throw new IllegalArgumentException("The command prefix must be 16 characters long at maximum! \"" + prefix + "\" is " + prefix.length() + " characters long.");
		this.commandPrefix = prefix;
	}
	
	/**
	 * Sets the message to display if an non-existent command is used.
	 * @param message
	 */
	public void setUnknownCommandMessage(String message) {
		this.unknownCommandMessage = message;
	}
	
	/**
	 * Returns the message display if an non-existent command us used.
	 * @return String unknownCommandMessage
	 */
	public String getUnknownCommandMessage() {
		return unknownCommandMessage;
	}
	
}
