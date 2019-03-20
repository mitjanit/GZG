import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class Scenarios {

    //Triangles
    public static void scenario05(PApplet pA, ArrayList<Layer> layers){

        ArrayList<PVector> ps = new ArrayList<PVector>();
        float rn=300;


        PVector orig = new PVector(Defaults.screenWidth/2, Defaults.screenHeight/2 + rn);
        ps.add(orig);

        ps.add(new PVector(orig.x, orig.y - 2*rn));

        ps.add(new PVector(orig.x + rn/1.2f, orig.y - rn/2));
        ps.add(new PVector(orig.x - rn/1.2f, orig.y - rn/2));

        ps.add(new PVector(orig.x + rn/1.2f, orig.y - rn*1.5f));
        ps.add(new PVector(orig.x - rn/1.2f, orig.y - rn*1.5f));


        float[]   r ={rn};
        String[]  estils = {"STYLE01"};
        float[]   starts = {0, 30};
        float[]   angle = {3*PI/2, PI/2, PI/2, PI/2,3*PI/2,3*PI/2};
        for(int i=0; i<ps.size(); i++){
            float radi = (i>=r.length)?r[(int)Defaults.getRandom(r.length)]:r[i];
            String estil = (i>=estils.length)?estils[(int)Defaults.getRandom(estils.length)]:estils[i];
            float start = (i>=starts.length)?starts[(int)Defaults.getRandom(starts.length)]:starts[i];
            triangle(layers.get(i), ps.get(i), radi, angle[i], 40, estil, start);
        }

    }

    // Rombe amb punts exteriors i graella de particules interior
    public static void triangle(Layer l, PVector c, float r, float a, int np, String styleName, float startFrame){

        PolygonOfPoints bop = new PolygonOfPoints(l.id, np, true, c, new RangFloat(r), new RangFloat(a), 3);
        bop.setVariability(new RangFloat(-10, 10), new RangFloat(-10, 10)); // Variabilitat X , Y
        // mapMassAtt, mapINMassAtt, mpaOutMassAtt, mapMassRep, mapInMassRep, mapOutMassRep, ratioAttRep
        bop.setMassParams(1, new RangFloat(0, 2400), new RangFloat(4, 160), 1, new RangFloat(0, 2400), new RangFloat(10, 20), 80);
        bop.setCollapseParams(0, new RangFloat(0, 2400), new RangFloat(0, 2400), false); // mapNPCollapse, mapInNPC, mapOutNPC, collapseable
        bop.setSpinAngleParams(0, new RangFloat(0, 2400), new RangFloat(0, 0.5f)); // mapSpinA, mapInSpinA, mpaOutSpinA
        bop.setRefParams(c); // RefColor
        bop.setPoints(0);

        l.lPoints.addAll(bop.points);
        l.lSets.add(bop);

        //int nv, int np, float lw, PVector c, RangFloat rmin, RangFloat rmax, RangFloat rv, RangFloat rs, RangFloat ar,  RangFloat as, RangFloat ra
        PolygonOfParticles pp = new PolygonOfParticles(3, 25, 0, c,
                new RangFloat(10), new RangFloat(r), new RangFloat(0), new RangFloat(5),
                new RangFloat(0), new RangFloat(0.23f), new RangFloat(a));
        pp.setStep(new RangFloat(1, 3));
        pp.setDelay(new RangFloat(0, 180));
        pp.setVariability(new RangFloat(-10, 10), new RangFloat(-10, 10));
        pp.setRandXY(new RangFloat(0), new RangFloat(0));
        pp.setFadeInSize(new RangFloat(100, 180));

        // Flow Params
        //pp.setRangFlowFrames(new RangFloat(100, 180)); pp.setRangFlowTimes(new RangFloat(2, 2));
        //pp.setFlowEnabled(true); pp.setFlowUpdate(false); pp.setFlowRandom(false);

        //pp.setStyle(styleName);
        //pp.setStartFrame(startFrame);

        l.lSources.add(pp);
        //layers.get(l).lParticles.addAll(pp.createParticlesFill());


    }

    //Scenario 04 : HEXAS

    void scenario04(PApplet pA, ArrayList<Layer> layers){

        ArrayList<PVector> ps = new ArrayList<PVector>();
        float rn=100;
        PVector orig = new PVector(Defaults.screenWidth/2, Defaults.screenHeight/2);
        ps.add(orig);

        float a=PI/6;
        for(int i=0; i<6; i++){
            float x = Defaults.screenWidth/2 + 1.75f*rn*cos(a);
            float y = Defaults.screenHeight/2 + 1.75f*rn*sin(a);
            ps.add(new PVector(x, y));
            a += TWO_PI/6.0;
        }
        a=0;
        for(int i=0; i<12; i++){
            float f = (i%2==0)?1.75f:2;
            float x = Defaults.screenWidth/2 + f*1.75f*rn*cos(a);
            float y = Defaults.screenHeight/2 + f*1.75f*rn*sin(a);
            ps.add(new PVector(x, y));
            a += TWO_PI/12.0;
        }
        a=PI/6;
        for(int i=0; i<18; i++){
            float f = (i%2==0)?2:2;
            float x = Defaults.screenWidth/2 + f*1.5f*1.75f*rn*cos(a);
            float y = Defaults.screenHeight/2 + f*1.5f*1.75f*rn*sin(a);
            ps.add(new PVector(x, y));
            a += TWO_PI/18.0;
        }
        float[] r ={rn};
        String[] estils = {"STYLE01"};
        float[] starts = {0, 30};
        for(int i=0; i<ps.size(); i++){
            float radi = (i>=r.length)?r[(int)Defaults.getRandom(r.length)]:r[i];
            String estil = (i>=estils.length)?estils[(int)Defaults.getRandom(estils.length)]:estils[i];
            float start = (i>=starts.length)?starts[(int)Defaults.getRandom(starts.length)]:starts[i];
            hexa(layers.get(i), ps.get(i), radi, 25, estil, start);
        }
    }


    // Rombe amb punts exteriors i graella de particules interior
    void hexa(Layer l, PVector c, float r, int np, String styleName, float startFrame){

        PolygonOfPoints bop = new PolygonOfPoints(l.id, np, true, c, new RangFloat(r), new RangFloat(0), 6);
        bop.setVariability(new RangFloat(-0, 0), new RangFloat(-0, 0)); // Variabilitat X , Y
        // mapMassAtt, mapINMassAtt, mpaOutMassAtt, mapMassRep, mapInMassRep, mapOutMassRep, ratioAttRep
        bop.setMassParams(1, new RangFloat(0, 2400), new RangFloat(4, 160), 1, new RangFloat(0, 2400), new RangFloat(10, 20), 80);
        bop.setCollapseParams(0, new RangFloat(0, 2400), new RangFloat(0, 2400), false); // mapNPCollapse, mapInNPC, mapOutNPC, collapseable
        bop.setSpinAngleParams(0, new RangFloat(0, 2400), new RangFloat(0, 0.5f)); // mapSpinA, mapInSpinA, mpaOutSpinA
        bop.setRefParams(c); // RefColor
        bop.setPoints(0);

        l.lPoints.addAll(bop.points);
        l.lSets.add(bop);

        //int nv, int np, float lw, PVector c, RangFloat rmin, RangFloat rmax, RangFloat rv, RangFloat rs, RangFloat ar,  RangFloat as, RangFloat ra
        PolygonOfParticles pp = new PolygonOfParticles(6, 25, 0, c,
                new RangFloat(10), new RangFloat(r), new RangFloat(0), new RangFloat(50),
                new RangFloat(0), new RangFloat(0.3f), new RangFloat(0.01f));
        pp.setStep(new RangFloat(1, 3));
        pp.setDelay(new RangFloat(0, 180));
        pp.setVariability(new RangFloat(-10, 10), new RangFloat(-10, 10));
        pp.setRandXY(new RangFloat(0), new RangFloat(0));
        pp.setFadeInSize(new RangFloat(100, 180));

        // Color & Size Params
     /*
     boolean grey = true;
     if(grey){ pp.setColorParams(5, new RangFloat(-1.0, 1.0), new RangFloat(20.0, 155.0), 0, new RangFloat(0.0, 2400.0), new RangFloat(0.0, 255.0));}
     else { pp.setColorParams(0, new RangFloat(0.0, 2400.0), new RangFloat(100.0, 255.0),
                              0, new RangFloat(0.0, 2400.0), new RangFloat(0.0, 55.0), 
                              0, new RangFloat(0.0, 2400.0), new RangFloat(0.0, 55.0), 
                              0, new RangFloat(0.0, 2400.0), new RangFloat(0.0, 255.0));}
    
     boolean equalWH = true;
     if(!equalWH){ pp.setSizeParams(3, new RangFloat(0.0, 2400.0), new RangFloat(0.0, 2.0),
                                    0, new RangFloat(0.0, 2400.0), new RangFloat(0.0, 3.0));}
     else { pp.setSizeParams(9, new RangFloat(0.0, 2400.0), new RangFloat(0.0, 2.0));}
    
     pp.setColorRef(c); */

        // Flow Params
        //pp.setRangFlowFrames(new RangFloat(100, 180)); pp.setRangFlowTimes(new RangFloat(2, 2));
        //pp.setFlowEnabled(true); pp.setFlowUpdate(false); pp.setFlowRandom(false);

        //pp.setStyle(styleName);
        //pp.setStartFrame(startFrame);

        l.lSources.add(pp);
        //layers.get(l).lParticles.addAll(pp.createParticlesFill());


    }

}
