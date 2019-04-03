import controlP5.*;
import static processing.core.PApplet.println;


public class ControlRandom {

    // AREA (X, Y) RANGE
    Range rX, rY;
    RangFloat xRange, yRange;

    //*********** SYMMETRY CONTROLS ********//
    Button bMirrorX, bMirrorY, bQuad, bHexa, bClone, bInvert, bRandom, bShuffle;
    boolean mirrorX, mirrorY, quadSim, hexaSim, clone, invert, randomize, shuffle;
    
    public ControlRandom(ControlWindow cw){
        setupRandomControls(cw);
    }
    
    public void setupRandomControls(ControlWindow cw){
        // X RANGE
        rX = cw.cp5.addRange("X").setBroadcast(false).setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*9)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_X, Defaults.sceneWidth)
                .setRangeValues(Defaults.MIN_X, Defaults.sceneWidth).setBroadcast(true)
                .moveTo("random")
        ;
        xRange = new RangFloat(Defaults.MIN_X, Defaults.sceneWidth);

        // Y RANGE
        rY = cw.cp5.addRange("Y").setBroadcast(false).setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*10)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_Y, Defaults.sceneHeight)
                .setRangeValues(Defaults.MIN_Y, Defaults.sceneHeight).setBroadcast(true)
                .moveTo("random")
        ;
        yRange = new RangFloat(Defaults.MIN_Y, Defaults.sceneHeight);

        // MIRROR X BUTTON
        bMirrorX = cw.cp5.addButton("MIRROR X").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*11)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO MIRROR X")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        mirrorX = bMirrorX.isOn();
                    }
                })
        ;

        // MIRROR Y BUTTON
        bMirrorY = cw.cp5.addButton("MIRROR Y").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth/4, cw.marginTop+ cw.rowStep*11).setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO MIRROR Y")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        mirrorY = bMirrorY.isOn();
                    }
                })
        ;

        // QUAD SYMMETRY BUTTON
        bQuad = cw.cp5.addButton("QUAD").setValue(0).setPosition(cw.marginLeft  + 2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*11).setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO QUAD")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        quadSim = bQuad.isOn();
                    }
                })
        ;

        // HEXA SYMMETRY BUTTON
        bHexa = cw.cp5.addButton("HEXA").setValue(0).setPosition(cw.marginLeft  + 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*11).setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO HEXA")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        hexaSim = bHexa.isOn();
                    }
                })
        ;

        // COPY BUTTON
        bClone = cw.cp5.addButton("CLONE").setValue(0).setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*12)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO CLONE")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        clone = bClone.isOn();
                    }
                })
        ;

        // INVERT BUTTON
        bInvert = cw.cp5.addButton("INVERT").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/4, cw.marginTop+ cw.rowStep*12)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO INVERT")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        invert = bInvert.isOn();
                    }
                })
        ;

        // RANDOM BUTTON
        bRandom = cw.cp5.addButton("RANDOM").setValue(0)
                .setPosition(cw.marginLeft  + 2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*12)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO RANDOM")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        randomize = bRandom.isOn();
                    }
                })
        ;

        // SHUFFLE BUTTON
        bShuffle = cw.cp5.addButton("SHUFFLE").setValue(0)
                .setPosition(cw.marginLeft  + 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*12)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO SHUFFLE")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        shuffle = bShuffle.isOn();
                    }
                })
        ;


    }

    public void resetControls(){
        rX.setRangeValues(Defaults.MIN_X,Defaults.sceneWidth).setBroadcast(true); xRange = new RangFloat(Defaults.MIN_X, Defaults.sceneWidth);
        rY.setRangeValues(Defaults.MIN_Y,Defaults.sceneHeight); yRange = new RangFloat(Defaults.MIN_Y, Defaults.sceneHeight);
        bMirrorX.setOff(); mirrorX = false; bMirrorY.setOff(); mirrorY = false;
        bQuad.setOff(); quadSim = false; bHexa.setOff(); hexaSim = false;
        bClone.setOff(); clone = false; bInvert.setOff(); invert = false;
        bRandom.setOff(); randomize = false; bShuffle.setOff(); shuffle = false;
    }

    void checkControlEvents(ControlEvent theControlEvent){
        // MIRROR X
        if(theControlEvent.isFrom("MIRROR X")) {
            if(mirrorX) bMirrorX.setLabel("MIRROR X");
            else bMirrorX.setLabel("NO MIRROR X");
            println("MIRROR X: "+mirrorX);
        }
        // MIRROR y
        else if(theControlEvent.isFrom("MIRROR Y")) {
            if(mirrorY) bMirrorY.setLabel("MIRROR Y");
            else bMirrorY.setLabel("NO MIRROR Y");
            println("MIRROR Y: "+mirrorY);
        }
        // QUAD SIMMETRY X
        else if(theControlEvent.isFrom("QUAD")) {
            if(quadSim) bQuad.setLabel("QUAD");
            else bQuad.setLabel("NO QUAD");
            println("QUAD SYMMETRY: "+quadSim);
        }
        // HEXA SIMMETRY X
        else if(theControlEvent.isFrom("HEXA")) {
            if(hexaSim) bHexa.setLabel("HEXA");
            else bHexa.setLabel("NO HEXA");
            println("HEXA SYMMETRY: "+hexaSim);
        }
        // CLONE
        else if(theControlEvent.isFrom("CLONE")) {
            if(clone) bClone.setLabel("CLONE");
            else bClone.setLabel("NO CLONE");
            println("CLONE: "+clone);
        }
        // INVERT
        else if(theControlEvent.isFrom("INVERT")) {
            if(invert) bInvert.setLabel("INVERT");
            else bInvert.setLabel("NO INVERT");
            println("INVERT: "+invert);
        }
        // RANDOMIZE
        else if(theControlEvent.isFrom("RANDOM")) {
            if(randomize) bRandom.setLabel("RANDOM");
            else bRandom.setLabel("NO RANDOM");
            println("RANDOMIZE: "+randomize);
        }
        // SHUFFLE
        else if(theControlEvent.isFrom("SHUFFLE")) {
            if(shuffle) bShuffle.setLabel("SHUFFLE");
            else bShuffle.setLabel("NO SHUFFLE");
            println("SHUFFLE: "+shuffle);
        }
        // X RANGE
        else if(theControlEvent.isFrom("X")) {
            xRange.setValues(rX.getLowValue(), rX.getHighValue());
            println("X RANGE: "+xRange);
        }
        // Y RANGE
        else if(theControlEvent.isFrom("Y")) {
            yRange.setValues(rY.getLowValue(),rY.getHighValue());
            println("Y RANGE: "+yRange);
        }
    }
}
