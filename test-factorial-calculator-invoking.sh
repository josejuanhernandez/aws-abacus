aws lambda invoke --function-name factorial-calculator --payload eyJzb3VyY2UiOiAidGVzdCIsICJkZXRhaWwtdHlwZSI6ICJmYWN0b3JpYWwtY2FsY3VsYXRpb24iLCAiZGV0YWlsIjogInsgXCJudW1iZXJcIjogNiB9IiwgImV2ZW50LWJ1cy1uYW1lIjogImFiYWN1cy1idXMifQ==  response.txt
aws logs tail /aws/lambda/factorial-calculator
aws sqs receive-message --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/responses
