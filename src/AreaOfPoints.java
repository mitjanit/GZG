import processing.core.PVector;

import javax.naming.ldap.Control;

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


    public void setPoints(ControlWindow controls, float minDistance,
                          boolean mirrorX, boolean mirrorY,
                          boolean quadSim, boolean hexaSim,
                          boolean clone, boolean shuffle, boolean randomize, boolean invert){

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

            PVector ref = controls.cParticles.refColor.copy();
            float distRef = dist(pos.x, pos.y, ref.x,ref.y);
            float mAtt = getMappedValueMassAtt(controls, pos, distRef, i, numPoints);
            float mRep = -getMappedValueMassRep(controls, pos, distRef, i, numPoints);
            float mass = (i<=numAtts)?mAtt:mRep;
            float spin = getMappedValueSpinAngle(controls, pos, distRef, i, numPoints);
            float np2c = getMappedValueNP2Collapse(controls, pos, distRef, i, numPoints);

            AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
            points.add(ap);println("ORIGINAL:"+ap);

            float d = dist(pos.x, pos.y, Defaults.sceneWidth/2, Defaults.sceneHeight/2);
            float dx = (Defaults.sceneWidth/2 - pos.x);
            float dy = (Defaults.sceneHeight/2 - pos.y);
            println("DY:"+dy);
            println(Defaults.screenHeight);
            float a = atan(dy/dx) + PI;

            if(mirrorX || mirrorY){
                if((mirrorX && abs(dx)>minDistance) || (mirrorY && abs(dy)>minDistance)){
                    AttractorPoint ap2 = getMirrorPoint(pos, mirrorX, mirrorY, dx, dy, mass, spin, np2c, enabled, collapsable, clone, invert, randomize);
                    points.add(ap2);println("SYMMETRY:"+ap2);
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

    public float getMappedValueMassAtt(ControlWindow c, PVector pos, float distRef, int i, int numPoints){
        int m = c.cCommons.mapMassAtt;
        RangFloat rIn = c.cCommons.mapInMassAtt;
        RangFloat rOut = c.cCommons.massAtt;
        return getMappedValue(m, rIn, rOut, pos, distRef, i, numPoints);
    }

    public float getMappedValueMassRep(ControlWindow c, PVector pos, float distRef, int i, int numPoints){
        int m = c.cCommons.mapMassRep;
        RangFloat rIn = c.cCommons.mapInMassRep;
        RangFloat rOut = c.cCommons.massRep;
        return getMappedValue(m, rIn, rOut, pos, distRef, i, numPoints);
    }

    public float getMappedValueSpinAngle(ControlWindow c, PVector pos, float distRef, int i, int numPoints){
        int m = c.cCommons.mapSpinAngle;
        RangFloat rIn = c.cCommons.mapInSpinAngle;
        RangFloat rOut = c.cCommons.spinAng;
        return getMappedValue(m, rIn, rOut, pos, distRef, i, numPoints);
    }

    public float getMappedValueNP2Collapse(ControlWindow c, PVector pos, float distRef, int i, int numPoints){
        int m = c.cCommons.mapNPCollapse;
        RangFloat rIn = c.cCommons.mapInNPCollapse;
        RangFloat rOut = c.cCommons.np2Col;
        return getMappedValue(m, rIn, rOut, pos, distRef, i, numPoints);
    }

    public float getMappedValue(int mapValue, RangFloat valueIn, RangFloat valueOut, PVector pos, float distRef, int i, int numPoints){
        float s=0;
        float valueInMin = valueIn.getMinValue();
        float valueInMax = valueIn.getMaxValue();
        float valueOutMin = valueOut.getMinValue();
        float valueOutMax = valueOut.getMaxValue();

        //"NONE", "RANDOM", "POS X","POS X INV", "POS Y", "POS Y INV", "DISTANCE REF", "DISTANCE REF INV", "NUM POINT", "NUM POINT INV"
        switch(mapValue){
            case 0: s = valueOut.getMaxValue(); break; //NONE
            case 1: s = Defaults.getRandom(valueOutMin, valueOutMax); break; // RANDOM
            case 2: s = map(pos.x, valueInMin, valueInMax, valueOutMin, valueOutMax); break;        // POS X
            case 3: s = map(pos.x, valueInMin, valueInMax, valueOutMax, valueOutMin); break;    // POS X INV
            case 4: s = map(pos.y, valueInMin, valueInMax, valueOutMin, valueOutMax); break;        // POS Y
            case 5: s = map(pos.y, valueInMin, valueInMax, valueOutMax, valueOutMin); break;    // POS Y INV
            case 6: s = map(distRef, valueInMin, valueInMax, valueOutMin, valueOutMax); break;    // DIST REF
            case 7: s = map(distRef, valueInMin, valueInMax, valueOutMax, valueOutMin); break;    // DIST REF INV
            case 8: s = map(i, 0, numPoints, valueOutMin, valueOutMax); break;    // NUM POINT
            case 9: s = map(i, 0, numPoints, valueOutMax, valueOutMin); break;    // NUM POINT INV
        }
        return s;
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
