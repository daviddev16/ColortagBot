package org.mantra;

import java.util.ArrayList;
import java.util.List;

import org.mantra.app.MainApplication;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildListener extends ListenerAdapter {
	

	private static GuildListener guildListener = new GuildListener();
	
	private List<ColorRole> colorRoles;
	
	public GuildListener() {
		
		colorRoles = new ArrayList<ColorRole>();

		colorRoles.add(findGuildRoleByName("Azul 1"));
		colorRoles.add(findGuildRoleByName("Azul 2"));
		colorRoles.add(findGuildRoleByName("Ciano"));
		colorRoles.add(findGuildRoleByName("Verde 1"));
		colorRoles.add(findGuildRoleByName("Verde 2"));
		colorRoles.add(findGuildRoleByName("Verde 3"));
		colorRoles.add(findGuildRoleByName("Verde 4"));
		colorRoles.add(findGuildRoleByName("Amarelo 1"));
		colorRoles.add(findGuildRoleByName("Amarelo 2"));
		colorRoles.add(findGuildRoleByName("Laranja 1"));
		colorRoles.add(findGuildRoleByName("Laranja 2"));
		colorRoles.add(findGuildRoleByName("Vermelho 1"));
		colorRoles.add(findGuildRoleByName("Vermelho 2"));
		colorRoles.add(findGuildRoleByName("Rosa 1"));
		colorRoles.add(findGuildRoleByName("Rosa 2"));
		colorRoles.add(findGuildRoleByName("Violeta"));
		colorRoles.add(findGuildRoleByName("Violeta"));
		
	}
	
	public ColorRole findGuildRoleByName(String name) {
		
		for(Role role : MainApplication.getMainGuild().getRoles()) {
			if(role.getName().equalsIgnoreCase(name)) {
				return new ColorRole(role);
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

	public void onMessageReceived(MessageReceivedEvent event) {

	
	}
	
	public void onRoleUpdateName(RoleUpdateNameEvent event) {
		
		ColorRole colorRole = findColor(event.getEntity());
		
		if(colorRole != null) {
			colorRole.update();
		}

	}
	
	public List<ColorRole> getColorRoles() {
		return colorRoles;
	}

	public static GuildListener getSingletion() {
		return guildListener;
	}
	
	
}
