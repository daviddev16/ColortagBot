package org.mantra.bundles;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Objects;

public class BundleManager {

	public static InputStream getInputStream(String name) throws Exception {
		
		Class<BundleManager> bundleClass = BundleManager.class;
		InputStream bundleInputStream = bundleClass.getResourceAsStream("/bundles/"+name);
		return Objects.requireNonNull(bundleInputStream, "Bundle não achado.");
	
	}
	
	public static String readString(InputStream inputStream) {
		
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
