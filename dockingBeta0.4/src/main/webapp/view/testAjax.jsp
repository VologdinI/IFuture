<%--
  Created by IntelliJ IDEA.
  User: Kano
  Date: 15.08.2019
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fuck JSP bless AJAX</title>

</head>
<body>
    <button id="getServerTime">get time</button></br>
    <p id="getTime"></p>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <%--<script src="../js/jqueryMin3.4.1.js"></script>--%>
    <script src="../js/script.js"></script>
<%--<script>
    $(document).ready(function () {
        $("#getServerTime").click(function () {
            $.ajax({
                url: 'getSomeTime',
                success: function (data) {
                    $("#getTime").html(data);
                }
            });
        });
    });
</script>--%>
</body>
</html>
