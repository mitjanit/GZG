import controlP5.*;
import geomerative.RG;

import java.util.Arrays;
import java.util.List;

import static controlP5.ControlP5Constants.*;
import static processing.core.PApplet.println;

public class ControlText {

    //************* TEXT CONTROLS **************//
    String word, fontFamily;
    Textfield tWord;
    ScrollableList slFontFamily, slFontAlignX, slFontAlignY, slPolygonizerMode;
    int fontAlignX, fontAlignY, polyMode, numPolyMode;
    Slider sFontSize, sPolyLength, sPolyAngle, sPolyStep, sAdaptScale, sAdaptOffset;
    float fontSize, polyLength, polyAngle, polyStep, adaptorScale, adaptorOffset;
    Range rRotAngle;
    RangFloat rotAngle;
    Button bLetterCenter;
    boolean letterCenter;

    String[] fontList = {"cubic.ttf","Basica.ttf","Qubix.ttf", "Remark.ttf","Supreme.ttf"};

    int[] fontAlignXValues  = {CENTER,LEFT,RIGHT},
            fontAlignYValues  = {TOP,BOTTOM,CENTER, BASELINE},
            polygonizerValues = {RG.BYPOINT, RG.BYELEMENTPOSITION, RG.BYELEMENTINDEX, RG.ADAPTATIVE, RG.UNIFORMLENGTH, RG.UNIFORMSTEP};

    List fontNames        = Arrays.asList(fontList);
    List fontAlignXNames  = Arrays.asList("CENTER", "LEFT","RIGHT");
    List fontAlignYNames  = Arrays.asList("TOP", "BOTTOM","CENTER", "BASELINE");
    List polygonizerNames = Arrays.asList("BY POINT","BY ELEMENT POSITION", "BY ELEMENT INDEX", "ADAPTATIVE", "UNIFORM LENGTH", "UNIFORM STEP");


    public ControlText(ControlWindow cw){
        setupTextControls(cw);
    }

    public void setupTextControls(ControlWindow cw){
        // Text
        RG.init(cw);
        // WORD
        tWord = cw.cp5.addTextfield("WORD")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*18)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setAutoClear(false) //.setFont(font)
                .moveTo("text")
        ;

        Label label1 = tWord.getCaptionLabel();
        label1.setText("WORD").align(ControlP5.RIGHT_OUTSIDE, CENTER).getStyle().setPaddingLeft(5);

        word = "";


