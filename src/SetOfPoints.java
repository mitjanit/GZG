import processing.core.PApplet;
import processing.core.PVector;
import static processing.core.PApplet.dist;
import static processing.core.PApplet.map;
import static processing.core.PApplet.println;

import java.util.ArrayList;


class SetOfPoints {

    ArrayList<AttractorPoint> points;

    // Capa del conjunt de punts
    int level;

    // Número de punts del conjunt
    int numPoints;

    // Valors de mappeig dels paràmetres de massa, rotació i num. particules per col·lapsar.
    // mapPointOptions ("NONE","RANDOM", "POSITION X", "POSITION X INV", "POSITION Y", "POSITION Y INV", "DISTANCE REF", "DISTANCE REF INV", "NUM POINT", "NUM POINT INV");
    int mapMassAtt, mapMassRep, mapSpinAng, mapNPCollapse;

    // Rangs de valors d'entradfa i sortida dels paràmetres dels punts del conjunt.
    RangFloat   massAttRangeOut,    massAttRangeIn,
                massRepRangeOut,    massRepRangeIn,
                spinAngRangeIn,     spinAngRangeOut,
                np2CollapseRangeIn, np2CollapseRangeOut;

    boolean enabled, collapsable;

    // Proporció de la relació entre punts attractors i repulsors.
    float ratioAP;

    // Punt de referència del conjunt (sobre el qual rotar o oscil·lar.
    PVector ref;

    // Rang de variablititat de les coordenadoes X i Y dels punts del conjunt
    RangFloat xVariability, yVariability;

    // Rang de valors per a les coordenades X i Y dels punts del conjunt
    RangFloat xRange, yRange;


    public SetOfPoints(){
        points = new ArrayList<AttractorPoint>();
        ref = new PVector();
        setDefaultParams(0, 0);
    }

    public SetOfPoints(int l, int n, boolean e){
        points = new ArrayList<AttractorPoint>();
        setDefaultParams(l, n);
        this.enabled = e;
    }

    public SetOfPoints(int l, int n, boolean e, RangFloat xvar, RangFloat yvar){
        points = new ArrayList<AttractorPoint>();
        setDefaultParams(l, n);
        this.enabled = e;
        this.xVariability = xvar;
        this.yVariability = yvar;
    }

    public SetOfPoints(int l, int n, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, RangFloat xvar, RangFloat yvar){
        points = new ArrayList<AttractorPoint>();
        setDefaultParams(l, n);
        this.massAttRangeOut = mar;
        this.massRepRangeOut = mrr;
        this.spinAngRangeOut = sar;
        this.enabled = e;
        this.xVariability = xvar;
        this.yVariability = yvar;
    }

