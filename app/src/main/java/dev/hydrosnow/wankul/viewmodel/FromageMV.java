package dev.hydrosnow.wankul.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dev.hydrosnow.wankul.R;
import dev.hydrosnow.wankul.HttpUtils;

public class FromageMV {
	private final String api_server;
	
	public FromageMV(final AppCompatActivity activity) {
		api_server = activity.getString(R.string.api_server);
	}
	
	public Fromage[] get() throws IOException, JSONException {
		final String str = HttpUtils.makeHttpConnection("GET", api_server + "/api/fromage");
		final JSONObject response = new JSONObject(str);
		if (response.getBoolean("valid") == false) {
			throw new JSONException("pd");
		}
		final JSONArray array = response.getJSONArray("result");
		final Fromage[] fromages = new Fromage[array.length()];
		for (int a = 0; a < array.length(); a++) {
			final JSONObject obj = array.getJSONObject(a);
			fromages[a] = new Fromage(obj);
		}
		return fromages;
	}
	
}
