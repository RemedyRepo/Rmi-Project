package world;

import interfaces.IWorld;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class WorldApp extends JFrame {
    IWorld world;
    ArrayList<SectorPanel>sectors= new ArrayList<>();

    public WorldApp() throws RemoteException, AlreadyBoundException {
        world = new World(this);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("World", world);
        System.out.println("World started");
        this.setSize(1100, 960);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Lab07 - WORLD");
        this.setVisible(true);
        generateMap();
        //generaField();

    }


    public void generateMap(){
        for(int i=0; i<64; i++){
            sectors.add(new SectorPanel(this));
        }
    }

//    public void generaField(){
//        for(int i=0; i<sectors.size(); i++){
//            System.out.println(sectors.get(i).getSector());
//        }
//    }



    public static void main(String[] args) throws  InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(()->{
            try {
                WorldApp worldApp = new WorldApp();
            } catch (RemoteException | AlreadyBoundException e) {
                System.out.println("World error");
                throw new RuntimeException(e);
            }
        });



    }
}