    public SetOfPoints(int l, int n, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        points = new ArrayList<AttractorPoint>();
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, xvar, yvar);
    }

    public SetOfPoints(int l, int n, RangFloat xr, RangFloat yr, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        points = new ArrayList<AttractorPoint>();
        setParams(l, n, xr, yr, mar, mrr, sar, e, c, npcr, rar, xvar, yvar);
    }

    /* Valors per defecte d'un Conjunt de Punts */
    public void setDefaultParams(int l, int n){
        //println("SET DEFAULT PARAMS FOR POINT SET IN LAYER: "+l);
        this.level = l;
        this.numPoints = n;
        this.enabled = true;
        this.collapsable = false;

        this.ratioAP = 100.0f; // Tots són attractors.
        this.ref = new PVector(Defaults.screenWidth/2, Defaults.screenHeight/2);

        this.xRange = new RangFloat(Defaults.MIN_X, Defaults.MAX_X);
        this.yRange = new RangFloat(Defaults.MIN_Y, Defaults.MAX_Y);

        this.xVariability = new RangFloat(0);
        this.yVariability = new RangFloat(0);

        this.mapMassAtt = 0;
        this.massAttRangeIn = new RangFloat(Defaults.MIN_MASS_ATT, Defaults.MAX_MASS_ATT);
        this.massAttRangeOut = new RangFloat(Defaults.MIN_MASS_ATT, Defaults.MAX_MASS_ATT);

        this.mapMassRep = 0;
        this.massRepRangeIn = new RangFloat(Defaults.MIN_MASS_REP, Defaults.MAX_MASS_REP);
        this.massRepRangeOut = new RangFloat(Defaults.MIN_MASS_REP, Defaults.MAX_MASS_REP);

        this.mapSpinAng = 0;
        this.spinAngRangeIn = new RangFloat(Defaults.MIN_SPIN, Defaults.MAX_SPIN);
        this.spinAngRangeOut = new RangFloat(0.0f); //new RangFloat(Defaults.MIN_SPIN, Defaults.MAX_SPIN);

        this.mapNPCollapse = 0;
        this.np2CollapseRangeIn = new RangFloat(Defaults.MIN_COLLAPSE, Defaults.MAX_COLLAPSE);
        this.np2CollapseRangeOut = new RangFloat(Defaults.MIN_COLLAPSE, Defaults.MAX_COLLAPSE);

    }

    void setParamsFromStyle(SetOfPointsStyle style){
        println("SET PARAMS FROM SET OF POINTS STYLE");

        this.enabled = true;
        this.collapsable = style.collapseable;

        this.ratioAP = style.ratioAttRep;
        this.ref = style.ref;

        this.xRange = style.xRange;
        this.yRange = style.yRange;

        this.xVariability = style.xVar;
        this.yVariability = style.yVar;

        this.mapMassAtt = style.mapMassAtt;
        this.massAttRangeIn = style.mapInMassAtt.copy();
        this.massAttRangeOut = style.mapOutMassAtt.copy();

        this.mapMassRep = style.mapMassRep;
        this.massRepRangeIn = style.mapInMassRep.copy();
        this.massRepRangeOut = style.mapOutMassRep.copy();

        this.mapSpinAng = style.mapSpinAngle;
        this.spinAngRangeIn = style.mapInSpinAngle.copy();
        this.spinAngRangeOut = style.mapOutSpinAngle.copy();

        this.mapNPCollapse = style.mapNPCollapse;
        this.np2CollapseRangeIn = style.mapInNPCollapse.copy();
        this.np2CollapseRangeOut = style.mapOutNPCollapse.copy();
    }

    public void setParams(int l, int n, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        this.level = l; this.numPoints = n;
        this.massAttRangeOut = mar; this.massRepRangeOut = mrr;
        this.spinAngRangeOut = sar;
        this.enabled = e; this.collapsable = c;
        this.np2CollapseRangeOut = npcr;
        this.ratioAP = rar;
        this.xVariability = xvar.copy(); this.yVariability =yvar.copy();
    }

    public void setParams(int l, int n, RangFloat xr, RangFloat yr, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xvar, RangFloat yvar){
        this.level = l; this.numPoints = n;
        this.massAttRangeOut = mar; this.massRepRangeOut = mrr;
        this.spinAngRangeOut = sar;
        this.enabled = e; this.collapsable = c;
        this.np2CollapseRangeOut = npcr;
        this.ratioAP = rar;
        this.xVariability = xvar.copy(); this.yVariability =yvar.copy();
        this.xRange = xr.copy(); this.yRange = yr.copy();
    }

    public void setVariability(RangFloat xv, RangFloat yv){
        this.xVariability = xv.copy(); this.yVariability = yv.copy();
    }

    public void setXVariability(float minV, float maxV){
        this.xVariability = new RangFloat(minV, maxV);
    }

    public void setYVariability(float minV, float maxV){
        this.yVariability = new RangFloat(minV, maxV);
    }

    public void setMassParams(int mMA, RangFloat mInMA, RangFloat mOutMA, int mMR, RangFloat mInMR, RangFloat mOutMR, float r){
        this.mapMassAtt=mMA; this.massAttRangeIn = mInMA.copy(); this.massAttRangeOut = mOutMA.copy();
        this.mapMassRep=mMR; this.massRepRangeIn = mInMR.copy(); this.massRepRangeOut = mOutMR.copy();
        this.ratioAP = r;
    }

    public void setCollapseParams(int mC, RangFloat mInC, RangFloat mOutC, boolean c){
        this.mapNPCollapse = mC; this.np2CollapseRangeIn = mInC.copy(); this.np2CollapseRangeOut=mOutC.copy(); this.collapsable = c;
    }

    public void setSpinAngleParams(int mSA, RangFloat mInSA, RangFloat mOutSA){
        this.mapSpinAng = mSA; this.spinAngRangeIn = mInSA.copy(); this.spinAngRangeOut = mOutSA.copy();
    }

    public void setRefParams(PVector p){
        this.ref = p.copy();
    }


    public void setEnabled(boolean b){
        for(AttractorPoint ap: points){
            ap.setEnabled(b);
        }
    }

    public void setCollapsable(boolean b){
        for(AttractorPoint ap: points){
            ap.setCollapsable(b);
        }
    }

    public void setOscillate(boolean[] b){
        for(AttractorPoint ap: points){
            ap.setOscillate(b);
        }
    }

    public boolean areRemoveable(){
        boolean b = false;
        for(int i=0; i<points.size(); i++){
            AttractorPoint ap = points.get(i);
            if(ap.isRemoveable()){
                b = true; break;
            }
        }
        return b;
    }

    public float getMappedValue(int mapValue, RangFloat valueIn, RangFloat valueOut, PVector p, int np){

        float s=0.0f;

        float valueInMin = valueIn.getMinValue();
        float valueInMax = valueIn.getMaxValue();
        float valueOutMin = valueOut.getMinValue();
        float valueOutMax = valueOut.getMaxValue();

        switch(mapValue){
            case 0: s = valueOut.getMaxValue(); break; //NONE
            case 1: s = valueOut.getRandomValue(); break;        // RANDOM
            case 2: s = map(p.x, valueInMin, valueInMax, valueOutMin, valueOutMax); break;        // POS X
            case 3: s = map(p.x, valueInMin, valueInMax, valueOutMax, valueOutMin); break;    // POS X INV
            case 4: s = map(p.y, valueInMin, valueInMax, valueOutMin, valueOutMax); break;    // POS Y
            case 5: s = map(p.y, valueInMin, valueInMax, valueOutMax, valueOutMin); break;   // POS Y INV
            case 6: s = map(dist(p.x, p.y, ref.x, ref.y), valueInMin, valueInMax, valueOutMin, valueOutMax); break;   // DIST REF
            case 7: s = map(dist(p.x, p.y, ref.x, ref.y), valueInMin, valueInMax, valueOutMax, valueOutMin); break;  // DIST REF INV
            case 8: s = map(np, valueInMin, valueInMax, valueOutMin, valueOutMax); break;  // NUM POINT
            case 9: s = map(np, valueInMin, valueInMax, valueOutMax, valueOutMin); break;     // NUM POINT INV
        }
        return s;
    }

    public float getMassAttValue(PVector pos, int np){
        return getMappedValue(mapMassAtt, massAttRangeIn, massAttRangeOut, pos, np);
    }

    public float getMassRepValue(PVector pos, int np){
        return getMappedValue(mapMassRep, massRepRangeIn, massRepRangeOut, pos, np);
    }

    public float getSpinAngleValue(PVector pos, int np){
        return getMappedValue(mapSpinAng, spinAngRangeIn, spinAngRangeOut, pos, np);
    }

    public float getNPCollapseValue(PVector pos, int np){
        return getMappedValue(mapNPCollapse, np2CollapseRangeIn, np2CollapseRangeOut, pos, np);
    }



    public void update(){
        for(int i=0; i<points.size(); i++){
            AttractorPoint ap = points.get(i);
            ap.update();
            if(ap.isCollapsed()){
                ap.setGravity(0.0f);
            }
        }
    }


    public void draw(PApplet p){
        for(int i=0; i<points.size(); i++){
            AttractorPoint ap = points.get(i);
            ap.drawAttractorPoint(p);
        }
    }

    SetOfPoints copy(){
        SetOfPoints sop = new SetOfPoints();
        for(AttractorPoint ap : points){
            AttractorPoint nap = ap.copy();
            sop.points.add(nap);
        }
        sop.level = this.level; sop.numPoints = this.numPoints;
        sop.massAttRangeOut = this.massAttRangeOut.copy(); sop.massRepRangeOut = this.massRepRangeOut.copy();
        sop.spinAngRangeOut = this.spinAngRangeOut.copy();
        sop.enabled = this.enabled; sop.collapsable = this.collapsable;
        sop.np2CollapseRangeOut = this.np2CollapseRangeOut.copy(); sop.ratioAP = this.ratioAP;
        sop.xVariability = this.xVariability.copy(); sop.yVariability =this.yVariability.copy();
        sop.xRange = this.xRange.copy(); sop.yRange = this.yRange.copy();
        return sop;
    }


    // Delete Points

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


    //
    void printSet(){

    }


    // Oscillation Data
    /*

    public void setOscillators(){
        for(OscillatorData oscData : oscGroup.getOscillatorData()){
            int mode = oscData.oscMode, dir = oscData.oscDirection;
            boolean enabled = oscData.oscEnabled;
            RangFloat omin = oscData.oscMin.copy();
            RangFloat omax = oscData.oscMax.copy();
            RangFloat step = oscData.oscStep.copy();
            RangFloat nframes = oscData.oscNumFrames.copy();
            RangFloat ntimes = oscData.oscNumTimes.copy();
            RangFloat delay = oscData.oscDelay.copy();
            setOscillation(mode, omin, omax, step, dir, enabled, nframes, ntimes, delay);
        }
    }


    public void setOscillation(int oscMode, RangFloat oscMin, RangFloat oscMax, RangFloat oscStep, int oscDirection, boolean oscEnabled, RangFloat oscNumFrames, RangFloat oscNumTimes, RangFloat oscDelay){
        println(oscNumFrames);
        if(oscMode!=0){
            //int dir = (oscDirection)?1:-1;
            switch (oscMode){
                case 1: // Oscillate POS X
                    setOscPosXR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 2: // Oscillate POS Y
                    setOscPosYR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 3: // Oscillate GRAVITY
                    setOscGravityR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 4: // Oscillate SPIN ANGLE
                    setOscSpinAngleR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 5: // Oscillate RADIUS
                    setOscRadiusR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 6: // Oscillate ANGLE
                    setOscAngleR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
            }
        }
    }
    */

    // Oscillation
    /*

    public void setOscillation(){
        println(oscNumFrames);
        if(oscMode!=0){
            //int dir = (oscDirection)?1:-1;
            switch (oscMode){
                case 1: // Oscillate POS X
                    setOscPosXR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 2: // Oscillate POS Y
                    setOscPosYR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 3: // Oscillate GRAVITY
                    setOscGravityR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 4: // Oscillate SPIN ANGLE
                    setOscSpinAngleR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 5: // Oscillate RADIUS
                    setOscRadiusR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
                case 6: // Oscillate ANGLE
                    setOscAngleR(oscMin, oscMax, oscStep, oscDirection, oscEnabled, oscNumFrames, oscNumTimes, oscDelay); break;
            }
        }
    }



    public void setOscPosXR(RangFloat minV, RangFloat maxV, RangFloat step, int d, boolean b, RangFloat f, RangFloat t, RangFloat dt){
        for(AttractorPoint ap : points){
            float minValue = minV.getRandomValue();
            float maxValue = maxV.getRandomValue();
            float stepValue = step.getRandomValue();
            float delayValue = dt.getRandomValue();
            float framesValue = f.getRandomValue();
            float timesValue = t.getRandomValue();
            int dirValue = d;
            if(d==0) dirValue = (int)pow(-1, (int)random(1,10));
            ap.setOscPosX(ap.getX() - minValue, ap.getX() + maxValue, ap.getX(), stepValue, dirValue, b, framesValue, timesValue, delayValue);
        }
    }

    public void setOscPosYR(RangFloat minV, RangFloat maxV, RangFloat step, int d, boolean b, RangFloat f, RangFloat t, RangFloat dt){
        for(AttractorPoint ap : points){
            float minValue = minV.getRandomValue();
            float maxValue = maxV.getRandomValue();
            float stepValue = step.getRandomValue();
            float delayValue = dt.getRandomValue();
            float framesValue = f.getRandomValue();
            float timesValue = t.getRandomValue();
            int dirValue = d;
            if(d==0) dirValue = (int)pow(-1, (int)random(1,10));
            ap.setOscPosY(ap.getY() - minValue, ap.getY() + maxValue, ap.getY(), stepValue, dirValue, b, framesValue, timesValue, delayValue);
        }
    }



    public void setOscGravityR(RangFloat minV, RangFloat maxV, RangFloat step, int d, boolean b, RangFloat f, RangFloat t, RangFloat dt){
        for(AttractorPoint ap : points){
            float minValue = minV.getRandomValue();
            float maxValue = maxV.getRandomValue();
            float stepValue = step.getRandomValue();
            float delayValue = dt.getRandomValue();
            float framesValue = f.getRandomValue();
            float timesValue = t.getRandomValue();
            int dirValue = d;
            if(d==0) dirValue = (int)pow(-1, (int)random(1,10));
            ap.setOscGravity(ap.getGravity() - minValue, ap.getGravity() + maxValue, ap.getGravity(), stepValue, dirValue, b, framesValue, timesValue, delayValue);
        }
    }


    public void setOscSpinAngleR(RangFloat minV, RangFloat maxV, RangFloat step, int d, boolean b, RangFloat f, RangFloat t, RangFloat dt){
        for(AttractorPoint ap : points){
            float minValue = minV.getRandomValue();
            float maxValue = maxV.getRandomValue();
            float stepValue = step.getRandomValue();
            float delayValue = dt.getRandomValue();
            float framesValue = f.getRandomValue();
            float timesValue = t.getRandomValue();
            int dirValue = d;
            if(d==0) dirValue = (int)pow(-1, (int)random(1,10));
            ap.setOscSpinAngle(ap.getSpinAngle() - minValue, ap.getSpinAngle() + maxValue, ap.getSpinAngle(), stepValue, dirValue, b, framesValue, timesValue, delayValue);
        }
    }


    public void setOscRadiusR(RangFloat minV, RangFloat maxV, RangFloat step, int d, boolean b, RangFloat f, RangFloat t, RangFloat dt){
        for(AttractorPoint ap : points){
            float minValue = minV.getRandomValue();
            float maxValue = maxV.getRandomValue();
            float stepValue = step.getRandomValue();
            float delayValue = dt.getRandomValue();
            float framesValue = f.getRandomValue();
            float timesValue = t.getRandomValue();
            int dirValue = d;
            if(d==0) dirValue = (int)pow(-1, (int)random(1,10));
            ap.setOscRadius(ap.getRadius() - minValue, ap.getRadius() + maxValue, ap.getRadius(), stepValue, dirValue, b, framesValue, timesValue, delayValue);
        }
    }


    public void setOscAngleR(RangFloat minV, RangFloat maxV, RangFloat step, int d, boolean b, RangFloat f, RangFloat t, RangFloat dt){
        for(AttractorPoint ap : points){
            float minValue = minV.getRandomValue();
            float maxValue = maxV.getRandomValue();
            float stepValue = step.getRandomValue();
            float framesValue = f.getRandomValue();
            float delayValue = dt.getRandomValue();
            float timesValue = t.getRandomValue();
            int dirValue = d;
            if(d==0) dirValue = (int)pow(-1, (int)random(1,10));
            ap.setOscAngle(ap.getAngle() - minValue, ap.getAngle() + maxValue, ap.getAngle(), stepValue, dirValue, b, framesValue, timesValue, delayValue);
        }
    }

    */





}
