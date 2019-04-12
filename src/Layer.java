import static processing.core.PApplet.*;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class Layer {

    // ID layer
    int id;

    // Collections of sets & attractor points.
    ArrayList<AttractorPoint> lPoints;
    ArrayList<SetOfPoints> lSets;

    // Collection of sources & particles.
    ArrayList<AttractedParticle> lParticles;
    ArrayList<SourceOfParticles> lSources;

    // Collection of points created with mouse.
    SetOfPoints mousePoints;

    public Layer(int n){
        this.id = n;
        lSets = new ArrayList<SetOfPoints>();
        lPoints = new ArrayList<AttractorPoint>();

        lParticles = new ArrayList<AttractedParticle>();
        lSources = new ArrayList<SourceOfParticles>();

        mousePoints = new SetOfPoints();
        lSets.add(mousePoints);
    }

    void update(PApplet pA, ControlWindow cw){
        for(int ns=0; ns<lSets.size(); ns++){
            SetOfPoints set = lSets.get(ns);
            set.update();
            if(cw.cDefaults.displayPoints){
                set.draw(pA);
            }
        }
        if(lPoints.size()>0){
            for(int np=0; np<lParticles.size(); np++) {
                AttractedParticle part = lParticles.get(np);
                if(part!=null && part.isAlive(pA.frameCount)){
                    part.move(cw, lPoints, pA.frameCount);
                    if(cw.cDefaults.displayParticles){ part.display(pA); }
                    if(part.isGoal()){ lParticles.remove(np); }
                }
            }
        }
        for(int ng=0; ng<lSources.size(); ng++){
            SourceOfParticles sop = lSources.get(ng);
            if(cw.cDefaults.displaySources){
                sop.draw();
            }
            if(sop.startFrame<pA.frameCount && !sop.createdParticles){
                this.lParticles.addAll(sop.createParticles(pA));
                sop.createdParticles = true;
            }
            if(false) {//sop.createdParticles && sop.isFlowEnabled() && sop.areTimesToFlow() && sop.isFrameToFlow()){

                println(pA.frameCount + ": CREATING NEW FLOW ("+sop.numTimesFlowed+")");

                if(sop.isUpdate()){

                    //println("UPDATE FLOW PARAMS");

                    if(sop.isFlowRandom()){

                        //println("RANDOM STYLE TO NEW FLOW");
                        AttractedParticleStyle ns = new AttractedParticleStyle();
                        ns.randomStyle();
                        //ns.setGUI();
                        // Color & Size Params
                        if(ns.grey){ sop.setColorParams(ns.mRed, ns.rIn, ns.rOut, ns.mOpac, ns.oIn, ns.oOut);}
                        else { sop.setColorParams(ns.mRed, ns.rIn, ns.rOut, ns.mGreen, ns.gIn, ns.gOut, ns.mBlue, ns.bIn, ns.bOut, ns.mOpac, ns.oIn, ns.oOut);}
                        if(ns.equalWH){ sop.setSizeParams(ns.mWidth, ns.wIn,ns.wOut,ns.mHeight, ns.hIn,ns.hOut);}
                        else { sop.setSizeParams(ns.mWidth, ns.wIn,ns.wOut);}
                        sop.setColorRef(ns.refCol);
                        sop.setFadeInSize(ns.fInSize);
                        sop.setColorMod(Defaults.blendOptions.get(ns.blendMode).toString());
                    }
                    else {
                        sop.updateVarParams();
                    }
                }

                sop.setNextFlowTime(pA.frameCount);
                sop.updateNumFlowTimes();

                ArrayList<AttractedParticle> parts = sop.createParticles(pA);
                lParticles.addAll(parts);

            }
        }

    }


    void draw(PApplet pA){
        for(SetOfPoints set : lSets){
            set.draw(pA);
        }
        for(AttractedParticle part :lParticles) {
            part.display(pA);
        }
    }


    void printLayerSets(){
        for(SetOfPoints set: lSets){
            set.printSet();
        }
    }

    void deleteAll(){
        lSets = new ArrayList<SetOfPoints>();
        lPoints = new ArrayList<AttractorPoint>();
        lParticles = new ArrayList<AttractedParticle>();
        lSources = new ArrayList<SourceOfParticles>();
        mousePoints = new SetOfPoints();
        lSets.add(mousePoints);
    }

    void deleteAllSets(){
        lSets = new ArrayList<SetOfPoints>();
        lPoints = new ArrayList<AttractorPoint>();
        mousePoints = new SetOfPoints();
        lSets.add(mousePoints);
    }

    void deleteAllPoints(){
        lPoints  = new ArrayList<AttractorPoint>();
        lSets = new ArrayList<SetOfPoints>();
        mousePoints = new SetOfPoints();
        lSets.add(mousePoints);
    }

    void deleteAllParticles(){
        lParticles = new ArrayList<AttractedParticle>();
    }

    void deleteAllSources(){
        lSources = new ArrayList<SourceOfParticles>();
    }

    void deleteSet(int ns){
        lSets.remove(ns);
    }

    void enableSet(int ns, boolean b){
        lSets.get(ns).setEnabled(b);
    }

    void collapseSet(int ns, boolean b){
        lSets.get(ns).setCollapsable(b);
    }

    void oscillateSet(int ns, boolean [] b){
        lSets.get(ns).setOscillate(b);
    }

    // Add elements to Layer

    void addSet(SetOfPoints sop){
        this.lSets.add(sop);
    }

    void addPoint(AttractorPoint ap){
        this.lPoints.add(ap);
    }

    void addSource(SourceOfParticles sop){
        this.lSources.add(sop);
    }

    void addParticles( ArrayList<AttractedParticle> p){
        this.lParticles.addAll(p);
    }


    // CREATE PARTICLES *******************************************************//

    void createParticleOnMouse(PApplet pA, AttractedParticleStyle style){
        PVector pos = new PVector(pA.mouseX, pA.mouseY);
        AttractedParticle p = new AttractedParticle(pos, pA.frameCount);
        p.setParamsFromStyle(style);
        this.lParticles.add(p);
    }

    void createParticleOnMouse(PApplet pA, ControlWindow controls){
        PVector pos = new PVector(pA.mouseX, pA.mouseY);
        AttractedParticle p = new AttractedParticle(pos, pA.frameCount);
        AttractedParticleStyle style = controls.createAttractedParticleStyleFromGUI();
        p.setParamsFromStyle(style);  // Falla estil o es transparent
        this.lParticles.add(p);
    }

    void createAreaOfParticles(PApplet pA, int numPs, float minDist, PVector corner1, PVector corner2, AttractedParticleStyle style){
        AreaOfParticles aop = new AreaOfParticles(corner1, corner2, numPs, minDist);
        aop.setParamsFromStyle(style);
        // Flow Params
        // Color & Size Variation Params
        this.lSources.add(aop);
        this.lParticles.addAll(aop.createParticles(pA));
    }

    void createLineOfParticles(PApplet pA, int numPs, float frameWidth, PVector corner1, PVector corner2, AttractedParticleStyle style){
        LineOfParticles lop = new LineOfParticles(corner1, corner2, numPs, frameWidth);
        lop.setParamsFromStyle(style);
        // Flow Params
        // Color & Size Variation Params
        this.lSources.add(lop);
        this.lParticles.addAll(lop.createParticles(pA));
    }

    void createFrameOfParticles(PApplet pA, PVector corner1, PVector corner2, int numCols, int numRows,float frameWidth, AttractedParticleStyle style){
        FrameOfParticles fop = new FrameOfParticles(corner1, corner2, numCols, numRows, frameWidth);
        fop.setParamsFromStyle(style);
        // Flow Params
        // Color & Size Variation Params
        this.lSources.add(fop);
        this.lParticles.addAll(fop.createParticles(pA));
    }

    void createGridOfParticles(PApplet pA, PVector corner1, PVector corner2, int numCols, int numRows, AttractedParticleStyle style){
        GridOfParticles gop = new GridOfParticles(corner1, corner2, numCols, numRows);
        gop.setParamsFromStyle(style);
        // Flow Params
        // Color & Size Variation Params
        this.lSources.add(gop);
        this.lParticles.addAll(gop.createParticles(pA));
    }

    void createRingOfParticles(PApplet pA, PVector centre,
                               RangFloat minRadius, RangFloat maxRadius, RangFloat radiusVariability, RangFloat radiusStep,
                               RangFloat angle, RangFloat angleStep, RangFloat randomAngle, AttractedParticleStyle style){
        RingOfParticles rop = new RingOfParticles(centre, minRadius, maxRadius, radiusVariability, radiusStep, angle, angleStep, randomAngle);
        rop.setParamsFromStyle(style);
        // Flow Params
        // Color & Size Variation Params
        this.lSources.add(rop);
        this.lParticles.addAll(rop.createParticles(pA));
    }

    void createPolygonOfParticles(PApplet pA, PVector centre, int numVertexos, int numPoints, float frameWidth,
                                  RangFloat minRadius, RangFloat maxRadius, RangFloat radiusVariability, RangFloat radiusStep,
                                  RangFloat angle, RangFloat angleStep, RangFloat randomAngle, AttractedParticleStyle style){

        PolygonOfParticles pop = new PolygonOfParticles(numVertexos, numPoints, frameWidth, centre, minRadius, maxRadius, radiusVariability, radiusStep, angle, angleStep, randomAngle);
        pop.setParamsFromStyle(style);
        // Flow Params
        // Color & Size Variation Params
        this.lSources.add(pop);
        this.lParticles.addAll(pop.createParticles(pA));
    }

    void createSpiralOfParticles(PApplet pA, PVector centre,
                                 RangFloat minRadius, RangFloat maxRadius, RangFloat radiusVariability, RangFloat radiusStep,
                                 RangFloat angle, RangFloat angleStep, RangFloat randomAngle, AttractedParticleStyle style){
        SpiralOfParticles pop = new SpiralOfParticles(centre, minRadius, maxRadius, radiusVariability, radiusStep, angle, angleStep, randomAngle);
        pop.setParamsFromStyle(style);
        // Flow Params
        // Color & Size Variation Params
        this.lSources.add(pop);
        this.lParticles.addAll(pop.createParticles(pA));
    }

    void createWaveOfParticles(PApplet pA, PVector corner1, PVector corner2,
                               RangFloat amplitud, boolean ampAsc, RangFloat xStep, RangFloat angle, RangFloat angleStep,
                               AttractedParticleStyle style){

        WaveOfParticles wop = new WaveOfParticles(corner1, corner2, amplitud, ampAsc, xStep, angle, angleStep);
        wop.setParamsFromStyle(style);
        // Flow Params
        // Color & Size Variation Params
        this.lSources.add(wop);
        this.lParticles.addAll(wop.createParticles(pA));
    }


    // DELETE PARTICLES

    void deleteParticleOn(PVector p, float distance){
        ArrayList<AttractedParticle> lP = this.lParticles;
        for(int i=0; i<lP.size(); i++){
            AttractedParticle ap = lP.get(i);
            if(dist(p.x, p.y, ap.position.x, ap.position.y)<distance){
                lP.remove(i);
            }
        }
    }



    // POINTS CREATION

    void createPointOnMouse(PApplet pA, SetOfPointsStyle style){
        PVector pos = new PVector(pA.mouseX, pA.mouseY);
        float g = style.mapOutMassAtt.getRandomValue();
        if(Defaults.getRandom(100)>style.ratioAttRep){
            g = - style.mapOutMassRep.getRandomValue();
        }
        float spin = style.mapOutSpinAngle.getRandomValue();
        float np2c = style.mapOutNPCollapse.getRandomValue();
        AttractorPoint ap = new AttractorPoint(pos, g, spin, style.enable, style.collapseable, np2c);

        mousePoints.points.add(ap);
        lPoints.add(ap);
    }

    void createPointOnMouse(PApplet pA, ControlWindow controls){
        PVector pos = new PVector(pA.mouseX, pA.mouseY);
        float g = controls.cCommons.massAtt.getRandomValue();
        if(Defaults.getRandom(100)>controls.cCommons.ratioAttRep){
            g = - controls.cCommons.massRep.getRandomValue();
        }
        float spin = controls.cCommons.spinAng.getRandomValue();
        float np2c = controls.cCommons.np2Col.getRandomValue();
        boolean enablePoint = controls.cCommons.enable;
        boolean collapseable = controls.cCommons.collapseable;
        AttractorPoint ap = new AttractorPoint(pos, g, spin, enablePoint, collapseable, np2c);

        mousePoints.points.add(ap);
        lPoints.add(ap);
    }

    //AREAS
    void createAreasOfPoints(ControlWindow controls, int numTimes, int numPoints, float minDistance, SetOfPointsStyle style,
                             RangFloat xR, RangFloat yR,RangFloat dx, RangFloat dy,
                             boolean cloneArea, boolean symmetryX, boolean symmetryY,
                             boolean mirrorX, boolean mirrorY, boolean quadSim, boolean hexaSim, boolean clone, boolean shuffle, boolean randomize, boolean invert){

        if(cloneArea){
            // Original
            AreaOfPoints aop = new AreaOfPoints(this.id,numPoints, style.enable, xR, yR);
            aop.setParamsFromStyle(style);
            aop.setPoints(controls,minDistance,mirrorX, mirrorY, quadSim, hexaSim, clone, shuffle, randomize, invert);
            //aop.setOscillators( );
            lPoints.addAll(aop.points);
            lSets.add(aop);

            for(int i=1; i<numTimes; i++){
                // Cloned & displaced
                AreaOfPoints aop2 = aop.copy();
                float vdx = i*dx.getRandomValue();
                float vdy = i*dy.getRandomValue();
                aop2.setParamsFromStyle(style);
                aop2.displacePoints(vdx, vdy);
                //aop2.setOscillators();
                lPoints.addAll(aop2.points);
                lSets.add(aop2);
            }
        }
        else {
            for(int i=0; i<numTimes; i++){
                RangFloat xr = style.xRange.displaceValues(i*dx.getRandomValue());
                RangFloat yr = style.yRange.displaceValues(i*dy.getRandomValue());
                AreaOfPoints aop = new AreaOfPoints(this.id,numPoints, style.enable, xr,yr);
                aop.setParamsFromStyle(style);
                aop.setPoints(controls,minDistance,mirrorX, mirrorY, quadSim, hexaSim, clone, shuffle, randomize, invert);
                //aop.setOscillators();
                lPoints.addAll(aop.points);
                lSets.add(aop);

                if(symmetryX && i>0){
                    RangFloat xr2 = style.xRange.displaceValues(-i*dx.getRandomValue());
                    RangFloat yr2 = style.yRange.displaceValues(i*dy.getRandomValue());
                    AreaOfPoints aop2 = new AreaOfPoints(this.id,numPoints, style.enable, xr2,yr2);
                    aop2.setParamsFromStyle(style);
                    aop2.setPoints(controls,minDistance,mirrorX, mirrorY, quadSim, hexaSim, clone, shuffle, randomize, invert);
                    //aop.setOscillators();
                    lPoints.addAll(aop2.points);
                    lSets.add(aop2);
                }
                else if(symmetryY && i>0){
                    RangFloat xr2 = style.xRange.displaceValues(i*dx.getRandomValue());
                    RangFloat yr2 = style.yRange.displaceValues(-i*dy.getRandomValue());
                    AreaOfPoints aop2 = new AreaOfPoints(this.id,numPoints, style.enable, xr2,yr2);
                    aop2.setPoints(controls,minDistance,mirrorX, mirrorY, quadSim, hexaSim, clone, shuffle, randomize, invert);
                    //aop.setOscillators();
                    lPoints.addAll(aop2.points);
                    lSets.add(aop2);
                }
            }
        }
    }

    void createPolygonOfPoints(int numTimes, int numPoints, int numVertexos, float minDistance, SetOfPointsStyle style,
                               PVector centre, RangFloat maxRadius, RangFloat randomAngle,
                               RangFloat dx, RangFloat dy , RangFloat dr, RangFloat da,
                               boolean symmetryX, boolean symmetryY){

        for(int t=0; t<numTimes; t++){

            PVector c = new PVector(centre.x + t*dx.getRandomValue(), centre.y + t*dy.getRandomValue());
            RangFloat mRadius = maxRadius.displaceValues(t*dr.getRandomValue());
            RangFloat randAngle = randomAngle.displaceValues(t*da.getRandomValue());

            PolygonOfPoints bop = new PolygonOfPoints(this.id, numPoints, style.enable, c, mRadius, randAngle, numVertexos);
            bop.setParamsFromStyle(style);
            bop.setPoints(minDistance);
            //bop.setOscillators();
            lPoints.addAll(bop.points);
            lSets.add(bop);

            if (symmetryX && t>0) {
                PVector cs = new PVector(centre.x - t*dx.getMaxValue(), centre.y + t*dy.getMaxValue());
                PolygonOfPoints bops = new PolygonOfPoints(this.id, numPoints, style.enable, cs, mRadius, randAngle, numVertexos);
                bops.setParamsFromStyle(style);
                bops.setPoints(minDistance);
                //bops.setOscillators();
                lPoints.addAll(bops.points);
                lSets.add(bops);
            }

            if (symmetryY && t>0) {
                PVector cs = new PVector(centre.x + t*dx.getMaxValue(), centre.y - t*dy.getMaxValue());
                PolygonOfPoints bops = new PolygonOfPoints(this.id, numPoints,style.enable, cs, mRadius, randAngle, numVertexos);
                bops.setParamsFromStyle(style);
                bops.setPoints(minDistance);
                //bops.setOscillators();
                lPoints.addAll(bops.points);
                lSets.add(bops);
            }

            if (symmetryX && symmetryY && t>0) {
                PVector cs = new PVector(centre.x - t*dx.getMaxValue(), centre.y - t*dy.getMaxValue());
                PolygonOfPoints bops = new PolygonOfPoints(this.id, numPoints, style.enable, cs, mRadius, randAngle, numVertexos);
                bops.setParamsFromStyle(style);
                bops.setPoints(minDistance);
                //bops.setOscillators();
                lPoints.addAll(bops.points);
                lSets.add(bops);
            }
        }
    }

    void createPolyLineOfPoints(int numTimes, int numPoints, ArrayList<PVector> polyPoints, float minDistance,
                                SetOfPointsStyle style,
                                RangFloat dx, RangFloat dy,
                                boolean symmetryX, boolean symmetryY){
        for(int i=0; i<numTimes; i++){
            ArrayList<PVector> pts = new ArrayList<PVector>();
            for(PVector p : polyPoints){
                PVector np = new PVector(p.x + i*dx.getRandomValue(), p.y + i*dy.getRandomValue());
                pts.add(np);
            }
            PolyLineOfPoints lop = new PolyLineOfPoints(this.id,numPoints,style.enable,pts);
            lop.setParamsFromStyle(style);
            lop.setPoints(minDistance);
            //lop.setOscillators();
            lPoints.addAll(lop.points);
            lSets.add(lop);

            if(symmetryX && i>0){
                println("Symmetry X");
                ArrayList<PVector> pts2 = new ArrayList<PVector>();
                for(PVector p : polyPoints){
                    PVector np = new PVector(p.x - i*dx.getRandomValue(), p.y + i*dy.getRandomValue());
                    pts2.add(np);
                }

                PolyLineOfPoints lop2 = new PolyLineOfPoints(this.id,numPoints,style.enable,pts2);
                lop2.setParamsFromStyle(style);
                //lop2.setOscillators();
                lPoints.addAll(lop2.points);
                lSets.add(lop2);
            }

            if(symmetryY && i>0){
                println("Symmetry Y");
                ArrayList<PVector> pts2 = new ArrayList<PVector>();
                for(PVector p : polyPoints){
                    PVector np = new PVector(p.x + i*dx.getRandomValue(), p.y - i*dy.getRandomValue());
                    pts2.add(np);
                }
                PolyLineOfPoints lop2 = new PolyLineOfPoints(this.id,numPoints,style.enable,pts2);
                lop2.setParamsFromStyle(style);
                lop2.setPoints(minDistance);
                //lop2.setOscillators();
                lPoints.addAll(lop2.points);
                lSets.add(lop2);
            }

            if(symmetryX && symmetryY && i>0){
                println("Symmetry Y");
                ArrayList<PVector> pts2 = new ArrayList<PVector>();
                for(PVector p : polyPoints){
                    PVector np = new PVector(p.x - i*dx.getRandomValue(), p.y - i*dy.getRandomValue());
                    pts2.add(np);
                }
                PolyLineOfPoints lop2 = new PolyLineOfPoints(this.id,numPoints,style.enable,pts2);
                lop2.setParamsFromStyle(style);
                lop2.setPoints(minDistance);
                //lop2.setOscillators();
                lPoints.addAll(lop2.points);
                lSets.add(lop2);
            }

        }
    }

    // LINES
    void createLineOfPoints(int numTimes, int numPoints, float minDistance, PVector corner1, PVector corner2,
                             SetOfPointsStyle style,
                             RangFloat dx, RangFloat dy,
                             boolean symmetryX, boolean symmetryY){
        for(int i=0; i<numTimes; i++){
            PVector p1 = new PVector(corner1.x + i*dx.getRandomValue(), corner1.y + i*dy.getRandomValue());
            PVector p2 = new PVector(corner2.x + i*dx.getRandomValue(), corner2.y + i*dy.getRandomValue());
            LineOfPoints lop = new LineOfPoints(this.id,numPoints,style.enable,p1,p2);
            lop.setParamsFromStyle(style);
            lop.setPoints(minDistance);
            //lop.setOscillators();
            lPoints.addAll(lop.points);
            lSets.add(lop);

            if(symmetryX && i>0){
                println("Symmetry X");
                PVector p1s = new PVector(corner1.x - i*dx.getRandomValue(), corner1.y + i*dy.getRandomValue());
                PVector p2s = new PVector(corner2.x - i*dx.getRandomValue(), corner2.y + i*dy.getRandomValue());
                LineOfPoints lop2 = new LineOfPoints(this.id,numPoints,style.enable,p1s,p2s);
                lop2.setParamsFromStyle(style);
                lop2.setPoints(minDistance);
                //lop2.setOscillators();
                lPoints.addAll(lop2.points);
                lSets.add(lop2);
            }
            if(symmetryY && i>0){
                println("Symmetry Y");
                PVector p1s = new PVector(corner1.x + i*dx.getRandomValue(), corner1.y - i*dy.getRandomValue());
                PVector p2s = new PVector(corner2.x + i*dx.getRandomValue(), corner2.y - i*dy.getRandomValue());
                LineOfPoints lop2 = new LineOfPoints(this.id,numPoints,style.enable,p1s,p2s);
                lop2.setParamsFromStyle(style);
                lop2.setPoints(minDistance);
                //lop2.setOscillators();
                lPoints.addAll(lop2.points);
                lSets.add(lop2);
            }
            if(symmetryX && symmetryY && i>0){
                println("Symmetry Y");
                PVector p1s = new PVector(corner1.x - i*dx.getRandomValue(), corner1.y - i*dy.getRandomValue());
                PVector p2s = new PVector(corner2.x - i*dx.getRandomValue(), corner2.y - i*dy.getRandomValue());
                LineOfPoints lop2 = new LineOfPoints(this.id,numPoints,style.enable,p1s,p2s);
                lop2.setParamsFromStyle(style);
                lop2.setPoints(minDistance);
                //lop2.setOscillators();
                lPoints.addAll(lop2.points);
                lSets.add(lop2);
            }

        }
    }

    // RINGS
    void createRingsOfPoints(int numTimes, int numPoints, float minDistance, PVector centre,
                             RangFloat minRadius, RangFloat maxRadius, RangFloat radiusVariability, RangFloat radiusStep,
                             RangFloat angle, RangFloat angleStep, RangFloat randomAngle,
                             boolean fibonacci,
                             SetOfPointsStyle style,
                             RangFloat dx, RangFloat dy, RangFloat dr,
                             boolean symmetryX, boolean symmetryY, boolean symmetryR){
        for(int i=0; i<numTimes; i++){
            PVector c1 = new PVector(centre.x + i*dx.getRandomValue(), centre.y + i*dy.getRandomValue());
            RangFloat rMin = minRadius.displaceValues(i*dr.getRandomValue());
            RangFloat rMax = maxRadius.displaceValues(i*dr.getRandomValue());
            //RingOfPoints rop = new RingOfPoints(this.id,numPoints,c1,rMin, rMax, radiusVariability, radiusStep, angle, angleStep, randomAngle, massAtt,massRep,spinAng,enable,collapseable,np2Col,ratioAttRep);
            RingOfPoints rop = new RingOfPoints(this.id,numPoints,style.enable,c1,rMin, rMax, radiusVariability, radiusStep, angle, angleStep, randomAngle);
            rop.setParamsFromStyle(style);
            rop.setPoints(minDistance, fibonacci);
            //rop.setOscillators();
            lPoints.addAll(rop.points);
            lSets.add(rop);
            if(symmetryX && i>0){
                println("Symmetry X");
                PVector c1s = new PVector(centre.x - i*dx.getRandomValue(), centre.y + i*dy.getRandomValue());
                RangFloat rMins = minRadius.displaceValues(i*dr.getRandomValue());
                RangFloat rMaxs = maxRadius.displaceValues(i*dr.getRandomValue());
                RingOfPoints rops = new RingOfPoints(this.id,numPoints,style.enable,c1s,rMins, rMaxs, radiusVariability, radiusStep, angle, angleStep, randomAngle);
                rops.setParamsFromStyle(style);
                rops.setPoints(minDistance, fibonacci);
                //rops.setOscillators();
                lPoints.addAll(rops.points);
                lSets.add(rops);
            }
            if(symmetryY && i>0){
                println("Symmetry Y");
                PVector c1s = new PVector(centre.x + i*dx.getRandomValue(), centre.y - i*dy.getRandomValue());
                RangFloat rMins = minRadius.displaceValues(i*dr.getRandomValue());
                RangFloat rMaxs = maxRadius.displaceValues(i*dr.getRandomValue());
                RingOfPoints rops = new RingOfPoints(this.id,numPoints,style.enable,c1s,rMins, rMaxs, radiusVariability, radiusStep, angle, angleStep, randomAngle);
                rops.setParamsFromStyle(style);
                rops.setPoints(minDistance, fibonacci);
                //rops.setOscillators();
                lPoints.addAll(rops.points);
                lSets.add(rops);
            }
            if(symmetryR && i>0){
                println("Symmetry R");
                PVector c1s = new PVector(centre.x + i*dx.getRandomValue(), centre.y + i*dy.getRandomValue());
                RangFloat rMins = minRadius.displaceValues(-i*dr.getRandomValue());
                RangFloat rMaxs = maxRadius.displaceValues(-i*dr.getRandomValue());
                RingOfPoints rops = new RingOfPoints(this.id,numPoints,style.enable,c1s,rMins, rMaxs, radiusVariability, radiusStep, angle, angleStep, randomAngle);
                rops.setParamsFromStyle(style);
                rops.setPoints(minDistance, fibonacci);
                //rops.setOscillators();
                lPoints.addAll(rops.points);
                lSets.add(rops);
            }
        }
    }

    // GRIDS
    void createGridsOfPoints(int numTimes, float minDistance, PVector corner1, PVector corner2, int numRows, int numCols,
                             RangFloat dx, RangFloat dy, SetOfPointsStyle style,
                             boolean symmetryX, boolean symmetryY){
        int numPoints = numCols*numRows;
        for(int i=0; i<numTimes; i++){
            PVector p1 = new PVector(corner1.x + i*dx.getRandomValue(), corner1.y + i*dy.getRandomValue());
            PVector p2 = new PVector(corner2.x + i*dx.getRandomValue(), corner2.y + i*dy.getRandomValue());
            GridOfPoints gop = new GridOfPoints(this.id, numPoints, style.enable,corner1, corner2, numRows, numCols);
            gop.setParamsFromStyle(style);
            gop.setPoints(minDistance);
            //gop.setOscillators();
            lPoints.addAll(gop.points);
            lSets.add(gop);

            // REPEAT WITH SYMMETRY
            if(symmetryX && i>0){
                println("Symmetry X");
                PVector p1s = new PVector(corner1.x - i*dx.getRandomValue(), corner1.y + i*dy.getRandomValue());
                PVector p2s = new PVector(corner2.x - i*dx.getRandomValue(), corner2.y + i*dy.getRandomValue());
                GridOfPoints gop2 = new GridOfPoints(this.id,numPoints,style.enable,p1s,p2s, numRows, numCols);
                gop2.setParamsFromStyle(style);
                gop2.setPoints(minDistance);
                //gop2.setOscillators();
                lPoints.addAll(gop2.points);
                lSets.add(gop2);
            }
            else if(symmetryY && i>0){
                println("Symmetry Y");
                PVector p1s = new PVector(corner1.x + i*dx.getRandomValue(), corner1.y - i*dy.getRandomValue());
                PVector p2s = new PVector(corner2.x + i*dx.getRandomValue(), corner2.y - i*dy.getRandomValue());
                GridOfPoints gop2 = new GridOfPoints(this.id,numPoints,style.enable,p1s,p2s, numRows, numCols);
                gop2.setParamsFromStyle(style);
                gop2.setPoints(minDistance);
                //gop2.setOscillators();
                lPoints.addAll(gop2.points);
                lSets.add(gop2);
            }
        }
    }



    // DELETE POINTS

    void deletePointOn(PVector p, float distance){
        ArrayList<AttractorPoint> lP = this.lPoints;
        for(int i=0; i<lP.size(); i++){
            AttractorPoint ap = lP.get(i);
            if(dist(p.x, p.y, ap.position.x, ap.position.y)<distance+abs(ap.gravity/4)){
                lP.remove(i);
                Defaults.resetBackground = true;
            }
        }
        ArrayList<SetOfPoints> lS  = this.lSets;
        for(int j=0; j<lS.size(); j++){
            ArrayList<AttractorPoint> lP2 = lS.get(j).points;
            for(int k=0; k<lP2.size(); k++){
                AttractorPoint ap = lP2.get(k);
                if(dist(p.x, p.y, ap.position.x, ap.position.y)<distance+abs(ap.gravity/4)){
                    lP2.remove(k);
                    Defaults.resetBackground = true;
                }
            }
        }
    }

    // SPIRALS
    void createSpiralOfPoints(int numTimes, int numPoints, PVector centre, float minDistance,
                              SetOfPointsStyle style, boolean invertRadius, boolean invertAngle,
                              RangFloat minRadius, RangFloat maxRadius, RangFloat radiusStep, RangFloat radiusVariability,
                              RangFloat angle, RangFloat angleStep, RangFloat randomAngle,
                              RangFloat dx, RangFloat dy, RangFloat da, RangFloat dr,
                              boolean symmetryX, boolean symmetryY){
        // encara no esta fet
        for(int i=0; i<numTimes; i++){
            PVector c = new PVector(centre.x + i*dx.getRandomValue(), centre.y + i*dy.getRandomValue());
            RangFloat angle0 = angle.displaceValues(i*da.getRandomValue());
            RangFloat rMin = minRadius.displaceValues(i*dr.getRandomValue());
            RangFloat rMax = maxRadius.displaceValues(i*dr.getRandomValue());
            SpiralOfPoints sop = new SpiralOfPoints(this.id,numPoints, style.enable, c, rMin, rMax, radiusVariability, radiusStep, angle0, angleStep, randomAngle);
            sop.setParamsFromStyle(style);
            sop.setPoints(minDistance, invertRadius, invertAngle);
            //sop.setOscillators();
            lPoints.addAll(sop.points);
            lSets.add(sop);
            // REPEAT WITH SYMMETRY
            if(symmetryX && i>0){
                println("Symmetry X");
                PVector c1s = new PVector(centre.x - i*dx.getRandomValue(), centre.y + i*dy.getRandomValue());
                RangFloat angle0s = angle.displaceValues(i*da.getRandomValue());
                RangFloat rMin0s = minRadius.displaceValues(i*dr.getRandomValue());
                RangFloat rMax0s = maxRadius.displaceValues(i*dr.getRandomValue());
                SpiralOfPoints sop2 = new SpiralOfPoints(this.id,numPoints, style.enable, c1s, rMin0s, rMax0s, radiusVariability, radiusStep, angle0s, angleStep, randomAngle);
                sop.setParamsFromStyle(style);
                sop.setPoints(minDistance, invertRadius, invertAngle);
                //sop2.setOscillators();
                lPoints.addAll(sop2.points);
                lSets.add(sop2);
            }
            else if(symmetryY && i>0){
                println("Symmetry Y");
                PVector c1s = new PVector(centre.x + i*dx.getRandomValue(), centre.y - i*dy.getRandomValue());
                RangFloat angle0s = angle.displaceValues(i*da.getRandomValue());
                RangFloat rMin0s = minRadius.displaceValues(i*dr.getRandomValue());
                RangFloat rMax0s = maxRadius.displaceValues(i*dr.getRandomValue());
                SpiralOfPoints sop2 = new SpiralOfPoints(this.id,numPoints, style.enable, c1s, rMin0s, rMax0s, radiusVariability, radiusStep, angle0s, angleStep, randomAngle);
                sop.setParamsFromStyle(style);
                sop.setPoints(minDistance, invertRadius, invertAngle);
                //sop2.setOscillators();
                lPoints.addAll(sop2.points);
                lSets.add(sop2);
            }
        }

    }

    // WAVES
    void createWavesOfPoints(int numTimes, int numPoints, PVector corner1, PVector corner2, float minDistance,
                             SetOfPointsStyle style,
                             RangFloat amplitud, RangFloat xStep, boolean ampAsc,
                             RangFloat angle, RangFloat angleStep, RangFloat randomAngle,
                             RangFloat dx, RangFloat dy, RangFloat da,
                             boolean symmetryX, boolean symmetryY){
        for(int i=0; i<numTimes; i++){
            PVector p1 = new PVector(corner1.x + i*dx.getRandomValue(), corner1.y + i*dy.getRandomValue());
            PVector p2 = new PVector(corner2.x + i*dx.getRandomValue(), corner2.y + i*dy.getRandomValue());
            RangFloat angle0 = angle.displaceValues(i*da.getRandomValue());
            WaveOfPoints wop = new WaveOfPoints(this.id,numPoints,style.enable, p1, p2, amplitud, xStep, angle0, angleStep, randomAngle);
            wop.setParamsFromStyle(style);
            wop.setPoints(minDistance, ampAsc);
            //wop.setOscillators();
            lPoints.addAll(wop.points);
            lSets.add(wop);
            // REPEAT WITH SYMMETRY
            if(symmetryX && i>0){
                println("Symmetry X");
                PVector p1s = new PVector(corner1.x - i*dx.getRandomValue(), corner1.y + i*dy.getRandomValue());
                PVector p2s = new PVector(corner2.x - i*dx.getRandomValue(), corner2.y + i*dy.getRandomValue());
                RangFloat angle0s = angle.displaceValues(i*da.getRandomValue());
                WaveOfPoints wop2 = new WaveOfPoints(this.id,numPoints,style.enable, p1s, p2s, amplitud, xStep, angle0s, angleStep, randomAngle);
                wop2.setParamsFromStyle(style);
                wop2.setPoints(minDistance, ampAsc);
                //wop2.setOscillators();
                lPoints.addAll(wop2.points);
                lSets.add(wop2);
            }
            else if(symmetryY && i>0){
                println("Symmetry Y");
                PVector p1s = new PVector(corner1.x + i*dx.getRandomValue(), corner1.y - i*dy.getRandomValue());
                PVector p2s = new PVector(corner2.x + i*dx.getRandomValue(), corner2.y - i*dy.getRandomValue());
                RangFloat angle0s = angle.displaceValues(i*da.getRandomValue());
                WaveOfPoints wop2 = new WaveOfPoints(this.id,numPoints,style.enable, p1s, p2s, amplitud, xStep, angle0s, angleStep, randomAngle);
                wop2.setParamsFromStyle(style);
                wop2.setPoints(minDistance, ampAsc);
                //wop2.setOscillators();
                lPoints.addAll(wop2.points);
                lSets.add(wop2);
            }
        }
    }

    // WHITNEY
    void createWhitneyOfPoints(int numTimes, int numPoints, PVector centre, float minDistance,
                               float wMagnify, float wPhase, float wAmplitude,
                               SetOfPointsStyle style,
                               RangFloat dx, RangFloat dy){
        for(int i=0; i<numTimes; i++){
            PVector c = new PVector(centre.x + i*dx.getRandomValue(), centre.y + i*dy.getRandomValue());
            WhitneyOfPoints wop = new WhitneyOfPoints(this.id, numPoints, style.enable, c);
            wop.setParamsFromStyle(style);
            wop.setWhitneyParams(wMagnify, wPhase, wAmplitude, numPoints);
            wop.setPoints(minDistance);
            //wop.setOscillators();
            lPoints.addAll(wop.points);
            lSets.add(wop);
        }
    }

    // FORMULAS
    void createFormulaOfPoints(int numTimes, int numPoints, PVector centre, float minDistance,
                               SetOfPointsStyle style,
                               float sizeF, float stepF,
                               float n1, float n2, float n3, float m, float laps,
                               RangFloat dx, RangFloat dy){
        for(int i=0; i<numTimes; i++){
            PVector c = new PVector(centre.x + i*dx.getRandomValue(), centre.y + i*dy.getRandomValue());
            FormulaOfPoints fop = new FormulaOfPoints(this.id, numPoints, style.enable, c);
            fop.setParamsFromStyle(style);
            fop.setScaleParams(sizeF, stepF);
            println("SET sizeF: "+sizeF+", StepF:"+stepF);
            fop.setFormulaParams(n1, n2, n3, (int)m, (int)laps, numPoints);
            println("SET n1: "+n1+", n2:"+n2+", n3:"+n3+", m:"+m+", laps:"+laps+" numPoints:"+numPoints);
            fop.setPoints(minDistance);  // No funciona!!!
            //fop.setOscillators();
            lPoints.addAll(fop.points);
            lSets.add(fop);
        }
    }

    void createBezierOfPoints(int numTimes, int numPoints, float minDistance, ArrayList<BezierCurve> beziers,
                              SetOfPointsStyle style,
                              RangFloat dx, RangFloat dy , RangFloat dr,
                              boolean symmetryX, boolean symmetryY){

        for(int t=0; t<numTimes; t++){
            float ddr = t*dr.getMaxValue();
            ArrayList<BezierCurve> dbcs = Defaults.dilateBeziers(beziers, ddr);
            ArrayList<PVector> pts = Defaults.getBezierPoints(dbcs, numPoints);

            BezierOfPoints bop = new BezierOfPoints(this.id, pts.size(), style.enable);
            bop.setBezierCurves(beziers);
            bop.setParamsFromStyle(style);
            float ddx = t*dx.getMaxValue();
            float ddy = t*dy.getMaxValue();

            bop.setPoints(minDistance, pts, ddx, ddy);
            //bop.setOscillators();
            lPoints.addAll(bop.points);
            lSets.add(bop);

            if(symmetryX && t>0){
                println("Symmetry X");
                BezierOfPoints bop2 = new BezierOfPoints(this.id, pts.size(),style.enable);
                bop2.setBezierCurves(beziers);
                bop2.setParamsFromStyle(style);
                float ddx2 = -t*dx.getMaxValue();
                float ddy2 = t*dy.getMaxValue();
                bop2.setPoints(minDistance,pts, ddx2, ddy2);
                //bop2.setOscillators();
                lPoints.addAll(bop2.points);
                lSets.add(bop2);
            }
            if(symmetryY && t>0){
                println("Symmetry Y");
                BezierOfPoints bop2 = new BezierOfPoints(this.id, pts.size(), style.enable);
                bop2.setBezierCurves(beziers);
                bop2.setParamsFromStyle(style);
                float ddx2 = t*dx.getMaxValue();
                float ddy2 = -t*dy.getMaxValue();
                bop2.setPoints(minDistance,pts, ddx2, ddy2);
                //bop2.setOscillators();
                lPoints.addAll(bop2.points);
                lSets.add(bop2);
            }
            if(symmetryX  && symmetryY && t>0){
                println("Symmetry Y");
                BezierOfPoints bop2 = new BezierOfPoints(this.id, pts.size(), style.enable);
                bop2.setParamsFromStyle(style);
                float ddx2 = -t*dx.getMaxValue();
                float ddy2 = -t*dy.getMaxValue();
                bop2.setPoints(minDistance, pts, ddx2, ddy2);
                //bop2.setOscillators();
                lPoints.addAll(bop2.points);
                lSets.add(bop2);
            }
        }
    }

    //POISSONS
    void createPoissonOfPoints(int numTimes, int poissonOption, float minDistance,
                               SetOfPointsStyle style,
                               PoissonRect pr, PoissonCircle pc, PoissonPolygon pp,
                               RangFloat dx, RangFloat dy,
                               boolean symmetryX, boolean symmetryY){

        ArrayList<PVector> poissons;

        switch(poissonOption){
            case 0: poissons = pr.getPoints(); break; // RECT
            case 1: poissons = pc.getPoints(); break; //CIRCLE
            case 2: poissons = pp.getPoints(); break; //CIRCLE
            default : poissons = new ArrayList<PVector>();
        }

        for(int t=0; t<numTimes; t++){
            PoissonOfPoints pop = new PoissonOfPoints(this.id, poissons.size(), style.enable);
            pop.setParamsFromStyle(style);
            float ddx = t*dx.getMaxValue();
            float ddy = t*dy.getMaxValue();
            pop.setPoints(minDistance,poissons, ddx, ddy);
            //pop.setOscillators();
            lPoints.addAll(pop.points);
            lSets.add(pop);

            if(symmetryX && t>0){
                println("Symmetry X");
                PoissonOfPoints pop2 = new PoissonOfPoints(this.id, poissons.size(), style.enable);
                pop2.setParamsFromStyle(style);
                float ddx2 = -t*dx.getMaxValue();
                float ddy2 = t*dy.getMaxValue();
                pop2.setPoints(minDistance, poissons, ddx2, ddy2);
                //pop2.setOscillators();
                lPoints.addAll(pop2.points);
                lSets.add(pop2);
            }
            else if(symmetryY && t>0){
                println("Symmetry Y");
                PoissonOfPoints pop2 = new PoissonOfPoints(this.id, poissons.size(), style.enable);
                pop2.setParamsFromStyle(style);
                float ddx2 = t*dx.getMaxValue();
                float ddy2 = -t*dy.getMaxValue();
                pop2.setPoints(minDistance, poissons, ddx2, ddy2);
                //pop2.setOscillators();
                lPoints.addAll(pop2.points);
                lSets.add(pop2);
            }
        }

    }

    // TEXTS
    void createTextsOfPoints(int numTimes, int numPoints, float minDistance,
                             PVector centre, String word, String fontFamily, float fontSize,
                             float fontAlignX, float fontAlignY, float polyMode,
                             float numPolyMode, float polyLength, float polyStep, float polyAngle,
                             float adaptorScale, float adaptorOffset,
                             RangFloat rotAngle, boolean letterCenter,
                             SetOfPointsStyle style,
                             RangFloat dx, RangFloat dy){
        for(int i=0; i<numTimes; i++){
            PVector c = new PVector(centre.x + i*dx.getRandomValue(), centre.y + i*dy.getRandomValue());
            TextOfPoints top = new TextOfPoints(this.id,numPoints, style.enable, c, word, fontFamily, fontSize);
            top.setParamsFromStyle(style);
            top.setFontAlign((int)fontAlignX, (int)fontAlignY);
            top.setPolygonizerParams((int)polyMode, polyLength, polyStep, polyAngle);
            top.setAdaptorParams(adaptorScale, adaptorOffset);
            top.setAdaptor(numPolyMode<3);
            top.setRotationParams(rotAngle, letterCenter);
            top.setPoints(minDistance);
            //top.setOscillators();
            lPoints.addAll(top.points);
            lSets.add(top);
        }
    }

    // IMAGES
    void createImagesOfPoints(PApplet pA,int numTimes, int numPoints,
                              PVector corner1, PVector corner2, int numRows, int numCols,
                              String pathImage,
                              RangFloat thresholdAtt, RangFloat thresholdRep, float threshold, boolean borderMode,
                              boolean ascAtt, boolean ascRep,
                              SetOfPointsStyle style,
                              RangFloat dx, RangFloat dy){
        for(int i=0; i<numTimes; i++){
            PVector p1 = new PVector(corner1.x + i*dx.getRandomValue(), corner1.y + i*dy.getRandomValue());
            PVector p2 = new PVector(corner2.x + i*dx.getRandomValue(), corner2.y + i*dy.getRandomValue());
            ImageOfPoints iop = new ImageOfPoints(this.id,numPoints,style.enable,pathImage,p1,p2, numRows, numCols);
            iop.setParamsFromStyle(style);
            iop.setThresholdParams(thresholdAtt, thresholdRep, threshold, borderMode);
            iop.setPoints(pA, ascAtt, ascRep);
            //iop.setOscillators();
            lPoints.addAll(iop.points);
            lSets.add(iop);
        }
    }

    void createDLAOfPoints(int nt, RangFloat dx, RangFloat dy){
    }


}