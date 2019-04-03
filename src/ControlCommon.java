import controlP5.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;
import java.util.List;

import static processing.core.PApplet.println;

public class ControlCommon {

    //CREATE
    Button bCreate, bReset, bPreview;
    boolean preview;

    //LAYER
    Slider nbLayer;
    int layer;

    //NUM. POINTS
    Slider nbNumPoints;
    int numPoints;

    // MASS (ATTRACTORS, REPELERS) RANGE
    Range rMassAtt, rMassRep;
    RangFloat massAtt, massRep;

    // RATIO ATTRACTORS VS REPELERS
    Slider sRatioAttRep;
    float ratioAttRep;

    // SPIN ANGLE RANGE
    Range rSpinAngle;
    RangFloat spinAng;

    // NUM. PARTICLES TO COLLAPSE RANGE
    Range rNumPart2Collpase;
    RangFloat np2Col;

    //COLLAPSEABLE
    Button bCollapse;
    boolean collapseable;

    //ENABLE
    Button bEnable;
    boolean enable;

    // MAP OPTIONS POINTS
    List mapPointOptions = Arrays.asList("NONE","RANDOM",
                                         "POSITION X", "POSITION X INV",
                                         "POSITION Y", "POSITION Y INV",
                                         "DISTANCE REF", "DISTANCE REF INV",
                                         "NUM POINT", "NUM POINT INV");

    ScrollableList slMapMassAtt, slMapMassRep, slMapSpinAngle, slMapNPCollapse;
    int mapMassAtt, mapMassRep, mapSpinAngle, mapNPCollapse;
    Range rMapMassAttIn, rMapMassRepIn, rMapSpinAngleIn, rMapNPCollapseIn;
    RangFloat mapInMassAtt, mapInMassRep, mapInSpinAngle, mapInNPCollapse;


    public ControlCommon(ControlWindow cw){
        setupCommonControls(cw);
    }

