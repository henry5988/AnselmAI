/*
* index.js
* */
var myWindow = window.open(this.href,"","width=650, height=350");

$(document).ready(function() {


  var txt = '';
  var xmlhttp = new XMLHttpRequest();
  xmlhttp.onreadystatechange = function(){
    if(xmlhttp.status == 200 && xmlhttp.readyState == 4){
      console.log("request success");
      txt = xmlhttp.responseText;
      console.log(txt);
      myWindow.document.write(txt);
    }
  };
  xmlhttp.open("GET","source-downloaded.txt",true);
  xmlhttp.send();

    // var data = Papa.parse("source-downloaded.csv", {
    //     complete: function(results) {
    //         console.log("Finished:", results.data);
    //     }
    // });
    // myWindow.document.write(data);

    //window.close();
});


function parseHeader(text){
  var textLines = text.split(/\r\n|\n/);
  var headers = textLines[0];
  var lines = [];
}

function processData(allText) {

    var allTextLines = allText.split(/\r\n|\n/);

    var headers = allTextLines[0].split(',');

    var lines = [];

    for (var i=1; i<allTextLines.length; i++) {

        var data = allTextLines[i].split(',');

        if (data.length == headers.length) {

            var tarr = [];

            for (var j=0; j<headers.length; j++) {

                tarr.push(headers[j]+":"+data[j]);

            }

            lines.push(tarr);

        }

    }
    return printLines(lines);
}

function printLines(lines){
    var text = "";
    for(line in lines){
        text += line + "<br>";
    }
    console.log(text);
    return text;
}

