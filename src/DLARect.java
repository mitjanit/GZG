import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public class DLARect {

    float r;
    int numPoints;
    int count;
    PVector topLeft, bottomRight, c;
    float rectWidth, rectHeight;

    ArrayList<PVector> pts;

    DLARect(float r, int np, PVector topLeft, PVector bottomRight, PVector c){

        this.r = r;
        this.numPoints = np;
        this.c = c;
        this.topLeft = topLeft; this.bottomRight = bottomRight;
        this.rectWidth = abs(topLeft.x - bottomRight.x);
        this.rectHeight = abs(topLeft.y - bottomRight.y);
        pts = new ArrayList<PVector>();

    }

    ArrayList<PVector> getDLAPoints(){
        return pts;
    }

    public void setDLAPoints() {

        println("Setting DLA points ............");
        pts = new ArrayList<PVector>();
        pts.add(new PVector(c.x, c.y));
        count = 1;

        while (count < numPoints) {
            PVector axis = new PVector(Defaults.getRandom(topLeft.x, bottomRight.x), Defaults.getRandom(topLeft.y, bottomRight.y));
            float minDist = 1000;
            int pin = 0;
            for (int i = 0; i < pts.size(); i++) {                      //find the closest particle of the organism
                PVector pi = pts.get(i);
                float updtDist = dist(axis.x, axis.y, pi.x, pi.y);
                if ((updtDist < minDist)) {
                    minDist = updtDist;
                    pin = i;
                }
            }

            PVector ppin = pts.get(pin);
            float theta = atan2(axis.y - ppin.y, axis.x - ppin.x);
            float nx = ppin.x + cos(theta) * r * 2;
            float ny = ppin.y + sin(theta) * r * 2;
            if (minDistanceDLA(nx, ny) > 2 * r) {
                float x = ppin.x + cos(theta) * r * 2;
                float y = ppin.y + sin(theta) * r * 2;
                PVector plast = new PVector(x, y);
                pts.add(plast);
                println("Added " + count);
                count++;
            }
        }
    }

    public float minDistanceDLA(float xx, float yy) {
        float minDist=1000;
        for (int i=0; i<count; i++) {
            PVector p = this.pts.get(i);
            float d= dist(p.x, p.y, xx, yy);
            if (d < minDist) {
                minDist=d;
            }
        }
        return minDist;
    }

    void drawDLA(PApplet pA){
        for(PVector p : pts){
            pA.fill(0); pA.noStroke();
            pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2);
                pA.translate(p.x, p.y);
                pA.ellipse(0,0, 5, 5);
            pA.popMatrix();
        }
    }

    void drawDLARect(PApplet pA){
        pA.noFill();
        pA.stroke(255, 50); pA.strokeWeight(1);
        pA.rect(topLeft.x, topLeft.y, rectWidth, rectHeight);
    }

}
