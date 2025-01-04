package main;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class Window {
    private long id;
    private String title;
    private Vector2i size, position;
    public boolean fullscreened = false, resizable = false, decorated = true, floating = false, vsync = false;

    public Window(String title, Vector2i size) {
        this.title = title;
        this.size = size;
        position = new Vector2i(0, 0);

        create();
    }

    private void create() {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }
        id = glfwCreateWindow(size.x, size.y, title, NULL, NULL);
        if (id == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        glfwSetWindowPos(id, position.x, position.y);
        glfwMakeContextCurrent(id);
        createCapabilities();
        glfwShowWindow(id);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glViewport(0, 0, size.x, size.y);
        setVSync(vsync);
    }

    public void wireframe(boolean wireframe) {
        glPolygonMode(GL_FRONT_AND_BACK, wireframe ? GL_LINE : GL_FILL);
    }



    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void update() {
        glfwSwapBuffers(id);
        glfwPollEvents();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(id);
    }

    public void destroy() {
        glfwDestroyWindow(id);
        glfwTerminate();
    }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(id, title);
    }

    public void setSize(Vector2i size) {
        this.size = size;
        glfwSetWindowSize(id, size.x, size.y);
    }

    public void setPosition(Vector2i position) {
        this.position = position;
        glfwSetWindowPos(id, position.x, position.y);
    }

    public String getTitle() {
        return title;
    }

    public Vector2i getSize() {
        return size;
    }

    public Vector2i getPosition() {
        return position;
    }

    public long getId() {
        return id;
    }

    public void setFullscreened(boolean fullscreened) {
        this.fullscreened = fullscreened;
        if (fullscreened) {
            glfwSetWindowMonitor(id, glfwGetPrimaryMonitor(), 0, 0, size.x, size.y, 0);
        } else {
            glfwSetWindowMonitor(id, NULL, position.x, position.y, size.x, size.y, 0);
        }
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
        glfwSetWindowAttrib(id, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
    }

    public void setDecorated(boolean decorated) {
        this.decorated = decorated;
        glfwSetWindowAttrib(id, GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
        glfwSetWindowAttrib(id, GLFW_FLOATING, floating ? GLFW_TRUE : GLFW_FALSE);
    }

    public boolean isFullscreened() {
        return fullscreened;
    }

    public boolean isResizable() {
        return resizable;
    }

    public boolean isDecorated() {
        return decorated;
    }

    public boolean isFloating() {
        return floating;
    }

    public void setVSync(boolean vsync) {
        glfwSwapInterval(vsync ? 1 : 0);
    }

    public boolean isVSync() {
        return vsync;
    }

    public void setIcon(String path) {
        try(InputStream in = Window.class.getResourceAsStream(path)){
            BufferedImage image = ImageIO.read(in);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + path);
            }
            int width = image.getWidth();
            int height = image.getHeight();

            ByteBuffer icon = BufferUtils.createByteBuffer(width * height * 4);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    icon.put((byte) ((pixel >> 16) & 0xFF));
                    icon.put((byte) ((pixel >> 8) & 0xFF));
                    icon.put((byte) (pixel & 0xFF));
                    icon.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            icon.flip();
            
            GLFWImage.Buffer icons = GLFWImage.malloc(1);
            GLFWImage glfwImage = GLFWImage.create();
            glfwImage.set(width, height, icon);
            icons.put(0, glfwImage);
            glfwSetWindowIcon(id, icons);
            icons.free();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load icon");
        }
    }


    public void setCursor(String path) {
    }
}
