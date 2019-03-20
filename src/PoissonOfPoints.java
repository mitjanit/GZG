import processing.core.PVector;

import java.util.ArrayList;

public
class PoissonOfPoints extends SetOfPoints {

    PVector p1, p2, c;
    int poissonDist = 0; // Rect
    PoissonRect pr;
    PoissonCircle pc;
    ArrayList<PVector> poissons;

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

    public void setPoissonDist(int d){
        this.poissonDist = d;
    }


    public PoissonOfPoints(int l, int n, RangFloat x, RangFloat y, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        super(l, n, x, y, mar, mrr, sar, e, c, npcr, rar, xvar, yvar);
        //int l, int n, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar
        this.p1 = new PVector(x.getMinValue(), y.getMinValue());
        this.p2 = new PVector(x.getMaxValue(), y.getMaxValue());
        //setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0.0), new RangFloat(0.0));
    }

    public PoissonOfPoints(int l, int n, boolean e){
        super(l,n,e);
    }

    public void setPoissonPoints(PoissonRect pr){
        this.poissons = pr.getPoints();
    }

    public void setPoissonPoints(PoissonCircle pc){
        this.poissons = pc.getPoints();
    }

    public void setPoissonPoints(PoissonPolygon pp){
        this.poissons = pp.getPoints();
    }


    public void setPoints(float minDistance, ArrayList<PVector> poissons, float dx, float dy){

        float numAtts = poissons.size()*ratioAP/100.0f;
        float numReps = poissons.size() - numAtts;
        int na=0, nr=0, np=0;

        for(PVector p : poissons){

            float x = p.x + xVariability.getRandomValue() + dx;
            float y = p.y + yVariability.getRandomValue() + dy;
            PVector pos = new PVector(x, y);

            float mAtt = getMassAttValue(pos, poissons.size());
            float mRep = -getMassRepValue(pos, poissons.size());
            float spin = getSpinAngleValue(pos, poissons.size());
            float np2c = getNPCollapseValue(pos, poissons.size());

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
