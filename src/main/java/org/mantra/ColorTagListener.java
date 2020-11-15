package org.mantra;

import java.util.ArrayList;
import java.util.List;

import org.mantra.app.MainApplication;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ColorTagListener extends ListenerAdapter {

	private List<Role> proAcceptedRoles;
	
	private List<ColorRole> colorRoles;

	private Guild currentGuild;
	
	private JDA jda;

	public ColorTagListener(long guildId, JDA jda) {

		colorRoles = new ArrayList<ColorRole>();
		proAcceptedRoles = new ArrayList<Role>();
		
		this.jda = jda;
		currentGuild = jda.getGuildById(guildId);

	}

	public ColorRole addColor(String colorRoleName) {

		ColorRole colorRole = new ColorRole(findRoleByName(colorRoleName));
		colorRoles.add(colorRole);

		return colorRole;
	}

	private Role findRoleByName(String name) {

		for(Role role : getGuild().getRoles()) {
			if(role.getName().equalsIgnoreCase(name)) {
				return role;
			}
		}
		return null;
	}

	public ColorRole findColor(Role role) {

		for(ColorRole colorRole : colorRoles) {
			if(colorRole.getRole().getIdLong() == role.getIdLong()) {
				return colorRole;
			}
		}

		return null;
	}
	
	public void generateEmbed(MessageChannel channel) {
		
		EmbedBuilder builder = new EmbedBuilder()
				.setTitle("Cores disponiveis:")
				.setAuthor("Daviiiddev", "https://github.com/daviddev16", MainApplication.getDeveloperIcon(getJDA()));
		
		
		channel.sendMessage(builder.build()).queue();
	}
	
	public Guild getGuild() {
		return currentGuild;
	}

	public List<ColorRole> getColorRoles() {
		return colorRoles;
	}
	
	/*JDA Listener*/
	public void onMessageReceived(MessageReceivedEvent event) {


	}

	/*JDA Listener*/
	public void onRoleUpdateName(RoleUpdateNameEvent event) {

		if(event.getEntity() != null) {

			ColorRole colorRole = findColor(event.getEntity());
			if(colorRole != null) {
				colorRole.update();
			}
		}
	
	}

	public List<Role> getProAcceptedRoles() {
		return proAcceptedRoles;
	}

	public JDA getJDA() {
		return jda;
	}



}
