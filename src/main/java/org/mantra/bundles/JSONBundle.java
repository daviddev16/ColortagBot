package org.mantra.bundles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.json.JSONObject;

public class JSONBundle extends ResourceBundle<JSONObject> {

	private JSONObject jsonObject;
	
	public JSONBundle(InputStream inputStream) throws IOException {
		super(inputStream);
	}

	@Override
	public JSONObject getContent() {
		return jsonObject;
	}

	@Override
	public void close() throws IOException {
		
		Objects.requireNonNull(getInputStream(), "InputStream invalido.");
		String content = BundleManager.readString(getInputStream());
		jsonObject = new JSONObject(content);
		getInputStream().close();
		
	}

}
