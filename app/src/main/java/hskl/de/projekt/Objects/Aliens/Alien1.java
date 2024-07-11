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

public class Alien1 extends AlienMaster implements Drawable{
    private float x, y, size;
    private float velocityX;
    private static FloatBuffer alienVerticesBuffer;
    private static ShortBuffer alienFacesBuffer;
    private float[] color;
    private float[] transformationMatrix;
    private static final float leftBoundary = -3.0f;
    private static final float rightBoundary = 3.0f;
    private static final float rowDown = 1.5f;


    private static final float[] alienModelVertices = {
            6.000000f, 3.000000f, -6.000000f,
            6.000000f, -3.000000f, -6.000000f,
            6.000000f, 3.000000f, 6.000000f,
            6.000000f, -3.000000f, 6.000000f,
            -6.000000f, 3.000000f, -6.000000f,
            -6.000000f, -3.000000f, -6.000000f,
            -6.000000f, 3.000000f, 6.000000f,
            -6.000000f, -3.000000f, 6.000000f,
            4.000000f, -5.000000f, -4.000000f,
            4.000000f, -7.000000f, -4.000000f,
            4.000000f, -5.000000f, 4.000000f,
            4.000000f, -7.000000f, 4.000000f,
            -4.000000f, -5.000000f, -4.000000f,
            -4.000000f, -7.000000f, -4.000000f,
            -4.000000f, -5.000000f, 4.000000f,
            -4.000000f, -7.000000f, 4.000000f,
            5.000000f, -3.000000f, -5.000000f,
            5.000000f, -5.000000f, -5.000000f,
            5.000000f, -3.000000f, 5.000000f,
            5.000000f, -5.000000f, 5.000000f,
            -5.000000f, -3.000000f, -5.000000f,
            -5.000000f, -5.000000f, -5.000000f,
            -5.000000f, -3.000000f, 5.000000f,
            -5.000000f, -5.000000f, 5.000000f,
            5.000000f, 5.000000f, -5.000000f,
            5.000000f, 3.000000f, -5.000000f,
            5.000000f, 5.000000f, 5.000000f,
            5.000000f, 3.000000f, 5.000000f,
            -5.000000f, 5.000000f, -5.000000f,
            -5.000000f, 3.000000f, -5.000000f,
            -5.000000f, 5.000000f, 5.000000f,
            -5.000000f, 3.000000f, 5.000000f,
            4.000000f, 6.000000f, -4.000000f,
            4.000000f, 4.000000f, -4.000000f,
            4.000000f, 6.000000f, 4.000000f,
            4.000000f, 4.000000f, 4.000000f,
            -4.000000f, 6.000000f, -4.000000f,
            -4.000000f, 4.000000f, -4.000000f,
            -4.000000f, 6.000000f, 4.000000f,
            -4.000000f, 4.000000f, 4.000000f,
            3.000000f, -7.000000f, -3.000000f,
            3.000000f, -9.000000f, -3.000000f,
            3.000000f, -7.000000f, 3.000000f,
            3.000000f, -9.000000f, 3.000000f,
            -3.000000f, -7.000000f, -3.000000f,
            -3.000000f, -9.000000f, -3.000000f,
            -3.000000f, -7.000000f, 3.000000f,
            -3.000000f, -9.000000f, 3.000000f,
            -1.000000f, -12.000000f, 1.000000f,
            -1.000000f, -8.000000f, 1.000000f,
            -1.000000f, -12.000000f, -1.000000f,
            -1.000000f, -8.000000f, -1.000000f,
            1.000000f, -12.000000f, 1.000000f,
            1.000000f, -8.000000f, 1.000000f,
            1.000000f, -12.000000f, -1.000000f,
            1.000000f, -8.000000f, -1.000000f,
            7.000000f, 4.000000f, 0.500000f,
            7.000000f, 5.000000f, 0.500000f,
            7.000000f, 4.000000f, -0.500000f,
            7.000000f, 5.000000f, -0.500000f,
            8.000000f, 4.000000f, 0.500000f,
            8.000000f, 5.000000f, 0.500000f,
            8.000000f, 4.000000f, -0.500000f,
            8.000000f, 5.000000f, -0.500000f,
            -7.000000f, 3.000000f, 0.500000f,
            -7.000000f, 4.000000f, 0.500000f,
            -7.000000f, 3.000000f, -0.500000f,
            -7.000000f, 4.000000f, -0.500000f,
            -6.000000f, 3.000000f, 0.500000f,
            -6.000000f, 4.000000f, 0.500000f,
            -6.000000f, 3.000000f, -0.500000f,
            -6.000000f, 4.000000f, -0.500000f,
            6.000000f, 3.000000f, 0.500000f,
            6.000000f, 4.000000f, 0.500000f,
            6.000000f, 3.000000f, -0.500000f,
            6.000000f, 4.000000f, -0.500000f,
            7.000000f, 3.000000f, 0.500000f,
            7.000000f, 4.000000f, 0.500000f,
            7.000000f, 3.000000f, -0.500000f,
            7.000000f, 4.000000f, -0.500000f,
            -9.500000f, 4.500000f, 1.000000f,
            -9.500000f, 6.500000f, 1.000000f,
            -9.500000f, 4.500000f, -1.000000f,
            -9.500000f, 6.500000f, -1.000000f,
            -7.500001f, 4.500000f, 1.000000f,
            -7.500001f, 6.500000f, 1.000000f,
            -7.500001f, 4.500000f, -1.000000f,
            -7.500001f, 6.500000f, -1.000000f,
            7.500001f, 4.500000f, 1.000000f,
            7.500001f, 6.500000f, 1.000000f,
            7.500001f, 4.500000f, -1.000000f,
            7.500001f, 6.500000f, -1.000000f,
            9.500000f, 4.500000f, 1.000000f,
            9.500000f, 6.500000f, 1.000000f,
            9.500000f, 4.500000f, -1.000000f,
            9.500000f, 6.500000f, -1.000000f,
            -8.000000f, 4.000000f, 0.500000f,
            -8.000000f, 5.000000f, 0.500000f,
            -8.000000f, 4.000000f, -0.500000f,
            -8.000000f, 5.000000f, -0.500000f,
            -7.000000f, 4.000000f, 0.500000f,
            -7.000000f, 5.000000f, 0.500000f,
            -7.000000f, 4.000000f, -0.500000f,
            -7.000000f, 5.000000f, -0.500000f,
            -4.000000f, -1.500000f, -4.100000f,
            -4.000000f, 1.500000f, -4.100000f,
            -4.000000f, -1.500000f, -6.100000f,
            -4.000000f, 1.500000f, -6.100000f,
            -1.000000f, -1.500000f, -4.100000f,
            -1.000000f, 1.500000f, -4.100000f,
            -1.000000f, -1.500000f, -6.100000f,
            -1.000000f, 1.500000f, -6.100000f,
            1.000000f, -1.500000f, -4.100000f,
            1.000000f, 1.500000f, -4.100000f,
            1.000000f, -1.500000f, -6.100000f,
            1.000000f, 1.500000f, -6.100000f,
            4.000000f, -1.500000f, -4.100000f,
            4.000000f, 1.500000f, -4.100000f,
            4.000000f, -1.500000f, -6.100000f,
            4.000000f, 1.500000f, -6.100000f,
            -2.750000f, 0.000000f, -5.110001f,
            -2.750000f, 1.000000f, -5.110001f,
            -2.750000f, 0.000000f, -6.110001f,
            -2.750000f, 1.000000f, -6.110001f,
            -1.750000f, 0.000000f, -5.110001f,
            -1.750000f, 1.000000f, -5.110001f,
            -1.750000f, 0.000000f, -6.110001f,
            -1.750000f, 1.000000f, -6.110001f,
            6.000000f, 3.000000f, 0.500000f,
            6.000000f, 4.000000f, 0.500000f,
            6.000000f, 3.000000f, -0.500000f,
            6.000000f, 4.000000f, -0.500000f,
            7.000000f, 3.000000f, 0.500000f,
            7.000000f, 4.000000f, 0.500000f,
            7.000000f, 3.000000f, -0.500000f,
            7.000000f, 4.000000f, -0.500000f,
            1.500000f, 0.000000f, -5.110001f,
            1.500000f, 1.000000f, -5.110001f,
            1.500000f, 0.000000f, -6.110001f,
            1.500000f, 1.000000f, -6.110001f,
            2.500000f, 0.000000f, -5.110001f,
            2.500000f, 1.000000f, -5.110001f,
            2.500000f, 0.000000f, -6.110001f,
            2.500000f, 1.000000f, -6.110001f
    };

