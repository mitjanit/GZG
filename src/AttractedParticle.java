import processing.core.PApplet;
import processing.core.PVector;

import javax.naming.ldap.Control;
import java.util.ArrayList;

import static processing.core.PApplet.*;

public class AttractedParticle {

    // Propietats de moviment de la Partícula
    PVector position, prevPos, orientation;
    float distance, birth, step, delay;
    RangFloat xR, yR;
    boolean goal;

    // Propietats de visualització (color, mida, fade) de la Particula.
    int mRed, mGreen, mBlue, mOpac, mWidth, mHeight;
    RangFloat rIn, rOut, gIn, gOut, bIn, bOut, oIn, oOut, wIn, wOut,hIn, hOut;
    boolean greyScale, equalWidthAndHeight;
    float fadeInSize;
    PVector  refColor;


    public AttractedParticle(PVector pos, float frameCount){
        this.position = new PVector(pos.x, pos.y);
        this.prevPos = new PVector(pos.x, pos.y);
        this.orientation = new PVector(0,0);
        this.birth = frameCount;
        this.goal = false;
        this.fadeInSize = 0.0f;

        setDefaults();
    }

    public AttractedParticle(PVector pos, float frameCount, float step, float delay){
        this.position = new PVector(pos.x, pos.y);
        this.orientation = new PVector(0,0);
        this.birth = frameCount + delay;

        setDefaults();

        this.step = step;
        this.delay = delay;
        this.goal = false;
        this.fadeInSize = 0.0f;
    }

    public void setStep(float s){
        this.step = s;
    }

    public void setDelay(float d){
        this.delay = d;
    }

    public void setRandXY(RangFloat rx, RangFloat ry){
        this.xR = rx.copy(); this.yR = ry.copy();
    }

    public float getAge(float frameCount){
        return (frameCount - birth);
    }

    public boolean isAlive(float frameCount){
        return (frameCount>=birth);
    }

    public boolean isGoal(){
        return goal;
    }

    public void setFadeInSize(RangFloat f){
        this.fadeInSize = f.getRandomValue();
    }


    void setParamsFromSource(SourceOfParticles sop){
        this.setStep(sop.step.getRandomValue());
        this.setDelay(sop.delay.getRandomValue());
        this.setRandXY(sop.xRand, sop.yRand);
        if(sop.greyScale){ this.setColorParams(sop.mRed, sop.rIn, sop.rOut, sop.mOpac, sop.oIn, sop.oOut); }
        else { this.setColorParams(sop.mRed, sop.rIn, sop.rOut, sop.mGreen, sop.gIn, sop.gOut, sop.mBlue, sop.bIn, sop.bOut, sop.mOpac, sop.oIn, sop.oOut); }
        if(sop.equalWH){ this.setSizeParams(sop.mWidth, sop.wIn, sop.wOut, sop.mHeight, sop.hIn, sop.hOut); }
        else { this.setSizeParams(sop.mWidth, sop.wIn, sop.wOut);}
        this.setColorRef(sop.refColor);
        this.setFadeInSize(sop.fInSize);
    }

    void setParamsFromStyle(AttractedParticleStyle style){
        this.setStep(style.pStep.getRandomValue());
        this.setDelay(style.pDelay.getRandomValue());
        this.setRandXY(style.xRand, style.yRand);
        if(style.grey){ this.setColorParams(style.mRed, style.rIn, style.rOut, style.mOpac, style.oIn, style.oOut); }
        else { this.setColorParams(style.mRed, style.rIn, style.rOut, style.mGreen, style.gIn, style.gOut, style.mBlue, style.bIn, style.bOut, style.mOpac, style.oIn, style.oOut); }
        if(style.equalWH){ this.setSizeParams(style.mWidth, style.wIn, style.wOut, style.mHeight, style.hIn, style.hOut); }
        else { this.setSizeParams(style.mWidth, style.wIn, style.wOut);}
        this.setColorRef(style.refCol);
        this.setFadeInSize(style.fInSize);
    }

    void setDefaults(){

        this.step = 5;
        this.delay = 0;
        this.fadeInSize = 0;

        this.greyScale=false;
        this.refColor = new PVector(Defaults.screenWidth/2, Defaults.screenHeight/2);

        this.mRed = 8;
        this.rIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        this.rOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        this.mGreen = 9;
        this.gIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        this.gOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        this.mBlue = 10;
        this.bIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        this.bOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        this.mOpac = 0;
        this.oIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        this.oOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        this.equalWidthAndHeight = false;

        this.mWidth = 0;
        this.wIn = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);
        this.wOut = new RangFloat(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT);

