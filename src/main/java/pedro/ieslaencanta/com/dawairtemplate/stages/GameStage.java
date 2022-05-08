/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.stages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Fighter;
import pedro.ieslaencanta.com.dawairtemplate.model.Level;
import pedro.ieslaencanta.com.dawairtemplate.model.Player;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;

/**
 *
 * @author Pedro
 */
public class GameStage extends AbstractScene {

    public void setNewHighScore(int score) {
        try {
            String ruta = "score.txt";
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(score + "");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Level actual;
    private Size s;
    private static Font font;//, Font.ITALIC, 50);
    private Player player;

    private Image newImagen;

    private ArrayList<Level> niveles;
    private static String pathurl = "avion.png";
    private int nivelActualInt = 0;

    private static float[][] probabilidades = {
        {0.2f, 0.5f, 1f},
        {0.6f, 1f},
        {0.5f, 1f}
    };

    static {
        font = new Font("8BIT WONDER Nominal", 30);//, Font.ITALIC, 50);
    }

    //0 delante, 1 detras,2 arriba, 3 abajo, 4 disparo
    //private boolean[] keys_presed;
    public GameStage(GraphicsContext context, GraphicsContext bg_context, Size s) {
        super(context, bg_context, s, null);
        newImagen = new Image(getClass().getResourceAsStream("/" + GameStage.pathurl));
        this.niveles = new ArrayList<>();
        this.s = s;
        this.state = SceneState.PRE_STARTED;
        player = new Player();
        Level nivel1 = new Level("/level1/bg2.png", "/level1/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0], 2500);
        Level nivel2 = new Level("/level2/bg2.png", "/level2/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0], 2500);
        Level nivel3 = new Level("/level3/bg3.png", "/level3/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0], 2500);

        nivel1.setPlayer(player);
        nivel2.setPlayer(player);
        nivel3.setPlayer(player);

        this.niveles.add(nivel1);
        this.niveles.add(nivel2);
        this.niveles.add(nivel3);

        this.actual = this.niveles.get(nivelActualInt);
        this.initFactory();
    }

    //se inicializan la factoria de enemigos
    private void initFactory() {

    }

    @Override
    public void TicTac() {

        if (this.state == SceneState.STARTED) {
            this.actual.TicTac();
            //para pasar de nivel
            if (this.actual.getEstado() == Level.Estado.END) {
                this.nivelActualInt = this.nivelActualInt + 1;

                if (this.nivelActualInt == this.niveles.size()) {
                    // Sale del juego
                    System.exit(0);
                } else {
                    this.actual = this.niveles.get(nivelActualInt);
                }
            }

            // Para el juego si no te quedan más vidas.
            if (this.player.getLifes() < 0) {
                // Sale del juego
                setNewHighScore((int) this.player.getScore());
                System.exit(0);
            }
        }

    }

    @Override
    public void onKeyPressed(KeyCode code) {
        if (this.state == SceneState.STARTED) {
            this.actual.onKeyPressed(code);
        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {
        if (GameStage.SceneState.PRE_STARTED == this.state) {
            this.state = GameStage.SceneState.STARTED;
        }

        if (this.state == SceneState.STARTED) {
            this.actual.onKeyReleased(code);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        this.actual.draw(gc);
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BROWN);
        gc.setFont(GameStage.font);
        gc.strokeText("SCORE " + this.player.getScore(), this.s.getWidth() / 2 - 100, 40);

        for (int i = 0; i < this.player.getLifes(); i++) {
            gc.drawImage(newImagen,
                    105, 8,
                    50, 150,
                    80 * i, 450,
                    100, 300);
        }

    }

}
