package org.mantra.bundles;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public abstract class ResourceBundle<O> implements Closeable {

	private InputStream inputStream;
	
	public ResourceBundle(InputStream inputStream) throws IOException {
		this.inputStream = inputStream;
		close();
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	
	public abstract O getContent();
	
}
