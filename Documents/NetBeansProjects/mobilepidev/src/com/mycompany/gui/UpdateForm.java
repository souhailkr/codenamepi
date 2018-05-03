/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
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
public class UpdateForm {

    Form f;
    Resources theme;

    public UpdateForm(Hashtable data) throws IOException {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
        f = new Form("Modifier recette");

        Button btn = new Button("confirmer");

        String image = ((String) data.get("image"));
        System.out.println(image);

        TextField nom = new TextField("", "nom");
        TextField desc = new TextField("", "description");
        TextField tp = new TextField("", "temps de preparation");
        TextField tc = new TextField("", "temps de cuisson");
        TextField nb = new TextField("", "nombre de personnes");

        ComboBox categorie = new ComboBox();

        ComboBox difficulte = new ComboBox();

        nom.setText((String) data.get("nom"));
        desc.setText((String) data.get("description"));
        tp.setText(String.valueOf(data.get("temps_preparation")));
        tc.setText(String.valueOf(data.get("temps_cuisson")));
        nb.setText(String.valueOf(data.get("nb_personnes")));

        if (((String) data.get("categorie")).equals("sucré")) {
            categorie.addItem("sucré");
            categorie.addItem("salée");
        } else {
            categorie.addItem("salée");

            categorie.addItem("sucré");
        }

        if (((String) data.get("difficulte")).equals("Facile")) {
            difficulte.addItem("Facile");
            difficulte.addItem("Moyen");
            difficulte.addItem("Difficile");

        } else if (((String) data.get("difficulte")).equals("Moyen")) {
            difficulte.addItem("Moyen");
            difficulte.addItem("Facile");
            difficulte.addItem("Difficile");

        } else {

            difficulte.addItem("Difficile");
            difficulte.addItem("Moyen");
            difficulte.addItem("Facile");
        }

        System.out.println(data);
        Container c = new Container(BoxLayout.y());
        c.setScrollableY(true);
        c.add(nom);
        c.add(desc);
        c.add(tp);
        c.add(tc);
        c.add(nb);
        Container cat = new Container();
        SpanLabel l1 = new SpanLabel("Categorie : ");
        cat.add(l1);
        cat.add(categorie);
        Container diff = new Container();
        SpanLabel l2 = new SpanLabel("Difficulté : ");
        diff.add(l2);

        diff.add(difficulte);
        f.add(c);
        f.add(cat);
        f.add(diff);
        f.add(btn);

        f.show();
        System.out.println(data);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                if (nom.getText().trim().equals("")) {
                    Dialog.show("champs vide", "Saisir un nom", "ok", "cancel");

                } else if (desc.getText().trim().equals("")) {
                    Dialog.show("champs vide", "Saisir un description", "ok", "cancel");

                } else if (hasNums(tp.getText()).trim().equals("None")) {
                    Dialog.show("champs invalide", "Saisir un temps de préparation valide", "ok", "cancel");
                } else if (hasNums(tc.getText()).trim().equals("None")) {
                    Dialog.show("champs invalide", "Saisir un temps de cuisson valide", "ok", "cancel");
                } else if (hasNums(nb.getText()).trim().equals("None")) {
                    Dialog.show("champs invalide", "Saisir un nombre de personnes valide", "ok", "cancel");
                } else {

                    InfiniteProgress ip = new InfiniteProgress();
                    Dialog dlg = ip.showInifiniteBlocking();
                    ServiceRecette ser = new ServiceRecette();
                    Recette r = new Recette();
                    r.setId((int) data.get("id"));
                    r.setNom(nom.getText());
                    r.setDescription(desc.getText());
                    r.setTemps_preparation(Integer.parseInt(tp.getText()));
                    r.setTemps_cuisson(Integer.parseInt(tc.getText()));
                    r.setNb_personnes(Integer.parseInt(nb.getText()));
                    r.setCategorie(categorie.getSelectedItem().toString());
                    r.setDifficulte(difficulte.getSelectedItem().toString());
                    if (data.containsKey("newimage")) {
                        Image newimg = ((Image) data.get("newimg"));
                        String newfileName = ((String) data.get("newimage"));

                        ImageIO imgIO = ImageIO.getImageIO();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        try {
                            imgIO.save(newimg, out, ImageIO.FORMAT_JPEG, 1);
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
                        request.addArgument("Name", newfileName);

                        request.setUrl("http://localhost/files/filename.php");
                        NetworkManager.getInstance().addToQueueAndWait(request);

                        r.setImage(newfileName);

                    } else {
                        r.setImage(image);
                    }
                    ser.updateRecette(r);
                    DetailsForm d;
                    try {
                        d = new DetailsForm((int) data.get("id"));
                        d.getF().show();
                        ToastBar.Status status = ToastBar.getInstance().createStatus();
                        status.setMessage("Recette modifiée");
                        status.showDelayed(50);
                        status.setExpires(3000);
                    } catch (IOException ex) {
                    }
                }
            }
        });

        f.getToolbar().addCommandToLeftBar("", theme.getImage("back-command.png"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    UpdateImageForm d = new UpdateImageForm(data);
                    d.getF().show();
                } catch (IOException ex) {
                }

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
        char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] toChar = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            toChar[i] = str.charAt(i);
            for (int j = 0; j < nums.length; j++) {
                if (toChar[i] == nums[j]) {
                    return str;
                }
            }
        }
        return "None";
    }

}
