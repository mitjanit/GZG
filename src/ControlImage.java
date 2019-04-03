import controlP5.*;
import java.io.File;

import processing.core.PApplet;
import select.files.SelectLibrary;
//import select.files.*;
import static controlP5.ControlP5Constants.*;
import static processing.core.PApplet.println;

public class ControlImage {

    String pathImage, imageName;
    Textfield tImage;
    Range rThresholdAtt, rThresholdRep;
    RangFloat thresholdAtt, thresholdRep;
    Button bLoadPic, bAscAtt, bAscRep, bBorderMode;
    boolean ascAtt, ascRep, borderMode;
    Slider sThreshold;
    float threshold;

    SelectLibrary files;
    File file;
    ControlWindow cw;

    public ControlImage(ControlWindow cw){
        setupImageControls(cw);
        this.cw = cw;
        files = new SelectLibrary((PApplet)cw);
    }

    public void setupImageControls(ControlWindow cw){
        // ASC ATTRACTOR BUTTON
        bLoadPic = cw.cp5.addButton("LOAD PIC").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*29)
                .setSize(cw.rangeWidth/4 -4,cw.controlHeight)
                .moveTo("image")
        ;

        // IMAGE NAME
        tImage = cw.cp5.addTextfield("IMAGE")
                .setPosition(cw.marginLeft + cw.rangeWidth/4,cw.marginTop + cw.rowStep*29)
                .setSize(3*cw.rangeWidth/4,cw.controlHeight)
                .setAutoClear(false) //.setFont(font)
                .moveTo("image")
        ;

        Label label2 = tImage.getCaptionLabel();
        label2.setText("IMAGE").align(ControlP5.RIGHT_OUTSIDE, CENTER).getStyle().setPaddingLeft(5);

        imageName = Defaults.DEFAULT_IMG;

        // ASC ATTRACTOR BUTTON
        bAscAtt = cw.cp5.addButton("ASC ATT").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*30)
                .setSize(cw.rangeWidth/4 -4,cw.controlHeight)
                .setSwitch(true).setOn().setLabel("ASC ATT")
                .moveTo("image")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        ascAtt = bAscAtt.isOn();
                    }
                })
        ;

        ascAtt = true;

        // THRESHOLD ATTRACTORS RANGE
        rThresholdAtt = cw.cp5.addRange("THRESHOLD ATT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth/4,cw.marginTop + cw.rowStep*30)
                .setSize(3*cw.rangeWidth/4,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_THRESHOLD, Defaults.MAX_THRESHOLD)
                .setRangeValues(Defaults.MIN_THRESHOLD, Defaults.MAX_THRESHOLD).setBroadcast(true)
                .moveTo("image")
        ;
        thresholdAtt = new RangFloat(Defaults.MIN_THRESHOLD, Defaults.MAX_THRESHOLD);

        // ASC REPELERS BUTTON
        bAscRep = cw.cp5.addButton("ASC REP").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*31)
                .setSize(cw.rangeWidth/4 -4,cw.controlHeight)
                .setSwitch(true).setOn().setLabel("ASC REP")
                .moveTo("image")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        ascRep = bAscRep.isOn();
                    }
                })
        ;

        ascRep = true;

        // THRESHOLD REPELERS RANGE
        rThresholdRep = cw.cp5.addRange("THRESHOLD REP").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth/4,cw.marginTop + cw.rowStep*31)
                .setSize(3*cw.rangeWidth/4,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_THRESHOLD, Defaults.MAX_THRESHOLD)
                .setRangeValues(Defaults.MIN_THRESHOLD, Defaults.MAX_THRESHOLD).setBroadcast(true)
                .moveTo("image")
        ;
        thresholdRep = new RangFloat(Defaults.MIN_THRESHOLD, Defaults.MAX_THRESHOLD);

        // BORDER MODE BUTTON
        bBorderMode = cw.cp5.addButton("BORDER").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*32)
                .setSize(cw.rangeWidth/4 -4,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("BORDER OFF")
                .moveTo("image")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        borderMode = bBorderMode.isOn();
                    }
                })
        ;

        // THRESHOLD SLIDER
        threshold = Defaults.MIN_THRESHOLD;

        sThreshold = cw.cp5.addSlider("THRESHOLD")
                .setPosition(cw.marginLeft + cw.rangeWidth/4,cw.marginTop + cw.rowStep*32)
                .setSize(3*cw.rangeWidth/4,cw.controlHeight)
                .setRange(Defaults.MIN_THRESHOLD, Defaults.MAX_THRESHOLD)
                .setValue(Defaults.MIN_THRESHOLD).setValueLabel(""+threshold)
                .moveTo("image")
        ;
    }

    public void resetControls(){

    }

    public void checkControlEvents(ControlEvent theControlEvent){
        // ASC ATTRACTOR
        if(theControlEvent.isFrom("LOAD PIC")) {
            println("LOADING PIC!!! ");
            this.cw.selectInput("Select image:","selectImage", file, this);
        }
        // ASC ATTRACTOR
        else if(theControlEvent.isFrom("ASC ATT")) {
            if(ascAtt) bAscAtt.setLabel("ASC ATT");
            else bAscAtt.setLabel("DESC ATT");
            println("ASC ATT: "+ascAtt);
        }
        // THRESHOLD ATTRACTORS RANGE
        else if(theControlEvent.isFrom("THRESHOLD ATT")) {
            thresholdAtt.setValues(rThresholdAtt.getLowValue(), rThresholdAtt.getHighValue());
            println("THRESHOLD ATT RANGE: "+thresholdAtt);
        }
        // ASC REPELERS
        else if(theControlEvent.isFrom("ASC REP")) {
            if(ascRep) bAscRep.setLabel("ASC REP");
            else bAscRep.setLabel("DESC REP");
            println("ASC REP: "+ascRep);
        }
        // THRESHOLD REPELERS RANGE
        else if(theControlEvent.isFrom("THRESHOLD REP")) {
            thresholdRep.setValues(rThresholdRep.getLowValue(), rThresholdRep.getHighValue());
            println("THRESHOLD REP RANGE: "+thresholdRep);
        }
        // BORDER MODE
        else if(theControlEvent.isFrom("BORDER")) {
            if(borderMode) bBorderMode.setLabel("BORDER ON");
            else bBorderMode.setLabel("BORDER OFF");
            println("BORDER MODE: "+borderMode);
        }
        // ADAPTOR LENGTH OFFSET
        else if(theControlEvent.isFrom("THRESHOLD")) {
            threshold = sThreshold.getValue();
            println("Threshold : "+threshold);
        }
    }
    public void selectImage(File selection){
        println("SELECT IMAGE FUNCTION");
        if(selection==null){
            pathImage=Defaults.DEFAULT_IMG;
        }
        else {
            pathImage = selection.getAbsolutePath();
            println(pathImage);
        }
        tImage.setValue(pathImage);
    }


}
