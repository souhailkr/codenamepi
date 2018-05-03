package com.mycompany.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entite.fos_user;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 *
 * @author Sayahi Ala
 */
public class Login {

    Form f;
    TextField username;
    TextField password;
    Button btncon;
    private ConnectionRequest cn;
    String message;
    Resources theme;
    ImageViewer iv;

    public static fos_user user;

    public Login() {
        theme = UIManager.initFirstTheme("/theme");

        f = new Form("", new FlowLayout(Component.CENTER, Component.CENTER));

        f.setUIID("Form");
        f.setUIID("Textfield");

        f.getUnselectedStyle().setBgImage(theme.getImage("intro.jpg"));

        f.getToolbar().setUIID("Container");

        f.getToolbar().hideToolbar();

        Image logo = theme.getImage("cupcake.png");

        Container c = new Container(BoxLayout.y());
        username = new TextField("", "Nom d'utilisateur");
        password = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        btncon = new Button("se connecter");
        Button fbButton = new Button("se connecter avec Facebook");
        Button registerButton = new Button("S'inscrire");
        iv = new ImageViewer(logo);
        c.add(iv);
        c.add(username);
        c.add(password);
        c.add(btncon);
        c.add(fbButton);
        c.add(registerButton);


//        Dialog d = new Dialog();
//        d.setDialogUIID("Container");
//        d.setLayout(new BorderLayout());
//        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
//        cnt.addComponent(ip);
//        d.addComponent(BorderLayout.CENTER, cnt);
//        d.setTransitionInAnimator(CommonTransitions.createEmpty());
//        d.setTransitionOutAnimator(CommonTransitions.createEmpty());
//        d.showPacked(BorderLayout.CENTER, false);

        f.add(c);

        btncon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                String Username = username.getText();
                String Password = password.getText();

                if (Username.trim().equals("")) {

                    Dialog.show("champs vide", "Saisir Un nom d'utilisateur", "ok", "cancel");

                } else if (Password.trim().equals("")) {

                    Dialog.show("champs vide", "Saisir Un mot de passe", "ok", "cancel");

                } else {

                    //url = "http://localhost/mobile/cnx.php?name=" + Username + "&password=" + Password;
                    String url = "http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/api/login";
                    cn = new ConnectionRequest(url);
                    cn.setPost(false);
                    cn.addArgument("username", Username);
                    cn.addArgument("password", Password);
                    InfiniteProgress ip = new InfiniteProgress();
                    Dialog dlg = ip.showInifiniteBlocking();
                    cn.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            try {
                                user = new fos_user();
                                message = new String(cn.getResponseData(), "utf-8");
                                cn.setDisposeOnCompletion(dlg);
                                

                                if (message.length() > 3) {

                                    JSONParser j = new JSONParser();
                                    Map<String, Object> u = j.parseJSON(new CharArrayReader(message.toCharArray()));
                                    user.setId((int) Float.parseFloat(u.get("id").toString()));
                                    float id = Float.parseFloat(u.get("id").toString());

                                    RecetteForm a = new RecetteForm();

                                    a.getF().show();

                                } else {
                                    dlg.dispose();

                                    Dialog.show("Erreur", "Erreur login", "ok", "cancel");
                                }
                            } catch (UnsupportedEncodingException ex) {
                            } catch (IOException ex) {
                            }

                        }
                    });
                    NetworkManager.getInstance().addToQueue(cn);

                }
            }
        });

        /*   btnaff.addActionListener((e)->{
        Affichage a=new Affichage();
        a.getF().show();
        });*/
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Registre r = new Registre();
                r.getF1().show();
            }
        });

        fbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                UserForm uf = new UserForm();
                uf.facebookLogin(uf);
            }
        });
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    public static fos_user getUser() {
        return user;
    }

    public static void setUser(fos_user user) {
        Login.user = user;
    }

}
