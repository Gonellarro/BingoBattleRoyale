<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
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
    <body OnLoad="inici();" background="${pageContext.request.contextPath}/resources/img/fons.png">
        <!-- Capçalera -->
        <jsp:include page="WEB-INF/comuns/capcalera.jsp"/>  
        <div id='platan' style="display: none;" class="text-white bg-dark" >${usuari.atacPlatan}</div>
        <form name="formulari" action="${pageContext.request.contextPath}/PartidesControler" method="post">
            <div class ="container">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header
                             <c:if test="${estrella}">
                                 bg-danger text-white
                             </c:if>  
                             <c:if test="${!estrella}">
                                 text-dark bg-light
                             </c:if>  
                             ">
                            <div class="container" >
                                <h4 class="modal-title">
                                    <div class="row">
                                        <div class="col-3">
                                            <div class="bg-light">
                                                <img src="${pageContext.request.contextPath}/resources/img/mario/<c:out value="${usuari.avatar}"/>.png" width="42" alt="">
                                            </div>

                                        </div>
                                        <div class="col-6 text-center text-light bg-secondary">
                                            ·
                                            <c:forEach var ="bolla" items="${partida.tresBolles}">
                                                <c:out value="${bolla.valor + 1}"/>·
                                            </c:forEach>
                                        </div>
                                        <div class="col-3">
                                            <c:if test="${sala.battleRoyale}"> 
                                                <c:if test="${usuari.pwup.nom ne 'FLASH'}"> 
                                                    <div class="bg-light text-right">
                                                        <img src="${pageContext.request.contextPath}/resources/img/perfils/<c:out value = "${usuari.pwup.nom}"/>.png" width="42">
                                                    </div>
                                                </c:if>
                                            </c:if>
                                        </div>
                                    </div>
                                </h4>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-12">
                                    <c:forEach var ="carto" items="${usuari.cartons}">
                                        <div class="text-right">
                                            <strong>Números restants: </strong><c:out value = "${15-carto.numeros}"/>
                                        </div>
                                        <table class="table table-bordered table-sm">
                                            <tr>
                                                <c:forEach var="linia" items="${carto.linies}">
                                                    <c:forEach var="valor" items="${linia}">
                                                        <c:if test="${valor[1] ge 0}">  
                                                            <c:if test="${valor[1] eq 0}">
                                                                <td class="table-primary">  
                                                                </c:if>
                                                                <c:if test="${valor[1] eq 1}">
                                                                <td class="table-danger">
                                                                </c:if>
                                                                <c:if test="${valor[1] eq 2}">
                                                                <td class="table-warning">  
                                                                </c:if>
                                                                <c:if test="${valor[1] eq 3}">
                                                                <td class="table-success"> 
                                                                </c:if>
                                                                <c:if test="${valor[1] eq 4}">
                                                                <td class="table-dark text-warning"> 
                                                                </c:if>
                                                                <c:out value="${valor[0]}"/>
                                                            </c:if>
                                                            <c:if test="${valor[1] lt 0}">
                                                            <td class="table-secondary">
                                                                <img src="${pageContext.request.contextPath}/resources/img/logo.png" width="20" height="20" alt="">
                                                            </c:if>
                                                        </td>
                                                    </c:forEach>
                                                </tr><tr>
                                                </c:forEach>
                                            </tr>
                                        </table>
                                    </c:forEach>
                                </div> 
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <c:if test="${usuari.pintarEvent}">
                                        <c:forEach var="missatge" items="${partida.missatgesEvent}" varStatus="comptador">
                                            <c:if test="${(comptador.count gt missatgesEventInici) && (comptador.count le missatgesEventFinal)}">  
                                                <div
                                                    <c:choose>
                                                        <c:when test="${missatge[1] eq 1}">
                                                            class="alert alert-warning" role="alert"
                                                        </c:when>
                                                        <c:when test="${missatge[1]  eq 2}">
                                                            class="alert alert-success" role="alert"
                                                        </c:when>
                                                        <c:when test="${missatge[1] eq 3}">
                                                            class="alert alert-danger" role="alert"
                                                        </c:when>
                                                        <c:when test="${missatge[1] eq 4}">
                                                            class="alert alert-success" role="alert"
                                                        </c:when>
                                                        <c:when test="${missatge[1] eq 5}">
                                                            class="alert alert-primary" role="alert"
                                                        </c:when>
                                                        <c:when test="${missatge[1] eq 6}">
                                                            class="alert alert-info" role="alert"
                                                        </c:when>
                                                        <c:when test="${missatge[1] eq 7}">
                                                            class="alert alert-warning" role="alert"
                                                        </c:when>
                                                    </c:choose>
                                                    >
                                                    <c:out value = "${missatge[0]}"/>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-4">
                                    <label><strong>Número</strong></label>
                                    <input type="number" class="form-control" id="numero" name ="numero"  placeholder="Bolla">
                                </div>
                                <div class="col-4 text-right">
                                    <label><strong>Participants</strong></label>
                                    <h1 class="display-5 text-right">
                                        <c:out value = "${jugadors}"/>
                                    </h1>
                                </div>
                                <div class="col-4">
                                    <label><strong>Falten</strong></label>
                                    <h1 class="display-5">
                                        <span
                                            <c:if test="${avisPwrUp eq 'danger'}">
                                                class="border border-light bg-danger" 
                                            </c:if>
                                            <c:if test="${avisPwrUp eq 'warning'}">
                                                class="border border-light bg-warning" 
                                            </c:if>
                                            <c:if test="${avisPwrUp eq 'success'}">
                                                class="border border-light bg-success" 
                                            </c:if>
                                            ><c:out value = "${90-partida.numeroBolles}"/>
                                        </span>
                                    </h1>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer text-dark bg-light">   
                            <div class="container">
                                <div class="row">
                                    <div class="col-6">   
                                        <c:if test="${sala.battleRoyale}"> 
                                            <c:if test="${usuari.pwup.nom eq 'bomba'}">
                                                <a href="${pageContext.request.contextPath}/PartidesControler?accio=bomba&idSala=<c:out value = "${sala.id}"/>" class="btn btn-danger" role="button">Bomba!</a>
                                            </c:if> 
                                            <c:if test="${usuari.pwup.nom eq 'platan'}">
                                                <a href="${pageContext.request.contextPath}/PartidesControler?accio=platan&idSala=<c:out value = "${sala.id}"/>" class="btn btn-danger" role="button">Platan!</a>
                                            </c:if> 
                                            <c:if test="${usuari.pwup.nom eq 'canvi'}">
                                                <a href="${pageContext.request.contextPath}/PartidesControler?accio=canvi&idSala=<c:out value = "${sala.id}"/>" class="btn btn-danger" role="button">Canvi!</a>
                                            </c:if>                                             

                                        </div>
                                    </c:if> 
                                    <div class="col-6 text-right"> 
                                        <button type="submit" id="enviar" class="btn btn-primary" >Enviar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-12">  
                                    <div class="text-left">
                                        <label><strong>Missatges</strong></label>
                                        <textarea class="form-control" readonly="true" rows="5" id="log" placeholder="Missatges de la partida"><c:out value="${partida.missatgesLog}"/></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <input type="hidden" id="idSala" name="idSala" value="<c:out value = "${sala.id}"/>">
            </div>
        </form>   

        <!--Comptador pel botó d'enviar en cas d'enviar un platan-->
        <script>
            var myTimer;
            function inici()
            {
                document.formulari.numero.focus();
                var platan = document.getElementById("platan").innerHTML;
                if (platan == "true") {
                    clock();
                    document.getElementById('enviar').innerHTML = 'Espera 5 segons';
                }
            }
            function clock() {
                myTimer = setInterval(myClock, 1000);
                document.getElementById("enviar").disabled = true;
                var c = 10;

                function myClock() {
                    --c;
                    document.getElementById("enviar").innerHTML = "Espera " + c + " segons";

                    if (c == 0) {
                        clearInterval(myTimer);
                        document.getElementById('enviar').textContent = "Enviar";
                        document.getElementById("enviar").disabled = false;

                    }
                }
            }
        </script>

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    </body>
</html>