package club;

import club.ClubPanel;

import model.*;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ClubFieldPanel extends JPanel {
    static int x=1;
    static int y=1;
    String field;
    static char letter = 'A';
    private boolean isScavenged =false;

    int random = (int) (Math.random()*3)+1;

    Artifact artifact;



    public ClubFieldPanel(ClubPanel panel){
        int helper = y;
        field = letter+""+helper;
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
        this.setSize(10,10);
        this.setLocation(10*x, 10*y);
        this.setBackground(Color.white);
        this.setLayout(null);
        panel.add(this);
        x++;
        letter++;
        if(x == 11){
            y++;
            x=1;
            letter = 'A';
        }
        if(y == 11){
            y=1;
        }

    }


    public boolean isScavenged() {
        return isScavenged;
    }

    public void setScavenged(boolean scavenged) {
        isScavenged = scavenged;
    }
}

