package hskl.de.projekt.Objects.Aliens;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import hskl.de.projekt.Objects.Direction;
import hskl.de.projekt.Objects.Drawable;
import hskl.de.projekt.Objects.Projectile;

public class Alien1 extends Alien implements Drawable{
    private float x, y, size;
    private float velocityX;
    private static FloatBuffer alienVerticesBuffer;
    private static ShortBuffer alienFacesBuffer;
    private float[] color;
    private float[] transformationMatrix;
    private static final float leftBoundary = -2.4f;
    private static final float rightBoundary = 2.4f;
    private static final float rowDown = 0.5f;


    private static final float[] alienModelVertices = {
            -1.000000f, -1.000000f, -1.000000f,
            -1.000000f, -1.000000f, 1.000000f,
            -1.000000f, 1.000000f, -1.000000f,
            -1.000000f, 1.000000f, 1.000000f,
            1.000000f, -1.000000f, -1.000000f,
            1.000000f, -1.000000f, 1.000000f,
            1.000000f, 1.000000f, -1.000000f,
            1.000000f, 1.000000f, 1.000000f,
            -1.000000f, 1.000000f, -4.000000f,
            -1.000000f, 1.000000f, -20.000000f,
            -1.000000f, -1.000000f, -4.000000f,
            -1.000000f, -1.000000f, -20.000000f,
            -1.666667f, 1.000000f, -4.000000f,
            -1.666667f, 1.000000f, -20.000000f,
            -1.666667f, -1.000000f, -4.000000f,
            -1.666667f, -1.000000f, -20.000000f,
            -1.000000f, -1.000000f, -19.000000f,
            -1.000000f, -1.000000f, -7.000000f,
            -1.000000f, 1.000000f, -19.000000f,
            -1.000000f, 1.000000f, -7.000000f,
            -0.333333f, -1.000000f, -19.000000f,
            -0.333333f, -1.000000f, -7.000000f,
            -0.333333f, 1.000000f, -19.000000f,
            -0.333333f, 1.000000f, -7.000000f,
            -0.333333f, -1.000000f, -18.000000f,
            -0.333333f, -1.000000f, -4.000000f,
            -0.333333f, 1.000000f, -18.000000f,
            -0.333333f, 1.000000f, -4.000000f,
            0.333333f, -1.000000f, -18.000000f,
            0.333333f, -1.000000f, -4.000000f,
            0.333333f, 1.000000f, -18.000000f,
            0.333333f, 1.000000f, -4.000000f,
            0.333333f, -1.000000f, -19.000000f,
            0.333333f, -1.000000f, -7.000000f,
            0.333333f, 1.000000f, -19.000000f,
            0.333333f, 1.000000f, -7.000000f,
            1.000000f, -1.000000f, -19.000000f,
            1.000000f, -1.000000f, -7.000000f,
            1.000000f, 1.000000f, -19.000000f,
            1.000000f, 1.000000f, -7.000000f,
            1.666667f, 1.000000f, -4.000000f,
            1.666667f, 1.000000f, -20.000000f,
            1.666667f, -1.000000f, -4.000000f,
            1.666667f, -1.000000f, -20.000000f,
            1.000000f, 1.000000f, -4.000000f,
            1.000000f, 1.000000f, -20.000000f,
            1.000000f, -1.000000f, -4.000000f,
            1.000000f, -1.000000f, -20.000000f,
            -1.666667f, -1.000000f, -5.000000f,
            -1.666667f, -1.000000f, -1.000000f,
            -1.666667f, 1.000000f, -5.000000f,
            -1.666667f, 1.000000f, -1.000000f,
            1.666667f, -1.000000f, -5.000000f,
            1.666667f, -1.000000f, -1.000000f,
            1.666667f, 1.000000f, -5.000000f,
            1.666667f, 1.000000f, -1.000000f
    };

