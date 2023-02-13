package eus.ehu.business_logic;

import eus.ehu.domain.Pilot;

import java.util.List;

public interface BLFacade {

    void storePilot(String name, String nationality, int points);
    List<Pilot> getAllPilots();
    List<Pilot> getPilotsByNationality(String nationality);
    int deletePilotByName(String pilotName);
    void addPointsToPilot(int morePoints, String pilotName);

    List<Pilot> getPilotsByPoints(int minPoints);
    Pilot getPilotByName(String name);
}
