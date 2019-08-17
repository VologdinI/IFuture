<%--
  Created by IntelliJ IDEA.
  User: Kano
  Date: 27.07.2019
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page isELIgnored="false" %>--%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "sh" uri ="/WEB-INF/tld/ShowTreeTagDescriptor.tld"%>

<html>
<head>
    <title>Fuck JSP and FrontEnd</title>
    <%--<link rel="stylesheet" type="text/css" href="_styles.css" media="screen">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/_styles.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="<spring:url value='resources/css/_styles.css' />" media="screen">--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/_styles.css"/>--%>
    <%--<link href="${pageContext.request.contextPath}/_style.css">--%>
    <%-- <spring:url value="/css/_styles.css" var="_stylesCSS" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <link href="${_stylesCSS}" rel="stylesheet" />--%>
    <%--<spring:url value="/css/_styles.css" var="bootstrapCss" />
        <link href="${bootstrapCss}" rel="stylesheet" />
    --%>
    <%--<link href="<c:url value="/resources/theme1/css/_styles.css" />" rel="stylesheet">--%>

    <%--<spring:url value="/resources/theme1/css/_styles.css" var="styles" />
    <link href="${styles}" rel="stylesheet" />--%>
    <%--<link href="<c:url value="/resources/css/_styles.css" />" rel="stylesheet">--%>
    <%--no way--%>
    <%-- <link href="${pageContext.request.contextPath}/resources/theme1/css/_styles.css" rel="stylesheet" >--%>
    <%--<style><%@include file="../resources/theme1/css/_styles.css"%></style>&lt;%&ndash;use this variant in jsp, but here no images&ndash;%&gt;--%>

    <%--../resources/theme1/css/--%>

    <link href="../css/style.css" type="text/css" rel="stylesheet">    <%--Why this not works in resources package--%>



</head>
<body>
<%--JSP from--%>
        <%--<form:form action="processFrom" modelAttribute="input">
            Extension = <form:input path="extension"/>
            <br/>
            Text for finding = <form:input path="goalText"/>
            <br/>
            <input type="submit" text="Submit to Armageddon"/>
        </form:form>--%>
<%--HTML variant of JSP from--%>
        <form id="input" action="processFrom" method="post">
            <label for="extension">Extension:</label><input id="extension" name="extension" type="text" value=".log"/>
            <br/>
            <label for="goalText">Text for finding:</label><input id="goalText" name="goalText" type="text" value=""/>
            <br/>
            <input type="submit" text="Submit to Armageddon"/>
        </form>

        <c:if test="${treePackage!=null}">
            <sh:ShowTreeTag>qew</sh:ShowTreeTag>
        </c:if>

<br/>
<p>Big bad static boy</p><br/>
<ul class="table-tree" cellspacing="0">

    <li>
        <label for="mainfolder">logExample</label>
        <input type="checkbox" id="mainfolder" />
        <ul class="table-wrapper">
            <li>
                <label for="subfolder1">BigBoyInside</label>
                <input type="checkbox" id="subfolder1" />
                <ul class="table-wrapper">
                    <li id="file0" class="file"><a href="">BigBoy.log</a>
                    </li><li id="file1" class="file"><a href="">SmallBoy.log</a>
                </li></ul>
            </li>
            <li>
                <label for="subfolder2">HiddenLogInside</label>
                <input type="checkbox" id="subfolder2" />
                <ul class="table-wrapper">
                    <li>
                        <label for="subsubfolder1">HiddenStuff</label>
                        <input type="checkbox" id="subsubfolder1" />
                        <ul class="table-wrapper">
                            <li id="file2" class="file"><a href="">hiddenguy.log</a>
                            </li></ul>
                    </li>
                    <li class="file"><a href="">afterFolders.log</a>
                    </li></ul>
            </li>
            <li>
                <label for="subfolder3">LogExampleInner</label>
                <input type="checkbox" id="subfolder3" />
                <ul class="table-wrapper">
                    <li class="file"><a href="">graphics.log</a>
                    </li></ul>
            </li>
            <li class="file"><a href="">game.log</a>
            </li><li class="file"><a href="">setup.log</a>
        </li></ul>
    </li>
</ul>

        <p><span id="errorMessage">Test</span></p><%--CSS not works here but works with folders--%></br>
        <p id="getTime"></p><br/>
        <p id="file3"></p>
<script src="../js/jqueryMin3.4.1.js"></script>
<script src="../js/script.js"></script>
<script>
    /*function changeStuff() {
        $.ajax({
            url: 'getSomeTime',
            success: function (data) {
                $("#getTime").html(data);
            }
        });
    }
    setInterval(changeStuff, 5000);*/
</script>
</body>
</html>
