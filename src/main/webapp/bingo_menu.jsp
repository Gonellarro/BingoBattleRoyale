<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
        <!-- Check Image CSS -->
        <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 

        <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">

        <title>BingoWeb Battle Royale</title>
    </head>
    <style>
    </style>

    <body background="${pageContext.request.contextPath}/resources/img/fons.png">
        <!-- Capçalera -->
        <jsp:include page="WEB-INF/comuns/capcalera_bombo.jsp"/>     

        <form action="${pageContext.request.contextPath}/BingoControler" method="post">
            <div class ="container">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header text-dark bg-light">
                            <h4 class="modal-title" id="myModalLabel">Creau la sala</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-12 input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Títol</span>
                                    </div>
                                    <input type="text" class="form-control" id="titol" name ="titol" placeholder="Títol de la sala" required>
                                </div>
                            </div>	 
                            <br>
                            <div class="row">
                                <div class="col-12 input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Cartons</span>
                                    </div>
                                    <input type="number" class="form-control" id="cartons" name ="cartons" placeholder="Cartons per jugador">
                                </div>
                            </div>  
                            <br>
                            <div class="row">
                                <div class="col-12 input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Freq PowerUps</span>
                                    </div>
                                    <input type="number" class="form-control" id="powerups" name ="powerups" value="10">
                                </div>
                            </div>  
                            <br>
                            <div class="row">
                                <div class="col-6  input-group mb-3">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">
                                            <input type="checkbox" name="easyon">
                                        </div>
                                    </div>
                                    <p class="form-control" data-toggle="tooltip" data-placement="top" title="Automatitza els cartons">Easy On</p>
                                </div>
                                <div class="col-6  input-group mb-3">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">
                                            <input type="checkbox" name="battle" checked>
                                        </div>
                                    </div>
                                    <p class="form-control"  data-toggle="tooltip" data-placement="top" title="Jugar amb regles de Battle Royale">Battle Rolyale</p>
                                </div> 
                                <br>
                            </div>
                        </div>
                        <div class="modal-footer text-dark bg-light">
                            <button type="submit" class="btn btn-primary">Enviar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <script>
            var slider = document.getElementById("myRange");
            var output = document.getElementById("demo");
            output.innerHTML = slider.value;

            slider.oninput = function () {
                output.innerHTML = this.value;
            }
        </script>

        <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/10.6.2/bootstrap-slider.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" ></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    </body>
</html>