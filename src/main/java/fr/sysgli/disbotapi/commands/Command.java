package fr.sysgli.disbotapi.commands;

/**
 * Models every command : its name, the needed permission and the CommandExecutor.
 * @author Jérémy LAMBERT
 * @see CommandExecutor
 */
public final class Command {

	private CommandExecutor executor;
	private String name;
	private String permission;
	private String permissionMessage;
	private String usageMessage;

	/**
	 * <p>Each command must have a name that will be used to identify it. Specify the name without the command prefix.<br>
	 * For example, if the command prefix is '&' and the command is meant to be used like this "&command", the returned value will be "command"</p>
	 * <p><u>Note :</u> The command name is not case sensitive.</p>
	 * @param name
	 */
	public Command(String name) {
		this.name = name;
		permissionMessage = CommandManager.DEFAULT_PERMISSION_MESSAGE;
		usageMessage = CommandManager.DEFAULT_USAGE_MESSAGE;
	}

	/**
	 * Sets the permission needed to use the command.<br>Set to null if the command is freely usable.
	 * @param permission
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * Returns the permission needed to use this command.
	 * @return
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * <p>Sets the message to display if the user who used the command hasn't got the needed permission.</p>
	 * @param message
	 */
	public void setPermissionMessage(String message) {
		this.permissionMessage = message;
	}

	/**
	 * Return the message to display if the user who used the command hasn't got the needed permission.
	 * @return String permissionMessage
	 */
	public String getPermissionMessage() {
		return permissionMessage;
	}
	
	/**
	 * <p>Sets the message to display if a command is not correctly used.</p>
	 * <p>A command with argument should <b>always</b> override the default usage message.</p>
	 * @param message
	 */
	public void setUsageMessage(String message) {
		this.usageMessage = message;
	}

	/**
	 * Returns the message to display if a command is not correctly used.
	 * @return String usageMessage;
	 */
	public String getUsageMessage() {
		return usageMessage;
	}
	
	/**
	 * Sets the behavior when the command is used.<br>The command will do nothing if null.
	 * @param executor
	 * @see CommandExecutor
	 */
	public void setExecutor(CommandExecutor executor) {
		executor.setCommand(this);
		this.executor = executor;
	}

	/**
	 * Returns the instance of the CommandExecutor associated with this command
	 * @return CommandExecutor executor
	 * @see CommandExecutor
	 */
	public CommandExecutor getExecutor() {
		return executor;
	}

	/**
	 * <p>The name of the command without the command prefix.</p>
	 * <p>For example, if the command prefix is "&" and the command is meant to be used like this "&command", the returned value will be "command"</p>
	 * @return the name of the command
	 */
	public String getName() {
		return name;
	}
}
