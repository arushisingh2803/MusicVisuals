package c21331753;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import java.util.ArrayList;

public class TaniaVisual1 extends PApplet {

    Minim minim;
    AudioPlayer audioPlayer;
    AudioBuffer audioBuffer;
    Eye leftEye, rightEye;
    float backgroundBrightness = 0;
    ParticleSystem particles;
    boolean[][] grid;
    boolean[][] next;

    int resolution = 5; // Smaller resolution for Game of Life
    int cols;
    int rows;

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        minim = new Minim(this);
        audioPlayer = minim.loadFile("java/data/meetmehalfway.mp3");
        audioPlayer.play();
        audioBuffer = audioPlayer.mix;

        // Initialising left and right eyes
        leftEye = new Eye(width / 2 - 200, height / 2, 150); // Smaller diameter
        rightEye = new Eye(width / 2 + 200, height / 2, 150); // Smaller diameter

        // Initialise particle system
        particles = new ParticleSystem();

        // Game of Life setup
        cols = width / resolution;
        rows = height / resolution;
        grid = new boolean[cols][rows];
        next = new boolean[cols][rows];
        initializeGrid();
    }

    @Override
    public void draw() {
        background(0);

        // Update and display Game of Life grid
        updateGrid();
        displayGrid();

        // Update and display left and right eyes
        leftEye.update();
        leftEye.display();
        rightEye.update();
        rightEye.display();

        // Update and display particles
        particles.update(leftEye, rightEye);
        particles.display();

        // Pulse the pupils to the beat of the music
        float pulseFactor = map(audioPlayer.mix.level(), 0, 1, 0.8f, 1.2f);
        leftEye.pulse(pulseFactor);
        rightEye.pulse(pulseFactor);
    }

    class Eye {
        float x, y;
        float diameter;
        float pulse;
        int pupilColor;
        float pupilX, pupilY;
        float pupilSpeed;
        float previousAudioLevel = 0;
        boolean movingRight = true;
    
        Eye(float x, float y, float diameter) {
            this.x = x;
            this.y = y;
            this.diameter = diameter;
            this.pulse = 40; // Double the initial pulse size for pupil
            this.pupilColor = color(255, 0, 0); // Initial colour (red)
            this.pupilX = x;
            this.pupilY = y;
            this.pupilSpeed = 3; // Initial pupil movement speed
        }
    
        void update() {
            // Move pupils to the music
            float currentAudioLevel = audioPlayer.mix.level();
            if (movingRight) {
                pupilX += pupilSpeed;
                if (pupilX >= x + diameter / 4) {
                    movingRight = false;
                }
            } else {
                pupilX -= pupilSpeed;
                if (pupilX <= x - diameter / 4) {
                    movingRight = true;
                }
            }
    
            // Pulse the eyes
            pulse = map(audioPlayer.mix.level(), 0, 1, 20, 60);
    
            // Change pupil color based on music beat
            pupilColor = color(random(255), random(255), random(255));
        }
    
        void pulse(float factor) {
            pulse *= factor;
        }
    
        void display() {
            // Draw eyes
            stroke(255);
            fill(255);
            ellipse(x, y, diameter, diameter); // Eye white
            fill(pupilColor);
            ellipse(pupilX, y, pulse, pulse); // Pupil
        }
    }
      

    class Particle {
        float x, y;
        float speedX, speedY;
        float accelerationY;
        float lifespan;
        int particleColor;

        Particle(float x, float y) {
            this.x = x;
            this.y = y;
            this.speedX = random(-5, 5);
            this.speedY = random(-5, 5);
            this.accelerationY = 0.1f;
            this.lifespan = random(150, 255);
            this.particleColor = color(random(255), random(255), random(255));
        }

        void update() {
            speedY += accelerationY;
            x += speedX;
            y += speedY;
            lifespan -= 1.5;
        }

        void display() {
            noStroke();
            fill(particleColor, lifespan);
            ellipse(x, y, 8, 8);
        }

        boolean isDead() {
            return lifespan < 0;
        }
    }

    class ParticleSystem {
        ArrayList<Particle> particles;

        ParticleSystem() {
            particles = new ArrayList<Particle>();
        }

        void update(Eye leftEye, Eye rightEye) {
            // Generate new particles
            particles.add(new Particle(random(width), random(height)));

            // Check for collisions with eyes
            for (Particle p : particles) {
                if (dist(p.x, p.y, leftEye.x, leftEye.y) < leftEye.diameter / 2 ||
                    dist(p.x, p.y, rightEye.x, rightEye.y) < rightEye.diameter / 2) {
                    p.lifespan = -1; // Remove particles on collision with eyes
                    break;
                }
            }

            // Update existing particles
            for (int i = particles.size() - 1; i >= 0; i--) {
                Particle p = particles.get(i);
                p.update();
                if (p.isDead()) {
                    particles.remove(i);
                }
            }
        }

        void display() {
            for (Particle p : particles) {
                p.display();
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("ie.tudublin.Sound1");
    }

    // Game of Life methods
    void initializeGrid() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = random(1) > 0.5;
            }
        }
    }

    void updateGrid() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                int neighbors = countNeighbors(grid, i, j);
                if (grid[i][j]) {
                    if (neighbors < 2 || neighbors > 3) {
                        next[i][j] = false;
                    } else {
                        next[i][j] = true;
                    }
                } else {
                    if (neighbors == 3) {
                        next[i][j] = true;
                    } else {
                        next[i][j] = false;
                    }
                }
            }
        }
        boolean[][] temp = grid;
        grid = next;
        next = temp;
    }

    int countNeighbors(boolean[][] grid, int x, int y) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int col = (x + i + cols) % cols;
                int row = (y + j + rows) % rows;
                if (grid[col][row]) {
                    count++;
                }
            }
        }
        if (grid[x][y]) {
            count--;
        }
        return count;
    }

    void displayGrid() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (grid[i][j]) {
                    fill(random(255), random(255), random(255));
                } else {
                    fill(0);
                }
                rect(i * resolution, j * resolution, resolution, resolution);
            }
        }
    }
}
