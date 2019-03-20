import controlP5.*;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.println;

public class ControlPolyLine {

    Button bSavePolyPoint, bRemovePolyPoints, bRemoveLastPolyPoint, bClosePolyPoints, bAddPolyPointOnClick, bEditPolyPointOnDrag;
    boolean savePolyPoint, closePolyPoint, addPolyPointOnClick, editPolyPointOnDrag;
    ArrayList<PVector> polylinePoints = new ArrayList<PVector>();
    PVector centre;  // Vius

    public ControlPolyLine(ControlWindow cw){
        setupPolyLineControls(cw);
    }

    public void setupPolyLineControls(ControlWindow cw){
        // SAVE POLYLINE POINT BUTTON
        bSavePolyPoint = cw.cp5.addButton("SAVE POLYLINE POINT").setLabel("SAVE POINT").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("polyline")
        ;

        savePolyPoint=false;

        // REMOVE LAST POLYLINE POINT BUTTON
        bRemoveLastPolyPoint = cw.cp5.addButton("REMOVE LAST POLYLINE POINT")
                .setLabel("REMOVE LAST").setValue(0)
                .setPosition(cw.marginLeft+cw.rangeWidth/4, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("polyline")
        ;

        // REMOVE ALL POLYLINE POINTS BUTTON
        bRemovePolyPoints = cw.cp5.addButton("REMOVE ALL POLYLINE POINTS")
                .setLabel("REMOVE ALL").setValue(0)
                .setPosition(cw.marginLeft+2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("polyline")
        ;

        // CLOSE POLYLINE BUTTON
        bClosePolyPoints = cw.cp5.addButton("CLOSE POLYLINE").setValue(0)
                .setPosition(cw.marginLeft + 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("polyline")
        ;

        closePolyPoint=false;
        polylinePoints = new ArrayList<PVector>();

        // ADD POLYLINE POINT ON MOUSE PRESSED  BUTTON
        bAddPolyPointOnClick = cw.cp5.addButton("ADD POLYLINE POINT ON CLICK").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*20)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD POINT ON CLICK")
                .moveTo("polyline")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        addPolyPointOnClick = bAddPolyPointOnClick.isOn();
                        if(addPolyPointOnClick) bEditPolyPointOnDrag.setOff();
                    }
                })
        ;

        addPolyPointOnClick=false;

        // EDIT POLYLINE POINT ON MOUSE DRAGGED  BUTTON
        bEditPolyPointOnDrag = cw.cp5.addButton("EDIT POLYLINE POINT ON DRAG").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/2, cw.marginTop+ cw.rowStep*20)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO EDIT POINT ON DRAG")
                .moveTo("polyline")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        editPolyPointOnDrag = bEditPolyPointOnDrag.isOn();
                        if(editPolyPointOnDrag) bAddPolyPointOnClick.setOff();
                    }
                })
        ;

        editPolyPointOnDrag=false;
    }

    public void resetControls(){
        savePolyPoint=false; closePolyPoint=false;
        polylinePoints = new ArrayList<PVector>();
        bAddPolyPointOnClick.setOff(); addPolyPointOnClick=false;
        bEditPolyPointOnDrag.setOff(); editPolyPointOnDrag=false;
    }

    public void checkControlEvents(ControlEvent theControlEvent, ControlWindow cw){
        // SAVE POLYLINE POINT BUTTON
        if(theControlEvent.isFrom("SAVE POLYLINE POINT")) {
            PVector c = cw.cCircle.centre.copy();
            PVector np = new PVector(c.x, c.y);
            polylinePoints.add(np);
            println("SAVE POLYLINE POINT ON "+c);
        }

        // REMOVE LAST POLYLINE POINT BUTTON
        else if(theControlEvent.isFrom("REMOVE LAST POLYLINE POINT")) {
            int n = polylinePoints.size();
            if(n>0) polylinePoints.remove(n-1);
            println("REMOVE LAST POLYLINE POINT");
        }

        // REMOVE ALL POLYLINE POINTS BUTTON
        else if(theControlEvent.isFrom("REMOVE ALL POLYLINE POINTS")) {
            polylinePoints.clear();
            println("REMOVE ALL POLYLINE POINTS");
        }

        // ADD POLYLINE POINT ON CLICK BUTTON
        else if(theControlEvent.isFrom("ADD POLYLINE POINT ON CLICK")) {
            if(addPolyPointOnClick) bAddPolyPointOnClick.setLabel("ADD POINT ON CLICK");
            else bAddPolyPointOnClick.setLabel("NO ADD POINT ON CLICK");
            println("ADD POLYLINE POINT ON CLICK: "+addPolyPointOnClick);
        }

        // EDIT POLYLINE POINT ON DRAG BUTTON
        else if(theControlEvent.isFrom("EDIT POLYLINE POINT ON DRAG")) {
            if(editPolyPointOnDrag) bEditPolyPointOnDrag.setLabel("EDIT POINT ON DRAG");
            else bEditPolyPointOnDrag.setLabel("NO EDIT POINT ON DRAG");
            println("EDIT BEZIER ON DRAG: "+editPolyPointOnDrag);
        }

        // CLOSE POLYGON BUTTON
        else if(theControlEvent.isFrom("CLOSE POLYLINE")) {
            println("CLOSE POLYLINE");
            closePolyPoint= true; closePolyLine();
            bAddPolyPointOnClick.setOff(); bEditPolyPointOnDrag.setOff();
            addPolyPointOnClick = false; editPolyPointOnDrag = false;
        }

    }

    void closePolyLine(){
        PVector v0 = polylinePoints.get(0).copy();
        polylinePoints.add(v0);
    }
}
