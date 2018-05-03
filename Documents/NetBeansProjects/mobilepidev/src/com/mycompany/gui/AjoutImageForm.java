/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author SouhaiKr
 */
public class AjoutImageForm {

    Form f;
    Image img;
    String fileName;
    ImageViewer iv;
    Hashtable data;
    Resources theme;

    public AjoutImageForm() {

        theme = UIManager.initFirstTheme("/theme");

        f = new Form("Ajouter Recette");
        Button bt = new Button("ajouter image");
        Button btnnext = new Button("suivant");
       

        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().openGallery(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        if (ev != null && ev.getSource() != null) {
                            String filePath = (String) ev.getSource();
                            int fileNameIndex = filePath.lastIndexOf("/") + 1;
                            fileName = filePath.substring(fileNameIndex);

                            if (filePath != null) {

                                try {
                                    img = Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath)).scaled(320, 200);

                                    f.add(img);
                                    f.add(btnnext);

                                    f.revalidate();
                                    data = new Hashtable();
                                    data.put("image", fileName);
                                    data.put("img", img);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                }, Display.GALLERY_IMAGE);

            }
        });

        btnnext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                AjoutRecetteForm a = new AjoutRecetteForm(data);
                a.getF().show();

            }
        });

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        f.setScrollableY(true);

        f.add(bt);
      

        f.show();

        f.getToolbar().addCommandToLeftBar("", theme.getImage("back-command.png"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                RecetteForm r = new RecetteForm();
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

}
