package main;

import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Camera2D {
    private Vector2f position, size;
    public Camera2D(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
    }

    public void update() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(position.x, position.x + size.x, position.y, position.y + size.y, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
    public Vector2f getPosition() {
        return position;
    }
    public Vector2f getSize() {
        return size;
    }
    public void setPosition(Vector2f position) {
        this.position = position;
    }
}
