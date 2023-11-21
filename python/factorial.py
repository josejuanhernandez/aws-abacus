import os
import json
import boto3

def handler(event, context):
    hostname = os.environ.get('LOCALSTACK_HOSTNAME')
    print(hostname)

    print(event)
    number = event.get("detail").get('number', 1)
    print("calculate " + str(number))

    factorial = 1
    for i in range(2, number + 1):
        factorial *= i

    message = {
         'number': number,
         'factorial': factorial
    }
    event = {
        'Source': 'factorial-calculator',
        'DetailType': 'factorial-result',
        'Detail': json.dumps(message),
        'EventBusName': 'abacus-bus'
    }    
    print(event)

    try:
        client = boto3.client('events', region_name='us-east-1', endpoint_url="http://" + hostname + ":4566")
        client.put_events(Entries=[event])
    except Exception as e:
        print(e)

    print("return")

    return {
     'statusCode': 200,
     'body': json.dumps(message)
    }
