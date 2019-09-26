//Ajax

function changeGreenStuff() {
    $.ajax({
        url: 'testGreen',
        success: function (data) {
            $("#file3").html(data);
        }
    });
}
setInterval(changeGreenStuff, 13000);


/*
//getSomeTime только через другой вариант
//нифига не работает, попробовать еще
/*
function senStuff(){//затестить потом с  EventListener
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET', 'http://localhost:8080/getSomeTime');
    ourRequest.onload = function () {
        if(ourRequest.status >= 200 && ourRequest.status <400) {
            var ourData = JSON.parse(ourRequest.responseText);
        }else{
            console.log("Conntected but get error");
        }
        renderHTML(ourData);
    }
    ourRequest.onerror = function () {
        console.log("Connection faild");
    }
}
//для верхнего теста
function renderHTML(data){
    let elem = document.getElementById('getTime');
    elem.innerHTML = data;
}
setInterval(senStuff, 3000);
*/

let mainPackage ={ID:'100'};
let emptyTreePackage = null;
let prevTreePackage;
//сильное заявление


function getTpAfterLogFinder() {
    $.ajax({
        url: 'sendTpAfterLogFinder',
        success: function (data) {
            emptyTreePackage = getFromJsonToObj(data);
        }
    });
}
getTpAfterLogFinder();
function changeStuffTP() {
    $.ajax({
        url: 'getTreePackage',
        success: function (data) {
            if(prevTreePackage == null)
                prevTreePackage = emptyTreePackage;
            let treePackage = getFromJsonToObj(data);
            makeTreePackage(prevTreePackage ,treePackage, mainPackage);
            prevTreePackage = treePackage;
        }
    });
}
setInterval(changeStuffTP, 3000);
//тестим создание папки

//let treePackage = getFromJsonToObj(oldFagTreePackage);
//setInterval(makeTreePackage(treePackage, mainPackage),5000);

//end Ajax
//support Ajax stuff

