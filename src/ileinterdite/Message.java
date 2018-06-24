package ileinterdite;

import java.util.HashMap;
import model.Aventurier;
import model.Tuile;

public class Message {

    public Message() {
        
    }
    
    public TypesMessages type;                  //Type de message
    
    public int nbJoueurs, difficulté;           //Champ utilisé pour COMMENCER
    public HashMap<Aventurier, String> joueurs; //Champ utilisé pour COMMENCER
    public boolean logs, aleas;                 //Champ utilisé pour COMMENCER
    
    public Tuile tuile;                         //Champ utilisé pour ACTION_ASSECHER, ACTION_DEPLACEMENT ou ASSECHER (sac de sable)
    
    public int numCarte;                        //Champ utilisé pour SELECTIONNER_CARTE, JOUER_SPECIALE, DONNER_CARTE
    public Aventurier receveur, joueurAffiché;  //Champ utilisé pour ACTION_DONNER | VOIR_JOUEUR
}
