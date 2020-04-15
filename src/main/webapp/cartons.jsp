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
    <body>
        <!-- Capçalera -->
        <jsp:include page="WEB-INF/comuns/capcalera.jsp"/>     
        <form action="${pageContext.request.contextPath}/MenuControler" method="post">
            <div class ="container">
                <div id="login-overlay" class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <!-- <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button> -->
                            <h4 class="modal-title" id="myModalLabel">Cartons del bingo</h4>
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
                                                            <c:if test="${valor < 1}">
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
                                    <c:if test="${linia}">
                                        <div class="alert alert-warning" role="alert">
                                            Has cantat linia!
                                        </div>
                                    </c:if>
                                    <c:if test="${bingo}">
                                        <div class="alert alert-success" role="alert">
                                            Bingo!
                                        </div>
                                    </c:if>     
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <label><strong>Número</strong></label>
                                    <input type="number" class="form-control" id="numero" name ="numero" placeholder="Introdueix el número que ha sortit">
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
                    <br>
                    <label><strong>Missatges</strong></label>
                    <div class="text-left">
                        <textarea class="form-control" readonly="true" rows="3" id="log" placeholder="En construcció..."><c:out value="${missatges}"/></textarea>
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