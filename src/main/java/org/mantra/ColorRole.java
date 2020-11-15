package org.mantra;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class ColorRole {

	public static String PRO_TAG = "[PRO]";

	private boolean pro = false;

	private Role role;

	public ColorRole(Role role) {
		this.role = role;
		update();
	}

	public void update() {

		if (this.role.getName().endsWith(PRO_TAG)) {
			pro = true;
			return;
		}

		pro = false;

	}

	public boolean isPro() {
		return pro;
	}

	public void applyTo(Member member, Guild guild) 
	{
		guild.addRoleToMember(member, getRole()).queue();
	}
	
	public void setPro(boolean pro) {

		update();

		if (pro) {
			role.getManager().setName(role.getName() + " " + PRO_TAG).queue();
		} else {
			role.getManager().setName(role.getName().substring(0, role.getName().length() - (" " + PRO_TAG).length()))
					.queue();
		}

		this.pro = pro;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
