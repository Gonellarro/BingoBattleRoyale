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

        <title>Menu bingo</title>
    </head>

    <body>
        <!-- Capçalera -->
        <jsp:include page="WEB-INF/comuns/capcalera.jsp"/>     
        
        <h4 class="text-center">1.Escollim el nostre jugador</h4>
        <br>
        <form action="${pageContext.request.contextPath}/MenuControler" method="post">
            <div class="section m-2 p-2 border text-center">
                <div class="row">
                    <div class="col-2">
                        <label>Nom</label>
                    </div>		
                    <div class="col-10">
                        <input type="text" class="form-control" id="nom" name ="nom" placeholder="Introdueix nom">
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
                    </div>
                </div>
                <br>
                <button type="submit" class="btn btn-primary" >Enviar</button>
            </div>	

        </form>

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>