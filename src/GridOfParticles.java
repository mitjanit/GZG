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

        float wCol = (c2.x - c1.x)/(numCols-1);
        float hRow = (c2.y - c1.y)/(numRows-1);

        for(int r=0; r<numRows; r++){
            for(int c=0; c < numCols; c++){
                float xpos = c1.x + wCol*c + xVar.getRandomValue();
                float ypos = c1.y + hRow*r + yVar.getRandomValue();
                PVector position = new PVector(xpos, ypos);
                AttractedParticle p = new AttractedParticle(position, pA.frameCount);
                p.setStep(step.getRandomValue());
                p.setDelay(delay.getRandomValue());
                p.setRandXY(xRand, yRand);
                if(greyScale){ p.setColorParams(mRed, rIn, rOut, mOpac, oIn, oOut); }
                else { p.setColorParams(mRed, rIn, rOut, mGreen, gIn, gOut, mBlue, bIn, bOut, mOpac, oIn, oOut); }
                if(equalWH){ p.setSizeParams(mWidth, wIn, wOut, mHeight, hIn,hOut); }
                else { p.setSizeParams(mWidth, wIn,wOut);}
                p.setColorRef(refColor);
                p.setFadeInSize(fInSize);
                particles.add(p);
            }
        }
        return particles;
    }


}