    private static final short[] alienModelFaces = {
            1, 5, 7, 3,
            4, 3, 7, 8,
            8, 7, 5, 6,
            6, 2, 4, 8,
            2, 1, 3, 4,
            6, 5, 1, 2,
            9, 13, 15, 11,
            12, 11, 15, 16,
            16, 15, 13, 14,
            14, 10, 12, 16,
            10, 9, 11, 12,
            14, 13, 9, 10,
            49, 50, 52, 51,
            51, 52, 56, 55,
            55, 56, 54, 53,
            53, 54, 50, 49,
            51, 55, 53, 49,
            56, 52, 50, 54,
            57, 58, 60, 59,
            59, 60, 64, 63,
            63, 64, 62, 61,
            61, 62, 58, 57,
            59, 63, 61, 57,
            64, 60, 58, 62,
            65, 66, 68, 67,
            67, 68, 72, 71,
            71, 72, 70, 69,
            69, 70, 66, 65,
            67, 71, 69, 65,
            72, 68, 66, 70,
            73, 74, 76, 75,
            75, 76, 80, 79,
            79, 80, 78, 77,
            77, 78, 74, 73,
            75, 79, 77, 73,
            80, 76, 74, 78,
            81, 82, 84, 83,
            83, 84, 88, 87,
            87, 88, 86, 85,
            85, 86, 82, 81,
            83, 87, 85, 81,
            88, 84, 82, 86,
            89, 90, 92, 91,
            91, 92, 96, 95,
            95, 96, 94, 93,
            93, 94, 90, 89,
            91, 95, 93, 89,
            96, 92, 90, 94,
            97, 98, 100, 99,
            99, 100, 104, 103,
            103, 104, 102, 101,
            101, 102, 98, 97,
            99, 103, 101, 97,
            104, 100, 98, 102,
            17, 21, 23, 19,
            20, 19, 23, 24,
            24, 23, 21, 22,
            22, 18, 20, 24,
            18, 17, 19, 20,
            22, 21, 17, 18,
            121, 122, 124, 123,
            123, 124, 128, 127,
            127, 128, 126, 125,
            125, 126, 122, 121,
            123, 127, 125, 121,
            128, 124, 122, 126,
            129, 130, 132, 131,
            131, 132, 136, 135,
            135, 136, 134, 133,
            133, 134, 130, 129,
            131, 135, 133, 129,
            136, 132, 130, 134,
            137, 138, 140, 139,
            139, 140, 144, 143,
            143, 144, 142, 141,
            141, 142, 138, 137,
            139, 143, 141, 137,
            144, 140, 138, 142,
            25, 29, 31, 27,
            28, 27, 31, 32,
            32, 31, 29, 30,
            30, 26, 28, 32,
            26, 25, 27, 28,
            30, 29, 25, 26,
            105, 106, 108, 107,
            107, 108, 112, 111,
            111, 112, 110, 109,
            109, 110, 106, 105,
            107, 111, 109, 105,
            112, 108, 106, 110,
            113, 114, 116, 115,
            115, 116, 120, 119,
            119, 120, 118, 117,
            117, 118, 114, 113,
            115, 119, 117, 113,
            120, 116, 114, 118,
            33, 37, 39, 35,
            36, 35, 39, 40,
            40, 39, 37, 38,
            38, 34, 36, 40,
            34, 33, 35, 36,
            38, 37, 33, 34,
            41, 45, 47, 43,
            44, 43, 47, 48,
            48, 47, 45, 46,
            46, 42, 44, 48,
            42, 41, 43, 44,
            46, 45, 41, 42
    };



    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;


