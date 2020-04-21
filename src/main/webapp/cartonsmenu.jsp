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

        <form action="${pageContext.request.contextPath}/CartonsControler" method="post">
            <div class ="container">
                <div id="login-overlay" class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <!-- <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button> -->
                            <h4 class="modal-title" id="myModalLabel">Escolliu el vostre jugador</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-2">
                                    <label>Nom</label>
                                </div>		
                                <div class="col-10">
                                    <input type="text" class="form-control" id="nom" name ="nom" placeholder="Introduïu nom" required>
                                </div>
                            </div>	 
                            <br>
                            <div class="row">
                                <div class="col-2">
                                    <label>Avatar</label>
                                </div>
                                <div class="col-10 cc-selector">
                                    <input id="face1" type="radio" name="avatar" value="face1" />
                                    <label class="drinkcard-cc face1" for="face1"></label>
                                    <input id="face2" type="radio" name="avatar" value="face2" />
                                    <label class="drinkcard-cc face2"for="face2"></label>                 
                                    <input id="face3" type="radio" name="avatar" value="face3" />
                                    <label class="drinkcard-cc face3"for="face3"></label>                 
                                    <input id="face4" type="radio" name="avatar" value="face4" />
                                    <label class="drinkcard-cc face4" for="face4"></label>
                                    <input id="face5" type="radio" name="avatar" value="face5" />
                                    <label class="drinkcard-cc face5"for="face5"></label>                 
                                    <input id="face6" type="radio" name="avatar" value="face6" />
                                    <label class="drinkcard-cc face6"for="face6"></label>                 
                                    <input id="face7" type="radio" name="avatar" value="face7" />
                                    <label class="drinkcard-cc face7" for="face7"></label>
                                    <input id="face8" type="radio" name="avatar" value="face8" />
                                    <label class="drinkcard-cc face8"for="face8"></label>                 
                                    <input id="face9" type="radio" name="avatar" value="face9" />
                                    <label class="drinkcard-cc face9"for="face9"></label>                 
                                    <input id="face10" type="radio" name="avatar" value="face10" />
                                    <label class="drinkcard-cc face10" for="face10"></label>
                                    <input id="face11" type="radio" name="avatar" value="face11" />
                                    <label class="drinkcard-cc face11"for="face11"></label>                 
                                    <input id="face12" type="radio" name="avatar" value="face12" />
                                    <label class="drinkcard-cc face12"for="face12"></label>                 
                                    <input id="face13" type="radio" name="avatar" value="face13" />
                                    <label class="drinkcard-cc face13"for="face13"></label>                 
                                    <input id="face14" type="radio" name="avatar" value="face14" />
                                    <label class="drinkcard-cc face14" for="face14"></label>
                                    <input id="face15" type="radio" name="avatar" value="face15" />
                                    <label class="drinkcard-cc face15"for="face15"></label>                 
                                    <input id="face16" type="radio" name="avatar" value="face16" />
                                    <label class="drinkcard-cc face16"for="face16"></label>                 
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-2">
                                    <label>ID</label>
                                </div>		
                                <div class="col-10">
                                    <input type="text" class="form-control" id="idPartida" name ="idPartida" placeholder="Introduïu Id partida" required>
                                </div>
                            </div>                            
                            <!--<div class="row">
                                <div class="col-2">
                                    <label>Codi</label>
                                </div>
                                <div class="col-10">
                                    <input type="text" class="form-control" id="invitacio" name ="invitacio" placeholder="Introduïu codi d'invitació">
                                </div>
                                <br>
                            </div>-->	
                            <br>
                        </div>
                        <div class="modal-footer">
                            <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Sortir</a>
                            <button type="submit" class="btn btn-primary">Enviar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>