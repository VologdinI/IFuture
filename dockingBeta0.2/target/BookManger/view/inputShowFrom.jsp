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

    <link href="../css/_styles.css" type="text/css" rel="stylesheet">
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
        <ol class="tree">
            <sh:ShowTreeTag>Body</sh:ShowTreeTag>
        </ol>
            </c:if>
<%--
        <ol class="tree">
            <li>
                <label for="folder1">Folder 1</label> <input type="checkbox" checked disabled id="folder1" />
                <ol>
                    <li class="file"><a href="document.html.pdf">File 1</a></li>
                    <li>
                        <label for="subfolder1">Subfolder 1</label> <input type="checkbox" id="subfolder1" />
                        <ol>
                            <li class="file"><a href="">Filey 1</a></li>
                            <li>
                                <label for="subsubfolder1">Subfolder 1</label> <input type="checkbox" id="subsubfolder1" />
                                <ol>
                                    <li class="file"><a href="">File 1</a></li>
                                    <li>
                                        <label for="subsubfolder2">Subfolder 1</label> <input type="checkbox" id="subsubfolder2" />
                                        <ol>
                                            <li class="file"><a href="">Subfile 1</a></li>
                                            <li class="file"><a href="">Subfile 2</a></li>
                                            <li class="file"><a href="">Subfile 3</a></li>
                                            <li class="file"><a href="">Subfile 4</a></li>
                                            <li class="file"><a href="">Subfile 5</a></li>
                                            <li class="file"><a href="">Subfile 6</a></li>
                                        </ol>
                                    </li>
                                </ol>
                            </li>
                            <li class="file"><a href="">File 3</a></li>
                            <li class="file"><a href="">File 4</a></li>
                            <li class="file"><a href="">File 5</a></li>
                            <li class="file"><a href="">File 6</a></li>
                        </ol>
                    </li>
                </ol>
            </li>
            <li>
                <label for="folder2">Folder 2</label> <input type="checkbox" id="folder2" />
                <ol>
                    <li class="file"><a href="">File 1</a></li>
                    <li>
                        <label for="subfolder2">Subfolder 1</label> <input type="checkbox" id="subfolder2" />
                        <ol>
                            <li class="file"><a href="">Subfile 1</a></li>
                            <li class="file"><a href="">Subfile 2</a></li>
                            <li class="file"><a href="">Subfile 3</a></li>
                            <li class="file"><a href="">Subfile 4</a></li>
                            <li class="file"><a href="">Subfile 5</a></li>
                            <li class="file"><a href="">Subfile 6</a></li>
                        </ol>
                    </li>
                </ol>
            </li>
            <li>
                <label for="folder3">Folder 3</label> <input type="checkbox" id="folder3" />
                <ol>
                    <li class="file"><a href="">File 1</a></li>
                    <li>
                        <label for="subfolder3">Subfolder 1</label> <input type="checkbox" id="subfolder3" />
                        <ol>
                            <li class="file"><a href="">Subfile 1</a></li>
                            <li class="file"><a href="">Subfile 2</a></li>
                            <li class="file"><a href="">Subfile 3</a></li>
                            <li class="file"><a href="">Subfile 4</a></li>
                            <li class="file"><a href="">Subfile 5</a></li>
                            <li class="file"><a href="">Subfile 6</a></li>
                        </ol>
                    </li>
                </ol>
            </li>
            <li>
                <label for="folder4">Folder 4</label> <input type="checkbox" id="folder4" />
                <ol>
                    <li class="file"><a href="">File 1</a></li>
                    <li>
                        <label for="subfolder4">Subfolder 1</label> <input type="checkbox" id="subfolder4" />
                        <ol>
                            <li class="file"><a href="">Subfile 1</a></li>
                            <li class="file"><a href="">Subfile 2</a></li>
                            <li class="file"><a href="">Subfile 3</a></li>
                            <li class="file"><a href="">Subfile 4</a></li>
                            <li class="file"><a href="">Subfile 5</a></li>
                            <li class="file"><a href="">Subfile 6</a></li>
                        </ol>
                    </li>
                </ol>
            </li>
            <li>
                <label for="folder5">Folder 5</label> <input type="checkbox" id="folder5" />
                <ol>
                    <li class="file"><a href="">File 1</a></li>
                    <li>
                        <label for="subfolder5">Subfolder 1</label> <input type="checkbox" id="subfolder5" />
                        <ol>
                            <li class="file"><a href="">Subfile 1</a></li>
                            <li class="file"><a href="">Subfile 2</a></li>
                            <li class="file"><a href="">Subfile 3</a></li>
                            <li class="file"><a href="">Subfile 4</a></li>
                            <li class="file"><a href="">Subfile 5</a></li>
                            <li class="file"><a href="">Subfile 6</a></li>
                        </ol>
                    </li>
                </ol>
            </li>
        </ol>
--%>
</body>
</html>
