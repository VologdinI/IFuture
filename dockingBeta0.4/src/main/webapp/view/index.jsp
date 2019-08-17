<%--
  Created by IntelliJ IDEA.
  User: Kano
  Date: 06.08.2019
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="../css/_styles.css" type="text/css" rel="stylesheet">
</head>
<body>
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

<script src="../js/script.js"></script>
</body>
</html>
