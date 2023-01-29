package seeker;

import interfaces.IClub;
import interfaces.IOffice;
import interfaces.ISeeker;
import interfaces.IWorld;
import model.Artifact;
import model.Report;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static java.lang.Thread.sleep;

public class Seeker extends UnicastRemoteObject implements ISeeker {
    protected static IOffice office;
    protected static IWorld world;
    JProgressBar jProgressBar;

    private String seekerName;
    protected IClub club;
    Report report;
    SeekerApp app;
    JFrame frame;
    JLabel scavenging;

    public Seeker(SeekerApp app) throws RemoteException {
        super();
        this.app = app;
    }

    public void startSeeker() throws RemoteException, NotBoundException {
        System.out.println("dupa");
        Registry registry = LocateRegistry.getRegistry("localhost", 1100);
        office = (IOffice) registry.lookup("Office");


        Registry registry2 = LocateRegistry.getRegistry("localhost", 1099);
        world = (IWorld) registry2.lookup("World");
    }
    
        public void progresBar(int time) {
            jProgressBar = new JProgressBar(1,time);
            jProgressBar.setVisible(true);
            jProgressBar.setValue(0);
            jProgressBar.setSize(400,50);
            jProgressBar.setLocation(0,200);
            jProgressBar.setStringPainted(true);
            jProgressBar.setString("SCAVENGING...");
            this.app.add(jProgressBar);
            int counter =0;
            while(counter <= time){
                jProgressBar.setValue(counter);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                counter+=200;
            }

        }





    @Override
    public boolean exploreTask(String sector, String field) throws RemoteException {

        Artifact artifact;
        artifact = world.explore(this.getName(),sector,field);


        if(artifact != null){
            progresBar(artifact.getExcavationTime());
            jProgressBar.setVisible(false);
            report = new Report(sector, field, artifact);
            club.report(report, this.getName());
            System.out.println("Wykopane");
        }
        return false;
    }

    @Override
    public String getName() throws RemoteException {
        return seekerName;
    }


    public void setSeekerName(String seekerName) {
        this.seekerName = seekerName;
    }
}
