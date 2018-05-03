/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entite;

import java.util.Date;



/**
 *
 * @author SouhaiKr
 */
public class Recette {
    int id ;
    String nom ;
    String description ;
    int temps_preparation ;
    int temps_cuisson ;
    int nb_personnes ;
    String difficulte ;
    String image ;
    int user_id ;
    String categorie ;
    Date  DateDeCreation ;
    fos_user user ;

    public fos_user getUser() {
        return user;
    }

    public void setUser(fos_user user) {
        this.user = user;
    }
    
    
    

    public Recette(int id, String nom, String description, int temps_preparation, int temps_cuisson, int nb_personnes, String difficulte, String image, int user_id, String categorie, Date DateDeCreation) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.temps_preparation = temps_preparation;
        this.temps_cuisson = temps_cuisson;
        this.nb_personnes = nb_personnes;
        this.difficulte = difficulte;
        this.image = image;
        this.user_id = user_id;
        this.categorie = categorie;
        this.DateDeCreation = DateDeCreation;
    }

    public Recette(String nom, String description, int temps_preparation, int temps_cuisson, int nb_personnes, String difficulte, String image, int user_id, String categorie, Date DateDeCreation) {
        this.nom = nom;
        this.description = description;
        this.temps_preparation = temps_preparation;
        this.temps_cuisson = temps_cuisson;
        this.nb_personnes = nb_personnes;
        this.difficulte = difficulte;
        this.image = image;
        this.user_id = user_id;
        this.categorie = categorie;
        this.DateDeCreation = DateDeCreation;
    }

    public Recette() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemps_preparation() {
        return temps_preparation;
    }

    public void setTemps_preparation(int temps_preparation) {
        this.temps_preparation = temps_preparation;
    }

    public int getTemps_cuisson() {
        return temps_cuisson;
    }

    public void setTemps_cuisson(int temps_cuisson) {
        this.temps_cuisson = temps_cuisson;
    }

    public int getNb_personnes() {
        return nb_personnes;
    }

    public void setNb_personnes(int nb_personnes) {
        this.nb_personnes = nb_personnes;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Date getDateDeCreation() {
        return DateDeCreation;
    }

    public void setDateDeCreation(Date DateDeCreation) {
        this.DateDeCreation = DateDeCreation;
    }

    @Override
    public String toString() {
        return "Recette{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", temps_preparation=" + temps_preparation + ", temps_cuisson=" + temps_cuisson + ", nb_personnes=" + nb_personnes + ", difficulte=" + difficulte + ", image=" + image + ", user_id=" + user_id + ", categorie=" + categorie + ", DateDeCreation=" + DateDeCreation + '}';
    }
    
    
    
    
}
