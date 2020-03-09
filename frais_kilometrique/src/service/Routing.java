package service;
// Maven : Add these dependecies to your pom.xml (java6+)
//  <dependency>
//    <groupId>org.glassfish.jersey.core</groupId>
//    <artifactId>jersey-client</artifactId>
//    <version>2.27</version>
//  </dependency>
//  <dependency>
//    <groupId>org.glassfish.jersey.inject</groupId>
//    <artifactId>jersey-hk2</artifactId>
//    <version>2.28</version>
//  </dependency>
//  <dependency>
//    <groupId>javax.activation</groupId>
//    <artifactId>activation</artifactId>
//    <version>1.1.1</version>
//  </dependency>
//  <dependency>
//    <groupId>javax.xml.bind</groupId>
//    <artifactId>jaxb-api</artifactId>
//    <version>2.3.1</version>
//  </dependency>




import org.json.*;





import javax.json.*;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.lang.reflect.Array;

public class Routing implements Serializable{

    public String findCoordoneesAdresse (String numero, String typeRue, String nomRue,int codePostal, String ville, String pays, String region){
        if (numero==null || typeRue==null || nomRue==null || codePostal==0 || ville==null || pays==null || region==null) return "ERREUR";

        //remplacement des espace par %20
        numero = numero.replaceAll("\\s","%20");
        nomRue = nomRue.replaceAll("\\s","%20");
        typeRue = typeRue.replaceAll("\\s","%20");
        pays = pays.replaceAll("\\s","%20");
        ville = ville.replaceAll("\\s","%20");
        region = region.replaceAll("\\s","%20");

        String adresse = numero+"%20"+typeRue+"%20"+nomRue+"&country="+pays+"&postalcode="+codePostal+"&region="+region+"&locality="+ville;

        Client client = ClientBuilder.newClient();
        Response response = client.target("https://api.openrouteservice.org/geocode/search/structured?api_key=5b3ce3597851110001cf6248ac4ad383ccff42e5835fa4d27205c3f7&" +
                "address="+adresse)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .get();

        //System.out.println("status: " + response.getStatus());
        //System.out.println("headers: " + response.getHeaders());
        //System.out.println("body:" + response.readEntity(String.class));

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

    public int demandeDistanceTrajet(String coordonneesDepart, String coordonneesArrivee){
        if (coordonneesDepart==null || coordonneesArrivee == null) return 0;

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

        if (status!=200)return 0;

        //recupération du fichier Json de la reponse à la requete
        JSONObject reponse = new JSONObject(response.readEntity(String.class));

        //récupération du tableau routes dans le fichier Json
        JSONArray routes = reponse.optJSONArray("routes");

        //récupération de l'objet Json emplacement 0 dans le tableau routes
        JSONObject routesZero = routes.getJSONObject(0);

        //récupération de l'objet Json summary
        JSONObject summary = routesZero.optJSONObject("summary");

        //récuperation de la distance total
        int distance = summary.getInt("distance");

        return distance;
    }



    public static void main(String[] args) {
        Routing r = new Routing();

        String coordonneesDepart = r.findCoordoneesAdresse("1", "rue", "de thiant", 59224, "Monchaux sur écaillon", "FRANCE", "NORD");
        String coordonneesArrivee = r.findCoordoneesAdresse("34", "rue", "la longue chasse", 59300, "Valenciennes", "FRANCE", "NORD");

        int distance = r.demandeDistanceTrajet(coordonneesDepart,coordonneesArrivee);

        System.out.println(distance);


    }



}