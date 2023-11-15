import ee.ut.dendroloj.Dendrologist;

import java.util.zip.ZipFile;


public class Kodu5 {

    static void kuvaKahendpuu(KOTipp juurTipp) {
        Dendrologist.drawBinaryTree(juurTipp, t -> Integer.toString(t.väärtus), t -> t.v, t -> t.p);}


    public static void main(String[] args) {
            KOTipp juur = AVLPuu(3);

             märgendaPuu(juur);
             int tippe = tippe(juur);
       int[] arvud =  new int[tippe];
       for (int i=0;i<tippe ;i++) arvud[i] = i;
      täidaKOP(juur,0,arvud);
//
//        //KOTipp() vähim = leiaVähim(juur);
//        KOTipp otsitav = otsiKirjet(juur, 5);
//        kuvaKahendpuu(juur);
//        //kuvaKahendpuu(otsitav);
        kuvaKahendpuu(juur);
        paremPööre(juur);
        vasakPööre(juur);
        kuvaKahendpuu(juur);
        //System.out.println("pikkus: "+ puuPikkus(juur,0));
        //kuvaKahendpuu(lisaKirje(juur, 15));

        //lisaKirje(juur,5);
        //lisaKirje(juur,6);
        //kuvaKahendpuu(lisaKirje(juur,7));

//        System.out.println("Hello world!");
    }
   public static KOTipp lisaKirje(KOTipp juur, int väärtus) {

        if (juur == null) return lisaVaartus(juur,väärtus);
        lisaVaartus(juur,väärtus);

        int vahe = puuPikkus(juur.v,0)- puuPikkus(juur.p,0);
        if (vahe == -2) vahetus(juur.p);
        if (vahe == 2) vahetus(juur.v);
       System.out.println("pikkusP: "+(puuPikkus(juur.p,0)));
       System.out.println("pikkusv: "+(puuPikkus(juur.v,0)));
        return juur;

    }

    public static void vahetus(KOTipp juur){
        System.out.println("kontroll-. " + juur.väärtus);

    }
    public static KOTipp vasakPööre(KOTipp juur){
        KOTipp vahtuvParem = juur.p;
        KOTipp paremaVasak = vahtuvParem.v;
        KOTipp paremaParem = vahtuvParem.p;
        KOTipp koguPuuVasak;
        kuvaKahendpuu(juur);
        //algse juure vasak osa
        if (juur.v != null){
        koguPuuVasak = new KOTipp(juur.v.väärtus,juur.v.v,juur.v.p);
        juur.v.v = koguPuuVasak;
        juur.v.väärtus = juur.väärtus;
        juur.väärtus = vahtuvParem.väärtus;
        juur.v.p = paremaVasak;}
        else {
            koguPuuVasak = new KOTipp(juur.väärtus,null,null);
            juur.v = koguPuuVasak;
            juur.v.v = paremaVasak;
            juur.väärtus = vahtuvParem.väärtus;

            //juur.v.p = paremaVasak;
        }
//        kuvaKahendpuu(koguPuuVasak);
//        kuvaKahendpuu(juur.v.v);


        juur.p =juur.p.p;



        return juur;
    }
    public static KOTipp paremPööre(KOTipp juur){
        KOTipp vahtuvVasak = juur.v;

        KOTipp vasakuVasak = vahtuvVasak.v;
        KOTipp vasakuParem = vahtuvVasak.p;

        //algse juure parem osa
        KOTipp koguPuuParem = new KOTipp(juur.p.väärtus,juur.p.v,juur.p.p);

        juur.p.p = koguPuuParem;
        juur.p.väärtus = juur.väärtus;
        juur.väärtus = vahtuvVasak.väärtus;
        juur.p.v = vasakuParem;

        juur.v = juur.v.v;

        return juur;
    }
    public static KOTipp paremVasakOööre(KOTipp juur){
        return null;
    }

