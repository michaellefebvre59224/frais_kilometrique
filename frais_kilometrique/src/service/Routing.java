package service;

import modele.FraisDAO;
import org.json.*;





import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.io.*;


public class Routing implements Serializable{

    public String findCoordoneesAdresse (String numero, String typeRue, String nomRue,int codePostal, String ville, String pays){
        if (numero==null || typeRue==null || nomRue==null || codePostal==0 || ville==null || pays==null) return "ERREUR";

        //remplacement des espace par %20
        numero = numero.replaceAll("\\s","%20");
        nomRue = nomRue.replaceAll("\\s","%20");
        typeRue = typeRue.replaceAll("\\s","%20");
        pays = pays.replaceAll("\\s","%20");
        ville = ville.replaceAll("\\s","%20");


        String adresse = numero+"%20"+typeRue+"%20"+nomRue+"&country="+pays+"&postalcode="+codePostal+"&locality="+ville;
        //String adresse = numero+"%20"+typeRue+"%20"+nomRue+"&country="+pays+"&postalcode="+codePostal+"&region="+region+"&locality="+ville;

        Client client = ClientBuilder.newClient();
        Response response = client.target("https://api.openrouteservice.org/geocode/search/structured?api_key=5b3ce3597851110001cf6248ac4ad383ccff42e5835fa4d27205c3f7&" +
                "address="+adresse).request(MediaType.TEXT_PLAIN_TYPE)
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .get();

        //recupération du status de la requète
        int status = response.getStatus();

        if (status!=200)return "STATUS";

        //resupération du fichier json de la reponse a la requete
        JSONObject fichierJsonCoordonees = new JSONObject(response.readEntity(String.class));

        //recupération du tableau features
        JSONArray features = fichierJsonCoordonees.optJSONArray("features");

        //recuperation du premier objet json dans le tableau feature
        JSONObject featuresZero = features.getJSONObject(0);

        //recuperation de 'lobjet json geometry
        JSONObject geometry = featuresZero.optJSONObject("geometry");

        //recuperation du tableau des coordonées
        JSONArray coordinates = geometry.optJSONArray("coordinates");

        //recupération des longitude et lattitude
        Double longitude = coordinates.getDouble(0);
        Double lattitude = coordinates.getDouble(1);

        String coordonnees = "["+longitude+","+lattitude+"]";

        return coordonnees;
    }

    public Double demandeDistanceTrajet(String coordonneesDepart, String coordonneesArrivee){
        if (coordonneesDepart==null || coordonneesArrivee == null) return 0.0d;



        //mise en forme du json pour les coordonnées du trajet
        String coordonnees = "{\"coordinates\":["+coordonneesDepart+","+coordonneesArrivee+"]}";


        Client client = ClientBuilder.newClient();
        Entity<String> payload = Entity.json(coordonnees);

        Response response = client.target("https://api.openrouteservice.org/v2/directions/driving-car")
                .request()
                .header("Authorization", "5b3ce3597851110001cf6248ac4ad383ccff42e5835fa4d27205c3f7")
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .header("Content-Type", "application/json; charset=utf-8")
                .post(payload);

        //recupération du status de la requète
        int status = response.getStatus();

        if (status!=200)return 0.0d;

        //recupération du fichier Json de la reponse à la requete
        JSONObject reponse = new JSONObject(response.readEntity(String.class));

        //récupération du tableau routes dans le fichier Json
        JSONArray routes = reponse.optJSONArray("routes");

        //récupération de l'objet Json emplacement 0 dans le tableau routes
        JSONObject routesZero = routes.getJSONObject(0);

        //récupération de l'objet Json summary
        JSONObject summary = routesZero.optJSONObject("summary");

        //récuperation de la distance total
        Double distance = summary.getDouble("distance");
        distance=distance/1000;
        return distance;
    }



    public static void main(String[] args) {
        Routing r = new Routing();

        String coordonneesDepart = r.findCoordoneesAdresse("1", "rue", "de thiant", 59224, "Monchaux sur écaillon", "FRANCE");
        String coordonneesArrivee = r.findCoordoneesAdresse("34", "rue", "la longue chasse", 59300, "Valenciennes", "FRANCE");

        Double distance = r.demandeDistanceTrajet(coordonneesDepart,coordonneesArrivee);

        System.out.println(distance);


    }



}
