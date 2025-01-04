package main;

import org.joml.Vector2f;

public class Player {
    private Vector2f position, size, velocity;
    private Model model;
    public boolean left, down, jump, right, sprint, crouch, attack, interact, inventory, pause;
    private float gravity = 0.5f;
    private float jumpHeight = 10;
    private float speed = 5;
    private float sprintSpeed = 10;
    private float crouchSpeed = 2;

    public Player(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
        velocity = new Vector2f(0, 0);
        model = new Model(size, new Texture2D("/res/slungus.jpg"));
        left = down = jump = right = sprint = crouch = attack = interact = inventory = pause = false;
    }

    public void update() {
        if(!inventory || !pause) {
            position.add(velocity);
            if (left) {
                velocity.x = -speed;
            }
            if (right) {
                velocity.x = speed;
            }
            if (down) {
                velocity.y = -speed;
            }
            if (jump) {
                velocity.y = -jumpHeight;
            }
            if (sprint) {
                speed = sprintSpeed;
            } else {
                speed = 5;
            }
            if (crouch) {
                speed = crouchSpeed;
            }
            if (!left && !right) {
                velocity.x = 0;
            }
            if (!down) {
                velocity.y = 0;
            }
            if (!jump) {
                velocity.y = gravity;
            }
        }
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getSize() {
        return size;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public void move(Vector2f velocity) {
        position.add(velocity);
    }

    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }

    public void setWidth(float width) {
        size.x = width;
    }

    public void setHeight(float height) {
        size.y = height;
    }

    public void setXVelocity(float x) {
        velocity.x = x;
    }

    public void setYVelocity(float y) {
        velocity.y = y;
    }

    public void render() {
        model.render();
    }
}
