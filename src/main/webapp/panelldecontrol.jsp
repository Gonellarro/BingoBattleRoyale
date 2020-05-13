<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <body>
        <h1>Panell de control</h1>
        <h2><c:out value="${bingo.nom}"/></h2>
        <c:forEach var ="sala" items="${bingo.sales}">
            <c:out value="${sala.nom}"/> - <c:out value="${sala.id}"/><br>
            <h2>Usuaris</h2>
            <c:forEach var ="usuari" items="${sala.usuaris}">
                <c:out value="${usuari.nom}"/> 
                <br>
            </c:forEach>
            <h2>Partides</h2>
            <c:forEach var ="partida" items="${sala.partides}">
                #<c:out value="${partida.nPartida + 1}"/> - Bingo: <c:out value="${partida.bingo}"/> - Linea: <c:out value="${partida.linea}"/> - Bolles:<c:out value="${partida.parrilla.comptador}"/> <br>
                <c:forEach var ="usuari" items="${partida.usuaris}">
                    <c:out value="${usuari.nom}"/> 
                    <c:forEach var ="carto" items="${usuari.cartons}">
                        <c:out value="${carto.numeros}"/> 
                    </c:forEach>
                    <br>
                </c:forEach>                
            </c:forEach>                
        </c:forEach>

    </body>
</html>
