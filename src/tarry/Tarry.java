package tarry;

import java.util.ArrayList;
import java.util.Scanner;

public class Tarry {
    private  ArrayList<String> sled;
    private  boolean podmienka;      //ak je true tak sa znova spusti algoritmu
    private  String aktualnyVrchol;
    private  int pocetVrcholov;
    private  int pocetHran;
    private  String zadanaHrana;
    private  String[][] hrany;
    private  String[] splitnutaHrana;
    private  ArrayList<String> navstivene;
    private Scanner skener;
    private Scanner skener2;
    private String nevracat;


    public Tarry() {
        sled = new ArrayList<String>();
        this.podmienka = true;
        this.aktualnyVrchol = aktualnyVrchol;
        this.pocetVrcholov = pocetVrcholov;
        this.pocetHran = pocetHran;
        this.zadanaHrana = zadanaHrana;
        navstivene = new ArrayList<String>();
        skener = new Scanner(System.in);
        skener2 = new Scanner(System.in);
        this.nevracat = nevracat;
    }



    public void nastavenieZaciatku() {
        aktualnyVrchol = "A";               // acko sa hneď nastavý ako prvý
        navstivene.add(aktualnyVrchol);     // hneď sa aj pridá do navštívených
        sled.add(aktualnyVrchol);           //prida sa aj do sledu
        while (podmienka) {                 //ked sa bude rovnat podmienka false tak sa prestane vykonavat algoritmus
            algoritmus();
        }
    }
    public void vypisSledu() {
        System.out.println("Sledy : ");     //nasledovne riadky vypisu sled
        for (String sled : sled) {
            System.out.print(sled);
        }
        System.out.println();
    }

//    public void zadajVrcholy() {
//        System.out.println("zadaj počet vrcholov");
//        pocetVrcholov = skener.nextInt();
//    }

    public void zadanieHran() {
        System.out.println("Zadaj počet hrán");
        pocetHran = skener.nextInt();

        splitnutaHrana = new String[2];                 //vytvorenie pola pre hranu
        hrany = new String[pocetHran * 2][2];           //vytvorim pole stringov 1.parameter pocet hran *2 pretoze hrana ma 2 smery, 2.par. zaciatok a koniec hrany
        for (int j = 0; j < pocetHran; j++) {
            System.out.println("Zadaj hrany v tvare (vrchol-vrchol)");
            System.out.println("Zadaj " + (j + 1) + ". hranu :");
            zadanaHrana = skener2.nextLine();
            splitnutaHrana = zadanaHrana.split("-");    //zadany tvar (A-B) rozdeli na dve casti podla "-",
            hrany[j][0] = splitnutaHrana[0];            //prida na 0 index pola zaciatok hrany (teda co sa nachadza pre splitom("-"))
            hrany[j][1] = splitnutaHrana[1];            //prida na 1 index pola zaciatok hrany (teda co sa nachadza za splitom("-"))
            hrany[j + pocetHran][0] = splitnutaHrana[1];    //tieto dva riadky robia to iste len hranu zadaju opacne aby sme sa dostali spat
            hrany[j + pocetHran][1] = splitnutaHrana[0];
        }
    }

    public void algoritmus() {
        for (int i = 0; i < (pocetHran * 2); i++) {     //prechadza vsetky hrany aj zdvojene
            if (hrany[i][0] != null) {                  //preskočí vymazané hrany ktoré sa neskor vymazavaju (resp. nastavuju na null)
                if ((hrany[i][0].equals(aktualnyVrchol)) && !(navstivene.contains(hrany[i][1]))) {      //ak hrana zacina na zaciaocny vrchol a zaroven sa jej koncovy vrchol nenachadza v navstivenich tak sa vykona
                    navstivene.add((hrany[i][1]));      //pridame koncovy vrchol do navstivenych (teda je to hrana prveho prichodu)
                    aktualnyVrchol = (hrany[i][1]);     //aktualny vrchol sa nastavy na konecny vrchol hrany aby sme mohli pokracovat dalej po grafe
                    sled.add(hrany[i][1]);              //koncovy sa prida do sledu abz sme videli ako sme chodili po grafe
                    nevracat = hrany[i][0];
                    hrany[i][0] = null;                 //tieto dva riadky "zmazu" hranu aby sme ju uz nebrali do uvahy teda tymto smerom sa uz neda ist
                    hrany[i][1] = null;
                    return;                             //ukonci celu metodu
                }
            }
        }
        for (int j = 0; j < (pocetHran * 2); j++) {
            if (hrany[j][0] != null) {                  //preskočí vymazané hrany ktoré sa neskor vymazavaju (resp. nastavuju na null)
                if ((hrany[j][0].equals(aktualnyVrchol)) && !(hrany[j][1]).equals(nevracat)) {   //ak sa zaciatok hrany rovna aktualnemu vrcholu a koncovy sa nerovna vrcholu ktorym zacina cely sled(keby sme sa mu nevzhli nemusime prejst cely graf)
                    //zacina s aktualnym a koncovi nie je A
                    aktualnyVrchol = (hrany[j][1]);
                    sled.add(hrany[j][1]);
                    hrany[j][0] = null;
                    hrany[j][1] = null;
                    return;
                }
            }
        }

        for (int k = 0; k < (pocetHran * 2); k++) {
            if (hrany[k][0] != null) {
                if (hrany[k][0].equals(aktualnyVrchol)) {       //ak sa zaciatocny vrchol rovna aktualnemuvrcholu teda je to klasicky posun
                    //zacina s aktualnym
                    aktualnyVrchol = (hrany[k][1]);
                    sled.add(hrany[k][1]);
                    nevracat = hrany[k][0];
                    hrany[k][0] = null;
                    hrany[k][1] = null;
                    return;
                }
            }
        }
        podmienka = hladaniePodgrafu();         //podmienka sa nastavy podla toho co vrati metoda hladaniePodgrafu()

    }

    public boolean hladaniePodgrafu() {          //vzdy ked algoritmus prejde hranou tak ju nastavy na null, ak sa este po ukonceny algoritmu nachadza nejaka hrana ktora este nieje null (teda je to podgraf lebo sme sa donej nijako nedostali)
        //tak nastavy zaciatok tejto hrany na aktualny vrchol a znovu spusti algoritmus
        for (int i = 0; i < pocetHran * 2; i++) {
            if (hrany[i][0] != null) {
                aktualnyVrchol = (hrany[i][0]);
                sled.add("\n");                 //zacne sled pisat do noveho riadku aby sme ho nepisali do kopy ale budu odelene
                sled.add(aktualnyVrchol);
                return true;    //ak sa splni podmienka tak sa znova vykona algoritmus
            }
        }
        return false;       //ak nie tak sa znova nevykona algoritmus
    }
}
