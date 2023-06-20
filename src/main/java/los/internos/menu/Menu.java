package los.internos.menu;

import los.internos.api.Controller;

import java.util.Scanner;

public class Menu {
    private Controller controller;

    public Menu(Controller controller) {
        this.controller = controller;
    }

    public void banner() {
        System.out.println(" __                       _______                                                                                               _______");
        System.out.println("| $$       ______        | $$$$$$$\\  ______   ______ ____    ______         __    __         _______  __    __   _______       | $$$$$$$\\  ______    ______   _______    ______    _______");
        System.out.println("| $$      |      \\       | $$  | $$ |      \\ |      \\    \\  |      \\       |  \\  |  \\       /       \\|  \\  |  \\ /       \\      | $$__/ $$ /      \\  /      \\ |       \\  /      \\  /       \\");
        System.out.println("| $$       \\$$$$$$\\      | $$  | $$  \\$$$$$$\\| $$$$$$\\$$$$\\  \\$$$$$$\\      | $$  | $$      |  $$$$$$$| $$  | $$|  $$$$$$$      | $$    $$|  $$$$$$\\|  $$$$$$\\| $$$$$$$\\|  $$$$$$\\|  $$$$$$$");
        System.out.println("| $$      /      $$      | $$  | $$ /      $$| $$ | $$ | $$ /      $$      | $$  | $$       \\$$    \\ | $$  | $$ \\$$    \\       | $$$$$$$ | $$    $$| $$  | $$| $$  | $$| $$    $$ \\$$    \\");
        System.out.println("| $$_____|  $$$$$$$      | $$__/ $$|  $$$$$$$| $$ | $$ | $$|  $$$$$$$      | $$__/ $$       _\\$$$$$$\\| $$__/ $$ _\\$$$$$$\\      | $$      | $$$$$$$$| $$__/ $$| $$  | $$| $$$$$$$$ _\\$$$$$$\\");
        System.out.println("| $$     \\\\$$    $$      | $$    $$ \\$$    $$| $$ | $$ | $$ \\$$    $$       \\$$    $$      |       $$ \\$$    $$|       $$      | $$       \\$$     \\ \\$$    $$| $$  | $$ \\$$     \\|       $$");
        System.out.println("\\$$$$$$$$ \\$$$$$$$       \\$$$$$$$   \\$$$$$$$ \\$$  \\$$  \\$$  \\$$$$$$$       _\\$$$$$$$       \\$$$$$$$   \\$$$$$$  \\$$$$$$$        \\$$        \\$$$$$$$  \\$$$$$$  \\$$   \\$$  \\$$$$$$$ \\$$$$$$$");
        System.out.println("                                                                          |  \\__| $$");
        System.out.println("                                                                           \\$$    $$");
        System.out.println("                                                                            \\$$$$$$");
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        int color = -1;
        String uuid;

        controller.setToken();
        banner();
        do {
            if (controller.getToken() == null) {
                System.out.println("**** TOKEN IS NULL ****");
            } else {
                System.out.println("***** TOKEN GENERADO: *****");
                System.out.println(controller.getToken());
                System.out.println("***************************************");
                System.out.println("     INTRODUCE UNA OPCIÓN: (1 o 2)");
                System.out.println("1- Create game.");
                System.out.println("2- Join game.");
                System.out.println("***************************************");
                System.out.println();
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        gameConfig(sc, color);
                        break;
                    case 2:
                        System.out.println("** Introduce the uuid: **");
                        uuid = sc.next();
                        controller.joinGame(uuid);
                        System.out.println("JOINED SUCCESSFULLY");
                        gameConfig(sc, color);
                        break;
                }
            }
            for (int i = 0; i < 10; i++) {
                System.out.println();

            }
        } while (true);
    }

    public boolean checkGameCreated() {
        if (controller.getUuid() != null) {
            System.out.println("***** GAME CREATED *****");
            System.out.println(controller.getUuid());
            return true;
        } else {
            System.err.println("COULD NOT CREATE GAME");
            return false;
        }
    }

    public void playGame(int color) {
        while (true) {
            if (controller.checkTurn()) {
                controller.setBoard();
                if (color == 1) {
                    controller.setWhites();
                    controller.setFreeWhites();
                    controller.setRandomFreeWhite();
                } else {
                    controller.setBlacks();
                    controller.setFreeBlacks();
                    controller.setRandomFreeBlack();
                }
                controller.setRandomToFromFrom();
                controller.move();
                System.out.println("MOVIENDO");
            } else {
            //System.out.println("ESPERANDO");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }



    public void gameConfig(Scanner sc, int color) {
        System.out.println("*** ELIGE UN COLOR: BLANCO (1) || NEGRO (0) ***");
        color = sc.nextInt();
        //CASE WHITE
        if (color == 1) {
            controller.setColorWhite();
            controller.setUuid();
            controller.setWhitesUser();
            if (checkGameCreated()) {
                System.out.println("TYPE ANYTHING TO START");
                sc.next();
                playGame(color);
            }

        } else if (color == 0) {
            controller.setColorBlack();
            controller.setUuid();
            controller.setBlacksUser();
            if (checkGameCreated()) {
                System.out.println("TYPE ANYTHING TO START");
                sc.next();
                playGame(color);
            }
        }
    }
}
