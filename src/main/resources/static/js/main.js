$(document).ready(function () {

    $.ajax({
        
        url: "lms/book/all/",
        type: 'GET',
        success: function (res) {
            console.log(res);
            for(var i = 0; i < res.length; i++){
                var bookname = res[i].name;
                var bookId = res[i].id;
                var author = res[i].author.name;
                var slot = res[i].nextAvailableSlot;
                $('#card' + i).show();
                $('#card' + i).find('.card-header button').html(bookname);
                $('#bookId' + i).val(bookId);
                $('#card' + i).find('.card-body').html('Next Available Slot is : ' + slot);
            }
            
        },
        error:  function(e){
            $('#headMessage').html("No Books to show At the moment.");
        }
    });

});