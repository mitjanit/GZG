import controlP5.*;

import java.util.Arrays;
import java.util.List;

import static processing.core.PApplet.println;

public class ControlFlow {

    List varOptions = Arrays.asList("NONE", "NEXT", "RANDOM", "INVERT");
    List varRefColorOptions = Arrays.asList("NONE", "RANDOM", "RANDOM STEP", "HORIZONTAL", "VERTICAL");

    ScrollableList slVarRed, slVarGreen, slVarBlue, slVarOpac, slVarWidth, slVarHeight, slVarRefColor;
    int varRed, varGreen, varBlue, varOpac, varWidth, varHeight, varRefColor;
    Range rVarRedIn, rVarRedOut, rVarGreenIn, rVarGreenOut, rVarBlueIn, rVarBlueOut, rVarOpacIn, rVarOpacOut, rVarRefColorIn, rVarRefColorOut;
    RangFloat varRedIn, varRedOut, varGreenIn, varGreenOut, varBlueIn, varBlueOut, varOpacIn, varOpacOut, varRefColorIn, varRefColorOut;
    Range rVarHeightIn, rVarHeightOut, rVarWidthIn, rVarWidthOut, rRefStep, rFlowFrames, rFlowTimes;
    RangFloat varHeightIn, varHeightOut, varWidthIn, varWidthOut, refStep, flowFrames, flowTimes;
    Slider sColor2BW;
    float color2bw;

    Button bAltColor, bFlowEnabled, bFlowUpdate, bFlowRandom;
    boolean altColor, flowEnabled, flowUpdate, flowRandom;

    Button bMinRedInVar, bMaxRedInVar, bMinRedOutVar, bMaxRedOutVar;
    Button bMinGreenInVar, bMaxGreenInVar, bMinGreenOutVar, bMaxGreenOutVar;
    Button bMinBlueInVar, bMaxBlueInVar, bMinBlueOutVar, bMaxBlueOutVar;
    Button bMinOpacInVar, bMaxOpacInVar, bMinOpacOutVar, bMaxOpacOutVar;
    Button bMinRefInVar, bMaxRefInVar, bMinRefOutVar, bMaxRefOutVar;
    Button bMinWidthInVar, bMaxWidthInVar, bMinWidthOutVar, bMaxWidthOutVar;
    Button bMinHeightInVar, bMaxHeightInVar, bMinHeightOutVar, bMaxHeightOutVar;

    boolean flowMinRedIn, flowMaxRedIn, flowMinRedOut, flowMaxRedOut;
    boolean flowMinGreenIn, flowMaxGreenIn, flowMinGreenOut, flowMaxGreenOut;
    boolean flowMinBlueIn, flowMaxBlueIn, flowMinBlueOut, flowMaxBlueOut;
    boolean flowMinOpacIn, flowMaxOpacIn, flowMinOpacOut, flowMaxOpacOut;
    boolean flowMinRefIn, flowMaxRefIn, flowMinRefOut, flowMaxRefOut;
    boolean flowMinWidthIn, flowMaxWidthIn, flowMinWidthOut, flowMaxWidthOut;
    boolean flowMinHeightIn, flowMaxHeightIn, flowMinHeightOut, flowMaxHeightOut;


    public ControlFlow(ControlWindow cw){
        setupFlowControls(cw);
    }


