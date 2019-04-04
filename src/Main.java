
import processing.core.*;

import java.util.ArrayList;

public class Main extends PApplet {

    //****************** Control View Vars ***************//
    String folderName = "SandBox_Scenario_01";
    String prefixFile = "SandBox_";
    boolean isRecordingPDF = false;
    boolean divGravity = false;

    float fadeAmount = 255.0f;

    //***************** Control Interaction Vars ***********//
    PVector lastPoint = new PVector();
    boolean createPointWithMousePressed = false;
    boolean deletePointWithMousePressed = false;
    boolean createParticleWithMousePressed = false;
    boolean deleteParticleWithMousePressed = true;

    //***************** Control Output Vars ***************//


    //******************* Object and Constnats ***********//

    public static ArrayList<Layer> layers;
    ArrayList<AttractedParticleStyle> particleStyles;
    ArrayList<SetOfPointsStyle> pointsStyles;

    Layer currentLayer;
    SetOfPointsStyle currentPointStyle;
    AttractedParticleStyle currentParticleStyle;

    ArrayList<BezierCurve> beziers = new ArrayList<BezierCurve>();
    ArrayList<PVector> draggingPoints = new ArrayList<PVector>();

    public static ControlWindow controls;

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    public void settings(){
        size(Defaults.sceneWidth, Defaults.sceneHeight, P2D);
        smooth(10);
    }

    public void setup(){

        background(Defaults.bgColor);

        controls = new ControlWindow("Controls Window.", 1400,1080);

        //LAYERS creation
        createLayers();
        currentLayer = layers.get(0);

        Layer l = layers.get(0);
        SetOfPointsStyle style = new SetOfPointsStyle();
        currentPointStyle = style;

        ArrayList<PVector> pts = new ArrayList<PVector>();
        pts.add(new PVector(50,50));
        pts.add(new PVector(400, 500));
        pts.add(new PVector(700, 50));


        AttractedParticleStyle pStyle = new AttractedParticleStyle();
        pStyle.randomStyle();
        currentParticleStyle = pStyle;

        // BIG IMAGE
        Defaults.big = createGraphics((int)(width*Defaults.BIG_SCALE),(int)(height*Defaults.BIG_SCALE));
        Defaults.big.beginDraw();
        Defaults.big.background(255);


    }

