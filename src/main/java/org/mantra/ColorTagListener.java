package org.mantra;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import org.mantra.app.MainApplication;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
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
	
	private Role findRoleById(long id) {

		for(Role role : getGuild().getRoles()) {
			if(role.getIdLong() == id) {
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
				.setAuthor("Daviiiddev", "https://github.com/daviddev16", MainApplication.getDeveloperIcon(getJDA()))
				.setDescription("Todas as cores:")
				.setThumbnail(MainApplication.getSelfAvatar(getJDA()))
				.setColor(Color.cyan.brighter().brighter());

		builder.addField("Como conseguir?", "", true);
		builder.addField(" - Digite: ", "Usar <color>", false);

		StringBuffer buffer = new StringBuffer();
		for (ColorRole colorRole : colorRoles) {
			buffer.append(colorRole.getRole().getAsMention()).append('\n');

		}

		StringJoiner joiner = new StringJoiner(" ,");
		for(Role accRoles : proAcceptedRoles) {
			joiner.add(accRoles.getAsMention());
		}

		buffer.trimToSize();
		builder.addField("Colors", buffer.toString(), false);
		
		builder.addField("Usuarios PRO:", joiner.toString(), false);
		
		channel.sendMessage(builder.build()).queue();
	}

	public Guild getGuild() {
		return currentGuild;
	}

	public List<ColorRole> getColorRoles() {
		return colorRoles;
	}
	
	public void addProRoles(String name) 
	{
		getProAcceptedRoles().add(findRoleByName(name));
	}
	public void addProRoles(long id) 
	{
		getProAcceptedRoles().add(findRoleById(id));
	}
	
	public boolean hasAccessToPro(Role role) 
	{
		for(Role accRole : getProAcceptedRoles()) {
			if(accRole.getIdLong() == role.getIdLong()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPermission(Member member) 
	{
		return member.getRoles().stream().anyMatch(r -> hasAccessToPro(r));
	}

	/*JDA Listener*/
	public void onMessageReceived(MessageReceivedEvent event) {

		if(!event.isFromGuild() || event.getAuthor().isBot())
		{
			return;
		}

		if(event.getChannel().getIdLong() == MainApplication.FREE_TAG_CHANNEL_ID) {

			String content = event.getMessage().getContentRaw();
			Message message = event.getMessage();

			if(content.equalsIgnoreCase("(criarEmbed)")) 
			{
				generateEmbed(event.getChannel());

			}
			else if(content.startsWith("Usar"))
			{
				List<Role> mentionedRoles = message.getMentionedRoles();

				if(!mentionedRoles.isEmpty()) {

					ColorRole colorRole = findColor(mentionedRoles.get(0));
					
					if(colorRole != null)
					{
						
						if(!colorRole.isPro()) 
						{
							colorRole.applyTo(event.getMember(), getGuild());
						}
						else 
						{
							if (hasPermission(event.getMember())) 
							{
								colorRole.applyTo(event.getMember(), getGuild());
							}
							else
							{
								event.getChannel().sendMessage("Você não é PRO.").queue(msg -> 
								{
									if(msg != null) {
										msg.delete().queueAfter(10L, TimeUnit.SECONDS);
									}
								});
							}
						}
						
					}
					else 
					{
						event.getChannel().sendMessage("Isso não é uma cor.").queue(msg -> 
						{
							if(msg != null) {
								msg.delete().queueAfter(10L, TimeUnit.SECONDS);
							}
						});
					}
					
				}
				else 
				{
					event.getChannel().sendMessage("Você precisa mencionar uma cor para isso.").queue(msg -> 
					{
						if(msg != null) {
							msg.delete().queueAfter(10L, TimeUnit.SECONDS);
						}
					});
				}
			}

			message.delete().queue();
			return;
		}

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
