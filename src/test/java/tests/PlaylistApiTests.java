package tests;

import applicationapi.PlaylistApi;
import io.qameta.allure.*;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pojo.InnerError;
import pojo.Playlist;
import utils.DataLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.FakerUtils.generateDescription;
import static utils.FakerUtils.generateName;

@Epic("Spotify Oauth 2.0")
@Feature("Playlist Api")
public class PlaylistApiTests {
    @Story("Create A Playlist Story")
    @Description("Added Three Items of Api Such As Name ,Description and PublicValue ")
    @Test(priority = 1,description = "Add Api Playlist ")
    public void AddApiPlaylist() {

        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatuscode(response.statusCode(),201);
        assertPlaylistisEqual(response.as(Playlist.class),requestPlaylist);


    }

    @Description("Get Items of Api That We added it Before ")
    @Test(priority = 2,description = "Get Api Playlist")
    public void GetApiPlaylist() {
        Playlist requestPlaylist = playlistBuilder("New playlist with pojo","New playlist description with pojo",false);

        Response response = PlaylistApi.Get(DataLoader.getInstance().getGetPlaylistId());
        assertStatuscode(response.statusCode(),200);
        assertPlaylistisEqual(response.as(Playlist.class),requestPlaylist);


    }

    @Description("Update value of Items with new value")
    @Test(priority = 3,description = "Update Api Playlist")
    public void UpdateApiPlaylist() {

        Playlist requestPlaylist = playlistBuilder("New playlist with pojo","New playlist description with pojo",false);

        Response response = PlaylistApi.Update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatuscode(response.statusCode(),200);


    }

    //negative scenario

    //add apiplaylist without name in request body of json
    @Story("Create A Playlist Story")
    @Description("We Check That how Will be Api without value of name")
    @Test(priority = 4,description = "Add Api Playlist Without name")
    public void AddApiPlaylistwithoutname() {
        Playlist requestPlaylist = playlistBuilder("","New playlist description with pojo",false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertStatuscode(response.statusCode(),400);
        assertError(response.as(InnerError.class),400,"Missing required field: name");

    }

    //negative scenario
    //add apiplaylist without access token in request body of json
    @Story("Create A Playlist Story")
    @Description("We Check That how Will be Api without value of access token")
    @Test(priority = 5,description = "Add Api Playlist Without Access Token")
    public void AddApiPlaylistwithoutaccesstoken() {
    String invalidtoken ="12345";
        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
        Response response = PlaylistApi.post(invalidtoken,requestPlaylist);
        assertStatuscode(response.statusCode(),401);
        assertError(response.as(InnerError.class), 401 ,"Invalid access token");


    }


    public Playlist playlistBuilder(String name,String description , boolean publicvalue){
     Playlist playlist = new Playlist();
     playlist.setName(name);
     playlist.setDescription(description);
     playlist.set_public(publicvalue);
     return playlist;

    }
    public void assertPlaylistisEqual(Playlist responsePlaylist, Playlist requestPlaylist){

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));

    }
    public void assertStatuscode(int actaulstatuscode , int expectedstatuscode){
        assertThat(actaulstatuscode,equalTo(expectedstatuscode));
    }
    public void assertError(InnerError error , int expectedstatuscode , String expectedmessage){
        assertThat(error.getError().getMessage(),equalTo(expectedmessage));
        assertThat(error.getError().getStatus(),equalTo(expectedstatuscode));

    }


}
