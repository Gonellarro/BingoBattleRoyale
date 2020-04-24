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

        <title>BingoWeb NoEstamBollats</title>

    </head>
    <body OnLoad="document.formulari.numero.focus();">
        <!-- Cap�alera -->
        <jsp:include page="WEB-INF/comuns/capcalera.jsp"/>     
        <form name="formulari" action="${pageContext.request.contextPath}/CartonsControler" method="post">
            <div class ="container">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">
                                <div class="row">
                                    <div class="col-12">
                                        <table style="width:100%"><tr>
                                                <td 
                                                    <c:if test="${estrella}">
                                                        class="bg-danger text-white border border-secondary"
                                                    </c:if>  
                                                    >
                                                    Darreres bolles&nbsp</td>
                                                    <c:forEach var ="bolla" items="${partida.tresBolles}">
                                                    <td class="table-primary border border-secondary"><c:out value="${bolla.valor + 1}"/></td>
                                                </c:forEach>
                                            </tr></table>
                                    </div>
                                </div>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-12">
                                    <c:forEach var ="carto" items="${cartons}">
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
                                        <c:choose>
                                            <c:when test="${partida.bingo}">
                                                <div class="alert alert-success" role="alert">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="alert alert-warning" role="alert">
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:out value = "${partida.missatgesEvents}"/>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <label><strong>N�mero</strong></label>
                                        <input type="number" class="form-control" id="numero" name ="numero"  placeholder="Introdueix el n�mero que ha sortit">
                                    </div>
                                    <div class="col-6">
                                        <label><strong>Participants</strong></label>
                                        <h1 class="display-5">
                                            <c:out value = "${jugadors}"/>
                                        </h1>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">          
                                <div class="row">
                                    <div class="col-12">                    
                                        <button type="submit" class="btn btn-primary btn-block" >Enviar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="text-left">
                        <label><strong>Missatges</strong></label>
                        <textarea class="form-control" readonly="true" rows="2" id="log" placeholder="En construcci�..."><c:out value="${partida.missatgesLog}"/></textarea>
                    </div>
                </div>
        </form>   

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    </body>
</html>