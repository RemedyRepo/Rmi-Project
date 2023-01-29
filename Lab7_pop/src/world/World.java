package world;

import interfaces.IWorld;
import model.Artifact;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class World extends UnicastRemoteObject implements IWorld{
    WorldApp app;

    public World(WorldApp app) throws  RemoteException{
        super();
        this.app = app;

    }

    @Override
    public Artifact explore(String seekerName, String sector, String field) throws RemoteException {
        Artifact artifact;
        for(SectorPanel panel : app.sectors){
            if(panel.getSector().equals(sector)){
                for(FieldPanel fieldPanel : panel.fieldPanels){
                    if(fieldPanel.field.equals(field) && !fieldPanel.isScavenged()){
                        fieldPanel.setBackground(Color.red);
                        fieldPanel.setScavenged(true);
                        artifact = fieldPanel.artifact;
                        System.out.println(artifact.getCategory());
                        return artifact;
                    }else{

                    }
                }
            }

        }
        return null;
    }
}
