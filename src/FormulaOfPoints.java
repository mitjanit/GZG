import processing.core.PVector;

import static processing.core.PApplet.*;
import static processing.core.PConstants.TWO_PI;

public class FormulaOfPoints extends SetOfPoints {

    PVector c;

    float scaleR, scaleRAmount;
    float n1, n2, n3;
    int m, s, numPoints;

    public FormulaOfPoints(int l, int n, boolean e, PVector a){
        super(l, n, e);
        this.c = new PVector(a.x, a.y);
    }

    public void setCenter(PVector a){
        this.c = new PVector(a.x, a.y);
    }

    public void setScaleParams(float s1, float s2){
        this.scaleR = s1; this.scaleRAmount = s2;
    }

    public void setFormulaParams(float n1, float n2, float n3, int m, int s, int np){
        this.n1 = n1; this.n2 = n2; this.n3 = n3;
        this.m = m; this.s = s; this.numPoints = np;
    }


    public FormulaOfPoints(int l, int n, PVector a, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar){
        super();
        this.c = new PVector(a.x, a.y);
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0.0f), new RangFloat(0.0f));
    }


    public void setPoints(float minDistance){

        float newscaler = scaleR;
        for (int s0 = s; s0 > 0; s0--) {

            float mm = m + s0;
            float nn1 = n1 + s0;
            float nn2 = n2 + s0;
            float nn3 = n3 + s0;
            newscaler = newscaler * scaleRAmount;
            float sscaler = newscaler;

            PVector[] points = superformula(mm, nn1, nn2, nn3);

            int np=0;
            for (int i = 0;i < points.length; i++) {

                float x = c.x + points[i].x*sscaler + xVariability.getRandomValue();
                float y = c.y + points[i].y*sscaler + yVariability.getRandomValue();
                PVector pos = new PVector(x, y, 0);

                float mAtt = getMassAttValue(pos, np);
                float mRep = -getMassRepValue(pos, np);
                float spin = getSpinAngleValue(pos, np);
                float np2c = getNPCollapseValue(pos, np);

                float mass = mAtt;
                float nrand = Defaults.getRandom(0,100);
                if(nrand>ratioAP){
                    mass = mRep;
                }

                AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
                this.points.add(ap);
                np++;
                println("ADDED POINT TO FORMULA OF POINTS");
            }
        }
        deletePoints(minDistance);
    }


    PVector[] superformula(float m,float n1,float n2,float n3) {
        float phi = TWO_PI / this.numPoints;
        PVector[] points = new PVector[this.numPoints+1];
        for (int i = 0;i <= this.numPoints;i++) {
            points[i] = superformulaPoint(m,n1,n2,n3,phi * i);
        }
        return points;
    }

    PVector superformulaPoint(float m,float n1,float n2,float n3,float phi) {
        float r;
        float t1,t2;
        float a=1,b=1;
        float x = 0;
        float y = 0;

        t1 = cos(m * phi / 4) / a;
        t1 = abs(t1);
        t1 = pow(t1,n2);

        t2 = sin(m * phi / 4) / b;
        t2 = abs(t2);
        t2 = pow(t2,n3);

        r = pow(t1+t2,1/n1);
        if (abs(r) == 0) {
            x = 0;
            y = 0;
        }
        else {
            r = 1 / r;
            x = r * cos(phi);
            y = r * sin(phi);
        }

        return new PVector(x, y);
    }


}
