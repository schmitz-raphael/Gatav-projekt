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

public class Alien2 implements Drawable {
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
            1.0f, 5.0f, 9.0f,
            1.0f, 11.0f, 9.0f,
            -1.0f, 5.0f, 9.0f,
            -1.0f, 11.0f, 9.0f,
            1.0f, 5.0f, 7.0f,
            1.0f, 11.0f, 7.0f,
            -1.0f, 5.0f, 7.0f,
            -1.0f, 11.0f, 7.0f,
            -5.0f, 3.0f, 9.0f,
            -5.0f, 7.0f, 9.0f,
            -7.0f, 3.0f, 9.0f,
            -7.0f, 7.0f, 9.0f,
            -5.0f, 3.0f, 7.0f,
            -5.0f, 7.0f, 7.0f,
            -7.0f, 3.0f, 7.0f,
            -7.0f, 7.0f, 7.0f,
            -5.0f, -1.0f, 9.0f,
            -5.0f, 1.0f, 9.0f,
            -11.0f, -1.0f, 9.0f,
            -11.0f, 1.0f, 9.0f,
            -5.0f, -1.0f, 7.0f,
            -5.0f, 1.0f, 7.0f,
            -11.0f, -1.0f, 7.0f,
            -11.0f, 1.0f, 7.0f,
            -5.0f, 1.0f, 9.0f,
            -5.0f, 3.0f, 9.0f,
            -9.0f, 1.0f, 9.0f,
            -9.0f, 3.0f, 9.0f,
            -5.0f, 1.0f, 7.0f,
            -5.0f, 3.0f, 7.0f,
            -9.0f, 1.0f, 7.0f,
            -9.0f, 3.0f, 7.0f,
            1.0f, 1.0f, 9.0f,
            1.0f, 3.0f, 9.0f,
            -3.0f, 1.0f, 9.0f,
            -3.0f, 3.0f, 9.0f,
            1.0f, 1.0f, 7.0f,
            1.0f, 3.0f, 7.0f,
            -3.0f, 1.0f, 7.0f,
            -3.0f, 3.0f, 7.0f,
            3.0f, -1.0f, 9.0f,
            3.0f, 1.0f, 9.0f,
            -3.0f, -1.0f, 9.0f,
            -3.0f, 1.0f, 9.0f,
            3.0f, -1.0f, 7.0f,
            3.0f, 1.0f, 7.0f,
            -3.0f, -1.0f, 7.0f,
            -3.0f, 1.0f, 7.0f,
            -1.0f, 3.0f, 9.0f,
            -1.0f, 7.0f, 9.0f,
            -3.0f, 3.0f, 9.0f,
            -3.0f, 7.0f, 9.0f,
            -1.0f, 3.0f, 7.0f,
            -1.0f, 7.0f, 7.0f,
            -3.0f, 3.0f, 7.0f,
            -3.0f, 7.0f, 7.0f,
            -7.0f, 5.0f, 9.0f,
            -7.0f, 11.0f, 9.0f,
            -9.0f, 5.0f, 9.0f,
            -9.0f, 11.0f, 9.0f,
            -7.0f, 5.0f, 7.0f,
            -7.0f, 11.0f, 7.0f,
            -9.0f, 5.0f, 7.0f,
            -9.0f, 11.0f, 7.0f,
            -1.0f, 7.0f, 9.0f,
            -1.0f, 11.0f, 9.0f,
            -7.0f, 7.0f, 9.0f,
            -7.0f, 11.0f, 9.0f,
            -1.0f, 7.0f, 7.0f,
            -1.0f, 11.0f, 7.0f,
            -7.0f, 7.0f, 7.0f,
            -7.0f, 11.0f, 7.0f,
            3.0f, 23.0f, 9.0f,
            3.0f, 25.0f, 9.0f,
            -11.0f, 23.0f, 9.0f,
            -11.0f, 25.0f, 9.0f,
            3.0f, 23.0f, 7.0f,
            3.0f, 25.0f, 7.0f,
            -11.0f, 23.0f, 7.0f,
            -11.0f, 25.0f, 7.0f,
            -9.0f, 17.0f, 9.0f,
            -9.0f, 25.0f, 9.0f,
            -11.0f, 17.0f, 9.0f,
            -11.0f, 25.0f, 9.0f,
            -9.0f, 17.0f, 7.0f,
            -9.0f, 25.0f, 7.0f,
            -11.0f, 17.0f, 7.0f,
            -11.0f, 25.0f, 7.0f,
            3.0f, 17.0f, 9.0f,
            3.0f, 25.0f, 9.0f,
            1.0f, 17.0f, 9.0f,
            1.0f, 25.0f, 9.0f,
            3.0f, 17.0f, 7.0f,
            3.0f, 25.0f, 7.0f,
            1.0f, 17.0f, 7.0f,
            1.0f, 25.0f, 7.0f,
            3.0f, 15.0f, 9.0f,
            3.0f, 17.0f, 9.0f,
            -11.0f, 15.0f, 9.0f,
            -11.0f, 17.0f, 9.0f,
            3.0f, 15.0f, 7.0f,
            3.0f, 17.0f, 7.0f,
            -11.0f, 15.0f, 7.0f,
            -11.0f, 17.0f, 7.0f,
            -11.0f, 17.0f, 9.0f,
            -11.0f, 19.0f, 9.0f,
            -13.0f, 17.0f, 9.0f,
            -13.0f, 19.0f, 9.0f,
            -11.0f, 17.0f, 7.0f,
            -11.0f, 19.0f, 7.0f,
            -13.0f, 17.0f, 7.0f,
            -13.0f, 19.0f, 7.0f,
            5.0f, 17.0f, 9.0f,
            5.0f, 19.0f, 9.0f,
            3.0f, 17.0f, 9.0f,
            3.0f, 19.0f, 9.0f,
            5.0f, 17.0f, 7.0f,
            5.0f, 19.0f, 7.0f,
            3.0f, 17.0f, 7.0f,
            3.0f, 19.0f, 7.0f,
            -1.0f, 25.0f, 9.0f,
            -1.0f, 27.0f, 9.0f,
            -7.0f, 25.0f, 9.0f,
            -7.0f, 27.0f, 9.0f,
            -1.0f, 25.0f, 7.0f,
            -1.0f, 27.0f, 7.0f,
            -7.0f, 25.0f, 7.0f,
            -7.0f, 27.0f, 7.0f,
            -3.0f, 17.0f, 9.0f,
            -3.0f, 25.0f, 9.0f,
            -5.0f, 17.0f, 9.0f,
            -5.0f, 25.0f, 9.0f,
            -3.0f, 17.0f, 7.0f,
            -3.0f, 25.0f, 7.0f,
            -5.0f, 17.0f, 7.0f,
            -5.0f, 25.0f, 7.0f
    };

    private static final short[] alienModelFaces = {
            0, 1, 3,
            0, 3, 2,
            2, 3, 7,
            2, 7, 6,
            6, 7, 5,
            6, 5, 4,
            4, 5, 1,
            4, 1, 0,
            2, 6, 4,
            2, 4, 0,
            7, 3, 1,
            7, 1, 5,
            8, 9, 11,
            8, 11, 10,
            10, 11, 15,
            10, 15, 14,
            14, 15, 13,
            14, 13, 12,
            12, 13, 9,
            12, 9, 8,
            10, 14, 12,
            10, 12, 8,
            15, 11, 9,
            15, 9, 13,
            16, 17, 19,
            16, 19, 18,
            18, 19, 23,
            18, 23, 22,
            22, 23, 21,
            22, 21, 20,
            20, 21, 17,
            20, 17, 16,
            18, 22, 20,
            18, 20, 16,
            23, 19, 17,
            23, 17, 21,
            24, 25, 27,
            24, 27, 26,
            26, 27, 31,
            26, 31, 30,
            30, 31, 29,
            30, 29, 28,
            28, 29, 25,
            28, 25, 24,
            26, 30, 28,
            26, 28, 24,
            31, 27, 25,
            31, 25, 29,
            32, 33, 35,
            32, 35, 34,
            34, 35, 39,
            34, 39, 38,
            38, 39, 37,
            38, 37, 36,
            36, 37, 33,
            36, 33, 32,
            34, 38, 36,
            34, 36, 32,
            39, 35, 33,
            39, 33, 37,
            40, 41, 43,
            40, 43, 42,
            42, 43, 47,
            42, 47, 46,
            46, 47, 45,
            46, 45, 44,
            44, 45, 41,
            44, 41, 40,
            42, 46, 44,
            42, 44, 40,
            47, 43, 41,
            47, 41, 45,
            48, 49, 51,
            48, 51, 50,
            50, 51, 55,
            50, 55, 54,
            54, 55, 53,
            54, 53, 52,
            52, 53, 49,
            52, 49, 48,
            50, 54, 52,
            50, 52, 48,
            55, 51, 49,
            55, 49, 53,
            56, 57, 59,
            56, 59, 58,
            58, 59, 63,
            58, 63, 62,
            62, 63, 61,
            62, 61, 60,
            60, 61, 57,
            60, 57, 56,
            58, 62, 60,
            58, 60, 56,
            63, 59, 57,
            63, 57, 61,
            64, 65, 67,
            64, 67, 66,
            66, 67, 71,
            66, 71, 70,
            70, 71, 69,
            70, 69, 68,
            68, 69, 65,
            68, 65, 64,
            66, 70, 68,
            66, 68, 64,
            71, 67, 65,
            71, 65, 69,
            72, 73, 75,
            72, 75, 74,
            74, 75, 79,
            74, 79, 78,
            78, 79, 77,
            78, 77, 76,
            76, 77, 73,
            76, 73, 72,
            74, 78, 76,
            74, 76, 72,
            79, 75, 73,
            79, 73, 77,
            80, 81, 83,
            80, 83, 82,
            82, 83, 87,
            82, 87, 86,
            86, 87, 85,
            86, 85, 84,
            84, 85, 81,
            84, 81, 80,
            82, 86, 84,
            82, 84, 80,
            87, 83, 81,
            87, 81, 85,
            88, 89, 91,
            88, 91, 90,
            90, 91, 95,
            90, 95, 94,
            94, 95, 93,
            94, 93, 92,
            92, 93, 89,
            92, 89, 88,
            90, 94, 92,
            90, 92, 88,
            95, 91, 89,
            95, 89, 93,
            96, 97, 99,
            96, 99, 98,
            98, 99, 103,
            98, 103, 102,
            102, 103, 101,
            102, 101, 100,
            100, 101, 97,
            100, 97, 96,
            98, 102, 100,
            98, 100, 96,
            103, 99, 97,
            103, 97, 101,
            104, 105, 107,
            104, 107, 106,
            106, 107, 111,
            106, 111, 110,
            110, 111, 109,
            110, 109, 108,
            108, 109, 105,
            108, 105, 104,
            106, 110, 108,
            106, 108, 104,
            111, 107, 105,
            111, 105, 109,
            112, 113, 115,
            112, 115, 114,
            114, 115, 119,
            114, 119, 118,
            118, 119, 117,
            118, 117, 116,
            116, 117, 113,
            116, 113, 112,
            114, 118, 116,
            114, 116, 112,
            119, 115, 113,
            119, 113, 117,
            120, 121, 123,
            120, 123, 122,
            122, 123, 127,
            122, 127, 126,
            126, 127, 125,
            126, 125, 124,
            124, 125, 121,
            124, 121, 120,
            122, 126, 124,
            122, 124, 120,
            127, 123, 121,
            127, 121, 125,
            128, 129, 131,
            128, 131, 130,
            130, 131, 135,
            130, 135, 134,
            134, 135, 133,
            134, 133, 132,
            132, 133, 129,
            132, 129, 128,
            130, 134, 132,
            130, 132, 128,
            135, 131, 129,
            135, 129, 133
    };



    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;


    public Alien2(float x, float y, float size, float velocityX) {

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
        gl.glScalef(0.15f,0.15f,0.15f);
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
