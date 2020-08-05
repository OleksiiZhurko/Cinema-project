$('form').submit(function(e) {
	if (eq()) {
		e.preventDefault();
	}
})

$('#password, #repassword').change(function() {
	if (($('#password').val().length === 0) || ($('#repassword').val().length === 0)) {
		$('#password').removeClass('has-success').removeClass('has-error')
		$('#repassword').removeClass('has-success').removeClass('has-error')
	} else if (eq()) {
		$('#password').removeClass('has-success').addClass('has-error');
		$('#repassword').removeClass('has-success').addClass('has-error');
	} else {
		$('#password').removeClass('has-error').addClass('has-success');
		$('#repassword').removeClass('has-error').addClass('has-success');
	}
})

function eq() {
	return $('#password').val() !== $('#repassword').val();
}
