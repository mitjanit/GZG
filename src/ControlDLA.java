import controlP5.*;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static processing.core.PApplet.println;

public class ControlDLA {

    ScrollableList slDLAOptions;
    int dlaOption;
    Button bCreateDLA, bSaveVertex, bRemoveVertex, bRemoveLastVertex, bClosePolygon, bAddVertexOnClick, bAddVertexOnDrag;
    boolean createDLA, saveVertex, closePolygon, addVertexOnClick, addVertexOnDrag;
    Slider sDlaDist;
    float dlaDist;
    Slider2D s2dStartDLA;
    PVector startDLA;
    DLARect dlaRect;
    //DLACircle dlaCircle;
    //DLAPolygon dlaPolygon;
    ArrayList<PVector> corners;
    List dlaOptions = Arrays.asList("RECT", "CIRCLE", "POLYGON");

    public ControlDLA(ControlWindow cw){
        setupDLAControls(cw);
    }

    public void setupDLAControls(ControlWindow cw){

        // DLA DISTANCE SLIDER
        sDlaDist = cw.cp5.addSlider("DLA DIST")
                .setPosition(cw.marginLeft + cw.rangeWidth/2, cw.marginTop + cw.rowStep*30)
                .setSize(cw.rangeWidth/2,cw.controlHeight)
                .setRange(Defaults.MIN_POISSON_K,Defaults.MAX_POISSON_K)
                .setValue(Defaults.MIN_POISSON_K).setValueLabel(""+Defaults.MIN_POISSON_K)
                .moveTo("dla")
        ;
        dlaDist = Defaults.MIN_POISSON_K;


        // CREATE POISSON BUTTON
        bCreateDLA = cw.cp5.addButton("CREATE DLA").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*30)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("CREATE DLA")
                .moveTo("dla")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        createDLA = bCreateDLA.isOn();
                    }
                })
        ;

        createDLA=false;

        // SAVE VERTEX BUTTON
        bSaveVertex = cw.cp5.addButton("SAVE VERTEX DLA").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("dla")
        ;

        saveVertex=false;

        // REMOVE LAST VERTEX BUTTON
        bRemoveLastVertex = cw.cp5.addButton("REMOVE LAST VERTEX DLA")
                .setLabel("REMOVE LAST").setValue(0)
                .setPosition(cw.marginLeft+cw.rangeWidth/4, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("dla")
        ;

        // REMOVE LAST VERTEX BUTTON
        bRemoveVertex = cw.cp5.addButton("REMOVE ALL VERTEX DLA")
                .setLabel("REMOVE ALL").setValue(0)
                .setPosition(cw.marginLeft+2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("dla")
        ;

        // SAVE VRETEX BUTTON
        bClosePolygon = cw.cp5.addButton("CLOSE POLYGON DLA").setValue(0)
                .setPosition(cw.marginLeft + 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("dla")
        ;

        closePolygon=false;
        corners = new ArrayList<PVector>();

        // ADD VERTEX MOUSE PRESSED  BUTTON
        bAddVertexOnClick = cw.cp5.addButton("ADD VERTEX ON CLICK DLA").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*32)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD VERTEX ON CLICK")
                .moveTo("dla")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        addVertexOnClick = bAddVertexOnClick.isOn();
                    }
                })
        ;

        addVertexOnClick=false;

        // ADD VERTEX MOUSE PRESSED  BUTTON
        bAddVertexOnDrag = cw.cp5.addButton("ADD VERTEX ON DRAG DLA").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/2, cw.marginTop+ cw.rowStep*32)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD VERTEX ON DRAG")
                .moveTo("dla")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        addVertexOnDrag = bAddVertexOnDrag.isOn();
                    }
                })
        ;

        addVertexOnDrag=false;

        // POISSON DISTRIBUTION
        slDLAOptions = cw.cp5.addScrollableList("DLA DISTRIBUTION")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*29) //27
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(dlaOptions)
                .setType(ScrollableList.DROPDOWN)//.setType(ScrollableList.LIST)
                .moveTo("dla")
        ;

        slDLAOptions.setValue(0.0f); dlaOption = 0;

        // SLIDER 2D START DLA
        s2dStartDLA = cw.cp5.addSlider2D("START DLA")
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*19)
                .setSize((int)(cw.rangeWidth),(int)(3*cw.rangeWidth/4))
                .setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2}).setCursorX(50).setCursorY(50)
                .setMinX(0).setMaxX(Defaults.sceneWidth).setMinY(0).setMaxY(Defaults.sceneHeight)
                .moveTo("dla")
        ;
        startDLA = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);

    }

    public void resetControls(){

        bCreateDLA.setOff(); createDLA=false; saveVertex=false; closePolygon=false;
        sDlaDist.setValue(Defaults.MIN_POISSON_DIST); dlaDist = Defaults.MIN_POISSON_DIST;
        corners = new ArrayList<PVector>();
        bAddVertexOnClick.setOff(); bAddVertexOnDrag.setOff();
    }

    public void checkControlEvents(ControlEvent theControlEvent, ControlWindow cw){

        // DLA DISTANCE SLIDER
        if(theControlEvent.isFrom("DLA DIST")) {
            dlaDist = (int)sDlaDist.getValue();
            sDlaDist.setValueLabel(""+dlaDist);
            println("DLA DISTANCE: "+dlaDist);
        }
        // CREATE DLA BUTTON
        else if(theControlEvent.isFrom("CREATE DLA")) {
            switch(dlaOption){
                case 0:
                    println("CREATE DLA RECT");
                    dlaRect = new DLARect((int)sDlaDist.getValue(),cw.cCommons.numPoints, cw.cLine.corner1, cw.cLine.corner2, cw.cCircle.centre);
                    dlaRect.setDLAPoints();
                    break;

                case 1:
                    println("CREATE DLA CIRCLE");

                    break;

                case 2:
                    println("CREATE DLA POLYGON");
                    closePolygon = true;
                    println("CORNERS NUM: "+corners.size());

                    break;
            }

        }
        // SAVE VERTEX BUTTON
        else if(theControlEvent.isFrom("SAVE VERTEX DLA")) {
            PVector newVertex = cw.cCircle.centre.copy();
            corners.add(newVertex);
            println("SAVE VERTEX ON "+newVertex);
        }
        // REMOVE LAST VERTEX BUTTON
        else if(theControlEvent.isFrom("REMOVE LAST VERTEX DLA")) {
            int n = corners.size();
            if(n>0) corners.remove(n-1);
            println("REMOVE LAST VERTEX");
        }
        // REMOVE ALL VERTEX BUTTON
        else if(theControlEvent.isFrom("REMOVE ALL VERTEX DLA")) {
            corners.clear();
            println("REMOVE ALL VERTEX");
        }
        // ADD VERTEX ON CLICK BUTTON
        else if(theControlEvent.isFrom("ADD VERTEX ON CLICK DLA")) {
            if(addVertexOnClick) bAddVertexOnClick.setLabel("ADD VERTEX ON CLICK ON");
            else bAddVertexOnClick.setLabel("ADD VERTEX ON CLICK OFF");
            println("ADD VERTEX ON CLICK: "+addVertexOnClick);
        }
        // ADD VERTEX ON DRAG BUTTON
        else if(theControlEvent.isFrom("ADD VERTEX ON DRAG DLA")) {
            if(addVertexOnDrag) bAddVertexOnDrag.setLabel("ADD VERTEX ON DRAG ON");
            else bAddVertexOnDrag.setLabel("ADD VERTEX ON DRAG OFF");
            println("ADD VERTEX ON DRAG: "+addVertexOnDrag);
        }
        // CLOSE POLYGON BUTTON
        else if(theControlEvent.isFrom("CLOSE POLYGON DLA")) {
            println("CLOSE POLYGON");
            closePolygon = true;
            bAddVertexOnClick.setOff(); bAddVertexOnDrag.setOff();
            addVertexOnClick = false; addVertexOnDrag = false;
        }
        // START DLA POSITION
        if(theControlEvent.isFrom("START DLA")) {
            float ox = s2dStartDLA.getArrayValue()[0];
            float oy = s2dStartDLA.getArrayValue()[1];
            startDLA = new PVector(ox, oy);
            println("START DLA POSITION // x: "+startDLA.x+", y: "+startDLA.y);
        }
        // DLA DISTRIBUTION SCROLL LIST
        else if(theControlEvent.isFrom("DLA DISTRIBUTION")) {
            dlaOption = (int) slDLAOptions.getValue();
            println("DLA Distribution Shape: " + dlaOption + " - " + dlaOptions.get(dlaOption));
            switch (dlaOption) {
                case 0: // RECT
                    // Posar
                    cw.cp5.getController("TL CORNER").moveTo("dla");
                    cw.cp5.getController("BR CORNER").moveTo("dla");
                    cw.cp5.getController("CENTER TL").moveTo("dla");
                    cw.cp5.getController("CENTER BR").moveTo("dla");
                    cw.cp5.getController("LOCK X").moveTo("dla");
                    cw.cp5.getController("LOCK X2").moveTo("dla");
                    cw.cp5.getController("LOCK Y").moveTo("dla");
                    cw.cp5.getController("LOCK Y2").moveTo("dla");
                    cw.cp5.getController("SAME X").moveTo("dla");
                    cw.cp5.getController("SAME Y").moveTo("dla");
                    //Llevar
                    cw.cp5.getController("CENTRE").moveTo("circle");
                    cw.cp5.getController("MIN RADIUS").moveTo("circle");
                    cw.cp5.getController("MAX RADIUS").moveTo("circle");
                    cw.cp5.getController("ORIGIN").moveTo("circle");
                    break;

                case 1: // CIRCLE
                    // Llevar
                    cw.cp5.getController("TL CORNER").moveTo("line");
                    cw.cp5.getController("BR CORNER").moveTo("line");
                    cw.cp5.getController("CENTER TL").moveTo("line");
                    cw.cp5.getController("CENTER BR").moveTo("line");
                    cw.cp5.getController("LOCK X2").moveTo("line");
                    cw.cp5.getController("LOCK Y2").moveTo("line");
                    cw.cp5.getController("SAME X").moveTo("line");
                    cw.cp5.getController("SAME Y").moveTo("line");

                    //Posar
                    cw.cp5.getController("CENTRE").moveTo("dla");
                    cw.cp5.getController("MIN RADIUS").moveTo("dla");
                    cw.cp5.getController("MAX RADIUS").moveTo("dla");
                    cw.cp5.getController("ORIGIN").moveTo("dla");
                    cw.cp5.getController("LOCK X").moveTo("dla");
                    cw.cp5.getController("LOCK Y").moveTo("dla");
                    break;

                case 2: // POLYGON
                    // Posar
                    cw.cp5.getController("CENTRE").moveTo("dla");
                    cw.cp5.getController("ORIGIN").moveTo("dla");
                    cw.cp5.getController("LOCK X").moveTo("dla");
                    cw.cp5.getController("LOCK Y").moveTo("dla");

                    //Llevar
                    cw.cp5.getController("TL CORNER").moveTo("line");
                    cw.cp5.getController("CENTER TL").moveTo("line");
                    cw.cp5.getController("BR CORNER").moveTo("line");
                    cw.cp5.getController("CENTER BR").moveTo("line");
                    cw.cp5.getController("LOCK X2").moveTo("line");
                    cw.cp5.getController("LOCK Y2").moveTo("line");
                    cw.cp5.getController("SAME X").moveTo("line");
                    cw.cp5.getController("SAME Y").moveTo("line");
                    cw.cp5.getController("MIN RADIUS").moveTo("circle");
                    cw.cp5.getController("MAX RADIUS").moveTo("circle");
                    break;
            }
            //cw.cp5.getController("POISSON DISTRIBUTION").bringToFront();


        }

    }

    void createVertexOn(PVector p){
        //corners.add(p.copy());
    }

    void createVertexOn(PVector p, float minDist){
        /*
        int n = corners.size();
        if(n>0 && p.dist(corners.get(n-1))>minDist) {
            corners.add(p.copy());
        }
        */
    }
}
