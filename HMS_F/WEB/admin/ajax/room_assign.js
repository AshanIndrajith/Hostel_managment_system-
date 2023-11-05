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
            // alert("Room saved successfully.");
            Swal.fire({

                width: '400px', // Set the width of the pop-up box
                heightAuto: false, // Prevent automatic height adjustment
                position: 'center',
                icon: 'success',
                title: 'Your work has been saved',
                icon: 'success',
                showConfirmButton: false,
                timer: 3000
              });
              
              // Delay the redirection for 3 seconds (3000 milliseconds)
              setTimeout(function() {
                window.location.href = 'availableRoom.html';
              }, 1000);
            // resetForm();
        }, success: function (data) {
            Swal.fire({

                width: '400px', // Set the width of the pop-up box
                heightAuto: false, // Prevent automatic height adjustment
                position: 'center',
                icon: 'success',
                title: 'Successfully Added!',
                icon: 'success',
                showConfirmButton: false,
                timer: 3000
              });
              
              // Delay the redirection for 3 seconds (3000 milliseconds)
              setTimeout(function() {
                window.location.href = 'availableRoom.html';
              }, 1000);
            // resetForm();
        },
        error: function (xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!'
              })
        }
    });
}