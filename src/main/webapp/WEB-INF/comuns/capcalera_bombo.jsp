<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}"> 
        <img src="${pageContext.request.contextPath}/resources/img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
        <c:out value = "${sala.nom}"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/BingoControler?accio=reiniciar&idSala=<c:out value = "${sala.id}"/>">
                    Reiniciar</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/BingoControler?accio=sortir">Sortir</a>
            </li>    
        </ul>
    </div>  
</nav>
<br>