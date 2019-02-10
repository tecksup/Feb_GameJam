package com.thecubecast.ReEngine.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Draw {

    public AssetManager manager = new AssetManager();

    //Animation Variables
    public Animation<TextureRegion> LoadingAnimation; // Must declare frame type (TextureRegion)
    Texture LoadingSheet;

    BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/Pixel.fnt"), new TextureRegion(new Texture(Gdx.files.internal("Fonts/Pixel.png"))));

    public void Init() {
        font.getData().markupEnabled = true;

        // Initialize the Animation with the frame interval and array of frames
        LoadingAnimation = new Animation<TextureRegion>(0.1f, loadAnim(LoadingSheet, "cube_loading_sprite.png", 4, 1));
    }

    public static TextureRegion[] loadAnim(Texture TexSheet, String FileLocation, int Cols, int Rows) {
        // Load the sprite sheet as a Texture
        TexSheet = new Texture(Gdx.files.internal(FileLocation));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(TexSheet,
                TexSheet.getWidth() / Cols,
                TexSheet.getHeight() / Rows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[Cols * Rows];
        int index = 0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        return walkFrames;
    }

    public void DrawAnimatedTile(SpriteBatch buffer, Animation<TextureRegion> animation_, int x, int y, float stateTime) {
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation_.getKeyFrame(stateTime, true);

        buffer.draw(currentFrame, x, y);
    }

    public void GUIDrawText(SpriteBatch buffer, int PosX, int PosY, String Text, Color color) {
        font.setColor(color);
        font.draw(buffer, Text, PosX, PosY);
        font.setColor(Color.WHITE);
    }

    public ShapeRenderer debugRenderer = new ShapeRenderer();

    public void DrawDebugLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix) {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    public void DrawDebugPoint(Vector2 start, int lineWidth, Color color, Matrix4 projectionMatrix) {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        debugRenderer.circle(start.x, start.y, lineWidth);
        debugRenderer.setColor(color);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

}