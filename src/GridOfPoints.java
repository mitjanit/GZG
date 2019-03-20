import processing.core.PVector;

public class GridOfPoints extends SetOfPoints {

    PVector p1, p2;
    int numRows, numCols;

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


    public GridOfPoints(int l, PVector a, PVector b, int nr, int nc,
                        RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c,
                        RangFloat npcr, float rar, RangFloat xVar, RangFloat yVar){
        super();
        this.p1 = new PVector(a.x, a.y); this.p2 = new PVector(b.x, b.y);
        this.numRows = nr; this.numCols = nc;
        this.xVariability = xVar.copy();
        this.yVariability = yVar.copy();
        setParams(l, nr*nc, mar, mrr, sar, e, c, npcr, rar, xVar, yVar);
    }

    public GridOfPoints(int l, int n, boolean e, PVector a, PVector b, int nr, int nc){
        super(l,n,e);
        this.p1 = new PVector(a.x, a.y); this.p2 = new PVector(b.x, b.y);
        this.numRows = nr; this.numCols = nc;
    }


    public void setPoints(float minDistance){

        float numAtts = numPoints*ratioAP/100.0f;
        float numReps = numPoints - numAtts;
        float na = 0, nr = 0;
        float wCol = (p2.x - p1.x)/(numCols-1);
        float hRow = (p2.y - p1.y)/(numRows-1);
        int np=0;

        for(int r=0; r<numRows; r++){
            for(int c=0; c < numCols; c++){
                float x = p1.x + wCol*c + xVariability.getRandomValue();
                float y = p1.y + hRow*r + yVariability.getRandomValue();
                PVector pos = new PVector(x, y);

                float mAtt = getMassAttValue(pos, np);
                float mRep = -getMassRepValue(pos, np);
                float spin = getSpinAngleValue(pos, np);
                float np2c = getNPCollapseValue(pos, np);

                float mass = mAtt;
                float nrand = Defaults.getRandom(0,100);
                if((nrand<ratioAP)&&(na<numAtts)){
                    mass = mAtt; na++;
                }
                else if((nrand>=ratioAP)&&(nr<numReps)){
                    mass=mRep; nr++;
                }
                else if(na<numAtts){
                    mass = mAtt; na++;
                }
                else if(nr<numReps){
                    mass=mRep; nr++;
                }

                AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
                //println(ap);
                points.add(ap);
                np++;
            }
        }
        deletePoints(minDistance);
    }


}
