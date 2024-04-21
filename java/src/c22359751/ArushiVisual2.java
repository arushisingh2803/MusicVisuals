package c22359751;

import ie.tudublin.Visual;
import processing.core.PImage;

public class ArushiVisual2 extends Visual {

    PImage albumCover;
    float lineLength; 
    int numLines = 30; 
    float[] lineLengths; 

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
        albumCover = loadImage("java/data/cover.png");
        albumCover.resize(width*2/3, height);
        lineLength = height * 2; 
        lineLengths = new float[numLines]; 
    }

    float angleOffset = 0; 

    public void draw() {
        calculateAverageAmplitude();
        calculateFrequencyBands();
        background(0);

        float amplitude = getSmoothedAmplitude();

        blendMode(ADD);
        
        // lines moving in circle
        strokeWeight(2);
        colorMode(HSB, 360, 100, 100);
        float angleStep = TWO_PI / numLines;
        for (int i = 0; i < numLines; i++) {
            float angle = angleOffset + i * angleStep;
            float radius = min(width, height) * 4; 

            float x1 = width / 2;
            float y1 = height / 2; 
            float x2 = x1 + cos(angle) * radius;
            float y2 = y1 + sin(angle) * radius;
    
            float hue = map(i, 0, numLines, 0, 360); 
            stroke(hue, 80, 80);
            line(x1, y1, -x2, -y2); 

            angleOffset += getSmoothedAmplitude(); 
        }
    
        blendMode(BLEND);

        float hue = map(amplitude, 0, 1, 0, 360);
        pushMatrix();
        translate(0, 0, 1); 
        imageMode(CENTER);
        tint(hue, 180, 180);
        image(albumCover, width / 2, height / 2);
        popMatrix(); 
    }

}