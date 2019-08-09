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

        <form:form action="processFrom" modelAttribute="input">
            Extension = <form:input path="extension"/>
            <br/>
            Text for finding = <form:input path="goalText"/>
            <br/>
            <input type="submit" text="Submit to Armageddon"/>
        </form:form>

        <%--<ol class="tree">
        <c:forEach items="${treePackage.treePackageList}" var="item">
            <li>
                <label for=${item.leverOfPackage}>${item.fileOfPackage.name}</label> <input type="checkbox" checked disabled id=${item.leverOfPackage} />
            </li>
        </c:forEach>
        </ol>--%>

        <c:if test="${treePackage!=null}">
            <sh:ShowTreeTag>qew</sh:ShowTreeTag>
        </c:if>

        <ul class="table-tree" cellspacing="0">
            <li>
                <label for="folder0">logExample</label>
                <input type="checkbox" id="folder0" />
                <ul class="table-wrapper">
                    <li>
                        <label for="folder1">BigBoyInside</label>
                        <input type="checkbox" id="folder1" />
                        <ul class="table-wrapper">
                            <li class="file"><a href="">BigBoy</a>

                            </li>

                        </ul>
                    </li>
                    <li>
                        <label for="folder2">HiddenLogInside</label>
                        <input type="checkbox" id="folder2" />
                        <ul class="table-wrapper">

                            <li>
                                <label for="subfolder1">HaveNoStuff</label>
                                <input type="checkbox" id="subfolder1" />
                                <ul class="table-wrapper">
                                </ul>
                            </li>
                            <li>
                                <label for="subfolder2">HiddenStuff</label>
                                <input type="checkbox" id="subfolder2" />
                                <ul class="table-wrapper">
                                    <li class="file"><a href="">hiddenguy</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <label for="folder3">LogExampleInner</label>
                        <input type="checkbox" id="folder3" />
                        <ul class="table-wrapper">
                            <li class="file"><a href="">graphics</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <label for="folder4">NoLogInside</label>
                        <input type="checkbox" id="folder4" />
                        <ul class="table-wrapper">
                        </ul>
                    </li>
                    <li class="file"><a href="">graphics</a>
                    </li>
                    <li class="file"><a href="">game</a>
                    </li>
                    </li>
                    </li>
                </ul>
        </ul>

</body>
</html>
