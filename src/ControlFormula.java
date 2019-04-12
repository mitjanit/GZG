import controlP5.ControlEvent;
import controlP5.Slider;

import static processing.core.PApplet.println;

public class ControlFormula {

    //*********** SUPERFORMULA CONTROLS ************//
    Slider sM, sN1, sN2, sN3, sSizeF, sStepF, sLapsF;
    float m, n1, n2, n3, sizeF, stepF, lapsF;
    

    public ControlFormula(ControlWindow cw){
        setupFormulaControls(cw);
    }

    public void setupFormulaControls(ControlWindow cw){
        sM = cw.cp5.addSlider("M")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*19)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FORMULA_M,Defaults.MAX_FORMULA_M)
                .setValue(Defaults.MIN_FORMULA_M)
                .moveTo("formula")
        ;

        m = Defaults.MIN_FORMULA_M;

        sN1 = cw.cp5.addSlider("N1")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*20)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FORMULA_N1,Defaults.MAX_FORMULA_N1)
                .setValue(Defaults.MIN_FORMULA_N1)
                .moveTo("formula")
        ;

        n1 = Defaults.MIN_FORMULA_N1;

        sN2 = cw.cp5.addSlider("N2")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*21)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FORMULA_N2,Defaults.MAX_FORMULA_N2)
                .setValue(Defaults.MIN_FORMULA_N2)
                .moveTo("formula")
        ;

        n2 = Defaults.MIN_FORMULA_N2;

        sN3 = cw.cp5.addSlider("N3")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*22)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FORMULA_N3,Defaults.MAX_FORMULA_N3)
                .setValue(Defaults.MIN_FORMULA_N3)
                .moveTo("formula")
        ;

        n3 = Defaults.MIN_FORMULA_N3;

        sSizeF = cw.cp5.addSlider("SIZE F")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*23)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FORMULA_SIZE,Defaults.MAX_FORMULA_SIZE)
                .setValue(Defaults.MIN_FORMULA_SIZE)
                .moveTo("formula")
        ;

        sizeF = Defaults.MIN_FORMULA_SIZE;

        sStepF = cw.cp5.addSlider("STEP F")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*24)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FORMULA_STEP,Defaults.MAX_FORMULA_STEP)
                .setValue(Defaults.MIN_FORMULA_STEP)
                .moveTo("formula")
        ;

        stepF = Defaults.MIN_FORMULA_STEP;

        sLapsF = cw.cp5.addSlider("LAPS F")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*25)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FORMULA_LAPS,Defaults.MAX_FORMULA_LAPS)
                .setValue(Defaults.MIN_FORMULA_LAPS)
                .moveTo("formula")
        ;

        lapsF = Defaults.MIN_FORMULA_LAPS;
    }

    public void resetControls(){

    }

    public void checkControlEvents(ControlEvent theControlEvent){

        if(theControlEvent.isFrom("M")) {
            m = sM.getValue();
            println("SUPERFORMULA M: "+m);
        }
        else if(theControlEvent.isFrom("N1")) {
            n1 = sN1.getValue();
            println("SUPERFORMULA n1: "+n1);
        }
        else if(theControlEvent.isFrom("N2")) {
            n2 = sN2.getValue();
            println("SUPERFORMULA N2: "+n2);
        }
        else if(theControlEvent.isFrom("N3")) {
            n3 = sN3.getValue();
            println("SUPERFORMULA N3: "+n3);
        }

        else if(theControlEvent.isFrom("SIZE F")) {
            sizeF = sSizeF.getValue();
            println("SUPERFORMULA SIZE F: "+sizeF);
        }
        else if(theControlEvent.isFrom("STEP F")) {
            stepF = sStepF.getValue();
            println("SUPERFORMULA STEP F: "+stepF);
        }
        else if(theControlEvent.isFrom("LAPS F")) {
            lapsF = sLapsF.getValue();
            println("SUPERFORMULA LAPS F: "+lapsF);
        }
    }
}
