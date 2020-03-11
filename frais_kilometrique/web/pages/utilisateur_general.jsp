<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: michael
  Date: 28/02/2020
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <style type="text/css"> @import url(pages/css/style_principal2.css);</style>
    <title>Ecran générale</title>

</head>
<body>
<div id="corp">

    <div id="trajet">

        <form method="POST" action="process?action=archiveTrajet">
            <div id="list">
        <table>
            <tr><th>Date</th><th>type</th><th>distance</th><th>Selectionner</th><tr/>
<c:forEach items="${trajetsUtilisateur}" var="trajet">
    <tr>
        <td>${trajet.dateTrajet}</td>
        <td>${trajet.route}</td>
        <td>${trajet.distance} Km</td>
        <td><input  type="checkbox" name="choix" value="${trajet.idTrajet}" required/></td>

    </tr>
</c:forEach>
        </table>
            </div>
            <input id="choix" type="submit" value="supprimer">
        </form>

        <form method="POST" action="process?action=ajoutTrajet">
            <fieldset>
                <legend>Adresse depart</legend>
                <label>Numero : </label>
                <input id="numero_ad_dep" type="text" name="numero_ad_dep" height="18px" width="20"  maxlength="5" required>
                <label>Type de rue : </label>
                <input id="type_rue_dep" type="text" name="type_rue_dep" height="18px" width="20"   maxlength="25" required>
                <label>Nom de rue : </label>
                <input id="nom_rue_dep" type="text" name="nom_rue_dep" height="18px" width="40"  maxlength="70" required>
                <br/>
                <label>Code postal : </label>
                <input id="code_postal_dep" type="text" name="code_postal_dep" height="18px" width="20"   maxlength="5" required>
                <label>Ville : </label>
                <input id="ville_dep" type="text" name="ville_dep" height="18px" width="40"  maxlength="25" required>
            </fieldset>
            <fieldset>
                <legend>Adresse arrive</legend>
                <label>Numero : </label>
                <input id="numero_ad_arr" type="text" name="numero_ad_arr" height="18px" width="20"  maxlength="5" required>
                <label>Type de rue : </label>
                <input id="type_rue_arr" type="text" name="type_rue_arr" height="18px" width="20"   maxlength="25" required>
                <label>Nom de rue : </label>
                <input id="nom_rue_arr" type="text" name="nom_rue_arr" height="18px" width="40"  maxlength="70" required>
                <br/>
                <label>Code postal : </label>
                <input id="code_postal_arr" type="text" name="code_postal_arr" height="18px" width="20"   maxlength="5" required>
                <label>Ville : </label>
                <input id="ville_arr" type="text" name="ville_arr" height="18px" width="40"  maxlength="25" required>
            </fieldset>
            <label for="start" id="date">Date du trajet:</label>

            <input type="date" id="start" name="date"
                   value="${date}" required
                   min="2018-01-01" max="${date}">

            <label>Type de trajet : </label>
            <select name="route" id="route" value="ALLER" required>
                <option value="ALLER">Aller</option>
                <option value="RETOUR">Retour</option>
                <option value="ALLER/RETOUR">Aller/Retour</option>
            </select>

            <input type="submit" class="input" id="input_ajout_trajet" value="Ajouter">
        </form>
    </div>
    <div id="compte">

        <table>
            <tr><th>Nom</th><th>Prenom</th></tr>
            <tr><td><input type="text" value="${utilisateur.nom}"/></td>
                <td><input type="text" value="${utilisateur.prenom}"/></td></tr>

        </table>
    </div>
    <div id="vehicule">

    </div>

</div>
</body>
<link rel= »stylesheet » href= »https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css« >
<script src= »https://code.jquery.com/ui/1.12.1/jquery-ui.js« ></script>
<script>
    $(function() {
        $(inputText-mbtext-102).datepicker();
    } );
</script>
</html>
