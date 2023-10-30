

function saveRoom() {
    var roomNumber = $('#room_num').val();
    var roomCapacity = 4;
    var status = "0"; // You can modify this based on your requirements
    var other = $('#others').val(); // Leave this empty for now

    // Send AJAX request
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/room/save", // Adjust the URL to match your backend endpoint
        async: true,
        data: JSON.stringify({
            "roomNumber": roomNumber,
            "roomCapacity": roomCapacity,
            "status": status,
            "other": other
        }),
        success: function (data) {
            alert("Room saved successfully.");
            resetForm();
        },
        error: function (xhr, status, error) {
            if (error.hasOwnProperty('message')) {
                alert("Error Message: " + error.message);
            } else {
                alert("Unknown Error Occurred");
            }
        }
    });
}




//reset function
function resetForm() {
    $('#room_num').val(''); // Clear room number
    $('#others').val(''); // Clear others field
}