    public void draw(){

        switch(controls.cDefaults.blendOptions.get(controls.cDefaults.blendM).toString()){
            case "ADD": blendMode(ADD); break;
            case "SUBTRACT": blendMode(SUBTRACT); break;
            case "DARKEST": blendMode(DARKEST); break;
            case "LIGHTEST": blendMode(LIGHTEST); break;
            case "DIFFERENCE": blendMode(DIFFERENCE); break;
            case "EXCLUSION": blendMode(EXCLUSION); break;
            case "MULTIPLY": blendMode(MULTIPLY); break;
            case "SCREEN": blendMode(SCREEN); break;
            case "REPLACE": blendMode(REPLACE); break;
            default: blendMode(BLEND); break;
        }

        if(controls.cDefaults.fadeEffect  || controls.cDefaults.liveColor){
            pushStyle();
                fill(controls.cDefaults.redBG, controls.cDefaults.greenBG, controls.cDefaults.blueBG,controls.cDefaults.fadeAmount);
                noStroke();
                pushMatrix();
                    translate(-Defaults.sceneWidth/2, -Defaults.sceneHeight/2,0);
                    rect(0,0,Defaults.sceneWidth, Defaults.sceneHeight);
                popMatrix();
            popStyle();
        }

        if (controls.cDefaults.saveToPrint && !isRecordingPDF) {
            println("saving to pdf – creating file ...");
            beginRecord(PDF, "output/pdf/"+folderName+"/"+Defaults.timestamp()+".pdf");
            isRecordingPDF = true;
        }

        if(controls.cDefaults.resetBackground || controls.cDefaults.liveColor) {
            background(controls.cDefaults.redBG, controls.cDefaults.greenBG, controls.cDefaults.blueBG, fadeAmount);
            Defaults.big.background(controls.cDefaults.redBG, controls.cDefaults.greenBG, controls.cDefaults.blueBG, fadeAmount);
            controls.cDefaults.resetBackground = false;
        }

        updateLayers(this, controls);

        if(controls.cCommons.preview){
            Preview.previewAll(this, controls);
        }

        if (controls.cDefaults.saveToPrint && isRecordingPDF) {
            controls.cDefaults.saveToPrint = false;
            println("saving to pdf – finishing");
            endRecord();
            println("saving to pdf – done");
            isRecordingPDF = false;
        }
        if (controls.cDefaults.saveOneFrame) {
            saveFrame("output/frame/"+folderName+"/"+prefixFile+Defaults.timestamp()+".tiff");
            controls.cDefaults.saveOneFrame=false;
        }
        if (controls.cDefaults.saveVideoFrame) {
            saveFrame("output/video/"+folderName+"/"+prefixFile+"####.tiff");
        }
        if (controls.cDefaults.saveBigSize) {
            Defaults.big.endDraw();
            Defaults.big.save("output/big/"+folderName+"/"+prefixFile+Defaults.timestamp()+".tiff");
            controls.cDefaults.saveBigSize = false;

            PImage imgOld = Defaults.big.get();
            Defaults.big = createGraphics((int)(width*Defaults.BIG_SCALE),(int)(height*Defaults.BIG_SCALE));
            Defaults.big.beginDraw();
            Defaults.big.background(Defaults.MAX_COLOR_OUT, Defaults.MAX_COLOR_OUT, Defaults.MAX_COLOR_OUT);
            Defaults.big.image(imgOld,0,0);
        }

    }

    public void mousePressed(){

        println("MOUSE PRESSED");

        currentLayer = layers.get(controls.cCommons.layer);

        if((controls.cMouse.createPointWithMousePressed) && (dist(mouseX, mouseY, lastPoint.x, lastPoint.y)>controls.cCircle.minDistance)){
            currentLayer.createPointOnMouse(this, currentPointStyle);
        }
        else if(controls.cMouse.deletePointWithMousePressed){
            currentLayer.deletePointOn(new PVector(mouseX, mouseY), 1.0f);
            controls.cDefaults.resetBackground = true;
        }
        else if((controls.cMouse.createParticleWithMousePressed) && (dist(mouseX, mouseY, lastPoint.x, lastPoint.y)>controls.cCircle.minDistance)){
            currentLayer.createParticleOnMouse(this, currentParticleStyle);
        }
        else if(controls.cMouse.deleteParticleWithMousePressed){
            currentLayer.deleteParticleOn(new PVector(mouseX, mouseY), Defaults.minDistance);
            controls.cDefaults.resetBackground = true;
        }
        /*
        else if(addVertexOnClick){
            createVertexOnMouse();
        }*/
        else if(controls.cBezier.addBezierOnClick){
            controls.cBezier.addBezier(new PVector(mouseX, mouseY));
        }
        else if(controls.cPolyline.addPolyPointOnClick){
            controls.cPolyline.addPolyLinePoint(new PVector(mouseX, mouseY));
        }

        lastPoint = new PVector(mouseX, mouseY);
    }

