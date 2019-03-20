import processing.core.PVector;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.TWO_PI;

public class WhitneyOfPoints extends SetOfPoints {

    PVector c;
    float magnify, phase, amplitude;

    public WhitneyOfPoints(int l, int n, boolean e, PVector a){
        super(l, n, e);
        this.c = new PVector(a.x, a.y);
    }

    public void setCenter(PVector a){
        this.c = new PVector(a.x, a.y);
    }

    public void setMagnify(float m){
        this.magnify = m;
    }

    public void setPhase(float p){
        this.phase = p;
    }

    public void setAmplitude(float a){
        this.amplitude = a;
    }

    public void setWhitneyParams(float m, float p, float a, int np){
        this.magnify = m; this.phase = (int)p; this.amplitude = (int)a; this.numPoints = np;
    }


    public WhitneyOfPoints(int l, int n, PVector a, float mag, float pha, float amp, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar){
        super();
        this.c = new PVector(a.x, a.y);
        this.magnify = mag;
        this.phase = (int)pha;
        this.amplitude = (int)amp;
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0.0f), new RangFloat(0.0f));
    }


    public void setPoints(float minDistance){

        float spacing = TWO_PI / this.numPoints;

        for(int i=0; i<this.numPoints; i++){
            float x = c.x + sin(spacing*amplitude*i)*(cos(spacing*phase*i)*magnify) + xVariability.getRandomValue();
            float y = c.y + sin(spacing*phase*i)*(sin(spacing*amplitude*i)*magnify) + yVariability.getRandomValue();
            PVector pos = new PVector(x, y);

            float mAtt = getMassAttValue(pos, i);
            float mRep = -getMassRepValue(pos, i);
            float spin = getSpinAngleValue(pos, i);
            float np2c = getNPCollapseValue(pos, i);

            float mass = mAtt;
            float nrand = Defaults.getRandom(0,100);
            if(nrand>ratioAP){
                mass = mRep;
            }

            AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
            this.points.add(ap);
        }
        deletePoints(minDistance);
    }




}
