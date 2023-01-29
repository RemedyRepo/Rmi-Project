package office;

import club.Club;
import club.ClubFieldPanel;
import club.ClubPanel;
import interfaces.IClub;
import interfaces.IOffice;
import model.Report;
import seeker.SeekerApp;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Random;

public class Office extends UnicastRemoteObject implements IOffice {


    private ArrayList<IClub> iClubs = new ArrayList<>();
    private HashMap<String, String> sectors = new HashMap<>();
    private HashMap<String, IClub> clubs = new HashMap<>();
    private String sectorToColor;
    private ArrayList<Color>colorsForClubs = new ArrayList<>();
    private HashMap<ArrayList<Report>, String>reportsFromClub;
    OfficeApp app;


    public Office(OfficeApp app) throws RemoteException {
        super();
        assembleMap();
        reportsFromClub = new HashMap<>();
        this.app = app;

    }

    @Override
    synchronized public boolean register(IClub ic) throws RemoteException {
        randomColor();
        if (!clubs.containsKey(ic.getName())) {
            clubs.put(ic.getName(), ic);
            iClubs.add(ic);
            System.out.println("Club succefully register");
            System.out.println(clubs);


        } else {
            System.out.println("cant register your club - name is already used");
            System.out.println(clubs);
        }
        return false;
    }

    @Override
    synchronized public boolean unregister(String clubName) throws RemoteException {
        if (clubs.containsKey(clubName)) {
            for (IClub club : iClubs) {
                if (club.getName().equals(clubName)) {
                    iClubs.remove(club);
                    clubs.remove(clubName);
                    System.out.println(iClubs);
                    System.out.println("Your club has been succfully unregistered");
                    sectors.forEach((key, value) -> {
                        for(OfficeSectorPanel panel : app.getSectors()) {
                            if(key.equals(panel.getSector()) && clubName.equals(value)){
                                panel.setBackground(Color.darkGray);
                                clubs.put(key, null);
                            }
                        }


                    });
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public boolean permissionRequest(String clubName, String sector) throws RemoteException {
        if (clubs.containsKey(clubName) && sectors.get(sector) == null) {
            sectors.put(sector, clubName);
            for(OfficeSectorPanel panel : app.getSectors()){
                if(panel.getSector().equals(sector)){
                    for(int i = 0; i < iClubs.size(); i++){
                        if(iClubs.get(i).getName().equals(clubName)){
                            panel.setBackground(colorsForClubs.get(i));
                        }
                    }
                }
            }
            System.out.println(sectors);
            return true;
        }
        return false;
    }

    @Override
    public boolean permissionEnd(String clubName, String sector) throws RemoteException {
        if (sectors.containsValue(clubName) && clubs.containsKey(clubName)) {
            sectors.put(sector, null);
            for(OfficeSectorPanel panel : app.getSectors()){
                if(panel.getSector().equals(sector)){
                   panel.setBackground(Color.darkGray);
                }
            }
            System.out.println(sectors);
        }
        return false;
    }

    @Override
    public boolean report(List<Report> reports, String clubName) throws RemoteException {

        for(Report reportToDraw : reports){
            for(OfficeSectorPanel panel : app.getSectors()){
                if(reportToDraw.getSector().equals(panel.getSector())){
                    for(OfficeFieldPanel fieldPanel : panel.getFieldPanels()){
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
        return false;
    }

    @Override
    public List<IClub> getClubs() throws RemoteException {
        return iClubs;

    }

    public void assembleMap() {
        char letter = 'A';
        int helper = 0;
        for (int i = 1; i < 64; i++) {
            helper++;
            sectors.put(letter + "" + helper, null);
            if (i % 8 == 0 && i > 7) {
                letter += 1;
                helper = 0;

            }

        }
        System.out.println(sectors.toString());
    }

    public void randomColor(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        colorsForClubs.add(new Color(r,g,b));
    }

}
