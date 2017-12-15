package com.thecubecast.ReEngine.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class ReadWrite {
	
	static Random rand;
	
	public void init() { //Create the folders that hold everything neatly
		rand = new Random();
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

		
		//returns true or false depending on whether world files were successfully loaded
		return true;
		//the chunks are loaded independently from the world creation.
	}
	
	//Does the Tiled Serialization
	public boolean CreateWorld(String Save, int width, int height) {
		
		Path checkPath = Paths.get("Saves/" + Save, "");
		if (Files.notExists(checkPath) != true) {
			return false;
		}
		
		int heightLeftToFill = height;
		
		Path path = Paths.get("Saves/" + Save, "map.tmx");
		new File("Saves/"+Save).mkdir();
		new File("Saves/"+Save+"/tileset").mkdir();
		
		File source = new File("WorldGen/untitled.tsx");
		File dest = new File("Saves/" + Save + "");
		File source1 = new File("WorldGen/tileset/packed.atlas");
		File source2 = new File("WorldGen/tileset/packed.png");
		File dest1 = new File("Saves/" + Save + "/tileset");
		try {
		    FileUtils.copyFileToDirectory(source, dest);
		    FileUtils.copyFileToDirectory(source1, dest1);
		    FileUtils.copyFileToDirectory(source2, dest1);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		ArrayList<String> lines = new ArrayList<String>();
        lines.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><map height=\"" + height + "\" nextobjectid=\"10\" orientation=\"orthogonal\" renderorder=\"right-down\" tiledversion=\"1.0.3\" tileheight=\"16\" tilewidth=\"16\" version=\"1.0\" width=\"" + width + "\"><properties><property name=\"atlas\" value=\"tileset/packed.atlas\"/></properties> <Custom playerSpawnX=\"" + (width/2) + "\" playerSpawnY=\"" + (height-11) + "\"  />\r\n" + 
        		" <tileset firstgid=\"1\" source=\"untitled.tsx\"/>\r\n" + 
        		" <layer height=\"" + height + "\" name=\"BACKGROUND\" width=\"" + width + "\">\r\n" + 
        		"  <data encoding=\"csv\">");
        
        for(int y = 0; y <= 11; y++) {//	Generates the Sky tiles above the grass
        	String line = "";
        	for(int x = 0; x < width; x++) { 
        		line = line + "8,";
        	}
        	lines.add(line);
        	heightLeftToFill--;
        }        		
        
        String BackGrass = "";
        for(int x = 0; x < width; x++) {  // 	Generates the background grass layer
        	BackGrass = BackGrass + "25,";
    	}
    	lines.add(BackGrass);
    	heightLeftToFill--;
        
    	int tempHeightLeft = heightLeftToFill;
    	for(int y = 0; y <= (tempHeightLeft-1); y++) {//	Generates the background underground tiles above the grass
        	String line = "";
        	for(int x = 0; x < width; x++) { 
        		if (width%3 == 1) {
        			line = line + "61,";
        		} else if (width%3 == 2) {
        			line = line + "61,62,";
        			x++;
        		} else {
        			line = line + "61,62,63,";
        			x++;
        			x++;
        		}
        	}
        	lines.add(line);
        	heightLeftToFill--;
        }    
    	
        lines.add("</data>\r\n" + 
        		" </layer>\r\n" + 
        		" <layer height=\"" + height + "\" name=\"TILES\" width=\"" + width + "\">\r\n" + 
        		"<properties>\r\n" + 
        		"   <property name=\"Cash\" type=\"int\" value=\"50\"/>\r\n" + 
        		"   <property name=\"Fuel\" type=\"float\" value=\"45\"/>\r\n" + 
        		"   <property name=\"SavedX\" type=\"float\" value=\"" + (width/2 - 6) + "\"/>\r\n" + //DEFAULT WAS 30
        		"   <property name=\"SavedY\" type=\"float\" value=\"" + (height - 12) + "\"/>\r\n" +   //DEFAULT IS height-12
        		"  </properties>" + 
        		"  <data encoding=\"csv\">");
        
        
        	//Below is the Tiles layer
        	//Below is the Tiles layer
        	
        	
        heightLeftToFill = height;
        for(int y = 0; y <= 11; y++) {//	Generates the Sky tiles above the grass
        	String line = "";
        	
        	for(int x = 0; x < width; x++) { 
        		if (y == 10) { 
        			if (x == (width/2) - 9) {
        				line = line + "78,";
        			} else if (x == (width/2) - 8) {
        				line = line + "79,";
        			} else if (x == (width/2) - 7) {
        				line = line + "80,";
        			} else {
        				line = line + "95,";
        			}
        		} else if (y == 11) {
        			int temp = rand.nextInt(4);
        			if (x == (width/2)) {
        				line = line + "74,";
        			} else if (x == (width/2) - 9) {
        				line = line + "75,";
        			} else if (x == (width/2) - 8) {
        				line = line + "76,";
        			} else if (x == (width/2) - 7) {
        				line = line + "77,";
        			} else if (temp == 0) {
        				line = line + "3,";
        			} else if (temp == 1) {
        				line = line + "4,";
        			} else if (temp == 2) {
        				line = line + "95,";
        			} else if (temp == 3) {
        				line = line + "95,";
        			}
        			
        		} else {
        			line = line + "95,";
        		}
        	}
        	lines.add(line);
        	heightLeftToFill--;
        }
        
        String Grass = "";
        for(int x = 0; x < width; x++) { // 	Generates the Grass Tiles on the surface
        	Grass = Grass + "2,";
    	}
    	lines.add(Grass);
    	heightLeftToFill--;
    	
    	
    	//THIS IS WERE THE RANDOM SHIT STARTS HAPPENING
    	//THIS IS WERE THE RANDOM SHIT STARTS HAPPENING
    	//THIS IS WERE THE RANDOM SHIT STARTS HAPPENING
    	
    	int tempHeightLeftFront = heightLeftToFill;
        for(int y = 0; y <= (tempHeightLeftFront-1); y++) {//	Generates the dirt tiles under the grass
        	//y is the amount of lines we have to work with until we reach the max world size.
        	
        	String line = "";
        	for(int x = 0; x < width; x++) { 
        		if (y <= 1) {
        			line = line + "1,";
        		} else if (y >= 1 && y <= 10) { // BETWEEN LAYERS 3 & 10 ARE DIRT WITH COAL AND STONE
        			int temp = rand.nextInt(16);
        			if (temp == 1 || temp == 3) {
        				line = line + "11,"; //COAL
        			} else if (temp == 2 ) {
        				line = line + "10,"; //STONE VEIN
        			} else {
        				line = line + "1,";
        			}
        			
        		} else if (y >= 10) {
        			if (y == 11) { 
        				int temp = rand.nextInt(3);
        				if (temp == 0 || temp == 1) {
            				line = line + "1,"; //Dirt
            			} else {
            				line = line + "9,"; //STONE
            			}
        			}
        			if (y > 11) {
        				int temp = rand.nextInt(26);
        				if (temp == 0 || temp == 1) {
            				line = line + "21,"; //Silver
            			} else if (temp == 2 || temp == 3) {
            				line = line + "22,"; //Copper
            			}else {
            				line = line + "9,"; //STONE
            			}
        			}
        		}
        	}
        	
        	
        	lines.add(line);
        	heightLeftToFill--;
        }
        
        
        //THIS IS WERE THE RANDOM SHIT STOPS HAPPENING
    	//THIS IS WERE THE RANDOM SHIT STOPS HAPPENING
    	//THIS IS WERE THE RANDOM SHIT STOPS HAPPENING
        
        
        lines.add("</data>\r\n" + //Just wrapping up the file
        		" </layer>\r\n" + 
        		"</map>");
        
		try {
			Files.deleteIfExists(path);
			Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
		} catch (IOException e) {e.printStackTrace();}
		return false;
	}
	
	public boolean saveMap(TiledMap tiledMap, String Save, Player player) {
		
		int width = tiledMap.getProperties().get("width", Integer.class);
		int height = tiledMap.getProperties().get("height", Integer.class);

		Path path = Paths.get("Saves/" + Save, "map.tmx");
		
		ArrayList<String> lines = new ArrayList<String>();
        lines.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><map height=\"" + height + "\" nextobjectid=\"10\" orientation=\"orthogonal\" renderorder=\"right-down\" tiledversion=\"1.0.3\" tileheight=\"16\" tilewidth=\"16\" version=\"1.0\" width=\"" + width + "\"><properties><property name=\"atlas\" value=\"tileset/packed.atlas\"/></properties> <Custom playerSpawnX=\"" + (width/2) + "\" playerSpawnY=\"" + (height-11) + "\"  />\r\n" + 
        		" <tileset firstgid=\"1\" source=\"untitled.tsx\"/>\r\n" + 
        		" <layer height=\"" + height + "\" name=\"BACKGROUND\" width=\"" + width + "\">\r\n" + 
        		"  <data encoding=\"csv\">");
        
        TiledMapTileLayer BackLay = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        for(int y = 0; y < height; y++) {//	Generates the Background tiles
        	String line = "";
        	for(int x = 0; x < width; x++) { 
        		int tempTileID = BackLay.getCell(x, y).getTile().getId();
        		line = line + tempTileID + ",";
        	}
        	lines.add(line);
        }        		
        
        
        
        
        
        
        //Splits the file between background and Tiled layer
        lines.add("</data>\r\n" + 
        		" </layer>\r\n" + 
        		" <layer height=\"" + height + "\" name=\"TILES\" width=\"" + width + "\">\r\n" + 
        		"<properties>\r\n" + 
        		"   <property name=\"Cash\" type=\"int\" value=\"" + player.Cash + "\"/>\r\n" + 
        		"   <property name=\"Fuel\" type=\"float\" value=\"" + player.Gas + "\"/>\r\n" + 
        		"   <property name=\"SavedX\" type=\"float\" value=\"" + player.getLocation()[0] + "\"/>\r\n" + //DEFAULT WAS 30
        		"   <property name=\"SavedY\" type=\"float\" value=\"" + player.getLocation()[1] + "\"/>\r\n" +   //DEFAULT IS height-12
        		"  </properties>" + 
        		"  <data encoding=\"csv\">");
        
        
        
        
        
        TiledMapTileLayer FrontLay = (TiledMapTileLayer)tiledMap.getLayers().get("TILES");
        for(int y = 0; y < height; y++) {//	Generates the main tiles
        	String line = "";
        	for(int x = 0; x < width; x++) { 
        		int tempTileID = FrontLay.getCell(x, y).getTile().getId();
        		line = line + tempTileID + ",";
        	}
        	lines.add(line);
        }      
        
        lines.add("</data>\r\n" + //Just wrapping up the file
        		" </layer>\r\n" + 
        		"</map>");
        
		try {
			Files.deleteIfExists(path);
			Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
		} catch (IOException e) {e.printStackTrace();}
		return false;
	}
}