import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;

import static processing.core.PApplet.println;

public class ControlMouse {

    // MOUSE FUNCTIONALITY
    Button bMousePressedPoint, bMouseDraggedPoint, bDelMousePressedPoint, bDelMouseDraggedPoint;
    Button bMousePressedParticle, bMouseDraggedParticle, bDelMousePressedParticle, bDelMouseDraggedParticle;
    boolean createPointWithMousePressed, createPointWithMouseDragged, deletePointWithMousePressed, deletePointWithMouseDragged;
    boolean createParticleWithMousePressed, createParticleWithMouseDragged, deleteParticleWithMousePressed, deleteParticleWithMouseDragged;

    public ControlMouse(ControlWindow cw){
        setupMouseControls(cw);
    }


    public void setupMouseControls(ControlWindow cw){
        ControlP5 cp5 = cw.cp5;
        float marginLeft = cw.marginLeft;
        float marginTop = cw.marginTop;
        float rowStep = cw.rowStep;
        float colStep = cw.colStep;
        int rangeWidth = cw.rangeWidth;
        int controlHeight = cw.controlHeight;
        // ADD POINT MOUSE PRESSED  BUTTON
        bMousePressedPoint = cp5.addButton("PRESSED POINT")
                .setValue(0).setPosition(marginLeft, marginTop+ rowStep*14)
                .setSize(rangeWidth/2 -5,controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD POINT ON CLICK")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        createPointWithMousePressed = bMousePressedPoint.isOn();
                        if(createPointWithMousePressed){
                            bDelMousePressedPoint.setOff();
                            bMousePressedParticle.setOff();
                            bDelMousePressedParticle.setOff();
                        }
                    }
                })
        ;

        createPointWithMousePressed=false;

        // ADD POINT MOUSE DRAGGED  BUTTON
        bMouseDraggedPoint = cp5.addButton("DRAGGED POINT").setValue(0).setPosition(marginLeft + rangeWidth/2, marginTop+ rowStep*14).setSize(rangeWidth/2 -5,controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD POINT ON DRAG")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        createPointWithMouseDragged = bMouseDraggedPoint.isOn();
                        if(createPointWithMouseDragged){
                            bDelMouseDraggedPoint.setOff();
                            bMouseDraggedParticle.setOff();
                            bDelMouseDraggedParticle.setOff();
                        }
                    }
                })
        ;

        createPointWithMouseDragged=false;

        // DELETE POINT MOUSE PRESSED BUTTON
        bDelMousePressedPoint = cp5.addButton("DEL PRESSED POINT").setValue(0).setPosition(marginLeft, marginTop+ rowStep*15).setSize(rangeWidth/2 -5,controlHeight)
                .setSwitch(true).setOff().setLabel("NO DEL POINT ON CLICK")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        deletePointWithMousePressed = bDelMousePressedPoint.isOn();
                        if(deletePointWithMousePressed){
                            bMousePressedPoint.setOff();
                            bMousePressedParticle.setOff();
                            bDelMousePressedParticle.setOff();
                        }
                    }
                })
        ;

        deletePointWithMousePressed=false;

        // DELETE POINT MOUSE PRESSED BUTTON
        bDelMouseDraggedPoint = cp5.addButton("DEL DRAGGED POINT").setValue(0).setPosition(marginLeft + rangeWidth/2, marginTop+ rowStep*15).setSize(rangeWidth/2 -5,controlHeight)
                .setSwitch(true).setOff().setLabel("NO DEL POINT ON DRAG")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        deletePointWithMouseDragged = bDelMouseDraggedPoint.isOn();
                        if(deletePointWithMouseDragged){
                            bMouseDraggedPoint.setOff();
                            bMouseDraggedParticle.setOff();
                            bDelMouseDraggedParticle.setOff();
                        }
                    }
                })
        ;

        deletePointWithMouseDragged=false;


        // ADD PARTICLE MOUSE PRESSED  BUTTON
        bMousePressedParticle = cp5.addButton("PRESSED PARTICLE").setValue(0).setPosition(marginLeft, marginTop+ rowStep*17).setSize(rangeWidth/2 -5,controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD PARTICLE ON CLICK")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        createParticleWithMousePressed = bMousePressedParticle.isOn();
                        if(createParticleWithMousePressed){
                            bDelMousePressedParticle.setOff();
                            bMousePressedPoint.setOff();
                            bDelMousePressedPoint.setOff();
                        }
                    }
                })
        ;

        createParticleWithMousePressed=false;

        // ADD PARTICLE MOUSE DRAGGED  BUTTON
        bMouseDraggedParticle = cp5.addButton("DRAGGED PARTICLE").setValue(0).setPosition(marginLeft + rangeWidth/2, marginTop+ rowStep*17).setSize(rangeWidth/2 -5,controlHeight)
                .setSwitch(true).setOff().setLabel("NO ADD PARTICLE ON DRAG")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        createParticleWithMouseDragged = bMouseDraggedParticle.isOn();
                        if(createParticleWithMouseDragged){
                            bDelMouseDraggedParticle.setOff();
                            bMouseDraggedPoint.setOff();
                            bDelMouseDraggedPoint.setOff();
                        }
                    }
                })
        ;

        createParticleWithMouseDragged=false;

        // DELETE PARTICLE MOUSE PRESSED BUTTON
        bDelMousePressedParticle = cp5.addButton("DEL PRESSED PARTICLE").setValue(0).setPosition(marginLeft, marginTop+ rowStep*18).setSize(rangeWidth/2 -5,controlHeight)
                .setSwitch(true).setOff().setLabel("NO DEL PARTICLE ON CLICK")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        deleteParticleWithMousePressed = bDelMousePressedParticle.isOn();
                        if(deleteParticleWithMousePressed){
                            bMousePressedParticle.setOff();
                            bMousePressedPoint.setOff();
                            bDelMousePressedPoint.setOff();
                        }
                    }
                })
        ;

        deleteParticleWithMousePressed=false;

        // DELETE PARTICLE MOUSE PRESSED BUTTON
        bDelMouseDraggedParticle = cp5.addButton("DEL DRAGGED PARTICLE").setValue(0).setPosition(marginLeft + rangeWidth/2, marginTop+ rowStep*18).setSize(rangeWidth/2 -5,controlHeight)
                .setSwitch(true).setOff().setLabel("NO DEL PARTICLE ON DRAG")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        deleteParticleWithMouseDragged = bDelMouseDraggedParticle.isOn();
                        if(deleteParticleWithMouseDragged){
                            bMouseDraggedParticle.setOff();
                            bMouseDraggedPoint.setOff();
                            bDelMouseDraggedPoint.setOff();
                        }
                    }
                })
        ;

        deleteParticleWithMouseDragged=false;
    }

    public void resetControls(){

    }

    public void checkControlEvents(ControlEvent theControlEvent){
        // ADD POINT MOUSE PRESSED BUTTON
        if(theControlEvent.isFrom("PRESSED POINT")) {
            if(createPointWithMousePressed) bMousePressedPoint.setLabel("ADD POINT ON CLICK");
            else bMousePressedPoint.setLabel("NO ADD POINT ON CLICK");
            println("ADD POINT ON CLICK: "+createPointWithMousePressed);
        }
        // ADD POINT MOUSE DRAGGED BUTTON
        else if(theControlEvent.isFrom("DRAGGED POINT")) {
            if(createPointWithMouseDragged) bMouseDraggedPoint.setLabel("ADD POINT ON DRAG");
            else bMouseDraggedPoint.setLabel("NO ADD POINT ON DRAG");
            println("ADD POINT ON DRAG: "+createPointWithMouseDragged);
        }
        // DEL POINT MOUSE PRESSED BUTTON
        if(theControlEvent.isFrom("DEL PRESSED POINT")) {
            if(deletePointWithMousePressed) bDelMousePressedPoint.setLabel("DEL POINT ON CLICK");
            else bDelMousePressedPoint.setLabel("NO DEL POINT ON CLICK");
            println("DEL POINT ON CLICK: "+deletePointWithMousePressed);
        }
        // DEL POINT MOUSE DRAGGED BUTTON
        else if(theControlEvent.isFrom("DEL DRAGGED POINT")) {
            if(deletePointWithMouseDragged) bDelMouseDraggedPoint.setLabel("DEL POINT ON DRAG");
            else bDelMouseDraggedPoint.setLabel("NO DEL POINT ON DRAG");
            println("DEL POINT ON DRAG: "+deletePointWithMouseDragged);
        }

        // ADD PARTICLE MOUSE PRESSED BUTTON
        if(theControlEvent.isFrom("PRESSED PARTICLE")) {
            if(createParticleWithMousePressed) bMousePressedParticle.setLabel("ADD PARTICLE ON CLICK");
            else bMousePressedParticle.setLabel("NO ADD PARTICLE ON CLICK");
            println("ADD PARTICLE ON CLICK: "+createParticleWithMousePressed);
        }
        // ADD PARTICLE MOUSE DRAGGED BUTTON
        else if(theControlEvent.isFrom("DRAGGED PARTICLE")) {
            if(createParticleWithMouseDragged) bMouseDraggedParticle.setLabel("ADD PARTICLE ON DRAG");
            else bMouseDraggedParticle.setLabel("NO ADD PARTICLE ON DRAG");
            println("ADD PARTICLE ON DRAG: "+createParticleWithMouseDragged);
        }
        // DEL PARTICLE MOUSE PRESSED BUTTON
        if(theControlEvent.isFrom("DEL PRESSED PARTICLE")) {
            if(deleteParticleWithMousePressed) bDelMousePressedParticle.setLabel("DEL PARTICLE ON CLICK");
            else bDelMousePressedParticle.setLabel("NO DEL PARTICLE ON CLICK");
            println("DEL PARTICLE ON CLICK: "+deleteParticleWithMousePressed);
        }
        // DEL PARTICLE MOUSE DRAGGED BUTTON
        else if(theControlEvent.isFrom("DEL DRAGGED PARTICLE")) {
            if(deleteParticleWithMouseDragged) bDelMouseDraggedParticle.setLabel("DEL PARTICLE ON DRAG");
            else bDelMouseDraggedParticle.setLabel("NO DEL PARTICLE ON DRAG");
            println("DEL PARTICLE ON DRAG: "+deleteParticleWithMouseDragged);
        }
    }
}
