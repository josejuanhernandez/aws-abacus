package software.abacus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.IntStream;

public class FactorialCalculator implements RequestStreamHandler {

	private static final String message = "{ \"number\": %d, \"factorial\": %d }";
	private static final String ResponsesQueue = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/responses";

	public void handleRequest(InputStream is, OutputStream os, Context context) throws IOException {
		int number = input(read(is));
		Factorial factorial = new Factorial(number);
		String output = publish(output(factorial));
		os.write(output.getBytes());
	}

	private static String publish(String output) {
		SendMessageRequest messageRequest = SendMessageRequest.builder()
				.messageBody(output)
				.queueUrl(ResponsesQueue)
				.build();
		try (SqsClient client = AwsFactory.sqs()) {
			client.sendMessage(messageRequest);
			return output;
		}
	}

	private static int input(String json) {
		return new Message(json).detail().get("number").getAsInt();
	}

	private static String output(Factorial factorial) {
		return String.format(message, factorial.number, factorial.result());
	}

	private static String read(InputStream is) {
		try (BufferedInputStream bis = new BufferedInputStream(is)) {
			return new String(bis.readAllBytes());
		} catch (IOException e) {
			return "{}";
		}
	}

	private static class Message {
		private final JsonObject jsonObject;

		public Message(String json) {
			jsonObject = new Gson().fromJson(json, JsonObject.class);
		}


		public JsonElement get(String key) {
			return jsonObject.get(key);
		}

		public Message detail() {
			return new Message(get("detail").getAsString());
		}
	}

	private static class Factorial {
		private final int number;

		public Factorial(int number) {
			this.number = number;
		}

		public int result() {
			return IntStream.range(2,number+1).reduce(1, (a,i)->a*i);
		}
	}
}
