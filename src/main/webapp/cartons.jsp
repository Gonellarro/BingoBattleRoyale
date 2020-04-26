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
    <body OnLoad="document.formulari.numero.focus();">
        <!-- Capçalera -->
        <jsp:include page="WEB-INF/comuns/capcalera.jsp"/>     
        <form name="formulari" action="${pageContext.request.contextPath}/CartonsControler" method="post">
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
                                        <div class="col-5">Darreres:</div>
                                        <div class="col-5">
                                            <table>
                                                <th class="col-3"></th>
                                                <th class="col-3"></th>
                                                <th class="col-3"></th>
                                                <tr>
                                                    <c:forEach var ="bolla" items="${partida.tresBolles}">
                                                        <td class="table-primary border border-secondary text-right"><c:out value="${bolla.valor + 1}"/></td>
                                                    </c:forEach>
                                                </tr></table>
                                        </div>
                                        <div class="col-2">
                                            <img src="${pageContext.request.contextPath}/resources/img/perfils/<c:out value = "${usuari.perfil}"/>.png" width="30" height="30" alt="">
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
                                                        <c:if test="${valor > 0}">  
                                                            <c:if test="${valor < 100}">
                                                                <td class="table-primary">  
                                                                </c:if>
                                                                <c:if test="${(valor > 100) && (valor < 200)}">
                                                                <td class="table-danger">  
                                                                    <c:set var="valor" value="${valor - 100}" />
                                                                </c:if>
                                                                <c:if test="${(valor > 200) && (valor < 300)}">
                                                                <td class="table-warning">  
                                                                    <c:set var="valor" value="${valor - 200}" />
                                                                </c:if>
                                                                <c:if test="${valor > 300}">
                                                                <td class="table-success">  
                                                                    <c:set var="valor" value="${valor - 300}" />
                                                                </c:if>
                                                                <c:out value="${valor}"/>
                                                            </c:if>
                                                            <c:if test="${valor < 0}">
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
                                        <div
                                            <c:choose>
                                                <c:when test="${partida.bingo}">
                                                    class="alert alert-success" role="alert"
                                                </c:when>
                                                <c:when test="${partida.atac}">
                                                    class="alert alert-danger" role="alert"
                                                </c:when>
                                                <c:when test="${partida.linea}">
                                                    class="alert alert-warning" role="alert"
                                                </c:when>
                                            </c:choose>
                                            >
                                            <c:out value = "${partida.missatgesEvents}"/>
                                        </div>
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
                                        <c:out value = "${90-partida.numero}"/>
                                    </h1>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer text-dark bg-light">   
                            <div class="container">
                                <div class="row">
                                    <div class="col-6">   
                                        <c:if test="${usuari.perfil=='atacant'}">
                                            <c:if test="${estrella}">
                                                <a href="${pageContext.request.contextPath}/CartonsControler?accio=bomba" class="btn btn-danger" role="button">Bomba!</a>
                                            </c:if>  
                                        </c:if> 
                                    </div>
                                    <div class="col-6">  
                                        <button type="submit" class="btn btn-primary" >Enviar</button>
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
                                        <textarea class="form-control" readonly="true" rows="2" id="log" placeholder="En construcció..."><c:out value="${partida.missatgesLog}"/></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>   

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    </body>
</html>