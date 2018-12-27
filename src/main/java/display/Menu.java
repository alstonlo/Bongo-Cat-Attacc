package display;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import utilities.Utils;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Menu implements Runnable{
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
        try{
        glfwInit();
        } catch (VerifyError e){
            e.printStackTrace();
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
        glClearColor(1,1,1,0);

    }

    @Override
    public void run(){
        init();
        while (!glfwWindowShouldClose(window)){
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glfwSwapBuffers(window);
        }
    }

}