    public Alien1(float x, float y, float size, float velocityX) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velocityX = velocityX;
        this.color = new float[]{0.374f, 0.960f, 0.404f, 0f};

        // Initialize the transformation matrix
        transformationMatrix = new float[16];
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
        Matrix.scaleM(transformationMatrix, 0, size, size, size);

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
//        //check boundaries
//        if (x <= leftBoundary) {
//            x = leftBoundary;
//            velocityX *= -1;
//            y -= rowDown;
//        }
//        if (x >= rightBoundary) {
//            x = rightBoundary;
//            velocityX *= -1;
//            y -= rowDown;
//        }
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
        gl.glRotatef(180f, 0,1,0);
        gl.glColor4f(color[0],color[1],color[2],color[3]);

        // Draw the cubes using triangles
        for (int i = 0; i < (alienModelFaces.length / 3); i++) {
            alienFacesBuffer.position(3 * i);
            gl.glDrawElements(GL10.GL_TRIANGLES, 3, GL10.GL_UNSIGNED_SHORT, alienFacesBuffer);
        }
        alienFacesBuffer.position(0);
        // Disable vertex arrays
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glPopMatrix();  // Restore the previous model view matrix

        for (Projectile projectile : projectiles) {
            projectile.draw(gl);
        }
    }
    public boolean checkBoundaries() {
        if (x <= leftBoundary) {
            //x = leftBoundary;
            //velocityX *= -1;
            return true;
        }
        if (x >= rightBoundary) {
            //x = rightBoundary;
            //velocityX *= -1;
            return true;
        }
        return false;
    }
    public float getX() {
        return transformationMatrix[12];
    }
    public float getY() {
        return transformationMatrix[13];
    }
    public void setY() {
        if (x <= leftBoundary) x =leftBoundary;
        else if (x >= rightBoundary) x = rightBoundary;
        y -= rowDown;
        velocityX *= -1;
    }
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
