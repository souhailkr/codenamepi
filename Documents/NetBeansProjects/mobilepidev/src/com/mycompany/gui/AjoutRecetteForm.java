/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import com.mycompany.entite.Recette;
import com.mycompany.service.ServiceRecette;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author SouhaiKr
 */
public class AjoutRecetteForm {

    EncodedImage enc;
    ImageViewer iv;
    Image img;
    Form f;
    Resources theme;
    Button btnajout = new Button("ajouter recette");

    public AjoutRecetteForm(Hashtable data) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

        f = new Form("Ajouter Recette");

        try {
            enc = EncodedImage.create("/giphy.gif");
        } catch (IOException ex) {

        }
//        img = URLImage.createToStorage(enc, "x", "http://localhost/files/a.png", URLImage.RESIZE_SCALE);
//        iv = new ImageViewer(img);
        Container c = new Container(BoxLayout.y());
        c.setScrollableY(true);

        iv = new ImageViewer();
        TextField nom = new TextField("", "nom");
        TextField desc = new TextField("", "description");
        TextField temps_p = new TextField("", "temps de preparation (en min)");
        TextField temps_c = new TextField("", "temps de cuisson (en min)");
        TextField nb_personnes = new TextField("", "nombre de personnes");

        ComboBox categorie = new ComboBox();

        categorie.addItem("sucré");
        categorie.addItem("salée");
        ComboBox difficulte = new ComboBox();
        difficulte.addItem("Facile");
        difficulte.addItem("Moyen");
        difficulte.addItem("Difficile");

      
        if (nom.getText().trim().equals(""))
            {
       
        btnajout.getPressedStyle().removeListeners();
            }
        else
        {
        }
      

        btnajout.addActionListener((e) -> {
            
            if (nom.getText().trim().equals(""))
            {
            Dialog.show("champs vide", "Saisir un nom", "ok", "cancel");

            }
            else if (desc.getText().trim().equals(""))
            {
            Dialog.show("champs vide", "Saisir un description", "ok", "cancel");

            }
           else if (hasNums(temps_p.getText()).trim().equals("None"))
           {
              Dialog.show("champs invalide", "Saisir un temps de préparation valide", "ok", "cancel");
          }
            else if (hasNums(temps_c.getText()).trim().equals("None"))
           {
              Dialog.show("champs invalide", "Saisir un temps de cuisson valide", "ok", "cancel");
          }
          
             else if (hasNums(nb_personnes.getText()).trim().equals("None"))
           {
              Dialog.show("champs invalide", "Saisir un nombre de personnes valide", "ok", "cancel");
          }
             
          
            
            else
                    
            {    
             InfiniteProgress ip = new InfiniteProgress();
            Dialog dlg = ip.showInifiniteBlocking();    
            
            ServiceRecette ser = new ServiceRecette();
            Recette t = new Recette();
            t.setNom(nom.getText());
            t.setDescription(desc.getText());
            t.setTemps_preparation(Integer.parseInt(temps_p.getText()));
            t.setTemps_cuisson(Integer.parseInt(temps_c.getText()));
            t.setNb_personnes(Integer.parseInt(nb_personnes.getText()));
            t.setCategorie(categorie.getSelectedItem().toString());
            t.setDifficulte(difficulte.getSelectedItem().toString());
            
            String fileName = ((String) data.get("image"));
            Image img = ((Image) data.get("img"));



            ImageIO imgIO = ImageIO.getImageIO();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                imgIO.save(img, out, ImageIO.FORMAT_JPEG, 1);
            } catch (IOException ex) {
            }
            byte[] ba = out.toByteArray();
            String Imagecode = Base64.encode(ba);
            ConnectionRequest request = new ConnectionRequest() {
                @Override
                protected void handleErrorResponseCode(int code, String message) {
                    System.out.println("Code :" + code + " Msg :" + message);
                }

            };
            request.setPost(true);
            request.setHttpMethod("POST");
            request.addArgument("Image", Imagecode);
            request.addArgument("Name", fileName);

            request.setUrl("http://localhost/files/filename.php");
            NetworkManager.getInstance().addToQueueAndWait(request);

//            t.setImage("1513725497664.jpeg");
            t.setImage(fileName);

            ser.ajoutRecette(t);

            RecetteForm r = new RecetteForm();

            r.getF().show();
            ToastBar.Status status = ToastBar.getInstance().createStatus();
            status.setMessage("Recette ajoutée");
            status.showDelayed(50);
            status.setExpires(3000);
            }
        });
        
        c.add(nom);
        c.add(desc);
        c.add(temps_p);
        c.add(temps_c);
        c.add(nb_personnes);
        Container cat = new Container();
        SpanLabel l1 = new SpanLabel("Categorie : ");
        cat.add(l1);
        cat.add(categorie);
        Container diff = new Container();
        SpanLabel l2 = new SpanLabel("Difficulté : ");
        diff.add(l2);

        diff.add(difficulte);
         c.add(cat);
        c.add(diff);
       
        c.add(btnajout);
        f.add(c);
        f.setScrollableY(true);

        f.show();

        f.getToolbar().addCommandToLeftBar("", theme.getImage("back-command.png"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AjoutImageForm r = new AjoutImageForm();
                r.getF().show();
            }
        });

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
     public String hasNums(String str) {
        char[] nums = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        char[] toChar = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            toChar[i] = str.charAt(i);
            for (int j = 0; j < nums.length; j++) {
                if (toChar[i] == nums[j]) { return str; }
            }
        }
        return "None";
    }

}
