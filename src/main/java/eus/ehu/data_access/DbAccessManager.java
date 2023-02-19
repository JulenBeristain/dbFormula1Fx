package eus.ehu.data_access;

import eus.ehu.domain.Pilot;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbAccessManager {

    Connection conn = null;
    String dbpath;

    public void open() {
        try {
            String url = "jdbc:sqlite:" + dbpath;
            conn = DriverManager.getConnection(url);
            //Commented to avoid messing up the output
            //System.out.println("Database connection established");
        } catch (Exception e) {
            System.err.println("Cannot connect to database server " + e);
        }
    }

    public void close() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        //Commented to avoid messing up the output
        //System.out.println("Database connection closed");
    }


    // singleton pattern
    private static final DbAccessManager instance = new DbAccessManager();

    private DbAccessManager() {

        // this is a hard-coded value... a code smell, should fix it later
        //dbpath = "formula1.db";

        //We take the value stored in config.properties, so if we want to change
        //the database, we only have to change the db property
        Properties prop = new Properties();
        try (InputStream input = new
                FileInputStream("config.properties")) {
            prop.load(input);
            dbpath = prop.getProperty("db");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static DbAccessManager getInstance() {
        return instance;
    }

    //Method to set the path to the a new database dynamically.
    //The db property in congif.properties is also changed
    public void setDataBase(String newDB){
        dbpath = newDB;
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream("config.properties")) {
            prop.setProperty("db", newDB);
            prop.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // later we will add further operations here

    public void storePilot(String name, String nationality, int points) {

        this.open();
        String sql = "INSERT INTO pilots (name, nationality, points) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, nationality);
            pstmt.setInt(3, points);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.close();
    }

    public List<Pilot> getAllPilots()  {

        var pilots  = new ArrayList<Pilot>();
        this.open();

        try {
            String query = "SELECT id, name, nationality, points FROM pilots";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                pilots.add(new Pilot(rs.getInt("id"), rs.getString("name"), rs.getString("nationality"), rs.getInt("points")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.close();
        return pilots;
    }

    public List<Pilot> getPilotsByNationality(String nationality){

        this.open();

        String sql = "SELECT id, name, nationality, points "
                + "FROM pilots WHERE nationality = ?";

        var result = new ArrayList<Pilot>();

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, nationality);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Pilot(rs.getInt("id"), rs.getString("name"), rs.getString("nationality"), rs.getInt("points")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.close();
        return result;
    }

    public List<Pilot> getPilotsByPoints(int minPoints){

        var result  = new ArrayList<Pilot>();
        this.open();
        /*
        try {
            String query = "SELECT name, nationality, points FROM pilots";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                if (rs.getInt("points") > minPoints)
                    result.add(new Pilot(rs.getString("name"),
                            rs.getString("nationality"),
                            rs.getInt("points")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        //or making a selection where points > minPoints directly in the DB with SQL
        String sql = "SELECT id, name, nationality, points "
                + "FROM pilots WHERE points > ?";

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setInt(1, minPoints);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Pilot(rs.getInt("id"), rs.getString("name"), rs.getString("nationality"), rs.getInt("points")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.close();
        return result;
    }

    public int deletePilotByName(String pilotName) {
        this.open();
        int amount = 0;
        String sql = "DELETE FROM pilots WHERE name = ?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setString(1, pilotName);
            amount = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.close();

        return amount;
    }

    public void addPointsToPilot(int morePoints, String pilotName) {
        this.open();
        String sql = "UPDATE pilots SET points = points + ? WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, morePoints);
            pstmt.setString(2, pilotName);
            pstmt.executeUpdate();
            //System.out.println(pilotName + " has been updated");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.close();
    }

    public Pilot getPilotByName(String name){

        this.open();

        String sql = "SELECT id, name, nationality, points "
                + "FROM pilots WHERE name = ?";

        Pilot result = null;

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, name);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()){     //true <-> There is a pilot with the name (only one)
                result = new Pilot(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nationality"),
                        rs.getInt("points"));
            }   //false -> result remains null
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.close();
        return result;
    }

    public void deletePilotById(int pilotId) {
        this.open();
        String sql = "DELETE FROM pilots WHERE id = ?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pilotId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.close();
    }
}
