<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}"> 
        <img src="${pageContext.request.contextPath}/resources/img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
        NoEstamBollats!</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav">
            <c:if test="${not empty nom}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/menu.jsp">
                        <img src="${pageContext.request.contextPath}/resources/img/starwars/<c:out value="${avatar}"/>.png" width="30" height="30" class="d-inline-block align-top" alt="">
                        Canviar d'usuari</a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/CartonsControler?accio=reiniciar">
                    Reiniciar</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/CartonsControler?accio=estadistiques">
                    Estadístiques</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/CartonsControler?accio=sortir">Sortir</a>
            </li>    
        </ul>
    </div>  
</nav>
<br>