    public void mouseDragged(){

        println("MOUSE DRAGGED");
        currentLayer = layers.get(controls.cCommons.layer);

        if((controls.cMouse.createPointWithMouseDragged) && (dist(mouseX, mouseY, lastPoint.x, lastPoint.y)>controls.cCircle.minDistance)){
            currentLayer.createPointOnMouse(this, controls);
            lastPoint = new PVector(mouseX, mouseY);
        }
        else if(controls.cMouse.deletePointWithMouseDragged){
            currentLayer.deletePointOn(new PVector(mouseX, mouseY), controls.cCircle.minDistance);
            controls.cDefaults.resetBackground = true;
        }
        else if((controls.cMouse.createParticleWithMouseDragged) && (dist(mouseX, mouseY, lastPoint.x, lastPoint.y)>controls.cCircle.minDistance)){
            currentLayer.createParticleOnMouse(this, controls);
        }
        else if(controls.cMouse.deleteParticleWithMouseDragged){
            currentLayer.deleteParticleOn(new PVector(mouseX, mouseY), controls.cCircle.minDistance);
            controls.cDefaults.resetBackground = true;
        }
        /*
        else if(addVertexOnDrag){
            createVertexOnMouse();
        }*/
        else if(controls.cBezier.editBezierOnDrag){
            controls.cBezier.editBezier(new PVector(mouseX, mouseY));
        }
        else if(controls.cPolyline.editPolyPointOnDrag){
            controls.cPolyline.editPolyLinePoint(this, new PVector(mouseX,mouseY ));
        }
    }

    public void mouseReleased() {
        controls.cBezier.draggingPoints.clear();
    }

    void createLayers(){
        layers = new ArrayList<Layer>();
        for(int i=Defaults.MIN_LAYER; i<=Defaults.MAX_LAYER; i++){
            layers.add(new Layer(i));
        }
    }

    void updateLayers(PApplet pA, ControlWindow controls){
        for(Layer layer: layers){
            layer.update(pA, controls);
        }
    }

    // REMOVING ELEMENTS (All, Points, Particles, Sources).

    public static void removeAll(){
        for(Layer l: layers){
            l.deleteAll();
        }
    }

    public static void removeAllPoints(){
        for(Layer l: layers){
            l.deleteAllPoints();
        }
    }

    public static void removeAllParticles(){
        for(Layer l:layers){
            l.deleteAllParticles();
        }
    }

    public static void removeAllSources(){
        for(Layer l:layers){
            l.deleteAllSources();
        }
    }


}


//SCENARIOS
        /*l.createAreasOfPoints(1, 10, 0, style, new RangFloat(0, width), new RangFloat(0, height), false, false, false,
                false, false, false, false, false, false, false, false);*/

        /*l.createPolygonOfPoints(1, 10, 3, 10, style,
                Defaults.Center, new RangFloat(300), new RangFloat(0),
                new RangFloat(0), new RangFloat(0) , new RangFloat(0), new RangFloat(0),
                false, false);*/