    public static KOTipp eemaldaKirje(KOTipp juur, int väärtus) {
        System.out.println("eemaldati: " + väärtus);
        KOTipp algne = juur;
        //yhe pikkysega, siis tagasta tyhjus
        if (puuPikkus(juur,0) == 0) return null;

        //kui eemaldataval on molemad alluvad
        if (algne.v != null && algne.p != null && puuPikkus(algne, 0 ) == 1) {
            eemaldavaartus(juur,väärtus);
            System.out.println("eemaldati algne");
            return juur;
        }
        //kui eemaldataval on yks alluv, siis tagasta yksik tipp
        if (algne.v == null || algne.p == null){
            KOTipp yksik = eemaldavaartus(juur, väärtus);
            if (puuPikkus(yksik,0) == 0) {System.out.println("yksik tagastati");return yksik;}
        }
        eemaldavaartus(algne,väärtus);
        System.out.println("tagastati tava");
        kuvaKahendpuu(juur);
        return juur;
    }
    public static int puuPikkus(KOTipp juur, int lugeja){
        if (juur == null) return 0;
        if(juur.v == null && juur.p == null){ return lugeja;}

        int vasak = puuPikkus(juur.v, lugeja+1);
        int parem = puuPikkus(juur.p, lugeja+1);

        return  vasak <= parem ? parem : vasak;

    }

    public static KOTipp liidaAVLpuud(KOTipp avl1, KOTipp avl2) {
        throw new UnsupportedOperationException();
    }


    public static KOTipp AVLPuu(int n){
        System.out.println(n);
        if (n ==0) return null;
        if (n ==1) return  new KOTipp(0);
        KOTipp juur = new KOTipp(0);

        juur.v = AVLPuu(n - (Math.random() > 0.5 ? 1 : 2));
        juur.p = AVLPuu(n - (Math.random() > 0.5 ? 1 : 2));
        return juur;
    }

    public static void täidaKOP(KOTipp juur ,int vasakPiir, int[] arvud){
        if (juur == null)return;
        int vasakud = tippe(juur.v);
        juur.väärtus = arvud[vasakPiir + vasakud];
        täidaKOP(juur.v, vasakPiir,arvud);
        täidaKOP(juur.p, vasakPiir + vasakud+1, arvud);
    }

    public static void märgendaPuu(KOTipp juur){
        if (juur == null) return;
        märgendaPuu(juur.v);
        märgendaPuu(juur.p);
        if (juur.v != null) juur.x = juur.v.x + 1 + tippe(juur.v.p);
    }

    public static int tippe(KOTipp juur){
        if (juur == null) return  0;
        return 1+ tippe(juur.v) + tippe(juur.p);
    }
    public static KOTipp leiaVähim(KOTipp juur){
        return null;
    }
    public static KOTipp otsiKirjet(KOTipp juur, int kirje){

        if (juur == null || juur.väärtus == kirje) return juur;

        //otsime paremast harust

        //otsime vasakust harutst
        if (kirje < juur.väärtus){ return otsiKirjet(juur.v, kirje);}

        return otsiKirjet(juur.p, kirje);
    }

    public static KOTipp lisaVaartus(KOTipp juur, int kirje){
        if (juur == null){
            System.out.println("lisatud");
            return new KOTipp(kirje);}

        if (kirje < juur.väärtus){
            juur.v = lisaVaartus(juur.v, kirje);}
        if (kirje > juur.väärtus){
            juur.p = lisaVaartus(juur.p, kirje);
        }
        return juur;
    }

    public static KOTipp eemaldavaartus(KOTipp juur, int kirje){
        if (juur == null) return  null;
        if (juur.väärtus == kirje){
            if (juur.v == null && juur.p == null )return null;
            if (juur.v != null && juur.p != null ){
                KOTipp jargmine = juur.p;
                System.out.println("vahetatav =" + jargmine.väärtus);
                while (jargmine.v != null) jargmine = jargmine.v;
                juur.väärtus = jargmine.väärtus;
                kuvaKahendpuu(juur);
                juur.p = eemaldavaartus(juur.p, juur.väärtus);

            }

            else if (juur.v != null) {
                System.out.println("tagastasin juure:v " + juur.v.väärtus);return juur.v;}
            else if (juur.p != null) return juur.p;

        }
        else if ( kirje < juur.väärtus){
            juur.v = eemaldavaartus(juur.v, kirje);
        }
        else if ( kirje > juur.väärtus){
            juur.p = eemaldavaartus(juur.p, kirje);
        }

        return juur;
    }
}