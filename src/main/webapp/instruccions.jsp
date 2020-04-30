<!doctype html>
<html lang="es">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="css/styles.css" rel="stylesheet">
        <title>BingoWeb BattleRoyale</title>
    </head>
    <body background="${pageContext.request.contextPath}/resources/img/fons.png">
        <div class="container text-center">
            <h3 class="mb-1 mt-5 text-light">Instruccions del Bingo Battle Royale!</h3>
            <br>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-12 text-left">
                                Aquest bingo es juga de forma diferent a un bingo normal. Les regles són les habituals,
                                és a dir, hi ha un bombo d'on es treuen les bolles i cada participant haurà d'anar marcant
                                als seus cartons les bolles que van sortint.<br><br>
                                El programa deliberadament NO controla si la bolla marcada ha sortit o no i haurà de ser el 
                                propi jugador qui s'encarregui d'estar atent per no marcar o oblidar una bolla.<br><br>
                                Fins aquí és com un bingo normal. Però el <strong>Bingo Battle Royale</strong> és diferent.
                                <strong>Bingo Battle Royale</strong> té el que s'anomenen "PoweUp"s 
                                que el fan més entretingut i divertit.<br><br> Els PowerUps existents són:<br><br>
                                <ul>
                                    <li>
                                        <img src="${pageContext.request.contextPath}/resources/img/perfils/bomba.png" width="42">
                                        Permet tirar una bomba al jugador que li quedi només una bolla per tapar 
                                        al seu cartó
                                    </li>
                                    <li>
                                        <img src="${pageContext.request.contextPath}/resources/img/perfils/escut.png" width="42">
                                        Si el jugador rep una bomba quan només li queda una bolla per cobrir, no es 
                                        veu afectat per la bomba
                                    </li>
                                    <li>
                                         <img src="${pageContext.request.contextPath}/resources/img/perfils/escutRebot.png" width="42">
                                        Si el jugador rep una bomba quan només li queda una bolla per cobrir,
                                        no es veu afectat per la bomba i li rebota al jugador que li ha tirat
                                    </li>
                                    <li>
                                        <img src="${pageContext.request.contextPath}/resources/img/perfils/platan.png" width="42">
                                        Permet tirar una pell de plàtan a un jugador al atzar (mai a ell mateix) que 
                                        li penalitza 10 segons per poder tornar a marcar les bolles al seu cartó
                                    </li>
                                    <li>
                                        <img src="${pageContext.request.contextPath}/resources/img/perfils/canvi.png" width="42">
                                        Permet canviar el cartons amb el jugador que millor vagi de la partida
                                    </li>
                                </ul>
                                <br><br>
                                Molta sort i divertiu-vos!
                                <br><br>
                                <a class="btn btn-primary btn-block" href="${pageContext.request.contextPath}/menu.jsp">Anar al joc</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>