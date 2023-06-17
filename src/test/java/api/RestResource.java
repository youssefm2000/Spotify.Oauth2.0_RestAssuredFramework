package api;

import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import java.util.HashMap;

import static api.Route.Api;
import static api.Route.Token;
import static api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {


    public static Response post(  String path ,String token , Object requestPlaylist){

        return given().
                spec(getrequestspec()).
                body(requestPlaylist).
                auth().oauth2(token).
                when().
                post(path).
                then().
                spec(getresponsesspec()).
                extract().
                response();

    }
    public static Response postaccount(HashMap<String,String> formparam){


        return given(getaccountrequestspec()).
                formParams(formparam).
                when().
                post(Api+Token).
                then().spec(getresponsesspec()).
                extract().response();

    }

    public static Response Get(String path,String token){

        return given().
                spec(getrequestspec()).
                auth().oauth2(token).
                when().
                get(path).
                then().
                spec(getresponsesspec()).
                extract().response();

    }


  public  static Response Update(String path,String token, Object requestPlaylist){

              return given().
              spec(getrequestspec()).
              body(requestPlaylist).
                      auth().oauth2(token).
              when().
              put(path).
              then().
              spec(getresponsesspec()).defaultParser(Parser.JSON).extract().response();


  }




}
