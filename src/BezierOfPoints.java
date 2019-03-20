import processing.core.PVector;

import java.util.ArrayList;

public
class BezierOfPoints extends SetOfPoints {

    ArrayList<BezierCurve> bcs;

    public void setBezierCurves(ArrayList<BezierCurve> b){
        bcs = new ArrayList<BezierCurve>();
        for(BezierCurve bc : b){
            bcs.add(bc.copy());
        }
    }

    public BezierOfPoints(int l, int n, boolean e){
        super(l,n,e);
    }

    public BezierOfPoints(int l, int n, ArrayList<BezierCurve> beziers, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        super(l, n, mar, mrr, sar, e, c, npcr, rar, xvar, yvar);
        setBezierCurves(beziers);
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

    public ArrayList<BezierCurve> dilateBeziers(float d){
        PVector centroide = this.getCentroideFromBeziers();
        ArrayList<BezierCurve> dbs = new ArrayList<BezierCurve>();
        for(BezierCurve bc : bcs){
            dbs.add(bc.getDilatedBezierCurve(centroide, d));
        }
        return dbs;
    }

    public PVector getCentroideFromBeziers() {
        return getCentroide(this.getAnchors());
    }


    public PVector getCentroide(ArrayList<PVector> ps) {
        float x=0, y=0;
        for (PVector p : ps) {
            x += p.x;
            y += p.y;
        }
        return new PVector(x/ps.size(), y/ps.size());
    }

    public ArrayList<PVector> getAnchors() {
        ArrayList<PVector> anchors = new ArrayList<PVector>();
        for (BezierCurve bc : this.bcs) {
            anchors.add(bc.a1.copy());
            anchors.add(bc.a2.copy());
        }
        return anchors;
    }


}
