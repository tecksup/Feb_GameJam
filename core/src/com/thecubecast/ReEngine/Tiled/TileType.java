package com.thecubecast.ReEngine.Tiled;

import java.util.HashMap;

public enum TileType {
	
	DIRT(1, 00, true, "DIRT"),
	DIRT_DESTROYED(26, 25, true, "DIRT_DESTROYED"),
	GRASS(2, 01, true, "Grass"),
	GRASS_DESTROYED(25, 24, true, "GRASS_DESTROYED"),
	STONE(9, 8, true, "STONE"),
	STONE_DESTROYED(34, 33, true, "STONE_DESTROYED"),
	AIR(8, 07, false, "AIR");
	
	public static final int Tile_Size = 16;
	
	private int id;
	private int TextureID;
	private boolean Collidable;
	private String name;
	private float Strength;
	
	private TileType (int id,int TextureID, boolean Collidable, String name) {
		this(id, TextureID, Collidable, name, 0);
	}
	
	private TileType (int id, int TextureID, boolean Collidable, String name, float Strength) {
		this.id = id;
		this.TextureID = TextureID;
		this.Collidable = Collidable;
		this.name= name;
		this.Strength = Strength;
	}

	public int getId() {
		return id;
	}

	public int getTextureID() {
		return TextureID;
	}

	public boolean isCollidable() {
		return Collidable;
	}

	public String getName() {
		return name;
	}

	public float getStrength() {
		return Strength;
	}
	
	private static HashMap<Integer , TileType> tileMap;
	
	static {
		tileMap = new HashMap<Integer, TileType>();
		for(TileType tileType : TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileTypeById (int id) {
		return tileMap.get(id);
	}
	
	
}