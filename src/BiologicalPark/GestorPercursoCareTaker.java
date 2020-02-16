/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiologicalPark;

import java.util.Stack;

/**
 *
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class GestorPercursoCareTaker {
    private Stack<GestorPercursoMemento> objMementos;
    
    public GestorPercursoCareTaker(){
        objMementos = new Stack<>();
    }
    
    public void saveState(GestorPercurso gestorPercurso){
        GestorPercursoMemento memento = gestorPercurso.createMemento();
        objMementos.push(memento);
    }
    
    public void restoreState(GestorPercurso gestorPercurso){
        if(objMementos.isEmpty()) return;
        gestorPercurso.setMemento(objMementos.pop());
    }
}
