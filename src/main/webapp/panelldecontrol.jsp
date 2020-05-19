<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <!-- Check Image CSS -->
        <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
        <title>BingoWeb Battle Royale</title>
    </head>



    <body background="${pageContext.request.contextPath}/resources/img/fons.png">
        <!-- Capçalera -->
        <div class ="container">
            <div id="login-overlay" class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel">Bingo <c:out value="${bingo.nom}"/></h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-12">
                                <c:forEach var ="sala" items="${bingo.sales}">
                                    <h4><strong>Sala: </strong> <c:out value="${sala.nom}"/> - 
                                        <strong>Id: </strong><c:out value="${sala.id}"/></h4>
                                    <h5>Partides</h5>

                                    <c:forEach var ="partida" items="${sala.partides}">
                                        <table class="table table-striped table-sm table-primary">
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col">Bingo</th>
                                                <th scope="col">Linea</th>
                                                <th scope="col">Bolles</th>
                                                <th scope="col">EasyOn</th>
                                                <th scope="col">BattleRoyale</th>
                                            </tr>                                            
                                            <tr>
                                                <td><c:out value="${partida.nPartida + 1}"/></td>
                                                <td><c:out value="${partida.bingo}"/></td>
                                                <td><c:out value="${partida.linea}"/></td>
                                                <td><c:out value="${partida.parrilla.comptador}"/></td>
                                                <td><c:out value="${partida.easyOn}"/></td>
                                                <td><c:out value="${partida.battleRoyale}"/></td>
                                            </tr>
                                        </table>
                                        <table class="table table-striped table-sm table-success">
                                            <tr>
                                                <th scope="col">Nom</th>
                                                <th scope="col">Cartons</th>
                                                <th scope="col">Linea</th>
                                                <th scope="col">Bingo</th>
                                                <th scope="col">PowerUp</th>
                                                <th scope="col">AtacPlatan</th>
                                            </tr>
                                            <c:forEach var ="usuari" items="${partida.usuaris}">
                                                <tr>
                                                    <td>
                                                        <c:out value="${usuari.nom}"/> 
                                                    </td>
                                                    <td>
                                                        <c:forEach var ="carto" items="${usuari.cartons}">
                                                            <c:out value="${carto.numeros}"/> 
                                                        </c:forEach>
                                                    </td>
                                                    <td><c:out value="${usuari.linea}"/> </td>
                                                    <td><c:out value="${usuari.bingo}"/> </td>
                                                    <td><c:out value="${usuari.pwup.nom}"/> </td>
                                                    <td><c:out value="${usuari.atacPlatan}"/> </td>
                                                </tr>
                                            </c:forEach>   
                                        </table>
                                    </c:forEach>
                                    </table>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">        
                        <div class ="container">
                            <div class="row">
                                <div class="col-12">    
                                    <button class="btn btn-primary" onclick="window.location.reload();">Refrescar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>


</html>
