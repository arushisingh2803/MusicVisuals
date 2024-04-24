package c22359751;

import ie.tudublin.CombinedVisual;
import processing.core.PApplet;
import processing.core.PImage;

public class ArushiVisual2 extends PApplet {

    CombinedVisual cv;
    int numLines = 30;
    PImage albumCover; // Declare albumCover variable here

    // Constructor that takes a parameter of type CombinedVisual
    public ArushiVisual2(CombinedVisual cv) {
        this.cv = cv;
    }

    public void settings() {
        cv.fullScreen();
        cv.fullScreen(P3D, SPAN);
    }

    public void keyPressed() {
        if (key == ' ') {
            cv.getAudioPlayer().cue(0);
            cv.getAudioPlayer().play();
        }
    }

    public void setup() {
        cv.background(0);
        cv.colorMode(HSB, 360, 100, 100);
        cv.startMinim();
        cv.loadAudio("java/data/meetmehalfway.mp3");
        albumCover = cv.loadImage("java/data/cover.png"); // Load the albumCover image here
        albumCover.resize(cv.width * 2 / 3, cv.height);
    }

    float angleOffset = 0;

    public void render() { 
        cv.calculateAverageAmplitude();
        cv.background(0);

        float amplitude = cv.getSmoothedAmplitude();

        cv.blendMode(ADD);

        // Lines moving in a circle
        cv.strokeWeight(2);
        cv.colorMode(HSB, 360, 100, 100);
        float angleStep = TWO_PI / numLines;
        for (int i = 0; i < numLines; i++) {
            float angle = angleOffset + i * angleStep;
            float radius = min(cv.width, cv.height) * 4;

            float x1 = cv.width / 2;
            float y1 = cv.height / 2;
            float x2 = x1 + cos(angle) * radius;
            float y2 = y1 + sin(angle) * radius;

            float hue = map(i, 0, numLines, 0, 360);
            cv.stroke(hue, 80, 80);
            cv.line(x1, y1, -x2, -y2);

            angleOffset += cv.getSmoothedAmplitude();
        }

        cv.blendMode(BLEND);

        float hue = map(amplitude, 0, 1, 0, 360);
        cv.pushMatrix();
        cv.translate(0, 0, 1);
        cv.imageMode(CENTER);
        cv.tint(hue, 180, 180);
        // Render the album cover image if it's not null
        if (albumCover != null) {
            cv.image(albumCover, cv.width / 2, cv.height / 2);
        }
        cv.popMatrix();
    }
}
