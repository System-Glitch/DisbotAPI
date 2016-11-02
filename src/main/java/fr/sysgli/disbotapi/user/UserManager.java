package fr.sysgli.disbotapi.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import fr.sysgli.disbotapi.utils.Logger_;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;

/**
 * This class manages users wrappers DiscordUser.
 * @author Jérémy LAMBERT
 * @see DiscordUser
 *
 */
public final class UserManager {

	private ConcurrentHashMap<String , DiscordGuild> guilds;
	private JDA jda;
	private File guildsDir;

	public UserManager(JDA jda) {
		guilds = new ConcurrentHashMap<String , DiscordGuild>();
		this.jda = jda;
		this.guildsDir = new File("disbot/guilds/");
		init();
	}

	private void init() {
		checkDataDirectory();
		scanGuilds();
	}

	private void checkDataDirectory() {
		File dataDir = new File("disbot/");
		if(!dataDir.exists()) {
			Logger_.info("Data directory is missing. Creating a fresh one.");
			dataDir.mkdirs();
		}
		if(!guildsDir.exists()) {
			Logger_.info("Guilds directory is missing. Creating a fresh one.");
			guildsDir.mkdirs();
		}
	}

	private void scanGuilds() {
		for(Guild guild : jda.getGuilds()) {
			String id = guild.getId();
			File dir = new File("disbot/guilds/" + id);
			boolean ok = false;
			for(File file : guildsDir.listFiles())
				if(file.getName().equals(id)) {
					ok = true;
					break;
				}					
			if(!ok) {
				Logger_.info("Unknown guild : " + guild.getName() + ". Creating a fresh directory.");
				dir.mkdir();
			}
			DiscordGuild discordGuild = registerGuild(guild , dir);
			scanUsers(discordGuild);
		}
		deleteUnconnectedGuilds();
	}
	
	private void deleteUnconnectedGuilds() {
		for(File f : guildsDir.listFiles()) {
			if(f.isDirectory() && !containsGuild(f.getName()))
				deleteGuild(f);
		}
	}
	
	private boolean containsGuild(String guildID) {
		for(DiscordGuild guild : guilds.values()) {
			if(guild.getGuild().getId().equals(guildID))
				return true;
		}
		return false;
	}

	private void scanUsers(DiscordGuild guild) {
		for(User user : guild.getGuild().getUsers()) {
			File userFile = new File(guild.getDirectory().getAbsolutePath() + "/" + user.getId());
			if(userFile.exists())
				loadUser(user.getId() , userFile , guild);
			else
				registerUser(guild.getGuild() , user);
		}
	}

	private DiscordGuild registerGuild(Guild guild , File dir) {
		DiscordGuild discordGuild = new DiscordGuild(guild , dir);
		guilds.put(guild.getId(), discordGuild);
		return discordGuild;
	}
	
	protected void scanGuild(Guild guild) {
		String id = guild.getId();
		File dir = new File("disbot/guilds/" + id);
		boolean ok = false;
		for(File file : guildsDir.listFiles())
			if(file.getName().equals(id)) {
				ok = true;
				break;
			}					
		if(!ok) {
			Logger_.info("Unknown guild : " + guild.getName() + ". Creating a fresh directory.");
			dir.mkdir();
		}
		DiscordGuild discordGuild = registerGuild(guild , dir);
		scanUsers(discordGuild);
	}
	
	protected void deleteGuild(Guild guild) {
		Logger_.info("Deleting guild files : " + guild.getName());
		DiscordGuild discordGuild = guilds.get(guild.getId());
		File dir = discordGuild.getDirectory();
		for(File f : dir.listFiles())
			f.delete();
		dir.delete();
		guilds.remove(guild.getId());
	}
	
	private void deleteGuild(File guildDir) {
		Logger_.info("Deleting unused guild files : " + guildDir.getName());
		for(File f : guildDir.listFiles())
			f.delete();
		guildDir.delete();
	}

	/**
	 * <p>Returns the DiscordUser associated with the specified JDA User. Returns null if doesn't exists or not known.</p>
	 * <p><u>Note :</u> Each DiscordUser is guild-relative.</p>
	 * @param guild
	 * @param user
	 * @return DiscordUser discordUser
	 * @see DiscordUser
	 */
	public DiscordUser getDiscordUser(Guild guild, User user) {
		return guilds.get(guild.getId()).getDiscordUser(user);
	}

	/**
	 * <p>Returns a DiscordUser from it's unique ID. Returns null if doesn't exists or not known.</p>
	 * <p><u>Note :</u> Each DiscordUser is guild-relative.</p>
	 * @param guild
	 * @param id, the unique ID of the user
	 * @return DiscordUser user
	 * @see DiscordUser
	 */
	public DiscordUser getDiscordUserById(Guild guild, String id) {
		return guilds.get(guild.getId()).getDiscordUserById(id);
	}

	/**
	 * <p>Returns all known users in a guild. Returns null if the guild is unknown.</p>
	 * <p><u>Note :</u> Each DiscordUser is guild-relative.</p>
	 * @param guild
	 * @return Collection<DiscordUser> users
	 */
	public Collection<DiscordUser> getUsers(Guild guild) {
		return guilds.get(guild.getId()).getUsers().values();
	}

	/**
	 * Returns all known guilds.
	 * @return
	 */
	Collection<DiscordGuild> getGuilds() {
		return guilds.values();
	}
	
	DiscordGuild getGuild(Guild guild) {
		return getGuild(guild.getId());
	}
	
	DiscordGuild getGuild(String id) {
		return guilds.get(id);
	}

	void registerUser(Guild guild , User user) {
		Logger_.info("Registering new user \"" + user.getUsername() + " (" + user.getId() + ")\" in guild \"" + guild.getName() + " (" + guild.getId() + ")\".");
		DiscordUser discordUser = new DiscordUser(user);
		DiscordGuild discordGuild = guilds.get(guild.getId());
		discordUser.setGuild(discordGuild);
		discordUser.setUser(user);
		discordGuild.addDiscordUser(discordUser);
		saveUser(discordUser);
	}

	void unregisterUser(Guild guild , User user) {
		Logger_.info("User \"" + user.getUsername() + " (" + user.getId() + ")\" left guild \"" + guild.getName() + " (" + guild.getId() + ")\", unregistering...");
		guilds.get(guild).removeDiscordUser(user);
		new File(getGuild(guild).getDirectory().getAbsolutePath() + "/" + user.getId()).delete();
	}

	private void loadUser(String id , File file , DiscordGuild guild) {
		ObjectInputStream ois = null;
		BufferedInputStream bis;
		FileInputStream fis;

		try {

			if(!file.exists())
				file.createNewFile();
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);

			DiscordUser user = (DiscordUser) ois.readObject();
			user.setGuild(guild);
			user.setUser(jda.getUserById(id));
			
			guild.addDiscordUser(user);

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveUser(DiscordUser user) {
		File file = new File(user.getGuild().getDirectory().getAbsolutePath() + "/" + user.getUser().getId());
		ObjectOutputStream oos = null;
		BufferedOutputStream bos;
		FileOutputStream fos;

		try {
			if(!file.exists())
				file.createNewFile();
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);

			oos.writeObject(user);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Force save everything
	 */
	public void saveAll() {
		Logger_.info("Saving...");
		for(DiscordGuild guild : guilds.values())
			for(DiscordUser user : guild.getUsers().values())
				saveUser(user);
	}
}
