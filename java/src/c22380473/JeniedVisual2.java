
package c22380473;

import ie.tudublin.Visual;
import processing.core.PApplet;

public class JeniedVisual2 extends Visual {
    int numDots = 1000;
    float maxRadius;
    double growthRate = 1;
    double angleIncrement = 0.2;
    float minDotSize = 1;
    float maxDotSize = 12;
    float dotSize;
    float[] dotRadii;
    int[] dotColors;
    float[] dotAngles;
    float heartSize;
    float heartBeat;
    float heartRotation;
    int heartColor;
    float pupilSize;
    float[] pupilRadii;

    public void settings() {
        fullScreen(P3D, SPAN);
    }

    public void keyPressed() {
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    public void setup() {
        colorMode(HSB, 360, 100, 100);
        startMinim();
        loadAudio("java/data/meetmehalfway.mp3");
        // Calculate maximum radius based on smaller canvas size
        maxRadius = min(width, height) / 3;
        dotSize = maxDotSize; // Start with maximum dot size
        dotRadii = new float[numDots];
        dotColors = new int[numDots];
        dotAngles = new float[numDots];
        for (int i = 0; i < numDots; i++) {
            dotAngles[i] = random(TWO_PI); // Random initial angle for each dot
        }
        heartSize = 80;
        heartBeat = 1;
        heartRotation = 0;
        heartColor = color(255, 0, 0);
        pupilSize = 1;
        pupilRadii = new float[numDots];

    }

    public void draw() {
        background(0);
        calculateAverageAmplitude();
        calculateFrequencyBands();

        // Calculate the amplitude of the audio signal
        float amplitude = getSmoothedAmplitude();

        // Generate colors based on the beat
        for (int i = 0; i < numDots; i++) {
            dotColors[i] = color((frameCount + i) % 360, 100, 100);
        }

        // Draw the spiral
        drawSpiral(amplitude);

        // Draw the eye
        drawEye(amplitude);
    }

    void drawSpiral(float amplitude) {
        float radius = maxRadius;
        for (int i = 0; i < numDots; i++) {
            // Calculate the position of the dot
            float x = width / 2 + radius * cos(dotAngles[i]);
            float y = height / 2 + radius * sin(dotAngles[i]);

            // Draw the dot
            fill(dotColors[i]);
            noStroke();
            ellipse(x, y, dotSize, dotSize);

            // Move the dot towards the center
            radius -= growthRate * amplitude; // Only spin with the music

            // Increment the angle for the next dot with rotation
            dotAngles[i] += (float) (angleIncrement);

            // Reset angle to prevent overflow
            if (dotAngles[i] > TWO_PI) {
                dotAngles[i] -= TWO_PI;
            }
        }
    }

    void drawEye(float amplitude) {
        // Set the position of the eye
        float eyeX = width / 2;
        float eyeY = height / 2;

        // Draw the eye outline
        stroke(255);
        strokeWeight(2);
        noFill();
        ellipse(eyeX, eyeY, 1350, 800); // Eye outline

        // Calculate the heart size based on amplitude
        float heartSize = map(amplitude, 0, 1, 200, 0);

        // Calculate the heart opacity and color intensity based on amplitude
        int heartAlpha = (int) map(amplitude, 0, 1, 100, 255);
        int heartColor = color((frameCount * 2) % 360, 100, 100); // Heart color changes with time

        // Draw the heart
        noStroke();
        fill(brighten(heartColor, 50), heartAlpha); // Increase saturation and value
        beginShape();
        vertex(eyeX, eyeY - heartSize * 0.4f);
        bezierVertex(eyeX + heartSize * 0.7f, eyeY - heartSize * 0.9f,
                eyeX + heartSize * 1.5f, eyeY - heartSize * 0.2f,
                eyeX, eyeY + heartSize * 0.8f);
        bezierVertex(eyeX - heartSize * 1.5f, eyeY - heartSize * 0.2f,
                eyeX - heartSize * 0.7f, eyeY - heartSize * 0.9f,
                eyeX, eyeY - heartSize * 0.4f);
        endShape(CLOSE);
    }

    // Function to brighten a color
    int brighten(int col, float factor) {
        float[] hsb = hueSatBrightness(col);
        hsb[1] = min(hsb[1] * factor, 100);
        hsb[2] = min(hsb[2] * factor, 100);
        return color(hsb[0], hsb[1], hsb[2]);
    }

    // Get hue, saturation, and brightness of a color
    float[] hueSatBrightness(int col) {
        float[] hsb = new float[3];
        colorMode(HSB, 360, 100, 100);
        hsb = new float[] { hue(col), saturation(col), brightness(col) };
        return hsb;
    }

}