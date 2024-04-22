package ie.tudublin;

import c22380473.JeniedVisual1;
import c22380473.JeniedVisual2;
import example.CubeVisual;
import example.MyVisual;
import example.RotatingAudioBands;

public class Main {

    public void startUI() {
        String[] a = { "MAIN" };
        processing.core.PApplet.runSketch(a, new JeniedVisual2());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startUI();
    }
}