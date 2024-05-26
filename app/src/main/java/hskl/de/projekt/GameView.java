package hskl.de.projekt;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import hskl.de.projekt.Objects.SpaceShip;

public class GameView extends GLSurfaceView {
    private GameRenderer renderer;
    public Context context;
    private SpaceShip ship = new SpaceShip();

    public GameView(Context context) {
        super(context);
        renderer = new GameRenderer();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public void setShipVelocity(float vx){
        ship.setVelocity(vx);
    }
    private class GameRenderer implements GLSurfaceView.Renderer {
        private float[] modelViewScene = new float[16];
        public float boundaryTop, boundaryBottom, boundaryLeft, boundaryRight;
        long lastFrameTime;

        public GameRenderer() {
            lastFrameTime = System.currentTimeMillis();
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glDisable(GL10.GL_DITHER);
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to white
            //gl.glEnable(GL10.GL_CULL_FACE);
            gl.glDisable(GL10.GL_CULL_FACE); // Disable backface culling
            gl.glShadeModel(GL10.GL_FLAT);
            gl.glEnable(GL10.GL_LEQUAL);
            gl.glEnable(GL10.GL_SMOOTH);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GL11 gl11 = (GL11) gl;
            gl.glViewport(0, 0, width, height);
            float aspectRatio = (float) width / height;
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            GLU.gluPerspective(gl, 90.0f, aspectRatio, 0.1f, 100.0f);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            GLU.gluLookAt(gl, 0.0f, 0.0f, 3.0f, // eye
                    0.0f, 0.0f, 0.0f, // center
                    0.0f, 1.0f, 0.0f); // up

            // Save the initial model view matrix
            gl11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelViewScene, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            long deltaTime = System.currentTimeMillis() - lastFrameTime;
            float fracSec = (float) deltaTime / 1000;
            lastFrameTime = System.currentTimeMillis();

            // Clear screen and depth buffer
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            GL11 gl11 = (GL11) gl;
            // Load the initial model view matrix
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl11.glLoadMatrixf(modelViewScene, 0);

            // Update the ship's position and rotation
            ship.updateShip(fracSec);
            // Draw the spaceship
            ship.draw(gl);
        }
    }
}
