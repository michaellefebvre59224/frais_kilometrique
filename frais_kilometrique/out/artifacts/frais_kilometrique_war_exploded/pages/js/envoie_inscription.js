/////////////// D E C L A R A T I O N   D E S   V A R I A B L E S //////////////////////
var inputMail = document.getElementById("input_mail");
var inputMailConf = document.getElementById("input_mail_conf");
var inputPW = document.getElementById("input_pw");
var inputPWConf = document.getElementById("input_pw_conf");
var messageElt = document.getElementById("message");
var boutonEnvoieElt = document.getElementById("bp_env");
//variable de verifcation du mot de passe
var regex1 = /[a-z]/;
var regex2 = /[A-Z]/;
var regex3 = /[0-9]/;
//variable de validation du formulaire
var longueurPasswordOk = false;
var passwordOk = false;
var emailOk =false;
//vadlidation du bouton envoie

///////////////////// E G A L I T E   C O N F I R M A T I O N   E M A I L //////////////////////////////
inputMailConf.addEventListener("blur", function () {
    if (inputMailConf.value !== inputMail.value){
        console.log(emailOk + "non ok");
        inputMailConf.style.backgroundColor = "rgb(255,176,171)";
        inputMail.style.backgroundColor = "rgb(255,176,171)";
        messageElt.textContent = "Le mail et sa confirmation ne sont pas identique."
        messageElt.style.color = "red";
        messageElt.style.height = "30px";
        emailOk = false;
        //verification si le mot de passe et sa confirmation sont identique si l'utilisateur
        //modifie le mot de passe apres la modification du mot de passe
        inputMail.addEventListener("blur", function () {
            if (inputMailConf.value !== inputMail.value){
                inputMail.style.backgroundColor = "rgb(255,176,171)";
                inputMailConf.style.backgroundColor = "rgb(255,176,171)";
                messageElt.textContent = "Le mail et sa confirmation ne sont pas identique."
                messageElt.style.color = "red";
                messageElt.style.height = "30px";
                emailOk = false;
            }else{
                messageElt.textContent = "";
                inputMail.style.backgroundColor = "white";
                inputMailConf.style.backgroundColor = "white";
                messageElt.style.height = "0px";
                emailOk = true;
            }
        })
    }else{
        emailOk = true;
        messageElt.textContent = "";
        inputMail.style.backgroundColor = "white";
        inputMailConf.style.backgroundColor = "white";
        messageElt.style.height = "0px";
    }
});

///////////////////// L E N G T H   P A S S W O R D  E T  R E G E X ////////////////////////////////////
inputPW.addEventListener("input", function () {
if (inputPW.value.length < 8 || inputPW.value.length > 30 || regex1.test(inputPW.value) == false || regex2.test(inputPW.value) == false
    || regex3.test(inputPW.value) == false) {
    messageElt.textContent = "Le mot de passe doit contenir au moins 8 caratère, une majusculet et un chiffre";
    messageElt.style.color = "red";
    messageElt.style.height = "30px";
    inputPW.style.color = "red";
    longueurPasswordOk = false;
} else if (inputPW.value.length < 12){
        messageElt.textContent = "";
        messageElt.style.color = "black";
        messageElt.style.height = "0px";
        inputPW.style.color = "orange";
        longueurPasswordOk = true;
    }else {
    longueurPasswordOk = true;
    messageElt.textContent = "";
    messageElt.style.color = "black";
    messageElt.style.height = "0px";
    inputPW.style.color = "green";
    console.log("vert");
}
});

//// V E R I R I F I C A T I O N   E G A L I T E   C O N F I R M A T I O N   M O T  D E   P A S S E //////
inputPWConf.addEventListener("blur", function () {
    if (inputPWConf.value !== inputPW.value){
        inputPWConf.style.backgroundColor = "rgb(255,176,171)";
        inputPW.style.backgroundColor = "rgb(255,176,171)";
        messageElt.textContent = "Le mot de passe et sa confirmation ne sont pas identique."
        messageElt.style.color = "red";
        messageElt.style.height = "30px";
        passwordOk = false;
        //verification si le mot de passe et sa confirmation sont identique si l'utilisateur
        //modifie le mot de passe apres la modification du mot de passe
        inputPW.addEventListener("blur", function () {
            if (inputPWConf.value !== inputPW.value){
                inputPW.style.backgroundColor = "rgb(255,176,171)";
                inputPWConf.style.backgroundColor = "rgb(255,176,171)";
                messageElt.textContent = "Le mot de passe et sa confirmation ne sont pas identique."
                messageElt.style.color = "red";
                messageElt.style.height = "30px";
                passwordOk = false;
            }else{
                messageElt.textContent = "";
                inputPW.style.backgroundColor = "white";
                inputPWConf.style.backgroundColor = "white";
                messageElt.style.height = "0px";
                passwordOk = true;
            }
        })
    }else{
        passwordOk = true;
        messageElt.textContent = "";
        inputPW.style.backgroundColor = "white";
        inputPWConf.style.backgroundColor = "white";
        messageElt.style.height = "0px";
    }
});

/////// V A L I D A T I O N   D U   F O R M U L A I R E   A V A N T   E N V O I E //////////
function verifForm(f)
{
       if(longueurPasswordOk && passwordOk && emailOk )
        return true;
    else
    {
        alert("Veuillez remplir correctement tous les champs");
        return false;
    }
};
