$('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
    localStorage.setItem('activeTab', $(e.target).attr('href'));
});

let activeTab = localStorage.getItem('activeTab');

if (activeTab) {
    $('#Tabs a[href="' + activeTab + '"]').tab('show');
}