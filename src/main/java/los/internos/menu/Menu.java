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

    public void showMenu(String token, String uuid){

        Scanner sc = new Scanner(System.in);
        int opcion=0;

        banner();
        do{
            if(token == null) {
                System.out.println("**** TOKEN IS NULL ****");
            }else{
                System.out.println("***** TOKEN GENERADO: *****");
                System.out.println(token);
                System.out.println("***************************************");
                System.out.println("     INTRODUCE UNA OPCIÓN: (1 o 2)");
                System.out.println("1- Create game.");
                System.out.println("2- Join game.");
                System.out.println("***************************************");
                System.out.println();
                opcion = sc.nextInt();
                switch (opcion){
                    case 1:
                        if(uuid != null) {
                            System.out.println("***** GAME CREATED *****");
                            System.out.println(uuid);

                        }else{
                            System.err.println("COULD NOT CREATE GAME");
                        }
                        break;
                }
            }
            for (int i = 0; i < 10; i++) {
                System.out.println();

            }

        }while(true);
    }
}
