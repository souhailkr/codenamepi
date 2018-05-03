/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;

/**
 *
 * @author SouhaiKr
 */
public class SplashScreenForm {

    Form f;
    Resources theme;

    public SplashScreenForm() {

        theme = UIManager.initFirstTheme("/theme");

        f = new Form("", new FlowLayout(Component.CENTER, Component.CENTER));
        f.getToolbar().setUIID("Container");

        f.getToolbar().hideToolbar();
        Image logo = theme.getImage("cupcake.png");
        f.add(logo);
          f.getContentPane().animateLayout(1000);

        UITimer timer = new UITimer(new Runnable() {
            public void run() {
                Login a = new Login();

                a.getF().show();
            }
        });
        timer.schedule(3000, false, f);

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
