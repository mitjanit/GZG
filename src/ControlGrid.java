import controlP5.*;
import static processing.core.PApplet.println;

public class ControlGrid {

    //*********** GRID CONTROLS ******************//
    Slider sNumCols, sNumRows;
    int numCols, numRows;

    public ControlGrid(ControlWindow cw){
        setupGridControls(cw);
    }

    public void setupGridControls(ControlWindow cw){

        // NUM COLS
        sNumCols = cw.cp5.addSlider("COLS").
                setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*8)
                .setSize((int)(cw.rangeWidth/2.25),cw.controlHeight)
                .setRange(Defaults.MIN_COLS, Defaults.MAX_COLS)
                .setValue(Defaults.MIN_COLS)
                .moveTo("grid")
        ;

        numCols = Defaults.MIN_COLS;

        // NUM ROWS
        sNumRows = cw.cp5.addSlider("ROWS")
                .setPosition(cw.marginLeft+ (int)(cw.rangeWidth/1.8),cw.marginTop + cw.rowStep*8)
                .setSize((int)(cw.rangeWidth/2.25),cw.controlHeight)
                .setRange(Defaults.MIN_ROWS, Defaults.MAX_ROWS)
                .setValue(Defaults.MIN_ROWS)
                .moveTo("grid")
        ;
        numRows = Defaults.MIN_ROWS;
    }

    public void resetControls(){
        sNumCols.setValue(Defaults.MIN_COLS); numCols = Defaults.MIN_COLS;
        sNumRows.setValue(Defaults.MIN_ROWS); numRows = Defaults.MIN_ROWS;
    }

    void checkControlEvents(ControlEvent theControlEvent){
        // NUM COLS
        if(theControlEvent.isFrom("COLS")) {
            numCols = (int)sNumCols.getValue();
            sNumCols.setBroadcast(false);
            sNumCols.setValueLabel(""+numCols);
            sNumCols.setBroadcast(true);
            println("Num.Cols : "+numCols);
        }
        // NUM ROWS
        else if(theControlEvent.isFrom("ROWS")) {
            numRows = (int)sNumRows.getValue();
            sNumRows.setBroadcast(false);
            sNumRows.setValueLabel(""+numRows);
            sNumRows.setBroadcast(true);
            println("Num.Rows : "+numRows);
        }
    }
}
