import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class FrameOfParticles extends SourceOfParticles {

    // Grid of Particles
    PVector c1, c2;
    float frameWidth;
    float numCols, numRows;

    public FrameOfParticles(PVector c1, PVector c2, float nc, float nr, float fw){
        super();
        this.c1 = c1.copy(); this.c2 = c2.copy();
        this.numCols = nc; this.numRows = nr; this.frameWidth = fw;
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

    public void setFrameWidth(float fw){
        this.frameWidth = fw;
    }


    public ArrayList<AttractedParticle> createParticles(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();

        float wCol = frameWidth / numCols;
        float hRow = frameWidth / numRows;

        float x0 = c1.x - frameWidth; float y0 = c1.y - frameWidth;
        float x1 = c2.x + frameWidth; float y1 = c2.y + frameWidth;

        for(float x=x0; x<x1; x+=wCol){
            for(float y=y0; y<y1; y+=hRow){
                float xpos = x + xVar.getRandomValue();
                float ypos = y + yVar.getRandomValue();
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
                if(!(xpos>=c1.x && xpos <=c2.x && ypos>=c1.y && ypos<=c2.y))
                    particles.add(p);
            }
        }
        return particles;
    }


}
