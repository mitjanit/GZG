import processing.core.PVector;

import java.io.PrintWriter;

import static processing.core.PApplet.println;
import static processing.core.PConstants.TAB;

public class AttractedParticleStyle {

    // Nom de l'estil
    String name;

    RangFloat pStep, pDelay;
    
    // Valors de mapping de Color i Mida
    int mRed, mGreen, mBlue, mOpac, mWidth, mHeight;
    RangFloat rIn, rOut, gIn, gOut, bIn, bOut, oIn, oOut;
    RangFloat wIn, wOut, hIn, hOut;
    boolean grey, equalWH;
    PVector refCol;
    RangFloat fInSize;

    RangFloat xRand, yRand;
    RangFloat xVar, yVar;

    int blendMode = 0;


    public AttractedParticleStyle(){
        this.name = "NO NAME";
    }

    public AttractedParticleStyle(String n){
        this.name = n;
    }

    public void setName(String n){
        this.name = n;
    }

        /*
    public void copyFromGUI(){
        setName(styleName);
        setStep(particleStep);
        setDelay(particleDelay);
        if(greyScale){ setColorParams(mapRed, redIn, redOut, mapOpac, opacIn, opacOut, refColor); }
        else { setColorParams(mapRed, redIn, redOut, mapGreen, greenIn, greenOut, mapBlue, blueIn, blueOut, mapOpac, opacIn, opacOut, refColor); }
        if(equalWidthAndHeight){ setSizeParams(mapWidth, widthIn, widthOut);}
        else { setSizeParams(mapWidth, widthIn, widthOut, mapHeight, heightIn, heightOut);}
    }*/
    public static AttractedParticleStyle createAttractedParticleStyleFromGUI(ControlWindow cw){
            AttractedParticleStyle style = new AttractedParticleStyle();
            style.setName(cw.cParticles.styleName);
            style.setStep(cw.cParticles.particleStep);
            style.setDelay(cw.cParticles.particleDelay);
            style.setVariability(cw.cLine.xVar, cw.cLine.yVar);
            style.setRandomness(cw.cRandom.xRange, cw.cRandom.yRange);
            style.setFadeInSize(cw.cParticles.fadeInSize);
            style.setColorParams(cw.cParticles.mapRed, cw.cParticles.redIn, cw.cParticles.redOut, cw.cParticles.mapGreen, cw.cParticles.greenIn, cw.cParticles.greenOut, cw.cParticles.mapBlue, cw.cParticles.blueIn, cw.cParticles.blueOut, cw.cParticles.mapOpac, cw.cParticles.opacIn, cw.cParticles.opacOut, cw.cParticles.refColor);
            style.setSizeParams(cw.cParticles.mapWidth, cw.cParticles.widthIn, cw.cParticles.widthOut, cw.cParticles.mapHeight, cw.cParticles.heightIn, cw.cParticles.heightOut);
            return style;
    }

