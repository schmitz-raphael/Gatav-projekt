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
    private static FloatBuffer alienNormalsBuffer;
    private static FloatBuffer alienTexturesBuffer;
    private static ShortBuffer alienFacesBuffer;
    private float[] color;
    private float[] transformationMatrix;
    private static final float leftBoundary = -2.4f;
    private static final float rightBoundary = 2.4f;
    private static final float rowDown = 0.5f;


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
            2.500000f, 1.000000f, -6.110001f,
    };

    private static final float[] alienModelNormals = {
            -0.0000f, 1.0000f, -0.0000f,
            -0.0000f, -0.0000f, 1.0000f,
            -1.0000f, -0.0000f, -0.0000f,
            -0.0000f, -1.0000f, -0.0000f,
            1.0000f, -0.0000f, -0.0000f,
            -0.0000f, -0.0000f, -1.0000f,
    };

    private static final float[] alienModelTextures = {
            0.625000f, 0.500000f,
            0.875000f, 0.500000f,
            0.875000f, 0.750000f,
            0.625000f, 0.750000f,
            0.375000f, 0.750000f,
            0.625000f, 1.000000f,
            0.375000f, 1.000000f,
            0.375000f, 0.000000f,
            0.625000f, 0.000000f,
            0.625000f, 0.250000f,
            0.375000f, 0.250000f,
            0.125000f, 0.500000f,
            0.375000f, 0.500000f,
            0.125000f, 0.750000f,
    };


    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Random random;


    public Alien1(float x, float y, float size, float velocityX) {
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

        ByteBuffer alienVerticesBB = ByteBuffer.allocateDirect(alienModelVertices.length * 4);
        alienVerticesBB.order(ByteOrder.nativeOrder());
        alienVerticesBuffer = alienVerticesBB.asFloatBuffer();
        alienVerticesBuffer.put(alienModelVertices);
        alienVerticesBuffer.position(0);

        ByteBuffer alienNormalsBB = ByteBuffer.allocateDirect(alienModelNormals.length * 4);
        alienNormalsBB.order(ByteOrder.nativeOrder());
        alienNormalsBuffer = alienNormalsBB.asFloatBuffer();
        alienNormalsBuffer.put(alienModelNormals);
        alienNormalsBuffer.position(0);

        ByteBuffer alienTexturesBB = ByteBuffer.allocateDirect(alienModelTextures.length * 4);
        alienTexturesBB.order(ByteOrder.nativeOrder());
        alienTexturesBuffer = alienTexturesBB.asFloatBuffer();
        alienTexturesBuffer.put(alienModelTextures);
        alienTexturesBuffer.position(0);

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

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, alienNormalsBuffer);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, alienTexturesBuffer);

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
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

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