    private static final short[] alienModelFaces = {
            0, 1, 3, 2,
            2, 3, 7, 6,
            6, 7, 5, 4,
            4, 5, 1, 0,
            2, 6, 4, 0,
            7, 3, 1, 5,
            8, 12, 14, 10,
            11, 10, 14, 15,
            15, 14, 12, 13,
            13, 9, 11, 15,
            9, 8, 10, 11,
            13, 12, 8, 9,
            16, 17, 19, 18,
            18, 19, 23, 22,
            22, 23, 21, 20,
            20, 21, 17, 16,
            18, 22, 20, 16,
            23, 19, 17, 21,
            24, 25, 27, 26,
            26, 27, 31, 30,
            30, 31, 29, 28,
            28, 29, 25, 24,
            26, 30, 28, 24,
            31, 27, 25, 29,
            32, 33, 35, 34,
            34, 35, 39, 38,
            38, 39, 37, 36,
            36, 37, 33, 32,
            34, 38, 36, 32,
            39, 35, 33, 37,
            40, 44, 46, 42,
            43, 42, 46, 47,
            47, 46, 44, 45,
            45, 41, 43, 47,
            41, 40, 42, 43,
            45, 44, 40, 41,
            48, 49, 51, 50,
            50, 51, 55, 54,
            54, 55, 53, 52,
            52, 53, 49, 48,
            50, 54, 52, 48,
            55, 51, 49, 53
    };



    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;


    public Alien1(float x, float y, float size, float velocityX) {
        super(x,y,size,velocityX);

        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityX = velocityX;
        this.color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};

        // Initialize the transformation matrix
        transformationMatrix = new float[16];
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
        Matrix.scaleM(transformationMatrix, 0, size, size, size);

        // Initialize the vertex buffer
        ByteBuffer alienVerticesBB = ByteBuffer.allocateDirect(alienModelVertices.length * 4);
        alienVerticesBB.order(ByteOrder.nativeOrder());
        alienVerticesBuffer = alienVerticesBB.asFloatBuffer();
        alienVerticesBuffer.put(alienModelVertices);
        alienVerticesBuffer.position(0);

        ByteBuffer alienFacesBB = ByteBuffer.allocateDirect(alienModelFaces.length * 2);
        alienFacesBB.order(ByteOrder.nativeOrder());
        alienFacesBuffer = alienFacesBB.asShortBuffer();
        alienFacesBuffer.put(alienModelFaces);
        alienFacesBuffer.position(0);

        // Initialize projectiles and random generator
        projectiles = new ArrayList<>();
        random = new Random();
    }

    public void update(float deltaTime) {
        // Update position
        x += velocityX * deltaTime;
        //check boundaries
        if (x <= leftBoundary) {
            x = leftBoundary;
            velocityX *= -1;
            y -= rowDown;
        }
        if (x >= rightBoundary) {
            x = rightBoundary;
            velocityX *= -1;
            y -= rowDown;
        }
        // Update transformation matrix with new position
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
        Matrix.scaleM(transformationMatrix, 0, size, size, size);

        List<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            projectile.updateProjectile(deltaTime);
            if (projectile.isOutOfBounds()) {
                projectilesToRemove.add(projectile);
            }
        }
        projectiles.removeAll(projectilesToRemove);

        // Randomly shoot a projectile
        if (random.nextInt(8000) < 10) { // 1/800 chance every frame
            shoot();
        }
    }

    public void shoot() {
        float[] color = {1.0f, 0.0f, 0.0f, 1.0f};
        projectiles.add(new Projectile(x, y, Direction.DOWN, color));
    }

    @Override
    public void draw(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();  // Save the current model view matrix

        // Apply the transformation matrix
        gl.glMultMatrixf(transformationMatrix, 0);

        // Enable vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, alienVerticesBuffer);

        // Scale, Rotate, Color
        gl.glScalef(0.10f,0.10f,0.10f);
        gl.glRotatef(25f,1,0,0);
        gl.glColor4f(color[0],color[1],color[2],color[3]);

        // Draw the cubes using triangles
        for (int i = 0; i < (alienModelFaces.length / 3); i++) {
            alienFacesBuffer.position(3 * i);
            gl.glDrawElements(GL10.GL_TRIANGLES, 3, GL10.GL_UNSIGNED_SHORT, alienFacesBuffer);
        }

        // Disable vertex arrays
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glPopMatrix();  // Restore the previous model view matrix

        for (Projectile projectile : projectiles) {
            projectile.draw(gl);
        }
    }

    public float getX() {
        return transformationMatrix[12];
    }
    public float getY() {
        return transformationMatrix[13];
    }
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
