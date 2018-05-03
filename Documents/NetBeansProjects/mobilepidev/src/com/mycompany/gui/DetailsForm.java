/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entite.Recette;
import com.mycompany.service.ServiceRecette;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author SouhaiKr
 */
public class DetailsForm {

    Form f;
    SpanLabel nom;
    SpanLabel desc;
    SpanLabel tp;
    SpanLabel tc;
    SpanLabel nb;
    SpanLabel diff;
    SpanLabel cat;
    EncodedImage enc;
    ImageViewer iv;
    Image img;
    Command cm;
        Resources theme;


    public DetailsForm(int id) throws IOException {
                theme = UIManager.initFirstTheme("/theme");


        f = new Form("details");
        Container c = new Container(BoxLayout.y());
        c.setScrollableY(true);

        enc = EncodedImage.create("/giphy.gif");

        ServiceRecette serviceRecette = new ServiceRecette();
        for (Recette re : serviceRecette.getDetails2(id)) {
            //nom.setText(re.getNom());
            //desc.setText(re.getDescription()) ;
            img = URLImage.createToStorage(enc, "recette:" + re.getImage(), "http://localhost/cupcakes-symfony-final/web/uploads/images/" + re.getImage(), URLImage.RESIZE_SCALE).scaled(320, 200);
            iv = new ImageViewer(img);

            nom = new SpanLabel("Nom :" + re.getNom());
            desc = new SpanLabel("Description :" + re.getDescription());
            tp = new SpanLabel("Temps de préparation :" + re.getTemps_preparation() + " min");
            tc = new SpanLabel("Temps de cuisson :" + re.getTemps_cuisson() + " min");
            nb = new SpanLabel("Nombre de personnes :" + re.getNb_personnes());
            diff = new SpanLabel("Difficulté :" + re.getDifficulte());
            cat = new SpanLabel("Catégorie :" + re.getCategorie());

//                       
            f.getToolbar().addCommandToLeftBar("", theme.getImage("back-command.png"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                RecetteForm r = new RecetteForm();
                r.getF().show();
            }
        });

            if (!serviceRecette.isFavourite(id)) {

                cm = f.getToolbar().addCommandToOverflowMenu("Ajouter au favoris", null, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        serviceRecette.ajoutFavoris(re, 3);
                        ToastBar.Status status = ToastBar.getInstance().createStatus();
                        status.setMessage("Recette ajoutée à mes favoris");
                        status.showDelayed(50);
                        status.setExpires(3000);
                        f.getToolbar().removeOverflowCommand(cm);
                    }
                });

            }

            f.getToolbar().addCommandToOverflowMenu("Modifier", null, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Hashtable data = new Hashtable();
                    data.put("id", re.getId());
                    data.put("nom", re.getNom());
                    data.put("description", re.getDescription());
                    data.put("temps_preparation", re.getTemps_preparation());
                    data.put("temps_cuisson", re.getTemps_cuisson());
                    data.put("nb_personnes", re.getNb_personnes());
                    data.put("difficulte", re.getDifficulte());
                    data.put("categorie", re.getCategorie());
                    data.put("image", re.getImage());

                    UpdateImageForm r;
                    try {
                        r = new UpdateImageForm(data);
                        r.getF().show();

                    } catch (IOException ex) {
                    }

                }
            });

            f.getToolbar().addCommandToOverflowMenu("Supprimer", null, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (Dialog.show("Confirmer", "Voulez-vous supprimer cette recette?", "Ok", "Cancel")) {
                        serviceRecette.deleteRecette(id);

                        RecetteForm r = new RecetteForm();

                        r.getF().show();
                        ToastBar.Status status = ToastBar.getInstance().createStatus();
                        status.setMessage("Recette supprimée");
                        status.showDelayed(50);
                        status.setExpires(3000);
                    }

                }
            });

            c.add(iv);
            c.add(nom);
            c.add(desc);
            c.add(tp);
            c.add(tc);
            c.add(nb);
            c.add(diff);
            c.add(cat);
            f.add(c);

            f.show();
        }
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
