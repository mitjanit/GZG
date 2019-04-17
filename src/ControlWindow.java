import controlP5.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class ControlWindow extends PApplet {

    public ControlP5 cp5;
    String wTitle;
    int wWidth, wHeight;

    int bgcolor = color(100);
    float marginLeft = 20, marginTop = 40;
    float rowStep = 30, colStep = 360;
    int rangeWidth = 300, controlHeight = 20;

    String[] tabNames = {"default", "random", "poisson", "line", "polyline", "bezier", "polygon", "grid", "circle", "spiral", "wave", "formula", "whitney", "dla", "text", "image", "particles"};
    String activeTab = "default";
    String[] commonControls = {"LAYER", "POINTS", "ENABLE", "RESET", "PREVIEW", "MASS ATT", "MASS REP", "RATIO", "SPIN ANGLE", "NP COLLAPSE", "COLLAPSEABLE", "CREATE", "MAP MASS ATT", "MAP MASS REP", "MAP SPIN ANGLE", "MAP NP COLLAPSE", "MASS ATT IN", "MASS REP IN", "SPIN ANGLE IN", "NP COLLAPSE IN", "REF COLOR", "TIMES", "OFFSET X", "OFFSET Y", "OFFSET R", "OFFSET A", "SYMMETRY X", "SYMMETRY Y", "SYMMETRY R", "OSC MODE", "OSC MIN", "OSC MAX", "OSC INIT", "OSC STEP", "OSC FRAMES", "OSC TIMES", "OSC DELAY", "ABSOLUTE", "OSC DIR", "OSC ON", "OSCILLATE"};
    String[] particlesTabControls = {"LAYER", "POINTS", "ENABLE", "RESET", "PREVIEW", "CREATE", "COLS", "ROWS", "TL CORNER", "BR CORNER", "X VAR", "Y VAR", "LOCK X", "LOCK Y", "SAME X", "SAME Y", "LOCK X2", "LOCK Y2", "CENTER TL", "CENTER BR", "NUM VERTEXS"};

    ControlDefaults cDefaults;
    ControlCommon cCommons;
    ControlRepeat cRepeat;
    ControlMouse cMouse;
    ControlCircle cCircle;
    ControlGrid cGrid;
    ControlWhitney cWhitney;
    ControlFormula cFormula;
    ControlText cText;
    ControlOscillation cOscillation;
    ControlPoisson cPoisson;
    ControlParticles cParticles;
    ControlRandom cRandom;
    ControlLine cLine;
    ControlPolyLine cPolyline;
    ControlBezier cBezier;
    ControlImage cImage;
    ControlWave cWave;
    ControlSpiral cSpiral;
    ControlFlow cFlow;

    public ControlWindow(String t, int w, int h) {
        super();
        this.wWidth = w;
        this.wHeight = h;
        this.wTitle = t;
        PApplet.runSketch(new String[]{this.getClass().getSimpleName()}, this);
        this.surface.setTitle(t);

    }

    public void settings() {
        size(wWidth, wHeight);
    }

    public void setup() {
        cp5 = new ControlP5(this);
        //styleGUI();
        setupGUI();
    }

    public void draw() {
        background(bgcolor);
    }

    void styleGUI() {
        int c1 = color(255, 163, 26), c2 = color(179, 255, 102), c3 = color(89, 179, 0);
        cp5.setColorForeground(c2);
        cp5.setColorBackground(c1);
        cp5.setColorActive(c3);
    }

    void setupGUI() {

        // KEYS
        //cp5.mapKeyFor(new ControlKey() {public void keyEvent() {resetControls();}}, ALT,'R');

        //TABS
        for (int i = 0; i < tabNames.length; i++) {
            cp5.addTab(tabNames[i]).setWidth(1200 / tabNames.length).setHeight((int) (controlHeight * 1.5));
            cp5.getTab(tabNames[i]).activateEvent(true).setLabel(tabNames[i]).setId(i + 1);
        }

        //CONTROLS & VARS
        cDefaults = new ControlDefaults(this);
        cCommons = new ControlCommon(this);
        cRepeat = new ControlRepeat(this);
        cMouse = new ControlMouse(this);
        cCircle = new ControlCircle(this);
        cSpiral = new ControlSpiral(this);
        cFormula = new ControlFormula(this);
        cWhitney = new ControlWhitney(this);
        cText = new ControlText(this);
        cOscillation = new ControlOscillation(this);
        cPoisson = new ControlPoisson(this);
        cParticles = new ControlParticles(this);
        cRandom = new ControlRandom(this);
        cLine = new ControlLine(this);
        cPolyline = new ControlPolyLine(this);
        cBezier = new ControlBezier(this);
        cImage = new ControlImage(this);
        cWave = new ControlWave(this);
        cGrid = new ControlGrid(this);
        cFlow = new ControlFlow(this);
    }

    public void resetControls() {
        cCommons.resetControls();
        cRepeat.resetControls();
        cDefaults.resetControls();
        cRandom.resetControls();
        cLine.resetControls();
        cBezier.resetControls();
        cMouse.resetControls();
        cCircle.resetControls();
        cSpiral.resetControls();
        cGrid.resetControls();
        cFormula.resetControls();
        cWhitney.resetControls();
        cText.resetControls();
        cOscillation.resetControls();
        cPoisson.resetControls();
        cImage.resetControls();
        cParticles.resetControls();
        cWave.resetControls();
        cFlow.resetControls();
    }

    public void controlEvent(ControlEvent theControlEvent) {

        this.checkTabControlEvents(theControlEvent);

        if (frameCount > 10) {

            checkAllControls(theControlEvent);

            // *******************************  CREATE & PREVIEW  *******************************//
            if (theControlEvent.isFrom("CREATE")) {
                Layer l = Main.layers.get(cCommons.layer);
                //creatingPoints = true;
                switch (activeTab) {
                    case "line":
                        createLineOfPoints(l);
                        break;
                    case "random":
                        createAreaOfPoints(this, l);
                        break;
                    case "polygon":
                        createPolygonOfPoints(l);
                        break;
                    case "polyline":
                        createPolyLineOfPoints(l);
                        break;
                    case "bezier":
                        createBezierOfPoints(l);
                        break;
                    case "poisson":
                        createPoissonOfPoints(l);
                        break;
                    case "circle":
                        createRingOfPoints(l);
                        break;
                    case "spiral":
                        createSpiralOfPoints(l);
                        break;
                    case "grid":
                        createGridOfPoints(l);
                        break;
                    case "wave":
                        createWaveOfPoints(l);
                        break;
                    case "formula":
                        createFormulaOfPoints(l);
                        break;
                    case "whitney":
                        createWhitneyOfPoints(l);
                        break;
                    case "dla":
                        createDLAOfPoints(l);
                        break;
                    case "text":
                        createTextOfPoints(l);
                        break;
                    case "image":
                        createImageOfPoints(this, l);
                        break;
                    case "particles":
                        createParticles(l, this);
                        break;
                    default:
                        println("CREATE WHAT ???");
                }
                //creatingPoints = false;
                //oscGroup.clear();
            } else if (theControlEvent.isFrom("PREVIEW")) {
                if(cCommons.preview){ cCommons.bPreview.setLabel("PREVIEW ON"); }
                else {
                    cCommons.bPreview.setLabel("PREVIEW OFF");
                    cDefaults.resetBackground = true;
                }
                println("PREVIEW: "+cCommons.preview);
            }
        }
    }

    //************** TABS ************//
    public void checkTabControlEvents(ControlEvent theControlEvent) {
        if (theControlEvent.isTab()) {
            // SHARED CONTROLS BETWEEN TABS
            String tabName = theControlEvent.getTab().getName();
            activeTab = tabName;
            println("ACTIVE TAB: " + activeTab);
            if ((tabName != "default") && (tabName != "particles")) {
                // COMMON CONTROLS
                for (int i = 0; i < commonControls.length; i++) {
                    println(commonControls[i]);
                    cp5.getController(commonControls[i]).moveTo(tabName);
                }
            }
            if (tabName == "particles") {
                for (int i = 0; i < particlesTabControls.length; i++) {
                    println(particlesTabControls[i]);
                    cp5.getController(particlesTabControls[i]).moveTo(tabName);
                }

                cp5.getController("TL CORNER").moveTo("particles");
                cp5.getController("BR CORNER").moveTo("particles");
                cp5.getController("CENTER TL").moveTo("particles");
                cp5.getController("CENTER BR").moveTo("particles");
                cp5.getController("COLS").moveTo("particles");
                cp5.getController("ROWS").moveTo("particles");
                cp5.getController("LOCK X").moveTo("particles");
                cp5.getController("LOCK X2").moveTo("particles");
                cp5.getController("LOCK Y").moveTo("particles");
                cp5.getController("LOCK Y2").moveTo("particles");
                cp5.getController("SAME X").moveTo("particles");
                cp5.getController("SAME Y").moveTo("particles");
                cp5.getController("REF COLOR").moveTo("particles");
                cp5.getController("REF COLOR").setPosition(marginLeft + rangeWidth + colStep / 4, marginTop + rowStep * 13);
                cp5.getController("REF COLOR").setLabel("REF COLOR");
                cp5.getController("NUM VERTEXS").setPosition(marginLeft, marginTop + rowStep * 7);

                //Llevar
                cp5.getController("CENTRE").moveTo("circle");
                cp5.getController("MIN RADIUS").moveTo("circle");
                cp5.getController("MAX RADIUS").moveTo("circle");
                cp5.getController("VAR RADIUS").moveTo("circle");
                cp5.getController("RADIUS STEP").moveTo("circle");
                cp5.getController("ANGLE").moveTo("circle");
                cp5.getController("ANGLE").setPosition(marginLeft, marginTop + rowStep * 23);
                cp5.getController("ANGLE STEP").moveTo("circle");
                cp5.getController("ANGLE STEP").setPosition(marginLeft, marginTop + rowStep * 24);
                cp5.getController("RANDOM ANGLE").moveTo("circle");
                cp5.getController("MIN DIST").moveTo("circle");
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 26);
                cp5.getController("FULL").moveTo("circle");
                cp5.getController("HALF B").moveTo("circle");
                cp5.getController("HALF T").moveTo("circle");
                cp5.getController("HALF R").moveTo("circle");
                cp5.getController("HALF L").moveTo("circle");
                cp5.getController("ORIGIN").moveTo("circle");
                cp5.getController("SINGLE").moveTo("circle");
                cp5.getController("PARTICLE DISTRIBUTION").moveTo("particles");
                // ORDER
                cp5.getController("MAP HEIGHT").bringToFront();
                cp5.getController("MAP WIDTH").bringToFront();
                cp5.getController("MAP OPAC").bringToFront();
                cp5.getController("MAP BLUE").bringToFront();
                cp5.getController("MAP GREEN").bringToFront();
                cp5.getController("MAP RED").bringToFront();
                cp5.getController("VAR HEIGHT").bringToFront();
                cp5.getController("VAR WIDTH").bringToFront();
                cp5.getController("VAR OPAC").bringToFront();
                cp5.getController("VAR BLUE").bringToFront();
                cp5.getController("VAR GREEN").bringToFront();
                cp5.getController("VAR RED").bringToFront();
            }
            if ((tabName == "line") || (tabName == "random") || (tabName == "wave")) {
                cp5.getController("MIN DIST").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 8);
            }
            if ((tabName == "wave")) {
                cp5.getController("ANGLE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ANGLE").setPosition(marginLeft, marginTop + rowStep * 29);
                cp5.getController("ANGLE STEP").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ANGLE STEP").setPosition(marginLeft, marginTop + rowStep * 30);
            }
            if ((tabName == "circle") || (tabName == "spiral")) {
                cp5.getController("CENTRE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("X VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("Y VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN RADIUS").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MAX RADIUS").moveTo(theControlEvent.getTab().getName());
                cp5.getController("VAR RADIUS").moveTo(theControlEvent.getTab().getName());
                cp5.getController("RADIUS STEP").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ANGLE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ANGLE").setPosition(marginLeft, marginTop + rowStep * 23);
                cp5.getController("ANGLE STEP").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ANGLE STEP").setPosition(marginLeft, marginTop + rowStep * 24);
                cp5.getController("RANDOM ANGLE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("RANDOM ANGLE").setPosition(marginLeft, marginTop + rowStep * 25);
                cp5.getController("MIN DIST").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 8);
                cp5.getController("FULL").moveTo(theControlEvent.getTab().getName());
                cp5.getController("HALF T").moveTo(theControlEvent.getTab().getName());
                cp5.getController("HALF B").moveTo(theControlEvent.getTab().getName());
                cp5.getController("HALF R").moveTo(theControlEvent.getTab().getName());
                cp5.getController("HALF L").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ORIGIN").moveTo(theControlEvent.getTab().getName());
                cp5.getController("SINGLE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("INVERT RADIUS").moveTo(theControlEvent.getTab().getName());
                cp5.getController("INVERT ANGLE").moveTo(theControlEvent.getTab().getName());
            }
            if ((tabName == "line") || (tabName == "grid") || (tabName == "wave") || (tabName == "image")) {
                cp5.getController("TL CORNER").moveTo(theControlEvent.getTab().getName());
                cp5.getController("BR CORNER").moveTo(theControlEvent.getTab().getName());
                cp5.getController("CENTER TL").moveTo(theControlEvent.getTab().getName());
                cp5.getController("CENTER BR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("X VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("Y VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X2").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y2").moveTo(theControlEvent.getTab().getName());
                cp5.getController("SAME X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("SAME Y").moveTo(theControlEvent.getTab().getName());

                if ((tabName == "image") || (tabName == "grid")) {
                    cp5.getController("COLS").moveTo(theControlEvent.getTab().getName());
                    cp5.getController("ROWS").moveTo(theControlEvent.getTab().getName());
                }
            }
            if ((tabName == "text")) {
                cp5.getController("WORD").moveTo(theControlEvent.getTab().getName());
                cp5.getController("FONT SIZE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("CENTRE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("X VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("Y VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ORIGIN").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 8);
            }
            if ((tabName == "formula")) {
                cp5.getController("M").moveTo(theControlEvent.getTab().getName());
                cp5.getController("N1").moveTo(theControlEvent.getTab().getName());
                cp5.getController("N2").moveTo(theControlEvent.getTab().getName());
                cp5.getController("N3").moveTo(theControlEvent.getTab().getName());
                cp5.getController("SIZE F").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LAPS F").moveTo(theControlEvent.getTab().getName());
                cp5.getController("STEP F").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 8);
                cp5.getController("CENTRE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("X VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("Y VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ORIGIN").moveTo(theControlEvent.getTab().getName());


            }
            if ((tabName == "whitney")) {
                cp5.getController("W MAGNIFY").moveTo(theControlEvent.getTab().getName());
                cp5.getController("W PHASE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("W AMPLITUDE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 8);
                cp5.getController("CENTRE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("X VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("Y VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ORIGIN").moveTo(theControlEvent.getTab().getName());


            }
            if (tabName == "poisson") {
                cp5.getController("POISSON DISTRIBUTION").bringToFront();
                cp5.getController("MIN DIST").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 8);
                cp5.getController("TL CORNER").moveTo(theControlEvent.getTab().getName());
                cp5.getController("BR CORNER").moveTo(theControlEvent.getTab().getName());
                cp5.getController("CENTER TL").moveTo(theControlEvent.getTab().getName());
                cp5.getController("CENTER BR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X2").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y2").moveTo(theControlEvent.getTab().getName());
                cp5.getController("SAME X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("SAME Y").moveTo(theControlEvent.getTab().getName());
            }
            if ((tabName == "bezier") || (tabName == "polyline") || (tabName == "dla")) {
                cp5.getController("MIN DIST").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 8);
                cp5.getController("CENTRE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("X VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("Y VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ORIGIN").moveTo(theControlEvent.getTab().getName());
            }
            if ((tabName != "default") && (tabName != "particles")) {
                cp5.getController("POISSON DISTRIBUTION").bringToFront();
                cp5.getController("MAP NP COLLAPSE").bringToFront();
                cp5.getController("MAP SPIN ANGLE").bringToFront();
                cp5.getController("MAP MASS REP").bringToFront();
                cp5.getController("MAP MASS ATT").bringToFront();
                cp5.getController("REF COLOR").setPosition(marginLeft + rangeWidth + colStep / 4, marginTop + rowStep * 10);
                cp5.getController("REF COLOR").setLabel("REF");
            }
            if (tabName == "polygon") {
                cp5.getController("MIN DIST").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MIN DIST").setPosition(marginLeft, marginTop + rowStep * 8);
                cp5.getController("CENTRE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("X VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("Y VAR").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK X").moveTo(theControlEvent.getTab().getName());
                cp5.getController("LOCK Y").moveTo(theControlEvent.getTab().getName());
                cp5.getController("ORIGIN").moveTo(theControlEvent.getTab().getName());
                cp5.getController("MAX RADIUS").moveTo(theControlEvent.getTab().getName());
                cp5.getController("NUM VERTEXS").moveTo(theControlEvent.getTab().getName());
                cp5.getController("NUM VERTEXS").setPosition(marginLeft, marginTop + rowStep * 20);
                cp5.getController("RANDOM ANGLE").moveTo(theControlEvent.getTab().getName());
                cp5.getController("RANDOM ANGLE").setPosition(marginLeft, marginTop + rowStep * 21);
            }

        }
    }

    public void checkAllControls(ControlEvent theControlEvent) {
        cRandom.checkControlEvents(theControlEvent);
        cLine.checkControlEvents(theControlEvent);
        cPolyline.checkControlEvents(theControlEvent, this);
        cBezier.checkControlEvents(theControlEvent, this);
        cMouse.checkControlEvents(theControlEvent);
        cRepeat.checkControlEvents(theControlEvent);
        cDefaults.checkControlEvents(this, theControlEvent);
        cCommons.checkControlEvents(theControlEvent);
        cCircle.checkControlEvents(theControlEvent);
        cSpiral.checkControlEvents(theControlEvent);
        cGrid.checkControlEvents(theControlEvent);
        cFormula.checkControlEvents(theControlEvent);
        cWhitney.checkControlEvents(theControlEvent);
        cText.checkControlEvents(theControlEvent);
        cOscillation.checkControlEvents(theControlEvent);
        cPoisson.checkControlEvents(theControlEvent, this);
        cImage.checkControlEvents(theControlEvent);
        cParticles.checkControlEvents(theControlEvent, this);
        cParticles.checkColorControlEvents(theControlEvent, this);
        cWave.checkControlEvents(theControlEvent);
        //cOscillation.checkControlEvents(theControlEvent);
        //cFlow.checkControlEvents(theControlEvent);
        cMouse.checkControlEvents(theControlEvent);
    }

    //*************** CREATORS *********************//

    public void createLineOfPoints(Layer l) {
        // Collect data from Controls
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE LINE OF POINTS ON LAYER " + l.id + ".");
        l.createLineOfPoints(nTimes, nPoints, minD, c1, c2, style, dx, dy, symX, symY);
    }

    public void createAreaOfPoints(ControlWindow controls, Layer l) {
        // Collect data from Controls
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        RangFloat xr = cRandom.xRange.copy();
        RangFloat yr = cRandom.yRange.copy();
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        boolean cloneArea = cRepeat.cloneArea;
        boolean mirrorX = cRandom.mirrorX;
        boolean mirrorY = cRandom.mirrorY;
        boolean quadSim = cRandom.quadSim;
        boolean hexaSim = cRandom.hexaSim;
        boolean clone = cRandom.clone;
        boolean shuffle = cRandom.shuffle;
        boolean randomize = cRandom.randomize;
        boolean invert = cRandom.invert;
        println("CREATE AREA OF POINTS ON LAYER " + l.id + ".");
        l.createAreasOfPoints(controls,nTimes, nPoints, minD, style, xr, yr, dx, dy, cloneArea, symX, symY, mirrorX, mirrorY, quadSim, hexaSim, clone, shuffle, randomize, invert);
    }

    public void createPolygonOfPoints(Layer l) {
        // Collect data from Controls
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        int nVertexs = cParticles.numVertexos;
        float minD = cCircle.minDistance;
        PVector centre = cCircle.centre.copy();
        RangFloat maxR = cCircle.maxRadius.copy();
        RangFloat randA = cCircle.randomAngle.copy();
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        RangFloat dr = cRepeat.displaceR.copy();
        RangFloat da = cRepeat.displaceA.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE POLYGON OF POINTS ON LAYER " + l.id + ".");
        l.createPolygonOfPoints(nTimes, nPoints, nVertexs, minD, style, centre, maxR, randA, dx, dy, dr, da, symX, symY);
    }

    public void createPolyLineOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        ArrayList<PVector> pPoints = cPolyline.polylinePoints;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE POLYLINE OF POINTS ON LAYER " + l.id + ".");
        l.createPolyLineOfPoints(nTimes, nPoints, pPoints, minD, style, dx, dy, symX, symY);
    }

    public void createBezierOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        ArrayList<BezierCurve> bs = cBezier.beziers;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        RangFloat dr = cRepeat.displaceR.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE BEZIER OF POINTS ON LAYER " + l.id + ".");
        l.createBezierOfPoints(nTimes, nPoints, minD, bs, style, dx, dy, dr, symX, symY);
        cBezier.beziers.clear();
        cBezier.bAddBezierOnClick.setOff();
        cBezier.bEditBezierOnDrag.setOff();
        cCommons.bPreview.setOff();  // ON ES
    }

    public void createPoissonOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int pOption = cPoisson.poissonOption;
        float minD = cCircle.minDistance;
        PoissonRect pr = cPoisson.pr;
        PoissonCircle pc = cPoisson.pc;
        PoissonPolygon pp = cPoisson.pp;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE POISSON OF POINTS ON LAYER " + l.id + ".");
        l.createPoissonOfPoints(nTimes, pOption, minD, style, pr, pc, pp, dx, dy, symX, symY);
        cPoisson.bCreatePoisson.setOff();
        cCommons.bPreview.setOff();
    }

    public void createRingOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c = cCircle.centre.copy();
        RangFloat minR = cCircle.minRadius.copy();
        RangFloat maxR = cCircle.maxRadius.copy();
        RangFloat rVar = cCircle.radiusVariability.copy();
        RangFloat rStep = cCircle.radiusStep.copy();
        RangFloat angle = cCircle.angle.copy();
        RangFloat aStep = cCircle.angleStep.copy();
        RangFloat rAng = cCircle.randomAngle.copy();
        boolean fibo = cCircle.fibonacci;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        RangFloat dr = cRepeat.displaceR.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        boolean symR = cRepeat.symmetryR;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE RING OF POINTS ON LAYER " + l.id + ".");
        l.createRingsOfPoints(nTimes, nPoints, minD, c, minR, maxR, rVar, rStep, angle, aStep, rAng, fibo, style, dx, dy, dr, symX, symY, symR);
    }

    public void createSpiralOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c = cCircle.centre.copy();
        RangFloat minR = cCircle.minRadius.copy();
        RangFloat maxR = cCircle.maxRadius.copy();
        RangFloat rVar = cCircle.radiusVariability.copy();
        RangFloat rStep = cCircle.radiusStep.copy();
        RangFloat angle = cCircle.angle.copy();
        RangFloat aStep = cCircle.angleStep.copy();
        RangFloat rAng = cCircle.randomAngle.copy();
        boolean invR = cSpiral.invertRadius;
        boolean invA = cSpiral.invertAngle;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        RangFloat dr = cRepeat.displaceR.copy();
        RangFloat da = cRepeat.displaceA.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE SPIRAL OF POINTS ON LAYER " + l.id + ".");
        l.createSpiralOfPoints(nTimes, nPoints, c, minD, style, invR, invA, minR, maxR, rStep, rVar, angle, aStep, rAng, dx, dy, da, dr, symX, symY);
    }

    public void createGridOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        int numR = cGrid.numCols;
        int numC = cGrid.numRows;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE GRID OF POINTS ON LAYER " + l.id + ".");
        l.createGridsOfPoints(nTimes, minD, c1, c2, numR, numC, dx, dy, style, symX, symY);
    }

    public void createWaveOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        RangFloat amp = cWave.amplitud.copy();
        RangFloat xStep = cWave.xStep.copy();
        boolean ampAsc = cWave.ampAsc;
        RangFloat angle = cCircle.angle.copy();
        RangFloat aStep = cCircle.angleStep.copy();
        RangFloat rAng = cCircle.randomAngle.copy();
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        RangFloat da = cRepeat.displaceA.copy();
        boolean symX = cRepeat.symmetryX;
        boolean symY = cRepeat.symmetryY;
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE WAVE OF POINTS ON LAYER " + l.id + ".");
        l.createWavesOfPoints(nTimes, nPoints, c1, c2, minD, style, amp, xStep, ampAsc, angle, aStep, rAng, dx, dy, da, symX, symY);
    }

    public void createFormulaOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c = cCircle.centre.copy();
        float sizeF = cFormula.sizeF;
        float stepF = cFormula.stepF;
        float n1 = cFormula.n1;
        float n2 = cFormula.n2;
        float n3 = cFormula.n3;
        float m = cFormula.m;
        float laps = cFormula.lapsF;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE FORMULA OF POINTS ON LAYER " + l.id + ".");
        l.createFormulaOfPoints(nTimes, nPoints, c, minD, style, sizeF, stepF, n1, n2, n3, m, laps, dx, dy);
    }

    public void createWhitneyOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c = cCircle.centre.copy();
        float magnify = cWhitney.wMagnify;
        float phase = cWhitney.wPhase;
        float amp = cWhitney.wAmplitude;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE WHITNEY OF POINTS ON LAYER " + l.id + ".");
        l.createWhitneyOfPoints(nTimes, nPoints, c, minD, magnify, phase, amp, style, dx, dy);
    }

    public void createDLAOfPoints(Layer l) {
        println("CREATE DLA OF POINTS.");
        //l.createDLAOfPoints(numTimes, displaceX, displaceY);
    }

    public void createTextOfPoints(Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c = cCircle.centre.copy();
        String word = cText.word;
        String font = cText.fontFamily;
        float sizeF = cText.fontSize;
        float fAlignX = cText.fontAlignX;
        float fAlignY = cText.fontAlignY;
        float pMode = cText.polyMode;
        float nPolyMode = cText.numPolyMode;
        float pLength = cText.polyLength;
        float pStep = cText.polyStep;
        float pAngle = cText.polyAngle;
        float aScale = cText.adaptorScale;
        float aOffset = cText.adaptorOffset;
        RangFloat rAngle = cText.rotAngle;
        boolean lCenter = cText.letterCenter;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE TEXT OF POINTS ON LAYER " + l.id + ".");
        l.createTextsOfPoints(nTimes, nPoints, minD, c, word, font, sizeF, fAlignX, fAlignY, pMode,
                nPolyMode, pLength, pStep, pAngle, aScale, aOffset, rAngle, lCenter,
                style, dx, dy);
    }

    public void createImageOfPoints(PApplet pA, Layer l) {
        int nTimes = cRepeat.numTimes;
        int nPoints = cCommons.numPoints;
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        int nR = cGrid.numRows;
        int nC = cGrid.numCols;
        String pathImg = cImage.pathImage;
        RangFloat thresAtt = cImage.thresholdAtt;
        RangFloat thresRep = cImage.thresholdRep;
        float thres = cImage.threshold;
        boolean borderMode = cImage.borderMode;
        boolean ascAtt = cImage.ascAtt;
        boolean ascRep = cImage.ascRep;
        RangFloat dx = cRepeat.displaceX.copy();
        RangFloat dy = cRepeat.displaceY.copy();
        SetOfPointsStyle style = createSetOfPointsStyleFromGUI();
        println("CREATE IMAGE OF POINTS ON LAYER " + l.id + ".");
        // float threshold, boolean borderMode,
        l.createImagesOfPoints(pA, nTimes, nPoints, c1, c2, nR, nC, pathImg, thresAtt, thresRep, thres, borderMode, ascAtt, ascRep, style, dx, dy);
    }

    public void createParticles(Layer l, PApplet pA) {
        print("CREATE PARTICLES: ");
        switch (cParticles.particleDist) {
            case 0:  createRandomParticles(l, pA); break;
            case 1:  createGridOfParticles(l, pA); break;
            case 2:  createFrameOfParticles(l, pA); break;
            case 3:  createRingOfParticles(l, pA); break;
            case 4:  createLineOfParticles(l, pA); break;
            case 5:  createPolygonOfParticles(l, pA); break;
            case 6:  createSpiralOfParticles(l, pA); break;
            case 7:  createWaveOfParticles(l, pA); break;
            default: println("CREATE PARTICLES ?");
        }
    }

    public void createRandomParticles(Layer l, PApplet pA) {
        int numParts = cCommons.numPoints;
        float minD = cCircle.minDistance;
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        AttractedParticleStyle style = createAttractedParticleStyleFromGUI();
        style.randomStyle();
        println("CREATE RANDOM PARTICLES ON LAYER " + l.id + ".");
        l.createAreaOfParticles(pA, numParts, minD, c1, c2, style);
    }

    public void createGridOfParticles(Layer l, PApplet pA) {
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        int nC = cGrid.numCols;
        int nR = cGrid.numRows;
        AttractedParticleStyle style = createAttractedParticleStyleFromGUI();
        style.randomStyle();
        println("CREATE GRID OF PARTICLES ON LAYER " + l.id + ".");
        l.createGridOfParticles(pA, c1, c2, nC, nR, style);
    }

    public void createFrameOfParticles(Layer l, PApplet pA) {
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        int nC = cGrid.numCols;
        int nR = cGrid.numRows;
        float fWidth = cParticles.frameWidth;
        AttractedParticleStyle style = createAttractedParticleStyleFromGUI();
        style.randomStyle();
        println("CREATE FRAME OF PARTICLES ON LAYER " + l.id + ".");
        l.createFrameOfParticles(pA, c1, c2, nC, nR, fWidth, style);
    }

    public void createRingOfParticles(Layer l, PApplet pA){
        PVector c = cCircle.centre.copy();
        RangFloat minR = cCircle.minRadius;
        RangFloat maxR = cCircle.maxRadius;
        RangFloat rVar = cCircle.radiusVariability;
        RangFloat rStep = cCircle.radiusStep;
        RangFloat angle = cCircle.angle;
        RangFloat aStep = cCircle.angleStep;
        RangFloat rAng = cCircle.randomAngle;
        AttractedParticleStyle style = createAttractedParticleStyleFromGUI();
        style.randomStyle();
        println("CREATE RING OF PARTICLES ON LAYER " + l.id + ".");
        l.createRingOfParticles(pA, c, minR, maxR, rVar, rStep, angle, aStep, rAng, style);
    }

    public void createLineOfParticles(Layer l, PApplet pA){
        int numParts = cCommons.numPoints;
        float fWidth = cParticles.frameWidth;
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        AttractedParticleStyle style = createAttractedParticleStyleFromGUI();
        style.randomStyle();
        println("CREATE LINE OF PARTICLES ON LAYER " + l.id + ".");
        l.createLineOfParticles(pA, numParts, fWidth, c1, c2, style);
        }

    public void createPolygonOfParticles(Layer l, PApplet pA){
        PVector c = cCircle.centre.copy();
        int nVertexs = cParticles.numVertexos;
        int numParts = cCommons.numPoints;
        float fWidth = cParticles.frameWidth;
        RangFloat minR = cCircle.minRadius;
        RangFloat maxR = cCircle.maxRadius;
        RangFloat rVar = cCircle.radiusVariability;
        RangFloat rStep = cCircle.radiusStep;
        RangFloat angle = cCircle.angle;
        RangFloat aStep = cCircle.angleStep;
        RangFloat rAng = cCircle.randomAngle;
        AttractedParticleStyle style = createAttractedParticleStyleFromGUI();
        style.randomStyle();
        println("CREATE LINE OF PARTICLES ON LAYER " + l.id + ".");
        l. createPolygonOfParticles(pA, c, nVertexs, numParts, fWidth, minR, maxR, rVar, rStep, angle, aStep, rAng, style);
    }

    public void createSpiralOfParticles(Layer l, PApplet pA){
        PVector c = cCircle.centre.copy();
        RangFloat minR = cCircle.minRadius;
        RangFloat maxR = cCircle.maxRadius;
        RangFloat rVar = cCircle.radiusVariability;
        RangFloat rStep = cCircle.radiusStep;
        RangFloat angle = cCircle.angle;
        RangFloat aStep = cCircle.angleStep;
        RangFloat rAng = cCircle.randomAngle;
        AttractedParticleStyle style = createAttractedParticleStyleFromGUI();
        style.randomStyle();
        println("CREATE SPIRAL OF PARTICLES ON LAYER " + l.id + ".");
        l.createSpiralOfParticles(pA, c, minR, maxR, rVar, rStep, angle, aStep, rAng, style);
    }

    public void createWaveOfParticles(Layer l, PApplet pA){
        PVector c1 = cLine.corner1.copy();
        PVector c2 = cLine.corner2.copy();
        RangFloat amplitud = cWave.amplitud;
        boolean ampAsc = cWave.ampAsc;
        RangFloat xStep = cWave.xStep;
        RangFloat angle = cCircle.angle;
        RangFloat aStep = cCircle.angleStep;
        AttractedParticleStyle style = createAttractedParticleStyleFromGUI();
        style.randomStyle();
        println("CREATE WAVE OF PARTICLES ON LAYER " + l.id + ".");
        l.createWaveOfParticles(pA, c1, c2, amplitud, ampAsc, xStep, angle, aStep, style);
    }


    // SET STYLES FROM GUI

     public SetOfPointsStyle createSetOfPointsStyleFromGUI(){
        boolean e = cCommons.enable;
        RangFloat xR = cRandom.xRange;
        RangFloat yR = cRandom.yRange;
        RangFloat xV = cLine.xVar;
        RangFloat yV = cLine.yVar;
        float ratio = cCommons.ratioAttRep;
        int ma = cCommons.mapMassAtt;
        RangFloat mai = cCommons.mapInMassAtt;
        RangFloat mao = cCommons.massAtt;
        int mr = cCommons.mapMassRep;
        RangFloat mri = cCommons.mapInMassRep;
        RangFloat mro = cCommons.massRep;
        boolean c = cCommons.collapseable;
        int mc = cCommons.mapNPCollapse;
        RangFloat mci = cCommons.mapInNPCollapse;
        RangFloat mco = cCommons.np2Col;
        int ms = cCommons.mapSpinAngle;
        RangFloat msi = cCommons.mapInSpinAngle;
        RangFloat mso = cCommons.spinAng;
        PVector ref = cParticles.refColor.copy();
        SetOfPointsStyle style = new SetOfPointsStyle(e, xR, yR, xV, yV, ratio, ma, mai, mao, mr, mri, mro,
                                                        c, mc, mci, mco, ms, msi, mso, ref);
        return style;
     }

     // pendent de fer!!!!!!
     public AttractedParticleStyle createAttractedParticleStyleFromGUI(){
        AttractedParticleStyle style = new AttractedParticleStyle();
         style.setName(cParticles.styleName);
         style.setStep(cParticles.particleStep);
         style.setDelay(cParticles.particleDelay);
         style.setVariability(cLine.xVar, cLine.yVar);
         style.setRandomness(cRandom.xRange, cRandom.yRange);
         style.setFadeInSize(cParticles.fadeInSize);
         println("MAP RED:"+cParticles.mapRed);
         style.setColorParams(cParticles.mapRed, cParticles.redIn, cParticles.redOut, cParticles.mapGreen, cParticles.greenIn, cParticles.greenOut, cParticles.mapBlue, cParticles.blueIn, cParticles.blueOut, cParticles.mapOpac, cParticles.opacIn, cParticles.opacOut, cParticles.refColor);
         style.setSizeParams(cParticles.mapWidth, cParticles.widthIn, cParticles.widthOut, cParticles.mapHeight, cParticles.heightIn, cParticles.heightOut);
        //style.randomStyle();
        println(style);
        return style;
     }
}
