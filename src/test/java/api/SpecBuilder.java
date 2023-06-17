package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.Route.Base_path;

public class SpecBuilder {

    // methods for fixed information such as baseuri and others for all test cases

    public static RequestSpecification getrequestspec(){
        return new RequestSpecBuilder().
        setBaseUri("https://api.spotify.com").
        setBasePath(Base_path).

        setContentType(ContentType.JSON).
        log(LogDetail.ALL).
                build();


    }
    public static RequestSpecification getaccountrequestspec(){
        return new RequestSpecBuilder().
                setBaseUri("https://accounts.spotify.com").
                setContentType(ContentType.URLENC).
                log(LogDetail.ALL).
                build();


    }


    public  static ResponseSpecification getresponsesspec(){

        return new ResponseSpecBuilder().
        log(LogDetail.ALL).
                build();


    }

}
