package software.abacus;

import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.EventBridgeClientBuilder;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;

import java.net.URI;

public class AwsFactory {
	private static final String LocalStackHostName	 = System.getenv("LOCALSTACK_HOST");
	private static final String LocalStackUrl = "http://" + LocalStackHostName + ":4566";

	public static SqsClient sqs() {
		return decorate(SqsClient.builder()).build();
	}

	public static EventBridgeClient eventBridge() {
		return decorate(EventBridgeClient.builder()).build();
	}

	private static EventBridgeClientBuilder decorate(EventBridgeClientBuilder builder) {
		return LocalStackHostName == null ? builder : builder
				.endpointOverride(URI.create(LocalStackUrl));
	}

	private static SqsClientBuilder decorate(SqsClientBuilder builder) {
		return LocalStackHostName == null ? builder : builder
				.endpointOverride(URI.create(LocalStackUrl));
	}

}
