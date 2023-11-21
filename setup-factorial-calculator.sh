cd factorial-calculator
mvn package
cd ..

aws lambda delete-function --function-name factorial-calculator
aws s3 cp "factorial-calculator/target/factorial-calculator.jar" "s3://abacus-artifacts/factorial-calculator.jar"
aws lambda create-function --cli-input-json file://setup/service.factorial-calculator.json --code S3Bucket=abacus-artifacts,S3Key=factorial-calculator.jar

