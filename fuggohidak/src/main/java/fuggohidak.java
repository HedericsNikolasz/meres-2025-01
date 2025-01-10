import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class fuggohidak {

    // A fő osztály a futtatáshoz
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FuggohidakFrame().setVisible(true));
    }
}

class Fuggohid {
    private String helyszin;
    private String nev;
    private String orszag;
    private int hossz;
    private int atadasEve;

    public Fuggohid(String helyszin, String nev, String orszag, int hossz, int atadasEve) {
        this.helyszin = helyszin;
        this.nev = nev;
        this.orszag = orszag;
        this.hossz = hossz;
        this.atadasEve = atadasEve;
    }

    public String getHelyszin() {
        return helyszin;
    }

    public String getNev() {
        return nev;
    }

    public String getOrszag() {
        return orszag;
    }

    public int getHossz() {
        return hossz;
    }

    public int getAtadasEve() {
        return atadasEve;
    }

    @Override
    public String toString() {
        return nev;
    }
}

class FuggohidakFrame extends JFrame {
    private DefaultListModel<Fuggohid> hidListModel;
    private JList<Fuggohid> hidList;
    private JTextField helyszinField, nevField, orszagField, hosszField, atadasEveField;
    private JComboBox<String> keresesiFeltetelCombo;

    public FuggohidakFrame() {
        setTitle("Függőhidak Adatkezelő");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Adatok betöltése
        List<Fuggohid> hidak = betoltAdatok("fuggohidak.csv");

        // ListBox
        hidListModel = new DefaultListModel<>();
        hidak.forEach(hidListModel::addElement);

        hidList = new JList<>(hidListModel);
        hidList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hidList.addListSelectionListener(e -> megjelenitAdatokat(hidList.getSelectedValue()));
        add(new JScrollPane(hidList), BorderLayout.WEST);

        // Részletes adatokat mutató panel
        JPanel reszletekPanel = new JPanel(new GridLayout(5, 2));

        reszletekPanel.add(new JLabel("Helyszín:"));
        helyszinField = new JTextField();
        helyszinField.setEditable(false);
        reszletekPanel.add(helyszinField);

        reszletekPanel.add(new JLabel("Név:"));
        nevField = new JTextField();
        nevField.setEditable(false);
        reszletekPanel.add(nevField);

        reszletekPanel.add(new JLabel("Ország:"));
        orszagField = new JTextField();
        orszagField.setEditable(false);
        reszletekPanel.add(orszagField);

        reszletekPanel.add(new JLabel("Hossz (m):"));
        hosszField = new JTextField();
        hosszField.setEditable(false);
        reszletekPanel.add(hosszField);

        reszletekPanel.add(new JLabel("Átadás éve:"));
        atadasEveField = new JTextField();
        atadasEveField.setEditable(false);
        reszletekPanel.add(atadasEveField);

        add(reszletekPanel, BorderLayout.CENTER);

        // Keresési és vezérlő panel
        JPanel keresesiPanel = new JPanel();

        keresesiPanel.add(new JLabel("Keresési feltétel:"));
        keresesiFeltetelCombo = new JComboBox<>(new String[]{"Minden", "2000 előtt", "2000 után"});
        keresesiPanel.add(keresesiFeltetelCombo);

        JButton keresButton = new JButton("Keresés");
        keresButton.addActionListener(e -> keresHidak());
        keresesiPanel.add(keresButton);

        JButton kilepesButton = new JButton("Kilépés");
        kilepesButton.addActionListener(e -> System.exit(0));
        keresesiPanel.add(kilepesButton);

        add(keresesiPanel, BorderLayout.SOUTH);
    }

    private List<Fuggohid> betoltAdatok(String fajlNev) {
        List<Fuggohid> hidak = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fajlNev))) {
            String sor;
            while ((sor = br.readLine()) != null) {
                String[] adatok = sor.split("\t"); // Feltételezve tabulátor a szeparátor
                if (adatok.length == 5) {
                    String helyszin = adatok[0];
                    String nev = adatok[1];
                    String orszag = adatok[2];
                    int hossz = Integer.parseInt(adatok[3]);
                    int atadasEve = Integer.parseInt(adatok[4]);
                    hidak.add(new Fuggohid(helyszin, nev, orszag, hossz, atadasEve));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Hiba a fájl beolvasása során: " + e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
        return hidak;
    }

    private void megjelenitAdatokat(Fuggohid hid) {
        if (hid != null) {
            helyszinField.setText(hid.getHelyszin());
            nevField.setText(hid.getNev());
            orszagField.setText(hid.getOrszag());
            hosszField.setText(String.valueOf(hid.getHossz()));
            atadasEveField.setText(String.valueOf(hid.getAtadasEve()));
        }
    }

    private void keresHidak() {
        String feltetel = (String) keresesiFeltetelCombo.getSelectedItem();
        hidListModel.clear();
        for (Fuggohid hid : betoltAdatok("C:\\Users\\nikoh\\Documents\\NetBeansProjects\\fuggohidak.csv")) {
            if ("2000 előtt".equals(feltetel) && hid.getAtadasEve() >= 2000) continue;
            if ("2000 után".equals(feltetel) && hid.getAtadasEve() < 2000) continue;
            hidListModel.addElement(hid);
        }
    }
}
