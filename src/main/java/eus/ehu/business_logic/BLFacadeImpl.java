package eus.ehu.business_logic;

import eus.ehu.data_access.DbAccessManager;
import eus.ehu.domain.Pilot;

import java.util.List;

public class BLFacadeImpl implements BLFacade {
    private final DbAccessManager dbManager= DbAccessManager.getInstance();
    @Override
    public void storePilot(String name, String nationality, int points) {
        dbManager.storePilot(name, nationality, points);
    }

    @Override
    public List<Pilot> getAllPilots() {
        return dbManager.getAllPilots();
    }

    @Override
    public List<Pilot> getPilotsByNationality(String nationality) {
        return dbManager.getPilotsByNationality(nationality);
    }

    @Override
    public int deletePilotByName(String pilotName) {
        return dbManager.deletePilotByName(pilotName);
    }

    @Override
    public void addPointsToPilot(int morePoints, String pilotName) {
        dbManager.addPointsToPilot(morePoints, pilotName);
    }

    @Override
    public List<Pilot> getPilotsByPoints(int minPoints) {
        return dbManager.getPilotsByPoints(minPoints);
    }

    @Override
    public Pilot getPilotByName(String name) {
        return dbManager.getPilotByName(name);
    }

    @Override
    public void deletePilotById(int pilotId) {
        dbManager.deletePilotById(pilotId);
    }
}
