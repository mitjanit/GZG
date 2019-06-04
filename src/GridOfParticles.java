import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public

class GridOfParticles extends SourceOfParticles {

    // Grid of Particles
    PVector c1, c2;
    float numCols, numRows;

    public GridOfParticles(PVector c1, PVector c2, float nc, float nr){
        super();
        this.c1 = c1.copy(); this.c2 = c2.copy();
        this.numCols = nc; this.numRows = nr;
    }

    public void setVertexs(PVector a, PVector b){
        this.c1 = new PVector(a.x, a.y);
        this.c2 = new PVector(b.x, b.y);
    }

    public void setVertex1(PVector a){
        this.c1 = new PVector(a.x, a.y);
    }

    public void setVertex2(PVector b){
        this.c2 = new PVector(b.x, b.y);
    }

    public void setNumCols(int nc){
        this.numCols = nc;
    }

    public void setNumRows(int nr){
        this.numRows = nr;
    }


    public ArrayList<AttractedParticle> createParticles(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();

        float xStep = (this.c2.x - this.c1.x)/this.numCols;
        float yStep = (this.c2.y - this.c1.y)/this.numRows;
        for(float x = c1.x; x<= c2.x; x+=xStep){
            for(float y = c1.y; y<= c2.y; y+=yStep){
                float xpos = x + xVar.getRandomValue();
                float ypos = y + yVar.getRandomValue();
                PVector position = new PVector(xpos, ypos);
                AttractedParticle p = new AttractedParticle(position, pA.frameCount);
                p.setParamsFromSource(this);
                particles.add(p);
            }
        }
        return particles;
    }

    @Override
    public String toString(){
        String txt ="C1: "+c1.x+", "+c1.y+" \n";
        txt += "C2: "+c2.x+", "+c2.y+" \n";
        txt += "NumCols: "+numCols+", NumRows: "+numRows+"\n";
        return txt;
    }


}
