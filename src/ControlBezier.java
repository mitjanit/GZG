import controlP5.*;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.println;

public class ControlBezier  {

    //*********** BEZIER CONTROLS ******************//
    String createText = "Click to add a point.";
    String editText = "Drag and drop to edit.";
    Button bSaveAnchor, bRemoveBezier, bRemoveLastBezier, bCloseBezier, bAddBezierOnClick, bEditBezierOnDrag;
    Button bSetRect, bSetCurve, bSetQuadratic;
    boolean createBezier, saveAnchor, closeBezier, addBezierOnClick, editBezierOnDrag;
    boolean curveMode, rectMode, quadraticMode;
    boolean saveVertex;

    ArrayList<BezierCurve> beziers = new ArrayList<BezierCurve>();
    ArrayList<PVector> draggingPoints = new ArrayList<PVector>();

    public ControlBezier(ControlWindow cw){
        setupBezierControls(cw);
    }

    public void setupBezierControls(ControlWindow cw){
        // SET BEZIER MODE BUTTON
        bSetCurve = cw.cp5.addButton("CURVE MODE").setValue(0).setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*18)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOn().setLabel("CURVE ON")
                .moveTo("bezier")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        curveMode = bSetCurve.isOn();
                        if(curveMode){ bSetRect.setOff(); bSetQuadratic.setOff();}
                    }
                })
        ;

        curveMode=true;

        // SET RECT BUTTON
        bSetRect = cw.cp5.addButton("RECT MODE").setValue(0).setPosition(cw.marginLeft+cw.rangeWidth/4, cw.marginTop+ cw.rowStep*18)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("RECT ON")
                .moveTo("bezier")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        rectMode = bSetRect.isOn();
                        if(rectMode){ bSetCurve.setOff(); bSetQuadratic.setOff();}
                    }
                })
        ;

        rectMode=false;

        // SET QUADRATIC BUTTON
        bSetQuadratic = cw.cp5.addButton("QUADRATIC MODE").setValue(0)
                .setPosition(cw.marginLeft+2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*18)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("QUAD ON")
                .moveTo("bezier")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        quadraticMode = bSetQuadratic.isOn();
                        if(quadraticMode){ bSetCurve.setOff(); bSetRect.setOff();}
                    }
                })
        ;

        quadraticMode=false;


        // SAVE ANCHOR BUTTON
        bSaveAnchor = cw.cp5.addButton("SAVE ANCHOR").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("bezier")
        ;

        saveVertex=false;

        // REMOVE LAST BEZIER CURVE BUTTON
        bRemoveLastBezier = cw.cp5.addButton("REMOVE LAST BEZIER").setLabel("REMOVE LAST")
                .setValue(0).setPosition(cw.marginLeft+cw.rangeWidth/4, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("bezier")
        ;

        // REMOVE ALL BEZIER CURVES BUTTON
        bRemoveBezier = cw.cp5.addButton("REMOVE ALL BEZIER").setLabel("REMOVE ALL").setValue(0)
                .setPosition(cw.marginLeft+2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("bezier")
        ;

        // CLOSE BEZIER CURVES BUTTON
        bCloseBezier = cw.cp5.addButton("CLOSE BEZIER").setValue(0)
                .setPosition(cw.marginLeft + 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("bezier")
        ;

        closeBezier=false;
        beziers = new ArrayList<BezierCurve>();

        // ADD ANCHOR MOUSE PRESSED  BUTTON
        bAddBezierOnClick = cw.cp5.addButton("ADD BEZIER ON CLICK").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*20)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD BEZIER ON CLICK")
                .moveTo("bezier")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        addBezierOnClick = bAddBezierOnClick.isOn();
                        if(addBezierOnClick) bEditBezierOnDrag.setOff();
                    }
                })
        ;

        addBezierOnClick=false;

        // ADD VERTEX MOUSE PRESSED  BUTTON
        bEditBezierOnDrag = cw.cp5.addButton("EDIT BEZIER ON DRAG").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/2, cw.marginTop+ cw.rowStep*20)
                .setSize(cw.rangeWidth/2 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO EDIT BEZIER ON DRAG")
                .moveTo("bezier")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        editBezierOnDrag = bEditBezierOnDrag.isOn();
                        if(editBezierOnDrag) bAddBezierOnClick.setOff();
                    }
                })
        ;

        editBezierOnDrag=false;

    }

    public void resetControls(){
        saveVertex=false;
        closeBezier=false;
        beziers.clear();
        bAddBezierOnClick.setOff(); addBezierOnClick=false;
        bEditBezierOnDrag.setOff(); editBezierOnDrag=false;
    }

    public void checkControlEvents(ControlEvent theControlEvent, ControlWindow cw){
        // CURVE MODE (DEFAULT)
        if(theControlEvent.isFrom("CURVE MODE")) {
            if(curveMode) bSetCurve.setLabel("CURVE ON");
            else bSetCurve.setLabel("CURVE OFF");
            println("CURVE MODE: "+curveMode);
            if(curveMode){
                for(BezierCurve bc : beziers){
                    bc.setCurve();
                }
            }
        }
        // RECT MODE (ANCHORS = CONTROLS)
        else if(theControlEvent.isFrom("RECT MODE")) {
            if(rectMode) bSetRect.setLabel("RECT ON");
            else bSetRect.setLabel("RECT OFF");
            println("RECT MODE: "+rectMode);
            if(rectMode){
                for(BezierCurve bc : beziers){
                    bc.setRect();
                }
            }
        }
        // QUADRATIC MODE (EQUAL CONTROLS)
        else if(theControlEvent.isFrom("QUADRATIC MODE")) {
            if(quadraticMode) bSetQuadratic.setLabel("QUAD ON");
            else bSetQuadratic.setLabel("QUAD OFF");
            println("QUADRATIC MODE: "+quadraticMode);
            if(quadraticMode){
                for(BezierCurve bc : beziers){
                    bc.setQuadratic();
                }
            }
        }
        // SAVE BEZIER ANCHOR BUTTON
        else if(theControlEvent.isFrom("SAVE ANCHOR")) {

            PVector a1, a2, c1, c2;
            PVector c = cw.cCircle.centre.copy();
            float dc = 50;

            if(beziers.size()>0){
                BezierCurve previousCurve = beziers.get(beziers.size()-1);
                a1 = previousCurve.a2.copy();
            }
            else {
                a1 = new PVector(Defaults.screenWidth/2, Defaults.screenHeight/2);
                c1 = new PVector(a1.x-dc,a1.y);
            }
            c1 = new PVector(a1.x-dc,a1.y);
            a2 = new PVector(constrain(c.x,10, Defaults.screenWidth-10),constrain(c.y, 10, Defaults.screenHeight-10));
            c2 = new PVector(constrain(a2.x+dc, 10, Defaults.screenWidth-10),constrain(a2.y, 10, Defaults.screenHeight-10));

            BezierCurve bc = new BezierCurve(c1,c2,a1,a2);
            beziers.add(bc);
            println("SAVE BEZIER ANCHOR ON "+c);
        }
        // REMOVE LAST BEZIER BUTTON
        else if(theControlEvent.isFrom("REMOVE LAST BEZIER")) {
            int n = beziers.size();
            if(n>0) beziers.remove(n-1);
            println("REMOVE LAST BEZIER");
        }
        // REMOVE ALL BEZIER BUTTON
        else if(theControlEvent.isFrom("REMOVE ALL BEZIER")) {
            beziers.clear();
            println("REMOVE ALL BEZIERS");
        }
        // ADD VERTEX ON CLICK BUTTON
        else if(theControlEvent.isFrom("ADD BEZIER ON CLICK")) {
            if(addBezierOnClick) bAddBezierOnClick.setLabel("ADD BEZIER ON CLICK");
            else bAddBezierOnClick.setLabel("NO ADD BEZIER ON CLICK");
            println("ADD BEZIER ON CLICK: "+addBezierOnClick);
        }
        // EDIT BEZIER ON DRAG BUTTON
        else if(theControlEvent.isFrom("EDIT BEZIER ON DRAG")) {
            if(editBezierOnDrag) bEditBezierOnDrag.setLabel("EDIT BEZIER ON DRAG");
            else bEditBezierOnDrag.setLabel("NO EDIT BEZIER ON DRAG OFF");
            println("EDIT BEZIER ON DRAG: "+editBezierOnDrag);
        }
        // CLOSE POLYGON BUTTON
        else if(theControlEvent.isFrom("CLOSE BEZIER")) {
            println("CLOSE BEZIER");
            closeBezier = true; closeBezier();
            bAddBezierOnClick.setOff(); bEditBezierOnDrag.setOff();
            addBezierOnClick = false; editBezierOnDrag = false;
        }
    }

    void closeBezier(){
        float dc = 50;
        PVector a2 = beziers.get(0).a1, c2 = new PVector(constrain(a2.x+dc, 10, Defaults.screenWidth-10),constrain(a2.y, 10, Defaults.screenHeight-10));
        PVector a1 = beziers.get(beziers.size()-1).a2, c1 = new PVector(a1.x-dc,a1.y);
        BezierCurve bc = new BezierCurve(c1, c2, a1, a2);
        beziers.add(bc);
    }
}