        // FONT SIZE
        sFontSize = cw.cp5.addSlider("FONT SIZE")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*19)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FONTSIZE, Defaults.MAX_FONTSIZE)
                .setValue(Defaults.MIN_FONTSIZE)
                .moveTo("text")
        ;

        fontSize = Defaults.MIN_FONTSIZE;
        sFontSize.setBroadcast(false); sFontSize.setValueLabel(""+(int)fontSize);sFontSize.setBroadcast(true);

        // POLY MODE

        slPolygonizerMode = cw.cp5.addScrollableList("POLYMODE").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*22)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth/3 -2).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight).addItems(polygonizerNames)
                .setType(ScrollableList.LIST)
                //.setType(ScrollableList.DROPDOWN)
                .moveTo("text")
        ;

        slPolygonizerMode.setValue(4.0f); polyMode = polygonizerValues[4]; numPolyMode = 4;

        // POLY LENGTH

        sPolyLength = cw.cp5.addSlider("POLY LENGTH")
                .setPosition(cw.marginLeft+ cw.rangeWidth/3 +2,cw.marginTop + cw.rowStep*22)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_POLY_LENGTH,Defaults.MAX_POLY_LENGTH)
                .setValue(Defaults.MIN_POLY_LENGTH)
                .moveTo("text")
        ;
        polyLength = Defaults.MIN_POLY_LENGTH;

        sPolyStep = cw.cp5.addSlider("POLY STEP")
                .setPosition(cw.marginLeft+ cw.rangeWidth/3 +2,cw.marginTop + cw.rowStep*23)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_POLY_STEP,Defaults.MAX_POLY_STEP)
                .setValue(Defaults.MIN_POLY_STEP)
                .moveTo("text")
        ;
        polyStep = Defaults.MIN_POLY_STEP;

        sPolyAngle = cw.cp5.addSlider("POLY ANGLE")
                .setPosition(cw.marginLeft+ cw.rangeWidth/3 +2,cw.marginTop + cw.rowStep*24)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_POLY_ANGLE,Defaults.MAX_POLY_ANGLE).setValue(Defaults.MIN_POLY_ANGLE)
                .moveTo("text")
        ;
        polyAngle = Defaults.MIN_POLY_ANGLE;

        sAdaptScale = cw.cp5.addSlider("ADAPT SCALE")
                .setPosition(cw.marginLeft+ cw.rangeWidth/3 +2,cw.marginTop + cw.rowStep*25)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_ADAPT_SCALE,Defaults.MAX_ADAPT_SCALE).setValue(Defaults.MIN_ADAPT_SCALE)
                .moveTo("text")
        ;
        adaptorScale = Defaults.MIN_ADAPT_SCALE;

        sAdaptOffset = cw.cp5.addSlider("ADAPT OFFSET")
                .setPosition(cw.marginLeft+ cw.rangeWidth/3 +2,cw.marginTop + cw.rowStep*26)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_ADAPT_OFFSET,Defaults.MAX_ADAPT_OFFSET)
                .setValue(Defaults.MIN_ADAPT_OFFSET)
                .moveTo("text")
        ;
        adaptorOffset = Defaults.MIN_ADAPT_OFFSET;

        // FONT FAMILY

        slFontFamily = cw.cp5.addScrollableList("FONT FAMILY")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*21)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth/3 -2).setItemHeight(cw.controlHeight)
                .setBarHeight(cw.controlHeight).addItems(fontNames)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("text")
        ;

        slFontFamily.setValue(0); fontFamily = fontList[0];

        // FONT ALIGN X
        slFontAlignX = cw.cp5.addScrollableList("ALIGN X")
                .setPosition(cw.marginLeft + cw.rangeWidth/3,cw.marginTop + cw.rowStep*21)
                //.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth/3 -2).setItemHeight(cw.controlHeight)
                .setBarHeight(cw.controlHeight).addItems(fontAlignXNames)
                .setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("text")
        ;

        slFontAlignX.setValue(0.0f); fontAlignX = fontAlignXValues[0];

        // FONT ALIGN Y

        slFontAlignY = cw.cp5.addScrollableList("ALIGN Y")
                .setPosition(cw.marginLeft + 2*cw.rangeWidth/3 +2 ,cw.marginTop + cw.rowStep*21)
                //.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth/3 -2).setItemHeight(cw.controlHeight)
                .setBarHeight(cw.controlHeight).addItems(fontAlignYNames)
                .setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("text")
        ;

        slFontAlignY.setValue(0.0f); fontAlignY = fontAlignYValues[0];

        // LETTER CENTER BUTTON
        bLetterCenter = cw.cp5.addButton("LETTER").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*20)
                .setSize(cw.rangeWidth/4 -2 ,cw.controlHeight)
                .setSwitch(true).setOn().setLabel("LETTER")
                .moveTo("text")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        letterCenter = bLetterCenter.isOn();
                    }
                })
        ;
        letterCenter = true;

        // ROTATE ANGLE RANGE
        rRotAngle = cw.cp5.addRange("ROT ANG").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth/4 + 3,cw.marginTop + cw.rowStep*20)
                .setSize((int)3*cw.rangeWidth/4-5,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_ROT_ANG, Defaults.MAX_ROT_ANG)
                .setRangeValues(0.0f, 0.0f).setBroadcast(true)
                .moveTo("text")
        ;
        rotAngle = Defaults.rZERO.copy();
    }

    public void resetControls(){
        tWord.setValue(""); word="";
        sFontSize.setValue(Defaults.MIN_FONTSIZE); fontSize = Defaults.MIN_FONTSIZE;
        slFontFamily.setValue(0); fontFamily = fontList[0];
        slFontAlignX.setValue(0); fontAlignX = fontAlignXValues[0];
        slFontAlignY.setValue(0); fontAlignY = fontAlignYValues[0];
        slPolygonizerMode.setValue(4.0f); polyMode = polygonizerValues[4]; numPolyMode=4;
        sPolyLength.setValue(Defaults.MIN_POLY_LENGTH); polyLength = Defaults.MIN_POLY_LENGTH;
        sPolyStep.setValue(Defaults.MIN_POLY_STEP); polyStep = Defaults.MIN_POLY_STEP;
        sPolyAngle.setValue(Defaults.MIN_POLY_ANGLE); polyAngle = Defaults.MIN_POLY_ANGLE;
        sAdaptScale.setValue(Defaults.MIN_ADAPT_SCALE); adaptorScale = Defaults.MIN_ADAPT_SCALE;
        sAdaptOffset.setValue(Defaults.MIN_ADAPT_OFFSET); adaptorOffset = Defaults.MIN_ADAPT_OFFSET;
        bLetterCenter.setOn(); letterCenter = true;
        rRotAngle.setRangeValues(0, 0); rotAngle = Defaults.rZERO.copy();
    }

    public void checkControlEvents(ControlEvent theControlEvent){
        // WORD
        if(theControlEvent.isFrom("WORD")) {
            word = tWord.getStringValue();
            println("Word : "+word);
        }
        // FONT SIZE
        else if(theControlEvent.isFrom("FONT SIZE")) {
            fontSize = (int)sFontSize.getValue();
            sFontSize.setBroadcast(false); sFontSize.setValueLabel(""+(int)fontSize);sFontSize.setBroadcast(true);
            println("Font Size : "+fontSize);
        }
        // FONT FAMILY
        else if(theControlEvent.isFrom("FONT FAMILY")) {
            int n = (int)slFontFamily.getValue();
            fontFamily = fontList[n];
            println("Font Family: "+fontFamily);
        }
        // ALIGN X
        else if(theControlEvent.isFrom("ALIGN X")) {
            int n = (int)slFontAlignX.getValue();
            fontAlignX = fontAlignXValues[n];
            println("Font Align X: "+fontAlignX+" "+fontAlignXNames.get(n));
        }
        // ALGIN Y
        else if(theControlEvent.isFrom("ALIGN Y")) {
            int n = (int)slFontAlignY.getValue();
            fontAlignY = fontAlignYValues[n];
            println("Font Align Y: "+fontAlignY+" "+fontAlignYNames.get(n));
        }
        // POLY MODE
        else if(theControlEvent.isFrom("POLYMODE")) {
            numPolyMode = (int)slPolygonizerMode.getValue();
            polyMode = polygonizerValues[numPolyMode];
            println("Poly Mode: "+polyMode+" "+polygonizerNames.get(numPolyMode));
        }
        // POLY LENGTH
        else if(theControlEvent.isFrom("POLY LENGTH")) {
            polyLength = sPolyLength.getValue();
            println("Poly Length : "+polyLength);
        }
        // POLY STEP
        else if(theControlEvent.isFrom("POLY STEP")) {
            polyStep = sPolyStep.getValue();
            println("Poly Step : "+polyStep);
        }
        // POLY ANGLE
        else if(theControlEvent.isFrom("POLY ANGLE")) {
            polyAngle = sPolyAngle.getValue();
            println("Poly Angle : "+polyAngle);
        }
        // ADAPTOR SCALE
        else if(theControlEvent.isFrom("ADAPT SCALE")) {
            adaptorScale = sAdaptScale.getValue();
            println("Adaptor Scale : "+adaptorScale);
        }
        // ADAPTOR LENGTH OFFSET
        else if(theControlEvent.isFrom("ADAPT OFFSET")) {
            adaptorOffset = sAdaptOffset.getValue();
            println("Adaptor Length Offset : "+adaptorOffset);
        }
        // LETTER OR WORD CENTER
        else if(theControlEvent.isFrom("LETTER")) {
            if(letterCenter) bLetterCenter.setLabel("LETTER");
            else bLetterCenter.setLabel("WORD");
            println("Letter Center: "+letterCenter);
        }
        // ROTATION ANGLE RANGE
        else if(theControlEvent.isFrom("ROT ANG")) {
            rotAngle.setValues(rRotAngle.getLowValue(), rRotAngle.getHighValue());
            println("ROTATION ANGLE RANGE: "+rotAngle);
        }
    }
}