        this.mHeight = 0;
        this.hIn = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);
        this.hOut = new RangFloat(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT);


    }

    public float distance(AttractedParticle p){
        return dist(this.position.x, this.position.y, p.position.x, p.position.y);
    }


    public boolean awayFrom(float minDist, ArrayList<AttractedParticle> ps){
        boolean b=true;
        for(AttractedParticle ap : ps){
            if(this.distance(ap)<minDist){
                b=false; break;
            }
        }
        return b;
    }


    public String toString(){
        String info = "PARTICLE. Pos: ["+position.x+", "+position.y+"] , Ori: ["+orientation.x+", "+orientation.y+"], Rand: [" +xR+", "+yR+"] \n";
        info += "Step: "+step+", Delay:"+delay+", Birth:"+birth+"\n";
        info += "Color Info: \n";
        info += "Map Red:"+Defaults.mapOptions.get(mRed)+", Red In:"+rIn+", Red Out:"+rOut+"\n";
        info += "Map Green:"+Defaults.mapOptions.get(mGreen)+", Green In:"+gIn+", Green Out:"+gOut+"\n";
        info += "Map Blue:"+Defaults.mapOptions.get(mBlue)+", Blue In:"+bIn+", Blue Out:"+bOut+"\n";
        info += "Map Width:"+Defaults.mapOptions.get(mWidth)+", Width In:"+wIn+", Width Out:"+wOut+"\n";
        return info;
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

    public void setSizeParams(int mWidth, RangFloat widthIn, RangFloat widthOut, int mHeight, RangFloat heightIn, RangFloat heightOut){
        this.mWidth = mWidth; this.wIn = widthIn.copy(); this.wOut = widthOut.copy();
        this.mHeight = mHeight; this.hIn = heightIn.copy(); this.hOut = heightOut.copy();
    }

    public void setSizeParams(int mWidth, RangFloat widthIn, RangFloat widthOut){
        this.mWidth = mWidth; this.wIn = widthIn.copy(); this.wOut = widthOut.copy();
        this.mHeight = mWidth; this.hIn = widthIn.copy(); this.hOut = widthOut.copy();
    }

    public float getMappedValue(int mapValue, RangFloat valueIn, RangFloat valueOut, float frameCount){
        float s=0.0f;
        float valueInMin = valueIn.getMinValue();
        float valueInMax = valueIn.getMaxValue();
        float valueOutMin = valueOut.getMinValue();
        float valueOutMax = valueOut.getMaxValue();

        //"NONE", "DISTANCE", "AGE","ORIENTATION X", "ORIENTATION Y", "POSITION X", "POSITION Y", "DISTANCE REF"
        switch(mapValue){
            case 0: s = valueOut.getMaxValue(); break; //NONE
            case 1: s = map(distance, valueInMin, valueInMax, valueOutMin, valueOutMax); break;        // DISTANCE
            case 2: s = map(distance, valueInMin, valueInMax, valueOutMax, valueOutMin); break;        // DISTANCE INV
            case 3: s = map(getAge(frameCount)%500, valueInMin, valueInMax, valueOutMin, valueOutMax); break;    // AGE
            case 4: s = map(getAge(frameCount)%500, valueInMin, valueInMax, valueOutMax, valueOutMin); break;    // AGE INV
            case 5: s = map(orientation.x, valueInMin, valueInMax, valueOutMin, valueOutMax); break;   // ORIENTATION X
            case 6: s = map(orientation.x, valueInMin, valueInMax, valueOutMax, valueOutMin); break;   // ORIENTATION X INV
            case 7: s = map(orientation.y, valueInMin, valueInMax, valueOutMin, valueOutMax); break;  // ORIENTATION Y
            case 8: s = map(orientation.y, valueInMin, valueInMax, valueOutMax, valueOutMin); break;  // ORIENTATION Y INV
            case 9: s = map(position.x, valueInMin, valueInMax, valueOutMin, valueOutMax); break;     // POSITION X
            case 10: s = map(position.x, valueInMin, valueInMax, valueOutMax, valueOutMin); break;     // POSITION X INV
            case 11: s = map(position.y, valueInMin, valueInMax, valueOutMin, valueOutMax); break;     // POSITION Y
            case 12: s = map(position.y, valueInMin, valueInMax, valueOutMax, valueOutMin); break;     // POSITION Y INV
            case 13: float distRef = dist(position.x, position.y, refColor.x, refColor.y);
                s = map(distRef, valueInMin, valueInMax, valueOutMin, valueOutMax); break;       // DISTANCE REF COLOR
            case 14: float distRef2 = dist(position.x, position.y, refColor.x, refColor.y);
                s = map(distRef2, valueInMin, valueInMax, valueOutMax, valueOutMin); break;       // DISTANCE REF COLOR INV
        }
        return s;
    }

    public float getWidth(float frameCount){
        return min(getMappedValue(this.mWidth, this.wIn, this.wOut, frameCount), Defaults.MAX_SIZE_OUT);
    }

    public float getHeight(float frameCount){
        return min(getMappedValue(this.mHeight, this.hIn, this.hOut, frameCount), Defaults.MAX_SIZE_OUT);
    }

    public float getRed(float frameCount){
        return getMappedValue(this.mRed, this.rIn, this.rOut, frameCount);
    }

    public float getGreen(float frameCount){
        return getMappedValue(this.mGreen, this.gIn, this.gOut, frameCount);
    }

    public float getBlue(float frameCount){
        return getMappedValue(this.mBlue, this.bIn, this.bOut, frameCount);
    }

    public float getOpacity(float frameCount){
        return getMappedValue(this.mOpac, this.oIn, this.oOut, frameCount);
    }

    public boolean canStart(float frameCount){
        return frameCount>= (birth + delay);
    }


    public void move(ControlWindow controls, ArrayList<AttractorPoint> attractors, float frameCount){
        if(canStart(frameCount)){

            PVector target = new PVector();
            orientation = new PVector();
            float minDist=100000000f;

            for (int i=0; i<attractors.size(); i++) {

                AttractorPoint ap = attractors.get(i);

                if(ap.isEnabled()){

                    float disti = this.position.dist(ap.position);


                    if (disti<minDist) {
                        minDist=disti;
                    }

                    if(disti<Defaults.getRandom(0,10)){
                        goal = true;
                        ap.addParticle();
                    }

                    PVector  vi = ap.position.copy();
                    vi.sub(this.position);
                    vi.normalize();
                    if(controls.cDefaults.divGravity){ vi.mult(ap.gravity/disti);
                    } else { vi.mult(ap.gravity); }
                    target.add(vi);
                    if(controls.cDefaults.divSpinAngle){ target.rotate(ap.spinAngle/disti);}
                    else { target.rotate(ap.spinAngle); }

                }

            }

            if(attractors.size()>0){

                distance = minDist;

                target.normalize();
                orientation=target.copy();
                target.setMag(this.step);  //5

                prevPos = position.copy();
                position.add(target);
            }

            if(getAge(frameCount)>=100000){
                goal = true;
            }
        }
    }


    public void display(PApplet pA){

        if(canStart(pA.frameCount)){

            float pRed      = getRed(pA.frameCount);
            float pGreen    = getGreen(pA.frameCount);
            float pBlue     = getBlue(pA.frameCount);
            float pOpac     = getOpacity(pA.frameCount);
            float pWidth    = getWidth(pA.frameCount);
            float pHeight   = getHeight(pA.frameCount);
            float pAge      = getAge(pA.frameCount);

            float xr = xR.getRandomValue();
            float yr = yR.getRandomValue();

            pA.noStroke();
            pA.fill(pRed,pGreen,pBlue,pOpac);
            pA.pushMatrix();
                //pA.translate(-Defaults.sceneWidth/2, -Defaults.sceneHeight/2);
                pA.translate(position.x + xr, position.y + yr);

                if(fadeInSize>=1 && pAge<fadeInSize){
                        float tp = min(pAge, fadeInSize)/fadeInSize;
                        float nW = lerp(0, pWidth, tp);
                        float nH = lerp(0, pHeight, tp);
                        pA.ellipse(0,0,nW, nH);
                }
                else {
                    pA.ellipse(0,0,pWidth, pHeight);
                }
            pA.popMatrix();



            // BIG EXPORT
            if(Defaults.bigExport){
                Defaults.big.noStroke();
                Defaults.big.fill(pRed,pGreen,pBlue,pOpac);
                int numTimesBig = 3;
                for(float times = 1; times <numTimesBig; times++){
                    Defaults.big.pushMatrix();


                        float interX = map(times, 1, numTimesBig, prevPos.x, position.x);
                        float interY = map(times, 1, numTimesBig, prevPos.y, position.y);

                        float bigX = map(interX, 0, pA.width, 0, pA.width*Defaults.BIG_SCALE);
                        float bigY = map(interY, 0, pA.height, 0, pA.height*Defaults.BIG_SCALE);

                        Defaults.big.translate(bigX + xr, bigY + yr);
                        if(fadeInSize>=1){
                            float topW = pWidth; float topH = pHeight;
                            if(pAge<fadeInSize){
                                float tp = min(pAge, fadeInSize)/fadeInSize;
                                float nW = lerp(0, topW, tp);
                                float nH = lerp(0, topH, tp);
                                Defaults.big.ellipse(0,0,nW*Defaults.BIG_SCALE, nH*Defaults.BIG_SCALE);
                            }
                            else {
                                Defaults.big.ellipse(0,0,pWidth*Defaults.BIG_SCALE, pHeight*Defaults.BIG_SCALE);
                            }
                        }
                        else {
                            Defaults.big.ellipse(0,0,pWidth*Defaults.BIG_SCALE, pHeight*Defaults.BIG_SCALE);
                        }

                    Defaults.big.popMatrix();
                }
            }
        }
    }


}
