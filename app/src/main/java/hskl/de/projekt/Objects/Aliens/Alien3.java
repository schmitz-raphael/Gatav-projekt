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

public class Alien3 extends AlienMaster implements Drawable {
    private float x, y, size;
    private float velocityX;
    private static FloatBuffer alienVerticesBuffer;
    private static FloatBuffer alienNormalsBuffer;
    private static FloatBuffer alienTexturesBuffer;
    private static ShortBuffer alienFacesBuffer;
    private float[] color;
    private float[] transformationMatrix;
    private static final float leftBoundary = -3.0f;
    private static final float rightBoundary = 3.0f;
    private static final float rowDown = 1.5f;


    private static final float[] alienModelVertices = {
            -2.000000f, 5.000000f, 1.000000f,
            -2.000000f, 7.000000f, 1.000000f,
            -2.000000f, 5.000000f, -1.000000f,
            -2.000000f, 7.000000f, -1.000000f,
            2.000000f, 5.000000f, 1.000000f,
            2.000000f, 7.000000f, 1.000000f,
            2.000000f, 5.000000f, -1.000000f,
            2.000000f, 7.000000f, -1.000000f,
            4.000000f, 6.000000f, -1.000000f,
            4.000000f, -2.000000f, -1.000000f,
            4.000000f, 6.000000f, 1.000000f,
            4.000000f, -2.000000f, 1.000000f,
            2.000000f, 6.000000f, -1.000000f,
            2.000000f, -2.000000f, -1.000000f,
            2.000000f, 6.000000f, 1.000000f,
            2.000000f, -2.000000f, 1.000000f,
            -2.000000f, 6.000000f, -1.000000f,
            -2.000000f, -2.000000f, -1.000000f,
            -2.000000f, 6.000000f, 1.000000f,
            -2.000000f, -2.000000f, 1.000000f,
            -4.000000f, 6.000000f, -1.000000f,
            -4.000000f, -2.000000f, -1.000000f,
            -4.000000f, 6.000000f, 1.000000f,
            -4.000000f, -2.000000f, 1.000000f,
            -4.000000f, 0.000000f, 1.000000f,
            -4.000000f, 2.000000f, 1.000000f,
            -4.000000f, 0.000000f, -1.000000f,
            -4.000000f, 2.000000f, -1.000000f,
            4.000000f, 0.000000f, 1.000000f,
            4.000000f, 2.000000f, 1.000000f,
            4.000000f, 0.000000f, -1.000000f,
            4.000000f, 2.000000f, -1.000000f,
            1.000000f, 2.000000f, 1.000000f,
            1.000000f, 4.000000f, 1.000000f,
            1.000000f, 2.000000f, -1.000000f,
            1.000000f, 4.000000f, -1.000000f,
            3.000000f, 2.000000f, 1.000000f,
            3.000000f, 4.000000f, 1.000000f,
            3.000000f, 2.000000f, -1.000000f,
            3.000000f, 4.000000f, -1.000000f,
            -3.000000f, 2.000000f, 1.000000f,
            -3.000000f, 4.000000f, 1.000000f,
            -3.000000f, 2.000000f, -1.000000f,
            -3.000000f, 4.000000f, -1.000000f,
            -1.000000f, 2.000000f, 1.000000f,
            -1.000000f, 4.000000f, 1.000000f,
            -1.000000f, 2.000000f, -1.000000f,
            -1.000000f, 4.000000f, -1.000000f,
            5.000000f, 0.000000f, 1.000000f,
            5.000000f, 4.000000f, 1.000000f,
            5.000000f, 0.000000f, -1.000000f,
            5.000000f, 4.000000f, -1.000000f,
            7.000000f, 0.000000f, 1.000000f,
            7.000000f, 4.000000f, 1.000000f,
            7.000000f, 0.000000f, -1.000000f,
            7.000000f, 4.000000f, -1.000000f,
            -7.000000f, 0.000000f, 1.000000f,
            -7.000000f, 4.000000f, 1.000000f,
            -7.000000f, 0.000000f, -1.000000f,
            -7.000000f, 4.000000f, -1.000000f,
            -5.000000f, 0.000000f, 1.000000f,
            -5.000000f, 4.000000f, 1.000000f,
            -5.000000f, 0.000000f, -1.000000f,
            -5.000000f, 4.000000f, -1.000000f,
            -4.000000f, 4.000000f, 1.000000f,
            -4.000000f, 6.000000f, 1.000000f,
            -4.000000f, 4.000000f, -1.000000f,
            -4.000000f, 6.000000f, -1.000000f,
            4.000000f, 4.000000f, 1.000000f,
            4.000000f, 6.000000f, 1.000000f,
            4.000000f, 4.000000f, -1.000000f,
            4.000000f, 6.000000f, -1.000000f
    };

    private static final short[] alienModelFaces = {
            1, 2, 4, 1, 4, 3,
            3, 4, 8, 3, 8, 7,
            7, 8, 6, 7, 6, 5,
            5, 6, 2, 5, 2, 1,
            3, 7, 5, 3, 5, 1,
            8, 4, 2, 8, 2, 6,
            9, 13, 15, 9, 15, 11,
            12, 11, 15, 12, 15, 16,
            16, 15, 13, 16, 13, 14,
            14, 10, 12, 14, 12, 16,
            10, 9, 11, 10, 11, 12,
            14, 13, 9, 14, 9, 10,
            17, 21, 23, 17, 23, 19,
            20, 19, 23, 20, 23, 24,
            24, 23, 21, 24, 21, 22,
            22, 18, 20, 22, 20, 24,
            18, 17, 19, 18, 19, 20,
            22, 21, 17, 22, 17, 18,
            25, 26, 28, 25, 28, 27,
            27, 28, 32, 27, 32, 31,
            31, 32, 30, 31, 30, 29,
            29, 30, 26, 29, 26, 25,
            27, 31, 29, 27, 29, 25,
            32, 28, 26, 32, 26, 30,
            33, 34, 36, 33, 36, 35,
            35, 36, 40, 35, 40, 39,
            39, 40, 38, 39, 38, 37,
            37, 38, 34, 37, 34, 33,
            35, 39, 37, 35, 37, 33,
            40, 36, 34, 40, 34, 38,
            41, 42, 44, 41, 44, 43,
            43, 44, 48, 43, 48, 47,
            47, 48, 46, 47, 46, 45,
            45, 46, 42, 45, 42, 41,
            43, 47, 45, 43, 45, 41,
            48, 44, 42, 48, 42, 46,
            49, 50, 52, 49, 52, 51,
            51, 52, 56, 51, 56, 55,
            55, 56, 54, 55, 54, 53,
            53, 54, 50, 53, 50, 49,
            51, 55, 53, 51, 53, 49,
            56, 52, 50, 56, 50, 54,
            57, 58, 60, 57, 60, 59,
            59, 60, 64, 59, 64, 63,
            63, 64, 62, 63, 62, 61,
            61, 62, 58, 61, 58, 57,
            59, 63, 61, 59, 61, 57,
            64, 60, 58, 64, 58, 62,
            65, 66, 68, 65, 68, 67,
            67, 68, 72, 67, 72, 71,
            71, 72, 70, 71, 70, 69,
            69, 70, 66, 69, 66, 65,
            67, 71, 69, 67, 69, 65,
            72, 68, 66, 72, 66, 70
    };


    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;


    public Alien3(float x, float y, float size, float velocityX) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityX = velocityX;
        this.color = new float[]{0.0392f, 0.400f, 0.980f, 0f};

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
        gl.glScalef(0.12f,0.12f,0.12f);
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
