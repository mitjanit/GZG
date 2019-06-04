
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.println;
import static processing.core.PConstants.ADD;

public class SourceOfParticles {

    // Color & Size Params
    int mRed, mGreen, mBlue, mOpac, mWidth, mHeight, colorMod;
    RangFloat step, delay, fInSize, rIn, rOut, gIn, gOut, bIn, bOut, oIn, oOut, wIn, wOut,hIn, hOut;
    boolean greyScale, equalWH;
    PVector refColor;

    //nou param
    float startFrame = 0;
    boolean createdParticles = false;

    // Params for FLOW
    boolean enabled, fEnabled, fUpdate, fRandom;
    float framesFlowing;
    int timesFlowing, numTimesFlowed;
    RangFloat xVar, yVar;
    //float xRand, yRand;
    RangFloat xRand, yRand;
    RangFloat fTimes, fFrames;


    // Params Variability between flows
    int vRed, vGreen, vBlue, vOpac, vRefCol, vWidth, vHeight;
    boolean color2grey;
    float col2greyChance;
    RangFloat rInVar, rOutVar, gInVar, gOutVar, bInVar, bOutVar, oInVar, oOutVar, wInVar, wOutVar, hInVar, hOutVar;

    public SourceOfParticles(){
        setDefaults();
        enabled = true;

        fEnabled = true; fUpdate= false; fRandom = false;
        framesFlowing = 300; timesFlowing = 1; numTimesFlowed = 0;

        xRand=new RangFloat(0); yRand=new RangFloat(0);
        xVar=new RangFloat(0); yVar=new RangFloat(0);
        fFrames = new RangFloat(0); fTimes = new RangFloat(-1);
    }

    void setDefaults(){

        this.step = new RangFloat(1.0f); // new RangFloat(Defaults.MIN_STEP, Defaults.MAX_STEP);
        this.delay = new RangFloat(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY);
        this.fInSize = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);

        this.greyScale=false;
        this.refColor = new PVector(Defaults.screenWidth/2, Defaults.screenHeight/2);

        this.mRed = 0;
        this.rIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        this.rOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        this.mGreen = 0;
        this.gIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        this.gOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        this.mBlue = 0;
        this.bIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        this.bOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        this.mOpac = 0;
        this.oIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        this.oOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        this.equalWH = false;

