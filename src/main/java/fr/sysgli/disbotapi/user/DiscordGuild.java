package fr.sysgli.disbotapi.user;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;

/**
 * Simple container for a Guild and all its users.
 * @author Jeremy LAMBERT
 *
 */
public final class DiscordGuild {
	
	private Guild guild;
	private ConcurrentHashMap<String , DiscordUser> users;
	private File dir;
	
	DiscordGuild(Guild guild , File dir) {
		this.guild = guild;
		this.dir = dir;
		users = new ConcurrentHashMap<String , DiscordUser>();
	}
	
	/**
	 * Returns the JDA Guild associated with this DiscordGuild instance.
	 * @return Guild guild
	 */
	public Guild getGuild() {
		return guild;
	}
	
	File getDirectory() {
		return dir;
	}
	
	ConcurrentHashMap<String , DiscordUser> getUsers() {
		return users;
	}
	
	DiscordUser getDiscordUser(User user) {
		return users.get(user.getId());
	}
	
	DiscordUser getDiscordUserById(String id) {
		return users.get(id);
	}
	
	void addDiscordUser(DiscordUser user) {
		users.put(user.getUser().getId(), user);
	}
	
	void removeDiscordUser(DiscordUser user) {
		removeDiscordUser(user.getUser());
	}
	
	DiscordUser removeDiscordUser(User user) {
		return users.remove(user.getId());
	}

}
