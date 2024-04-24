package c22359751;

import processing.core.PApplet;
import ie.tudublin.CombinedVisual;

public class ArushiVisual1 extends PApplet {

    CombinedVisual cv;

    // Constructor that takes a parameter of type CombinedVisual
    public ArushiVisual1(CombinedVisual cv) {
        this.cv = cv;
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
        cv.loadAudio("meetmehalfway.mp3"); // Adjust audio file name as needed
    }

    public void render() {
        cv.calculateAverageAmplitude();
        cv.background(0);
        cv.colorMode(HSB, 360, 100, 100);

        float hue = cv.frameCount % 360;
        cv.stroke(hue, 80, 80);
        cv.fill(hue, 80, 80);
        cv.camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
        cv.translate(0, 0, -250);

        float scale = 0.3f;
        float[] bands = cv.getSmoothedBands();

        // Waves on the x-axis
        cv.noFill();
        for (int i = 0; i < 4; i++) { // Number of waves
            float waveX = i * 20 + cv.frameCount * bands.length; // Adjust speed of waves
            cv.stroke(360);
            cv.beginShape();
            for (float x = -cv.width / 2; x < cv.width / 2; x += 2) { // Adjust for full width
                float y = sin(x * bands.length + waveX) * 10 * sin(cv.frameCount * bands.length); // Adjust amplitude and frequency
                cv.vertex(x, y, -10);
            }
            cv.endShape();
        }

        // Waves on the y-axis
        cv.noFill();
        for (int i = 0; i < 2; i++) { // Number of waves
            float waveY = i * 20 + cv.frameCount * cv.getSmoothedAmplitude(); // Adjust speed of waves
            cv.stroke(hue, 80, 80);
            cv.beginShape();
            for (float y = -150; y < 150; y += 2) {
                float x = sin(y * cv.getSmoothedAmplitude() + waveY) * 10 * sin(cv.frameCount * cv.getSmoothedAmplitude()); // Adjust amplitude and frequency
                cv.vertex(x, y);
            }
            cv.endShape();
        }

        // Function to draw the heart
        drawHeart(scale);
        drawGrid();
    }

    private void drawHeart(float scale) {
        float hue = cv.frameCount % 360;
        cv.stroke(hue, 80, 80);
        cv.fill(hue, 80, 80);

        // First half of the heart
        cv.beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 20;
            float x = r * 16 * pow(sin(a), 3) * scale + (map(cv.getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = -r * (13 * cos(a) - 5 * cos(2 * a) - 2 * cos(3 * a) - cos(4 * a)) * scale;
            cv.vertex(x, y, -5);
        }
        cv.endShape();

        // Draw second half of the heart
        cv.beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 20;
            float x = -r * 16 * pow(sin(a), 3) * scale - (map(cv.getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = -r * (13 * cos(a) - 5 * cos(2 * a) - 2 * cos(3 * a) - cos(4 * a)) * scale;
            cv.vertex(x, y, -5);
        }
        cv.endShape();
    }

    private void drawGrid() {
        float spacing = 20;
        float gridWidth = cv.width * 2; // Adjust for fullscreen width

        // Draw vertical lines
        for (float x = -gridWidth / 2; x < gridWidth / 2; x += spacing) {
            cv.line(x, -gridWidth / 2, 0, x, gridWidth / 2, 0);
        }

        // Draw horizontal lines
        for (float y = -gridWidth / 2; y < gridWidth / 2; y += spacing) {
            cv.line(-gridWidth / 2, y, 0, gridWidth / 2, y, 0);
        }
    }
}
