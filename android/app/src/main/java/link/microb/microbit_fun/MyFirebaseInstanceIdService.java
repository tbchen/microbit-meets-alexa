package link.microb.microbit_fun;

import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by chentb on 2016/12/10.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("", "Refreshed token: " + refreshedToken);
        //amazon cognito
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:1dd5feb1-d3d4-4adf-a892-81d539a8b6eb", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        final String SNS_Platform_Application_Arn = "arn:aws:sns:us-west-2:146859495932:app/GCM/microbit_fly";
        final String SNS_Topic_Arn = "arn:aws:sns:us-west-2:146859495932:testtopic";
        final Regions Default_Service_Region_Type = Regions.US_WEST_2;

        AmazonSNSClient snsClient = new AmazonSNSClient(credentialsProvider);
        snsClient.setRegion(Region.getRegion(Default_Service_Region_Type));

        CreatePlatformEndpointRequest createRequest = new CreatePlatformEndpointRequest();
        createRequest.setToken(refreshedToken);
        createRequest.setPlatformApplicationArn(SNS_Platform_Application_Arn);
        CreatePlatformEndpointResult platformEndpoint = snsClient.createPlatformEndpoint(createRequest);

        String endpointArn = platformEndpoint.getEndpointArn();
        snsClient.subscribe(SNS_Topic_Arn, "application", endpointArn); // 作成したトピックARN
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
}
