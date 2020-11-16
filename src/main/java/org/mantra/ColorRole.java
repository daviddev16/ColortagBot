package org.mantra;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class ColorRole {

	public static String PRO_TAG = "[PRO]";
	
	public static String FREE_TAG = "[FREE]";

	private boolean pro = false;

	private Role role;

	public ColorRole(Role role) {
		this.role = role;
		this.pro = false;
	}

	public boolean isPro() {
		return pro;
	}

	public void applyTo(Member member, Guild guild) {
		guild.addRoleToMember(member, getRole()).queue();
	}

	public void setPro(boolean pro) {
		this.pro = pro;
		updateName();
	}

	public void updateName() {
		if (isPro()) {
			getRole().getManager().setName(getOriginalName() + " " + PRO_TAG).queue();
			return;
		}
		getRole().getManager().setName(getOriginalName() + " " + FREE_TAG).queue();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getOriginalName() {
		if (getRole().getName().endsWith(PRO_TAG)) {
			int nameWithoutTagEnd = role.getName().lastIndexOf('[') - 1;
			return role.getName().substring(0, nameWithoutTagEnd).trim();
		} else {
			return role.getName().trim();
		}
	}

}
