package office;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class OfficeSectorPanel extends JPanel {


    static int x=0;
    static int y=0;
    private String sector;
    static char letter = 'A';
     private ArrayList<OfficeFieldPanel>fieldPanels = new ArrayList<>();

    public ArrayList<OfficeFieldPanel> getFieldPanels() {
        return fieldPanels;
    }

    public OfficeSectorPanel(Frame worldApp){
        int helper = y+1;
        sector = letter+""+helper;
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
        this.setSize(120,120);
        this.setLocation(120*x, 120*y);
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(null);
        worldApp.add(this);
        x++;
        letter++;
        if(x == 8){
            y++;
            x=0;
            letter = 'A';
        }
        generateFields();
    }

    public void generateFields(){
        for(int i = 0; i < 100; i++){
            fieldPanels.add(new OfficeFieldPanel(this));
        }
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}