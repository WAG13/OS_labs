package prompt;

import manager.Manager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Prompt {

    private static final int MENU_DELAY = 1000;
    public static boolean prompt = true;
    private static Scanner input = new Scanner(System.in);

    public static double getX() {
        System.out.print("Enter x: ");
        double x = input.nextDouble();
        input.nextLine();
        return x;
    }

    public static void runMenuEsc(Manager manager) {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(manager::closeAll));
    }

    public static void runMenu(Manager manager) {

        long start = System.nanoTime();
        long end;
        while (true) {
            runMenuEsc(manager);
            try {
                Thread.sleep(MENU_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (prompt) {
                short answer;
                synchronized (System.out) {
                    end = System.nanoTime();
                    System.out.println("Time between prompts: " + (end - start) / 1000000 + "ms");
                    System.out.println("1. Continue\n" +
                            "2. Continue without prompt\n" +
                            "3. Cancel\n" //+ "4. Exit"
                    );
                    answer = input.nextShort();
                    input.nextLine();
                }

                if (answer == 1) {
                    start = System.nanoTime();
                } else if (answer == 2) {
                    prompt = false;
                } else if (answer == 3) {
                    manager.closeAll();
                }
//                else if (answer == 4) {
//                    System.exit(0);
//                }
            }
        }
    }
}