    public void setupFlowControls(ControlWindow cw){

        // VAR RED IN MIN BUTTON
        bMinRedInVar = cw.cp5.addButton("MIN R IN VAR")
                .setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*1)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinRedIn = bMinRedInVar.isOn();
                    }
                })
        ;

        flowMinRedIn = false;

        // VAR RED IN MAX BUTTON
        bMaxRedInVar = cw.cp5.addButton("MAX R IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*1)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxRedIn = bMaxRedInVar.isOn();
                    }
                })
        ;

        flowMaxRedIn = false;

        // VAR RED MAP IN
        rVarRedIn = cw.cp5.addRange("RED VAR IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*1)
                .setSize(cw.rangeWidth - 2*cw.controlHeight - 4,cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2)
                .setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varRedIn = Defaults.rZERO.copy();

        // VAR RED OUT MIN BUTTON
        bMinRedOutVar = cw.cp5.addButton("MIN R OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*2)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinRedOut = bMinRedOutVar.isOn();
                    }
                })
        ;

        flowMinRedOut = false;

        // VAR RED OUT MAX BUTTON
        bMaxRedOutVar = cw.cp5.addButton("MAX R OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*2)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxRedOut = bMaxRedOutVar.isOn();
                    }
                })
        ;

        flowMaxRedOut = false;


        // VAR RED MAP OUT
        rVarRedOut = cw.cp5.addRange("RED VAR OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*2)
                .setSize(cw.rangeWidth - 2*cw.controlHeight - 4,cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2)
                .setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varRedOut = Defaults.rZERO.copy();

        // RED VAR MODE
        slVarRed = cw.cp5.addScrollableList("VAR RED")
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*0)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(varOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slVarRed.setValue(0.0f); varRed = 0;

        // VAR GREEN IN MIN BUTTON
        bMinGreenInVar = cw.cp5.addButton("MIN G IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*4)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinGreenIn = bMinGreenInVar.isOn();
                    }
                })
        ;

        flowMinGreenIn = false;

        // VAR GREEN IN MAX BUTTON
        bMaxGreenInVar = cw.cp5.addButton("MAX G IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*4)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxGreenIn = bMaxGreenInVar.isOn();
                    }
                })
        ;

        flowMaxGreenIn = false;

        // VAR GREEN MAP IN
        rVarGreenIn = cw.cp5.addRange("GREEN VAR IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*4)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varGreenIn = Defaults.rZERO.copy();

        // VAR GREEN OUT MIN BUTTON
        bMinGreenOutVar = cw.cp5.addButton("MIN G OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*5)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinGreenOut = bMinGreenOutVar.isOn();
                    }
                })
        ;

        flowMinGreenOut = false;

        // VAR GREEN OUT MAX BUTTON
        bMaxGreenOutVar = cw.cp5.addButton("MAX G OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*5)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxGreenOut = bMaxGreenOutVar.isOn();
                    }
                })
        ;

        flowMaxGreenOut = false;

        // VAR GREEN MAP OUT
        rVarGreenOut = cw.cp5.addRange("GREEN VAR OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*5)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varGreenOut = Defaults.rZERO.copy();

        // GREEN VAR MODE
        slVarGreen = cw.cp5.addScrollableList("VAR GREEN")
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*3)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(varOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slVarGreen.setValue(0.0f); varGreen = 0;

        // VAR BLUE IN MIN BUTTON
        bMinBlueInVar = cw.cp5.addButton("MIN B IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*7)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinBlueIn = bMinBlueInVar.isOn();
                    }
                })
        ;

        flowMinBlueIn = false;

        // VAR BLUE IN MAX BUTTON
        bMaxBlueInVar = cw.cp5.addButton("MAX B IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*7)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxBlueIn = bMaxBlueInVar.isOn();
                    }
                })
        ;

        flowMaxBlueIn = false;

        // VAR BLUE MAP IN
        rVarBlueIn = cw.cp5.addRange("BLUE VAR IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*7)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varBlueIn = Defaults.rZERO.copy();

        // VAR BLUE OUT MIN BUTTON
        bMinBlueOutVar = cw.cp5.addButton("MIN B OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*8)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinBlueOut = bMinBlueOutVar.isOn();
                    }
                })
        ;

        flowMinBlueOut = false;

        // VAR BLUE OUT MAX BUTTON
        bMaxBlueOutVar = cw.cp5.addButton("MAX B OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*8)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxBlueOut = bMaxBlueOutVar.isOn();
                    }
                })
        ;

        flowMaxBlueOut = false;

        // VAR BLUE MAP OUT
        rVarBlueOut = cw.cp5.addRange("BLUE VAR OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*8)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varBlueOut = Defaults.rZERO.copy();

        // BLUE VAR MODE
        slVarBlue = cw.cp5.addScrollableList("VAR BLUE")
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*6)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(varOptions)
                .setType(ScrollableList.DROPDOWN)//.setType(ScrollableList.LIST)
                .moveTo("particles")
        ;

        slVarBlue.setValue(0.0f); varBlue = 0;

        // VAR OPAC IN MIN BUTTON
        bMinOpacInVar = cw.cp5.addButton("MIN O IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*11)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinOpacIn = bMinOpacInVar.isOn();
                    }
                })
        ;

        flowMinOpacIn = false;

        // VAR OPAC IN MAX BUTTON
        bMaxOpacInVar = cw.cp5.addButton("MAX O IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*11)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxOpacIn = bMaxOpacInVar.isOn();
                    }
                })
        ;

        flowMaxOpacIn = false;

        // VAR OPAC MAP IN
        rVarOpacIn = cw.cp5.addRange("OPAC VAR IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*11)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2)
                .setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varOpacIn = Defaults.rZERO.copy();

        // VAR OPAC OUT MIN BUTTON
        bMinOpacOutVar = cw.cp5.addButton("MIN O OUT VAR").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*12)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinOpacOut = bMinOpacOutVar.isOn();
                    }
                })
        ;

        flowMinOpacOut = false;

        // VAR OPAC OUT MAX BUTTON
        bMaxOpacOutVar = cw.cp5.addButton("MAX O OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*12)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxOpacOut = bMaxOpacOutVar.isOn();
                    }
                })
        ;

        flowMaxOpacOut = false;

        // VAR OPAC MAP OUT
        rVarOpacOut = cw.cp5.addRange("OPAC VAR OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*12)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2)
                .setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varOpacOut = Defaults.rZERO.copy();

        // OPAC VAR MODE
        slVarOpac = cw.cp5.addScrollableList("VAR OPAC")
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*10)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(varOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slVarOpac.setValue(0.0f); varOpac = 0;


        // FLOW FRAMES RANGE
        rFlowFrames = cw.cp5.addRange("FLOW FRAMES").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*14)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_FRAMES, Defaults.MAX_OSC_FRAMES)
                .setRangeValues(Defaults.MIN_OSC_FRAMES, Defaults.MAX_OSC_FRAMES).setBroadcast(true)
                .moveTo("particles")
        ;

        flowFrames = new RangFloat(Defaults.MIN_OSC_FRAMES, Defaults.MAX_OSC_FRAMES);


        // FLOW TIMES RANGE
        rFlowTimes = cw.cp5.addRange("FLOW TIMES").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*15)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_TIMES, Defaults.MAX_OSC_TIMES)
                .setRangeValues(Defaults.MIN_OSC_TIMES, Defaults.MAX_OSC_TIMES).setBroadcast(true)
                .moveTo("particles")
        ;

        flowTimes = new RangFloat(Defaults.MIN_OSC_TIMES, Defaults.MAX_OSC_TIMES);

        // FLOW ENABLED BUTTON
        bFlowEnabled = cw.cp5.addButton("FLOW ON").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*16)
                .setSize(cw.rangeWidth/3 - 3,cw.controlHeight)
                .setSwitch(true).setOn().setLabel("FLOW ON")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowEnabled = bFlowEnabled.isOn();
                    }
                })
        ;

        flowEnabled = true;

        // FLOW UPDATE BUTTON
        bFlowUpdate = cw.cp5.addButton("FLOW UPDATE").setValue(0)
                .setPosition(cw.marginLeft + 4*cw.rangeWidth/3 + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*16)
                .setSize(cw.rangeWidth/3 - 3,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("FLOW FIXED")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowUpdate = bFlowUpdate.isOn();
                    }
                })
        ;

        flowUpdate = false;

        // FLOW RANDOM BUTTON
        bFlowRandom = cw.cp5.addButton("FLOW RANDOM").setValue(0)
                .setPosition(cw.marginLeft + 5*cw.rangeWidth/3 + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*16)
                .setSize(cw.rangeWidth/3 - 3,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("FLOW RAND OFF")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowRandom = bFlowRandom.isOn();
                    }
                })
        ;

        flowRandom = false;


        // COLOR OR B&W ALTERNATE BUTTON

        bAltColor = cw.cp5.addButton("ALTERNATE").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*9)
                .setSize(cw.rangeWidth/3 - 3,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO ALTERNATE")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        altColor = bAltColor.isOn();
                    }
                })
        ;

        altColor = false;

        sColor2BW = cw.cp5.addSlider("COLOR VS GREY")
                .setPosition(cw.marginLeft + 4*cw.rangeWidth/3 + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*9)
                .setSize(2*cw.rangeWidth/3,cw.controlHeight).setRange(0,100).setValue(100)
                .moveTo("particles")
        ;
        color2bw = 100;



        // VAR REF STEP
        rRefStep = cw.cp5.addRange("REF STEP")
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*18)
                .setSize(cw.rangeWidth,cw.controlHeight).setRange(0,100).setRangeValues(0,100)
                .moveTo("particles")
        ;

        refStep = new RangFloat(0.0f,100.0f);

        // VAR REF COLOR IN MIN BUTTON
        bMinRefInVar = cw.cp5.addButton("MIN REF IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinRefIn = bMinRefInVar.isOn();
                    }
                })
        ;

        flowMinRefIn = false;

        // VAR REF IN MAX BUTTON
        bMaxRefInVar = cw.cp5.addButton("MAX REF IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*19)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxRefIn = bMaxRefInVar.isOn();
                    }
                })
        ;

        flowMaxRefIn = false;

        // VAR REF COLOR MAP IN
        rVarRefColorIn = cw.cp5.addRange("REF COLOR VAR IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*19)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2)
                .setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varRefColorIn = Defaults.rZERO.copy();

        // VAR REF COLOR OUT MIN BUTTON
        bMinRefOutVar = cw.cp5.addButton("MIN REF OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*20)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinRefOut = bMinRefOutVar.isOn();
                    }
                })
        ;

        flowMinRefOut = false;

        // VAR REF IN MAX BUTTON
        bMaxRefOutVar = cw.cp5.addButton("MAX REF OUT VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*20)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxRefOut = bMaxRefOutVar.isOn();
                    }
                })
        ;

        flowMaxRefOut = false;

        // VAR REF COLOR MAP OUT
        rVarRefColorOut = cw.cp5.addRange("REF COLOR VAR OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*20)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2)
                .setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varRefColorOut = Defaults.rZERO.copy();

        // REF COLOR VAR MODE
        slVarRefColor = cw.cp5.addScrollableList("VAR REF COLOR")
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*17)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(varRefColorOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slVarRefColor.setValue(0.0f); varRefColor = 0;


        // VAR WIDTH IN MIN BUTTON
        bMinWidthInVar = cw.cp5.addButton("MIN W IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*23)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinWidthIn = bMinWidthInVar.isOn();
                    }
                })
        ;

        flowMinWidthIn = false;

        // VAR WIDTH IN MAX BUTTON
        bMaxWidthInVar = cw.cp5.addButton("MAX W IN VAR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*23)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxWidthIn = bMaxWidthInVar.isOn();
                    }
                })
        ;

        flowMaxWidthIn = false;

        // VAR WIDTH MAP IN
        rVarWidthIn = cw.cp5.addRange("WIDTH VAR IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*23)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varWidthIn = Defaults.rZERO.copy();

        // VAR WIDTH OUT MIN BUTTON
        bMinWidthOutVar = cw.cp5.addButton("MIN W OUT VAR").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*24)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinWidthOut = bMinWidthOutVar.isOn();
                    }
                })
        ;

        flowMinWidthOut = false;

        // VAR WIDTH IN MAX BUTTON
        bMaxWidthOutVar = cw.cp5.addButton("MAX W OUT VAR").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*24)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxWidthOut = bMaxWidthOutVar.isOn();
                    }
                })
        ;

        flowMaxWidthIn = false;

        // VAR WIDTH MAP OUT
        rVarWidthOut = cw.cp5.addRange("WIDTH VAR OUT").setBroadcast(false).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*24)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varWidthOut = Defaults.rZERO.copy();

        // WIDTH VAR MODE
        slVarWidth = cw.cp5.addScrollableList("VAR WIDTH").setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*22)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(varOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slVarWidth.setValue(0.0f); varOpac = 0;


        // VAR HEIGHT IN MIN BUTTON
        bMinHeightInVar = cw.cp5.addButton("MIN H IN VAR").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*26)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinHeightIn = bMinHeightInVar.isOn();
                    }
                })
        ;

        flowMinHeightIn = false;

        // VAR HEIGHT IN MAX BUTTON
        bMaxHeightInVar = cw.cp5.addButton("MAX H IN VAR").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*26)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxHeightIn = bMaxHeightInVar.isOn();
                    }
                })
        ;

        flowMaxHeightIn = false;

        // VAR HEIGHT MAP IN
        rVarHeightIn = cw.cp5.addRange("HEIGHT VAR IN").setBroadcast(false).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*26)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varHeightIn = Defaults.rZERO.copy();


        // VAR HEIGHT OUT MIN BUTTON
        bMinHeightOutVar = cw.cp5.addButton("MIN H OUT VAR").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*27)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("<")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMinHeightOut = bMinHeightOutVar.isOn();
                    }
                })
        ;

        flowMinHeightOut = false;

        // VAR HEIGHT OUT MAX BUTTON
        bMaxHeightOutVar = cw.cp5.addButton("MAX H OUT VAR").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.controlHeight + 2, cw.marginTop+ cw.rowStep*27)
                .setSize(cw.controlHeight,cw.controlHeight)
                .setSwitch(true).setOff().setLabel(">")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        flowMaxHeightOut = bMaxHeightOutVar.isOn();
                    }
                })
        ;

        flowMaxHeightOut = false;

        // VAR WIDTH MAP OUT
        rVarHeightOut = cw.cp5.addRange("HEIGHT VAR OUT").setBroadcast(false).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep  + 2*cw.controlHeight + 4,cw.marginTop + cw.rowStep*27)
                .setSize(cw.rangeWidth  - (2*cw.controlHeight + 4),cw.controlHeight)
                .setRange(-Defaults.MAX_COLOR_IN/2, Defaults.MAX_COLOR_IN/2).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("particles")
        ;

        varHeightOut = Defaults.rZERO.copy();

        // HEIGHT VAR MODE
        slVarHeight = cw.cp5.addScrollableList("VAR HEIGHT").setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*25)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(varOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slVarHeight.setValue(0.0f); varHeight = 0;

    }

    public void resetControls(){

    }

    public void checkControlEvents(ControlEvent theControlEvent){
        // VAR RED
        if(theControlEvent.isFrom("VAR RED")) {
            varRed = (int)slVarRed.getValue();
            println("VAR RED: "+varRed+" - "+varOptions.get(varRed));
        }
        // VAR RED IN, MIN / MAX
        else if(theControlEvent.isFrom("RED VAR IN") || theControlEvent.isFrom("MIN R IN VAR") || theControlEvent.isFrom("MAX R IN VAR")) {
            varRedIn.setValues(rVarRedIn.getLowValue(), rVarRedIn.getHighValue(), flowMinRedIn, flowMaxRedIn);
            println("VAR RED IN: "+varRedIn);
        }
        // VAR RED OUT, MIN / MAX
        else if(theControlEvent.isFrom("RED VAR OUT") || theControlEvent.isFrom("MIN R OUT VAR") || theControlEvent.isFrom("MAX R OUT VAR")) {
            varRedOut.setValues(rVarRedOut.getLowValue(), rVarRedOut.getHighValue(), flowMinRedOut, flowMaxRedOut);
            println("VAR RED OUT: "+varRedOut);
        }

        // VAR GREEN
        else if(theControlEvent.isFrom("VAR GREEN")) {
            varGreen = (int)slVarGreen.getValue();
            println("VAR GREEN: "+varGreen+" - "+varOptions.get(varGreen));
        }
        // VAR GREEN IN, MIN / MAX
        else if(theControlEvent.isFrom("GREEN VAR IN") || theControlEvent.isFrom("MIN G IN VAR") || theControlEvent.isFrom("MAX G IN VAR")) {
            varGreenIn.setValues(rVarGreenIn.getLowValue(), rVarGreenIn.getHighValue(), flowMinGreenIn, flowMaxGreenIn);
            println("VAR GREEN IN: "+varGreenIn);
        }
        // VAR GREEN OUT, MIN / MAX
        else if(theControlEvent.isFrom("GREEN VAR OUT") || theControlEvent.isFrom("MIN G OUT VAR") || theControlEvent.isFrom("MAX G OUT VAR")) {
            varGreenOut.setValues(rVarGreenOut.getLowValue(), rVarGreenOut.getHighValue(), flowMinGreenOut, flowMaxGreenOut);
            println("VAR GREEN OUT: "+varGreenOut);
        }

        // VAR BLUE
        else if(theControlEvent.isFrom("VAR BLUE")) {
            varBlue = (int)slVarBlue.getValue();
            println("VAR BLUE: "+varBlue+" - "+varOptions.get(varBlue));
        }
        // VAR BLUE IN, MIN / MAX
        else if(theControlEvent.isFrom("BLUE VAR IN") || theControlEvent.isFrom("MIN B IN VAR") || theControlEvent.isFrom("MAX B IN VAR")) {
            varBlueIn.setValues(rVarBlueIn.getLowValue(), rVarBlueIn.getHighValue(), flowMinBlueIn, flowMaxBlueIn);
            println("VAR BLUE IN: "+varBlueIn);
        }
        // VAR BLUE OUT, MIN / MAX
        else if(theControlEvent.isFrom("BLUE VAR OUT") || theControlEvent.isFrom("MIN B OUT VAR") || theControlEvent.isFrom("MAX B OUT VAR")) {
            varBlueOut.setValues(rVarBlueOut.getLowValue(), rVarBlueOut.getHighValue(), flowMinBlueOut, flowMaxBlueOut);
            println("VAR BLUE OUT: "+varBlueOut);
        }

        // VAR OPAC
        else if(theControlEvent.isFrom("VAR OPAC")) {
            varOpac = (int)slVarOpac.getValue();
            println("VAR OPAC: "+varOpac+" - "+varOptions.get(varOpac));
        }
        // VAR OPAC IN, MIN / MAX
        else if(theControlEvent.isFrom("OPAC VAR IN") || theControlEvent.isFrom("MIN O IN VAR") || theControlEvent.isFrom("MAX O IN VAR")) {
            varOpacIn.setValues(rVarOpacIn.getLowValue(), rVarOpacIn.getHighValue(), flowMinOpacIn, flowMaxOpacIn);
            println("VAR OPAC IN: "+varOpacIn);
        }
        // VAR OPAC OUT, MIN / MAX
        else if(theControlEvent.isFrom("OPAC VAR OUT") || theControlEvent.isFrom("MIN O OUT VAR") || theControlEvent.isFrom("MAX O OUT VAR")) {
            varOpacOut.setValues(rVarOpacOut.getLowValue(), rVarOpacOut.getHighValue(), flowMinOpacOut, flowMaxOpacOut);
            println("VAR OPAC OUT: "+varOpacOut);
        }

        // VAR REF COLOR
        else if(theControlEvent.isFrom("VAR REF")) {
            varRefColor = (int)slVarRefColor.getValue();
            println("VAR REF COLOR: "+varRefColor+" - "+varRefColorOptions.get(varRefColor));
        }
        // VAR REF IN, MIN / MAX
        else if(theControlEvent.isFrom("REF COLOR VAR IN") || theControlEvent.isFrom("MIN REF IN VAR") || theControlEvent.isFrom("MAX REF IN VAR")) {
            varRefColorIn.setValues(rVarRefColorIn.getLowValue(), rVarRefColorIn.getHighValue(), flowMinRefIn, flowMaxRefIn);
            println("VAR REF IN: "+varRefColorIn);
        }
        // VAR REF OUT, MIN / MAX
        else if(theControlEvent.isFrom("REF COLOR VAR OUT") || theControlEvent.isFrom("MIN REF OUT VAR") || theControlEvent.isFrom("MAX REF OUT VAR")) {
            varRefColorOut.setValues(rVarRefColorOut.getLowValue(), rVarRefColorOut.getHighValue(), flowMinRefOut, flowMaxRefOut);
            println("VAR REF OUT: "+varRefColorOut);
        }

        // VAR WIDTH
        else if(theControlEvent.isFrom("VAR WIDTH")) {
            varWidth = (int)slVarWidth.getValue();
            println("VAR WIDTH: "+varWidth+" - "+varOptions.get(varWidth));
        }
        // VAR WIDTH IN, MIN / MAX
        else if(theControlEvent.isFrom("WIDTH VAR IN") || theControlEvent.isFrom("MIN W IN VAR") || theControlEvent.isFrom("MAX W IN VAR")) {
            varWidthIn.setValues(rVarWidthIn.getLowValue(), rVarWidthIn.getHighValue(), flowMinWidthIn, flowMaxWidthIn);
            println("VAR WIDTH IN: "+varWidthIn);
        }
        // VAR WIDTH OUT, MIN / MAX
        else if(theControlEvent.isFrom("WIDTH VAR OUT") || theControlEvent.isFrom("MIN W OUT VAR") || theControlEvent.isFrom("MAX W OUT VAR")) {
            varWidthOut.setValues(rVarWidthOut.getLowValue(), rVarWidthOut.getHighValue(), flowMinWidthOut, flowMaxWidthOut);
            println("VAR WIDTH OUT: "+varWidthOut);
        }

        // VAR HEIGHT
        else if(theControlEvent.isFrom("VAR HEIGHT")) {
            varHeight = (int)slVarHeight.getValue();
            println("VAR HEIGHT: "+varHeight+" - "+varOptions.get(varHeight));
        }
        // VAR HEIGHT IN, MIN / MAX
        else if(theControlEvent.isFrom("HEIGHT VAR IN") || theControlEvent.isFrom("MIN H IN VAR") || theControlEvent.isFrom("MAX H IN VAR")) {
            varHeightIn.setValues(rVarHeightIn.getLowValue(), rVarHeightIn.getHighValue(), flowMinHeightIn, flowMaxHeightIn);
            println("VAR HEIGHT IN: "+varHeightIn);
        }
        // VAR HEIGHT OUT, MIN / MAX
        else if(theControlEvent.isFrom("HEIGHT VAR OUT") || theControlEvent.isFrom("MIN H OUT VAR") || theControlEvent.isFrom("MAX H OUT VAR")) {
            varHeightOut.setValues(rVarHeightOut.getLowValue(), rVarHeightOut.getHighValue(), flowMinHeightOut, flowMaxHeightOut);
            println("VAR HEIGHT OUT: "+varHeightOut);
        }

        // ALTERNATE COLOR - B&W
        else if(theControlEvent.isFrom("ALTERNATE")) {
            if(altColor) bAltColor.setLabel("ALTERNATE");
            else bAltColor.setLabel("NO ALTERNATE");
            println("ALTERNATE: "+altColor);
        }
        // ADAPTOR LENGTH OFFSET
        else if(theControlEvent.isFrom("COLOR VS GREY")) {
            color2bw = sColor2BW.getValue();
            println("Color2BW : "+color2bw);
        }

        // FLOW ON
        else if(theControlEvent.isFrom("FLOW ON")) {
            if(flowEnabled) bFlowEnabled.setLabel("FLOW ON");
            else bFlowEnabled.setLabel("FLOW OFF");
            println("FLOW ENABLED: "+flowEnabled);
        }
        // FLOW UPDATE
        else if(theControlEvent.isFrom("FLOW UPDATE")) {
            if(flowUpdate) bFlowUpdate.setLabel("FLOW UPDATE");
            else bFlowUpdate.setLabel("FLOW FIXED");
            println("FLOW UPDATE: "+flowUpdate);
        }
        // FLOW RANDOM
        else if(theControlEvent.isFrom("FLOW RANDOM")) {
            if(flowRandom) bFlowRandom.setLabel("FLOW RAND ON");
            else bFlowRandom.setLabel("FLOW RAND OFF");
            println("FLOW RANDOM: "+flowRandom);
        }
        // FLOW FRAMES RANGE
        else if(theControlEvent.isFrom("FLOW FRAMES")) {
            flowFrames.setValues(rFlowFrames.getLowValue(), rFlowFrames.getHighValue());
            println("FLOW FRAMES: "+flowFrames);
        }
        // FLOW TIMES RANGE
        else if(theControlEvent.isFrom("FLOW TIMES")) {
            flowTimes.setValues(rFlowTimes.getLowValue(), rFlowTimes.getHighValue());
            println("FLOW TIMES: "+flowTimes);
        }
    }

}
