package club;

import interfaces.IClub;
import interfaces.ISeeker;
import office.OfficeSectorPanel;
import seeker.Seeker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class ClubApp extends JFrame implements ActionListener {
    String sector;
    String sectorToRemove;
    JButton register;
    JButton unregister;
    JButton permissionRequest;
    JButton permissionEnd;
    JButton exploreTask;
    JComboBox<String> seekerNames;
    JLabel ownedSectors;
    JLabel clubName;
    private ArrayList<ClubPanel>sectors = new ArrayList<>();

    public ArrayList<ClubPanel> getSectors() {
        return sectors;
    }

    Club club;


    public ClubApp() throws RemoteException, NotBoundException {
        this.setSize(1300, 1000);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Lab07 - club");
        this.setVisible(true);
        clubName = new JLabel("CLUB NAME");
        ownedSectors = new JLabel("WORKING SECTORS");


        seekerNames = new JComboBox<>();
        register = new JButton("register");
        unregister = new JButton("unregister");
        permissionRequest = new JButton("permissionRequest");
        permissionEnd = new JButton("permissionEnd");
        exploreTask = new JButton("update");


        register.setSize(130, 50);
        unregister.setSize(130, 50);
        permissionRequest.setSize(130, 50);
        permissionEnd.setSize(130, 50);
        seekerNames.setSize(130, 50);
        ownedSectors.setSize(130, 50);
        clubName.setSize(130,50);


        register.setLocation(1150, 0);
        unregister.setLocation(1150, 50);
        permissionRequest.setLocation(1150, 100);
        permissionEnd.setLocation(1150, 150);
        seekerNames.setLocation(1150, 200);
        ownedSectors.setLocation(1150, 250);
        clubName.setLocation(1150,300);


        this.add(seekerNames);
        this.add(register);
        this.add(unregister);
        this.add(permissionRequest);
        this.add(permissionEnd);
        this.add(exploreTask);
        this.add(ownedSectors);
        this.add(clubName);


        register.addActionListener(this);
        unregister.addActionListener(this);
        permissionRequest.addActionListener(this);
        permissionEnd.addActionListener(this);
        exploreTask.addActionListener(this);
        seekerNames.addActionListener(this);

        club = new Club(this);
        club.startClub();

        seekerNames.addItem("Select");
        generateMap();
    }
    public void generateMap(){
        for(int i=0; i<64; i++){
            sectors.add(new ClubPanel(this));
        }
    }



    public static void main(String[] args) throws InterruptedException, InvocationTargetException {

        SwingUtilities.invokeAndWait(() -> {
            try {
                ClubApp app = new ClubApp();


                //club.office.permissionRequest(club.getClubName(), "A1");
                //club.assignSeeker(0).exploreTask(club.assignSector("A1"), "A1");
                //club.office.permissionEnd(club.getClubName(), "A1");
                //club.office.unregister(club.getName());
            } catch (RemoteException | NotBoundException e) {
                System.out.println("Club error");
                throw new RuntimeException(e);
            }

        });


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) {
            try {
                String m = JOptionPane.showInputDialog(null, "Enter club name", "Creation of club", JOptionPane.INFORMATION_MESSAGE);
                club.setClubName(m);
                club.office.register(club);
                clubName.setText(m);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }


        }
        if (e.getSource() == unregister) {
            try {
                club.office.unregister(club.getName());
                for(ClubPanel panel : sectors){
                    for(String sectorTorRemove : club.workingSectors){
                        if(panel.getSector().equals(sectorTorRemove)){
                            panel.setBackground(Color.darkGray);
                        }
                    }

                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == permissionRequest) {
            try {
                sector = JOptionPane.showInputDialog(null, "Enter sector you want ", "Permision request", JOptionPane.INFORMATION_MESSAGE);
                if (club.workingSectors.size() >= 2) {
                    JOptionPane.showMessageDialog(this, "You cant work on more than 2 sectors",
                            "Too many sectrs", JOptionPane.WARNING_MESSAGE);
                } else {
                    if(club.office.permissionRequest(club.getClubName(), sector)){
                        club.office.permissionRequest(club.getClubName(), sector);
                        club.workingSectors.add(sector);
                        ownedSectors.setText(club.workingSectors.toString());
                        for(ClubPanel panel : sectors){
                            if(panel.getSector().equals(sector)){
                                panel.setBackground(Color.GREEN);
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "You are trying to get permision to not your sector",
                                "Too many sectrs", JOptionPane.WARNING_MESSAGE);
                    }


                }

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == permissionEnd) {
            try {
                sectorToRemove = JOptionPane.showInputDialog(null, "Enter sector you want to remove ", "Permision request", JOptionPane.INFORMATION_MESSAGE);
                if (club.workingSectors.contains(sectorToRemove)) {
                    club.office.permissionEnd(club.getClubName(), sectorToRemove);
                    club.workingSectors.remove(sectorToRemove);
                    ownedSectors.setText(club.workingSectors.toString());
                    for(ClubPanel panel : sectors){
                        if(panel.getSector().equals(sectorToRemove)){
                            panel.setBackground(Color.darkGray);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error input",
                            "Error input..", JOptionPane.WARNING_MESSAGE);
                }

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == seekerNames && e.getSource() != exploreTask) {
            for (ISeeker seeker : club.getSeekers()) {
                try {
                    if (seeker.getName().equals(seekerNames.getSelectedItem())) {
                        String sector1 = JOptionPane.showInputDialog(null, "Enter sector you want ", "Permision request", JOptionPane.INFORMATION_MESSAGE);
                        String field1 = JOptionPane.showInputDialog(null, "Enter field you want ", "Permision request", JOptionPane.INFORMATION_MESSAGE);
                        if (club.workingSectors.contains(sector1)) {
                            seeker.exploreTask(sector1, field1);
                        } else {
                            JOptionPane.showMessageDialog(this, "You dont have permission to seek on that sector",
                                    "Wrong sector", JOptionPane.WARNING_MESSAGE);
                        }

                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }


    }
}
