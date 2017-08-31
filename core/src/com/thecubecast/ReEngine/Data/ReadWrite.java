package com.thecubecast.ReEngine.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class ReadWrite {
	
	public void init() { //Create the folders that hold everything neatly
		Path path = Paths.get("Saves");
		//Path p3 = Paths.get(URI.create("http:///dev.thecubecast.com/Login.php?User=BLANK"));
		
		if (Files.notExists(path)) {
			new File("Saves").mkdir();
			Common.print("Created 'Saves' folder!");
		}
	}
	
	public static Object[] LoadSettings() {
		//all the code that reads the file
		
		Path SettingsPath = Paths.get("Settings.properties");
		Object[] settup = null;
		
		if (Files.notExists(SettingsPath)) { // runs if the settings file does not exist
			//create new settings file with universal settings that work for everyone 
			Path path = Paths.get("", "Settings.properties");
			ArrayList<String> lines = new ArrayList<String>();
	        lines.add("#Settings");
	        lines.add("\n");
	        lines.add("Agreed:False");
			try {
				Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
			} catch (IOException e) {e.printStackTrace();}
		}
		else {
			//save each setting to a value in the array
			try {
				settup = Files.readAllLines(SettingsPath).toArray();
			} catch (IOException e) {e.printStackTrace();}
			
		}
		return settup; // returns the array containing each value in settings. Settings file format can be dynamically changed to add new values.
		
	}
	
	public boolean CreateSave(String Title) {
		//creates the world folder
		new File("Saves/"+Title).mkdir();
		new File("Saves/"+Title+"/Chunks").mkdir();
		Common.print("Created '"+ Title +"' save!");
		//creates the chunk folder and populates it with the starting chunks.
		//for (int i=1; i < 10; i++) {
		//	if(i == 1 || i == 2 || i == 3) {
			//	int id[] = new int[] {0,i};
			//	CreateChunk(Title, id, 16);
		//	}
			int id1[] = new int[] {-1,1};
			CreateChunk(Title, id1, 16);
			id1 = null;
			int id2[] = new int[] {1,1};
			CreateChunk(Title, id2, 16);
			id2 = null;
			int id3[] = new int[] {2,1};
			CreateChunk(Title, id3, 16);
			id3 = null;
			int id4[] = new int[] {-1,-1};
			CreateChunk(Title, id4, 16);
			id4 = null;
			int id5[] = new int[] {1,-1};
			CreateChunk(Title, id5, 16);
			id5 = null;
			int id6[] = new int[] {2,-1};
			CreateChunk(Title, id6, 16);
			id6 = null;
			int id7[] = new int[] {-1,-2};
			CreateChunk(Title, id7, 16);
			id7 = null;
			int id8[] = new int[] {1,-2};
			CreateChunk(Title, id8, 16);
			id8 = null;
			int id9[] = new int[] {2,-2};
			CreateChunk(Title, id9, 16);
			id9 = null;
		
		//returns true or false depending on whether world files were successfully loaded
		return true;
		//the chunks are loaded independently from the world creation.
	}
	
	//Creates a new chunk
	public boolean CreateChunk(String Save, int[] id, int ChunkSize) {
		
		if(id[1] > 1) {
			//Don't create the chunks that don't need to exist
		}
		
		if(id[1] == 1) {
			//These are the chunks above y=0, They have nothing.
			//Maybe Buildings at some point, trees. Decoration
			ArrayList<String> lines = new ArrayList<String>();
			for (int i=1; i < (ChunkSize + 1); i++) {
				if (i < (ChunkSize)) {
					lines.add("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
				} else {
					lines.add("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~"); // This sets up a impenetrable block	
				}
			}
			try {
				Files.write(Paths.get("Saves/"+Save+"/Chunks", "Chunk_"+id[0]+"_"+id[1]+".dat"), lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
			} catch (IOException e) {e.printStackTrace();}
		}
		
		if(id[1] < 0) {
			ArrayList<String> lines = new ArrayList<String>();
			for (int i=1; i < (ChunkSize + 1); i++) {
				lines.add("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
			}
			try {
				Files.write(Paths.get("Saves/"+Save+"/Chunks", "Chunk_"+id[0]+"_"+id[1]+".dat"), lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
			} catch (IOException e) {e.printStackTrace();}
		}
		return true;
	}
	
	//Loads the chunk from file and adds its 2d array to memory
	public boolean LoadChunk(int[] id) {
		//int[] id is the chunk id, EX (1, -4) or 1 chunk to the right and down 4 from origin 
		
		return true;
	}
	
	//This is run when the player mines a block, changing the 2d array of the chunk
	//This takes the 2d array and rewrites the chunk on file
	public boolean UpdateChunk() {
		
		return true;
	}
	
	//This unloads the chunk from memory
	public boolean UnloadChunk() {
		
		return true;
	}
}