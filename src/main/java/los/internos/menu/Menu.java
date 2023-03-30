package los.internos.menu;

import los.internos.api.Controller;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Menu{
    private Controller controller;

    public Menu(Controller controller) {
        this.controller = controller;
    }

    public void banner(){
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

    public void showMenu(){
        Scanner sc = new Scanner(System.in);
        int opcion;
        int color;

        controller.setToken();
        banner();
        do{
            if(controller.getToken() == null) {
                System.out.println("**** TOKEN IS NULL ****");
            }else{
                System.out.println("***** TOKEN GENERADO: *****");
                System.out.println(controller.getToken());
                System.out.println("***************************************");
                System.out.println("     INTRODUCE UNA OPCIÓN: (1 o 2)");
                System.out.println("1- Create game.");
                System.out.println("2- Join game.");
                System.out.println("***************************************");
                System.out.println();
                opcion = sc.nextInt();
                switch (opcion){
                    case 1:
                        System.out.println("*** ELIGE UN COLOR: BLANCO (1) || NEGRO (0) ***");
                        color = sc.nextInt();
                        //CASE WHITE
                        if(color == 1){
                            controller.setColorWhite();
                            controller.setUuid();
                            if(checkGameCreated()){
                                System.out.println("TYPE ANYTHING TO START");
                                sc.next();
                                playGame(color);
                            }else{

                                break;
                            }

                        }
                    case 2:
                        System.out.println("*** ELIGE UN COLOR: BLANCO (1) || NEGRO (0) ***");
                        color = sc.nextInt();
                        //CASE BLACK
                        if(color == 0){
                            controller.setColorBlack();
                            controller.setUuid();
                            if(checkGameCreated()){
                                System.out.println("TYPE ANYTHING TO START");
                                sc.next();
                                playGame(color);
                            }else{

                                break;
                            }

                        }
                        break;
                }


            }
            for (int i = 0; i < 10; i++) {
                System.out.println();

            }
        }while(true);
    }

    public boolean checkGameCreated(){
        if(controller.getUuid() != null) {
            System.out.println("***** GAME CREATED *****");
            System.out.println(controller.getUuid());
            return true;
        }else{
            System.err.println("COULD NOT CREATE GAME");
            return false;
        }
    }

    public void playGame(int color){
        while (true) {
            while (controller.checkTurn()) {
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
            }
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
