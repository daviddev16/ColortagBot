package org.mantra.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.mantra.app.MainApplication;

public class TextUtils {
	
	
	public static String DISCORD_URL = "https://discordapp.com";
	
	
	public static int randomIntegerBeetween(long seed, int min, int max) 
	{
		MainApplication.random.setSeed(seed);
		return (int) ((MainApplication.random.nextFloat() * (max - min)) + min);
	}

	public static String generateShortHex(int seed) 
	{
		return Integer.toHexString(randomIntegerBeetween(seed, 4096, 65565));
	}
	
	public static String readString(InputStream inputStream) 
	{
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		LineNumberReader reader = new LineNumberReader(inputStreamReader);
		StringBuilder builder = new StringBuilder();
		reader.lines().forEach(line -> {
			builder.append(line).append('\n');
		});
		
		builder.trimToSize();
		return builder.toString();
	}
	
	
}
