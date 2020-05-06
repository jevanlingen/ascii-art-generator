package nl.jcore.com.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.jcore.com.guice.config.BasicModule;
import nl.jcore.com.guice.service.Communication;

import java.util.Scanner;

public class Generator {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        Communication comm = injector.getInstance(Communication.class);

        printIntoText();
        handleImages(comm);
    }

    private static void printIntoText() {
        System.out.println("================================");
        System.out.println("CONVERT IMAGE TO ASCII GENERATOR");
        System.out.println("================================");
        System.out.println("Please enter an url:");
    }

    private static void handleImages(Communication communication) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                System.exit(0);
            } else {
                communication.downloadAsAscii(input);
            }
        }
    }

}
