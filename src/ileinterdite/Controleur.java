package ileinterdite;

import model.*;
import util.*;
import util.Utils.Pion;
import view.VueAventurier;
import java.util.*;

public class Controleur implements Observateur {

    private Grille grille;
    private int niveauEau;
    private VueAventurier[] ihm;
    private Trésor[] trésors;
    private ArrayList<Aventurier> joueurs;
    private Stack<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private Stack<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;
    private boolean pouvoirPiloteDispo = true;

    @Override
    public void traiterMessage(Message m) {
    }
    
    //METHODES DE GESTION
    
    public void gererDeplacement(Aventurier joueur) {
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
            choix = choix.toUpperCase().substring(0, 1);
            if (choix.equals("O")) {
                pouvoirPiloteDispo = false;
                tuilesDispo = calculTouteTuileDispo(g);
            } else {
                tuilesDispo = joueur.calculTuileDispo(g);
            }
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
                    //Voir VP pour savoir s'il est plus judicieux
                    //de passer par une unique méthode (qu'on
                    //détail par la suite niveau MVC)
                } else {
                    System.out.println("\tCHOIX NON-VALIDE.");
                }
            }
        }

    }

    public void gererAssechement(Aventurier joueur) {
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
                Tuile tuileChoisie = g.getTuile(X, Y);
                if (tuilesDispo.contains(tuileChoisie)) {
                    choixValide = true;
                    tuileChoisie.setEtat(sec);
                } else {
                    System.out.println("CHOIX NON-VALIDE.");
                }
            }
        }
    }

    public void gererDonation() {/*Pour plus tard*/}

    public void gererGainTresor() {/*Pour plus tard*/}

    public void gererCarteOrange(Aventurier a) {
        
    }

    public void gererCarteBleue(int nivEau) {
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
        Stack<CarteBleue> piocheBleu = this.getPiocheBleues();
        CarteBleue c;
        for (int i = 0; i < nbPioche ; i++){
            c = piocheBleu.pop();
            System.out.println("la carte innondation "+c.getInnonde().getNom().toString()+" à été piochée...");
            if (c.getInnonde().getEtat()==Utils.EtatTuile.ASSECHEE){
                c.getInnonde().setEtat(Utils.EtatTuile.INONDEE);
                // A FINIR
            } else if (c.getInnonde().getEtat()==Utils.EtatTuile.INONDEE) {
                c.getInnonde().setEtat(Utils.EtatTuile.COULEE);
            }
            // A FINIR
        }
    }
    
    //METHODES UTILES

    public final Grille getGrille() {
        return grille;
    }
    
    public final int getNiveau() {
        return niveauEau;
    }

    public final ArrayList<Tuile> calculTouteTuileDispo(Grille g) {
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
    
    public final Stack<CarteBleue> getPiocheBleues() {
        return this.piocheBleues;
    }
    
    public final ArrayList<CarteBleue> getDefausseBleues(){
        return this.defausseBleues;
    }
    
    public final Stack<CarteOrange> getPiocheOranges(){
        return this.piocheOranges;
    }
    
    public final ArrayList<CarteOrange> getDefausseOranges(){
        return this.defausseOranges;
    }
    
    //CONSTUCTEUR
    
    public Controleur() {    
        //initialisations des tableaux/array
        joueurs = new ArrayList<>();
        trésors = new Trésor[4];
        piocheOranges = new Stack<>();
        defausseOranges = new ArrayList<>();
        piocheBleues = new Stack<>();
        defausseBleues = new ArrayList<>();
        
        //Génération des 24 tuiles
        ArrayList<Tuile> Tuiles = new ArrayList();
        for (int i = 1; i < 25; i++) {
            Tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }

        //Initialisation de la Grille
        //Collections.shuffle(Tuiles);
        grille = new Grille(Tuiles);
        
        //Création des Aventuriers.   
        joueurs.add(new Pilote(grille.getTuile(3, 2), Pion.BLEU));
        joueurs.add(new Navigateur(grille.getTuile(3, 1), Pion.JAUNE));
        joueurs.add(new Ingénieur(grille.getTuile(3, 0), Pion.ROUGE));
        joueurs.add(new Explorateur(grille.getTuile(4, 2), Pion.VERT));
        joueurs.add(new Messager(grille.getTuile(1, 2), Pion.BLANC));
        joueurs.add(new Plongeur(grille.getTuile(2, 1), Pion.NOIR));
        //Mélange de ceux-ci dans joueurs.
        //joueurs = Utils.melangerAventuriers(joueurs);
        
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
        for (Tuile t : Tuiles){
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

        //Declaration de variable utiles
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
            System.out.println("\t =>");
            choix = sc.nextLine();
            choixInt = new Integer(choix);
            if (choixInt >= 1 || choixInt <= 4) {
                this.niveauEau=choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);

        //Création d'une vue pour chaque aventurier...
        /*
        ihm = new VueAventurier[nbJoueurs];
        for (int i = 1; i < nbJoueurs; i++) {
            ihm[i] = new VueAventurier(nomJoueurs[i], joueurs.get(i - 1).getNomAventurier(), joueurs.get(i - 1).getCouleur().getCouleur());
        }
        */
        
        //pioche des cartes innondations nécessaires au commencement du jeu
        //gererCarteBleue(niveauEau);
        
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