/*l.createPolyLineOfPoints(1, 10, pts, 10, style,
                                new RangFloat(0), new RangFloat(0),
                                false, false);*/

        /*l.createLinesOfPoints(1, 10, 10, new PVector(100,100), new PVector(600,400),
                style, new RangFloat(0), new RangFloat(0), false, false);*/

        /*l.createRingsOfPoints(1, 10, 10, Defaults.Center,
                new RangFloat(100), new RangFloat(400), new RangFloat(0), new RangFloat(100),
                new RangFloat (0, TWO_PI), new RangFloat(0.95f), new RangFloat(0),
                false,
                style,
                new RangFloat(0), new RangFloat(0), new RangFloat(0),
                false, false, false);*/

        /*l.createGridsOfPoints(1, 10, Defaults.TopLeft, Defaults.BottomRight, 10, 15,
        new RangFloat(0), new RangFloat(0), style, false, false);*/

        /*l.createSpiralOfPoints(1, 1, Defaults.Center, 10, style, false, false,
                new RangFloat(0), new RangFloat(400), new RangFloat(10), new RangFloat(0),
                new RangFloat(0, 6*TWO_PI), new RangFloat(0.3f), new RangFloat(0),
                new RangFloat(0), new RangFloat(0), new RangFloat(0), new RangFloat(0),
                false, false);*/

        /*l.createWavesOfPoints(1, 10, new PVector(0, height/2), new PVector(width, height/2), 10,
        style, new RangFloat(100, 300), new RangFloat(15), false,
        new RangFloat (0), new RangFloat(0.15f), new RangFloat(0),
        new RangFloat(0), new RangFloat(0), new RangFloat(0),
        false, false);*/

        /*l.createWhitneyOfPoints(1, 10, Defaults.Center, 10,
        21.5f, 120, 25,
        style,
        new RangFloat(0), new RangFloat(0),
        false, false);*/

        /*l.createFormulaOfPoints(1, 10, Defaults.Center, 10, style,
                new RangFloat (1), new RangFloat (2),
                new RangFloat(210.0f), new RangFloat(600.0f), new RangFloat(10.0f), new RangFloat(50.0f), new RangFloat(3),
                new RangFloat(0), new RangFloat(0));*/

        /*BezierCurve bc1 = new BezierCurve(new PVector(50,50), new PVector(400,50), new PVector(100,100), new PVector(300,300));
        beziers.add(bc1);
        BezierCurve bc2 = new BezierCurve(new PVector(400,50), new PVector(600,850), new PVector(300,300), new PVector(500,400));
        beziers.add(bc2);
        l.createBezierOfPoints(1, 10, 10, beziers, style,
                new RangFloat(0), new RangFloat(0) , new RangFloat(0), true, true);*/

        /*PoissonRect pr = new PoissonRect(10,10, Defaults.TopLeft, Defaults.BottomRight);
        PoissonCircle pc = new PoissonCircle(10,10,Defaults.Center, 400, 0);
        ArrayList<PVector> vertexs = new ArrayList<PVector>();
        vertexs.add(new PVector(0,0)); vertexs.add(new PVector(400,400));
        vertexs.add(new PVector(200, 800));
        PoissonPolygon pp = new PoissonPolygon(10,10,vertexs);
        l.createPoissonOfPoints(1, 0, 10,
        style, pr, pc, pp, new RangFloat(0), new RangFloat(0),
        false, false);*/

        /*l.createTextsOfPoints(1, 100, 10,
        Defaults.Center, "A", String fontFamily, 250,
        float fontAlignX, float fontAlignY, float polyMode,
        float numPolyMode, float polyLength, float polyStep, float polyAngle,
        float adaptorScale, float adaptorOffset,
        new RangFloat(0, TWO_PI), true,
        style, new RangFloat(0), new RangFloat(0));*/

        /*l.createImagesOfPoints(this, 1, 10,
        Defaults.TopLeft, Defaults.BottomRight, 20, 30,
        "foto.jpg", new RangFloat(0, 100), new RangFloat(0, 50), 50, true,
        true, false, style, new RangFloat(0), new RangFloat(0));*/
//l.createRandomParticles(this,50, 10, Defaults.TopLeft, Defaults.Center, pStyle);
//l.createLineOfParticles(this,100,10,Defaults.Center, Defaults.BottomRight, pStyle);
//l.createFrameOfParticles(this, new PVector(100,100), new PVector(width-100, height-100), 2, 5, 50,pStyle );
//l.createGridOfParticles(this,Defaults.TopLeft, Defaults.BottomRight, 10,5,pStyle);

//l.createRingOfParticles(this, Defaults.Center, new RangFloat(0), new RangFloat(400), new RangFloat(10), new RangFloat(20), new RangFloat(0, TWO_PI), new RangFloat(0.1f), new RangFloat(0), pStyle);

        /*l.createPolygonOfParticles(this, Defaults.Center, 4, 10, 0,
                                    new RangFloat(0), new RangFloat(400), new RangFloat(0),new RangFloat(100),
                                    new RangFloat(0, TWO_PI), new RangFloat(0.1f),new RangFloat(0),pStyle);*/

        /*l.createSpiralOfParticles(this,Defaults.Center,
                                    new RangFloat(0),new RangFloat(600),new RangFloat(0),new RangFloat(5),
                                    new RangFloat(0, 6*TWO_PI),new RangFloat(0.05f),new RangFloat(0),pStyle);*/

        /*l.createWaveOfParticles(this,new PVector(0, height/2), new PVector(width, height/2),
                                new RangFloat(100, 100),false, new RangFloat(5),
                                new RangFloat(0, TWO_PI),new RangFloat(0.1f), pStyle);*/

//Scenarios.scenario05(this, layers);