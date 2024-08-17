package friday;

import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private static MouseListener instance = null;

    private MouseListener(){
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    private static MouseListener getInstance(){
        if (MouseListener.instance == null){
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

    public static void mousePosCallback(long glfwWindow, double xpos, double yPos){
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
        getInstance().xPos = xpos;
        getInstance().yPos = yPos;
        getInstance().isDragging = getInstance().mouseButtonPressed[0] || getInstance().mouseButtonPressed[1] || getInstance().mouseButtonPressed[2];
        // OR call get once for an instance and make a local variable with that instance (better way)
        //   MouseListener instance = getInstance();
        //   instance.lastX = instance.xPos;

    }

    public  static void mouseButtonCallback(long glfwWindow, int button, int action, int mods){
        if (button >= getInstance().mouseButtonPressed.length){
            return;
        }

        if (action == GLFW_PRESS){
            getInstance().mouseButtonPressed[button] = true;
        } else if (action == GLFW_RELEASE) {
            getInstance().mouseButtonPressed[button] = false;
            getInstance().isDragging = false;
        }
    }

    public static void mouseScrollCallback(long glfwWindow, double xOffset, double yOffset){
        getInstance().scrollX = xOffset;
        getInstance().scrollY = yOffset;
    }

    public static void endFrame(){
        getInstance().scrollX = 0;
        getInstance().scrollY = 0;
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
    }

    public static float getX(){
        return (float)getInstance().xPos;
    }

    public static float getY(){
        return (float)getInstance().yPos;
    }

    public static float getDx(){
        return (float)(getInstance().lastX - getInstance().xPos);
    }

    public static float getDy(){
        return (float)(getInstance().lastY - getInstance().yPos);
    }

    public static float getScrollX(){
        return (float)getInstance().scrollX;
    }

    public static float getScrollY(){
        return (float)getInstance().scrollY;
    }

    public static boolean isDragging(){
        return getInstance().isDragging;
    }

    public static boolean mouseButtonDown(int button){
        if (button < getInstance().mouseButtonPressed.length){
            return getInstance().mouseButtonPressed[button];
        }
        else {
            return false;
        }
    }
}
