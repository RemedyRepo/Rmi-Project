package club;

import interfaces.IClub;
import interfaces.IOffice;
import interfaces.ISeeker;
import model.Report;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Club extends UnicastRemoteObject implements IClub {

    private String clubName;
    private String sector;
    private String field;
    private boolean isRegister = false;
    ArrayList<Report> reports = new ArrayList<>();


    protected static IOffice office;


    private ArrayList<ISeeker> Seekers;
    private final HashMap<String, ISeeker> iSeekers = new HashMap<>();
    HashSet<String> workingSectors = new HashSet<>();
    ClubApp app;


    public Club(ClubApp app) throws RemoteException {
        super();
        this.app = app;
    }

    public void startClub() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1100);
        office = (IOffice) registry.lookup("Office");
        Seekers = new ArrayList<>();

    }


    @Override
    public boolean register(ISeeker ic) throws RemoteException {
        if (!iSeekers.containsKey(ic.getName())) {
            iSeekers.put(ic.getName(), ic);
            Seekers.add(ic);
            System.out.println("Seeker has been succefuly registered");
            System.out.println(iSeekers);

            if (app.seekerNames != null) {

                app.seekerNames.removeAllItems();
                app.seekerNames.addItem("Select");
            }
            for (int i = 0; i < this.getSeekers().size(); i++) {
                try {
                    app.seekerNames.addItem(this.getSeekers().get(i).getName());
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } else {
            System.out.println("cant register seeker - name is already used");
            System.out.println(Seekers);
        }
        return false;

    }

    @Override
    public boolean unregister(String seekerName) throws RemoteException {
        if (iSeekers.containsKey(seekerName)) {
            for (ISeeker seek : Seekers) {
                if (seek.getName().equals(seekerName)) {
                    Seekers.remove(seek);
                    Seekers.remove(seekerName);
                    if (app.seekerNames != null) {
                        app.seekerNames.removeAllItems();
                        app.seekerNames.addItem("Select");
                    }
                    for (int i = 0; i < this.getSeekers().size(); i++) {
                        try {
                            System.out.println("gowno");
                            app.seekerNames.addItem(this.getSeekers().get(i).getName());
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    System.out.println(Seekers);
                    System.out.println("Your seeker has been succfully unregistered");

                    return true;

                }
            }

        }
        return false;
    }

    @Override
    public String getName() throws RemoteException {
        return clubName;
    }

    @Override
    public boolean report(Report report, String seekerName) throws RemoteException {
        reports.add(report);
        for(Report reportToDraw : reports){
            for(ClubPanel panel : app.getSectors()){
                if(reportToDraw.getSector().equals(panel.getSector())){
                    for(ClubFieldPanel fieldPanel : panel.getFieldPanels()){
                        if(reportToDraw.getField().equals(fieldPanel.field)){
                            switch(reportToDraw.getArtifact().getCategory()){
                                case GOLD:
                                    fieldPanel.setBackground(Color.decode("#FDED0E"));
                                    break;
                                case SILVER:
                                    fieldPanel.setBackground(Color.decode("#E1E1E1"));
                                    break;
                                case BRONZE:
                                    fieldPanel.setBackground(Color.decode("#DA7C01"));
                                    break;
                                case IRON:
                                    fieldPanel.setBackground(Color.decode("#868686"));
                                case OTHER:
                                    fieldPanel.setBackground(Color.decode("#4B2706"));
                                    break;

                            }
                        }
                    }
                }
            }
        }
        office.report(reports, this.getName());
        return false;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public ArrayList<ISeeker> getSeekers() {
        return Seekers;
    }

    public void setSeekers(ArrayList<ISeeker> seekers) {
        Seekers = seekers;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }


    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
