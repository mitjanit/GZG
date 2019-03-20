import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public class WaveOfParticles extends SourceOfParticles {

    // Wave of Particles
    PVector p1, p2;
    RangFloat amplitud, xStep;
    boolean ampAsc;
    RangFloat angle, angleStep;

    public WaveOfParticles(PVector a, PVector b, RangFloat amp, boolean amp2, RangFloat xs, RangFloat ar, RangFloat as){
        super();
        this.p1 = a.copy(); this.p2 = b.copy();
        this.amplitud = amp.copy(); this.ampAsc = amp2; this.xStep = xs.copy();
        this.angle = ar.copy(); this.angleStep = as.copy();
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

    public void setAmplitud(float a0, float a1){
        this.amplitud = new RangFloat(a0,a1);
    }

    public void setAmpAsc(boolean b){
        this.ampAsc = b;
    }

    public void setAmplitud(RangFloat a){
        this.amplitud = new RangFloat(a.getMinValue(), a.getMaxValue());
    }

    public void setXStep(float x0, float x1){
        this.xStep = new RangFloat(x0,x1);
    }

    public void setXStep(RangFloat x){
        this.xStep = new RangFloat(x.getMinValue(), x.getMaxValue());
    }


    public void setAngle(float a0, float a1){
        this.angle = new RangFloat(a0,a1);
    }

    public void setAngleStep(RangFloat a){
        this.angleStep = new RangFloat(a.getMinValue(), a.getMaxValue());
    }



    public ArrayList<AttractedParticle> createParticles(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();

        float xs = xStep.getRandomValue(); //println("XS:"+xs);
        float as = angleStep.getRandomValue(); //println("AS:"+as);
        float h = amplitud.getRandomValue(); //println("H:"+h);
        float a = angle.getRandomValue();

        float distX = abs(p1.x - p2.x); float distY = abs(p1.y - p2.y);

        if(distX >= distY){
            float minX = min(p1.x, p2.x); float maxX = max(p1.x, p2.x);
            for(float x = minX; x < maxX; x+=xs){
                if(this.ampAsc) h = map(x, minX, maxX, amplitud.getMinValue(), amplitud.getMaxValue());
                else h = map(x, minX, maxX, amplitud.getMaxValue(), amplitud.getMinValue());
                float xf = x + xVar.getRandomValue();
                float y = map(x, p1.x, p2.x, p1.y, p2.y) + h*sin(a) + yVar.getRandomValue();
                PVector pos = new PVector(xf, y);

                AttractedParticle p = new AttractedParticle(pos, pA.frameCount);
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

                a+= as;
            }
        }
        else {
            float minY = min(p1.y, p2.y); float maxY = max(p1.y, p2.y);
            for(float y = minY; y < maxY; y+=xs){
                if(this.ampAsc) h = map(y, minY, maxY, amplitud.getMinValue(), amplitud.getMaxValue());
                else h = map(y, minY, maxY, amplitud.getMaxValue(), amplitud.getMinValue());
                float yf = y + yVar.getRandomValue();
                float x = map(y, p1.y, p2.y, p1.x, p2.x) + h*sin(a) + xVar.getRandomValue();
                PVector pos = new PVector(x, yf);


                AttractedParticle p = new AttractedParticle(pos, pA.frameCount);
                p.setStep(step.getRandomValue());
                p.setDelay(delay.getRandomValue());
                if(greyScale){ p.setColorParams(mRed, rIn, rOut, mOpac, oIn, oOut); }
                else { p.setColorParams(mRed, rIn, rOut, mGreen, gIn, gOut, mBlue, bIn, bOut, mOpac, oIn, oOut); }
                if(equalWH){ p.setSizeParams(mWidth, wIn, wOut, mHeight, hIn,hOut); }
                else { p.setSizeParams(mWidth, wIn,wOut);}
                p.setColorRef(refColor);
                p.setFadeInSize(fInSize);
                particles.add(p);

                a+= as;
            }
        }
        return particles;
    }


}
