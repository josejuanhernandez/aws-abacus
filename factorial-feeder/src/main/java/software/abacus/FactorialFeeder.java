package software.abacus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FactorialFeeder implements RequestStreamHandler {

	@Override
	public void handleRequest(InputStream is, OutputStream os, Context context) throws IOException {
		String json = read(is);
		Event event = new Event(json);
		System.out.println(json);
	}

	private static String read(InputStream is) {
		try (BufferedInputStream bis = new BufferedInputStream(is)) {
			String s = new String(bis.readAllBytes());
			System.out.println(s);
			return s;
		} catch (IOException e) {
			return "{}";
		}
	}



	private static class Event {
		private final JsonObject jsonObject;

		public Event(String json) {
			jsonObject = new Gson().fromJson(json, JsonObject.class);
		}


		public JsonObject get(String key) {
			return jsonObject.getAsJsonObject(key);
		}
	}
}
