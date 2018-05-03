package com.mycompany.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entite.fos_user;
import com.mycompany.service.ServiceUser;
import java.util.Vector;

/**
 *
 * @author Sayahi Ala
 */
public class Registre {

    Form f1;
    TextField nomField;
    TextField preField;
    TextField adrField;
    TextField userField;
    TextField passField;
    TextField emaField;
    Button ajoutuser;
    Vector<String> items1 = new Vector<>();

    public Form getF1() {
        return f1;
    }

    public void setF1(Form f1) {
        this.f1 = f1;
    }

//        items1.add("a:1:{i:0;s:11:\"ROLE_CLIENT\";}");
//        items1.add("a:1:{i:0;s:11:\"ROLE_CLIENT\";}");
//        items1.add("a:1:{i:0;s:10:\"ROLE_ECOLE\";}");
    //  ComboBox role = new ComboBox(items1);
    ComboBox<String> roleBox = new ComboBox<>("chef", "responsable");

    public Registre() {
        //  f = new Form("home");
        f1 = new Form("Créer Compte", new BoxLayout(BoxLayout.Y_AXIS));
        nomField = new TextField("", "nom");
        preField = new TextField("", "prenom");
        userField = new TextField("", "username");
        passField = new TextField("", "password");
        adrField = new TextField("", "adresse");
        emaField = new TextField("", "email");
        ajoutuser = new Button("ajouter");
        f1.add(nomField);
        f1.add(preField);
        f1.add(userField);
        f1.add(passField);
        f1.add(adrField);
        f1.add(emaField);
        f1.add(roleBox);
        f1.add(ajoutuser);

        ajoutuser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ServiceUser uc = new ServiceUser();
                System.out.println(roleBox.getSelectedItem());
                fos_user u = new fos_user(userField.getText(), emaField.getText(), roleBox.getSelectedItem(), passField.getText(), nomField.getText(), preField.getText(), adrField.getText());
                uc.ajoutUser(u);
            }
        });

    }
}
