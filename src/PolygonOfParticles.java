import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;
import static processing.core.PConstants.TWO_PI;

public

class PolygonOfParticles extends SourceOfParticles {

    // Polygon of Particles
    RangFloat radiusMin, radiusMax, radiusStep, radiusVariability;
    RangFloat angle, angleStep, randAngle;
    PVector c;

    int numVertexs, numParticles;
    float lineWidth;  // si linewidth==0 llavors fill

    public PolygonOfParticles(int nv, int np, float lw, PVector c, RangFloat rmin, RangFloat rmax, RangFloat rv, RangFloat rs, RangFloat ar, RangFloat as, RangFloat ra){
        super();
        this.numVertexs = nv; this.numParticles = np; this.lineWidth = lw;
        this.c = c.copy();
        this.radiusMin = rmin.copy(); this.radiusMax = rmax.copy();
        this.radiusStep = rs.copy(); this.radiusVariability = rv.copy();
        this.angle = ar.copy(); this.angleStep = as.copy(); this.randAngle = ra.copy();
    }


    public void setNumVertexs(int nv){
        this.numVertexs = nv;
    }

    public void setNumParticles(int np){
        this.numParticles = np;
    }

    public void setLineWidth(float lw){
        this.lineWidth = lw;
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
        if(lineWidth!=0){ return createParticlesLine(pA); }
        else { return createParticlesFill(pA); }
    }


    public ArrayList<AttractedParticle> createParticlesLine(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();

        // Calcular els punts d'acord amb el num. de vertexos
        ArrayList<PVector> vertexos = new ArrayList<PVector>();
        float a = 0.0f;
        float aStep = TWO_PI / numVertexs;
        float a0 = randAngle.getRandomValue();
        float r = radiusMax.getRandomValue();
        for(int i=0; i<numVertexs; i++){
            float xpos = c.x + r*cos(a0+a);
            float ypos = c.y + r*sin(a0+a);
            PVector v = new PVector(xpos, ypos);
            vertexos.add(v);
            a+=aStep;
            println("VERTEX "+i+":"+v);
        }

        float amtStep = 1.0f/numParticles;
        float wStep = 15.0f;

        for(int n=0; n<vertexos.size(); n++){

            PVector c1 = vertexos.get(n);
            PVector c2 = vertexos.get((n+1)%numVertexs);

            println("CREATING LINE "+n);

            for(float w=0.0f; w<lineWidth; w+=wStep){
                for(float amt = 0.0f; amt<=1.0f; amt+=amtStep){
                    println("NEW PART " + amt);
                    float xpos = lerp(c1.x, c2.x, amt) + xVar.getRandomValue();
                    //float ypos = lerp(c1.y + w, c2.y + w, amt) + yVar.getRandomValue();
                    float ypos = lerp(c1.y + 0, c2.y + 0, amt) + yVar.getRandomValue();
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
        }

        return particles;
    }


    public ArrayList<AttractedParticle> createParticlesFill(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();

        // Calcular els punts d'acord amb el num. de vertexos
        float aStep = TWO_PI / numVertexs;
        float a0 = randAngle.getRandomValue();
        float rMin = radiusMin.getRandomValue();
        float rMax = radiusMax.getRandomValue();
        float rStep =  radiusStep.getRandomValue();


        for(float r=rMin; r<=rMax; r+=rStep){

            float a = 0.0f;
            ArrayList<PVector> vertexos = new ArrayList<PVector>();

            for(int i=0; i<numVertexs; i++){
                float xpos = c.x + r*cos(a0+a);
                float ypos = c.y + r*sin(a0+a);
                PVector v = new PVector(xpos, ypos);
                vertexos.add(v);
                a+=aStep;
            }

            float amtStep = 1.0f / map(r, rMin, rMax, 1, numParticles);

            for(int n=0; n<vertexos.size(); n++){

                PVector c1 = vertexos.get(n);
                PVector c2 = vertexos.get((n+1)%numVertexs);

                for(float amt = 0.0f; amt<=1.0f; amt+=amtStep){
                    float xpos = lerp(c1.x, c2.x, amt) + xVar.getRandomValue();
                    float ypos = lerp(c1.y, c2.y, amt) + yVar.getRandomValue();
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

        }

        return particles;
    }


}
