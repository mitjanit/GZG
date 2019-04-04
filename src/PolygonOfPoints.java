import processing.core.PVector;

import static processing.core.PApplet.*;

public class PolygonOfPoints extends SetOfPoints {

    PVector c;
    RangFloat radiusMax;
    RangFloat randAngle;
    int numVertexs;

    public void setCenter(PVector a){
        this.c = new PVector(a.x, a.y);
    }

    public void setRadiusMax(float r0, float r1){
        this.radiusMax = new RangFloat(r0,r1);
    }

    public void setRandomAngle(RangFloat a){
        this.randAngle = new RangFloat(a.getMinValue(), a.getMaxValue());
    }

    public void setNumVertexs(int nv){
        this.numVertexs = nv;
    }


    public PolygonOfPoints(int l, int n, PVector a, RangFloat rmax, RangFloat ra, int nv, RangFloat mar, RangFloat mrr, RangFloat sar, boolean e, boolean c, RangFloat npcr, float rar){
        super();
        this.c = new PVector(a.x, a.y, a.z);
        this.radiusMax = new RangFloat(rmax.getMinValue(), rmax.getMaxValue());
        this.randAngle = new RangFloat(ra.getMinValue(), ra.getMaxValue());
        this.numVertexs = nv;
        setParams(l, n, mar, mrr, sar, e, c, npcr, rar, new RangFloat(0.0f), new RangFloat(0.0f));
    }

    //+++++++++
    public PolygonOfPoints(int l, int n, boolean e, PVector a, RangFloat rmax, RangFloat ra, int nv){
        super(l, n, e);
        this.c = new PVector(a.x, a.y, a.z);
        this.radiusMax = new RangFloat(rmax.getMinValue(), rmax.getMaxValue());
        this.randAngle = new RangFloat(ra.getMinValue(), ra.getMaxValue());
        this.numVertexs = nv;
    }


    public void setPoints(float minDistance){

        float astep = TWO_PI/(float)this.numVertexs;
        float a = this.randAngle.getRandomValue();
        println("RAND ANGLE: "+a);
        float r = this.radiusMax.getRandomValue();

        int np=0;

        for(float i=0; i<this.numVertexs; i++){

            PVector p1 = new PVector (c.x + r*cos(a + i*astep) , c.y + r*sin(a + i*astep));
            PVector p2 = new PVector (c.x + r*cos(a + ((i+1)%numVertexs)*astep) , c.y + r*sin(a + ((i+1)%numVertexs)*astep));
            for(int j=0; j<numPoints; j++){
                float x = map(j, 0, numPoints-1, p1.x, p2.x) + xVariability.getRandomValue();
                float y = map(j, 0, numPoints-1, p1.y, p2.y) + yVariability.getRandomValue();
                PVector pos = new PVector(x, y);
                float mAtt = getMassAttValue(pos, np); println(mAtt);
                float mRep = -getMassRepValue(pos, np);
                float spin = getSpinAngleValue(pos, np);
                float np2c = getNPCollapseValue(pos, np);

                float mass = mAtt;
                float nrand = Defaults.getRandom(0,100);
                if(nrand>ratioAP){
                    mass = mRep;
                }

                AttractorPoint ap = new AttractorPoint(pos, mass, spin, enabled, collapsable, np2c);
                println("Point at:"+pos.x+" "+pos.y+" with g:"+mass);
                this.points.add(ap);
                np++;
            }
        }
        deletePoints(minDistance);
    }

}
