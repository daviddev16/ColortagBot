package org.mantra.app;

import java.io.IOException;
import java.util.EnumSet;
import org.json.JSONException;
import org.mantra.ColorTagListener;
import org.mantra.bundles.BundleManager;
import org.mantra.bundles.JSONBundle;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;

/* YETI BOT */
/* Oficial Guild -> https://discord.gg/nYsxdeS */
public class MainApplication {

	public static String PRESENCE = " Ao Servidor | open-source github.com/daviddev16/ColortagBot";
	
	public static final long DEVELOPER_USER_ID = 339978701297156098L;

	public static long FREE_TAG_CHANNEL_ID = 777665596740337694L;
	
	public static long SOCIEDADE_SECRETA_GUILD_ID = 762173566376345630L;
	
	public static void main(String[] args) throws Exception {
		
		JDABuilder jdaBuilder = JDABuilder.createDefault(getToken(), EnumSet.of(
				GatewayIntent.GUILD_MEMBERS, 
				GatewayIntent.GUILD_EMOJIS, 
				GatewayIntent.GUILD_VOICE_STATES));
		
		jdaBuilder.addEventListeners(new ListenerAdapter() {

			public void onReady(ReadyEvent event) 
			{
				JDA jda = event.getJDA();
				jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.of(ActivityType.WATCHING, PRESENCE));
				setupColorSystem(jda);
				
			}

		});

		jdaBuilder.setChunkingFilter(ChunkingFilter.ALL);
		jdaBuilder.setEnabledIntents(EnumSet.allOf(GatewayIntent.class));
		jdaBuilder.build();
	}
	
	private static void setupColorSystem(JDA jda) {
		
		
		ColorTagListener tagsListener = new ColorTagListener(SOCIEDADE_SECRETA_GUILD_ID, jda);

		tagsListener.addProRoles("Membro da Staff");
		tagsListener.addProRoles("Server Booster");
		tagsListener.addProRoles(767283598918418445L); //Caçador de recompensas.
		
		jda.addEventListener(tagsListener);

		tagsListener.addColor("Branco").setPro(true);
		tagsListener.addColor("Azul 1");
		tagsListener.addColor("Azul 2");
		tagsListener.addColor("Ciano 1");
		tagsListener.addColor("Ciano 2").setPro(true);
		tagsListener.addColor("Verde 1");
		tagsListener.addColor("Verde 2").setPro(true);
		tagsListener.addColor("Verde 3").setPro(true);
		tagsListener.addColor("Verde 4");
		tagsListener.addColor("Amarelo 1");
		tagsListener.addColor("Amarelo 2").setPro(true);
		tagsListener.addColor("Laranja 1");
		tagsListener.addColor("Laranja 2").setPro(true);
		tagsListener.addColor("Vermelho 1");
		tagsListener.addColor("Vermelho 2").setPro(true);
		tagsListener.addColor("Rosa 1");
		tagsListener.addColor("Rosa 2");
		tagsListener.addColor("Rosa 3").setPro(true);
		tagsListener.addColor("Violeta").setPro(true);
		tagsListener.addColor("Roxo");
		tagsListener.addColor("Preto").setPro(true);

	}
	
	public static String getDeveloperIcon(JDA jda) 
	{
		User developerUser = jda.getUserById(DEVELOPER_USER_ID);
		if(developerUser != null) {
			return developerUser.getEffectiveAvatarUrl();
		}
		return "";
	}
	
	public static String getSelfAvatar(JDA jda) 
	{
		return jda.getSelfUser().getEffectiveAvatarUrl();
	}

	public static JSONBundle getTokenBundle() throws IOException, Exception 
	{
		return new JSONBundle(BundleManager.getInputStream("bot.json"));
	}

	public static String getToken() throws JSONException, IOException, Exception 
	{
		return getTokenBundle().getContent().getString("id");
	}

}
