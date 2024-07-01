package hskl.de.projekt;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import hskl.de.projekt.Objects.SpaceShip;
import hskl.de.projekt.Objects.Alien;
import hskl.de.projekt.Objects.Projectile;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class GameView extends GLSurfaceView {
    private GameRenderer renderer;
    public Context context;
    private SpaceShip ship = new SpaceShip();
    private List<Alien> aliens = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        renderer = new GameRenderer();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        initAliens();
    }

    public void initAliens() {
        float startX = -2.0f;
        float startY = 3.0f;
        float size = 0.5f;
        float spacing = 0.5f;
        float velocityX = 0.5f;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 10; col++) {
                float x = startX + col * (size + spacing);
                float y = startY - row * (size + spacing);
                aliens.add(new Alien(x, y, size, velocityX));
            }
        }
    }

    public void setShipVelocity(float vx){
        ship.setVelocity(vx);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ship.shoot();
        return true;
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
            GLU.gluLookAt(gl, 0.0f, 0.0f, 10.0f, // eye
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
            // Check for any Hits
            checkHits();
            // Update the ship's position and rotation
            ship.updateShip(fracSec);
            // Draw the spaceship
            ship.draw(gl);
            // Update and draw aliens
            for (Alien alien : aliens) {
                alien.update(fracSec);
                alien.draw(gl);
            }
            // Spawn new Aliens
            if (aliens.isEmpty()) {
                initAliens();
            }
        }

        public void checkHits () {
            List<Alien> aliensToRemove = new ArrayList<>();
            List<Projectile> shipProjToRemove = new ArrayList<>();
            List<Projectile> alienProjToRemove = new ArrayList<>();
            for (Alien alien : aliens) {
                for (Projectile proj : ship.getProjectiles()) {
                    float squaredDistance = ((proj.getX() - alien.getX()) * (proj.getX() - alien.getX()) + (proj.getY() - alien.getY()) * (proj.getY() - alien.getY()));
                    if (squaredDistance < 0.01f) {
                        aliensToRemove.add(alien);
                        shipProjToRemove.add(proj);
                    }
                }
            }
            for (Alien alien : aliens) {
                for (Projectile proj : alien.getProjectiles()) {
                    float squaredDistance = ((proj.getX() - ship.getX()) * (proj.getX() - ship.getX()) + (proj.getY() - ship.getY()) * (proj.getY() - ship.getY()));
                    if (squaredDistance < 0.1f) {
                        alienProjToRemove.add(proj);
                    }
                }
            }
            for (Alien alien : aliensToRemove) aliens.remove(alien);
            for (Projectile proj : shipProjToRemove) ship.getProjectiles().remove(proj);
            for (Alien alien : aliens) {
                for (Projectile proj : alienProjToRemove) alien.getProjectiles().remove(proj);
            }
            aliensToRemove.clear();
            shipProjToRemove.clear();
            alienProjToRemove.clear();
        }
    }
}
