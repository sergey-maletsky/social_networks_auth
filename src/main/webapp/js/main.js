$(function () {

    $("#user-submit").on("click", function (event) {
        event.preventDefault();

        var formData = $("#user").serializeArray();
        var json = convertFormToJSON(formData);
        post('/users', json);
    });

    $("#generate").on("click", function (event) {
        event.preventDefault();

        var formData = $("#generate_users").serializeArray();
        var json = convertFormToJSON(formData);
/*        var studyId = $("#study_id").text();*/
        post('/users/generate', json);
    });

    $("#search-submit").on("click", function (event) {
        event.preventDefault();

        var formData = $("#search").serializeArray();
        var json = convertFormToJSON(formData);
        get('/users', json);
    });

    $("#search-clean").on("click", function (event) {
        event.preventDefault();
        location.reload();
    });

    function convertFormToJSON(formData) {

        var json = {};
        $.each(formData, function() {
            json[this.name] = this.value || "";
        });
        return json;
    }

    function post(url, json) {

        options = {
            method: 'POST',
            url: url,
            data: json,
            dataType: "json"
        }
        request(options);
    }

    function put(url, json) {

        options = {
            method: 'PUT',
            url: url,
            data: json,
            dataType: "json"
        }
        request(options);
    }

    function get(url) {

        options = {
            method: 'GET',
            accept: 'application/json',
            url: url
        }
        request(options);
    }

    function get(url, json) {

        options = {
            method: 'GET',
            accept: 'application/json',
            url: url,
            data: json,
            dataType: "json"
        }
        request(options);
    }

    function request(options, timeToWaitIfTimeout) {

        $('#error-field').text("");
        timeToWaitIfTimeout = (typeof timeToWaitIfTimeout === 'undefined') ? 1 : timeToWaitIfTimeout;

        return $.ajax(options)
            .done(function (data) {
                if (data.result) {
                    var index = 0;
                    if (data.message !== 'create') {
                        $("#users_table .users_tr").remove();
                        $.each(data.result, function () {
                            index++;
                            $("#users_table").append("<tr class=\"users_tr\"> <td>" + index + "</td> <td>" + this.name_t + "</td> <td>" + this.age + "</td></tr>")
                        });

                    } else {
                        data.message = "";
                        $("#users_table").append("<tr class=\"users_tr\"> <td></td> <td>" + data.result.name_t + "</td> <td>" + data.result.age + "</td></tr>")
                    }
                }

                if (data.message === "search") {
                    data.message = "";
                    $(".users-pages").remove();
                }

                $('#error-field').text(data.message ? data.message : "");
            })
            .fail(function (jqXHR, textStatus) {
                processError(jqXHR, options, textStatus, timeToWaitIfTimeout);
            });
    }

    function processError(jqXHR, options, textStatus, timeToWaitIfTimeout) {

        if ((textStatus == 'timeout' || jqXHR.status >= 500) && timeToWaitIfTimeout < 16) {
            console.warn("Connection timeout or http status 5xx. \nURL:%s \nWaiting for %d seconds and requesting again.",
                options.url, timeToWaitIfTimeout);
            setTimeout(request(options, timeToWaitIfTimeout * 2), 1000 * timeToWaitIfTimeout);
        } else {
            console.error("Error while trying to make " + options.method + " request to %s \nCode: %d, %s",
                options.url, jqXHR.status, jqXHR.statusText); //validatorErrors
            var errors = $.parseJSON(jqXHR.responseText);

            $('#error-field').text(errors.message ? errors.message : getValidatorErrors(errors.validatorErrors));
        }
    }
    
    function getValidatorErrors(errors) {

        var result = "";
        $.each( errors, function (key, val) {
            result += key + " : " + val + "; ";
        });
        return result;
    }
});