    public void randomStyle(){
        name = "RANDOM " + Defaults.timestamp();
        int no = Defaults.mapOptions.size();
        mRed = (int)Defaults.getRandom(0,no); mGreen = (int)Defaults.getRandom(0,no); mBlue = (int)Defaults.getRandom(0,no); mOpac = (int)Defaults.getRandom(0,no);
        mWidth = (int)Defaults.getRandom(0,no); mHeight = (int)Defaults.getRandom(0,no);
        pStep = new RangFloat(Defaults.getRandom(0, 2), Defaults.getRandom(2, 5)); pDelay = new RangFloat(Defaults.getRandom(0, 60), Defaults.getRandom(60, 180));
        grey = (Defaults.getRandom(1)>0.5);
        equalWH = (Defaults.getRandom(1)>0.5);

        xRand = new RangFloat(0);
        yRand = new RangFloat(0);

        xVar = new RangFloat(0);
        yVar = new RangFloat(0);

        // RED IN
        if(mRed>=5 && mRed<=8){ // MAPPED TO ORIENTATION X o Y
            rIn = new RangFloat(Defaults.getRandom(Defaults.MIN_ORIENT,0), Defaults.getRandom(0, Defaults.MAX_ORIENT));
        }
        else if(mRed>=9 && mRed<=10){ // MAPPED TO POS X o POS X INV
            rIn = new RangFloat(Defaults.getRandom(Defaults.MIN_WIDTH,Defaults.MAX_WIDTH/2), Defaults.getRandom(Defaults.MAX_WIDTH/2, Defaults.MAX_WIDTH));
        }
        else if(mRed>=11 && mRed<=12){ // MAPPED TO POS Y o POS Y INV
            rIn = new RangFloat(Defaults.getRandom(Defaults.MIN_HEIGHT,Defaults.MAX_HEIGHT/2), Defaults.getRandom(Defaults.MAX_HEIGHT/2, Defaults.MAX_HEIGHT));
        }
        else {
            rIn = new RangFloat(Defaults.getRandom(Defaults.MIN_COLOR_IN,Defaults.MAX_COLOR_IN/2), Defaults.getRandom(Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN));
        }

        // GREEN IN
        if(mGreen>=5 && mGreen<=8){ // MAPPED TO ORIENTATION X o Y
            gIn = new RangFloat(Defaults.getRandom(Defaults.MIN_ORIENT,0), Defaults.getRandom(0, Defaults.MAX_ORIENT));
        }
        else if(mGreen>=9 && mGreen<=10){ // MAPPED TO POS X o POS X INV
            gIn = new RangFloat(Defaults.getRandom(Defaults.MIN_WIDTH,Defaults.MAX_WIDTH/2), Defaults.getRandom(Defaults.MAX_WIDTH/2, Defaults.MAX_WIDTH));
        }
        else if(mGreen>=11 && mGreen<=12){ // MAPPED TO POS Y o POS Y INV
            gIn = new RangFloat(Defaults.getRandom(Defaults.MIN_HEIGHT,Defaults.MAX_HEIGHT/2), Defaults.getRandom(Defaults.MAX_HEIGHT/2, Defaults.MAX_HEIGHT));
        }
        else {
            gIn = new RangFloat(Defaults.getRandom(Defaults.MIN_COLOR_IN,Defaults.MAX_COLOR_IN/2), Defaults.getRandom(Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN));
        }

        // BLUE IN
        if(mBlue>=5 && mBlue<=8){ // MAPPED TO ORIENTATION X o Y
            bIn = new RangFloat(Defaults.getRandom(Defaults.MIN_ORIENT,0), Defaults.getRandom(0, Defaults.MAX_ORIENT));
        }
        else if(mBlue>=9 && mBlue<=10){ // MAPPED TO POS X o POS X INV
            bIn = new RangFloat(Defaults.getRandom(Defaults.MIN_WIDTH,Defaults.MAX_WIDTH/2), Defaults.getRandom(Defaults.MAX_WIDTH/2, Defaults.MAX_WIDTH));
        }
        else if(mBlue>=11 && mBlue<=12){ // MAPPED TO POS Y o POS Y INV
            bIn = new RangFloat(Defaults.getRandom(Defaults.MIN_HEIGHT,Defaults.MAX_HEIGHT/2), Defaults.getRandom(Defaults.MAX_HEIGHT/2, Defaults.MAX_HEIGHT));
        }
        else {
            bIn = new RangFloat(Defaults.getRandom(Defaults.MIN_COLOR_IN,Defaults.MAX_COLOR_IN/2), Defaults.getRandom(Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN));
        }

        // OPAC IN
        if(mOpac>=5 && mOpac<=8){ // MAPPED TO ORIENTATION X o Y
            oIn = new RangFloat(Defaults.getRandom(Defaults.MIN_ORIENT,0), Defaults.getRandom(0, Defaults.MAX_ORIENT));
        }
        else if(mOpac>=9 && mOpac<=10){ // MAPPED TO POS X o POS X INV
            oIn = new RangFloat(Defaults.getRandom(Defaults.MIN_WIDTH,Defaults.MAX_WIDTH/2), Defaults.getRandom(Defaults.MAX_WIDTH/2, Defaults.MAX_WIDTH));
        }
        else if(mOpac>=11 && mOpac<=12){ // MAPPED TO POS Y o POS Y INV
            oIn = new RangFloat(Defaults.getRandom(Defaults.MIN_HEIGHT,Defaults.MAX_HEIGHT/2), Defaults.getRandom(Defaults.MAX_HEIGHT/2, Defaults.MAX_HEIGHT));
        }
        else {
            oIn = new RangFloat(Defaults.getRandom(Defaults.MIN_COLOR_IN,Defaults.MAX_COLOR_IN/2), Defaults.getRandom(Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN));
        }

        // width IN
        if(mWidth>=5 && mWidth<=8){ // MAPPED TO ORIENTATION X o Y
            wIn = new RangFloat(Defaults.getRandom(Defaults.MIN_ORIENT,0), Defaults.getRandom(0, Defaults.MAX_ORIENT));
        }
        else if(mWidth>=9 && mWidth<=10){ // MAPPED TO POS X o POS X INV
            wIn = new RangFloat(Defaults.getRandom(Defaults.MIN_WIDTH,Defaults.MAX_WIDTH/2), Defaults.getRandom(Defaults.MAX_WIDTH/2, Defaults.MAX_WIDTH));
        }
        else if(mWidth>=11 && mWidth<=12){ // MAPPED TO POS Y o POS Y INV
            wIn = new RangFloat(Defaults.getRandom(Defaults.MIN_HEIGHT,Defaults.MAX_HEIGHT/2), Defaults.getRandom(Defaults.MAX_HEIGHT/2, Defaults.MAX_HEIGHT));
        }
        else {
            wIn = new RangFloat(Defaults.getRandom(Defaults.MIN_COLOR_IN,Defaults.MAX_COLOR_IN/2), Defaults.getRandom(Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN));
        }

        // HEIGHT IN
        if(mHeight>=5 && mHeight<=8){ // MAPPED TO ORIENTATION X o Y
            hIn = new RangFloat(Defaults.getRandom(Defaults.MIN_ORIENT,0), Defaults.getRandom(0, Defaults.MAX_ORIENT));
        }
        else if(mHeight>=9 && mHeight<=10){ // MAPPED TO POS X o POS X INV
            hIn = new RangFloat(Defaults.getRandom(Defaults.MIN_WIDTH,Defaults.MAX_WIDTH/2), Defaults.getRandom(Defaults.MAX_WIDTH/2, Defaults.MAX_WIDTH));
        }
        else if(mHeight>=11 && mHeight<=12){ // MAPPED TO POS Y o POS Y INV
            hIn = new RangFloat(Defaults.getRandom(Defaults.MIN_HEIGHT,Defaults.MAX_HEIGHT/2), Defaults.getRandom(Defaults.MAX_HEIGHT/2, Defaults.MAX_HEIGHT));
        }
        else {
            hIn = new RangFloat(Defaults.getRandom(Defaults.MIN_COLOR_IN,Defaults.MAX_COLOR_IN/2), Defaults.getRandom(Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN));
        }

        rOut = new RangFloat(Defaults.getRandom(0, 125), Defaults.getRandom(125, 255));
        gOut = new RangFloat(Defaults.getRandom(0, 125), Defaults.getRandom(125, 255));
        bOut = new RangFloat(Defaults.getRandom(0, 125), Defaults.getRandom(125, 255));
        oOut = new RangFloat(Defaults.getRandom(0, 125), Defaults.getRandom(125, 255));
        wOut = new RangFloat(Defaults.getRandom(0, 2), Defaults.getRandom(2, 5)); hOut = new RangFloat(Defaults.getRandom(0, 2), Defaults.getRandom(2, 5));

        refCol = new PVector(Defaults.getRandom(0, Defaults.sceneWidth), Defaults.getRandom(0, Defaults.sceneHeight));

        fInSize = new RangFloat(0, Defaults.getRandom(0,10));

    }

