// Blueprint for all GameState subclasses.
// Has a reference to the GameStateManager
// along with the four methods that must
// be overridden.

package com.thecubecast.ReEngine.GameStates;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thecubecast.ReEngine.Data.GameStateManager;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(SpriteBatch g, int width, int height, float Time);
	
	public void RenderCam() {}

	public void reSize(SpriteBatch g, int h, int w) {}
	
}
