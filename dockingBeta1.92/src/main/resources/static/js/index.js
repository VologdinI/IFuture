let goal="server";//то с чем будет произведена связь при поиске логов
let ip = "";
let mapNavigator = new Map();


const maxSize = 50000000; //50Мб размер того что посылает пользователь
let mainPackage ={ID:'100'};
let emptyTreePackage = null;
let prevTreePackage = null;
var mainUl = document.getElementById("ulSubFoldersAndFiles100");


$.getJSON('http://www.geoplugin.net/json.gp?jsoncallback=?', function(data) {
    ip += data.geoplugin_request;
    }
)
//работа с кнопакми
let buttonNet = document.getElementById("buttonNet");
buttonNet.addEventListener('click', updateButtonNet);
function updateButtonNet() {
    goal="net";
    setClassForCurrentBtn('#buttonNet');
}
let buttonServer = document.getElementById("buttonServer");
buttonServer.addEventListener('click', updateButtonServer);
function updateButtonServer() {
    goal="server";
    setClassForCurrentBtn('#buttonServer');
}
let buttonUser = document.getElementById("buttonUser");
buttonUser.addEventListener('click', updateButtonUser);
function updateButtonUser() {
    goal="user";
    setClassForCurrentBtn('#buttonUser');
}
function setClassForCurrentBtn(idOfElem){
    $('#buttonNet').removeClass('btn-outline-dark');
    $('#buttonServer').removeClass('btn-outline-dark');
    $('#buttonUser').removeClass('btn-outline-dark');
    $('#buttonNet').addClass('btn-outline-primary');
    $('#buttonServer').addClass('btn-outline-primary');
    $('#buttonUser').addClass('btn-outline-primary');

    $(idOfElem).toggleClass('btn-outline-dark');
}

//Кнопка отправки расширения и требуемой строки
let buttonInput = document.getElementById("submitTextAndFiles");
buttonInput.addEventListener('click', sendInput);
//Получить treePackage до изменений
function sendInput() {
    hideNavigation();
    //поставить нулл чтобы не отрисовывалось заново
    prevTreePackage = null;
    emptyTreePackage = null;
    let ext = document.getElementById("extension").value;
    let text = document.getElementById("goalText").value;
    //кнопка превращается в загрузку
    document.getElementById("submitTextAndFiles").setAttribute("disabled", "disabled");//true mb
    document.getElementById("load").style.visibility = 'visible';
    //очистить все
    mainUl.innerText ="";
    //innerHTML не эстетичен
    document.getElementById("divText").innerHTML = '<div id="divTabFolder">\n' +
        '            </div>';

    makeTextBtnsDisabled();
    //снова ip
        let url = "getTreePackageBeforeAnalysis?goal=" + goal +"&ip=" + ip;
        $.ajax({
            type: "get",
            url: url,
            data: {
                extension: ext,
                goalText: text
            },
            success: function (treeP) {
                emptyTreePackage = getFromJsonToObj(treeP);
            }
        });
    return false;
}
//отключить кнопки сбоку
function makeTextBtnsDisabled(){
let btns = $("#divNavigation button");
    for (let i = 0; i < btns.length; i++) {
        btns[i].setAttribute("disabled","disabled");
    }
}
function makeTextBtnsEnabled(){
    let btns = $("#divNavigation button");
    for (let i = 0; i < btns.length; i++) {
        btns[i].removeAttribute("disabled");
    }
}
function prepareForNewFile(){
    showNavigation();

    makeTextBtnsEnabled();
    let p = document.getElementById("indicator");
    let id = -1;
    id = getCurrentTa();
    let overlaps = 1;
    if(mapNavigator.has(id)) {
        overlaps = mapNavigator.get(id);
    }
    let s = "Amount of overlaps: "+overlaps;
    let textArea = $("#text000");
    textArea.val("");
    p.innerHTML = s;
}
hideNavigation();
function hideNavigation(){
    document.getElementById("divTextFind").hidden = true;
}
function showNavigation(){
    document.getElementById("divTextFind").hidden = false;
}

