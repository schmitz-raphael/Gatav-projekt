package hskl.de.projekt.Objects;

import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Projectile implements Drawable {
    private float x;
    private float y;

    // Vertices for a narrow rectangular projectile
    private float[] vertices = {
            // Front face
            -0.01f, -0.05f,  0.05f,
            0.01f, -0.05f,  0.05f,
            -0.01f,  0.05f,  0.05f,
            0.01f,  0.05f,  0.05f,
            // Back face
            -0.01f, -0.05f, -0.05f,
            -0.01f,  0.05f, -0.05f,
            0.01f, -0.05f, -0.05f,
            0.01f,  0.05f, -0.05f,
            // Left face
            -0.01f, -0.05f, -0.05f,
            -0.01f, -0.05f,  0.05f,
            -0.01f,  0.05f, -0.05f,
            -0.01f,  0.05f,  0.05f,
            // Right face
            0.01f, -0.05f, -0.05f,
            0.01f, -0.05f,  0.05f,
            0.01f,  0.05f, -0.05f,
            0.01f,  0.05f,  0.05f,
            // Top face
            -0.01f,  0.05f, -0.05f,
            0.01f,  0.05f, -0.05f,
            -0.01f,  0.05f,  0.05f,
            0.01f,  0.05f,  0.05f,
            // Bottom face
            -0.01f, -0.05f, -0.05f,
            -0.01f, -0.05f,  0.05f,
            0.01f, -0.05f, -0.05f,
            0.01f, -0.05f,  0.05f
    };

    private float[] color;

    private FloatBuffer vertexBuffer;

    public float[] transformationMatrix;
    private float leftBoundary = -1.0f;
    private float rightBoundary = 1.0f;
    private float bottomBoundary = -3.0f;
    private float topBoundary = 3.0f;

    public Projectile(float x, float y) {
        this.x = x;
        this.y = y;

        this.color = new float[]{1.0f, 0.0f, 0.0f, 1.0f}; // Red by default

        transformationMatrix = new float[16];
        Matrix.setIdentityM(transformationMatrix, 0);

        // Initialize the vertex buffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4); // 4 bytes per float
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();  // Save the current model view matrix

        // Apply the transformation matrix
        gl.glMultMatrixf(transformationMatrix, 0);

        // Set the color of the projectile
        gl.glColor4f(color[0], color[1], color[2], color[3]);

        // Enable vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Draw the rectangle faces using GL_TRIANGLE_STRIP for simplicity
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);

        // Disable vertex arrays
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glPopMatrix();  // Restore the previous model view matrix
    }

    public void updateProjectile(float fracSec) {
        float PROJECTILESPEED = 5;
        y += PROJECTILESPEED * fracSec;
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);
    }

    public boolean isOutOfBounds() {
        return x < leftBoundary || x > rightBoundary || y < bottomBoundary || y > topBoundary;
    }
}
