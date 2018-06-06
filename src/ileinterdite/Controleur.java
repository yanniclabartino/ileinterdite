package ileinterdite;

import model.*;
import util.*;
import util.Utils.Pion;
import view.VueAventurier;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Controleur implements Observateur {

    private Grille grille;
    private int niveauEau;
    private VueAventurier[] ihm;
    private Trésor[] trésors;
    private HashMap<String, Aventurier> joueurs;
    private Stack<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private Stack<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;
    private boolean pouvoirPiloteDispo = true;

    @Override
    public void traiterMessage(Message m) {
    }
    
    //METHODES DE GESTION DU JEU
    
    private void gererDeplacement(Aventurier joueur) {
        //Gère les déplacements d'un aventurier
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        //Pouvoir pilote :
        if (joueur.getCouleur() == Utils.Pion.JAUNE && pouvoirPiloteDispo) {
            System.out.println("Voulez-vous vous déplacer sur n'importe qu'elle tuile ? (utilisable une fois par tour) :");
            System.out.print("(oui/non) => ");
            choix = sc.nextLine();
            choix = choix.toUpperCase().substring(0, 0);
            if (choix.equals("O")) {
                pouvoirPiloteDispo = false;
                tuilesDispo = calculTouteTuileDispo(g);
            } else {
                tuilesDispo = joueur.calculTuileDispo(g);
            }
        //Sinon si non-pilote (ou sans pouvoir) :
        } else {
            tuilesDispo = joueur.calculTuileDispo(g);
            g.afficheGrilleTexte(joueur);
            System.out.println("Voici la liste des tuiles disponible :");
            for (Tuile t : tuilesDispo) {
                t.affiche();
            }
            boolean choixValide = false;
            while (!choixValide) {
                System.out.println("\n\tChoix de la tuile :");
                System.out.print("X = ");
                choix = sc.nextLine();
                X = new Integer(choix);
                System.out.print("Y = ");
                choix = sc.nextLine();
                Y = new Integer(choix);
                Tuile tuileChoisie = g.getTuile(X, Y);
                if (tuilesDispo.contains(tuileChoisie)) {
                    choixValide = true;
                    joueur.seDeplace(tuileChoisie);
                } else {
                    System.out.println("\tCHOIX NON-VALIDE.");
                }
            }
        }

    }

    private void gererAssechement(Aventurier joueur) {
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = joueur.calculTuileAss(g);
        Utils.EtatTuile sec = Utils.EtatTuile.ASSECHEE;
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        int nbAssechement = 1;
        if (joueur.getCouleur() == Utils.Pion.ROUGE && tuilesDispo.size() >= 2) {
            System.out.println("Voulez-vous assecher 2 tuiles pour cette action ?");
            System.out.print("\t(oui/non)=> ");
            choix = sc.nextLine();
            choix = choix.toUpperCase().substring(0, 1);
            if (choix.equals("O")) {
                nbAssechement = 2;
            }
        }
        System.out.println("Voici la liste des tuiles disponible :");
        for (Tuile t : tuilesDispo) {
            t.affiche();
        }
        g.afficheGrilleTexte(joueur);
        for (int i = 0; i < nbAssechement; i++) {
            boolean choixValide = false;
            while (!choixValide) {
                System.out.println("\n\tChoix de la tuile :");
                System.out.print("X = ");
                choix = sc.nextLine();
                X = new Integer(choix);
                System.out.print("Y = ");
                choix = sc.nextLine();
                Y = new Integer(choix);
                Tuile tuileChoisie = g.getTuile(X-1, Y-1);
                if (tuilesDispo.contains(tuileChoisie)) {
                    choixValide = true;
                    tuileChoisie.setEtat(sec);
                } else {
                    System.out.println("CHOIX NON-VALIDE.");
                }
            }
        }
    }

    private void gererDonation() {/*Pour plus tard*/}

    private void gererGainTresor() {/*Pour plus tard*/}

    private void gererCarteOrange(Aventurier a) {
        
    }

    private void gererCarteBleue(int nivEau) {
        int nbPioche;
        System.out.print("Le niveau d'eau s'élève à : "+nivEau+".\nVous piochez donc : ");
        if (nivEau>=8){
            System.out.println("5 cartes.");
            nbPioche = 5;
        } else if (nivEau>=6){
            System.out.println("4 cartes.");
            nbPioche = 4;
        } else if (nivEau>=3) {
            System.out.println("3 cartes.");
            nbPioche = 3;
        } else {
            System.out.println("2 cartes.");
            nbPioche = 2;
        }
        CarteBleue c;
        for (int i = 0; i < nbPioche ; i++){
            c = piocheCarteBleue();
            Tuile t = getGrille().getTuile(c.getInnonde().getNom());
            System.out.println("la carte innondation "+t.getNom().toString()+" à été piochée...");
            if (t.getEtat()==Utils.EtatTuile.ASSECHEE){
                t.setEtat(Utils.EtatTuile.INONDEE);
                System.out.print("La tuile correspondante est désormais innondée : ");
                t.affiche();
                addDefausseBleues(c);
            } else if (t.getEtat()==Utils.EtatTuile.INONDEE) {
                t.setEtat(Utils.EtatTuile.COULEE);
                System.out.print("La tuile correspondante est désormais coulée : ");
                t.affiche();
            }
            if (getPiocheBleues().empty()){
                ArrayList<CarteBleue> defausse = getDefausseBleues();
                Collections.shuffle(defausse);
                for (CarteBleue b : defausse){
                    addPiocheBleue(b);
                }
                viderDefausseBleues();
            }
        }
    }
    
    //METHODES UTILES

    private Grille getGrille() {
        return grille;
    }
    
    private int getNiveau() {
        return niveauEau;
    }

    private ArrayList<Tuile> calculTouteTuileDispo(Grille g) {
        ArrayList<Tuile> touteTuileDispo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (g.getTuile(i, j).getEtat() != Utils.EtatTuile.COULEE) {
                    touteTuileDispo.add(g.getTuile(i, j));
                }
            }
        }

        return touteTuileDispo;
    }

    //méthodes pour les cartes bleues
    //pioche
    private Stack<CarteBleue> getPiocheBleues() {
        return piocheBleues;
    }
    private CarteBleue piocheCarteBleue(){
        return this.piocheBleues.pop();
    }
    private void addPiocheBleue(CarteBleue c){
        this.piocheBleues.push(c);
    }
    //défausse
    private ArrayList<CarteBleue> getDefausseBleues(){
        return this.defausseBleues;
    }
    private void addDefausseBleues(CarteBleue carte){
        this.defausseBleues.add(carte);
    }
    private void viderDefausseBleues(){
        this.defausseOranges.removeAll(this.defausseOranges);
    }

    //méthodes pour les cartes oranges
    //pioche
    private Stack<CarteOrange> getPiocheOranges() {
        return piocheOranges;
    }
    private CarteOrange piocheCarteOrange(){
        return this.piocheOranges.pop();
    }
    private void addPiocheOrange(CarteOrange c){
        this.piocheOranges.push(c);
    }
    //défausse
    private ArrayList<CarteOrange> getDefausseOranges(){
        return this.defausseOranges;
    }
    private void addDefausseOranges(CarteOrange carte){
        this.defausseOranges.add(carte);
    }
    private void viderDefausseOranges(){
        this.defausseOranges.removeAll(this.defausseOranges);
    }
    
    //CONSTUCTEUR
    
    public Controleur() {    
        //initialisations des tableaux/vecteurs
        joueurs = new HashMap<>();
        trésors = new Trésor[4];
        piocheOranges = new Stack<>();
        defausseOranges = new ArrayList<>();
        piocheBleues = new Stack<>();
        defausseBleues = new ArrayList<>();
        
        //Declaration de variable utiles pour l'interface texte
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer choixInt;
        boolean choixConforme = false;
        int nbJoueurs = 2;
        String[] nomJoueurs;
        
        //Choix de l'utilisateur du nombre de joueurs à jouer la partie (max 6)
        do {
            System.out.println("Combien de joueurs vont jouer ? Faites un choix (entier entre 2 et 6) : ");
            System.out.print("\t=> ");
            choix = sc.nextLine();
            choixInt = new Integer(choix);
            if (choixInt >= 2 || choixInt <= 6) {
                nbJoueurs = choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);

        //sélection des noms de joueurs
        nomJoueurs = new String[nbJoueurs];
        for (int i = 0; i < nbJoueurs; i++) {
            System.out.println("Nom joueur n°" + (i + 1) + " : ");
            choix = sc.nextLine();
            nomJoueurs[i] = choix;
        }

        //sélection de la difficulté
        choixConforme = false;
        do {
            System.out.println("Quel niveau de difficulté ? (1/2/3/4)");
            System.out.print("\t =>");
            choix = sc.nextLine();
            choixInt = new Integer(choix);
            if (choixInt >= 1 || choixInt <= 4) {
                this.niveauEau=choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);
        
        //Génération des 24 tuiles
        ArrayList<Tuile> Tuiles = new ArrayList();
        for (int i = 1; i < 25; i++) {
            Tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }

        //Initialisation de la Grille
        //Collections.shuffle(Tuiles);
        grille = new Grille(Tuiles);
        
        //Création des Aventuriers.   
        // A FINIR
        ArrayList<Aventurier> aventuriers = new ArrayList<>();
        if (Parameters.ALEAS) {
            aventuriers.add(new Pilote(grille.getTuile(3, 2), Pion.BLEU));
            aventuriers.add(new Navigateur(grille.getTuile(3, 1), Pion.JAUNE));
            aventuriers.add(new Ingénieur(grille.getTuile(3, 0), Pion.ROUGE));
            aventuriers.add(new Explorateur(grille.getTuile(4, 2), Pion.VERT));
            aventuriers.add(new Messager(grille.getTuile(1, 2), Pion.BLANC));
            aventuriers.add(new Plongeur(grille.getTuile(2, 1), Pion.NOIR));
            aventuriers = Utils.melangerAventuriers(aventuriers);
        } else {
            ArrayList<String> avDispo = new ArrayList<>();
            avDispo.add("Pilote");
            avDispo.add("Navigateur");
            avDispo.add("Ingénieur");
            avDispo.add("Explorateur");
            avDispo.add("Messager");
            avDispo.add("Plongeur");
            for (int i = 0 ; i < nbJoueurs ; i++) {
                System.out.println(nomJoueurs[i]+", veuillez sélectionner votre aventurier parmi :");
                for (String s : avDispo){
                    System.out.print("\t- ");
                    System.out.println(s);
                }
                do {
                    System.out.println("Choix");
                    System.out.print("\t =>");
                    choix = sc.nextLine();
                    choix = choix.toLowerCase().substring(0, 1);
                    if (choix.equals("pi")) {
                        avChoisis.add(aventuriers.get(0));
                        choixConforme = true;
                    } else if (choix.equals("na")) {
                        avChoisis.add(aventuriers.get(1));
                        choixConforme = true;
                    } else if (choix.equals("in")) {
                        avChoisis.add(aventuriers.get(2));
                        choixConforme = true;
                    } else if (choix.equals("ex")) {
                        avChoisis.add(aventuriers.get(3));
                        choixConforme = true;
                    } else if (choix.equals("me")) {
                        avChoisis.add(aventuriers.get(4));
                        choixConforme = true;
                    } else if (choix.equals("pl")) {
                        avChoisis.add(aventuriers.get(5));
                        choixConforme = true;
                    }
                } while (!choixConforme);
            }
            aventuriers = avChoisis;
        }
        //ajout de ceux-ci dans le HashMap
        for (int i = 0 ; i < nbJoueurs ; i++) {
            joueurs.put(nomJoueurs[i],aventuriers.get(i));
        }
        
        //Création des cartes oranges (trésor)        
        ArrayList<CarteOrange> tmpOranges = new ArrayList<>();
        for (int i = 0 ; i < 3 ; i++){
            tmpOranges.add(new CarteMonteeDesEaux());
            tmpOranges.add(new CarteHelicoptere());
        }
        for (int i = 0;i<5;i++){
            tmpOranges.add(new CarteTrésor(NomTresor.LE_CRISTAL_ARDENT));
            tmpOranges.add(new CarteTrésor(NomTresor.LA_STATUE_DU_ZEPHYR));
            tmpOranges.add(new CarteTrésor(NomTresor.LE_CALICE_DE_L_ONDE));
            tmpOranges.add(new CarteTrésor(NomTresor.LA_PIERRE_SACREE));
        }
        tmpOranges.add(new CarteSacDeSable());
        tmpOranges.add(new CarteSacDeSable());
        Collections.shuffle(tmpOranges);
        for (CarteOrange c : tmpOranges) {
            piocheOranges.push(c);
        }
        
        //Création des cartes bleues (innondation)        
        ArrayList<CarteBleue> tmpBleues = new ArrayList<>();
        for (Tuile t : grille.getGrille()){
            tmpBleues.add(new CarteBleue(t));
        }
        Collections.shuffle(tmpBleues);
        for (CarteBleue c : tmpBleues){
            piocheBleues.push(c);
        }
        
        //Création des Trésors
        trésors[0] = new Trésor(NomTresor.LE_CRISTAL_ARDENT);
        trésors[1] = new Trésor(NomTresor.LA_PIERRE_SACREE);
        trésors[2] = new Trésor(NomTresor.LA_STATUE_DU_ZEPHYR);
        trésors[3] = new Trésor(NomTresor.LE_CALICE_DE_L_ONDE);

        //Création d'une vue pour chaque aventurier...
        /*
        ihm = new VueAventurier[nbJoueurs];
        for (int i = 1; i < nbJoueurs; i++) {
            ihm[i] = new VueAventurier(nomJoueurs[i], joueurs.get(i - 1).getNomAventurier(), joueurs.get(i - 1).getCouleur().getCouleur());
        }
        */
        
        //pioche des cartes innondations nécessaires au commencement du jeu
        
        gererCarteBleue(getNiveau());
        
        //boucle du jeu
        //A FAIRE
        
        //test graphique
        grille.afficheGrilleTexte(joueurs.get(1));

    }

    //MAIN
    
    public static void main(String[] args) {
        new Controleur();
    }
}
