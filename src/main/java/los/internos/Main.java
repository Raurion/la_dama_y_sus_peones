package los.internos;

import los.internos.api.Controller;
import los.internos.menu.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Menu menu = new Menu(controller);
        menu.showMenu();
    }
}