import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.GraphCanvas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Kodu8 {
    static class Tipp {
        final String info; // tipu info
        final List<Kaar> kaared = new ArrayList<>(); // sellest tipust väljuvate kaarte loetelu
        int x = 0; // abiväli täisarvu hoidmiseks
        int y = 0; // teine abiväli täisarvu hoidmiseks
        Tipp z = null; // abiväli tipu hoidmiseks

        public Tipp(String info) {
            this.info = info;
        }
    }

    static class Kaar {
        final Tipp alg; // kaare lähtetipp
        final Tipp lõpp; // kaare suubumistipp
        final double kaal; // kaare kaal (kui peaks vaja olema)

        public Kaar(Tipp alg, Tipp lõpp, double kaal) {
            this.alg = alg;
            this.lõpp = lõpp;
            this.kaal = kaal;
        }
    }

    /**
     * Leiab minimaalse kaugusega toesepuu Kruskali algoritmiga.
     *
     * @param nimed Asukohtade nimed
     * @param K     Asukohtade koordinaadid tabelina, kus iga rida on kujul [laiuskraad, pikkuskraad]
     * @return Minimaalse toesepuu kaarte loend, kujul [[i1, j2], [i2, j2], ...], kus i ja j on asukohtade indeksid alates 0-ist
     */
    public static int[][] toesKruskal(String[] nimed, double[][] K) {
        throw new UnsupportedOperationException();
    }

    /**
     * Leiab lähendi rändkaupmehe probleemile.
     *
     * @param nimed Asukohtade nimed
     * @param K     Asukohtade koordinaadid tabelina, kus iga rida on kujul [laiuskraad, pikkuskraad]
     * @return Rändkaupmehe lähend kui asukohtade läbimise järjestus arvude 0...n-1 permutatsioonina, kus n on tippude arv
     */
    public static int[] rändkaupmees(String[] nimed, double[][] K) {
        throw new UnsupportedOperationException();
    }


    public static void main(String[] args) throws IOException {
        Dendrologist.setUIScale(1.5);
        NimedegaKoordinaadid tipud = loeKoordinaadid(new File("src/omniva.csv"));
        HashMap<String[], Double> koikKaugused = leiaKoikKaugused(tipud);

        System.out.println(koikKaugused.values());
        for (String[] a: koikKaugused.keySet()){
            System.out.println(Arrays.toString(a));
        }

        //kuvaGraaf(tipud, tipud.K);
    }

    // Abimeetodid andmete lugemiseks failist:

    /**
     * Loeb TSV failist graafi külgnevusstruktuurina.
     * Tagastab külgnevusstruktuuri tippude järjendina.
     */
//    static List<Tipp> loeKülgnevusstruktuur(File fail) throws IOException {
//        List<String> read = Files.readAllLines(fail.toPath());
//        Map<String, Tipp> tipud = new LinkedHashMap<>();
//        for (String rida : read) {
//            String[] väärtused = rida.split("\t");
//            Tipp tipp = new Tipp(väärtused[0]);
//            tipud.put(tipp.info, tipp);
//        }
//        for (String rida : read) {
//            String[] väärtused = rida.split("\t");
//            Tipp tipp = tipud.get(väärtused[0]);
//            for (int i = 2; i < väärtused.length; i++)
//                tipp.kaared.add(new Kaar(tipp, tipud.get(väärtused[i]), 1));
//        }
//        return new ArrayList<>(tipud.values());
//    }

    /**
     * Loeb CSV failist tippude nimed ja koordinaadid.
     * Tagastab koordinaatide massiivi kujul [[laiuskraad_1, pikkuskraad_1], ..., [laiuskraad_n, pikkuskraad_n]]
     * ja nimede massiivi kujul [nimi_1, ..., nimi_n].
     * <p>
     * Mõeldud kasutamiseks failiga omniva.csv.
     */
    static NimedegaKoordinaadid loeKoordinaadid(File fail) throws IOException   {
        List<String> read = Files.readAllLines(fail.toPath());
        String[] nimed = new String[read.size()];
        double[][] K = new double[read.size()][];
        for (int i = 0; i < read.size(); i++) {
            String[] väärtused = read.get(i).split(",");
            nimed[i] = väärtused[0];
            K[i] = new double[]{Double.parseDouble(väärtused[1]), Double.parseDouble(väärtused[2])};
        }
        return new NimedegaKoordinaadid(K, nimed);
    }

    static class NimedegaKoordinaadid {
        public final double[][] K; // koordinaatide massiiv (iga element on kujul [pikkuskraad, laiuskraad]).
        public final String[] nimed;

        public NimedegaKoordinaadid(double[][] K, String[] nimed) {
            this.K = K;
            this.nimed = nimed;
        }
    }

    // Abimeetodid graafide kuvamiseks dendroloj abil:

    /**
     * Kuvab külgnevusstruktuurina esitatud graafi.
     */
    static void kuvaGraaf(List<Tipp> tipud) {
        DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.ROOT));
        GraphCanvas<Tipp> lõuend = new GraphCanvas<>();
        for (Tipp tipp : tipud) {
            lõuend.drawVertex(tipp, tipp.info);
            for (Kaar kaar : tipp.kaared)
                lõuend.drawDirectedEdge(kaar.alg, kaar.lõpp, df.format(kaar.kaal));
        }
        Dendrologist.drawGraph(lõuend);
    }

    /**
     * Kuvab külgnevusstruktuurina esitatud graafi, kus tippude asukohad on määratud koordinaatide massiivis K olevate koordinaatidega.
     * <p>
     * NB: See meetod eeldab, et koordinaatide massiivis K ja tipude järjendis on tipud samas järjestuses!
     */
    static void kuvaGraaf(List<Tipp> tipud, double[][] K) {
        DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.ROOT));
        GraphCanvas<Tipp> lõuend = new GraphCanvas<>();
        for (int i = 0; i < tipud.size(); i++) {
            // Pikkuskraadi cos(60°) = 0.5-ga korrutamine vähendab oluliselt moonutust (vt https://et.wikipedia.org/wiki/Ruutlabaprojektsioon#Valemid)
            lõuend.drawFixedVertex(tipud.get(i), tipud.get(i).info.split(" ")[0], K[i][1] * 0.5, K[i][0]);
        }
        for (Tipp tipp : tipud) {
            for (Kaar kaar : tipp.kaared)
                lõuend.drawDirectedEdge(kaar.alg, kaar.lõpp, df.format(kaar.kaal));
        }
        Dendrologist.drawGraph(lõuend);
    }


    /**
     * Leiab kahe punkti vahelise kauguse, kasutades valemit siit:
     * https://en.wikipedia.org/wiki/Haversine_formula
     *
     * @param lai1 Esimese punkti laiuskraad
     * @param pik1 Esimese punkti pikkuskraad
     * @param lai2 Teise punkti laiuskraad
     * @param pik2 Teise punkti pikkuskraad
     * @return Punktide vaheline kaugus kilomeetrites
     */
    public static double kaugus(double lai1, double pik1, double lai2, double pik2) {
        double dLaius = Math.pow(Math.sin(Math.toRadians(lai2 - lai1) / 2), 2);
        return 2 * 6371.0088 * Math.asin(Math.sqrt(dLaius +
                (1 - dLaius - Math.pow(Math.sin(Math.toRadians(lai1 + lai2) / 2), 2)) *
                        Math.pow(Math.sin(Math.toRadians(pik2 - pik1) / 2), 2)));
    }
    public static HashMap<String[], Double> leiaKoikKaugused(NimedegaKoordinaadid tipud){
        HashMap<String[], Double> pikkusedTippudeVahel = new HashMap<>();
        for (int i=0;i< tipud.nimed.length-1;i++){
            for (int j =i+1;j< tipud.nimed.length;j++){
                String esimene = tipud.nimed[i];
                String teine = tipud.nimed[j];
                double kaugusEjaTvahel = kaugus(tipud.K[i][0],tipud.K[i][1],tipud.K[j][0],tipud.K[j][1]);
                String[] kobinatsioon = {esimene, teine};
                pikkusedTippudeVahel.put(kobinatsioon,kaugusEjaTvahel);
            }
        }
        return pikkusedTippudeVahel;
    }

}