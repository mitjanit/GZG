import controlP5.*;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static processing.core.PApplet.println;

public class ControlPoisson {

    ScrollableList slPoissonOptions;
    int poissonOption;
    Button bCreatePoisson, bSaveVertex, bRemoveVertex, bRemoveLastVertex, bClosePolygon, bAddVertexOnClick, bAddVertexOnDrag;
    boolean createPoisson, saveVertex, closePolygon, addVertexOnClick, addVertexOnDrag;
    Slider sPoissonDist, sPoissonK;
    float poissonDist, poissonK;
    PoissonRect pr;
    PoissonCircle pc;
    PoissonPolygon pp;
    ArrayList<PVector> corners;
    List poissonOptions = Arrays.asList("RECT", "CIRCLE", "POLYGON");

    PVector centre, corner1, corner2; //VIUS repetits
    RangFloat rMinRadius, rMaxRadius; //VIUS repetits


    public ControlPoisson(ControlWindow cw){
        setupPoissonControls(cw);
    }

    public void setupPoissonControls(ControlWindow cw){

        // POISSON DISTANCE SLIDER
        sPoissonK = cw.cp5.addSlider("POISSON K")
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*28)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_POISSON_K,Defaults.MAX_POISSON_K)
                .setValue(Defaults.MIN_POISSON_K).setValueLabel(""+Defaults.MIN_POISSON_K)
                .moveTo("poisson")
        ;
        poissonK = Defaults.MIN_POISSON_K;

        // POISSON DISTANCE SLIDER
        sPoissonDist = cw.cp5.addSlider("POISSON DIST")
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*29)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_POISSON_DIST,Defaults.MAX_POISSON_DIST)
                .setValue(Defaults.MIN_POISSON_DIST).setValueLabel(""+Defaults.MIN_POISSON_DIST)
                .moveTo("poisson")
        ;
        poissonDist = Defaults.MIN_POISSON_DIST;

        // CREATE POISSON BUTTON
        bCreatePoisson = cw.cp5.addButton("CREATE POISSON").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*30)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("CREATE POISSON")
                .moveTo("poisson")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        createPoisson = bCreatePoisson.isOn();
                    }
                })
        ;

        createPoisson=false;

        // SAVE VERTEX BUTTON
        bSaveVertex = cw.cp5.addButton("SAVE VERTEX").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("poisson")
        ;

        saveVertex=false;

        // REMOVE LAST VERTEX BUTTON
        bRemoveLastVertex = cw.cp5.addButton("REMOVE LAST VERTEX")
                .setLabel("REMOVE LAST").setValue(0)
                .setPosition(cw.marginLeft+cw.rangeWidth/4, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("poisson")
        ;

        // REMOVE LAST VERTEX BUTTON
        bRemoveVertex = cw.cp5.addButton("REMOVE ALL VERTEX")
                .setLabel("REMOVE ALL").setValue(0)
                .setPosition(cw.marginLeft+2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("poisson")
        ;

        // SAVE VRETEX BUTTON
        bClosePolygon = cw.cp5.addButton("CLOSE POLYGON").setValue(0)
                .setPosition(cw.marginLeft + 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("poisson")
        ;

        closePolygon=false;
        corners = new ArrayList<PVector>();

        // ADD VERTEX MOUSE PRESSED  BUTTON
        bAddVertexOnClick = cw.cp5.addButton("ADD VERTEX ON CLICK").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*32)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD VERTEX ON CLICK")
                .moveTo("poisson")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        addVertexOnClick = bAddVertexOnClick.isOn();
                    }
                })
        ;

        addVertexOnClick=false;

        // ADD VERTEX MOUSE PRESSED  BUTTON
        bAddVertexOnDrag = cw.cp5.addButton("ADD VERTEX ON DRAG").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/2, cw.marginTop+ cw.rowStep*32)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD VERTEX ON DRAG")
                .moveTo("poisson")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        addVertexOnDrag = bAddVertexOnDrag.isOn();
                    }
                })
        ;

        addVertexOnDrag=false;

        // POISSON DISTRIBUTION
        slPoissonOptions = cw.cp5.addScrollableList("POISSON DISTRIBUTION")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*27) //27
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(poissonOptions)
                .setType(ScrollableList.DROPDOWN)//.setType(ScrollableList.LIST)
                .moveTo("poisson")
        ;

        slPoissonOptions.setValue(0.0f); poissonOption = 0;
    }

    public void resetControls(){
        bCreatePoisson.setOff(); createPoisson=false; saveVertex=false; closePolygon=false;
        sPoissonDist.setValue(Defaults.MIN_POISSON_DIST); poissonDist = Defaults.MIN_POISSON_DIST;
        sPoissonK.setValue(Defaults.MIN_POISSON_K); poissonK = Defaults.MIN_POISSON_K;
        corners = new ArrayList<PVector>();
        bAddVertexOnClick.setOff(); bAddVertexOnDrag.setOff();
    }

    public void checkControlEvents(ControlEvent theControlEvent, ControlWindow cw){
        // POISSON TIMES SLIDER
        if(theControlEvent.isFrom("POISSON K")) {
            poissonK = (int)sPoissonK.getValue();
            sPoissonK.setValueLabel(""+poissonK);
            println("POISSON K: "+poissonK);

        }
        // POISSON DISTANCE SLIDER
        else if(theControlEvent.isFrom("POISSON DIST")) {
            poissonDist = (int)sPoissonDist.getValue();
            sPoissonDist.setValueLabel(""+poissonDist);
            println("POISSON DISTANCE: "+poissonDist);

        }
        // CREATE POISSON BUTTON
        else if(theControlEvent.isFrom("CREATE POISSON")) {

            switch(poissonOption){
                case 0: //println("CREATE POISSON RECT");
                    pr = new PoissonRect((int)sPoissonK.getValue(),(int)sPoissonDist.getValue(),corner1, corner2);
                    pr.startPoisson();
                    break;

                case 1: //println("CREATE POISSON CIRCLE");
                    pc = new PoissonCircle((int)sPoissonK.getValue(),(int)sPoissonDist.getValue(),centre, rMaxRadius.getMaxValue(), rMinRadius.getMaxValue());
                    PVector startPoint = new PVector(centre.x + rMinRadius.getMaxValue() + (rMaxRadius.getMaxValue() - rMinRadius.getMaxValue())/2, centre.y);
                    pc.startPoisson(startPoint);
                    break;

                case 2: //println("CREATE POISSON POLYGON");
                    closePolygon = true;
                    pp = new PoissonPolygon((int)sPoissonK.getValue(),(int)sPoissonDist.getValue(),corners);
                    pp.startPoisson(cw);
                    break;
            }

        }
        // SAVE VERTEX BUTTON
        else if(theControlEvent.isFrom("SAVE VERTEX")) {
            PVector newVertex = centre.copy();
            corners.add(newVertex);
            println("SAVE VERTEX ON "+newVertex);
        }
        // REMOVE LAST VERTEX BUTTON
        else if(theControlEvent.isFrom("REMOVE LAST VERTEX")) {
            int n = corners.size();
            if(n>0) corners.remove(n-1);
            println("REMOVE LAST VERTEX");
        }
        // REMOVE ALL VERTEX BUTTON
        else if(theControlEvent.isFrom("REMOVE ALL VERTEX")) {
            corners.clear();
            println("REMOVE ALL VERTEX");
        }
        // ADD VERTEX ON CLICK BUTTON
        else if(theControlEvent.isFrom("ADD VERTEX ON CLICK")) {
            if(addVertexOnClick) bAddVertexOnClick.setLabel("ADD VERTEX ON CLICK ON");
            else bAddVertexOnClick.setLabel("ADD VERTEX ON CLICK OFF");
            println("ADD VERTEX ON CLICK: "+addVertexOnClick);
        }
        // ADD VERTEX ON DRAG BUTTON
        else if(theControlEvent.isFrom("ADD VERTEX ON DRAG")) {
            if(addVertexOnDrag) bAddVertexOnDrag.setLabel("ADD VERTEX ON DRAG ON");
            else bAddVertexOnDrag.setLabel("ADD VERTEX ON DRAG OFF");
            println("ADD VERTEX ON DRAG: "+addVertexOnDrag);
        }
        // CLOSE POLYGON BUTTON
        else if(theControlEvent.isFrom("CLOSE POLYGON")) {
            println("CLOSE POLYGON");
            closePolygon = true;
            bAddVertexOnClick.setOff(); bAddVertexOnDrag.setOff();
            addVertexOnClick = false; addVertexOnDrag = false;
        }
        // POISSON DISTRIBUTION SCROLL LIST
        else if(theControlEvent.isFrom("POISSON DISTRIBUTION")) {
            poissonOption = (int)slPoissonOptions.getValue();
            println("Poisson Distribution Shape: "+poissonOption+" - "+poissonOptions.get(poissonOption));
            switch(poissonOption){
                case 0: // RECT
                    // Posar
                    cw.cp5.getController("TL CORNER").moveTo("poisson");
                    cw.cp5.getController("BR CORNER").moveTo("poisson");
                    cw.cp5.getController("CENTER TL").moveTo("poisson");
                    cw.cp5.getController("CENTER BR").moveTo("poisson");
                    cw.cp5.getController("LOCK X").moveTo("poisson");
                    cw.cp5.getController("LOCK X2").moveTo("poisson");
                    cw.cp5.getController("LOCK Y").moveTo("poisson");
                    cw.cp5.getController("LOCK Y2").moveTo("poisson");
                    cw.cp5.getController("SAME X").moveTo("poisson");
                    cw.cp5.getController("SAME Y").moveTo("poisson");
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
                    cw.cp5.getController("CENTRE").moveTo("poisson");
                    cw.cp5.getController("MIN RADIUS").moveTo("poisson");
                    cw.cp5.getController("MAX RADIUS").moveTo("poisson");
                    cw.cp5.getController("ORIGIN").moveTo("poisson");
                    cw.cp5.getController("LOCK X").moveTo("poisson");
                    cw.cp5.getController("LOCK Y").moveTo("poisson");
                    break;

                case 2: // POLYGON
                    // Posar
                    cw.cp5.getController("CENTRE").moveTo("poisson");
                    cw.cp5.getController("ORIGIN").moveTo("poisson");
                    cw.cp5.getController("LOCK X").moveTo("poisson");
                    cw.cp5.getController("LOCK Y").moveTo("poisson");

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

}
