import controlP5.*;
import static processing.core.PApplet.println;

public class ControlSpiral {

    //************* SPIRAL CONTROLS *************//
    Button bInvRadius, bInvAngle;
    boolean invertRadius, invertAngle;

    public ControlSpiral(ControlWindow cw){
        setupSpiralControls(cw);
    }

    public void setupSpiralControls(ControlWindow cw){
        // INVERT RADIUS BUTTON
        bInvRadius = cw.cp5.addButton("INVERT RADIUS").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*26)
                .setSize(cw.rangeWidth/4 -2,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("ASC RADIUS")
                .moveTo("spiral")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        invertRadius = bInvRadius.isOn();
                    }
                })
        ;

        // HALF TOP ANGLE BUTTON
        bInvAngle = cw.cp5.addButton("INVERT ANGLE").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/4, cw.marginTop+ cw.rowStep*26)
                .setSize(cw.rangeWidth/4 -2,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("ASC ANGLE")
                .moveTo("spiral")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        invertAngle = bInvAngle.isOn();
                    }
                })
        ;
    }

    public void resetControls(){

    }

    void checkControlEvents(ControlEvent theControlEvent){
        // INVERT RADIUS
        if(theControlEvent.isFrom("INVERT RADIUS")) {
            println("Invert Radius: "+invertRadius);
            if(invertRadius) bInvRadius.setLabel("DESC RADIUS");
            else bInvRadius.setLabel("ASC RADIUS");
        }
        // INVERT ANGLE
        else if(theControlEvent.isFrom("INVERT ANGLE")) {
            println("Invert Angle: "+invertAngle);
            if(invertAngle) bInvAngle.setLabel("DESC ANGLE");
            else bInvAngle.setLabel("ASC ANGLE");
        }
    }

}
