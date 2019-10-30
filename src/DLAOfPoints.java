/**
 * Afegit per provar GIT PULL
 */

import processing.core.PVector;
import java.util.ArrayList;
import static processing.core.PApplet.*;

public class DLAOfPoints extends SetOfPoints {

    PVector p1, p2, c;
    int count;
    //float r= 20;
    ArrayList<PVector> dlas;

    public void setVertexs(PVector a, PVector b){
        this.p1 = new PVector(a.x, a.y);
        this.p2 = new PVector(b.x, b.y);
    }

    public void setVertex1(PVector a){
        this.p1 = new PVector(a.x, a.y);
    }

    public void setVertex2(PVector b){
        this.p2 = new PVector(b.x, b.y);
    }

    public void setCentre(PVector c){
        this.c = new PVector(c.x, c.y);
    }


    public DLAOfPoints(int l, int n, RangFloat x, RangFloat y, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        super(l, n, x, y, mar, mrr, sar, e, c, npcr, rar, xvar, yvar);
        this.p1 = new PVector(x.getMinValue(), y.getMinValue());
        this.p2 = new PVector(x.getMaxValue(), y.getMaxValue());
        this.dlas = new ArrayList<PVector>();
        //setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0.0), new RangFloat(0.0));
    }

    public DLAOfPoints(int l, int n, boolean e){
        super(l,n,e);
        this.dlas = new ArrayList<PVector>();
    }

    public void setDLAPoints(float r){

        println("Setting DLA points ............");
        this.dlas = new ArrayList<PVector>();
        this.dlas.add(new PVector(c.x, c.y));
        int count = 1;

        while(count<numPoints) {
            PVector axis = new PVector(Defaults.getRandom(p1.x, p2.x), Defaults.getRandom(p1.y, p2.y));
            println("AXIS: "+axis.x+", "+axis.y);
            float minDist = 1000;
            int pin = 0;
            for (int i = 0; i < this.dlas.size(); i++) {                      //find the closest particle of the organism
                PVector pi = this.dlas.get(i);
                float updtDist = dist(axis.x, axis.y, pi.x, pi.y);
                if ((updtDist < minDist)) {
                    minDist = updtDist;
                    pin = i;
                }
            }

            PVector ppin = this.dlas.get(pin);
            float theta = atan2(axis.y - ppin.y, axis.x - ppin.x);
            float nx = ppin.x + cos(theta) * r * 2;
            float ny = ppin.y + sin(theta) * r * 2;
            if (minDistanceDLA(nx, ny) > 2 * r) {
                float x = ppin.x + cos(theta) * r * 2;
                float y = ppin.y + sin(theta) * r * 2;
                PVector plast = new PVector(x, y);
                this.dlas.add(plast);
                println("Added " + count);
                count++;
            }
        }

    }



    public float minDistanceDLA(float xx, float yy) {
        float minDist=1000;
        for (int i=0; i<count; i++) {
            PVector p = this.dlas.get(i);
            float d= dist(p.x, p.y, xx, yy);
            if (d < minDist) {
                minDist=d;
            }
        }
        return minDist;
    }

    public void setPoints(float minDistance, ArrayList<PVector> pts, float dx, float dy){

        float numAtts = pts.size()*ratioAP/100.0f;
        float numReps = pts.size() - numAtts;
        int na=0, nr=0, np=0;

        for(PVector p : pts){

            float x = p.x + xVariability.getRandomValue() + dx;
            float y = p.y + yVariability.getRandomValue() + dy;
            PVector pos = new PVector(x, y);

            float mAtt = getMassAttValue(pos, pts.size());
            float mRep = -getMassRepValue(pos, pts.size());
            float spin = getSpinAngleValue(pos, pts.size());
            float np2c = getNPCollapseValue(pos, pts.size());

            //Random Distribution of Att & Rep
            float mass = mAtt;
            if((na==numAtts) && (nr<numReps)){
                mass=mRep; nr++;
            } else if((nr==numReps) && (na<numAtts)){
                na++;
            }
            else {
                if(Defaults.getRandom(0,100)>ratioAP){
                    mass = mRep; nr++;
                } else {
                    na++;
                }
            }

            AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
            points.add(ap);
        }
        deletePoints(minDistance);
    }


}
