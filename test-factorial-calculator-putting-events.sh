aws events put-events --entries file://tests/factorials.json
aws logs tail /aws/lambda/factorial-calculator
aws sqs receive-message --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/responses
