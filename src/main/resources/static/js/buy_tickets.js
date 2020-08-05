$(function () {
    let token = $("input[name='_csrf']").val();
    let header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$('#prev').click(function(event) {
    event.preventDefault();

    $('#box').hide();
    $('#hall').show();

    $('#hr1').siblings('div.info').remove();
});

$('#next').click(function() {
    let num;

    if ((num = $(":checked").length) > 0) {
        for (let i = 0; i < rows.length; i++) {
            $('#hr1').after(
                '<div class="row info text-center px-0">' +
                    '<h5 class="col-4">' + rows[i] + '</h5>' +
                    '<h5 class="col-4">' + seats[i] + '</h5>' +
                    '<h5 class="col-4">' + ticketPrice + '</h5>' +
                '</div>'
            );
        }

        document.getElementById("total_next").innerHTML = ticketPrice * num;

        $('#hall').hide();
        $('#box').show();
    }
});

$('#buy').click((event) => {
    event.preventDefault();

    let arrSeats = "[";

    for (let i = 0; i < rows.length; i++) {
        arrSeats += "{" + "row:" + rows[i] + "," + "seat:" + seats[i] + "," + "index:" + indexes[i] + "}";

        arrSeats += (i + 1 < rows.length) ? "," : "]";
    }

    const dt = {
        sessionId : parseInt(window.location.href.match(/(?<=\/tickets\/session\?id=)(\d)+/)[0]),
        login : null,
        price : parseInt($('#price').attr("value")),
        seats : arrSeats
    };

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/tickets/buy",
        data : JSON.stringify(dt),
        dataType : 'json',
        success : function(result) {
            location.reload();
        },
        error : function(e) {

        }
    });
});
