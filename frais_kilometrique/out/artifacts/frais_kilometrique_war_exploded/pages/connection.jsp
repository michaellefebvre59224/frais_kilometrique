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
    <style type="text/css"> @import url(pages/css/style_principal.css);</style>
    <title>Connection</title>
</head>
<body>
<header>
<h1>FRAIS KILOMETRIQUE</h1>
</header>
<div id="corp">
<section id="connect">

<form method="POST" action="process?action=authenticate">
    <fieldset id="zone">
        <legend>Identifiant de connection</legend>
        <label class="lab" id="lab_mail">Identifiant : </label>
        <input type="email" name="email" class="input" id="input_mail" placeholder="adresse mail" maxlength="70">
        <br>
        <label class="lab" id="lab_pw">Mot de passe : </label>
        <input type="password" name="password" class="input" id="input_pw" placeholder="mot de passe" maxlength="30" >
    </fieldset>
    <br>

    <input type="submit" class="input" id="input_connect" value="Connection">

</form>

<p>
    <span><a href="process?action=afficheInscription">Inscription</a></span>
    <span><a>Mot de passe oublié</a></span>
</p>

</section>
</div>

<footer>

</footer>
</body>
</html>
