package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("ChatBot: Witaj! Napisz coś (lub 'exit', aby zakończyć)");
        while (true) {
            System.out.print("Ty: ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("ChatBot: Do zobaczenia!");
                break;
            }

            //String response = zwrot od chata
            //System.out.println("ChatBot: " + response);
        }
        scanner.close();
    }
}
