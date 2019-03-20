import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public class SpiralOfParticles extends SourceOfParticles {

    // Spiral of Particles
    RangFloat radiusMin, radiusMax, radiusStep, radiusVariability;
    RangFloat angle, angleStep, randAngle;
    PVector c;

    boolean invertRadius, invertAngle;

    public SpiralOfParticles(PVector c, RangFloat rmin, RangFloat rmax, RangFloat rv, RangFloat rs, RangFloat ar, RangFloat as, RangFloat ra){
        super();
        this.c = c.copy();
        this.radiusMin = rmin.copy(); this.radiusMax = rmax.copy();
        this.radiusStep = rs.copy(); this.radiusVariability = rv.copy();
        this.angle = ar.copy(); this.angleStep = as.copy(); this.randAngle = ra.copy();

        this.invertAngle=false; this.invertRadius = false;
    }

    public void setCenter(PVector a){
        this.c = new PVector(a.x, a.y);
    }

    public void setRadiusMin(float r0, float r1){
        this.radiusMin = new RangFloat(r0,r1);
    }

    public void setRadiusMax(float r0, float r1){
        this.radiusMax = new RangFloat(r0,r1);
    }

    public void setRadiusVariability(RangFloat r){
        this.radiusVariability = new RangFloat(r.getMinValue(), r.getMaxValue());
    }

    public void setRadiusStep(RangFloat r){
        this.radiusStep = new RangFloat(r.getMinValue(), r.getMaxValue());
    }

    public void setAngle(float a0, float a1){
        this.angle = new RangFloat(a0,a1);
    }

    public void setAngleStep(RangFloat a){
        this.angleStep = new RangFloat(a.getMinValue(), a.getMaxValue());
    }

    public void setRandomAngle(RangFloat a){
        this.randAngle = new RangFloat(a.getMinValue(), a.getMaxValue());
    }

    public void setInvertRadius(boolean b){
        this.invertRadius = b;
    }

    public void setInvertAngle(boolean b){
        this.invertAngle = b;
    }


    public ArrayList<AttractedParticle> createParticles(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();

        float r0 = radiusMin.getMinValue();
        float r1 = radiusMax.getMaxValue();
        float rStep = radiusStep.getMaxValue();
        float ra = randAngle.getRandomValue();
        float a0 = angle.getMinValue();
        float a1 = angle.getMaxValue();
        float aStep = angleStep.getMaxValue();

        for(float r = r0, a=a0; r<r1 && a<a1; r+=rStep, a+=aStep){
            if(invertRadius){ rStep = map(r, r0, r1, radiusStep.getMaxValue(), radiusStep.getMinValue()); }
            else {rStep = map(r, r0, r1, radiusStep.getMinValue(), radiusStep.getMaxValue());}
            if(invertAngle){ aStep = map(a, a0, a1, angleStep.getMaxValue(), angleStep.getMinValue());}
            else { aStep = map(a, a0, a1, angleStep.getMinValue(), angleStep.getMaxValue());}
            float rf = r + radiusVariability.getRandomValue();
            float x = c.x + rf*cos(a+ra) + xVar.getRandomValue();
            float y = c.y + rf*sin(a+ra) + yVar.getRandomValue();
            PVector pos = new PVector(x, y);
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
        }
        return particles;
    }


}
