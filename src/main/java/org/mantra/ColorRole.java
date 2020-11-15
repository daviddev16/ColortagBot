package org.mantra;

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

		if(this.role.getName().endsWith(PRO_TAG)) {
			pro = true;
		}
		
	}

	public boolean isPro() {
		return pro;
	}

	public void setPro(boolean pro) {
		this.pro = pro;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}