    public void writeFile(PrintWriter output){
        String s ="";
        s += name + TAB;  // NAME 0
        s += "" + mRed + TAB + mGreen+ TAB + mBlue+ TAB + mOpac+ TAB + mWidth+ TAB + mHeight + TAB; //MAP 1 - 6
        s += "" + pStep.getMinValue() + TAB + pStep.getMaxValue()+ TAB + pDelay.getMinValue()+ TAB + pDelay.getMaxValue()+ TAB; //STEP & DELAY 7 -10 // 0 - 1
        s += "" + rIn.getMinValue() + TAB + rIn.getMaxValue()+ TAB + rOut.getMinValue()+ TAB + rOut.getMaxValue()+ TAB; //RED IN & OUT 11 - 14 // 2 - 3
        s += "" + gIn.getMinValue() + TAB + gIn.getMaxValue()+ TAB + gOut.getMinValue()+ TAB + gOut.getMaxValue()+ TAB; //GREEN IN & OUT 15 - 18  // 4 - 5
        s += "" + bIn.getMinValue() + TAB + bIn.getMaxValue()+ TAB + bOut.getMinValue()+ TAB + bOut.getMaxValue()+ TAB; //BLUE IN & OUT 19 - 22  // 6 - 7
        s += "" + oIn.getMinValue() + TAB + oIn.getMaxValue()+ TAB + oOut.getMinValue()+ TAB + oOut.getMaxValue()+ TAB; //OPAC IN & OUT 23 - 26  // 8 - 9
        s += "" + wIn.getMinValue() + TAB + wIn.getMaxValue()+ TAB + wOut.getMinValue()+ TAB + wOut.getMaxValue()+ TAB; //WIDTH IN & OUT 27 - 30  // 10 - 11
        s += "" + hIn.getMinValue() + TAB + hIn.getMaxValue()+ TAB + hOut.getMinValue()+ TAB + hOut.getMaxValue()+ TAB; //HEIGHT IN & OUT 31 - 34  //  12 - 13
        s += "" + refCol.x + TAB + refCol.y + TAB; // REFCOLOR X & Y 35 - 36
        s += "" + (grey?"true":"false") + TAB + (equalWH?"true":"false"); // Grey & EqualWH 37 - 38
        output.println(s);
    }

