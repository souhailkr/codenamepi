/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entite.fos_user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sana
 */
public class ServiceUser {

    public void ajoutUser(fos_user u) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/api/register?nom=" + u.getNom() + "&prenom=" + u.getPrenom() + "&email=" + u.getEmail()+"&username="+ u.getUsername()+ "&password=" +u.getPassword() +"&role="+u.getRoles();
        con.setUrl(Url);

        System.out.println("tt");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
//            if (str.trim().equalsIgnoreCase("OK")) {
//                f2.setTitle(tlogin.getText());
//             f2.show();
//            }
//            else{
//            Dialog.show("error", "login ou pwd invalid", "ok", null);
//            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public ArrayList<fos_user> getListUser(String json) {

        ArrayList<fos_user> listEtudiants = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(etudiants);
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("root");

            for (Map<String, Object> obj : list) {
                fos_user e = new fos_user();

                // System.out.println(obj.get("id"));
                float id = Float.parseFloat(obj.get("id").toString());
                System.out.println(id);
                e.setId((int) id);
                //e.setId(Integer.parseInt(obj.get("id").toString().trim()));
               
                e.setNom(obj.get("name").toString());
                System.out.println(e);
                listEtudiants.add(e);

            }

        } catch (IOException ex) {
        }
        System.out.println(listEtudiants);
        return listEtudiants;

    }
    ArrayList<fos_user> listUser = new ArrayList<>();
    
    public ArrayList<fos_user> getList2(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://41.226.11.243:10004/tasks/");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceUser ser = new ServiceUser();
                listUser = ser.getListUser(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listUser;
    }
    
    

}
