package seeker;

import interfaces.IClub;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class SeekerApp extends JFrame implements ActionListener {
    JComboBox<String> registerToClub;
    ArrayList<String> listOfClubs;
    JButton unregister;
    JButton report;
    JButton updateClub;
    Seeker seeker;
    JLabel name;
    JLabel clubOfSeeker;

    public SeekerApp() throws RemoteException, NotBoundException {
        seeker = new Seeker(this);
        seeker.startSeeker();
        this.setSize(400, 300);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Lab07 - Seeker");
        this.setVisible(true);
        unregister = new JButton("unregister");
        unregister.setSize(100, 50);
        unregister.setLocation(300, 0);
        unregister.addActionListener(this);
        this.add(unregister);
        seeker = new Seeker(this);
        registerToClub = new JComboBox<>();
        registerToClub.setSize(200, 50);
        registerToClub.setLocation(0, 0);
        registerToClub.addActionListener(this);
        registerToClub.addItem("Select");
        this.add(registerToClub);
        updateClub = new JButton("updateList");
        updateClub.addActionListener(this);
        updateClub.setSize(100, 50);
        updateClub.setLocation(200, 0);
        updateClub.addActionListener(this);
        name = new JLabel("Name of seeker");
        name.setSize(150,50);
        name.setLocation(200,50);
        this.add(name);
        clubOfSeeker = new JLabel("Club of seeker: ");
        clubOfSeeker.setSize(150,50);
        clubOfSeeker.setLocation(0,50);
        this.add(clubOfSeeker);


        this.add(updateClub);


    }


    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            try {
                SeekerApp app = new SeekerApp();
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void checkClubs() throws RemoteException {
        registerToClub.removeAllItems();
        registerToClub.addItem("Select");
        for (IClub club : seeker.office.getClubs()) {
            registerToClub.addItem(club.getName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == unregister) {
            try {
                seeker.club.unregister(seeker.getName());
                seeker.club = null;
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == registerToClub) {
            try {
                for (IClub club : seeker.office.getClubs()) {
                    if (club.getName().equals(registerToClub.getSelectedItem())) {
                        String nameOfSeeker = JOptionPane.showInputDialog(null, "Enter sector you want ", "Permision request", JOptionPane.INFORMATION_MESSAGE);
                        seeker.setSeekerName(nameOfSeeker);
                        seeker.club = club;
                        seeker.club.register(seeker);
                        name.setText("Name of seeker: " + nameOfSeeker);
                        clubOfSeeker.setText("Club of seeker: " + club.getName());


                    }
                }

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == updateClub) {
            try {
                checkClubs();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }


    }


}
