//Ajax

function changeGreenStuff() {
    $.ajax({
        url: 'threadsStates',
        success: function (data) {
            if(data=="done") {
                document.getElementById("submitExtAndFiles").removeAttribute("disabled");
                document.getElementById("load").style.visibility = 'hidden';
            }
            $("#file3").html(data);
        }
    });
}
setInterval(changeGreenStuff, 13000);

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
//while(emptyTreePackage==null)
    setInterval(getTpAfterLogFinder, 1000);


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
                success: function(data, textStatus) {
                    if ( textStatus == 'success' ) {
                        //меняем ссылку
                        document.getElementById("a"+file.ID).classList.toggle("text-success");
                        $("#a"+file.ID).removeAttr("href");
                        document.getElementById("a"+file.ID).setAttribute("disabled","disabled");
                        //добавление textarea
                        addButtonForTextArea(file.ID, file.file.path);
                        addTextArea(file.ID, data);
                    }
                }
            });
            return false;//без этого перезагружает страницу
        };
    }
}
function addTextArea(id, text) {
    let textArea = document.createElement("textarea");
    textArea.setAttribute("id", "text"+ id);
    textArea.setAttribute("placeholder", "Замещающий текст");
    textArea.setAttribute("wrap", "off");
    textArea.setAttribute("readonly", "readonly");
    textArea.innerText = text;
    textArea.hidden = true;
    document.getElementById("divText").appendChild(textArea);
}
function addButtonForTextArea(id, text){
    let button = document.createElement('button');
    button.classList.add('btn');
    button.classList.add('btn-primary');

    button.setAttribute("id", "button"+id);
    //$("#button"+id).addClass('btn btn-primary');
    button.textContent = text;
    document.getElementById("divTabFolder").appendChild(button);
    //button.addEventListener('click', );
    $( "#button"+id ).click(function() {
        let textAreas = document.getElementsByTagName("textarea");
        for (let i = 0; i < textAreas.length; i++) {
            textAreas[i].hidden = true;
        }
        document.getElementById("text"+id).hidden = false;
    });
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


//работа с кнопакми
//потестить еще
let buttonNet = document.getElementById("buttonNet");
buttonNet.addEventListener('click', updateButtonNet);
function updateButtonNet() {
    $.post(
        'workWithNet',
    );
}
let buttonServer = document.getElementById("buttonServer");
buttonServer.addEventListener('click', updateButtonServer);
function updateButtonServer() {
    $.post(
        'workWithServer',
    );
}
//заменим processForm
let buttonInput = document.getElementById("submitExtAndFiles");
buttonInput.addEventListener('click', sendInput);
function sendInput() {
    let ext = document.getElementById("extension").value;
    let text = document.getElementById("goalText").value;

    //кнопка превращается в загрузку
    document.getElementById("submitExtAndFiles").setAttribute("disabled", "disabled");//true mb
    document.getElementById("load").style.visibility = 'visible';
    $.ajax({
        type: "get",
        url: "processFrom",
        data: {
            extension: ext,
            goalText: text
        }
    });
    return false;
}

