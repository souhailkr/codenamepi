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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entite.Favoris;
import com.mycompany.entite.Recette;
import com.mycompany.entite.Timestamp;
import com.mycompany.entite.fos_user;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SouhaiKr
 */
public class ServiceRecette {

    ArrayList<Recette> listRecettes = new ArrayList<>();

    public ArrayList<Recette> getList2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/all");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceRecette ser = new ServiceRecette();
                listRecettes = ser.getListTask(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listRecettes;
    }

    private ArrayList<Recette> getListTask(String json) {
        ArrayList<Recette> listRecettes = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("root");
            System.out.println(list);
            for (Map<String, Object> obj : list) {
                Recette e = new Recette();

                // System.out.println(obj.get("id"));
                float id = Float.parseFloat(obj.get("id").toString());
                System.out.println(id);
                e.setId((int) id);
                //e.setId(Integer.parseInt(obj.get("id").toString().trim()));
                e.setNom(obj.get("nom").toString());
                e.setDescription(obj.get("description").toString());
                e.setTemps_preparation((int) Float.parseFloat(obj.get("tempsPreparation").toString().trim()));
                e.setTemps_cuisson((int) Float.parseFloat(obj.get("tempsCuisson").toString().trim()));
                e.setNb_personnes((int) Float.parseFloat(obj.get("nbPersonnes").toString().trim()));
                e.setCategorie(obj.get("categorie").toString());
                e.setDifficulte(obj.get("difficulte").toString());
                e.setImage(obj.get("image").toString());

                //System.out.println(e);
                listRecettes.add(e);

            }

        } catch (IOException ex) {
        }
        //System.out.println(listRecettes);
        return listRecettes;

    }

    ArrayList<Recette> details = new ArrayList<>();

    public ArrayList<Recette> getDetails2(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/find/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceRecette ser = new ServiceRecette();
                details = ser.getDetails(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return details;
    }

    private ArrayList<Recette> getDetails(String json) {
        ArrayList<Recette> details = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> obj = j.parseJSON(new CharArrayReader(json.toCharArray()));

            Recette e = new Recette();

            // System.out.println(obj.get("id"));
            float id = Float.parseFloat(obj.get("id").toString());
            System.out.println(id);
            e.setId((int) id);
            //e.setId(Integer.parseInt(obj.get("id").toString().trim()));
            e.setNom(obj.get("nom").toString());
            e.setDescription(obj.get("description").toString());
            e.setTemps_preparation((int) Float.parseFloat(obj.get("tempsPreparation").toString().trim()));
            e.setTemps_cuisson((int) Float.parseFloat(obj.get("tempsCuisson").toString().trim()));
            e.setNb_personnes((int) Float.parseFloat(obj.get("nbPersonnes").toString().trim()));
            e.setCategorie(obj.get("categorie").toString());
            e.setDifficulte(obj.get("difficulte").toString());
            e.setImage(obj.get("image").toString());

            //System.out.println(e);
            details.add(e);

        } catch (IOException ex) {
        }
        //System.out.println(listRecettes);
        return details;

    }

    public void deleteRecette(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/delete/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void ajoutRecette(Recette r) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/new?" + "nom=" + r.getNom() + "&description=" + r.getDescription()
                + "&temps_preparation=" + r.getTemps_preparation() + "&temps_cuisson=" + r.getTemps_cuisson() + "&nb_personnes=" + r.getNb_personnes() + "&difficulte=" + r.getDifficulte()
                + "&image=" + r.getImage() + "&categorie=" + r.getCategorie();
        con.setUrl(Url);

        System.out.println("tt");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
     public void updateRecette(Recette r) {
        ConnectionRequest conn = new ConnectionRequest();
        String Url = "http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/update/"+r.getId()+"?nom=" + r.getNom() + "&description=" + r.getDescription()
                + "&temps_preparation=" + r.getTemps_preparation() + "&temps_cuisson=" + r.getTemps_cuisson() + "&nb_personnes=" + r.getNb_personnes() + "&difficulte=" + r.getDifficulte()
                + "&image=" + r.getImage() + "&categorie=" + r.getCategorie();
        conn.setUrl(Url);


        conn.addResponseListener((e) -> {
            String str = new String(conn.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(conn);
    }

    public void ajoutFavoris(Recette r, int id) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/add?" + "idUser=" + id + "&idRecette=" + r.getId();

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

    ArrayList<Favoris> listfavoris = new ArrayList<>();

    public ArrayList<Favoris> getListfavoris2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/allfavoris");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceRecette ser = new ServiceRecette();
                listfavoris = ser.getListfavoris(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listfavoris;
    }

    private ArrayList<Favoris> getListfavoris(String json) {
        ArrayList<Favoris> listfavoris = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> favoris = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(favoris);

            List<Map<String, Object>> list = (List<Map<String, Object>>) favoris.get("root");

            System.out.println(list);
            for (Map<String, Object> obj : list) {
                Favoris f = new Favoris();
                Recette r = new Recette() ;
                Map<String,Object> tou = (Map<String,Object>) obj.get("idRecette");
                System.out.println(tou);
                System.out.println(tou.get("nom"));


                float id = Float.parseFloat(tou.get("id").toString());
                System.out.println(id);
                r.setId((int) id);
                //e.setId(Integer.parseInt(obj.get("id").toString().trim()));
                r.setNom(tou.get("nom").toString());
                r.setDescription(tou.get("description").toString());
               
                r.setImage(tou.get("image").toString());
                f.setRecette(r);
                f.setId((int) Float.parseFloat(obj.get("id").toString())) ;

                //System.out.println(e);
                listfavoris.add(f);

            }

        } catch (IOException ex) {
        }
        //System.out.println(listRecettes);
        return listfavoris;

    }
    
    
      ArrayList<Favoris> favoris_details = new ArrayList<>();

    public ArrayList<Favoris> getFavorisDetails2(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/favoris/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceRecette ser = new ServiceRecette();
                favoris_details = ser.getFavorisDetails(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return favoris_details;
    }

    private ArrayList<Favoris> getFavorisDetails(String json) {
        ArrayList<Favoris> details = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> obj = j.parseJSON(new CharArrayReader(json.toCharArray()));
             Map<String,Object> re = (Map<String,Object>) obj.get("idRecette");

            Favoris f = new Favoris() ;
            Recette e = new Recette();

            // System.out.println(obj.get("id"));
            float id = Float.parseFloat(obj.get("id").toString());
            System.out.println(id);
            e.setId((int) id);
            //e.setId(Integer.parseInt(obj.get("id").toString().trim()));
            e.setNom(re.get("nom").toString());
            e.setDescription(re.get("description").toString());
            e.setTemps_preparation((int) Float.parseFloat(re.get("tempsPreparation").toString().trim()));
            e.setTemps_cuisson((int) Float.parseFloat(re.get("tempsCuisson").toString().trim()));
            e.setNb_personnes((int) Float.parseFloat(re.get("nbPersonnes").toString().trim()));
            e.setCategorie(re.get("categorie").toString());
            e.setDifficulte(re.get("difficulte").toString());
            e.setImage(re.get("image").toString());
            f.setRecette(e);
            //System.out.println(e);
            details.add(f);

        } catch (IOException ex) {
        }
        //System.out.println(listRecettes);
        return details;

    }
    
    public void deleteFavoris(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cupcakes-symfony-final/web/app_dev.php/mobile/recettes/favoris/delete/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public boolean isFavourite(int idRecette)
    {   
        for (Favoris c : getListfavoris2())
        {
        if (c.getRecette().getId()==idRecette){
            return true ;
        }
        
        }
        
        return false;
    
    
    }
    

}
