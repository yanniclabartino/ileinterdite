package model;
import util.NomTuile;
import java.util.*;
import util.Utils.EtatTuile;

public class Tuile {

	ArrayList<Aventurier> possède;
	private EtatTuile etat;
	private int ligne;
	private int colonne;
	private NomTuile nom;

	public void suppAventurier(Aventurier a) {
	}

	public void addAventurier(Aventurier a) {
	}
        
	public void setEtat(EtatTuile nouvelEtat) {
		this.etat = nouvelEtat;
	}

}