//аналог тега JSP
function makeTreePackage(oldTreePackage ,treePackage, parentPackage) {
    makeFolder(oldTreePackage.fileOfPackage, treePackage.fileOfPackage, parentPackage);
    for(let i=0;i<treePackage.treePackageList.length;i++){
        makeTreePackage(oldTreePackage.treePackageList[i] ,treePackage.treePackageList[i], treePackage.fileOfPackage);
    }
    for(let i=0;i<treePackage.fileList.length;i++){
        makeFile(oldTreePackage.fileList[i] ,treePackage.fileList[i], treePackage.fileOfPackage);
    }
}
//вставить файл, для верхней f
function makeFile(oldFile, file, packageForFile){//packageForFile li find element
    if((!oldFile.isContainsRequiredString)&&file.isContainsRequiredString){
        let s='<li id="file'+file.ID+'" class="file"><a id="a'+file.ID+'" href="">'+file.file.path+'</a></li>';
        let packageId="ulSubFoldersAndFiles"+packageForFile.ID;
        let ul=document.getElementById(packageId);
        ul.insertAdjacentHTML('beforeend', s);
        let sFile = "file"+file.ID;
        //document.getElementById("a"+file.ID).addEventListener("click", sendIdOfFile());
        document.getElementById("a"+file.ID).onclick = function() {
            let sUrl="";
            sUrl+=file.ID;
            $.ajax({
                type: "get",
                url: sUrl,
                data: {
                },
                success: function(msg, textStatus) {
                    if ( textStatus == 'success' )
                    document.getElementById("showText").innerText = msg;
                }
            });
            return false;//без этого перезагружает страницу
        };
    }
}
//вставить папку, для верхней f
function makeFolder(oldFile, file, packageForFile){//packageForFile li find element
    if((!oldFile.isContainsRequiredString)&&file.isContainsRequiredString){
        let li = document.createElement('li');
        li.id = 'folder'+file.ID;

        let lable = document.createElement('label');
        let sFor="checkBox"+file.ID;
        let lableText = document.createTextNode(file.file.path);
        lable.setAttribute('for','checkBox'+file.ID);
        lable.appendChild(lableText);
        li.appendChild(lable);

        let input = document.createElement('input');
        input.id=sFor;
        input.type='checkbox';
        li.appendChild(input);

        let ul = document.createElement('ul');
        ul.classList.toggle('table-wrapper');
        ul.id = "ulSubFoldersAndFiles" + file.ID;
        li.appendChild(ul);
        //добавить папку в середину списка(после папок, но перед файлами)
        let elem = document.getElementById("ulSubFoldersAndFiles"+packageForFile.ID);
        let arTemp = [];
        let arBuff = [];

        arBuff = elem.childNodes;
        let j=0;
        for(let i=0; i < arBuff.length; i++){
            if(arBuff[i].className=='file'){
                arTemp[j] = arBuff[i].cloneNode(true);
                j++;
            }
        }
        j=0;
        for(let i=0; i < arBuff.length+j; i++){
            if(arBuff[i-j].className=='file'){
                arBuff[i-j].remove();
                j++;
            }
        }
        elem.appendChild(li);
        for(let i=0; i < arTemp.length; i++){
            elem.appendChild(arTemp[i]);
        }
        //oldFile.isContainsRequiredString = true;
    }
}
//превратить JSON в объект с нормальным адресом
function getFromJsonToObj(jsonStr){
    let regexp=/\\/g;
    let jsonStrReplace = jsonStr.replace(regexp,'|');
    return JSON.parse(jsonStrReplace, function (key, value) {
            if(key=="path"){
                let sLastName=value.split("|");
                return sLastName[sLastName.length-1];
            }
            else
                return value;
        }
    )
}
//end of support Ajax stuff
//немного красоты в окно ввода
var extension = document.getElementById("extension");
extension.onfocus = function() {
    if ( extension.value == ".log") {
        extension.value = "";
    }
};
extension.onblur = function() {
    if ( extension.value == "") {
        extension.value = ".log";
    }
};
var errorMessage = document.getElementById("errorMessage");
errorMessage.style.color="#20fa34";

//работа с кнопакми
//потестить еще
var buttonNet = document.getElementById("buttonNet");
buttonNet.addEventListener('click', updateButtonNet);
function updateButtonNet() {
    $.post(
        'workWithNet',
    );
}
var buttonServer = document.getElementById("buttonServer");
buttonServer.addEventListener('click', updateButtonServer);
function updateButtonServer() {
    $.post(
        'workWithServer',
    );
}
//Добро это можно при создании файла сделать
function sendIdOfFile() {
    console.log(this);
    let id;
    id = this.id;
    let sUrl=id.substr(1,id.length);
    console.log(sUrl);
    $.ajax({
        url: sUrl,
        success: function(msg){
            //document.getElementById("showText").innerText = msg;
        }
    });
}

/*function sendIdOfFile(file) {
    let id;
    id = file.id;
    console.log(id);
    id="3";
    $.post(
        '/processFrom/sendIdOfFile',
        {
            Id: id
        },
    );
}*/
/*function updateButton() {
    $.ajax({
        url: 'workWithNet',
        success: function (data) {
            emptyTreePackage = getFromJsonToObj(data);
        }
    });
}*/
/*function updateButton() {
    $.post("workWithNet",
        {
            boolean: "true"
        },
        function(data, status){
            alert("Data: " + data + "\nStatus: " + status);
        });
}*/

//работа с кнопками ajax post

//ajax
/*
$("button").click(function(){
    $.ajax({url: "demo_test.txt", success: function(result){
            $("#div1").html(result);
        }});
});
*/
//post
/*
$("button").click(function(){
    $.post("demo_test_post.asp",
        {
            name: "Donald Duck",
            city: "Duckburg"
        },
        function(data, status){
            alert("Data: " + data + "\nStatus: " + status);
        });
});
*/

