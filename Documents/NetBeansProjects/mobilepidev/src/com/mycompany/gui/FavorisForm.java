/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entite.Favoris;
import com.mycompany.entite.Recette;
import com.mycompany.service.ServiceRecette;
import java.io.IOException;

/**
 *
 * @author SouhaiKr
 */
public class FavorisForm {
    Form f;
        Resources theme;

    
    public FavorisForm()
    {
         theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
        
        f = new Form("Mes Favoris", new BorderLayout());
//       
        ServiceRecette serviceRecette = new ServiceRecette();
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        for (Favoris v : serviceRecette.getListfavoris2()) {

            MultiButton mb = new MultiButton(v.getRecette().getNom());
            mb.setTextLine2("details");
            Style s = UIManager.getInstance().getComponentStyle(v.getRecette().getNom());

            FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

            EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 4), false);
//            mb.setIcon(URLImage.createToStorage(placeholder, "C:/wamp64/www/cupcakes-symfony-final/web/uploads/images/1513725497664.jpeg", "test"));

            Image roundMask = Image.createImage(placeholder.getWidth(), placeholder.getHeight(), 0xff000000);
            Graphics gr = roundMask.getGraphics();
            gr.setColor(0xffffff);
            gr.fillArc(0, 0, placeholder.getWidth(), placeholder.getHeight(), 0, 360);

            URLImage.ImageAdapter ada = URLImage.createMaskAdapter(roundMask);
            Image i = URLImage.createToStorage(placeholder, v.getRecette().getImage(), "http://localhost/cupcakes-symfony-final/web/uploads/images/" + v.getRecette().getImage(), ada);
            mb.setIcon(i);
            list.add(mb);
            mb.addActionListener(
                    (e) -> {
                        FavorisDetailsForm dr;
                        try {
                            dr = new FavorisDetailsForm(v.getId());
                            dr.getF().show();

                        } catch (IOException ex) {
                        }

                    }
            );
            
           

        }
          f.getToolbar().addCommandToLeftBar("", theme.getImage("back-command.png"),  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            RecetteForm r = new RecetteForm();
            r.getF().show();
        }});
             f.add(BorderLayout.CENTER, list);

        f.show();
        }
        
        
    
    
      public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
}
