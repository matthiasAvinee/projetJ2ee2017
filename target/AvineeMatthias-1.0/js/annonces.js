function compterAnnonces() {
    var requeteComptage = new XMLHttpRequest();
    requeteComptage.open("GET","compterannonce",true);
    requeteComptage.responseType="text";

    requeteComptage.onload=function () {
        console.log("Nombre d'annonce à acheter : "+ this.response );
        var spanNbAnnonces= document.querySelector(("span#totalAnnonces"));
        spanNbAnnonces.textContent=this.response;
    }

    requeteComptage.send();
};

window.onload = function () {
    compterAnnonces();
}