$(document).ready(function(){
    
    $.get('lms/book/all/', function(data, status){
        console.log(status);
        console.log(data);
    });
});