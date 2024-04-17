package c22380473;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.core.PApplet;

public class JeniedVisual1 extends Visual {
    int maxDots = 2000; 
    float[] dotX = new float[maxDots]; 
    float[] dotY = new float[maxDots]; 
    float[] dotSizes = new float[maxDots];
    float maxDotSize = 15; 
    float minDotSize = 5;
    float maxSpread = 700; 
    float minSpread = 700; 
    float sphereSize = 0;
    float prevAmplitude = 0;
    float angle = 0;

    public void settings() {
        println("CWD: " + System.getProperty("user.dir"));
        fullScreen(P3D, SPAN);
    }

    public void keyPressed() {
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    public void setup() {
        colorMode(HSB);
        setFrameSize(256);
        startMinim();
        loadAudio("java/data/meetmehalfway.mp3");
    }

    public void draw() {
        background(0);
        calculateAverageAmplitude();
        calculateFrequencyBands();
        colorMode(HSB, 360, 100, 100);
        float hue = frameCount % 360;
        noStroke();
        fill(0, 100); 

        // Calculate the number of dots based on the amplitude
        float amplitude = getSmoothedAmplitude();
        int newDots = (int) map(amplitude, 0, 1, 0, maxDots);

        // Drop new dots based on the audio
        for (int i = 0; i < newDots; i++) {
            // Map dot spread based on amplitude
            float spread = map(amplitude, 0, 1, minSpread, maxSpread);
            // Randomly position the dots based on the spread
            dotX[i] = random(-spread, spread);
            dotY[i] = random(-spread, spread);
        }

        // Draw all dots
        for (int i = 0; i < newDots; i++) {
            // Map dot size based on amplitude
            float size = map(amplitude, 0, 1, minDotSize, maxDotSize);
            float brightness = map(i, 0, newDots, 50, 100); 
            fill(hue, 80, brightness);
            ellipse(width / 2 + dotX[i], height / 2 + dotY[i], size, size);
        }

        prevAmplitude = amplitude;

         
        // Draw the sphere
        drawSphere();

    }

    void drawSphere() {
        // Calculate the amplitude of the audio signal
        float amplitude = getSmoothedAmplitude();

        sphereSize = constrain(sphereSize, 190, 200); 

        // Rotate the centre sphere based on the amplitude 
        float rotationSpeed = map(amplitude, 0, 1, 0, TWO_PI);
        rotationSpeed *= -0.1; 
        angle += rotationSpeed; 

        // Draw the centre sphere in middle of canvas
        pushMatrix();
        translate(width / 2, height / 2, 0);
        rotateY(angle); // Rotate around the y-axis
        noFill();
        stroke(frameCount % 360, 80, 100); 

        // Draw the main sphere
        sphere(sphereSize);

        // Calculate the position of the 2nd sphere
        float circleRadius = 300; 
        float circleX1 = width / 5; 
        float circleY1 = height / 2 - 500; 
        float circleZ1 = 0;
 
        // Draw the 2nd sphere
        pushMatrix();
        translate(circleX1, circleY1, circleZ1); 
        rotateX(angle); 
        sphere(50); 

        // Calculate the position of the spinning sphere relative to the centre sphere
        float spinRadius = 150; // orbit
        float spinX = spinRadius; 
        float spinY = 0; 

        // Draw the 3rd spinning sphere
        pushMatrix();
        translate(spinX, spinY, 0); 
        rotateX(angle); 
        sphere(30); 
 
        popMatrix();
        popMatrix();
        popMatrix(); // Restore original transformation
    }


}