        this.mWidth = 0;
        this.wIn = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);
        this.wOut = new RangFloat(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT);

        this.mHeight = 0;
        this.hIn = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);
        this.hOut = new RangFloat(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT);

    }

    void setParamsFromStyle(AttractedParticleStyle style){
        // Basic Params
        this.setStep(style.pStep.copy());           //Range Particle STEP
        this.setDelay(style.pDelay.copy());         //Range Particle DELAY
        this.setVariability(style.xVar.copy(), style.yVar.copy());     //Ranges Variability X & Y Particle Position
        this.setRandXY(style.xRand, style.yRand);  //Range Particle X & Y Randomness from Original Position

        // Color & Size Params
        this.getColorParamsFromStyle(style); //Color Params from Style
        this.getSizeParamsFromStyle(style);  //Size Params from Style
        this.setFadeInSize(style.fInSize);   //Range Particle FadeInSize frames

        this.setColorRef(style.refCol.copy());      //Ref from Style
    }

    void setFlowParamsFromStyle(AttractedParticleStyle style){
        this.fFrames = style.fFrames.copy();
        this.fTimes = style.fTimes.copy();
        this.timesFlowing = (int)fTimes.getRandomValue();
        this.fEnabled = style.fEnabled;
        this.fUpdate = style.fUpdate;
        this.fRandom = style.fRandom;
    }


    void setVarParamsFromStyle(AttractedParticleStyle style){
        setRedVarFlowParams(style.vRed, style.vRedIn, style.vRedOut);
        setGreenVarFlowParams(style.vGreen, style.vGreenIn, style.vGreenOut);
        setBlueVarFlowParams(style.vBlue, style.vBlueIn, style.vBlueOut);
        setOpacVarFlowParams(style.vOpac, style.vOpacIn, style.vOpacOut);
        setWidthVarFlowParams(style.vWidth, style.vWidthIn, style.vWidthOut);
        setHeightVarFlowParams(style.vHeight, style.vHeightIn, style.vHeightOut);
    }

    public void setEnabled(boolean b){
        this.enabled = b;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setFlowEnabled(boolean b){
        this.fEnabled = b;
    }

    public boolean isFlowEnabled(){
        //println("FLOW ENABLED:"+fEnabled);
        return fEnabled;
    }

    public void setFlowUpdate(boolean b){
        this.fUpdate = b;
    }

    public void setColorMod(String c){
        this.colorMod = ADD;
        /*
        switch(c){
            case "ADD": this.colorMod = ADD; break;
            case "SUBTRACT": this.colorMod = SUBTRACT; break;
            case "DARKEST": this.colorMod = DARKEST; break;
            case "LIGHTEST": this.colorMod = LIGHTEST; break;
            case "DIFFERENCE": this.colorMod = DIFFERENCE; break;
            case "EXCLUSION": this.colorMod = EXCLUSION; break;
            case "MULTIPLY": this.colorMod = MULTIPLY; break;
            case "SCREEN": this.colorMod = SCREEN; break;
            case "REPLACE": this.colorMod = REPLACE; break;
            default: this.colorMod = BLEND; break;
        }
        */
    }

    void setStartFrame(float f){
        this.startFrame = f;
    }

    public boolean isUpdate(){
        return fUpdate;
    }

    public void setFlowRandom(boolean b){
        this.fRandom = b;
    }

    public boolean isFlowRandom(){
        return fRandom;
    }

    public void setFlowFrames(float f){
        this.framesFlowing = f;
    }

    public void setFlowFramesNow(float framesNow){
        this.framesFlowing = framesNow + (int)fFrames.getRandomValue();
    }

    public void setFlowTimes(float f){
        this.timesFlowing = (int)f;
    }

    public void setFlowTimes(){
        this.timesFlowing = (int)fTimes.getRandomValue();
    }

    public void updateNumFlowTimes(){
        this.numTimesFlowed++;
        println("INCR. FLOWED TIMES: "+numTimesFlowed);
    }

    public void setNextFlowTime(float framesNow){
        this.framesFlowing = framesNow + fFrames.getRandomValue();
        println("NEXT TIME TO FLOW: "+framesFlowing);
    }

    public void setRangFlowFrames(RangFloat r, float framesNow){
        this.fFrames = r.copy();
        this.framesFlowing = framesNow + this.fFrames.getRandomValue();
    }

    public void setRangFlowTimes(RangFloat r){
        this.fTimes = r.copy();
        this.timesFlowing = (int)this.fTimes.getRandomValue();
    }


    public void setStep(RangFloat s){
        this.step = s.copy();
    }

    public void setDelay(RangFloat d){
        this.delay = d.copy();
    }

    public void setFadeInSize(RangFloat f){
        this.fInSize = f.copy();
    }

    public void setVariability(RangFloat xv, RangFloat yv){
        this.xVar = xv.copy(); this.yVar = yv.copy();
    }

    public void setRandX(RangFloat xr){
        this.xRand = xr.copy();
    }

    public void setRandY(RangFloat yr){
        this.yRand = yr.copy();
    }

    public void setRandXY(RangFloat xr, RangFloat yr){
        this.xRand = xr.copy(); this.yRand = yr.copy();
    }

    public void getColorParamsFromStyle(AttractedParticleStyle style){

        this.greyScale  = style.grey;

        this.mRed       = style.mRed;       this.rIn = style.rIn;       this.rOut = style.rOut;
        this.mGreen     = style.mGreen;     this.gIn = style.gIn;       this.gOut = style.gOut;
        this.mBlue      = style.mBlue;      this.bIn = style.bIn;       this.bOut = style.bOut;
        this.mOpac      = style.mOpac;      this.oIn = style.oIn;       this.oOut = style.oOut;
    }


    public void setColorParams(int mG, RangFloat greyIn, RangFloat greyOut, int mO, RangFloat opacIn, RangFloat opacOut){
        this.greyScale = true;
        this.mRed = mG; this.rIn = greyIn.copy(); this.rOut = greyOut.copy();
        this.mGreen = mG;this.gIn = greyIn.copy(); this.gOut = greyOut.copy();
        this.mBlue = mG;this.bIn = greyIn.copy(); this.bOut = greyOut.copy();
        this.mOpac = mO; this.oIn = opacIn.copy(); this.oOut = opacOut.copy();
    }

    public void setColorParams(int mR, RangFloat redIn, RangFloat redOut, int mG, RangFloat greenIn, RangFloat greenOut, int mB, RangFloat blueIn, RangFloat blueOut, int mO, RangFloat opacIn, RangFloat opacOut){
        this.greyScale = false;
        this.mRed = mR; this.rIn = redIn.copy(); this.rOut = redOut.copy();
        this.mGreen = mG; this.gIn = greenIn.copy(); this.gOut = greenOut.copy();
        this.mBlue = mB; this.bIn = blueIn.copy(); this.bOut = blueOut.copy();
        this.mOpac = mO; this.oIn = opacIn.copy(); this.oOut = opacOut.copy();
    }

    public void setColorRef(PVector r){
        this.refColor = new PVector(r.x, r.y);
    }

    public void getSizeParamsFromStyle(AttractedParticleStyle style){

        this.equalWH = style.equalWH;

        this.mWidth  = style.mWidth;    this.wIn = style.wIn;   this.wOut = style.wOut;
        this.mHeight = style.mHeight;   this.hIn = style.hIn;   this.hOut = style.hOut;
    }

    public void setSizeParams(int mWidth, RangFloat widthIn, RangFloat widthOut, int mHeight, RangFloat heightIn, RangFloat heightOut){
        this.mWidth = mWidth; this.wIn = widthIn.copy(); this.wOut = widthOut.copy();
        this.mHeight = mHeight; this.hIn = heightIn.copy(); this.hOut = heightOut.copy();
    }

    public void setSizeParams(int mWidth, RangFloat widthIn, RangFloat widthOut){
        this.mWidth = mWidth; this.wIn = widthIn.copy(); this.wOut = widthOut.copy();
        this.mHeight = mWidth; this.hIn = widthIn.copy(); this.hOut = widthOut.copy();
    }

    // falta metode per passar-li al SoP tots els paràmetres de VAR FLOW

    public void setRedVarFlowParams(int v, RangFloat v0, RangFloat v1){
        this.vRed = v; this.rInVar = v0.copy(); this.rOutVar = v1.copy();
        //println("RED IN VAR:"+rInVar+", RED OUT VAR:"+rOutVar);
    }

    public void setGreenVarFlowParams(int v, RangFloat v0, RangFloat v1){
        this.vGreen = v; this.gInVar = v0.copy(); this.gOutVar = v1.copy();
    }

    public void setBlueVarFlowParams(int v, RangFloat v0, RangFloat v1){
        this.vBlue = v; this.bInVar = v0.copy(); this.bOutVar = v1.copy();
    }

    public void setOpacVarFlowParams(int v, RangFloat v0, RangFloat v1){
        this.vOpac = v; this.oInVar = v0.copy(); this.oOutVar = v1.copy();
    }

    public void setWidthVarFlowParams(int v, RangFloat v0, RangFloat v1){
        this.vWidth = v; this.wInVar = v0.copy(); this.wOutVar = v1.copy();
    }

    public void setHeightVarFlowParams(int v, RangFloat v0, RangFloat v1){
        this.vHeight = v; this.hInVar = v0.copy(); this.hOutVar = v1.copy();
    }

    boolean areTimesToFlow(){
        //println("TIMES FLOWING: "+timesFlowing+", TIMES FLOWED:"+numTimesFlowed);
        return fEnabled && ((timesFlowing==-1) || (numTimesFlowed<timesFlowing));
    }

    boolean isFrameToFlow(float framesNow){
        //println("TIME TO NEXT FLOW: "+framesFlowing+", FRAMES NOW: "+framesNow);
        return fEnabled && (framesNow>=framesFlowing);
    }


    public void updateVarParams(){
        updateRedVarParams();/*
        updateGreenVarParams();
        updateBlueVarParams();
        updateOpacVarParams();
        updateWidthVarParams();
        updateHeightVarParams();*/
    }

    public void updateRedVarParams(){
        RangFloat rInValid;
        String txt="";
        switch(vRed){
            case 0 : // NONE
                txt += "NO RED MAP VARIATION";
                break;

            case 1 : // NEXT
                mRed++; if(mRed>=Defaults.mapOptions.size()) mRed=0;
                txt += "RED MAP TO NEXT : "+mRed+ " - "+Defaults.mapOptions.get(mRed);
                break;

            case 2 : // RANDOM
                mRed = (int)Defaults.getRandom(0, Defaults.mapOptions.size());
                txt += "RED MAP TO RANDOM : "+mRed+ " - "+Defaults.mapOptions.get(mRed);
                break;

            case 3 : // INVERT
                if(mRed!=0){ mRed = (mRed%2==0)? (mRed-1) : (mRed+1);}
                txt += "RED MAP TO INVERT : "+mRed+ " - "+Defaults.mapOptions.get(mRed);
                break;

            default: txt += "RED VARIATION UNKNOWN ????"; break;
        }

        println(txt);

        // En funcio de mRed s'ha d'adaptar rInValid
        if(mRed>=5 && mRed<=8){ // RED MAPPED TO ORIENTATION X o Y
            rInValid = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
        }
        else if(mRed>=9 && mRed<=10){ // RED MAPPED TO POS X o POS X INV
            rInValid = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
        }
        else if(mRed>=11 && mRed<=12){ // RED MAPPED TO POS Y o POS Y INV
            rInValid = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
        }
        else {
            rInValid = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        }

        RangFloat rInf = rIn.copy(), rOutf = rOut.copy();
        rInf.applyVariation(rInVar.copy(), rInValid);
        rOutf.applyVariation(rOutVar.copy(), new RangFloat(0.0f, 255.0f));

        rIn = rInf.copy();
        rOut = rOutf.copy();

        println("VARIATED RED IN: "+rIn+", OUT: "+rOut);
    }

    public void updateGreenVarParams(){
        RangFloat gInValid;
        switch(vGreen){
            case 0 : // NONE
                println("NO GREEN MAP VARIATION"); break;

            case 1 : // NEXT
                mGreen++; if(mGreen>=Defaults.mapOptions.size()) mGreen=0;
                println("GREEN MAP TO NEXT : "+mGreen+ " - "+Defaults.mapOptions.get(mGreen)); break;

            case 2 : // RANDOM
                mGreen = (int)Defaults.getRandom(0, Defaults.mapOptions.size());
                println("GREEN MAP TO RANDOM : "+mGreen+ " - "+Defaults.mapOptions.get(mGreen)); break;

            case 3 : // INVERT
                if(mGreen!=0){ mGreen = (mGreen%2==0)? (mGreen-1) : (mGreen+1);}
                println("GREEN MAP TO INVERT : "+mGreen+ " - "+Defaults.mapOptions.get(mGreen)); break;

            default: println("GREEN VARIATION UNKNOWN ????"); break;
        }

        // En funcio de mRed s'ha d'adaptar rInValid
        if(mGreen>=5 && mGreen<=8){ // RED MAPPED TO ORIENTATION X o Y
            gInValid = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
        }
        else if(mGreen>=9 && mGreen<=10){ // RED MAPPED TO POS X o POS X INV
            gInValid = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
        }
        else if(mGreen>=11 && mGreen<=12){ // RED MAPPED TO POS Y o POS Y INV
            gInValid = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
        }
        else {
            gInValid = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        }

        RangFloat gInf = gIn.copy(), gOutf = gOut.copy();
        gInf.applyVariation(gInVar.copy(), gInValid);
        gOutf.applyVariation(gOutVar.copy(), new RangFloat(0.0f, 255.0f));

        gIn = gInf.copy();
        gOut = gOutf.copy();

        println("VARIATED GREEN IN: "+gIn+", OUT: "+gOut);
    }

    public void updateBlueVarParams(){
        RangFloat bInValid;
        switch(vBlue){
            case 0 : // NONE
                println("NO BLUE MAP VARIATION"); break;

            case 1 : // NEXT
                mBlue++; if(mBlue>=Defaults.mapOptions.size()) mBlue=0;
                println("BLUE MAP TO NEXT : "+mBlue+ " - "+Defaults.mapOptions.get(mBlue)); break;

            case 2 : // RANDOM
                mGreen = (int)Defaults.getRandom(0, Defaults.mapOptions.size());
                println("BLUE MAP TO RANDOM : "+mBlue+ " - "+Defaults.mapOptions.get(mBlue)); break;

            case 3 : // INVERT
                if(mBlue!=0){ mBlue = (mBlue%2==0)? (mBlue-1) : (mBlue+1);}
                println("BLUE MAP TO INVERT : "+mBlue+ " - "+Defaults.mapOptions.get(mBlue)); break;

            default: println("BLUE VARIATION UNKNOWN ????"); break;
        }

        // En funcio de mRed s'ha d'adaptar rInValid
        if(mBlue>=5 && mBlue<=8){ // RED MAPPED TO ORIENTATION X o Y
            bInValid = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
        }
        else if(mBlue>=9 && mBlue<=10){ // RED MAPPED TO POS X o POS X INV
            bInValid = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
        }
        else if(mBlue>=11 && mBlue<=12){ // RED MAPPED TO POS Y o POS Y INV
            bInValid = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
        }
        else {
            bInValid = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        }

        RangFloat bInf = bIn.copy(), bOutf = bOut.copy();
        bInf.applyVariation(bInVar.copy(), bInValid);
        bOutf.applyVariation(bOutVar.copy(), new RangFloat(0.0f, 255.0f));

        bIn = bInf.copy();
        bOut = bOutf.copy();

        println("VARIATED BLUE IN: "+bIn+", OUT: "+bOut);
    }


    void draw(){

    }


    // CREATE PARTICLES
    public ArrayList<AttractedParticle> createParticles(PApplet pA){
        return new ArrayList<AttractedParticle>();
    }

    /*

    void setStyle(String name){
        AttractedParticleStyle s=new AttractedParticleStyle();

        for(AttractedParticleStyle style : pStyles){
            if(style.name.equals(name)){
                s = style;
            }
        }


        this.step = s.pStep.copy();
        this.delay = s.pDelay.copy();

        this.greyScale = s.grey;
        this.mRed = s.mRed; this.rIn = s.rIn; this.rOut = s.rOut;
        this.mGreen = s.mGreen; this.gIn = s.gIn; this.gOut = s.gOut;
        this.mBlue = s.mBlue; this.bIn = s.bIn; this.bOut = s.bOut;
        this.mOpac = s.mOpac; this.oIn = s.oIn; this.oOut = s.oOut;

        this.equalWH = s.equalWH;
        this.mWidth = s.mWidth; this.wIn = s.wIn; this.wOut = s.wOut;
        this.mHeight = s.mHeight; this.hIn = s.hIn; this.hOut = s.hOut;

        this.refColor = s.refCol.copy();
    }

    */



}