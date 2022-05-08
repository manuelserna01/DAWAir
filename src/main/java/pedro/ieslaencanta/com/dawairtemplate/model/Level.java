/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import java.io.File;
import java.io.FileNotFoundException;
import pedro.ieslaencanta.com.dawairtemplate.enemigo.Enemy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pedro.ieslaencanta.com.dawairtemplate.Background;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.enemigo.AEnemy;
import pedro.ieslaencanta.com.dawairtemplate.enemigo.EnemyDos;
import pedro.ieslaencanta.com.dawairtemplate.enemigo.EnemyTres;
import pedro.ieslaencanta.com.dawairtemplate.enemigo.FactoryEnemies;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IDrawable;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author Pedro
 */
public class Level implements IDrawable, IWarnClock, IKeyListener {

    public String getScore() throws FileNotFoundException {
        String tempo = "";
        File doc = new File("score.txt");
        Scanner obj = new Scanner(doc);
        while (obj.hasNextLine()) {
            tempo = obj.nextLine();
        }
        return tempo;
    }

    public enum Estado {
        PRE_STARTED,
        RUNNING,
        STOPPED,
        PAUSED,
        PRE_END,
        END
    }
    private static String[] msg = {
        "\"Pulsar una tecla para empezar", "Siguiente nivel... \nULTIMA PUNTUACIÓN: "};
    private String background_path;
    private int speed;
    private int position;
    private int fin;
    private Background background;

    private GraphicsContext bg_ctx;
    private MediaPlayer player;
    private float[] probabilidadenemigos;
    private Size s;

    private Estado estado;
    private Player p;
    private Fighter fighter;

    ArrayList<AEnemy> enemigos;
    ArrayList<Bullet> bullets;
    ArrayList<Bullet> balasenemigos;

    Enemy enemigo1;

    public Level(String image_path, String music_path, Size s, int speed, Coordenada start_position, GraphicsContext bg_ctx, float[] probabilidad_enemigos, int fin) {
        this.background = new Background(image_path, s, speed, start_position);
        this.background.setBg(bg_ctx);
        this.position = 0;
        this.speed = speed;
        this.estado = Estado.PRE_STARTED;
        this.fin = fin;
        this.s = s;
        //crear el avion
        this.probabilidadenemigos = probabilidad_enemigos;
        this.initSound(music_path);

        this.enemigos = new ArrayList<>();

        this.balasenemigos = new ArrayList<>();

        this.fighter = new Fighter(
                3,
                new Size(74, 26),
                new Coordenada(20, s.getHeight() / 2),
                new Rectangle(new Coordenada(0, 0), new Coordenada(s.getWidth(), s.getHeight())));

        FactoryEnemies.addEnemy("Enemigo1", Enemy::new);
        FactoryEnemies.addEnemy("Enemigo2", EnemyDos::new);
        FactoryEnemies.addEnemy("Enemigo3", EnemyTres::new);

    }

