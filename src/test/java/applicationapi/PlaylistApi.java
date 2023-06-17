package applicationapi;

import api.RestResource;
import io.restassured.response.Response;
import pojo.Playlist;
import utils.ConfigLoader;

import static api.Route.Playlists;
import static api.Route.Users;
import static api.TokenManager.gettoken;

public class PlaylistApi {


    public static Response post(Playlist requestPlaylist){
         return RestResource.post(Users +"/"+ ConfigLoader.getInstance().getUser() + Playlists,gettoken(),requestPlaylist);


    }
    public static Response post( String token ,Playlist requestPlaylist){
     return RestResource.post(Users +"/"+ ConfigLoader.getInstance().getUser()+ Playlists, token, requestPlaylist);

    }


    public static Response Get(String playlistid){
     return RestResource.Get(Playlists +"/"+playlistid, gettoken());

    }


  public  static Response Update(String playlistid,Playlist requestPlaylist){
     return RestResource.Update(Playlists +"/"+playlistid , gettoken() ,requestPlaylist);

  }


}
