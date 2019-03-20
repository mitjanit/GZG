import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public

class RingOfParticles extends SourceOfParticles {

    // Ring of Particles
    RangFloat radiusMin, radiusMax, radiusStep, radiusVariability;
    RangFloat angle, angleStep, randAngle;
    PVector c;

    public RingOfParticles(PVector c, RangFloat rmin, RangFloat rmax, RangFloat rv, RangFloat rs,
                                      RangFloat ar, RangFloat as, RangFloat ra){
        super();
        this.c = c.copy();
        this.radiusMin = rmin.copy(); this.radiusMax = rmax.copy();
        this.radiusStep = rs.copy(); this.radiusVariability = rv.copy();
        this.angle = ar.copy(); this.angleStep = as.copy(); this.randAngle = ra.copy();
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


    public ArrayList<AttractedParticle> createParticles(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();

        float rStep = radiusStep.getMaxValue();
        float r0 = radiusMin.getRandomValue();
        float r1 = radiusMax.getRandomValue();

        for(float r = r0; r<r1; r+=rStep){

            float a0 = angle.getMinValue();
            float a1 = angle.getMaxValue();
            float aStep = map(r, r0, r1, angleStep.getMaxValue(), angleStep.getMinValue());
            rStep = map(r, r0, r1, radiusStep.getMaxValue(), radiusStep.getMinValue());
            float ra = randAngle.getRandomValue();

            for(float a = a0; a < a1; a += aStep){

                float rf = r + radiusVariability.getRandomValue();
                float xpos = c.x + rf*cos(a+ra) + xVar.getRandomValue();
                float ypos = c.y + rf*sin(a+ra) + yVar.getRandomValue();
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
                particles.add(p);
            }
        }
        return particles;
    }


}
