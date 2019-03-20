import processing.core.PVector;

import static processing.core.PApplet.*;

public class SpiralOfPoints extends SetOfPoints {

    PVector c;
    RangFloat radiusMin, radiusMax, radiusStep, radiusVariability;
    RangFloat angle, angleStep, randAngle;

    public void setCenter(PVector a){
        this.c = new PVector(a.x, a.y);
    }

    public void setRadiusMin(float r0, float r1){
        this.radiusMin = new RangFloat(r0,r1);
    }

    public void setRadiusMax(float r0, float r1){
        this.radiusMax = new RangFloat(r0,r1);
    }

    public void setRadiusVariability(RangFloat r){
        this.radiusVariability = new RangFloat(r.getMinValue(), r.getMaxValue());
    }

    public void setRadiusStep(RangFloat r){
        this.radiusStep = new RangFloat(r.getMinValue(), r.getMaxValue());
    }

    public void setAngle(float a0, float a1){
        this.angle = new RangFloat(a0,a1);
    }

    public void setAngleStep(RangFloat a){
        this.angleStep = new RangFloat(a.getMinValue(), a.getMaxValue());
    }

    public void setRandomAngle(RangFloat a){
        this.randAngle = new RangFloat(a.getMinValue(), a.getMaxValue());
    }



    public SpiralOfPoints(int l, int n, PVector a, RangFloat rmin,RangFloat rmax, RangFloat rv, RangFloat rs, RangFloat ar, RangFloat as, RangFloat ra, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar){
        super();
        this.c = new PVector(a.x, a.y);
        this.radiusMin = new RangFloat(rmin.getMinValue(), rmin.getMaxValue());
        this.radiusMax = new RangFloat(rmax.getMinValue(), rmax.getMaxValue());
        this.radiusStep = new RangFloat(rs.getMinValue(), rs.getMaxValue());
        this.radiusVariability = new RangFloat(rv.getMinValue(), rv.getMaxValue());
        this.angle = new RangFloat(ar.getMinValue(), ar.getMaxValue());
        this.angleStep = new RangFloat(as.getMinValue(), as.getMaxValue());
        this.randAngle = new RangFloat(ra.getMinValue(), ra.getMaxValue());
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0.0f), new RangFloat(0.0f));
    }

    public SpiralOfPoints(int l, int n,boolean e, PVector a, RangFloat rmin,RangFloat rmax, RangFloat rv, RangFloat rs, RangFloat ar, RangFloat as, RangFloat ra){
        super(l, n, e);
        this.c = new PVector(a.x, a.y);
        this.radiusMin = new RangFloat(rmin.getMinValue(), rmin.getMaxValue());
        this.radiusMax = new RangFloat(rmax.getMinValue(), rmax.getMaxValue());
        this.radiusStep = new RangFloat(rs.getMinValue(), rs.getMaxValue());
        this.radiusVariability = new RangFloat(rv.getMinValue(), rv.getMaxValue());
        this.angle = new RangFloat(ar.getMinValue(), ar.getMaxValue());
        this.angleStep = new RangFloat(as.getMinValue(), as.getMaxValue());
        this.randAngle = new RangFloat(ra.getMinValue(), ra.getMaxValue());
    }


    public void setPoints(float minDistance, boolean invertRadius, boolean invertAngle){

        float r0 = radiusMin.getMinValue();
        float r1 = radiusMax.getMaxValue();
        float rStep = radiusStep.getMaxValue();
        float ra = randAngle.getRandomValue();
        float a0 = angle.getMinValue();
        float a1 = angle.getMaxValue();
        float aStep = angleStep.getMaxValue();
        int np=0;

        for(float r = r0, a=a0; r<r1 && a<a1; r+=rStep, a+=aStep){

            if(invertRadius){ rStep = map(r, r0, r1, radiusStep.getMaxValue(), radiusStep.getMinValue()); }
            else {rStep = map(r, r0, r1, radiusStep.getMinValue(), radiusStep.getMaxValue());}
            if(invertAngle){ aStep = map(a, a0, a1, angleStep.getMaxValue(), angleStep.getMinValue());}
            else { aStep = map(a, a0, a1, angleStep.getMinValue(), angleStep.getMaxValue());}
            float rf = r + radiusVariability.getRandomValue();
            float x = c.x + rf*cos(a+ra) + xVariability.getRandomValue();
            float y = c.y + rf*sin(a+ra) + yVariability.getRandomValue();
            PVector pos = new PVector(x, y);

            float mAtt = getMassAttValue(pos, np);
            float mRep = -getMassRepValue(pos, np);
            float spin = getSpinAngleValue(pos, np);
            float np2c = getNPCollapseValue(pos, np);

            float mass = mAtt;
            float nrand = Defaults.getRandom(0,100);
            if(nrand>ratioAP){ mass = mRep;}

            AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c, new PVector(c.x, c.y), rf, a);
            this.points.add(ap);
            np++;
        }
        deletePoints(minDistance);
    }

    public void deletePoints(float minDist){
        for(int i=0; i<points.size(); i++){
            for(int j=i+1; j<points.size(); j++){
                AttractorPoint p1 = points.get(i);
                AttractorPoint p2 = points.get(j);
                if(!p1.isRemoveable()){
                    if((p1.distance(p2)<minDist)&&(!p2.isRemoveable())){
                        p2.setRemoveable(true);
                        //println("remove "+j);
                    }
                }
            }
        }
        while(this.areRemoveable()){
            for(int k=0; k<points.size(); k++){
                AttractorPoint p = points.get(k);
                if(p.isRemoveable()){
                    this.points.remove(p);
                }
            }
        }
    }


}
