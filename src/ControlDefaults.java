import controlP5.*;

import java.util.Arrays;
import java.util.List;

import static processing.core.PApplet.println;
import static processing.core.PApplet.radians;

public class ControlDefaults {

    List angleOptions = Arrays.asList("0", "15", "30", "45", "60", "75", "90", "120", "270", "315");
    List blendOptions = Arrays.asList("BLEND", "ADD", "SUBTRACT", "DARKEST", "LIGHTEST", "DIFFERENCE", "EXCLUSION", "MULTIPLY", "SCREEN", "REPLACE");


    //DEFAULT
    Button bResetBackground, bLiveColor;
    Button bDisplayAll, bDisplayPoints, bDisplayParticles, bDisplaySources;
    Button bRemovePoints, bRemoveParticles, bRemoveSources, bRemoveAll;
    Button bFade, bFadeIn, bFadeOut;
    Button bSaveFrame, bSavePDF, bSaveVideo, bSaveBig;
    Button bDivGravity, bDivSpinAngle;
    boolean divGravity, divSpinAngle;
    boolean resetBackground, liveColor, displayAll=true, displaySources=true, displayPoints = true, displayParticles = true;
    boolean fadeEffect, fadeIn, fadeOut;

    // TRANSFORMATIONS
    Button bResetTRS;
    Range rRotateX, rRotateY, rRotateZ;
    RangFloat rotX, rotY, rotZ;
    ScrollableList slAngles;
    Button bRX, bRY, bRZ;
    Range rTranslateX, rTranslateY, rTranslateZ;
    RangFloat transX, transY, transZ;
    Range rScaleX, rScaleY, rScaleZ;
    RangFloat scaleX, scaleY, scaleZ;

    ScrollableList slBlendMode;
    int blendM;

    Slider sRedBG, sGreenBG, sBlueBG;
    float redBG, greenBG, blueBG;
    Slider sFadeAmount, sFadeFrames;
    float fadeAmount = 0.0f, fadeFrames=0.0f;

    ControlDefaults(ControlWindow cw){
        setupDefaultControls(cw);
    }

