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
    <body background="${pageContext.request.contextPath}/resources/img/fons.png">
        <!-- Cap�alera -->
        <jsp:include page="WEB-INF/comuns/capcalera_bombo.jsp"/>    
        <form action="${pageContext.request.contextPath}/BingoControler" method="post">
        <div class ="container">
            <div id="login-overlay" class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <!-- <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">�</span><span class="sr-only">Close</span></button> -->
                        <h4 class="modal-title" id="myModalLabel">Bombo manual del bingo</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-12">
                                <table class="table table-bordered table-sm">
                                    <tr>
                                        <c:forEach var ="bolla" items="${parrilla.bombo}" varStatus="numero">
                                            <c:if test="${bolla.sortit}">
                                                <td class="table-primary rounded-circle">  
                                                    <c:out value="${bolla.valor + 1}"/></td>
                                                </c:if>
                                                <c:if test="${!bolla.sortit}">
                                                <td class="table-secondary">  
                                                    <img src="${pageContext.request.contextPath}/resources/img/logo.png" width="15" height="15" alt="">
                                                </c:if>

                                            <c:if test="${(bolla.valor + 1) mod 10 == 0}"> </tr></c:if>
                                        </c:forEach>
                                </table>
                            </div>  
                        </div>  
                        <div class="row">
                            <div class="col-12">
                                <label><strong>N�mero</strong></label>
                                <input type="number" class="form-control" id="numero" name ="numero" placeholder="Introdueix n�mero">
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
        </div>
        </form>

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    </body>
</html>