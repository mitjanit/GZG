import geomerative.RG;
import geomerative.RPoint;
import geomerative.RShape;
import processing.core.PVector;

import static processing.core.PApplet.println;
import static processing.core.PConstants.*;

public class TextOfPoints extends SetOfPoints {

    String word;
    String fontFamily;
    float fontSize;
    int fontAlignX, fontAlignY, polyMode;
    float polyLength, polyAngle, polyStep, adaptorScale, adaptorOffset;
    RangFloat rotAngle;
    boolean letterCenter;
    PVector c;

    RShape grp;
    RPoint[] pointsRG;

    boolean adaptor;

    TextOfPoints(int l, int n, boolean e, PVector c, String w, String ff, float fs){
        super(l, n, e);
        this.c = c.copy();
        this.word=w;
        this.fontFamily = ff;
        this.fontSize = fs;
        this.fontAlignX = CENTER;
        this.fontAlignY = BASELINE;
        this.polyMode = RG.UNIFORMLENGTH;
        this.polyLength = 20;
        this.polyAngle = Defaults.getRandom(0, PI/2);
        this.polyStep = Defaults.getRandom(0,1);
        this.adaptorScale = 1.0f;
        this.adaptorOffset = Defaults.getRandom(0,1);
        this.rotAngle=new RangFloat(0.0f);
        this.letterCenter = false;
        adaptor = false;
    }

    TextOfPoints(int l, int n, String w, String ff, float fs, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar){
        super();
        this.word=w;
        this.fontFamily = ff;
        this.fontSize = fs;
        this.fontAlignX = CENTER;
        this.fontAlignY = BASELINE;
        this.polyMode = RG.UNIFORMLENGTH;
        this.polyLength = 20;
        this.polyAngle = Defaults.getRandom(0, PI/2);
        this.polyStep = Defaults.getRandom(0,1);
        this.adaptorScale = 1.0f;
        this.adaptorOffset = Defaults.getRandom(0,1);
        this.rotAngle=new RangFloat(0.0f);
        this.letterCenter = false;
        adaptor = false;
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0), new RangFloat(0));
    }

    public void setCenter(PVector a){
        this.c = new PVector(a.x, a.y);
    }

    void setWord(String w){
        this.word = w;
    }

    void setFontFamily(String f){
        this.fontFamily = f;
    }

    void setFontSize(float s){
        this.fontSize = s;
    }

    void setFontAlignX(int a){
        this.fontAlignX = a;
    }

    void setFontAlignY(int a){
        this.fontAlignY = a;
    }

    void setFontAlign(int ax, int ay){
        this.fontAlignX = ax; this.fontAlignY = ay;
    }

    void setRotateAngle(RangFloat a){
        this.rotAngle = a;
    }

    void setLetterCenter(boolean b){
        this.letterCenter = b;
    }

    void setRotationParams(RangFloat ra, boolean lc){
        this.rotAngle = ra.copy(); this.letterCenter = lc;
    }

    void setPolygonizerParams(int pm, float pl, float ps, float pa){
        this.polyMode = pm; this.polyLength = pl; this.polyStep = ps; this.polyAngle = pa;
    }

    void setAdaptorParams(float as, float ao){
        this.adaptorScale = as; this.adaptorOffset = ao;
    }

    void setAdaptor(boolean b){
        this.adaptor = b;
    }

    boolean isAdaptor(){
        return adaptor;
    }

    boolean isPolygonizer(){
        return !adaptor;
    }



    void setPoints(float minDistance){

        //println("WORD: "+word+", FONT: "+fontFamily+", SIZE: "+fontSize+", ALIGN X :"+fontAlignX+", ALIGN Y:"+fontAlignY+",POLY MODE:"+polyMode+", ROT ANGLE:"+rotAngle+", LETTER CENTER:"+letterCenter);

        grp = RG.getText(word, fontFamily, (int)fontSize, fontAlignX);

        if(letterCenter){
            for(int i=0; i<grp.children.length; i++){
                grp.children[i].rotate(rotAngle.getRandomValue(), grp.children[i].getCenter());
            }
        }
        else {
            grp.rotate(rotAngle.getRandomValue(), grp.getCenter());
        }

        //RG.BYPOINT, RG.BYELEMENTPOSITION or RG.BYELEMENTINDEX
        if(isAdaptor()){
            println("ADAPTOR");
            RG.setAdaptor(polyMode);
            RG.setAdaptorScale(adaptorScale);
            RG.setAdaptorLengthOffset(adaptorOffset); // [0,1]
        }
        // RG.ADAPTATIVE, RG.UNIFORMLENGTH or RG.UNIFORMSTEP.
        else {
            println("POLIGONIZER");
            RG.setPolygonizer(polyMode);
            RG.setPolygonizerLength(polyLength); //UNIFORMLENGTH - [0, Length]
            RG.setPolygonizerAngle(polyAngle);  //ADAPTATIVE - [0 - PI/2]
            RG.setPolygonizerStep(polyStep); //UNIFORMSTEP  - [0, 1] (step) o [0, NumSteps]
        }
        pointsRG = grp.getPoints();
        int nPoints = pointsRG.length;

        float numAtts = nPoints*ratioAP/100.0f;
        float numReps = nPoints - numAtts;
        int na=0, nr=0;

        for (int i = 0; i < nPoints; i++) {

            PVector pos = new PVector(pointsRG[i].x + c.x, pointsRG[i].y + c.y + fontSize/3);

            float mAtt = getMassAttValue(pos, i);
            float mRep = -getMassRepValue(pos, i);
            float spin = getSpinAngleValue(pos, i);
            float np2c = getNPCollapseValue(pos, i);

            //Random Distribution of Att & Rep
            float mass = mAtt;
            if((na==numAtts) && (nr<numReps)){
                mass=mRep; nr++;
            }
            else if((nr==numReps) && (na<numAtts)){
                na++;
            }
            else {
                if(Defaults.getRandom(0,100)>ratioAP){
                    mass = mRep; nr++;
                }
                else {
                    na++;
                }
            }

            AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
            points.add(ap);
        }
        deletePoints(minDistance);
    }
}