    void setupDefaultControls(ControlWindow cw){
        ControlP5 cp5 = cw.cp5;
        float marginLeft = cw.marginLeft;
        float marginTop = cw.marginTop;
        float rowStep = cw.rowStep;
        float colStep = cw.colStep;
        int rangeWidth = cw.rangeWidth;
        int controlHeight = cw.controlHeight;

        // RESET BACKGROUND
        bResetBackground= cp5.addButton("RESET BG").setPosition(marginLeft, marginTop)
                             .setSize(rangeWidth/2 -2,controlHeight)
                             .moveTo("default")
        ;

        // FADE
        bLiveColor = cp5.addButton("LIVE COLOR").setValue(0).
                setPosition(marginLeft  + rangeWidth/2, marginTop)
                .setSize(rangeWidth/2 -2 ,controlHeight)
                .setSwitch(true).setOff().setLabel("LIVE COLOR OFF")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        liveColor = bLiveColor.isOn();
                    }
                })
        ;

        liveColor = false;

        // BACKGROUND COLOR
        sRedBG = cp5.addSlider("RED BG").setPosition(marginLeft,marginTop + rowStep*1)
                .setSize(rangeWidth,controlHeight)
                .setRange(Defaults.MIN_COLOR_OUT,Defaults.MAX_COLOR_OUT)
                .setValue(Defaults.MAX_COLOR_OUT)//.setSliderMode(Slider.FLEXIBLE)
                .moveTo("default")
        ;
        redBG = Defaults.MAX_COLOR_OUT;

        sGreenBG = cp5.addSlider("GREEN BG").setPosition(marginLeft,marginTop + rowStep*2)
                .setSize(rangeWidth,controlHeight)
                .setRange(Defaults.MIN_COLOR_OUT,Defaults.MAX_COLOR_OUT)
                .setValue(Defaults.MAX_COLOR_OUT)
                .moveTo("default")
        ;
        greenBG = Defaults.MAX_COLOR_OUT;

        sBlueBG = cp5.addSlider("BLUE BG").setPosition(marginLeft,marginTop + rowStep*3)
                .setSize(rangeWidth,controlHeight)
                .setRange(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT)
                .setValue(Defaults.MAX_COLOR_OUT)
                .moveTo("default")
        ;
        blueBG = Defaults.MAX_COLOR_OUT;

        // FADE
        bFade = cp5.addButton("FADE").setValue(0).setPosition(marginLeft, marginTop+ rowStep*4).setSize(rangeWidth/3 -3 ,controlHeight)
                .setSwitch(true).setOff().setLabel("NO FADE")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        fadeEffect = bFade.isOn();
                    }
                })
        ;

        fadeEffect = false;

        bFadeIn = cp5.addButton("FADE IN").setValue(0)
                .setPosition(marginLeft + rangeWidth/3, marginTop+ rowStep*4)
                .setSize(rangeWidth/3 -3 ,controlHeight)
                .setSwitch(true).setOff().setLabel("NO FADE IN")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        fadeIn = bFadeIn.isOn();
                    }
                })
        ;

        fadeIn = false;

        bFadeOut = cp5.addButton("FADE OUT").setValue(0)
                .setPosition(marginLeft + 2*rangeWidth/3, marginTop+ rowStep*4)
                .setSize(rangeWidth/3 -3 ,controlHeight)
                .setSwitch(true).setOff().setLabel("NO FADE OUT")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        fadeOut = bFadeOut.isOn();
                    }
                })
        ;

        fadeOut = false;

        sFadeAmount = cp5.addSlider("FADE AMOUNT")
                .setPosition(marginLeft,marginTop + rowStep*5)
                .setSize(rangeWidth,controlHeight).setRange(0,10)
                .setValue(5)
                .moveTo("default")
        ;
        fadeAmount = 5;

        sFadeFrames = cp5.addSlider("FADE FRAMES")
                .setPosition(marginLeft,marginTop + rowStep*6)
                .setSize(rangeWidth,controlHeight)
                .setRange(Defaults.MIN_OSC_FRAMES, Defaults.MAX_OSC_FRAMES)
                .setValue(Defaults.MIN_OSC_FRAMES)
                .moveTo("default")
        ;
        fadeFrames = Defaults.MIN_OSC_FRAMES;

        // REMOVE ALL (POINTS & PARTICLES) FROM ALL LAYERS
        bRemoveAll = cp5.addButton("REMOVE ALL")
                .setPosition(marginLeft, marginTop  + rowStep*9)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .moveTo("default")
        ;

        // REMOVE POINTS FROM ALL LAYERS
        bRemovePoints = cp5.addButton("R. POINTS")
                .setPosition(marginLeft + rangeWidth/4, marginTop  + rowStep*9)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .moveTo("default")
        ;

        // REMOVE PARTICLES FROM ALL LAYERS
        bRemoveParticles = cp5.addButton("R. PARTICLES")
                .setPosition(marginLeft + 2*rangeWidth/4, marginTop  + rowStep*9)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .moveTo("default")
        ;

        // REMOVE SOURCES FROM ALL LAYERS
        bRemoveSources = cp5.addButton("R. SOURCES")
                .setPosition(marginLeft + 3*rangeWidth/4, marginTop  + rowStep*9)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .moveTo("default")
        ;


        // DISPLAY POINTS
        bDisplayAll = cp5.addButton("SHOW ALL").setValue(0)
                .setPosition(marginLeft, marginTop+ rowStep*10)
                .setSize(rangeWidth/4 - 3 ,controlHeight)
                .setSwitch(true).setOn().setLabel("SHOW ALL")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        displayAll = bDisplayAll.isOn();
                    }
                })
        ;

        // DISPLAY POINTS
        bDisplayPoints = cp5.addButton("SHOW POINTS").setValue(0)
                .setPosition(marginLeft + rangeWidth/4, marginTop+ rowStep*10)
                .setSize(rangeWidth/4 - 3 ,controlHeight)
                .setSwitch(true).setOff().setLabel("SHOW POINTS")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        displayPoints = bDisplayPoints.isOn();
                    }
                })
        ;

        displayPoints = false;

        // DISPLAY PARTICLES
        bDisplayParticles = cp5.addButton("SHOW PARTICLES").setValue(0)
                .setPosition(marginLeft + 2*rangeWidth/4 , marginTop+ rowStep*10)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .setSwitch(true).setOn().setLabel("SHOW PARTICLES")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        displayParticles = bDisplayParticles.isOn();
                    }
                })
        ;

        displayParticles = true;

        // DISPLAY SOURCES
        bDisplaySources = cp5.addButton("SHOW SOURCES").setValue(0)
                .setPosition(marginLeft + 3*rangeWidth/4 , marginTop+ rowStep*10)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .setSwitch(true).setOn().setLabel("SHOW SOURCES")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        displaySources = bDisplaySources.isOn();
                    }
                })
        ;

        displaySources = true;


        // TRANSLATE X
        rTranslateX = cp5.addRange("TRANSLATE X").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*12)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_TRANS_X,Defaults.MAX_TRANS_X).setRangeValues(0,0)
                .setBroadcast(true)
                .moveTo("default")
        ;
        transX = Defaults.rZERO.copy();

        // TRANSLATE Y
        rTranslateY = cp5.addRange("TRANSLATE Y").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*13)
                .setSize(rangeWidth,controlHeight)
                .setHandleSize(20)
                .setRange(Defaults.MIN_TRANS_Y,Defaults.MAX_TRANS_Y)
                .setRangeValues(0,0)
                .setBroadcast(true)
                .moveTo("default")
        ;
        transY = Defaults.rZERO.copy();

        // TRANSLATE Z
        rTranslateZ = cp5.addRange("TRANSLATE Z").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*14)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_TRANS_Z,Defaults.MAX_TRANS_Z)
                .setRangeValues(0,0).setBroadcast(true)
                .moveTo("default")
        ;
        transZ = Defaults.rZERO.copy();

        // ROTATE X
        rRotateX = cp5.addRange("ROTATE X").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*16)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_ROT,Defaults.MAX_ROT)
                .setRangeValues(0,0)
                .setBroadcast(true)
                .moveTo("default")
        ;
        rotX = Defaults.rZERO.copy();

        // ROTATE Y
        rRotateY = cp5.addRange("ROTATE Y").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*17)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_ROT,Defaults.MAX_ROT)
                .setRangeValues(0,0)
                .setBroadcast(true)
                .moveTo("default")
        ;
        rotY = Defaults.rZERO.copy();

        // ROTATE Z
        rRotateZ = cp5.addRange("ROTATE Z").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*18)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_ROT, Defaults.MAX_ROT)
                .setRangeValues(0,0)
                .setBroadcast(true)
                .moveTo("default")
        ;
        rotZ = Defaults.rZERO.copy();



        // RX BUTTON
        bRX = cp5.addButton("RX")
                .setPosition(marginLeft + rangeWidth/4, marginTop  + rowStep*19)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .moveTo("default")
        ;

        // RX BUTTON
        bRY = cp5.addButton("RY")
                .setPosition(marginLeft + 2*rangeWidth/4, marginTop  + rowStep*19)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .moveTo("default")
        ;

        // RX BUTTON
        bRZ = cp5.addButton("RZ")
                .setPosition(marginLeft + 3*rangeWidth/4, marginTop  + rowStep*19)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .moveTo("default")
        ;

        // SCALE X
        rScaleX = cp5.addRange("SCALE X")
                .setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*21)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_SCALE,Defaults.MAX_SCALE)
                .setRangeValues(1,1)
                .setBroadcast(true)
                .moveTo("default")
        ;
        scaleX = Defaults.rONE.copy();

        // SCALE Y
        rScaleY = cp5.addRange("SCALE Y").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*22)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_SCALE, Defaults.MAX_SCALE)
                .setRangeValues(1,1)
                .setBroadcast(true)
                .moveTo("default")
        ;
        scaleY = Defaults.rONE.copy();

        // SCALE Z
        rScaleZ = cp5.addRange("SCALE Z").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*23)
                .setSize(rangeWidth,controlHeight)
                .setHandleSize(20)
                .setRange(Defaults.MIN_SCALE,Defaults.MAX_SCALE)
                .setRangeValues(1,1)
                .setBroadcast(true)
                .moveTo("default")
        ;
        scaleZ = Defaults.rONE.copy();

        // ANGLES LIST

        slAngles = cp5.addScrollableList("ANGLES")
                .setPosition(marginLeft,marginTop + rowStep*19)
                //.setSize(rangeWidth,controlHeight)
                .setWidth(rangeWidth/4 -3).setItemHeight(controlHeight)
                .setBarHeight(controlHeight).addItems(angleOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("default")
        ;

        slAngles.setValue(0);

        // RESET ROT+SCALE+TRANS BUTTON
        bResetTRS = cp5.addButton("RESET TRS")
                .setPosition(marginLeft, marginTop  + rowStep*25)
                .setSize(rangeWidth ,controlHeight)
                .moveTo("default")
        ;


        // BLEND MODE SCROLL LIST
        slBlendMode = cp5.addScrollableList("BLEND MODE")
                .setPosition(marginLeft,marginTop + rowStep*7)
                .setWidth(rangeWidth).setItemHeight(controlHeight)
                .setBarHeight(controlHeight)
                .addItems(Defaults.blendOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("default")
        ;

        slBlendMode.setValue(0.0f); blendM = 0;


        // DIV BY GRAVITY & SPIN ANGLE

        bDivGravity = cp5.addButton("DIV GRAVITY").setValue(0)
                .setPosition(marginLeft + rangeWidth + colStep/4, marginTop + rowStep*4)
                .setSize(rangeWidth/2 -2 ,controlHeight)
                .setSwitch(true).setOn().setLabel("DIV GRAVITY OFF")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        divGravity = bDivGravity.isOn();
                    }
                })
        ;

        divGravity = true;

        bDivSpinAngle = cp5.addButton("DIV SPIN ANGLE").setValue(0)
                .setPosition(marginLeft + 3*rangeWidth/2 + colStep/4, marginTop + rowStep*4)
                .setSize(rangeWidth/2 -2 ,controlHeight)
                .setSwitch(true).setOn().setLabel("DIV SPIN ANGLE OFF")
                .moveTo("default")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        divSpinAngle = bDivSpinAngle.isOn();
                    }
                })
        ;

        divSpinAngle = true;


        // OUTPUT BUTTONSreset

        bSaveFrame = cp5.addButton("SAVE FRAME").setValue(0)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop)
                .setSize(rangeWidth,controlHeight)
                .moveTo("default")
        ;
        bSavePDF = cp5.addButton("SAVE PDF").setValue(0)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep)
                .setSize(rangeWidth,controlHeight)
                .moveTo("default")
        ;
        bSaveVideo = cp5.addButton("SAVE VIDEO").setValue(0)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*2)
                .setSize(rangeWidth,controlHeight)
                .moveTo("default")
        ;

        bSaveBig = cp5.addButton("SAVE BIG").setValue(0)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*3)
                .setSize(rangeWidth,controlHeight)
                .moveTo("default")
        ;
    }

    public void resetControls(){
        sRedBG.setValue(Defaults.MAX_COLOR_OUT); redBG = Defaults.MAX_COLOR_OUT;
        sGreenBG.setValue(Defaults.MAX_COLOR_OUT); greenBG = Defaults.MAX_COLOR_OUT;
        sBlueBG.setValue(Defaults.MAX_COLOR_OUT); blueBG = Defaults.MAX_COLOR_OUT;
        bFade.setOff(); fadeEffect = false;
        bFadeIn.setOff(); fadeIn = false;
        bFadeOut.setOff(); fadeOut = false;
        sFadeAmount.setValue(5); fadeAmount = 5;
        sFadeFrames.setValue(Defaults.MIN_OSC_FRAMES); fadeFrames = Defaults.MIN_OSC_FRAMES;
        // TRANSFORMATIONS
        rTranslateX.setRangeValues(0,0);
        transX = Defaults.rZERO.copy();
        rTranslateY.setRangeValues(0,0); transY = Defaults.rZERO.copy();
        rTranslateZ.setRangeValues(0,0); transZ = Defaults.rZERO.copy();
        rRotateX.setRangeValues(0,0); rotX = Defaults.rZERO.copy();
        rRotateY.setRangeValues(0,0); rotY = Defaults.rZERO.copy();
        rRotateZ.setRangeValues(0,0); rotZ = Defaults.rZERO.copy();
        rScaleX.setRangeValues(1,1); scaleX = Defaults.rONE.copy();
        rScaleY.setRangeValues(1,1); scaleY = Defaults.rONE.copy();
        rScaleZ.setRangeValues(1,1); scaleZ = Defaults.rONE.copy();
        slAngles.setValue(0);
    }

    public void checkControlEvents(ControlEvent theControlEvent){
        if(theControlEvent.isFrom("RESET BG")) {
            resetBackground = true;
            println("RESET BACKGROUND ");
        }
        else if(theControlEvent.isFrom("LIVE COLOR")) {
            if(liveColor) bLiveColor.setLabel("LIVE COLOR ON");
            else bLiveColor.setLabel("LIVE COLOR OFF");
            println("LIVE COLOR: "+liveColor);
        }
        else if(theControlEvent.isFrom("RED BG")) {
            redBG = sRedBG.getValue();
            println("RED GB : "+redBG);
        }
        else if(theControlEvent.isFrom("GREEN BG")) {
            greenBG = sGreenBG.getValue();
            println("GREEN BG : "+greenBG);
        }
        else if(theControlEvent.isFrom("BLUE BG")) {
            blueBG = sBlueBG.getValue();
            println("BLUE BG : "+blueBG);
        }
        else if(theControlEvent.isFrom("FADE")) {
            if(fadeEffect) bFade.setLabel("FADE");
            else bFade.setLabel("NO FADE");
            println("FADE EFFECT: "+fadeEffect);
        }
        else if(theControlEvent.isFrom("FADE IN")) {
            if(fadeIn) bFadeIn.setLabel("FADE IN");
            else bFadeIn.setLabel("NO FADE IN");
            println("FADE IN: "+fadeIn);
        }
        else if(theControlEvent.isFrom("FADE OUT")) {
            if(fadeOut) bFadeOut.setLabel("FADE OUT");
            else bFadeOut.setLabel("NO FADE OUT");
            println("FADE OUT: "+fadeOut);
        }
        else if(theControlEvent.isFrom("FADE AMOUNT")) {
            fadeAmount = sFadeAmount.getValue();
            println("FADE AMOUNT : "+fadeAmount);
        }
        else if(theControlEvent.isFrom("FADE FRAMES")) {
            fadeFrames = sFadeFrames.getValue();
            println("FADE FRAMES : "+fadeFrames);
        }
        else if(theControlEvent.isFrom("SHOW ALL")) {
            if(displayAll){
                bDisplayAll.setLabel("SHOW ALL");
                bDisplayPoints.setOn(); bDisplayParticles.setOn(); bDisplaySources.setOn();
            }
            else {
                bDisplayAll.setLabel("HIDE ALL");
                bDisplayPoints.setOff(); bDisplayParticles.setOff(); bDisplaySources.setOff();
            }
            println("DISPLAY ALL: "+displayAll);
        }
        else if(theControlEvent.isFrom("SHOW POINTS")) {
            if(displayPoints) bDisplayPoints.setLabel("SHOW POINTS");
            else bDisplayPoints.setLabel("HIDE POINTS");
            println("DISPLAY POINTS: "+displayPoints);
        }
        else if(theControlEvent.isFrom("SHOW PARTICLES")) {
            if(displayParticles) bDisplayParticles.setLabel("SHOW PARTICLES");
            else bDisplayParticles.setLabel("HIDE PARTICLES");
            println("DISPLAY PARTICLES: "+displayParticles);
        }
        else if(theControlEvent.isFrom("SHOW SOURCES")) {
            if(displaySources) bDisplaySources.setLabel("SHOW SOURCES");
            else bDisplaySources.setLabel("HIDE SOURCES");
            println("DISPLAY SOURCES: "+displaySources);
        }
        else if(theControlEvent.isFrom("REMOVE ALL")) {
            //removeAll();
            println("REMOVE ALL");
        }
        else if(theControlEvent.isFrom("R. POINTS")) {
            //removeAllPoints();
            println("REMOVE ALL POINTS");
        }
        else if(theControlEvent.isFrom("R. PARTICLES")) {
            //removeAllParticles();
            println("REMOVE ALL PARTICLES");
        }
        else if(theControlEvent.isFrom("R. SOURCES")) {
            //removeAllSources();
            println("REMOVE ALL SOURCES");
        }
        // TRANSFORMATIONS
        // TRANS X
        else if(theControlEvent.isFrom("TRANSLATE X")) {
            transX.setValues(rTranslateX.getLowValue(), rTranslateX.getHighValue());
            println("TRANSLATE X : "+transX);
        }
        // TRANS Y
        else if(theControlEvent.isFrom("TRANSLATE Y")) {
            transY.setValues(rTranslateY.getLowValue(), rTranslateY.getHighValue());
            println("TRANSLATE Y : "+transY);
        }
        // TRANS Z
        else if(theControlEvent.isFrom("TRANSLATE Z")) {
            transZ.setValues(rTranslateZ.getLowValue(), rTranslateZ.getHighValue());
            println("TRANSLATE Z : "+transZ);
        }
        // ROT X
        else if(theControlEvent.isFrom("ROTATE X")) {
            rotX.setValues(rRotateX.getLowValue(), rRotateX.getHighValue());
            println("ROTATE X : "+rotX);
        }
        // ROT Y
        else if(theControlEvent.isFrom("ROTATE Y")) {
            rotY.setValues(rRotateY.getLowValue(), rRotateY.getHighValue());
            println("ROTATE Y : "+rotY);
        }
        // ROT Z
        else if(theControlEvent.isFrom("ROTATE Z")) {
            rotZ.setValues(rRotateZ.getLowValue(), rRotateZ.getHighValue());
            println("ROTATE Z : "+rotZ);
        }
        // ANGLE ROT
        else if(theControlEvent.isFrom("ANGLES")) {
            int n = (int)slAngles.getValue();
            String sang = (String)angleOptions.get(n);
            println("ANG: "+sang);
        }
        // RX BUTTON
        else if(theControlEvent.isFrom("RX")) {
            int n = (int)slAngles.getValue();
            String sang = (String)angleOptions.get(n);
            float rd = radians(Float.parseFloat(sang));
            rRotateX.setRangeValues(rd, rd); rotX.setValues(rd, rd);
        }
        // RY BUTTON
        else if(theControlEvent.isFrom("RY")) {
            int n = (int)slAngles.getValue();
            String sang = (String)angleOptions.get(n);
            float rd = radians(Float.parseFloat(sang));
            rRotateY.setRangeValues(rd, rd); rotY.setValues(rd, rd);
        }
        // RZ BUTTON
        else if(theControlEvent.isFrom("RZ")) {
            int n = (int)slAngles.getValue();
            String sang = (String)angleOptions.get(n);
            float rd = radians(Float.parseFloat(sang));
            rRotateZ.setRangeValues(rd, rd); rotZ.setValues(rd, rd);
        }
        // SCALE X
        else if(theControlEvent.isFrom("SCALE X")) {
            scaleX.setValues(rScaleX.getLowValue(), rScaleX.getHighValue());
            println("SCALE X : "+scaleX);
        }
        // SCALE Y
        else if(theControlEvent.isFrom("SCALE Y")) {
            scaleY.setValues(rScaleY.getLowValue(), rScaleZ.getHighValue());
            println("SCALE Y : "+scaleY);
        }
        // SCALE Z
        else if(theControlEvent.isFrom("SCALE Z")) {
            scaleZ.setValues(rScaleY.getLowValue(), rScaleZ.getHighValue());
            println("SCALE Z : "+scaleZ);
        }
        // RESET TRS
        else if(theControlEvent.isFrom("RESET TRS")){
            rTranslateX.setRangeValues(0,0); transX = Defaults.rZERO.copy();
            rTranslateY.setRangeValues(0,0); transY = Defaults.rZERO.copy();
            rTranslateZ.setRangeValues(0,0); transZ = Defaults.rZERO.copy();
            rRotateX.setRangeValues(0,0); rotX = Defaults.rZERO.copy();
            rRotateY.setRangeValues(0,0); rotY = Defaults.rZERO.copy();
            rRotateZ.setRangeValues(0,0); rotZ = Defaults.rZERO.copy();
            rScaleX.setRangeValues(1,1); scaleX = Defaults.rONE.copy();
            rScaleY.setRangeValues(1,1); scaleY = Defaults.rONE.copy();
            rScaleZ.setRangeValues(1,1); scaleZ = Defaults.rONE.copy();
            slAngles.setValue(0);
            println("RESET TRS");
        }
        // DIV GRAVITY BUTTOM
        else if(theControlEvent.isFrom("DIV GRAVITY")){
            if(divGravity) bDivGravity.setLabel("DIV GRAVITY ON");
            else bDivGravity.setLabel("DIV GRAVITY OFF");
            println("DIV GRAVITY: "+divGravity);
        }
        // DIV SPIN ANGLE BUTTOM
        else if(theControlEvent.isFrom("DIV SPIN ANGLE")){
            if(divSpinAngle) bDivSpinAngle.setLabel("DIV SPIN ANGLE ON");
            else bDivSpinAngle.setLabel("DIV SPIN ANGLE OFF");
            println("DIV SPIN ANGLE: "+divSpinAngle);
        }
    }

}
