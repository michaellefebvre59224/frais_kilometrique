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
    <link rel="stylesheet" href="css/style_principal.css" type="text/css"/>
    <title>Connection</title>
</head>
<body>
<header>
<h1>FRAIS KILOMETRIQUE</h1>
</header>
<div id="corp">
<section id="connect">

<form method="POST">
    <fieldset id="zone">
        <legend>Identifiant de connection</legend>
        <label class="lab" id="lab_mail">Identifiant : </label>
        <input type="email" name="mail" class="input" id="input_mail" placeholder="adresse mail" maxlength="70">
        <br>
        <label class="lab" id="lab_pw">Mot de passe : </label>
        <input type="password" name="password" class="input" id="input_pw" placeholder="mot de passe" maxlength="30" >
    </fieldset>
    <br>

    <input type="submit" class="input" id="input_connect" value="Connection">

</form>

<p>
    <span><a href="inscription.jsp">Inscription</a></span>
    <span><a>Mot de passe oublié</a></span>
</p>

</section>
</div>

<footer>

</footer>
</body>
</html>
