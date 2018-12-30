package display;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import utilities.Utils;

import java.util.EventListener;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Menu implements Runnable{
    Texture bongoCat;
    public static void main(String[] args){
        new Menu("Main Menu");

    }

    private Thread thread;
    private String title;
    private long window;

    Menu(String title){
        this.title = title;
        thread = new Thread(this, "Menu");
        thread.start();
    }

    private void init(){
        if (!glfwInit()){
            throw new RuntimeException("Unable to initialzed glfw");
        }

        window = glfwCreateWindow(Utils.MAX_X, Utils.MAX_Y, title, NULL, NULL);
        if (window == NULL){
            throw new RuntimeException("Unable to create window.");
        }

        glfwShowWindow(window);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (int) ((vidMode.width()*0.5)-(Utils.MAX_X*0.5)),
                (int) ((vidMode.height()*0.5) - (Utils.MAX_Y*0.5)));

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);

        bongoCat = new Texture("res/bongocat.png");

        glClearColor(1,1,1,0);

    }

    public static void drawImage(float x, float y, float width, float height){

    }

    @Override
    public void run(){
        init();
        while (!glfwWindowShouldClose(window)){
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);

            bongoCat.bind();

            glBegin(GL_QUADS);
            {
                glTexCoord2f(0, 0);
                glVertex2f(-0.5f, 0.5f);
                glTexCoord2f(1, 0);
                glVertex2f(0.5f, 0.5f);
                glTexCoord2f(1, 1);
                glVertex2f(0.5f, -0.5f);
                glTexCoord2f(0, 1);
                glVertex2f(-0.5f, -0.5f);
            }
            glEnd();

            glfwSwapBuffers(window);
        }
    }

}