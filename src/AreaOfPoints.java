import processing.core.PVector;

import static processing.core.PApplet.println;
import static processing.core.PApplet.*;
import static processing.core.PApplet.dist;
import static processing.core.PConstants.PI;


class AreaOfPoints extends SetOfPoints {

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


    public AreaOfPoints(int l, int n, RangFloat x, RangFloat y, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        super(l, n, x, y, mar, mrr, sar, e, c, npcr, rar, xvar, yvar);
        this.p1 = new PVector(x.getMinValue(), y.getMinValue());
        this.p2 = new PVector(x.getMaxValue(), y.getMaxValue());
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0.0f), new RangFloat(0.0f));
    }

    public AreaOfPoints(int l, int n, boolean e, RangFloat x, RangFloat y){
        super(l,n, e, x, y);
        //public SetOfPoints(int l, int n, RangFloat xr, RangFloat yr, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar)
        this.p1 = new PVector(x.getMinValue(), y.getMinValue());
        this.p2 = new PVector(x.getMaxValue(), y.getMaxValue());
    }


    public void setPoints(float minDistance, boolean mirrorX, boolean mirrorY, boolean quadSim, boolean hexaSim, boolean clone, boolean shuffle, boolean randomize, boolean invert){

        float numAtts = numPoints*ratioAP/100.0f;
        println("numATTS: "+numAtts+", numREPS: "+(numPoints-numAtts));
        println("AREA: ["+p1.x+","+p1.y+"] to ["+p2.x+","+p2.y+"].");

        for (int i = 0; i < numPoints; i++) {
            AttractorPoint aptemp;
            PVector pos;
            do {
                float x = Defaults.getRandom(p1.x, p2.x); // + xVariability.getRandomValue();
                float y = Defaults.getRandom(p1.y, p2.y); // + yVariability.getRandomValue();
                pos = new PVector(x, y);
                aptemp = new AttractorPoint(pos);
            } while(!aptemp.awayFrom(minDistance, points));

            float mAtt = massAttRangeOut.getRandomValue();
            //float mAtt = getMappedValue(mapMassAtt, Rang<Float> valueIn, Rang<Float> valueOut, PVector p, PVector r, int np, int npt);
            float mRep = -massRepRangeOut.getRandomValue();
            float mass = (i<=numAtts)?mAtt:mRep;
            float spin = spinAngRangeOut.getRandomValue();
            float np2c = np2CollapseRangeOut.getRandomValue();

            AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
            println(ap);
            points.add(ap.copy());

            float d = dist(pos.x, pos.y, Defaults.sceneWidth/2, Defaults.sceneHeight/2);
            float dx = (Defaults.sceneWidth/2 - pos.x);
            float dy = (Defaults.sceneHeight/2 - pos.y);
            println("DY:"+dy);
            println(Defaults.screenHeight);
            float a = atan(dy/dx) + PI;

            if(mirrorX || mirrorY){
                if((mirrorX && abs(dx)>minDistance) || (mirrorY && abs(dy)>minDistance)){
                    AttractorPoint ap2 = getMirrorPoint(pos, mirrorX, mirrorY, dx, dy, mass, spin, np2c, enabled, collapsable, clone, invert, randomize);
                    points.add(ap2);println("SIMMETRY:"+ap2);
                }
            }
            else if(quadSim){
                if(dx<0 ){ a+=PI;}
                for(int q=0; q<3; q++){
                    a+=HALF_PI;
                    AttractorPoint ap2 = getSimmetryPoint( q,  d,  a,  mass,  spin,  np2c,  enabled,  collapsable,  clone,  invert,  randomize,  shuffle);
                    points.add(ap2);println("QUAD:"+ap2);
                }
            }
            else if(hexaSim){
                if(dx<0 ){ a+=PI;}
                for(int q=0; q<5; q++){
                    a+=PI/3;
                    AttractorPoint ap2 = getSimmetryPoint( q,  d,  a,  mass,  spin,  np2c,  enabled,  collapsable,  clone,  invert,  randomize,  shuffle);
                    points.add(ap2);println("HEXA:"+ap2);
                }
            }
        }
        deletePoints(minDistance);
    }

    public AttractorPoint getMirrorPoint(PVector pos, boolean mirrorX, boolean mirrorY, float dx, float dy, float mass, float spin, float np2c, boolean enabled, boolean collapsable, boolean clone, boolean invert, boolean randomize){
        float newX = (mirrorX)?(Defaults.sceneWidth/2 + dx):pos.x;
        float newY = (mirrorY)?(Defaults.sceneHeight/2 + dy):pos.y;
        PVector newPos = new PVector(newX, newY);
        float massN = massAttRangeOut.getRandomValue();;
        if(clone){
            massN = mass;
        }
        else if(invert){
            massN = -mass;
        }
        else if((randomize)&&(Defaults.getRandom(0,100)>ratioAP)){
            massN = -massRepRangeOut.getRandomValue();
        }
        return new AttractorPoint(newPos, massN, spin, enabled, collapsable, np2c);
    }

    public AttractorPoint getSimmetryPoint(int q, float d, float a, float mass, float spin, float np2c, boolean enabled, boolean collapsable, boolean clone, boolean invert, boolean randomize, boolean shuffle){
        float x = Defaults.sceneWidth/2 + d*cos(a);
        float y = Defaults.sceneHeight/2 + d*sin(a);
        PVector newPos = new PVector(x, y);
        float massN = massAttRangeOut.getRandomValue();
        if(clone){
            massN = mass;
        }
        else if(invert){
            massN = -mass;
        }
        else if((randomize)&&(Defaults.getRandom(0,100)>ratioAP)){
            massN = -massRepRangeOut.getRandomValue();
        }
        else if(shuffle){
            massN=(q%2==0)?-mass:mass;
        }
        return new AttractorPoint(newPos, massN, spin, enabled, collapsable, np2c);
    }

    AreaOfPoints copy(){
        AreaOfPoints sop = new AreaOfPoints(level, numPoints, xRange, yRange, massAttRangeOut, massRepRangeOut, spinAngRangeOut, enabled, collapsable, np2CollapseRangeOut, ratioAP, xVariability, yVariability);
        for(AttractorPoint ap : points){
            AttractorPoint nap = ap.copy();
            sop.points.add(nap);
        }
        return sop;
    }



    void displacePoints(float dx, float dy){
        this.xRange.displaceValues(dx);
        this.yRange.displaceValues(dy);
        for(AttractorPoint ap: points){
            ap.displacePosition(dx, dy);
        }
    }


}
