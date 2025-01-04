package main;

import org.joml.Vector2f;
import org.joml.Vector2i;

public class Main {
    public static void main(String[] args) {
        Window window = new Window("Slungus Platformer", new Vector2i(1024, 600));
        Player player = new Player(new Vector2f(0, 0), new Vector2f(1, 1));
        Camera2D camera = new Camera2D(new Vector2f(0, 0), new Vector2f(1024, 600));
        window.setIcon("/res/slungus.jpg");
        window.wireframe(true);
        while (!window.shouldClose()) {
            window.clear();
            camera.update();
            player.update();
            player.render();
            window.update();
        }
        window.destroy();
    }
}