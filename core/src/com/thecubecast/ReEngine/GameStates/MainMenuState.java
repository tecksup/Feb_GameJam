// GameState that shows Main Menu.

package com.thecubecast.ReEngine.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.thecubecast.ReEngine.Data.GameStateManager;
import com.thecubecast.ReEngine.Data.controlerManager;
import com.thecubecast.ReEngine.Graphics.Scene2D.UIFSM;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import static com.thecubecast.ReEngine.Data.GameStateManager.AudioM;


public class MainMenuState extends GameState {

    OrthographicCamera cameraGui;
    UIFSM Menus;

    int BGMusicID;

    TextureAtlas.AtlasRegion Background;
    Texture Title;

    public MainMenuState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        Background = gsm.Render.getTexture("test");
        Title = new Texture(Gdx.files.internal("Sprites/Title.png"));

        gsm.DiscordManager.setPresenceState("In Menus");

        cameraGui = new OrthographicCamera();

        Menus = new UIFSM(cameraGui, gsm);

        BGMusicID = AudioM.playMusic("NoName.wav", true, true);
    }

    public void update() {
        handleInput();

        cameraGui.update();
    }

    public void draw(SpriteBatch bbg, int height, int width, float Time) {

        cameraGui.setToOrtho(false, width, height);
        bbg.setProjectionMatrix(cameraGui.combined);
        bbg.begin();

        bbg.draw(Background, 0, 0, width, height);
        bbg.draw(Title, width / 2 - (200 / 2), 200, 200, 28);

        bbg.end();


    }

    public void drawUI(SpriteBatch g, int height, int width, float Time) {
        //Draws things on the screen, and not the world positions
        cameraGui.setToOrtho(false, width, height);
        g.setProjectionMatrix(cameraGui.combined);
        g.begin();
        //GUI must draw last
        Menus.Draw(g);
        g.end();
    }


    public void handleInput() {

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            //JukeBox.stop("MenuNavigate");
            //Click.play((SoundVolume * MasterVolume),1,0);
            //Check what button the user is on, runs its function
        }

        if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
            //JukeBox.stop("MenuNavigate");

            //Moves the Chosen button RIGHT
        }

        if (GameStateManager.ctm.isButtonJustDown(0, controlerManager.buttons.BUTTON_START)) {

        }

    }

    public void reSize(SpriteBatch g, int H, int W) {
        //stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
        cameraGui.setToOrtho(false);
        //Menus.reSize(cameraGui);
    }


    public String GetLogin(String email, String Password) throws Exception {
        String url = "https://dev.thecubecast.com/game/login_game.php";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.addRequestProperty("email", email);
        con.addRequestProperty("password", Password);

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(("email=" + email).getBytes());
        os.write(("password=" + Password).getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return (response.toString());
    }

    @Override
    public void Shutdown() {

    }

    @Override
    public void dispose() {
        //AudioM.stopMusic(BGMusicID);
    }

}