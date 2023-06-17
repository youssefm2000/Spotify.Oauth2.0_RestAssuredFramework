package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ConfigLoader;

import java.time.Instant;
import java.util.HashMap;

import static api.SpecBuilder.getresponsesspec;
import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String access_token;
    private static Instant expiry_time;
    public static String gettoken() {

        try {
            if (access_token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing Token ....");
                Response response = renewaccesstoken();
                access_token = response.path("access_token");
                int expiryinseconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryinseconds - 300);
            }else {
                System.out.println("Token is good to use");
            }

        }
        catch (Exception e){
            throw new RuntimeException(" !!! renew token failed");
        }
       return access_token;

    }


       private static Response renewaccesstoken(){

        HashMap<String,String> formparam = new HashMap<String,String>();
        formparam.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formparam.put("refresh_token",ConfigLoader.getInstance().getRefreshToken());
        formparam.put("client_id",ConfigLoader.getInstance().getClientId());
        formparam.put("client_secret",ConfigLoader.getInstance().getClientSecret());

        Response response =RestResource.postaccount(formparam);

       if (response.statusCode() !=200){

           throw new RuntimeException(" !!! renew token failed");
       }
      return response;

    }
}
