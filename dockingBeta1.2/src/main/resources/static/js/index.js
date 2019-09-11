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
        url: 'getSomeTime',
        success: function (data) {
            if(prevTreePackage == null)
                prevTreePackage = emptyTreePackage;
            let treePackage = getFromJsonToObj(data);
            makeTreePackage(prevTreePackage ,treePackage, mainPackage)
        }
    });
}
setInterval(changeStuffTP, 10000);

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
        let s='<li id="file'+file.ID+'" class="file"><a d="a'+file.ID+'" href="">'+file.file.path+'.log</a></li>';
        let packageId="ulSubFoldersAndFiles"+packageForFile.ID;
        let ul=document.getElementById(packageId);
        ul.insertAdjacentHTML('beforeend', s);
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
        console.log('fuck');
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



