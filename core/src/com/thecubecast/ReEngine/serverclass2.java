package com.thecubecast.ReEngine;

import com.badlogic.gdx.ApplicationAdapter;
import kryoNetwork.KryoServer;

import java.io.IOException;

public class serverclass2 extends ApplicationAdapter {

    public void create() { // INIT FUNCTION
        KryoServer server = new KryoServer();
        try {
            server.main();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}