function changeGreenStuff() {
    $.ajax({
        url: 'threadsStates',
        success: function (data) {
            if(data=="done") {
                document.getElementById("submitTextAndFiles").removeAttribute("disabled");
                document.getElementById("load").style.visibility = 'hidden';
            }
        }
    });
}
setInterval(changeGreenStuff, 1000);
//обновление отрисовки
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
//действия перед закрытием вкладки
window.onbeforeunload = function () {
    let url = 'endSession';
    $.ajax({
        type: "post",
        url: url,
        data: {
            ip: ip
        },
    });
};

//добавить добро за счет скрола
function addLiveLoadForTextArea(e) {
    //если до конца, то
    let textArea = e.target;
    if (textArea.scrollHeight - textArea.scrollTop <= (textArea.clientHeight+5)) {
        // добавим больше данных
        //это еще надо потестить, пока в postman буду серверную часть писать
        let id = getNubmersFromEnd(textArea.id);
        let sUrl = "getMoreData?ip="+ip+"&id="+id;
        $.ajax({
            type: "get",
            url: sUrl,
            data: {},
            success: function (data, textStatus) {
                if (textStatus == 'success') {
                        //вставить текст, 400 строчек, меньше если это последний блок
                        textArea.insertAdjacentHTML("beforeend", data);
                }
            }
        });
    }
}

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
//создание textarea и кнопок связанных с textarea
function makeFile(oldFile, file, packageForFile){//packageForFile li find element
    if((!oldFile.isContainsRequiredString)&&file.isContainsRequiredString){
        let s='<li id="file'+file.ID+'" class="file"><a id="a'+file.ID+'" href="">'+file.file.path+'</a></li>';
        let packageId="ulSubFoldersAndFiles"+packageForFile.ID;
        let ul=document.getElementById(packageId);
        ul.insertAdjacentHTML('beforeend', s);
        let sFile = "file"+file.ID;
        document.getElementById("a"+file.ID).onclick = function() {
            if(document.getElementById("a"+file.ID).className =="") {
                let sUrl = "";
                sUrl += "getFirstData?ip="+ip+"&id="+file.ID;
                $.ajax({
                    type: "get",
                    url: sUrl,
                    data: {},
                    success: function (data, textStatus) {
                        if (textStatus == 'success') {
                            //меняем ссылку
                            document.getElementById("a" + file.ID).classList.toggle("text-success");
                            $("#a" + file.ID).removeAttr("href");
                            document.getElementById("a" + file.ID).setAttribute("disabled", "disabled");
                            //добавление textarea
                            addButtonForTextArea(file.ID, file.file.path);
                            //включить кнопки
                            document.getElementById("button"+file.ID).addEventListener('click', prepareForNewFile, false);
                            addTextArea("text", file.ID,"mainTa" , data, document.getElementById("divText"));
                            //событие на прокрутку до конца
                            document.getElementById("text"+ file.ID).addEventListener('scroll', addLiveLoadForTextArea, false);
                        }
                    }
                });
            }
            return false;//без этого перезагружает страницу
        };
    }
}
//навигация


addTextArea("text","000" ,"navigator", "", document.getElementById("divNavigate"));
document.getElementById("text000").removeAttribute("hidden");
document.getElementById("next").addEventListener('click', getNextBlock, false);
document.getElementById("prev").addEventListener('click', getPrevBlock, false);
function  getNextBlock() {
    let textArea = $("#text000");
    let id = getCurrentTa();
    let sUrl = "getNextBlock?id="+id;
    if(id!=-1) {
        $.ajax({
            type: "get",
            url: sUrl,
            success: function (data, textStatus) {
                if (textStatus == 'success') {
                    //вставить текст, 400 строчек, меньше если это последний блок
                    //

                    textArea.val(data);
                }
            }
        });
    }
}
function  getPrevBlock() {
    let textArea = $("#text000");
    let id = getCurrentTa();
    let sUrl = "getPrevBlock?id="+id;
    if(id!=-1) {
        $.ajax({
            type: "get",
            url: sUrl,
            success: function (data, textStatus) {
                if (textStatus == 'success') {
                    //вставить текст, 400 строчек, меньше если это последний блок
                    textArea.val(data);
                }
            }
        });
    }
}
document.getElementById("overlap").addEventListener('click', makeOverlaps, false);
function  makeOverlaps() {
    let id = getCurrentTa();
    let sUrl = "makeOverlaps?id="+id;
    if(id!=-1) {
        $.ajax({
            type: "get",
            url: sUrl,
            success: function (data, textStatus) {
                if (textStatus == 'success') {
                    //добавить 200 вхождений
                    if(mapNavigator.has(id)){
                        let numOfOverlaps = mapNavigator.get(id);
                        numOfOverlaps+=Number.parseInt(data);
                        mapNavigator.set(id, numOfOverlaps);
                    }else{
                        let numOfOverlaps = Number.parseInt(data);
                        mapNavigator.set(id, numOfOverlaps);
                    }
                    //let p =$("#indicator");  //не работает почему то
                    let p = document.getElementById("indicator");
                    let s = "Amount of overlaps: "+mapNavigator.get(id);
                    p.innerHTML = s;
                }
            }
        });
    }
}


