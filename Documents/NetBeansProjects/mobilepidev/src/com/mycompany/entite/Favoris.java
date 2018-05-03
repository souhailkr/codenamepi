/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entite;

/**
 *
 * @author SouhaiKr
 */
public class Favoris {
    
    int id ;
    Recette recette ;
    fos_user user ;

    public Favoris() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public fos_user getUser() {
        return user;
    }

    public void setUser(fos_user user) {
        this.user = user;
    }
    
    
    
    
}
