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


function changeStuff() {
    $.ajax({
        url: 'getSomeTime',
        success: function (data) {
            $("#getTime").html(data);
        }
    });
}
setInterval(changeStuff, 3000);
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

//сильное заявление
/*
function changeStuffTP() {
    $.ajax({
        url: 'gsonTreePackage',
        success: function (data) {
            /!*let treePackage = getFromJsonToObj(data);
            makeTreePackage(treePackage, mainPackage)*!/
            //$("#realAjax").html(data);
            //Json сразу контейнер, затестим
            $("#getTime").html(data);
        }
    });
}
setInterval(changeStuffTP, 3000);
*/


//тестим создание папки

//let treePackage = getFromJsonToObj(oldFagTreePackage);
//setInterval(makeTreePackage(treePackage, mainPackage),5000);

//end Ajax
//support Ajax stuff

//аналог тега JSP
function makeTreePackage(treePackage, parentPackage) {
    makeFolder(treePackage.fileOfPackage, parentPackage);
    for(let i=0;i<treePackage.treePackageList.length;i++){
        makeTreePackage(treePackage.treePackageList[i], treePackage.fileOfPackage);
    }
    for(let i=0;i<treePackage.fileList.length;i++){
        makeFile(treePackage.fileList[i], treePackage.fileOfPackage);
    }
}
//вставить файл, для верхней f
function makeFile(file, packageForFile){//packageForFile li find element
    if(file.isContainsRequiredString){
        let s='<li id="file'+file.ID+'" class="file"><a d="a'+file.ID+'" href="">'+file.file.path+'.log</a></li>';
        let packageId="ulSubFoldersAndFiles"+packageForFile.ID;
        let ul=document.getElementById(packageId);
        ul.insertAdjacentHTML('beforeend', s);
    }
}
//вставить папку, для верхней f
function makeFolder(file, packageForFile){//packageForFile li find element
    if(file.isContainsRequiredString){
        console.log(file);
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

        let elem = document.getElementById("ulSubFoldersAndFiles"+packageForFile.ID);
        elem.appendChild(li);
        console.log(li);
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

/*
document.getElementsByClassName("file")[0].classList.add('fileGreen');
if ( document.getElementsByClassName("file")[0].classList.contains('file') )
    document.getElementsByClassName("file")[0].classList.toggle('file');
*/


