import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.map;
import static processing.core.PApplet.println;

public class PolyLineOfPoints extends SetOfPoints {

    ArrayList<PVector> ps;

    public void setVertexs(ArrayList<PVector> a){
        ps = new ArrayList<PVector>();
        ps.addAll(a);
    }


    public PolyLineOfPoints(int l, int n, ArrayList<PVector> a, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        super();
        ps = new ArrayList<PVector>(); ps.addAll(a);
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, xvar, yvar);
    }

    public PolyLineOfPoints(int l, int n, boolean e, ArrayList<PVector> a){
        super(l, n, e);
        ps = new ArrayList<PVector>(); ps.addAll(a);
    }


    public void setPoints(float minDistance){

        float numAtts = numPoints*ratioAP/100.0f;
        float numReps = numPoints - numAtts;

        for(int j=1; j<ps.size(); j++){

            PVector p1 = ps.get(j-1);
            PVector p2 = ps.get(j);
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


}