    private void initSound(String music_path) {
        this.player = new MediaPlayer(new Media(getClass().getResource(music_path).toString()));

        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
            }
        });

    }

    @Override
    public void draw(GraphicsContext gc) {

        this.background.paint(gc);

        if (this.estado == Estado.PRE_STARTED) {
            gc.setFill(Color.BROWN);
            gc.setStroke(Color.WHITE);
            gc.strokeText(Level.msg[0], 100, 200);
            gc.fillText(Level.msg[0], 100, 200);
            try {
                gc.setFill(Color.GREEN);
                gc.strokeText("Mejor Puntuacion: " + this.getScore(), 200, 300);
                gc.fillText("Mejor Puntuacion: " + this.getScore(), 200, 300);
            } catch (FileNotFoundException ex) {
                gc.setFill(Color.GREEN);
                gc.strokeText("Mejor Puntuacion: 0" , 200, 300);
                gc.fillText("Mejor Puntuacion: 0" , 200, 300);
            }
        }

        if (this.getEstado() == Estado.RUNNING) {

            // Hace el draw de los enemigos mientras estén vivos recorriendo la lista de enemigos.
            this.enemigos.forEach(e -> {
                if (e.isLive()) {
                    e.draw(gc);
                }
            });

            this.fighter.draw(gc);
            this.balasenemigos.forEach(e -> {
                //if (e.isLive()) {
                e.draw(gc);
                // }
            });
        }

    }

    public String getEnemyName() {
        float random = (float) Math.random();
        String nombre = null;
        System.out.println(random);
        int contador = 0;
        while (nombre == null) {
            if (random <= this.probabilidadenemigos[contador]) {
                nombre = FactoryEnemies.getKeyNames().get(contador);
            }
            contador++;
        }

        return nombre;
    }

    public void crearEnemigo() {

        AEnemy enemigo;

        if (Math.random() > 0.98999) {
            int y = (int) (Math.random() * s.getHeight());
            System.out.println(this.getEnemyName());
            enemigo = FactoryEnemies.create(this.getEnemyName());

            enemigo.init(new Coordenada(s.getWidth() - 30, y),
                    new Rectangle(new Coordenada(0, 0), new Coordenada(s.getWidth(), s.getHeight())
                    ));

            this.enemigos.add(enemigo);
            enemigo.setBullets(this.balasenemigos);
        }

    }

    @Override
    public void TicTac() {
        if (this.getEstado() == Estado.RUNNING) {
            //llamar a tictac de los hijos
            this.TicTacChildrens();

            //posicion en la que termina
            if (this.position < this.fin) {
                this.position += this.speed;
            } else {
                this.EndLevel();
            }
            crearEnemigo();

        }
    }

    private void detectCollisions() {

        // Detectar balas de enemigos a mi avión
        this.balasenemigos.forEach(eb -> {
            if (this.fighter.isCollision(eb)) {
                eb.setLive(false);
                this.p.setLifes(this.p.getLifes() - 1);
                // System.out.println(this.p.getLifes());
            }
        });

        // Detecta si las balas colisionan con los enemigos y los mata a ambos.
        // Aumenta el Score en 100 cada vez que los mata.
        this.enemigos.forEach(e -> {
            this.fighter.getBalas().forEach(b -> {
                if (e.isCollision(b)) {
                    e.setLive(false);
                    b.setLive(false);
                    this.p.setScore(this.p.getScore() + 100);
                }
            });
        });

        // Elimina de la lista de enemigos al elemento si no está vivo.
        this.enemigos.removeIf(b -> {
            return (!b.isLive());
        });

        // Elimina las balas de los enemigos cuando me chocan
        this.balasenemigos.removeIf(eb -> {
            return (!eb.isLive());
        });

        // Elimina los enemigos cuando se chocan con el fondo del nivel
        this.enemigos.removeIf(n -> (n.getX() <= 2));

        // Detectar las balas enemigas y que se eliminen al final
        this.balasenemigos.removeIf(b -> (b.getX() <= 2));

        // Elimina mis balas cuando superan el nivel
        this.fighter.getBalas().removeIf(fb -> (fb.getX() >= 1000));

    }

    private void TicTacChildrens() {
        //pintar el fondo
        this.background.TicTac();
        this.fighter.TicTac();

        // Hace los tictacs de los enemigos recorriendo la lista.
        this.enemigos.forEach(e -> {
            e.TicTac();
        });

        this.moveBullets();
        this.detectCollisions();

    }

    private void moveBullets() {

        //mover las balas
        //comprobar las condiciones para borrar y borrar las balas
        this.enemigos.forEach(e -> {
            e.shoot();
        });

        this.balasenemigos.forEach(e -> {
            e.move();
        });
    }

    public void setPlayer(Player p) {
        this.p = p;
    }

    public boolean isEnd() {
        return this.getEstado() == Estado.STOPPED;
    }

    private void EndLevel() {
        this.player.stop();
        this.setEstado(Estado.END);
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @return the p
     */
    public Player getP() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(Player p) {
        this.p = p;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public void onKeyPressed(KeyCode code) {
        if (this.getEstado() == Level.Estado.RUNNING) {
            this.fighter.onKeyPressed(code);
        }

    }

    @Override
    public void onKeyReleased(KeyCode code) {
        //para iniciar el juego
        if (this.getEstado() == Level.Estado.PRE_STARTED) {
            this.setEstado(Level.Estado.RUNNING);

        }
        if (this.getEstado() == Level.Estado.RUNNING) {
            this.fighter.onKeyReleased(code);
            if (player.getStatus() == MediaPlayer.Status.READY) {
                player.play();
            }
        }

    }
}
