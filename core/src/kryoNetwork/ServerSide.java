package kryoNetwork;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class ServerSide { // Only here to relay server/client messages and update the Users arraylist for everyone
    public String text;
    
    int Map;
    
    List<User> Users;
 }