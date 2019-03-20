import processing.core.PVector;

import static processing.core.PApplet.*;

public class WaveOfPoints extends SetOfPoints {

    PVector p1, p2;
    RangFloat amplitud, xStep;
    RangFloat angle, angleStep, randAngle;

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

    public void setAmplitud(float a0, float a1){
        this.amplitud = new RangFloat(a0,a1);
    }

    public void setAmplitud(RangFloat a){
        this.amplitud = new RangFloat(a.getMinValue(), a.getMaxValue());
    }

    public void setXStep(float x0, float x1){
        this.xStep = new RangFloat(x0,x1);
    }

    public void setXStep(RangFloat x){
        this.xStep = new RangFloat(x.getMinValue(), x.getMaxValue());
    }


    public void setAngle(float a0, float a1){
        this.angle = new RangFloat(a0,a1);
    }

    public void setAngleStep(RangFloat a){
        this.angleStep = new RangFloat(a.getMinValue(), a.getMaxValue());
    }

    public void setRandomAngle(float a0, float a1){
        this.randAngle = new RangFloat(a0,a1);
    }

    public void setRandomAngle(RangFloat a){
        this.randAngle = new RangFloat(a.getMinValue(), a.getMaxValue());
    }



    public WaveOfPoints(int l, int n, PVector a, PVector b, RangFloat amp,RangFloat xs, RangFloat ar, RangFloat as, RangFloat ra, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar){
        super();
        this.p1 = new PVector(a.x, a.y); this.p2 = new PVector(b.x, b.y);
        this.amplitud = new RangFloat(amp.getMinValue(), amp.getMaxValue());
        this.xStep = new RangFloat(xs.getMinValue(), xs.getMaxValue());
        //this.radiusStep = new RangFloat(rs.getMinValue(), rs.getMaxValue());
        //this.radiusVariability = new RangFloat(rv.getMinValue(), rv.getMaxValue());
        this.angle = new RangFloat(ar.getMinValue(), ar.getMaxValue());
        this.angleStep = new RangFloat(as.getMinValue(), as.getMaxValue());
        this.randAngle = new RangFloat(ra.getMinValue(), ra.getMaxValue());
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0.0f), new RangFloat(0.0f));
    }

    public WaveOfPoints(int l, int n, boolean e, PVector a, PVector b, RangFloat amp,RangFloat xs, RangFloat ar, RangFloat as, RangFloat ra){
        super(l, n, e);
        this.p1 = new PVector(a.x, a.y); this.p2 = new PVector(b.x, b.y);
        this.amplitud = new RangFloat(amp.getMinValue(), amp.getMaxValue());
        this.xStep = new RangFloat(xs.getMinValue(), xs.getMaxValue());
        this.angle = new RangFloat(ar.getMinValue(), ar.getMaxValue());
        this.angleStep = new RangFloat(as.getMinValue(), as.getMaxValue());
    }

    public void setPoints(float minDistance, boolean ampAsc){

        float xs = xStep.getRandomValue(); //println("XS:"+xs);
        float as = angleStep.getRandomValue(); //println("AS:"+as);
        float h = amplitud.getRandomValue(); //println("H:"+h);
        float a0 = angle.getRandomValue(); //println("A0:"+a0);
        float a = a0;
        int np=0;

        float distX = abs(p1.x - p2.x); float distY = abs(p1.y - p2.y);

        if(distX >= distY){
            float minX = min(p1.x, p2.x); float maxX = max(p1.x, p2.x);
            for(float x = minX; x < maxX; x+=xs){
                if(ampAsc) h = map(x, minX, maxX, amplitud.getMinValue(), amplitud.getMaxValue());
                else h = map(x, minX, maxX, amplitud.getMaxValue(), amplitud.getMinValue());
                float xf = x + xVariability.getRandomValue();
                float y = map(x, p1.x, p2.x, p1.y, p2.y) + h*sin(a) + yVariability.getRandomValue();
                PVector pos = new PVector(xf, y);

                float mAtt = getMassAttValue(pos, np);
                float mRep = -getMassRepValue(pos, np);
                float spin = getSpinAngleValue(pos, np);
                float np2c = getNPCollapseValue(pos, np);

                float mass = mAtt;
                float nrand = Defaults.getRandom(0,100);
                if(nrand>ratioAP){
                    mass = mRep;
                }

                AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c, new PVector(p1.x, p1.y), h, a);
                this.points.add(ap);
                a+= as;
                np++;
            }
        }
        else {
            float minY = min(p1.y, p2.y); float maxY = max(p1.y, p2.y);
            for(float y = minY; y < maxY; y+=xs){
                if(ampAsc) h = map(y, minY, maxY, amplitud.getMinValue(), amplitud.getMaxValue());
                else h = map(y, minY, maxY, amplitud.getMaxValue(), amplitud.getMinValue());
                float yf = y + yVariability.getRandomValue();
                float x = map(y, p1.y, p2.y, p1.x, p2.x) + h*sin(a) + xVariability.getRandomValue();
                PVector pos = new PVector(x, yf);

                float mAtt = getMassAttValue(pos, np);
                float mRep = -getMassRepValue(pos, np);
                float spin = getSpinAngleValue(pos, np);
                float np2c = getNPCollapseValue(pos, np);

                float mass = mAtt;
                float nrand = Defaults.getRandom(0,100);
                if(nrand>ratioAP){
                    mass = mRep;
                }

                AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c, new PVector(p1.x, p1.y), h, a);
                this.points.add(ap);
                a+= as;
                np++;
            }
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
