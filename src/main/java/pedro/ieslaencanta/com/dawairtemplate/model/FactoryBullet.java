/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Manuel
 */
public class FactoryBullet {

    private static HashMap<String, Supplier<Bullet>> enemigos;
    private static ArrayList<String> names;

    static {
        enemigos = new HashMap<>();
        names = new ArrayList<>();
    }

    public static void addBullet(String name, Supplier<Bullet> s) {
        FactoryBullet.enemigos.put(name, s);
        FactoryBullet.names.add(name);

    }

    public static Bullet get(Supplier<? extends Bullet> s) {
        return s.get();
    }

    public static List<String> getKeyNames() {
        return FactoryBullet.names;
        // return new ArrayList<String>(FactoryEnemigos.enemigos.keySet());
    }

    public static Bullet create(String nombre) {
        if (FactoryBullet.enemigos.get(nombre) != null) {
            return FactoryBullet.enemigos.get(nombre).get();
        } else {
            return null;
        }
    }
}
