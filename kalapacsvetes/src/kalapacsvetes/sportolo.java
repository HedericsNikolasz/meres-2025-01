/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kalapacsvetes;

/**
 *
 * @author nikoh
 */
public class sportolo {
    private String helyezes;
    private double eredmeny;
    private String nev;
    private String orszagKod;
    private String helyszin;
    private String datum;

    public sportolo(String sor) {
        String[] adatok = sor.split(";");
        this.helyezes = adatok[0];
        this.eredmeny = Double.parseDouble(adatok[1]);
        this.nev = adatok[2];
        this.orszagKod = adatok[3];
        this.helyszin = adatok[4];
        this.datum = adatok[5];
    }

    public double getEredmeny() {
        return eredmeny;
    }

    public String getOrszagKod() {
        return orszagKod;
    }

    public String getDatum() {
        return datum;
    }

    public String toFileString() {
        return String.format("%s;%.2f;%s;%s;%s;%s", 
            helyezes, eredmeny, nev, orszagKod, helyszin, datum);
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %.2f m (%s, %s)", helyezes, nev, eredmeny, helyszin, datum);
    }
}