    void setupCommonControls(ControlWindow cw) {
        ControlP5 cp5 = cw.cp5;
        float marginLeft = cw.marginLeft;
        float marginTop = cw.marginTop;
        float rowStep = cw.rowStep;
        float colStep = cw.colStep;
        int rangeWidth = cw.rangeWidth;
        int controlHeight = cw.controlHeight;
        // PREVIEW
        bPreview = cp5.addButton("PREVIEW").setValue(0)
                .setPosition(marginLeft + 2*rangeWidth/4 +2, marginTop+ rowStep*2)
                .setSize(rangeWidth/4 -3,controlHeight)
                .setSwitch(true).setOff().setLabel("NO PREVIEW")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        preview = bPreview.isOn();
                    }
                })
        ;

        // CREATE
        bCreate = cp5.addButton("CREATE")
                .setPosition(marginLeft + 3*rangeWidth/4 +2, marginTop  + rowStep*2)
                .setSize(rangeWidth/4 -3,controlHeight)
                .moveTo("random")
        ;

        // RESET
        bReset = cp5.addButton("RESET").setPosition(marginLeft + rangeWidth/4 +2, marginTop  + rowStep*2)
                .setSize(rangeWidth/4 -3 ,controlHeight)
                .moveTo("random")
        ;

        // LAYER
        nbLayer = cp5.addSlider("LAYER").setPosition(marginLeft,marginTop)
                .setSize(rangeWidth,controlHeight)
                .setRange(Defaults.MIN_LAYER,Defaults.MAX_LAYER)
                .snapToTickMarks(true).setNumberOfTickMarks(6)
                .setValue(Defaults.MIN_LAYER).setValueLabel(""+Defaults.MIN_LAYER)
                .moveTo("random")
        ;

        layer = Defaults.MIN_LAYER;

        // NUM POINTS
        nbNumPoints = cp5.addSlider("POINTS").setPosition(marginLeft,marginTop + rowStep*1)
                .setSize(rangeWidth,controlHeight)
                .setRange(Defaults.MIN_POINTS,Defaults.MAX_POINTS)
                .setValue(Defaults.MIN_POINTS)
                .setValueLabel(""+Defaults.MIN_POINTS)
                .moveTo("random")
        ;
        numPoints = Defaults.MIN_POINTS;

        // ENABLE BUTTON
        bEnable = cp5.addButton("ENABLE").setValue(0)
                .setPosition(marginLeft, marginTop+ rowStep*2)
                .setSize(rangeWidth/4,controlHeight)
                .setSwitch(true).setOn().setLabel("ENABLED")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        enable = bEnable.isOn();
                    }
                })
        ;

        enable = true;

        //*********************************** MASS (MIN, MAX) RANGES, AND RATIO FOR ATTRACTORS & REPELERS ************************************//

        // MASS ATRACTORS RANGE
        rMassAtt = cp5.addRange("MASS ATT").setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*2)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setLabel("MASS ATT OUT")
                .setRange(Defaults.MIN_MASS_ATT, Defaults.MAX_MASS_ATT)
                .setRangeValues(Defaults.MIN_MASS_ATT, Defaults.MAX_MASS_ATT)
                .setBroadcast(true)
                .moveTo("random")
        ;
        massAtt = new RangFloat(Defaults.MIN_MASS_ATT, Defaults.MAX_MASS_ATT);

        // MASS REPELERS RANGE
        rMassRep = cp5.addRange("MASS REP").setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*5)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setLabel("MASS REP OUT")
                .setRange(Defaults.MIN_MASS_REP, Defaults.MAX_MASS_REP)
                .setRangeValues(Defaults.MIN_MASS_REP, Defaults.MAX_MASS_REP)
                .setBroadcast(true)
                .moveTo("random")
        ;
        massRep = new RangFloat(Defaults.MIN_MASS_REP, Defaults.MAX_MASS_REP);

        // RATIO ATTRACTORS VS REPELERS
        sRatioAttRep = cp5.addSlider("RATIO")
                .setPosition(marginLeft,marginTop + rowStep*3)
                .setSize(rangeWidth,controlHeight)
                .setRange(Defaults.MIN_RATIO, Defaults.MAX_RATIO)
                .setValue(Defaults.MAX_RATIO)
                .moveTo("random")
        ;
        ratioAttRep = Defaults.MAX_RATIO;

        //*********************************** SPIN ANGLE (MIN, MAX) RANGE ************************************//
        // SPIN ANGLE RANGE
        rSpinAngle = cp5.addRange("SPIN ANGLE")
                .setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*8)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setLabel("SPIN ANGLE OUT")
                .setRange(Defaults.MIN_SPIN, Defaults.MAX_SPIN)
                .setRangeValues(0,0).setBroadcast(true)
                .moveTo("random")
        ;
        spinAng = Defaults.rZERO.copy();

        //*********************************** NUM PARTICLES TO COLLPASE (MIN, MAX) RANGE ************************************//

        // COLLPASEABLE CHECKBOX
        bCollapse = cp5.addButton("COLLAPSEABLE").setValue(0)
                .setPosition(marginLeft, marginTop+ rowStep*4)
                .setSize(rangeWidth/4,controlHeight)
                .setSwitch(true).setOff().setLabel("COLLAPSE OFF")
                .moveTo("random")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        collapseable = bCollapse.isOn();
                    }
                })
        ;

        // NUM: PARTCILES TO COLLAPSE RANGE
        rNumPart2Collpase = cp5.addRange("NP COLLAPSE")
                .setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*7)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setLabel("COLLAPSE OUT")
                .setRange(Defaults.MIN_COLLAPSE, Defaults.MAX_COLLAPSE)
                .setRangeValues(Defaults.MIN_COLLAPSE, Defaults.MAX_COLLAPSE)
                .setBroadcast(true)
                .moveTo("random")
        ;

        np2Col = new RangFloat(Defaults.MIN_COLLAPSE,Defaults.MAX_COLLAPSE);


        //********************** MAP IN PARAMETERS () *****************//

        // MAPIN MASSATT RANGE
        rMapMassAttIn = cp5.addRange("MASS ATT IN").setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*1)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_X, Defaults.sceneWidth)
                .setRangeValues(Defaults.MIN_X, Defaults.sceneWidth)
                .setBroadcast(true)
                .moveTo("random")
        ;
        mapInMassAtt = new RangFloat(Defaults.MIN_X, Defaults.sceneWidth);



        // MAPIN MASSREP RANGE
        rMapMassRepIn = cp5.addRange("MASS REP IN").setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*4)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_X, Defaults.sceneWidth)
                .setRangeValues(Defaults.MIN_X, Defaults.sceneWidth)
                .setBroadcast(true)
                .moveTo("random")
        ;
        mapInMassRep = new RangFloat(Defaults.MIN_X, Defaults.sceneWidth);

        // MAPIN SPINANGLE RANGE
        rMapSpinAngleIn = cp5.addRange("SPIN ANGLE IN").setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*7)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_X, Defaults.sceneWidth)
                .setRangeValues(Defaults.MIN_X, Defaults.sceneWidth).setBroadcast(true)
                .moveTo("random")
        ;
        mapInSpinAngle = new RangFloat(Defaults.MIN_X, Defaults.sceneWidth);



        // MAPIN NP COLLAPSE RANGE
        rMapNPCollapseIn = cp5.addRange("NP COLLAPSE IN").setBroadcast(false)
                .setPosition(marginLeft,marginTop + rowStep*6)
                .setSize(rangeWidth,controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_X, Defaults.sceneWidth)
                .setRangeValues(Defaults.MIN_X, Defaults.sceneWidth)
                .setBroadcast(true)
                .moveTo("random")
        ;
        mapInNPCollapse = new RangFloat(Defaults.MIN_X, Defaults.sceneWidth);

        // SPINANGLE MAP IN SCROLL LIST
        slMapSpinAngle = cp5.addScrollableList("MAP SPIN ANGLE")
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*6)
                .setWidth(rangeWidth).setItemHeight(controlHeight).setBarHeight(controlHeight)
                .addItems(mapPointOptions)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("random")
        ;

        slMapSpinAngle.setValue(0.0f); mapSpinAngle = 0;

        // NP COLLAPSE MAP IN SCROLL LIST
        slMapNPCollapse = cp5.addScrollableList("MAP NP COLLAPSE").setPosition(marginLeft,marginTop + rowStep*5)
                .setWidth(rangeWidth).setItemHeight(controlHeight).setBarHeight(controlHeight)
                .addItems(mapPointOptions)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("random")
        ;

        slMapNPCollapse.setValue(0.0f); mapNPCollapse = 0;


        // MASSREP MAP IN SCROLL LIST
        slMapMassRep = cp5.addScrollableList("MAP MASS REP")
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*3)
                .setWidth(rangeWidth).setItemHeight(controlHeight).setBarHeight(controlHeight)
                .addItems(mapPointOptions)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("random")
        ;

        slMapMassRep.setValue(0.0f); mapMassRep = 0;

        // MASSATT MAP IN SCROLL LIST
        slMapMassAtt = cp5.addScrollableList("MAP MASS ATT")
                .setPosition(marginLeft + rangeWidth + colStep/4,marginTop + rowStep*0)
                .setWidth(rangeWidth).setItemHeight(controlHeight).setBarHeight(controlHeight)
                .addItems(mapPointOptions)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("random")
        ;

        slMapMassAtt.setValue(0.0f); mapMassAtt = 0;

    }

    public void resetControls(){
        rMassAtt.setRangeValues(Defaults.MIN_MASS_ATT,Defaults.MAX_MASS_ATT); 
        massAtt = new RangFloat(Defaults.MIN_MASS_ATT,Defaults.MAX_MASS_ATT);
        rMassRep.setRangeValues(Defaults.MIN_MASS_REP,Defaults.MAX_MASS_REP);
        massRep = new RangFloat(Defaults.MIN_MASS_REP,Defaults.MAX_MASS_REP);
        sRatioAttRep.setValue(Defaults.MAX_RATIO); ratioAttRep = Defaults.MAX_RATIO;
        rSpinAngle.setRangeValues(0,0); spinAng = Defaults.rZERO.copy();
        bCollapse.setOff(); collapseable = false;
        rNumPart2Collpase.setRangeValues(Defaults.MIN_COLLAPSE,Defaults.MAX_COLLAPSE);
        np2Col = new RangFloat(Defaults.MIN_COLLAPSE,Defaults.MAX_COLLAPSE);
        //rX.setRangeValues(Defaults.MIN_X,Defaults.sceneWidth).setBroadcast(true); xRange = new RangFloat(Defaults.MIN_X, Defaults.sceneWidth);
        //rY.setRangeValues(Defaults.MIN_Y,Defaults.sceneHeight); yRange = new RangFloat(Defaults.MIN_Y, Defaults.sceneHeight);
    }

    public void checkControlEvents(ControlEvent theControlEvent){
        // LAYER
        if(theControlEvent.isFrom("LAYER")) {
            layer = (int)nbLayer.getValue();
            nbLayer.setBroadcast(false); nbLayer.setValueLabel(""+layer); nbLayer.setBroadcast(true);
            println("Layer : "+layer);
        }
        // NUM. POINTS
        else if(theControlEvent.isFrom("POINTS")) {
            numPoints = (int)nbNumPoints.getValue();
            nbNumPoints.setBroadcast(false); nbNumPoints.setValueLabel(""+numPoints); nbNumPoints.setBroadcast(true);
            println("Num.Points : "+numPoints);
        }

        // MASS ATTRACTORS RANGE IN
        else if(theControlEvent.isFrom("MASS ATT IN")) {
            mapInMassAtt.setValues(rMapMassAttIn.getLowValue(), rMapMassAttIn.getHighValue());
            println("MASS ATT RANGE IN: "+mapInMassAtt);
        }
        // MASS ATTRACTORS RANGE OUT
        else if(theControlEvent.isFrom("MASS ATT")) {
            massAtt.setValues(rMassAtt.getLowValue(), rMassAtt.getHighValue());
            println("MASS ATT RANGE OUT: "+massAtt);
        }
        // MASS REPELERS RANGE IN
        else if(theControlEvent.isFrom("MASS REP IN")) {
            mapInMassRep.setValues(rMapMassRepIn.getLowValue(),rMapMassRepIn.getHighValue());
            println("MASS REP RANGE IN: "+mapInMassRep);
        }
        // MASS REPELERS RANGE
        else if(theControlEvent.isFrom("MASS REP")) {
            massRep.setValues(rMassRep.getLowValue(),rMassRep.getHighValue());
            println("MASS REP RANGE OUT: "+massRep);
        }
        // RATIO ATTRACTORS VS REPELERS
        else if(theControlEvent.isFrom("RATIO")) {
            ratioAttRep = sRatioAttRep.getValue();
            println("Ratio ATT vs REP : "+ratioAttRep);
        }
        // SPIN ANGLE RANGE IN
        else if(theControlEvent.isFrom("SPIN ANGLE IN")) {
            mapInSpinAngle.setValues(rMapSpinAngleIn.getLowValue(), rMapSpinAngleIn.getHighValue());
            println("SPIN ANGLE RANGE IN: "+mapInSpinAngle);
        }
        // SPIN ANGLE RANGE OUT
        else if(theControlEvent.isFrom("SPIN ANGLE")) {
            spinAng.setValues(rSpinAngle.getLowValue(), rSpinAngle.getHighValue());
            println("SPIN ANGLE RANGE OUT: "+spinAng);
        }
        // NUM. PARTICLES TO COLLAPSE RANGE IN
        else if(theControlEvent.isFrom("NP COLLAPSE IN")) {
            mapInNPCollapse.setValues(rMapNPCollapseIn.getLowValue(),rMapNPCollapseIn.getHighValue());
            println("NP COLLAPSE RANGE IN: "+mapInNPCollapse);
        }
        // NUM. PARTICLES TO COLLAPSE RANGE OUT
        else if(theControlEvent.isFrom("NP COLLAPSE")) {
            np2Col.setValues(rNumPart2Collpase.getLowValue(),rNumPart2Collpase.getHighValue());
            println("NP COLLAPSE RANGE OUT: "+np2Col);
        }
        // COLLAPSEABLE
        else if(theControlEvent.isFrom("COLLAPSEABLE")) {
            if(collapseable) bCollapse.setLabel("COLLAPSE ON");
            else bCollapse.setLabel("COLLAPSE OFF");
            println("Collapseable: "+collapseable);
        }
        // ENABLE
        else if(theControlEvent.isFrom("ENABLE")) {
            if(enable) bEnable.setLabel("ENABLED");
            else bEnable.setLabel("DISABLED");
            println("Enable: "+enable);
        }
        // SCROLL LIST MAP MASS ATT
        else if(theControlEvent.isFrom("MAP MASS ATT")) {
            mapMassAtt = (int)slMapMassAtt.getValue();
            println("MAP MASS ATT: "+mapMassAtt+" - "+mapPointOptions.get(mapMassAtt));
            switch(mapMassAtt){
                case 0: case 1: rMapMassAttIn.setRange(0, Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // NONE & RANDOM
                case 2: case 3: rMapMassAttIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // POS X & X INV
                case 4: case 5: rMapMassAttIn.setRange(0,Defaults.sceneHeight).setRangeValues(0, Defaults.sceneHeight); break; // POS Y & Y INV
                case 6: case 7: rMapMassAttIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // DIST REF & DIST REF INV
                case 8: case 9: rMapMassAttIn.setRange(0,numPoints).setRangeValues(0, numPoints); break; // DIST REF & DIST REF INV
                default:
            }
        }
        // SCROLL LIST MAP MASS REP
        else if(theControlEvent.isFrom("MAP MASS REP")) {
            mapMassRep = (int)slMapMassRep.getValue();
            println("MAP MASS REP: "+mapMassRep+" - "+mapPointOptions.get(mapMassRep));
            switch(mapMassRep){
                case 0: case 1: rMapMassRepIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // NONE & RANDOM
                case 2: case 3: rMapMassRepIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // POS X & X INV
                case 4: case 5: rMapMassRepIn.setRange(0,Defaults.sceneHeight).setRangeValues(0, Defaults.sceneHeight); break; // POS Y & Y INV
                case 6: case 7: rMapMassRepIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // DIST REF & DIST REF INV
                case 8: case 9: rMapMassRepIn.setRange(0,numPoints).setRangeValues(0, numPoints); break; // DIST REF & DIST REF INV
                default:
            }
        }
        // SCROLL LIST MAP SPIN ANGLE
        else if(theControlEvent.isFrom("MAP SPIN ANGLE")) {
            mapSpinAngle = (int)slMapSpinAngle.getValue();
            println("MAP SPIN ANGLE: "+mapSpinAngle+" - "+mapPointOptions.get(mapSpinAngle));
            switch(mapSpinAngle){
                case 0: case 1: rMapSpinAngleIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // NONE & RANDOM
                case 2: case 3: rMapSpinAngleIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // POS X & X INV
                case 4: case 5: rMapSpinAngleIn.setRange(0,Defaults.sceneHeight).setRangeValues(0, Defaults.sceneHeight); break; // POS Y & Y INV
                case 6: case 7: rMapSpinAngleIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // DIST REF & DIST REF INV
                case 8: case 9: rMapSpinAngleIn.setRange(0,numPoints).setRangeValues(0, numPoints); break; // DIST REF & DIST REF INV
                default:
            }
        }
        // SCROLL LIST MAP NP2COLLAPSE
        else if(theControlEvent.isFrom("MAP NP COLLAPSE")) {
            mapNPCollapse = (int)slMapNPCollapse.getValue();
            println("MAP NP COLLAPSE: "+mapNPCollapse+" - "+mapPointOptions.get(mapNPCollapse));
            switch(mapNPCollapse){
                case 0: case 1: rMapNPCollapseIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // NONE & RANDOM
                case 2: case 3: rMapNPCollapseIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // POS X & X INV
                case 4: case 5: rMapNPCollapseIn.setRange(0,Defaults.sceneHeight).setRangeValues(0, Defaults.sceneHeight); break; // POS Y & Y INV
                case 6: case 7: rMapNPCollapseIn.setRange(0,Defaults.sceneWidth).setRangeValues(0, Defaults.sceneWidth); break; // DIST REF & DIST REF INV
                case 8: case 9: rMapNPCollapseIn.setRange(0,numPoints).setRangeValues(0, numPoints); break; // DIST REF & DIST REF INV
                default:
            }
        }
    }

    public float getMappedValue(PApplet pA, int mapValue, RangFloat valueIn, RangFloat valueOut,
                                float distance, float age, PVector position, PVector refColor, PVector orientation){
        float s=0;
        float valueInMin = valueIn.getMinValue();
        float valueInMax = valueIn.getMaxValue();
        float valueOutMin = valueOut.getMinValue();
        float valueOutMax = valueOut.getMaxValue();

        //"NONE", "DISTANCE", "AGE","ORIENTATION X", "ORIENTATION Y", "POSITION X", "POSITION Y", "DISTANCE REF"
        switch(mapValue){
            case 0: s = valueOut.getMaxValue(); break; //NONE
            case 1: s = pA.map(distance, valueInMin, valueInMax, valueOutMin, valueOutMax); break;        // DISTANCE
            case 2: s = pA.map(distance, valueInMin, valueInMax, valueOutMax, valueOutMin); break;        // DISTANCE INV
            case 3: s = pA.map(age%500, valueInMin, valueInMax, valueOutMin, valueOutMax); break;    // AGE
            case 4: s = pA.map(age%500, valueInMin, valueInMax, valueOutMax, valueOutMin); break;    // AGE INV
            case 5: s = pA.map(orientation.x, valueInMin, valueInMax, valueOutMin, valueOutMax); break;   // ORIENTATION X
            case 6: s = pA.map(orientation.x, valueInMin, valueInMax, valueOutMax, valueOutMin); break;   // ORIENTATION X INV
            case 7: s = pA.map(orientation.y, valueInMin, valueInMax, valueOutMin, valueOutMax); break;  // ORIENTATION Y
            case 8: s = pA.map(orientation.y, valueInMin, valueInMax, valueOutMax, valueOutMin); break;  // ORIENTATION Y INV
            case 9: s = pA.map(position.x, valueInMin, valueInMax, valueOutMin, valueOutMax); break;     // POSITION X
            case 10: s = pA.map(position.x, valueInMin, valueInMax, valueOutMax, valueOutMin); break;     // POSITION X INV
            case 11: s = pA.map(position.y, valueInMin, valueInMax, valueOutMin, valueOutMax); break;     // POSITION Y
            case 12: s = pA.map(position.y, valueInMin, valueInMax, valueOutMax, valueOutMin); break;     // POSITION Y INV
            case 13: float distRef = pA.dist(position.x, position.y, refColor.x, refColor.y);
                    s = pA.map(distRef, valueInMin, valueInMax, valueOutMin, valueOutMax); break;       // DISTANCE REF COLOR
            case 14: float distRef2 = pA.dist(position.x, position.y, refColor.x, refColor.y);
                    s = pA.map(distRef2, valueInMin, valueInMax, valueOutMax, valueOutMin); break;       // DISTANCE REF COLOR INV
        }
        return s;
    }

}
