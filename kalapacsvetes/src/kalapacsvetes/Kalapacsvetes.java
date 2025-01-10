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
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author nikoh
 */
public class Kalapacsvetes {
   public static void main(String[] args) {
        List<sportolo> sportolok = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\nikoh\\Documents\\GitHub\\meres-2025-01\\kalapacsvetes.txt"))) {
          
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                sportolok.add(new sportolo(line));
            }

            // 4.
            long dobasokSzama = sportolok.size();
            System.out.println("4. feladat: " + dobasokSzama + " dobás eredménye található.");

            // 5. 
            OptionalDouble magyarAtlag = sportolok.stream()
                .filter(s -> s.getOrszagKod().equals("HUN"))
                .mapToDouble(sportolo::getEredmeny)
                .average();
            
            System.out.println("5. feladat: A magyar sportolók átlagosan " + 
                String.format("%.2f", magyarAtlag.orElse(0)) + " métert dobtak.");

            // 6. 
            System.out.println("6. feladat: Adjon meg egy évszámot:");
            Scanner sc = new Scanner(System.in);
            String ev = sc.nextLine();
            
            List<sportolo> eviDobasok = sportolok.stream()
                .filter(s -> s.getDatum().startsWith(ev))
                .collect(Collectors.toList());

            if (eviDobasok.isEmpty()) {
                System.out.println("Egy dobás sem került be ebben az évben.");
            } else {
                System.out.println(eviDobasok.size() + " darab dobás került be ebben az évben.");
                eviDobasok.forEach(s -> System.out.println(s.getNev()));
            }

            // 7. 
            System.out.println("7. feladat: Statisztika");
            Map<String, Long> orszagStatisztika = sportolok.stream()
                .collect(Collectors.groupingBy(sportolo::getOrszagKod, Collectors.counting()));
            orszagStatisztika.forEach((orszag, db) -> 
                System.out.printf("%s - %d dobás%n", orszag, db));

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