package fr.sysgli.disbotapi;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import fr.sysgli.disbotapi.commands.Command;
import fr.sysgli.disbotapi.commands.CommandManager;
import fr.sysgli.disbotapi.commands.PermissionsExecutor;
import fr.sysgli.disbotapi.events.CommandEvent;
import fr.sysgli.disbotapi.events.EventManager;
import fr.sysgli.disbotapi.user.UserEvents;
import fr.sysgli.disbotapi.user.UserManager;
import fr.sysgli.disbotapi.utils.Logger_;
import fr.sysgli.disbotapi.utils.Logger_.LogStatus;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.entities.User;

/**
 * The main class used to create a bot.
 * @author Jérémy LAMBERT
 * @see CommandManager
 * @see EventManager
 * @see UserManager
 *
 */
public final class Disbot {

	private static final long AUTO_SAVE_DELAY = 10*60*1000;
	
	private JDA jda;
	private ArrayList<User> superUsers;
	private CommandManager commandManager;
	private EventManager eventManager;
	private UserManager userManager;
	private Thread autoSaveThread;
	
	private boolean shuttingDown;

	public Disbot(String token) {

		long timeStart = System.currentTimeMillis();
		
		superUsers = new ArrayList<User>();
		
		try {
			jda = new JDABuilder().setBotToken(token).setBulkDeleteSplittingEnabled(false).buildBlocking();
			commandManager = new CommandManager();
			eventManager = new EventManager(jda);
			userManager = new UserManager(jda);
			
			registerDefaultEvents();
			registerDefaultCommands();
			
			initAutoSave();
			registerShutdownHook();
			
			Logger_.info("Finished loading ! (" + (System.currentTimeMillis() - timeStart) + "ms)");
		} catch (LoginException e) {
			Logger_.error("Invalid token.");
		} catch (IllegalArgumentException | InterruptedException e) {
			e.printStackTrace();
			Logger_.log(LogStatus.ERROR, "An error occured while generating client.\n");
		}
	}
	
	/**
	 * Adds a super user. Super users have all permissions.
	 * @param user
	 */
	public void addSuperUser(User user) {
		superUsers.add(user);
	}
	
	/**
	 * Removes a super user. Does nothing if the user is not a super user.
	 * @param user
	 */
	public void removeSuperUser(User user) {
		superUsers.remove(user);
	}
	
	/**
	 * Returns if the user is a super user. Super users have all permissions.
	 * @param user
	 * @return boolean isSuperUser
	 */
	public boolean isSuperUser(User user) {
		return superUsers.contains(user);
	}

	private void registerDefaultEvents() {
		eventManager.registerEvent(new CommandEvent(this));
		eventManager.registerEvent(new UserEvents(this));
	}

	private void registerDefaultCommands() {
		Logger_.info("Registering default permission management commands...");
		Command command = new Command("perm");
		command.setPermission("permissions");
		command.setUsageMessage("**Utilisation :** " + commandManager.getCommandPrefix() + "perm [add|remove|clear] [@user] [permission]");
		command.setExecutor(new PermissionsExecutor(this));
		commandManager.registerCommand(command);
	}

	private void initAutoSave() {
		autoSaveThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while(!shuttingDown) {
					try {
						for(long i = 0 ; i < AUTO_SAVE_DELAY ; i += 1000) {
							if(shuttingDown)
								return;
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					userManager.saveAll();
				}
			}
			
		});
		autoSaveThread.start();
	}
	
	private void registerShutdownHook() {
		shuttingDown = false;
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				Logger_.info("Shutting down...");
				shuttingDown = true;
				try {
					Logger_.info("Stopping auto-save thread...");
					autoSaveThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				userManager.saveAll();
				Logger_.info("Save complete!\n");
			}
			
		}));
	}
	
	/**
	 * Get the JDA object associated with this bot
	 * @return JDA jda
	 */
	public JDA getJDA() {
		return jda;
	}

	/**
	 * <p>Returns the CommandManager of this bot.</p>
	 * <p>Use this in order to register or unregister commands or to change the command prefix.</p>
	 * @return CommandManager commandManager
	 * @see CommandManager
	 */
	public CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * <p>Returns the EventManager of this bot.</p>
	 * <p>Use this in order to register or unregister events.</p>
	 * @return EventManager eventManager
	 * @see EventManager
	 */
	public EventManager getEventManager() {
		return eventManager;
	}
	
	/**
	 * 
	 * @return UserManager userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}
}
