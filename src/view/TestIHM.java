/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import model.CarteHelicoptere;
import model.CarteOrange;
import model.CarteSacDeSable;
import model.CarteTrésor;
import util.NomTresor;

/**
 *
 * @author yannic
 */

public class TestIHM {

    
    public static void main(String[] args) {
        // TODO code application logic here
        VueAventurier v = new VueAventurier();
        ArrayList<CarteOrange> main = new ArrayList<>();
        main.add(new CarteHelicoptere());
        main.add(new CarteSacDeSable());
        main.add(new CarteTrésor(NomTresor.LE_CRISTAL_ARDENT));
        main.add(new CarteTrésor(NomTresor.LE_CRISTAL_ARDENT));
        main.add(new CarteTrésor(NomTresor.LA_STATUE_DU_ZEPHYR));
        v.dessinCartes(main);
            
    }
    
}
