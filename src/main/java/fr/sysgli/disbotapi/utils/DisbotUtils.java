package fr.sysgli.disbotapi.utils;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;

/**
 * Some simple tools.
 * @author Jeremy LAMBERT
 *
 */
public abstract class DisbotUtils {
	
	/**
	 * Returns the nickname of a user in the chosen guild.<br>Returns the real username if has no nickame.
	 * @param guild
	 * @param user
	 * @return String name
	 */
	public static final String getNameForUser(Guild guild , User user) {
		String nick = guild.getNicknameForUser(user);
		if(nick == null)
			return user.getUsername();
		else
			return nick;
	}

}
