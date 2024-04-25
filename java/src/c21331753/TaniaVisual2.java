package c21331753;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PImage;

public class TaniaVisual2 extends PApplet {

    Minim minim;
    AudioPlayer player;
    AudioBuffer buffer;
    PImage img;
    float imgAngle = 0; // Initial angle for image rotation

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        minim = new Minim(this);
        player = minim.loadFile("java/data/meetmehalfway.mp3");
        player.play();

        buffer = player.mix;

        img = loadImage("images/cdart-removebg-preview.png");
        img.resize(200, 200); // Resize the image to a larger size
    }

    @Override
    public void draw() {
        colorMode(HSB);
        background(0);

        float lerpedAvg = calculateAverageAmplitude();

        drawWaveform();
        drawAmplitudeCircles();

        // draw the spinning image
        updateImageAngle();
        drawSpinningImage();

        // Draw the rainbow synth ring
        drawRainbowSynthRing(lerpedAvg);
    }

    private void drawWaveform() {
        stroke(255);
        float halfHeight = height / 2;
        for (int i = 0; i < buffer.size() - 1; i++) {
            float x1 = map(i, 0, buffer.size(), 0, width);
            float x2 = map(i + 1, 0, buffer.size(), 0, width);
            float y1 = map(buffer.get(i), -1, 1, halfHeight, 0);
            float y2 = map(buffer.get(i + 1), -1, 1, halfHeight, 0);
            line(x1, y1, x2, y2);
        }
    }

    private float calculateAverageAmplitude() {
        float total = 0;
        for (int i = 0; i < buffer.size(); i++) {
            total += abs(buffer.get(i));
        }
        return total / buffer.size();
    }

    private void drawAmplitudeCircles() {
        float halfHeight = height / 2;
        float lerpedAvg = calculateAverageAmplitude();
        stroke(250, 255, 255);
        circle((float)(width / 2), (float)(height / 2), (float)(lerpedAvg * halfHeight * 2)); // Adjust the size
        stroke(200, 255, 255);
        circle((float)(width / 4), (float)(height / 2), (float)(lerpedAvg * halfHeight * 1.5)); // Adjust the size
        stroke(100, 255, 255);
        circle((float)(3 * width / 4), (float)(height / 2), (float)(lerpedAvg * halfHeight * 1.5)); // Adjust the size
    }

    // Update image rotation angle based on frame count
    private void updateImageAngle() {
        imgAngle += 0.2; // Adjust the speed of rotation here (faster)
    }

    // Draw spinning image
    private void drawSpinningImage() {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        pushMatrix();
        translate(halfWidth, halfHeight); // Move origin to center of the screen
        rotate(imgAngle); // Rotate the image
        imageMode(CENTER);
        image(img, 0, 0); // Draw the image at rotated angle
        popMatrix();
    }

    // Draw the rainbow synth ring
    private void drawRainbowSynthRing(float amplitude) {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        float circleSize = (float)(amplitude * halfHeight * 2);
        float circleAlpha = map(amplitude, 0, 1, 100, 255); // Map amplitude to alpha value
        noFill();
        for (int i = 0; i < 360; i += 10) {
            float hue = map(i, 0, 360, 0, 255);
            stroke(hue, 255, 255, circleAlpha);
            float x = halfWidth + cos(radians(i)) * circleSize;
            float y = halfHeight + sin(radians(i)) * circleSize;
            ellipse(halfWidth, halfHeight, x, y);
        }
    }

    public static void main(String[] args) {
        PApplet.main("ie.tudublin.Sound1");
    }
}
