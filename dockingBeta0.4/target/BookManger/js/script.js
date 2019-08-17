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


