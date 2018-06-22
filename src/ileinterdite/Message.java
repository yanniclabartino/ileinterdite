package ileinterdite;

import java.util.HashMap;
import model.Aventurier;
import model.CarteOrange;
import model.Tuile;

public class Message {

    public Message() {
        
    }
    
    public TypesMessages type; //Type de message
    
    public int nbJoueurs, difficulté;           //Champ utilisé pour COMMENCER
    public HashMap<Aventurier, String> joueurs; //Champ utilisé pour COMMENCER
    public boolean logs, aleas;                 //Champ utilisé pour COMMENCER
    
    public Tuile tuile;                         //Champ utilisé pour ACTION_ASSECHER, ACTION_DEPLACEMENT ou JOUER_SPECIAL (Sac de sable)
    
    public int numCarte;                        //Champ utilisé pour ACTION_DONNER, JOUER_SPECIALE, DEFAUSSE_CARTE
    public Aventurier receveur;                 //Champ utilisé pour ACTION_DONNER
}
