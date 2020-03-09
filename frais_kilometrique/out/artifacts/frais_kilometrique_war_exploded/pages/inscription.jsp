<%--
  Created by IntelliJ IDEA.
  User: michael
  Date: 21/02/2020
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="pages/css/style_principal2.css" type="text/css"/>
    <title>Inscription</title>
</head>
<body>
<div id="corp">
    <section id="formulaire">

        <form method="POST" action="process?action=register" onsubmit="return verifForm(this)">
            <fieldset id="zone">
                <legend></legend>
                <label class="lab" id="lab_nom">Nom : </label>
                <br>
                <input type="text" name="nom" class="input" id="input_nom" placeholder="entrez votre nom" maxlength="70" required>
                <br>
                <label class="lab" id="lab_prenom">Prenom : </label>
                <br>
                <input type="text" name="prenom" class="input" id="input_prenom" placeholder="votre prénom" maxlength="70" required>
                <br>
                <label class="lab" id="lab_email">Adresse mail : </label>
                <br>
                <input type="email" name="email" class="input" id="input_mail" placeholder="votre mail" maxlength="70" required>
                <br>
                <label class="lab" id="lab_email_conf">Confiramtion adresse mail : </label>
                <br>
                <input type="email" name="email_conf" class="input" id="input_mail_conf" placeholder="votre mail" maxlength="70" required>
                <br>
                <label class="lab" id="lab_pw">Mot de passe : </label>
                <br>
                <input type="password" name="password" class="input" id="input_pw" placeholder="mot de passe" maxlength="30" required>
                <br>
                <label class="lab" id="lab_pw_conf">Confirmer le mot de passe : </label>
                <br>
                <input type="password" name="password_conf" class="input" id="input_pw_conf" placeholder="confirmation" maxlength="30" required>

            </fieldset>
            <span id="message"></span>
            <br>

            <input type="submit" class="bo_env" id="bp_env" value="Envoyer">

        </form>

        <p>

        </p>

    </section>
</div>

<footer>

</footer>
<script src="pages/js/envoie_inscription.js"></script>
</body>
</html>
