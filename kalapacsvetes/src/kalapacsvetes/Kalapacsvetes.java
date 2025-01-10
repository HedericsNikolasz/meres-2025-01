/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kalapacsvetes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 *
 * @author nikoh
 */
public class Kalapacsvetes {
    public static void main(String[] args) {
        List<sportolo> sportolok = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("kalapacsvetés.txt"))) {
          
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                sportolok.add(new sportolo(line));
            }

            // 4. 
            long dobasok80Felett = sportolok.stream()
                .filter(s -> s.getEredmeny() >= 80.0)
                .count();
            System.out.println("80 méter feletti dobások száma: " + dobasok80Felett);

            // 5. 
            OptionalDouble magyarAtlag = sportolok.stream()
                .filter(s -> s.getOrszagKod().equals("HUN"))
                .mapToDouble(sportolo::getEredmeny)
                .average();
            
            if (magyarAtlag.isPresent()) {
                System.out.printf("Magyar sportolók átlageredménye: %.2f m%n", magyarAtlag.getAsDouble());
            } else {
                System.out.println("Nincs magyar sportoló az adatok között.");
            }

            // 6. 
            System.out.println("\nÉvenkénti legjobb eredmények:");
            sportolok.stream()
                .collect(Collectors.groupingBy(s -> s.getDatum().substring(0, 4)))
                .forEach((ev, lista) -> {
                    double maxEredmeny = lista.stream()
                        .mapToDouble(sportolo::getEredmeny)
                        .max()
                        .orElse(0.0);
                    System.out.printf("%s: %.2f m%n", ev, maxEredmeny);
                });

            // 7. 
            System.out.println("\nOrszágonkénti statisztika:");
            Map<String, Long> orszagStatisztika = sportolok.stream()
                .collect(Collectors.groupingBy(sportolo::getOrszagKod, Collectors.counting()));
            orszagStatisztika.forEach((orszag, db) -> 
                System.out.printf("%s: %d dobás%n", orszag, db));

            // 8. 
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("magyarok.txt"))) {
                writer.write("helyezés;eredmény;sportoló;országkód;helyszín;dátum\n");
                sportolok.stream()
                    .filter(s -> s.getOrszagKod().equals("HUN"))
                    .forEach(s -> {
                        try {
                            writer.write(s.toFileString() + "\n");
                        } catch (IOException e) {
                            System.err.println("Hiba a fájl írása közben: " + e.getMessage());
                        }
                    });
            }

        } catch (IOException e) {
            System.err.println("Hiba történt a fájl olvasása közben: " + e.getMessage());
        }
    }
}