function  getCurrentTa() {
    let navigators = $(".mainTa");//textarea
    let id = -1;
    for (let i = 0; i < navigators.length; i++) {
        if (navigators[i].hidden == false){
            id = getNubmersFromEnd(navigators[i].id);//какая сейчас активна
        }
    }
    return id;
}

//добавить textArea
function addTextArea(textId, id, className, text, parent) {
    let textArea = document.createElement("textarea");
    textArea.setAttribute("id", textId+ id);
    textArea.setAttribute("placeholder", "Замещающий текст");
    textArea.setAttribute("readonly", "readonly");
    textArea.setAttribute("rows", "25");
    textArea.className = className;
    textArea.innerText = text;
    textArea.hidden = true;
    parent.appendChild(textArea);
}
//добавить кнопок для textArea
function addButtonForTextArea(id, text){
    let button = document.createElement('button');
    button.classList.add('btn');
    button.classList.add('btn-primary');
    button.setAttribute("id", "button"+id);
    button.textContent = text;
    document.getElementById("divTabFolder").appendChild(button);
    $( "#button"+id ).click(function() {
        let textAreas = document.getElementsByClassName("mainTa");
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

        let arBuff = elem.childNodes;
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

//передать добро с жесткого диска на сервер
function readALotOfFiles(e) {
    buttonUser.removeAttribute("disabled");

    let files = e.target.files;
    let summSize = 0;
    for (let i = 0; i < files.length; i++) {
        summSize+=files[i].size;
    }
    if(summSize>maxSize)
        alert("Size of files should be less than 50 MB, in your case it's = " + summSize + "MB");
    else {
        let arReaders = new Array(files.length);
        let arPaths = new Array(files.length);

        for(let i=0;i<files.length;i++){
            arReaders[i] = new FileReader();
            arPaths[i] = files[i].webkitRelativePath;
        }
        for(let i=0;i<files.length;i++){
            arReaders[i].onload = function(e) {
                let contents = e.target.result;
                //let url = "fileFromClient?id="+id; //какого черта тут он не меняется?
                $.getJSON('http://www.geoplugin.net/json.gp?jsoncallback=?', function(data) {
                    let id = arReaders.indexOf(e.target);
                    let url = "fileFromClient";//"fileFromClient?id="+id;
                    let ip ="";
                    ip += data.geoplugin_request;
                        $.ajax({
                                type: "post",
                                url: url,
                                data: {
                                    id: id,
                                    content: contents,
                                    localPath: arPaths[id],
                                    ip: ip
                                }
                                ,
                                success: function (dataInner, textStatus) {
                                    if (textStatus == 'success') {
                                        console.log(dataInner);
                                    }
                                }
                        });
                });
            };
        }
        for(let i=0;i<files.length;i++){
            arReaders[i].readAsText(files[i]);
        }
    }
}

document.getElementById('files-input').addEventListener('change', readALotOfFiles, false);
//конец файлов

//взять из теста формата Текст123 только число, в строковом виде
function getNubmersFromEnd(str){
    let r = /\d+/;
    let numbers = "";
    numbers += str.match(r);
    return numbers;
}


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
