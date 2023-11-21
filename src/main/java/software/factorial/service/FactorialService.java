package software.factorial.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FactorialService implements RequestStreamHandler {

	@Override
	public void handleRequest(InputStream is, OutputStream os, Context context) throws IOException {
		Event event = new Event(is);
		var number = event.get("number").getAsInt();
		System.out.println("Executing factorial of " + number);


	}


	private static class Event {
		private final JsonObject jsonObject;

		public Event(InputStream is) {
			jsonObject = new Gson().fromJson(read(is), JsonObject.class);
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

		public JsonObject get(String key) {
			return jsonObject.getAsJsonObject(key);
		}
	}
}
