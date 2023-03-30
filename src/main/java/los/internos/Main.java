package los.internos;

import los.internos.api.Controller;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        System.out.println(controller.getBoard());
        controller.setWhites();
        System.out.println(controller.getWhites());
        System.out.println(controller.getAvailableMoves());
        Scanner sc = new Scanner(System.in);
        System.out.println(controller.getFrom());
        System.out.println(controller.getTo());
        sc.nextLine();
        controller.move();

    }
}