package fr.sysgli.disbotapi.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.dv8tion.jda.entities.User;

/**
 * Wraps the classic JDA user with metadata.<br>
 * Each DiscordUser is Guild-relative.
 * @author Jérémy LAMBERT
 * @see DiscordGuild
 *
 */
public final class DiscordUser implements Serializable {

	private static final long serialVersionUID = -4917131877096003260L;
	
	private transient User user;
	private transient DiscordGuild guild;
	private UserData data;
	private ArrayList<String> permissions;
	
	protected DiscordUser(User user) {
		this.user = user;
		data = new UserData();
		permissions = new ArrayList<String>();
	}
	
	protected void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Returns the JDA User.
	 * @return User user
	 * @see User
	 */
	public User getUser() {
		return user;
	}
	
	protected void setGuild(DiscordGuild guild) {
		this.guild = guild;
	}
	
	/**
	 * Returns the guild in which the user is.
	 * @return DiscordGuild guild
	 * @see DiscordGuild
	 */
	public DiscordGuild getGuild() {
		return guild;
	}
	
	/**
	 * <p>Adds a permission to the user.</p>
	 * <p>Permissions are <b>case sensitive</b>!</p>
	 * @param permission
	 */
	public void addPermission(String permission) {
		permissions.add(permission);
	}
	
	/**
	 * <p>Removes a permission to the user. Does nothing if the user doesn't have the specified permission.</p>
	 * <p>Permissions are <b>case sensitive</b>!</p>
	 * @param permission
	 */
	public void removePermission(String permission) {
		permissions.remove(permission);
	}
	
	/**
	 * <p>Check if the user has a permission.</p>
	 * <p>Permissions are <b>case sensitive</b>!</p>
	 * @param permission
	 * @return boolean hasPermission
	 */
	public boolean hasPermission(String permission) {
		return permissions.contains(permission);
	}
	
	/**
	 * Removes all permissions from this user. The user will still be able to use commands that don't need any permission to be ran.
	 */
	public void clearPermissions() {
		permissions.clear();
	}
	
	/**
	 * Returns the List containing all permissions for this user. <b>Read only.</b>
	 * @return List<String> permissions
	 */
	public List<String> getPermissions() {
		return Collections.unmodifiableList(permissions);
	}
	
	/**
	 * Returns the UserData object containing all metadata for this user.
	 * @return UserData data
	 * @see UserData
	 */
	public UserData getUserData() {
		return data;
	}
	
}
