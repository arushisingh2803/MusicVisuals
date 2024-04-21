package ie.tudublin;

import c22359751.ArushiVisual1;
import c22359751.ArushiVisual2;
import example.CubeVisual;
import example.MyVisual;
import example.RotatingAudioBands;
public class Main {

    public void startUI() {
        String[] a = { "MAIN" };
        processing.core.PApplet.runSketch(a, new ArushiVisual2());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startUI();
    }
}