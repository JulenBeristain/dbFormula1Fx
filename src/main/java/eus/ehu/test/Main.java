package eus.ehu.test;

import eus.ehu.business_logic.BLFacade;
import eus.ehu.business_logic.BLFacadeImpl;

public class Main {
    public static void main(String[] args) {
        BLFacade bussinesLogic = new BLFacadeImpl();
        //bussinesLogic.getPilotsByNationality("French").forEach(System.out::println);

        //DbAccessManager dataManager = DbAccessManager.getInstance();

        bussinesLogic.storePilot("Lewis Hamilton", "British", 380);
        bussinesLogic.storePilot("Max Verstappen", "French", 400);
        bussinesLogic.storePilot("Sergio Perez", "Italian", 420);
        bussinesLogic.storePilot("Charles Leclerc", "Polish", 440);
        bussinesLogic.storePilot("Carlos Sainz", "Spanish", 460);
        bussinesLogic.storePilot("George Russell", "British", 480);
        bussinesLogic.storePilot("Esteban Ocon", "French", 500);


        //dataManager.getAllPilots().forEach(System.out::println);
        //dataManager.getPilotsByNationality("French").forEach(System.out::println);
        //dataManager.getPilotsByPoints(450).forEach(System.out::println);


        /*
        System.out.println("Introduce the name of a pilot:");
        Scanner stdIn = new Scanner(System.in);
        String pName = stdIn.nextLine();
         */

        /*
        dataManager.deletePilotByName(pName);
        dataManager.deletePilotByName(pName);
         */

        /*
        System.out.println("Introduce the number of points to add to the pilot:");
        int extraPoints;
        while (true){
            try{    //We could also check if the number is negative, but it could make sense in case of a sanction
                extraPoints = stdIn.nextInt();
                break;
            } catch (InputMismatchException e){
                stdIn.nextLine();   //To clean the buffer
                System.out.println("Introduce an integer value:");
            }
        }
       dataManager.addPointsToPilot(extraPoints, pName);
       */

        //System.out.println(dataManager.getPilotByName("Carlos Sainz"));
    }
}

