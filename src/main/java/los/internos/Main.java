package los.internos;

import los.internos.api.Controller;
import los.internos.menu.Menu;

import java.util.AbstractSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        //System.out.println(controller.getPositions());
        //controller.setWhites();
        //System.out.println(controller.getWhites());
        //controller.getAvailableMoves();
        //Scanner sc = new Scanner(System.in);
        //System.out.println(controller.getFrom());
        //System.out.println(controller.getTo());
        //sc.nextLine();
        //controller.moveToFrom();

        Menu m = new Menu(controller);
        m.showMenu(controller.getToken(), controller.getUuid());

    }

}

