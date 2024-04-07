package c22359751;

import ie.tudublin.Visual;


public class ArushiVisual1 extends Visual
{ 

    public void settings()
    {
        println("CWD: " + System.getProperty("user.dir"));
        fullScreen(P3D, SPAN);
    }

    public void keyPressed()
    {
        if (key == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
            
        }
    }

    public void setup()
    {
        colorMode(HSB);
        setFrameSize(256);
        startMinim();
        loadAudio("java/data/meetmehalfway.mp3");     
    }

    public void draw() {
        calculateAverageAmplitude();
        calculateFrequencyBands();
        background(0);
        colorMode(HSB, 360, 100, 100);
        float hue = frameCount % 360;
        stroke(hue, 80, 80);
        fill(hue, 80, 80);
        camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
        translate(0, 0, -250);

        float scale = 0.3f; 
        float[] bands = getSmoothedBands();

        //waves on the x-axis
        noFill();
        for (int i = 0; i < 4; i++) { // Number of waves
            float waveX = i * 20 + frameCount * bands.length; // Adjust speed of waves
            stroke(360); 
            beginShape();
            for (float x = -150; x < 150; x += 2) {
                float y = sin(x * bands.length + waveX) * 10 * sin(frameCount * bands.length); // Adjust amplitude and frequency
                vertex(x, y, -10);
            }
            endShape();
        }

        //waves on the y-axis
        noFill();
        for (int i = 0; i < 2; i++) { // Number of waves
            float waveY = i * 20 + frameCount * getSmoothedAmplitude(); // Adjust speed of waves
            stroke(hue, 80, 80);
            beginShape();
            for (float y = -150; y < 150; y += 2) {
                float x = sin(y * getSmoothedAmplitude() + waveY) * 10 * sin(frameCount * getSmoothedAmplitude()); // Adjust amplitude and frequency
                vertex(x, y);
            }
            endShape();
        }

        //function to draw the heart
        drawHeart(scale);

    } 


    private void drawHeart(float scale) {

        float hue = frameCount % 360;
        stroke(hue, 80, 80);
        fill(hue, 80, 80);
        

        //first half of the heart
        beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 15;
            float x = r * 16 * pow(sin(a), 3) * scale + (map(getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = -r*(13 * cos(a) - 5*cos(2*a) - 2*cos(3*a) - cos(4*a)) * scale;
            vertex (x, y, -5);
        }
        endShape ();

        // Draw second half of the heart
        beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 15;
            float x = -r * 16 * pow(sin(a), 3) * scale - (map(getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = -r * (13 * cos(a) - 5 * cos(2 * a) - 2 * cos(3 * a) - cos(4 * a)) * scale;
            vertex(x, y, -5);
        }
        endShape();
    }

    
}           

            



