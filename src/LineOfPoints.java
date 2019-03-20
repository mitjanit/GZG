import processing.core.PVector;

import static processing.core.PApplet.map;
import static processing.core.PApplet.println;

public class LineOfPoints extends SetOfPoints {

    PVector p1, p2;

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


    public LineOfPoints(int l, int n, PVector a, PVector b, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        super();
        this.p1 = new PVector(a.x, a.y); this.p2 = new PVector(b.x, b.y);
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, xvar, yvar);
    }

    public LineOfPoints(int l, int n, boolean e, PVector a, PVector b){
        super(l, n, e);
        this.p1 = new PVector(a.x, a.y); this.p2 = new PVector(b.x, b.y);
    }


    public void setPoints(float minDistance){

        float numAtts = numPoints*ratioAP/100.0f;
        float numReps = numPoints - numAtts;
        int na=0, nr=0, np=0;

        for (int i = 0; i < numPoints; i++) {

            float x = map(i, 0, numPoints-1, p1.x, p2.x) + xVariability.getRandomValue();
            float y = map(i, 0, numPoints-1, p1.y, p2.y) + yVariability.getRandomValue();
            PVector pos = new PVector(x, y);

            float mAtt = getMassAttValue(pos, np);
            float mRep = -getMassRepValue(pos, np);
            float spin = getSpinAngleValue(pos, np);
            float np2c = getNPCollapseValue(pos, np);

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
            println(ap);
            np++;
        }
        deletePoints(minDistance);
    }


}
