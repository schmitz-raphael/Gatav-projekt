package hskl.de.projekt.Objects;

import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class SpaceShip {
    // Coordinates of the spaceship
    private float x;
    private float y;

    private float vx;
    // Color of the spaceship (red, green, blue, alpha)
    private float[] color;

    public float[] transformationMatrix;

    // Vertices for a smaller cube (scale down by a factor of 0.25)

    private float[] vertices = {
            // Front face
            -0.125f, -0.125f,  0.125f,
            0.125f, -0.125f,  0.125f,
            -0.125f,  0.125f,  0.125f,
            0.125f,  0.125f,  0.125f,
            // Back face
            -0.125f, -0.125f, -0.125f,
            -0.125f,  0.125f, -0.125f,
            0.125f, -0.125f, -0.125f,
            0.125f,  0.125f, -0.125f,
            // Left face
            -0.125f, -0.125f, -0.125f,
            -0.125f, -0.125f,  0.125f,
            -0.125f,  0.125f, -0.125f,
            -0.125f,  0.125f,  0.125f,
            // Right face
            0.125f, -0.125f, -0.125f,
            0.125f, -0.125f,  0.125f,
            0.125f,  0.125f, -0.125f,
            0.125f,  0.125f,  0.125f,
            // Top face
            -0.125f,  0.125f, -0.125f,
            0.125f,  0.125f, -0.125f,
            -0.125f,  0.125f,  0.125f,
            0.125f,  0.125f,  0.125f,
            // Bottom face
            -0.125f, -0.125f, -0.125f,
            -0.125f, -0.125f,  0.125f,
            0.125f, -0.125f, -0.125f,
            0.125f, -0.125f,  0.125f
    };

    private FloatBuffer vertexBuffer;

    private float leftBoundary = -1.0f;
    private float rightBoundary = 1.0f;
    private float bottomBoundary = -1.0f;
    private float topBoundary = 1.0f;

    public SpaceShip() {
        this.x = 0;
        this.y = -2.0f;
        this.vx = 1;  // Initial velocity
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

    public void draw(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();  // Save the current model view matrix

        // Apply the transformation matrix
        gl.glMultMatrixf(transformationMatrix, 0);

        // Set the color of the spaceship
        gl.glColor4f(color[0], color[1], color[2], color[3]);

        // Enable vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Draw the cube faces using GL_TRIANGLES for simplicity
        for (int i = 0; i < vertices.length / 3; i += 4) {
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i, 4);
        }

        // Disable vertex arrays
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glPopMatrix();  // Restore the previous model view matrix
    }

    public void updateShip(float fracSec) {
        // Update position
        x += vx * fracSec;

        // Collision detection with the boundaries
        if (x < leftBoundary) {
            x = leftBoundary;
            vx *= -1; // Bounce back
        } else if (x > rightBoundary) {
            x = rightBoundary;
            vx *= -1; // Bounce back
        }

        // Update transformation matrix with new position
        Matrix.setIdentityM(transformationMatrix, 0);
        Matrix.translateM(transformationMatrix, 0, x, y, 0);

        // Log position and velocity for debugging
        Log.d("SpaceShip", "updateShip: x=" + x + ", vx=" + vx);

        // Rotate the spaceship (optional, for visual effect)
        rotate(0, vx * 3, 0.0f);
    }

    public void setVelocity(float vx) {
        this.vx = vx;
    }

    public float getVelocity() {
        return vx;
    }

    public void rotate(float angleX, float angleY, float angleZ) {
        // Apply the rotations to the transformation matrix
        Matrix.rotateM(transformationMatrix, 0, angleX, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(transformationMatrix, 0, angleY, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(transformationMatrix, 0, angleZ, 0.0f, 0.0f, 1.0f);
    }
}
