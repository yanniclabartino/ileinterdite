package ileinterdite;

/**
 *
 * @author malodv
 */
public enum TypesMessages {
    COMMENCER, /*
    type de message pour debuter le jeu, accompagné de :
            - paramètres de jeu : aléatoire / trace dans la console.
            - informations quand aux aventuriers : nombre / nom + role.
            - la difficulté choisie.
    */
    SOUHAITE_DEPLACEMENT, /* type de message quand un joueur veut se déplacer. */
    ACTION_DEPLACEMENT, /*
    type de message quand un joueur essaye de se déplacer sur la grille, accompagné de :
            - la tuile d'arrivée
    */
    SOUHAITE_ASSECHER, /* type de message quand un joueur veut assécher une tuile. */
    ACTION_ASSECHER, /*
    type de message quand un joueur asseche une tuile, accompagné de :
            - la tuile concerné
    */
    SOUHAITE_DONNER, /* type de message quand un joueur veut donner une carte. */
    ACTION_DONNER, /*
    type de message quand un joueur donne une carte, accompagné de :
            - le destinataire de cette carte
    */
    ACTION_GAGNER_TRESOR, /* type de message quand un joueur veut gagner un trésor. */
    SOUHAITE_JOUER_SPECIALE, /* type de message quand un joueur veut jouer une carte spéciale. */
    JOUER_SPECIALE, /*
    type de message quand un joueur joue une carte spéciale, accompagné de :
            - la carte concernée
    */
    ASSECHER, /* type de message quand un joueur assèche avec une carte sac de sable, accompagné de :
            - la tuile concernée
    */
    FINIR_TOUR, /* type de message quand un joueur termine son tour. */
    ANNULER, /* type de message quand un joueur veut annuler son intéraction en cours. */
    SELECTIONNER_CARTE; /* type de message quand un joueur doit sélectionner une carte à utiliser ou donner */
}
