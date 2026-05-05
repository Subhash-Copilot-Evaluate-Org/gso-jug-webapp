$(window).scroll(function () {
    if ($(this).scrollTop() >= 50) {        // If page is scrolled more than 50px
        $('.return-to-top').fadeIn(200);    // Fade in the arrow
    } else {
        $('.return-to-top').fadeOut(200);   // Else fade out the arrow
    }
});

$('.return-to-top').click(function () {    // When arrow is clicked
    $('body,html').animate({
        scrollTop: 0                         // Scroll to top of body
    }, 500);
});

$('#raffleForm').submit(function (event){
    var form = this;
    if (form.checkValidity() === false) {
        event.preventDefault();
        event.stopPropagation();
    } else{

        $.ajax({
            type: 'POST',
            url: 'raffle',
            data: JSON.stringify({
                'first_name': $('#first_name').val(),
                'last_name': $('#last_name').val(),
                'email': $('#email_id').val().trim()
            }),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {

                console.log("Registered Successfully");
                setInterval(window.location.reload(true), 5000);

            },
            error: function(data){
                console.log("Errored out");
            }
        });
    }
});

/*
$(".modal").on("hidden.bs.modal", function(){
    $('#first_name').val('');
    $('#last_name').val('');
    $('#email_id').val('');
});*/

// Opinion Poll functionality
var votedPolls = JSON.parse(localStorage.getItem('votedPolls') || '{}');

$(document).ready(function () {
    var pollId = $('#pollId').val();
    if (pollId && votedPolls[pollId]) {
        fetchAndShowResults(pollId);
        $('#pollDiv').hide();
    }
});

$('#pollForm').submit(function (event) {
    event.preventDefault();

    var selectedOption = $('input[name="pollOption"]:checked').val();
    var pollId = $('#pollId').val();

    if (!selectedOption) {
        $('#pollMessage').text('Please select an option before voting.').show();
        return;
    }

    if (votedPolls[pollId]) {
        $('#pollMessage').text('You have already voted in this poll.').show();
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/poll/vote',
        data: JSON.stringify({'optionId': parseInt(selectedOption)}),
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            votedPolls[pollId] = selectedOption;
            localStorage.setItem('votedPolls', JSON.stringify(votedPolls));
            showResults(data);
            $('#pollDiv').hide();
        },
        error: function () {
            $('#pollMessage').text('An error occurred. Please try again.').show();
        }
    });
});

function fetchAndShowResults(pollId) {
    $.ajax({
        type: 'GET',
        url: '/poll/results/' + pollId,
        dataType: 'json',
        success: function (data) {
            showResults(data);
        }
    });
}

function showResults(poll) {
    var totalVotes = 0;
    $.each(poll.options, function (i, option) {
        totalVotes += option.voteCount;
    });

    var html = '';
    $.each(poll.options, function (i, option) {
        var percentage = totalVotes > 0 ? Math.round((option.voteCount / totalVotes) * 100) : 0;
        html += '<div class="mb-3">';
        html += '<div class="d-flex justify-content-between">';
        html += '<span>' + option.optionText + '</span>';
        html += '<span>' + option.voteCount + ' vote(s) (' + percentage + '%)</span>';
        html += '</div>';
        html += '<div class="progress">';
        html += '<div class="progress-bar bg-success" role="progressbar" style="width: ' + percentage + '%" ';
        html += 'aria-valuenow="' + percentage + '" aria-valuemin="0" aria-valuemax="100"></div>';
        html += '</div>';
        html += '</div>';
    });

    $('#pollResults').html(html);
    $('#totalVotes').text('Total votes: ' + totalVotes);
    $('#resultsDiv').show();
}
