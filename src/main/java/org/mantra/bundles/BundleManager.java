package org.mantra.bundles;

import java.io.InputStream;
import java.util.Objects;

public class BundleManager {

	public static InputStream accessStream(String name) throws Exception 
	{
		Class<BundleManager> bundleClass = BundleManager.class;
		InputStream bundleInputStream = bundleClass.getResourceAsStream("/bundles/"+name);
		return Objects.requireNonNull(bundleInputStream, "Bundle não achado.");
	}


}
