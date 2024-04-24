package c22359751;

import processing.core.PApplet;
import ie.tudublin.CombinedVisual;

public class ArushiVisual1 extends PApplet {

    CombinedVisual cv;


    public ArushiVisual1(CombinedVisual cv) {
        this.cv = cv;
    }



    public void render() {
        cv.calculateAverageAmplitude();
        float amplitude = cv.getSmoothedAmplitude();
        cv.background(0);
        cv.colorMode(HSB, 360, 100, 100);
        float hue = map(amplitude, 0, 1, 0, 360);
        cv.stroke(hue, 80, 80);
        cv.fill(hue, 80, 80);
        //camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
        //translate(0, 0, -250);
        float scale = 0.4f;
        //float[] bands = cv.getSmoothedBands();


        // Function to draw the heart
        drawHeart(scale);
        drawGrid();
    }

    private void drawHeart(float scale) {
        float amplitude = cv.getSmoothedAmplitude();
        cv.colorMode(HSB, 360, 100, 100);
        float hue = map(amplitude, 0, 1, 0, 360);
        cv.stroke(hue, 80, 80);
        cv.fill(hue, 80, 80);
    
        float centerX = cv.width / 2;  // Calculate center X coordinate
        float centerY = cv.height / 2; // Calculate center Y coordinate
    
        // First half of the heart
        cv.beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 50;
            float x = centerX + r * 16 * pow(sin(a), 3) * scale + (map(cv.getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = centerY - r * (13 * cos(a) - 5 * cos(2 * a) - 2 * cos(3 * a) - cos(4 * a)) * scale;
            cv.vertex(x, y, -5);
        }
        cv.endShape();
    
        // Draw second half of the heart
        cv.beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 50;
            float x = centerX - r * 16 * pow(sin(a), 3) * scale - (map(cv.getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = centerY - r * (13 * cos(a) - 5 * cos(2 * a) - 2 * cos(3 * a) - cos(4 * a)) * scale;
            cv.vertex(x, y, -5);
        }
        cv.endShape();
    }
    
    private void drawGrid() {
        float amplitude = cv.getSmoothedAmplitude();
        float spacing = 50;
        cv.colorMode(HSB, 360, 100, 100);
        float hue = map(amplitude, 0, 1, 0, 360);
        cv.stroke(hue, 80, 80);
        float centerX = cv.width;  // Calculate center X coordinate
        float centerY = cv.height; // Calculate center Y coordinate
    
        // Draw vertical lines
        for (float x = -centerX; x < centerX; x += spacing) {
            cv.line(x, -centerY, 0, x, centerY, 0);
        }
    
        // Draw horizontal lines
        for (float y = -centerY; y < centerY; y += spacing) {
            cv.line(-centerX, y, 0, centerX, y, 0);
        }
    }
    
}
