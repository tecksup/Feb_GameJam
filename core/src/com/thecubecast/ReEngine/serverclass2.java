package com.thecubecast.ReEngine;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import kryoNetwork.KryoServer;

public class serverclass2 extends ApplicationAdapter {
	
	public void create () { // INIT FUNCTION
		KryoServer server = new KryoServer();
		try {
			server.main();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}