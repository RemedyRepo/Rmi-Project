package office;

import interfaces.IOffice;
import world.SectorPanel;
import world.World;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class OfficeApp extends JFrame {
    IOffice office;


    private ArrayList<OfficeSectorPanel> sectors= new ArrayList<>();

    public OfficeApp() throws RemoteException, AlreadyBoundException {
        office = new Office(this);

        Registry registry = LocateRegistry.createRegistry(1100);
        registry.bind("Office", office);
        System.out.println("Office started");
        this.setSize(1100, 1000);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Lab07 - office");
        this.setVisible(true);
        generateMap();
    }

    public void generateMap(){
        for(int i=0; i<64; i++){
            sectors.add(new OfficeSectorPanel(this));
        }
    }




    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            try {
                OfficeApp app = new OfficeApp();


            } catch (RemoteException | AlreadyBoundException e) {
                System.out.println("Office error");
                throw new RuntimeException(e);

            }
        });

    }

    public ArrayList<OfficeSectorPanel> getSectors() {
        return sectors;
    }
}
