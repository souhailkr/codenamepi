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
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
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
public class UpdateImageForm {

    Form f;
    Resources theme;
    Image img;
    String fileName;
    ImageViewer iv;
    Hashtable data;
    EncodedImage enc;

    public UpdateImageForm(Hashtable data) throws IOException {
        theme = UIManager.initFirstTheme("/theme");
        enc = EncodedImage.create("/giphy.gif");

        f = new Form("Modifier Recette");
        Button bt = new Button("changer image");
        Button btnnext = new Button("suivant");
        String oldfileName = ((String) data.get("image"));
        String newfileName = ((String) data.get("newimage"));

        Image oldimg = ((Image) data.get("img"));
        Image newimg = ((Image) data.get("newimg"));

        Container c = new Container(BoxLayout.y());
        System.out.println(newfileName);
        System.out.println(oldfileName);
        if( newfileName==null || oldfileName.equals(newfileName))
        {
        img = URLImage.createToStorage(enc, "recette:" + oldfileName, "http://localhost/cupcakes-symfony-final/web/uploads/images/" + oldfileName, URLImage.RESIZE_SCALE).scaled(320, 200);
        c.add(img) ;

        }
        else
        {
        c.add(newimg) ;

        }

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
                                    c.removeAll();
                                    c.repaint();
                                    c.revalidate();

                                    c.add(img);
                                  
                                    data.put("newimage", fileName);
                                    data.put("newimg", img);
                                    

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

                UpdateForm a;
                try {
                    a = new UpdateForm(data);
                    a.getF().show();

                } catch (IOException ex) {
                }

            }
        });

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        f.setScrollableY(true);
        f.add(c);
        f.add(bt);
        f.add(btnnext);

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
