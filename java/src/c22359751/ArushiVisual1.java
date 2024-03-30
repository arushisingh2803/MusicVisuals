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
        background(0);
        stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 180, 360);
        fill(map(getSmoothedAmplitude(), 0, 1, 0, 255), 180, 360);
        camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
        translate(0, 0, -250);

        float scale = 0.3f; 
        
        //first half of the heart
        beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 8;
            float x = r * 16 * pow(sin(a), 3) * scale + (map(getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = -r*(13 * cos(a) - 5*cos(2*a) - 2*cos(3*a) - cos(4*a)) * scale;
            vertex (x, y);
        }
        endShape ();

        //second half of the heart
        beginShape();
        for (float a = 0; a < PI; a += 0.01) {
            float r = 8;
            float x = -r * 16 * pow(sin(a), 3) * scale - (map(getSmoothedAmplitude(), 0, 1, 0, 255));
            float y = -r*(13 * cos(a) - 5*cos(2*a) - 2*cos(3*a) - cos(4*a)) * scale;
            vertex (x, y);
        }
        endShape ();
    }
            
}

