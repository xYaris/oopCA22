package ie.assignment;

import damkjer.ocd.*;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import processing.core.PApplet;

public class OOP extends PApplet {
    float rotation = 0;
    float direction = 0;
    Camera camera1;
    Camera camera2;

    Minim minim;
    AudioInput ai;
    AudioPlayer ap;
    AudioBuffer ab;

    BeatDetect beat;

    int mode = 0;

    static final int FADE = 2500;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;

    int menu = 0;
    int choice = 0;

    int screenBrightness = 0;

    String[] Songs = {"GUMMY.mp3", "POISON.mp3", "DARE.mp3"};

    public void keyPressed() {

        if (keyCode == LEFT && direction == 0) {
            direction -= 120;

            if(menu == 0) 
                menu = 2;

            else
                menu--;
            
            print(menu);

            loadMusic(Songs[menu]);
        }

        if (keyCode == RIGHT && direction == 0) {
            direction += 120;

            if(menu == 2)
                menu = 0;

            else
                menu++;

            print(menu);

            loadMusic(Songs[menu]);
        }

        if (key == ENTER) {

            // if (choice == 1) {
            //     af.pause();
            //     ay.pause();
            //     ag.pause();
            //     // wait(50);
            //     ag.play();
            //     ag.rewind();
            // }
        }

    }

    public void settings() {
        size(1200, 1000, P3D);
        // fullScreen();
    }

    public void setup() {
        minim = new Minim(this);
        
        ap = minim.loadFile("GUMMY.mp3", 1024);
        ab = ap.mix;

        // BeatDetect(1024, 44100.0f)
        beat = new BeatDetect(ap.bufferSize(), ap.sampleRate());
        beat.setSensitivity(300);

        colorMode(RGB);

        y = height / 2;
        smoothedY = y;

        lerpedBuffer = new float[width];

        camera1 = new Camera(this,
                width / 2, height / 2, 0,
                width / 2, height / 2, -width,
                0, 1, 0);

    }

    float off = 0;

    public void draw() {
        switch (choice) {
            case 0:
                textSize(100);
                textAlign(CENTER);
                camera1.feed();
                rectMode(CENTER);
                double third = width * 0.866;
                float move = (float) third;
                translate(width / 2, height / 2, -width);
                gooba();
                translate(move, 0, width + (width / 2));
                rotateY(-2 * PI / 3);
                yaris();
                rotateY(2 * PI / 3);
                translate(-2 * move, 0, 0);
                rotateY(2 * PI / 3);
                finn();
                
                hint(DISABLE_DEPTH_TEST); // 2D code starts here
                camera();
                noLights();
                if (frameCount % 60 < 30 && direction == 0) {
                    text("<Enter>", width / 2, (height) - 50);
                }

                hint(ENABLE_DEPTH_TEST); // 2D code ends here

                if (direction > 0) {
                    RotateRight();
                    direction--;
                }
                if (direction < 0) {
                    RotateLeft();
                    direction++;
                }

                break;

            case 1: // Gooba
                gooba();
                print(choice);
                break;

            case 2: // Yaris
                yaris();
                print(choice);
                break;

            case 3: // Finn
                finn();
                print(choice);
                break;
        }

    }

    public void yaris() {

        stroke(255, 0, 0);
        noFill();
        box(100);
        rect(0, 0, width - 100, height - 100);
    }

    public void finn() {
        ap.play();
        // takes input of
        BeatDetection fBeat = new BeatDetection();
        beat.detect(ap.mix);
        Boolean type = fBeat.readBeat((beat));

        background(screenBrightness);
        screenBrightness = backgroundBeat(type, screenBrightness);

        if (screenBrightness > 10)
            screenBrightness -= 2;

        stroke(0, 255, 0);
        noFill();
        box(100);
        rect(0, 0, width - 100, height - 100);

    }

    public void gooba() {
        stroke(0, 0, 255);
        noFill();
        box(100);
        rect(0, 0, width - 100, height - 100);

    }

    public void RotateRight() {
        camera1.look((float) (radians((float) (1))), 0);
    }

    public void RotateLeft() {
        camera1.look(-(float) (radians((float) (1))), 0);
    }

    public int backgroundBeat(Boolean type, int i) {

        if (type == true)
            i += 20;

        return i;
    }

    public void loadMusic(String Song) {
        ap.shiftGain(0, -50, FADE);
        ap = minim.loadFile(Song, 1024);
        ap.shiftGain(-50, 0, FADE);
        ab = ap.mix;
    }
}

class BeatDetection {

    public Boolean readBeat(BeatDetect beat) {

        if (beat.isKick())
            return true;

        return false;
    }
}