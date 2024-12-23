# Lambda, DynamoDB, and X-Ray Setup Guide

## Prerequisites
- AWS account with necessary permissions
- IAM Role with permissions for Lambda, DynamoDB, and X-Ray
- AWS SDK and X-Ray SDK installed (for Lambda function)

## Steps

### 1. **Create a DynamoDB Table**
1. Open the [DynamoDB Console](https://console.aws.amazon.com/dynamodb/).
2. Click **Create table**.
3. Define the **Table Name** and **Primary Key** (e.g., `ID` as Partition Key).
4. Set up **Provisioned or On-Demand capacity** depending on needs.
5. Click **Create**.

### 2. **Create an IAM Role for Lambda**
1. Go to the [IAM Console](https://console.aws.amazon.com/iam/).
2. Create a new IAM role with the following policies:
    - `AWSLambdaBasicExecutionRole` (for CloudWatch Logs).
    - `AmazonDynamoDBFullAccess` (for DynamoDB operations).
    - `AWSXRayDaemonWriteAccess` (for X-Ray integration).
3. Attach the role to the Lambda function.
4. enable X-Ray tracing in the Lambda function configuration.

### 3. **Create the Lambda Function**
1. Open the [Lambda Console](https://console.aws.amazon.com/lambda/).
2. Click **Create function** > **Author from scratch**.
3. Choose the runtime (e.g., Node.js, Python).
4. In the **Execution Role**, select the IAM role created above.
5. Add the function code to interact with DynamoDB (CRUD operations).

