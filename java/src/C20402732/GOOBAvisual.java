package C20402732;

import C20492576.DynamicColour;
import ie.assignment.OOP;

import processing.core.PVector;

public class GOOBAvisual extends OOP {

    OOP oop;

    public GOOBAvisual(OOP oop) {
        this.oop = oop;
        dc = new DynamicColour(oop);
    }

    DynamicColour dc;

    int bleh = 0;
    float blehcontrol = 0;
    float diameter = 0;

    public void render() {

        oop.calculateAverageAmplitude();
        
        oop.noFill();
        Kickflash();
        HatFlash();
        circle();
        centre();
        poison();

    }

    public void HatFlash() {
        oop.beat.detect(oop.getAudioPlayer().mix);
        Boolean type = oop.fBeat.HatBeat((oop.beat));
        float actualsize = map(oop.spheresize, 0, 20, 50, 100);
        oop.noFill();
        oop.strokeWeight(1);
        oop.stroke(80,180,80);
        oop.pushMatrix();
        
        oop.translate(oop.width / 2, oop.height / 2, 0);
        oop.sphere(actualsize);
        oop.translate(-oop.width, 0, 0);
        oop.sphere(actualsize);
        oop.translate(0, -oop.height, 0);
        oop.sphere(actualsize);
        oop.translate(oop.width, 0, 0);
        oop.sphere(actualsize);
        oop.popMatrix();
        oop.spheresize = backgroundBeat(type, oop.spheresize);

        if (oop.spheresize > 5)
            oop.spheresize -= 2;
    }

    public void Kickflash() {
        oop.beat.detect(oop.getAudioPlayer().mix);
        Boolean type = oop.fBeat.readBeat((oop.beat));


        //oop.background(oop.screenBrightness);
        oop.screenBrightness = backgroundBeat(type, oop.screenBrightness);

        if (oop.screenBrightness > 10)
            oop.screenBrightness -= 2;
    }

    public void circle() {
        oop.stroke(255, 120, 255);
        diameter = oop.getSmoothedAmplitude();
        diameter = map(diameter, 0, 1, 400, 500);
        oop.rotate(radians(frameCount % 360 * 2));
        oop.strokeWeight(15);
        oop.noFill();
        oop.ellipse(0, 0, diameter*2, diameter*2);
        oop.fill(0, 5);

        oop.pushMatrix();
        
        for (int j = 0; j < 360; j++) {

            oop.line(cos(j) * diameter, sin(j) * diameter, cos(j) * abs(oop.getAudioPlayer().left.get(j)) * 200 + cos(j) * diameter,
                    sin(j) * abs(oop.getAudioPlayer().right.get(j)) * 200 + sin(j) * diameter);
        }
        oop.strokeWeight(1);
        oop.popMatrix();
    }

    public void centre() {
        oop.pushMatrix();
        
        oop.strokeWeight(3);
        for (int i = 0; i < 360; i++) {
            dc.changeColour(0.01f);
            oop.rotate(radians((blehcontrol%360)+180));
            oop.pushMatrix();
            oop.translate(0, (bleh%360));
            oop.line(0,0,0,40);
            oop.popMatrix();
            bleh++;
        }
        
        oop.popMatrix();
        blehcontrol = (float) (blehcontrol+0.01);

      }

    public void poison() {

        
        float passed = oop.getAudioPlayer().position();

        float dx = map(oop.mouseX, 0, oop.width, (float) -0.2, (float) 0.2);
        PVector wind = new PVector(dx, 0);
        OOP.tlps.applyForce(wind);
        OOP.trps.applyForce(wind);
        OOP.blps.applyForce(wind);
        OOP.brps.applyForce(wind);
        OOP.tlps.run();
        OOP.trps.run();
        OOP.blps.run();
        OOP.brps.run();
        if (passed > 39500 && passed < 40000 ||
                passed > 44000 && passed < 44500 ||
                passed > 48500 && passed < 49000 ||
                passed > 53000 && passed < 53500 ||
                passed > 113000 && passed < 113500 ||
                passed > 117500 && passed < 118000 ||
                passed > 122000 && passed < 122500 ||
                passed > 126500 && passed < 127000) {
            OOP.tlps.addParticle();
            OOP.trps.addParticle();
            OOP.blps.addParticle();
            OOP.brps.addParticle();

        }
    }
}
