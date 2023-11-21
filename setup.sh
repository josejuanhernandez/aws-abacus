aws sqs create-queue --queue-name "requests"
aws sqs create-queue --queue-name "responses"

aws events create-event-bus --name "abacus-bus"

aws s3 mb "s3://datalake"

aws s3 mb "s3://abacus-artifacts"
aws s3 cp "factorial-feeder/target/factorial-feeder.jar" "s3://abacus-artifacts/factorial-feeder.jar"
aws s3 cp "factorial-calculator/target/factorial-calculator.jar" "s3://abacus-artifacts/factorial-calculator.jar"

aws iam create-policy --policy-name "service-execution-policy" --policy-document "file://setup/policy.service-execution.json"
aws iam create-role --role-name "service-role" --assume-role-policy-document "file://setup/policy.role.json"
aws iam attach-role-policy --role-name "service-role" --policy-arn "arn:aws:iam::000000000000:policy/service-execution-policy"

#aws lambda create-function --cli-input-json file://setup/service.factorial-feeder.json --code S3Bucket=abacus-artifacts,S3Key=factorial-feeder.jar
aws lambda create-function --cli-input-json file://setup/service.factorial-calculator.json --code S3Bucket=abacus-artifacts,S3Key=factorial-calculator.jar

aws events put-rule --name "sqs-request-rule" --event-pattern "file://setup/event-pattern.sqs-request.json"
aws events put-targets --rule "sqs-request-rule" --targets "Id"="1","Arn"="arn:aws:lambda:us-east-1:000000000000:function:factorial-feeder"

aws events put-rule --name "factorial-calculation-rule" --event-bus "abacus-bus" --event-pattern "file://setup/event-pattern.factorial-calculation.json"
aws events put-targets --rule "factorial-calculation-rule" --event-bus "abacus-bus" --targets "Id"="1","Arn"="arn:aws:lambda:us-east-1:000000000000:function:factorial-calculator"
