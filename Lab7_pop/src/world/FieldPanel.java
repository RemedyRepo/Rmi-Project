package world;

import model.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FieldPanel extends JPanel {
    static int x=0;
    static int y=0;
    String field;
    static char letter = 'A';
    private boolean isScavenged =false;

    int random = (int) (Math.random()*3)+1;

    Artifact artifact;



    public FieldPanel(SectorPanel panel){
        int helper = y+1;
        field = letter+""+helper;
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
        this.setSize(11,11);
        this.setLocation(11*x, 11*y);
        this.setBackground(Color.green);
        this.setLayout(null);
        panel.add(this);
        x++;
        letter++;
        if(x == 10){
            y++;
            x=0;
            letter = 'A';
        }
        if(y == 10){
            y=0;
        }
        rngFact();
    }

    public void rngFact(){
        switch(random) {
            case 1:
                artifact = new Junk(2000);
                this.setBackground(Color.decode("#4B2706"));
                break;
            case 2:
                artifact = new Blank(2000);
                this.setBackground(Color.decode("#3E7324"));
                break;
            case 3:
                int random2 = ((int) (Math.random() * 6));
                artifact = new Treasure(5000, Category.values()[random2]);
                switch(random2){
                    case 0:
                        this.setBackground(Color.decode("#FDED0E"));
                        break;
                    case 1:
                        this.setBackground(Color.decode("#E1E1E1"));
                        break;
                    case 2:
                        this.setBackground(Color.decode("#DA7C01"));
                        break;
                    case 3:
                        this.setBackground(Color.decode("#868686"));
                        break;
                    case 4:
                        this.setBackground(Color.decode("#4B2706"));
                        break;
                    case 5:
                        this.setBackground(Color.decode("#3E7324"));
                        break;
                }

                break;
                // code block
        }
    }


    public boolean isScavenged() {
        return isScavenged;
    }

    public void setScavenged(boolean scavenged) {
        isScavenged = scavenged;
    }
}

