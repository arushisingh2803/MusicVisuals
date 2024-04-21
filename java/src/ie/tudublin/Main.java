package ie.tudublin;

import c22359751.ArushiVisual1;
import c22359751.ArushiVisual2;
import c21331753.TaniaVisual1;
import c21331753.TaniaVisual2;
import c22380473.JeniedVisual1;
import c22380473.JeniedVisual2;
import processing.core.PApplet;

public class Main extends PApplet {
    int mode = 0;
    
    // Define constants for visual types
    public static final int ARUSHI_VISUAL_1 = 1;
    public static final int ARUSHI_VISUAL_2 = 2;
    public static final int TANIA_VISUAL_1 = 3;
    public static final int TANIA_VISUAL_2 = 4;
    public static final int JENIED_VISUAL_1 = 5;
    public static final int JENIED_VISUAL_2 = 5;

    // Variable to store the current visual
    private int currentVisual = ARUSHI_VISUAL_2; // Default visual is ArushiVisual2

    public void settings() {
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        startUI();
    }

    public void draw() {
        // Empty draw method to continuously display the current visual
    }

    public void startUI() {
        String[] a = { "MAIN" };
    
        // Create the selected visual based on the currentVisual variable
        switch (currentVisual) {
            case ARUSHI_VISUAL_1:
                processing.core.PApplet.runSketch(a, new ArushiVisual1());
                break;
            case ARUSHI_VISUAL_2:
                processing.core.PApplet.runSketch(a, new ArushiVisual2());
                break;
            // Add cases for other visual types here
            default:
                System.out.println("Invalid visual type.");
                break;
        }
    }
    
    public void keyPressed() {
        // Check for key presses and switch visual type accordingly
        switch (key) {
            case '1':
                currentVisual = ARUSHI_VISUAL_1;
                break;
            case '2':
                currentVisual = ARUSHI_VISUAL_2;
                break;
            // Add cases for other keys and visual types here
            default:
                // Ignore other keys
                break;
        }
        mode = currentVisual;
        startUI(); // Restart UI with the new visual type
    }
    

    public static void main(String[] args) {
        PApplet.main("ie.tudublin.Main");
    }
}