    AttractedParticleStyle getDataFromLine(String[] w){
        AttractedParticleStyle s = new AttractedParticleStyle(w[0]);
        int[] ms = new int[6]; for(int i=0; i<ms.length; i++){ ms[i] = Integer.parseInt(w[i+1]); println(i+":"+ms[i]);}
        RangFloat[] rs = new RangFloat[14];
        for(int i=0, j=7; i<rs.length; i++, j+=2){ rs[i] = new RangFloat(Float.parseFloat(w[j]), Float.parseFloat(w[j+1])); println(i+":"+rs[i]);}
        PVector p = new PVector(Float.parseFloat(w[35]), Float.parseFloat(w[36]));
        s.setStep(rs[0]);s.setDelay(rs[1]);
        println("B&W:"+w[37]); println("EqualWH:"+w[38]);
        if(w[37].equals("false")){ s.setColorParams(ms[0], rs[2], rs[3], ms[1], rs[4], rs[5], ms[2], rs[6], rs[7], ms[3], rs[8], rs[9], p);}
        else {s.setColorParams(ms[0], rs[2], rs[3], ms[3], rs[8], rs[9], p);}
        if(w[38].equals("true")){ s.setSizeParams(ms[4], rs[10], rs[11]);}
        else { s.setSizeParams(ms[4], rs[10], rs[11], ms[5], rs[12], rs[13]);}
        return s;
    }

    public void setStep(RangFloat s){
        this.pStep = s.copy();
    }

    public void setDelay(RangFloat d){
        this.pDelay = d.copy();
    }

    public void setVariability(RangFloat xV, RangFloat yV){
        this.xVar = xV.copy(); this.yVar = yV.copy();
    }

    public void setFadeInSize(RangFloat fadeInSize){
        this.fInSize = fadeInSize.copy();
    }

    public void setRandomness(RangFloat xR, RangFloat yR){
        this.xRand = xR.copy(); this.yRand = yR.copy();
    }

    public void setColorParams(int mr, RangFloat r0, RangFloat r1,
                               int mg, RangFloat g0, RangFloat g1,
                               int mb, RangFloat b0, RangFloat b1,
                               int mo, RangFloat o0, RangFloat o1, PVector p){
        this.mRed = mr; this.rIn = r0.copy(); this.rOut = r1.copy();
        this.mGreen = mg; this.gIn = g0.copy(); this.gOut = g1.copy();
        this.mBlue = mb; this.bIn = b0.copy(); this.bOut = b1.copy();
        this.mOpac = mo; this.oIn = o0.copy(); this.oOut = o1.copy();
        grey = false; this.refCol = p.copy();
    }

    public void setColorParams(int mr, RangFloat r0, RangFloat r1, int mo, RangFloat o0, RangFloat o1, PVector p){
        this.mRed = mr; this.rIn = r0.copy(); this.rOut = r1.copy();
        this.mGreen = mr; this.gIn = r0.copy(); this.gOut = r1.copy();
        this.mBlue = mr; this.bIn = r0.copy(); this.bOut = r1.copy();
        this.mOpac = mo; this.oIn = o0.copy(); this.oOut = o1.copy();
        grey = true; this.refCol = p.copy();
    }

    public void setSizeParams(int mw, RangFloat w0, RangFloat w1, int mh, RangFloat h0, RangFloat h1){
        this.mWidth = mw; this.wIn = w0.copy(); this.wOut = w1.copy();
        this.mHeight = mh; this.hIn = h0.copy(); this.hOut = h1.copy();
        this.equalWH = false;
    }

    public void setSizeParams(int mw, RangFloat w0, RangFloat w1){
        this.mWidth = mw; this.wIn = w0.copy(); this.wOut = w1.copy();
        this.mHeight = mw; this.hIn = w0.copy(); this.hOut = w1.copy();
        this.equalWH = true;
    }


    @Override
    public String toString(){
        String info="*************** ESTIL DE PARTICULES **************** \n";
        info += " NOM: "+this.name+"\n";
        info += " STEP: "+pStep+", DELAY: "+pDelay+"\n";
        info += " XVAR: "+xVar+", YVAR: "+yVar+"\n";
        info += " X RAND: "+xRand+", Y RAND: "+yRand+"\n";
        String mR = (String)ControlParticles.mapOptions.get(mRed);
        info += " RED MAP: "+mR+", IN: "+rIn+", OUT: "+rOut+"\n";
        String mG = (String)ControlParticles.mapOptions.get(mGreen);
        info += " GREN MAP: "+mG+", IN: "+gIn+", OUT: "+gOut+"\n";
        String mB = (String)ControlParticles.mapOptions.get(mBlue);
        info += " BLUE MAP: "+mB+", IN: "+bIn+", OUT: "+bOut+"\n";
        String mO = (String)ControlParticles.mapOptions.get(mOpac);
        info += " OPAC MAP: "+mO+", IN: "+oIn+", OUT: "+oOut+"\n";
        return info;
    }

}
