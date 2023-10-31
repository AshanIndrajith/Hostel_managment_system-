getAVailbleRoom()

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





function getAVailbleRoom() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/room/AvailableRoom", // Update with your actual URL
        success: function (data, status) {
            console.log("Status:", status);
            console.log("Data:", data);
  
            // Get the table body to populate data
            var tableBody = $('#AvailbleRoom tbody');
            tableBody.empty(); // Clear existing table rows
  
            // Loop through the data and create table rows
            for (var i = 0; i < data.length; i++) {
                var room = data[i];
                var room_number = room[0];
                var  available_capacity = room[1];
                
  
                var newRow = '<tr>' +
                    '<td>' + room_number + '</td>' +
                    '<td>' + available_capacity + '</td>' +
                    '<td><button type="button" class="update btn btn-success" onclick="getRoomNumber(\'' + room_number + '\')">Assign</button> ' +

                   
                    '</tr>';
  
                tableBody.append(newRow);
            }
        },
        error: function (xhr, status, error) {
            console.error("Request failed with status: " + status);
            console.error("Error details: " + error);
            console.error("hello");
        }
    });


  }


  function getRoomNumber(room_number) {
      alert(room_number);
    var rnum = String(room_number); // Convert to a string to preserve leading zeros

    // Construct the URL for the new page with query parameters
    var url = "allocate_room.html" +
        "?rnum=" + encodeURIComponent(rnum);

    // Redirect the user to the new page
    window.location.href = url;
}


  
  