/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.entite.Favoris;
import com.mycompany.entite.Recette;
import com.mycompany.service.ServiceRecette;
import java.io.IOException;

/**
 *
 * @author SouhaiKr
 */
public class FavorisDetailsForm {
    Form f ;
    SpanLabel nom;
    SpanLabel desc;
    SpanLabel tp;
    SpanLabel tc;
    SpanLabel nb;
    EncodedImage enc;
    ImageViewer iv;
    Image img;
    public FavorisDetailsForm(int id) throws IOException
    {
         f = new Form("details", new FlowLayout(Component.CENTER));
        Container c = new Container(BoxLayout.y());
        c.setScrollableY(true);

        enc = EncodedImage.create("/giphy.gif");

        ServiceRecette serviceRecette = new ServiceRecette();
        for (Favoris v : serviceRecette.getFavorisDetails2(id)) {
            //nom.setText(re.getNom());
            //desc.setText(re.getDescription()) ;
            img = URLImage.createToStorage(enc, "recette:" + v.getRecette().getImage(), "http://localhost/cupcakes-symfony-final/web/uploads/images/" + v.getRecette().getImage(), URLImage.RESIZE_SCALE);
            iv = new ImageViewer(img);

            nom = new SpanLabel("Nom :" + v.getRecette().getNom());
            desc = new SpanLabel("Description :" + v.getRecette().getDescription());
            tp = new SpanLabel("Temps de préparation :" + v.getRecette().getTemps_preparation() + " min");
            tc = new SpanLabel("Temps de cuisson :" + v.getRecette().getTemps_cuisson() + " min");
            nb = new SpanLabel("Nombre de personnes :" + v.getRecette().getNb_personnes());

//                       
            f.getToolbar().addCommandToLeftBar("back", null, (ev) -> {
                FavorisForm r = new FavorisForm();
                r.getF().show();
            });

            f.getToolbar().addCommandToOverflowMenu("Supprimer de mes favoris", null, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                   
                    serviceRecette.deleteFavoris(id);

                    FavorisForm r = new FavorisForm();
                    r.getF().show();
                     ToastBar.Status status = ToastBar.getInstance().createStatus();
            status.setMessage("Recette supprimée de mes favoris");
            status.showDelayed(50);
            status.setExpires(3000);
                }
            });

           

           

            c.add(iv);
            c.add(nom);
            c.add(desc);
            c.add(tp);
            c.add(tc);
            c.add(nb);
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
