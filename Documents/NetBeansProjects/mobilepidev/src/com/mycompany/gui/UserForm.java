package com.mycompany.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.codename1.components.ScaleImageLabel;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 * @author Sayahi Ala
 */
public class UserForm extends com.codename1.ui.Form {
    
    public UserForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }
    
    public UserForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        showFormElements();
    }
    
     private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.LayeredLayout());
        setInlineStylesTheme(resourceObjectInstance);
        setInlineStylesTheme(resourceObjectInstance);
        setTitle("UserForm");
        setName("UserForm");
    }// </editor-fold>
     
      private void showFormElements() {
        this.setScrollable(false);
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        showData(this);
    }

    private void showData(UserForm form) {
        String token = (String) Storage.getInstance().readObject("token");
        if(token == null || token.equals("")){
            showIfNotLoggedIn(form);
        } else {
            showIfLoggedIn(form);
        }
    }

    private void showIfNotLoggedIn(UserForm form) {
        form.getContentPane().removeAll(); //Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
        Storage.getInstance().writeObject("token", "");
        ScaleImageLabel myPic = new ScaleImageLabel();
        //            Image img = Image.createImage("/anonimo.jpg");
        //   myPic.setIcon(img);
        Dimension d = new Dimension(50, 50);
        myPic.setPreferredSize(d);
        form.add(myPic);
        form.add(new Label("User not connected"));
        Button buttonLogin = new Button("Login");
        buttonLogin.addActionListener((e) -> {
            facebookLogin(form);
        });
        form.add(buttonLogin);
        form.revalidate();
        //form.show();
    }

    private void showIfLoggedIn(UserForm form) {
        String token = (String) Storage.getInstance().readObject("token");
        FaceBookAccess.setToken(token);
            final User me = new User();
            try {
                FaceBookAccess.getInstance().getUser("me", me, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String miNombre = me.getName();
                        
                        form.getContentPane().removeAll();
                        
                        form.add(new Label(miNombre));
                        
                        Button buttonLogout = new Button("Logout");
                        buttonLogout.addActionListener((e) -> {
                            facebookLogout(form);
                            showIfNotLoggedIn(form);
                        });

                        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
                        URLImage background = URLImage.createToStorage(placeholder, "fbuser.jpg",
                                "https://graph.facebook.com/v2.11/me/picture?access_token=" + token);
                        background.fetch();
                        ScaleImageLabel myPic = new ScaleImageLabel();
                        myPic.setIcon(background);
                        
                        form.add(myPic);
                        form.add(buttonLogout);
                        
                        form.revalidate();
                        //form.show();
                    }

                    
                });
            } catch (IOException ex) {
                ex.printStackTrace();
                showIfNotLoggedIn(form);
            }
    }

    private void facebookLogout(UserForm form) {
        String clientId = "591661031201011";
        String redirectURI = "https://www.google.com/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
        String clientSecret = "05331b66761aa8546e4243ea05890a0b";
        FacebookConnect fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);

        //trigger the login if not already logged in
        fb.doLogout();
        Storage.getInstance().writeObject("token", "EAAIaHNGXmPMBAMFbx5mS26ZCNKHIo3lWHUKmoqKTM9ziYVqAjuxIP5BNyvNBHMWC4E5XujGH2aeofY1O4OnMczOdBHNaRkEi9lkAxber4cxT3EVzP0Y55KK4AZBHkjqkmbRMVg6pKyVJk5LGwzPt6lsvuxysQCwybUwz19i2nvJxPZCFFwyoa5PsvQuf5NPuRDzO9VB6Q3mawNH5ndG");
        showIfNotLoggedIn(form);
    }
    
    public void facebookLogin(UserForm form) {
        //use your own facebook app identifiers here   
        //These are used for the Oauth2 web login process on the Simulator.
         String clientId = "591661031201011";
        String redirectURI = "https://www.google.com/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
        String clientSecret = "05331b66761aa8546e4243ea05890a0b";
        FacebookConnect fb = FacebookConnect.getInstance();
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);
        //Sets a LoginCallback listener
        fb.setCallback(new LoginCallback() {
            @Override
            public void loginFailed(String errorMessage) {
                System.out.println("Falló el login");
                Storage.getInstance().writeObject("token", "EAAIaHNGXmPMBAMFbx5mS26ZCNKHIo3lWHUKmoqKTM9ziYVqAjuxIP5BNyvNBHMWC4E5XujGH2aeofY1O4OnMczOdBHNaRkEi9lkAxber4cxT3EVzP0Y55KK4AZBHkjqkmbRMVg6pKyVJk5LGwzPt6lsvuxysQCwybUwz19i2nvJxPZCFFwyoa5PsvQuf5NPuRDzO9VB6Q3mawNH5ndG");
                showIfNotLoggedIn(form);
            }

            @Override
            public void loginSuccessful() {
                System.out.println("Funcionó el login");
                String token = fb.getAccessToken().getToken();
                Storage.getInstance().writeObject("token", token);
               // showIfLoggedIn(form);
               RecetteForm a=new RecetteForm();
               a.getF().show();
            }
            
        });
        //trigger the login if not already logged in
        if(!fb.isUserLoggedIn()){
            fb.doLogin();
        }else{
            //get the token and now you can query the facebook API
            String token = fb.getAccessToken().getToken();
            Storage.getInstance().writeObject("token", token);
            showIfLoggedIn(form);
        }
    }

    private void setInlineStylesTheme(Resources resourceObjectInstance) {
    }

    
    
    
}
