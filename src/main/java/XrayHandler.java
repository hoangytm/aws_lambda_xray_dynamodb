import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

public class XrayHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object event, Context context) {
        System.out.println("Event: " + event);

        Segment segment = AWSXRay.beginSegment("LambdaFunction");

        try {
            System.out.println("Fetching items from DynamoDB table...");
            DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                    .region(Region.AP_SOUTHEAST_1)
                    .build();
            ScanRequest request = ScanRequest.builder()
                    .tableName("test")
                    .build();
            ScanResponse response = dynamoDbClient.scan(request);
            System.out.println("we has already had response");
            System.out.println("Response: " + response);
            segment.end();
            if (!response.items().isEmpty()) {
                segment.putAnnotation("ItemsFetched", true);
                segment.putMetadata("DynamoDBItems", response.items());
                return "Items retrieved: " + response.items().toString();
            } else {
                segment.putAnnotation("ItemsFetched", false);
                return "No items found.";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println(e);
            throw new RuntimeException(e);
        } finally {
            AWSXRay.endSegment();
        }
    }
}