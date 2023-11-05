function AssignRoom() {
    var roomNumber = $('#room_number').val();
    var studentId = $('#user_index').val();
    
    
    // Send AJAX request
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/roomAssign/assignRoom", // Adjust the URL to match your backend endpoint
        async: true,
        data: JSON.stringify({
            "roomId": roomNumber,
            "userId": studentId
            
        }),
        success: function (data) {
            Swal.fire('Room allocation has succeeded!', 'success');
   
           
        },
        error: function (xhr, status, error) {
            if (error.hasOwnProperty('message')) {
                alert("Error Message: " + error.message);
            } else {
                alert("User is already assigned to a room");
            }
        }
    });
}