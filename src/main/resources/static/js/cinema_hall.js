let rows = [];
let seats = [];
let indexes = [];

$('#hall').show();
$('#box').hide();

const ticketPrice = parseInt($('#price').attr("value"))

$('input:checked').each(function() {
	$(this).parent().replaceWith('<div class="seat-occupied"></div>');
});

$(':checkbox').click(function() {

	const num = $(":checked").length;

	let row = parseInt($(this).siblings('.seat_row').attr('value'));
	let seat = parseInt($(this).siblings('.seat_place').attr('value'));
	let index = (row - 1) * parseInt($(this).parent().parent().children().length) + (seat - 1);

	if ($(this).prop('checked')) {
		rows.push(row);
		seats.push(seat);
		indexes.push(index);
	} else {
		rows.remove(row);
		seats.remove(seat);
		indexes.push(index);
	}

	$("label[for="+$(this).attr('id')+"]").toggleClass('check');

	document.getElementById("count").innerHTML = num;
	document.getElementById("total").innerHTML = ticketPrice * num;

})

$('#prev-page').click(function () {
	window.history.go(-1);
});

Array.prototype.remove = function() {
    let arg = arguments;
    let len = arg.length;
    let elem;
    while (len && this.length) {
        if ((elem = this.indexOf(arg[--len])) !== -1) {
            this.splice(elem, 1);
        }
    }
    return this;
};
