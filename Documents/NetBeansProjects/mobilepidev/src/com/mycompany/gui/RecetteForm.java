/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
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
public class RecetteForm {

    Form f;
    Resources theme;
   

    public RecetteForm() {
       
        
        
        
        
       
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

        f = new Form("Recettes", new BorderLayout());
       
//       
        ServiceRecette serviceRecette = new ServiceRecette();
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        for (Recette r : serviceRecette.getList2()) {

            MultiButton mb = new MultiButton(r.getNom());
            mb.setTextLine2("details");
            Style s = UIManager.getInstance().getComponentStyle(r.getNom());

            FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

            EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 4), false);
//            mb.setIcon(URLImage.createToStorage(placeholder, "C:/wamp64/www/cupcakes-symfony-final/web/uploads/images/1513725497664.jpeg", "test"));

            Image roundMask = Image.createImage(placeholder.getWidth(), placeholder.getHeight(), 0xff000000);
            Graphics gr = roundMask.getGraphics();
            gr.setColor(0xffffff);
            gr.fillArc(0, 0, placeholder.getWidth(), placeholder.getHeight(), 0, 360);

            URLImage.ImageAdapter ada = URLImage.createMaskAdapter(roundMask);
            Image i = URLImage.createToStorage(placeholder, r.getImage(), "http://localhost/cupcakes-symfony-final/web/uploads/images/" + r.getImage(), ada);
            mb.setIcon(i);
            list.add(mb);
            mb.addActionListener(
                    (e) -> {
                        DetailsForm dr;
                        try {
                            dr = new DetailsForm(r.getId());
                            dr.getF().show();

                        } catch (IOException ex) {
                        }

                    }
            );

        }
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);

        fab.createSubFAB(FontImage.MATERIAL_STARS, "Mes favoris").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {


                FavorisForm a = new FavorisForm();
                a.getF().show();

            }
        });
        fab.createSubFAB(FontImage.MATERIAL_INSERT_CHART, "Statistiques").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ChartForm a = new ChartForm();
                a.getF().show();

            }
        });
        fab.bindFabToContainer(f.getContentPane());
        f.add(BorderLayout.CENTER, list);

        f.show();
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ADD, "TitleCommand", 4);

        f.getToolbar().addCommandToRightBar("", icon, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AjoutImageForm a = new AjoutImageForm();
                a.getF().show();

            }
        });
        Toolbar tb = f.getToolbar();

        Image logo = theme.getImage("cupcake.png");
        Container topBar = BorderLayout.center(new Label(logo));
        topBar.setUIID("SideCommand");
        tb.addComponentToSideMenu(topBar);

        tb.addMaterialCommandToSideMenu("Recettes", FontImage.MATERIAL_CAKE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                f.show();

            }
        });
       

        tb.addMaterialCommandToSideMenu("Patisseries", FontImage.MATERIAL_STORE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });

        tb.addMaterialCommandToSideMenu("Astuces", FontImage.MATERIAL_INFO, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        
         tb.addMaterialCommandToSideMenu("Mon profil", FontImage.MATERIAL_FACE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });

        tb.addMaterialCommandToSideMenu("Se déconnecter", FontImage.MATERIAL_ALL_OUT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Login a = new Login();
                a.getF().show();
                

            }
        });

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
