import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static processing.core.PApplet.*;

public class ImageOfPoints extends SetOfPoints {

    String imageName;
    PVector p1, p2;
    int numRows, numCols;

    RangFloat thresholdAtt, thresholdRep;
    boolean thresholdMode;
    float threshold;

    public ImageOfPoints(int l, int n, boolean e, String imgName, PVector a, PVector b, int nr, int nc){
        super(l, n, e);
        this.p1 = new PVector(a.x, a.y); this.p2 = new PVector(b.x, b.y);
        this.imageName = imgName;
        this.numRows = nr; this.numCols = nc;
    }

    public ImageOfPoints(int l, int n, String imgName, PVector a, PVector b, int nr, int nc, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar, RangFloat xVar, RangFloat yVar){
        super();
        this.p1 = new PVector(a.x, a.y); this.p2 = new PVector(b.x, b.y);
        this.imageName = imgName;
        this.numRows = nr; this.numCols = nc;
        this.xVariability = xVar.copy();
        this.yVariability = yVar.copy();
        setParams(l, nr*nc, mar, mrr, sar, e, c, npcr, rar, xVar, yVar);
    }

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

    public void setThresholdParams(RangFloat ta, RangFloat tr, float t, boolean tm){
        this.thresholdAtt = ta.copy();
        this.thresholdRep = tr.copy();
        this.threshold = t;
        this.thresholdMode = tm;
    }


    public void setPoints(PApplet pA, boolean ascAtt, boolean ascRep){

        float wCol = floor((p2.x - p1.x)/(numCols-1));  //floor
        float hRow = floor((p2.y - p1.y)/(numRows-1));

        PImage img = pA.loadImage(imageName);
        img.loadPixels();
        int dimension = img.width * img.height;
        int np=0;

        for(int col=0; col < numCols; col++){
            for(int row=0; row<numRows; row++){

                float x = constrain(p1.x + wCol*col + xVariability.getRandomValue(), 0, Defaults.sceneWidth);
                float y = constrain(p1.y + hRow*row + yVariability.getRandomValue(), 0, Defaults.sceneHeight);
                PVector pos = new PVector(x, y);

                float x2 = map(col,0, numCols, 0, img.width);
                float y2 = map(row,0, numRows, 0, img.height);
                int loc = (int)x2 + (int)(y2*img.width);

                float r = pA.red(img.pixels[loc]);
                float g = pA.green(img.pixels[loc]);
                float b = pA.blue(img.pixels[loc]);

                float mAtt = getMassAttValue(pos, np);
                float mRep = -getMassRepValue(pos, np);
                float spin = getSpinAngleValue(pos, np);
                float np2c = getNPCollapseValue(pos, np);

                float mass = 0.0f;

                if(r>=thresholdAtt.getMinValue() && r<=thresholdAtt.getMaxValue()){
                    mass = mAtt;
                    if(ascAtt){
                        mass = map(r, thresholdAtt.getMinValue(), thresholdAtt.getMaxValue(), massAttRangeOut.getMinValue(), massAttRangeOut.getMaxValue());
                    }
                    else {
                        mass = map(r, thresholdAtt.getMinValue(), thresholdAtt.getMaxValue(), massAttRangeOut.getMaxValue(), massAttRangeOut.getMinValue());
                    }
                }
                else if(r>=thresholdRep.getMinValue() && r<=thresholdRep.getMaxValue()){
                    mass=mRep; //println("REP");
                    if(ascRep){
                        mass = -map(r, thresholdRep.getMinValue(), thresholdRep.getMaxValue(), massRepRangeOut.getMinValue(), massRepRangeOut.getMaxValue());
                    }
                    else {
                        mass = -map(r, thresholdRep.getMinValue(), thresholdRep.getMaxValue(), massRepRangeOut.getMaxValue(), massRepRangeOut.getMinValue());
                    }
                }
                else {
                    println("?????");
                }

                AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
                if(mass!=0.0) points.add(ap);
                np++;
            }
        }
    }

}
