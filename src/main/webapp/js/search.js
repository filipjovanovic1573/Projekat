$(document).ready(function () {
    $("#pretaga").keyup(function () {
        var value = $(this).val();
        if (value != "")
            $.ajax({
                url: 'services/FindState?ime=' + value,
                data: {
                    ime: value
                },
                success: function (data) {
                    $('#info').empty();
                    $('#info').append(data);
                }
            });
    });
});