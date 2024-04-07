package c22359751;

import ie.tudublin.Visual;

public class ArushiVisual1 extends Visual
{

    public void settings()
    {
        size(800, 800, P3D);
        println("CWD: " + System.getProperty("user.dir"));
        //fullScreen(P3D, SPAN);
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
        noCursor();
        
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
        
        noFill();
        for (int i = 0; i < 10; i++) { // Number of waves
            float waveX = i * 20 + frameCount * getSmoothedAmplitude(); // Adjust speed of waves
            stroke(hue, 80, 80); 
            beginShape();
            for (float x = -150; x < 150; x += 2) {
                float y = sin(x * getSmoothedAmplitude() + waveX) * 10 * sin(frameCount * getSmoothedAmplitude()); // Adjust amplitude and frequency
                vertex(x, y);
            }
            endShape();
        }

        noFill();
        for (int i = 0; i < 10; i++) { // Number of waves
            float waveY = i * 20 + frameCount * getSmoothedAmplitude(); // Adjust speed of waves
            stroke(hue, 80, 80);
            beginShape();
            for (float y = -150; y < 150; y += 2) {
                float x = sin(y * getSmoothedAmplitude() + waveY) * 10 * sin(frameCount * getSmoothedAmplitude()); // Adjust amplitude and frequency
                vertex(x, y);
            }
            endShape();
        }

        stroke(hue, 80, 80);
        fill(hue, 80, 80);

        //first half of the heart
        beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 15;
            float x = r * 16 * pow(sin(a), 3) * scale + (map(getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = -r*(13 * cos(a) - 5*cos(2*a) - 2*cos(3*a) - cos(4*a)) * scale;
            vertex (x, y);
        }
        endShape ();

        //second half of the heart
        beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 15;
            float x = -r * 16 * pow(sin(a), 3) * scale - (map(getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = -r*(13 * cos(a) - 5*cos(2*a) - 2*cos(3*a) - cos(4*a)) * scale;
            vertex (x, y);
        }
        
        endShape ();

        
    } 
        
}     

            


