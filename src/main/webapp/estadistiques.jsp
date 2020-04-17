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
        <title>BingoWeb NoEstamBollats</title>
    </head>
    
    <script>
        function goBack() {
            window.history.back();
        }
    </script>    

    <body>
        <!-- Capçalera -->
        <jsp:include page="WEB-INF/comuns/capcalera.jsp"/>   
        <div class ="container">
            <div id="login-overlay" class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <!-- <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button> -->
                        <h4 class="modal-title" id="myModalLabel">Estadistiques de la partida XXX</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-12">
                                <table class="table table-striped table-sm">
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Avatar</th>
                                        <th scope="col">Nom</th>
                                        <th scope="col">Linies</th>
                                        <th scope="col">Bingos</th>
                                    </tr>
                                    <c:forEach var ="usuari" items="${usuaris}" varStatus="loopCounter">   
                                        <tr>
                                            <td><c:out value="${loopCounter.count}"/></td>
                                            <td>
                                                <img src="${pageContext.request.contextPath}/resources/img/starwars/<c:out value="${usuari.avatar}"/>.png" width="30" height="30" class="d-inline-block align-top" alt="">
                                            </td>
                                            <td>
                                                <c:out value="${usuari.nom}"/>
                                            </td>
                                            <td>
                                                <c:out value="${usuari.linies}"/>
                                            </td>
                                            <td>
                                                <c:out value="${usuari.bingos}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">          
                        <div class="row">
                            <div class="col-12">    
                                <button class="btn btn-primary btn-block" onclick="goBack()">Torna enrera</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
