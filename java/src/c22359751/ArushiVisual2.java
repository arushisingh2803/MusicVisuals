package c22359751;

import ie.tudublin.Visual;
import processing.core.PImage;

public class ArushiVisual2 extends Visual {

    PImage albumCover;
    float lineLength; // Length of the lines
    int numLines = 20; // Number of lines
    float[] lineLengths; // Array to store lengths of individual lines


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
    }

    public void draw() {
        calculateAverageAmplitude();
        calculateFrequencyBands();
        background(0);
        colorMode(HSB, 360, 100, 100);
        float amplitude = getSmoothedAmplitude();
        
        float hue = map(amplitude, 0, 1, 0, 300);

        tint(hue, 180, 180); 
        imageMode(CENTER);
        image(albumCover, width / 2, height / 2);
    }

}
