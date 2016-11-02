package fr.sysgli.disbotapi.user;

import fr.sysgli.disbotapi.Disbot;
import fr.sysgli.disbotapi.utils.Logger_;
import fr.sysgli.disbotapi.utils.Logger_.LogStatus;
import net.dv8tion.jda.events.guild.GuildJoinEvent;
import net.dv8tion.jda.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberBanEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 * Handle user joins and leave.
 * @author Jérémy LAMBERT
 *
 */
public class UserEvents extends ListenerAdapter {

	private Disbot bot;

	public UserEvents(Disbot bot) {
		Logger_.log(LogStatus.INFO, "Loading user event...");
		this.bot = bot;
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		bot.getUserManager().registerUser(event.getGuild(), event.getUser());
	}
	
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		bot.getUserManager().unregisterUser(event.getGuild(), event.getUser());
	}
	
	@Override
	public void onGuildMemberBan(GuildMemberBanEvent event) {
		bot.getUserManager().unregisterUser(event.getGuild(), event.getUser());
	}
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		Logger_.info("Joined a new guild : " + event.getGuild().getName());
		bot.getUserManager().scanGuild(event.getGuild());
	}
	
	@Override
	public void onGuildLeave(GuildLeaveEvent event) {
		Logger_.info("Left a guild : " + event.getGuild().getName());
		bot.getUserManager().deleteGuild(event.getGuild());
	}
		
}
