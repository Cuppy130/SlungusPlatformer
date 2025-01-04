package main;

import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;



public class Texture2D {
    private int id;
    private Vector2i size;
    public Texture2D(String path) {
        id = 0;
        size = new Vector2i(0, 0);
        try(InputStream stream = Texture2D.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new RuntimeException("Failed to load texture: " + path);
            }
            BufferedImage image = ImageIO.read(stream);
            size.x = image.getWidth();
            size.y = image.getHeight();
            int[] pixels = new int[size.x * size.y];
            image.getRGB(0, 0, size.x, size.y, pixels, 0, size.x);
            ByteBuffer data = BufferUtils.createByteBuffer(size.x * size.y * 4);
            for (int y = 0; y < size.y; y++) {
                for (int x = 0; x < size.x; x++) {
                    int pixel = pixels[y * size.x + x];
                    data.put((byte) ((pixel >> 16) & 0xFF));
                    data.put((byte) ((pixel >> 8) & 0xFF));
                    data.put((byte) (pixel & 0xFF));
                    data.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            data.flip();
            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size.x, size.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void destroy() {
        glDeleteTextures(id);
    }

}
