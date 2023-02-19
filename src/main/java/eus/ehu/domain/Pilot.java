package eus.ehu.domain;

public class Pilot {
    private final int id;
    private final String name;
    private final String nationality;
    private int points;

    public Pilot(int id, String name, String nat, int pts) {
        this.id = id;
        this.name = name;
        this.nationality = nat;
        this.points = pts;
    }

    public void addPoints(int morePoints) {
        this.points += morePoints;
    }

    @Override
    public String toString() {
        return String.format("%d %s (%s) - %d points", id, name, nationality, points);
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}

