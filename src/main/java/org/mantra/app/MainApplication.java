package org.mantra.app;

import java.util.EnumSet;
import java.util.Random;
import org.json.JSONObject;
import org.mantra.bundles.BundleManager;
import org.mantra.bundles.JSONBundle;
import org.mantra.bundles.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;

public class MainApplication {

	private static Logger logger = LoggerFactory.getLogger(MainApplication.class);

	public final static Random random = new Random();

	private static ResourceBundle<JSONObject> privateInformations;

	private static JDA jda;

	public static void main(String[] args) throws Exception {

		privateInformations = new JSONBundle(BundleManager.accessStream("private.lock"));
		JDABuilder jdaBuilder = JDABuilder.createDefault(getToken(),
				EnumSet.of(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_VOICE_STATES));
		
		jdaBuilder.addEventListeners(new ListenerAdapter() {

			public void onReady(ReadyEvent event) 
			{
				jda = event.getJDA();
				jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, false);
			}

		});

		jdaBuilder.setChunkingFilter(ChunkingFilter.ALL);
		jdaBuilder.setEnabledIntents(EnumSet.allOf(GatewayIntent.class));
		jdaBuilder.build();
	}

	public static ResourceBundle<JSONObject> getPrivateInformations() {
		return privateInformations;
	}

	public static Guild getMainGuild() {
		return getJDA().getGuildById(762173566376345630L);
	}

	public static synchronized Member getMember(User user) {

		Member m = getMainGuild().getMember(user);
		return m;
	}

	public static String getToken() {
		return getPrivateInformations().getContent().getString("id");
	}

	public static JDA getJDA() {
		return jda;
	}

	public static Logger getLogger() {
		return logger;
	}

}
