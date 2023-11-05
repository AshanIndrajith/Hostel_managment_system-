


 
  function getRoomNumber() {
      
    var rid = $('#rnum').val();
    
    $.ajax({
      method: "GET",
      url: "http://localhost:8080/api/roomAssign/getByRoom?roomNumber=" + rid,
      success: function (data) {
        // Clear existing table rows
        $('#RoomMembers tbody').empty();
  
        // Loop through the array and create table rows dynamically
        for (let i = 0; i < data.length; i++) {
          let member = data[i];
          let id = member.assignmentId;
          let uid = member.userId;
          let rid = member.roomId;
          let date = member.assignmentDate;
  
          let newRow = '<tr>' +
            '<td>' + uid + '</td>' +
            '<td>' + rid + '</td>' +
            '<td>' + date + '</td>' +
            '<td><button type="button" class="delete btn btn-danger" onclick="deallocate(\'' + uid + '\')">Deallocate</button>' +
            '</tr>';
          $('#RoomMembers tbody').append(newRow);
        }
      },
      error: function (xhr, status, error) {
        // Handle the error response
        console.log("Error:", error);
      }
    });
  }
  





  function deallocate(ID) {
    $.ajax({
      method: "DELETE",
      url: "http://localhost:8080/api/roomAssign/delete/" + ID,
      async: true,
      success: function(data) {
        Swal.fire('Deallocated!', 'The room assignment has been deallocated.', 'success')
          .then(() => {
            window.location.href = "assigningRoomStudent.html"; // Removed extra ".html"
          });
      },
      error: function(xhr, status, error) {
        alert("Error: " + status); // Display the error message
      }
    });
  }
  
  
