package com.thecubecast.ReEngine.Data.TkMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TkTileset {

    String Name;
    String FilePath;
    int TileSizeW;
    int TileSizeH;
    int TileSep;

    Texture TilesetImage;
    TextureRegion[] Tiles;

    public TkTileset(String name, String Path, int tileSizeW, int tileSizeH, int tileSep) {
        Name = name;
        FilePath = Path;
        TileSizeW = tileSizeW;
        TileSizeH = tileSizeH;
        TileSep = tileSep;
        TilesetImage = new Texture(Gdx.files.internal(FilePath));

        int cols = TilesetImage.getWidth()/TileSizeW;
        int rows = TilesetImage.getHeight()/TileSizeH;

        Tiles = new TextureRegion[rows * cols];

        TextureRegion[][] tmp = TextureRegion.split(TilesetImage,
                TileSizeW,
                TileSizeH);

        int index = 0;
        for (int l = 0; l < TilesetImage.getHeight()/TileSizeH; l++) {
            for (int j = 0; j < TilesetImage.getWidth()/TileSizeW; j++) {
                Tiles[index++] = tmp[l][j];
            }
        }

    }

    public TextureRegion[] getTiles() {
        return Tiles;
    }
}
