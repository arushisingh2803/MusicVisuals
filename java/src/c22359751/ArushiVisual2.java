package c22359751;

import processing.core.PApplet;
import ie.tudublin.CombinedVisual;
import processing.core.PImage;

public class ArushiVisual2 extends PApplet {

    CombinedVisual cv;
    PImage albumCover;

    public ArushiVisual2(CombinedVisual cv) {
        this.cv = cv;
        albumCover = cv.loadImage("images/cover.png");
        albumCover.resize(width * 2 / 3, height); // Resize the image once during initialization
    }

    public void settings() {
        //cv.size(1024, 500);
        // Use this to make fullscreen
        println("CWD: " + System.getProperty("user.dir"));
        cv.fullScreen();
        // Use this to make fullscreen and use P3D for 3D graphics
        cv.fullScreen(P3D, SPAN);
    }

    public void setup() {
        cv.startMinim();
        cv.loadAudio("meetmehalfway.mp3");
    }

    public void render() { 
        cv.calculateAverageAmplitude();
        cv.background(0);
        cv.colorMode(HSB, 360, 100, 100);
        float angleOffset = 0;
        int numLines = 30; 
    
        float amplitude = cv.getSmoothedAmplitude();
    
        cv.blendMode(ADD);
    
        // Lines moving in a circle
        cv.strokeWeight(2);
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
        cv.image(albumCover, cv.width / 2, cv.height / 2); 
        cv.popMatrix();
    }
    
}
