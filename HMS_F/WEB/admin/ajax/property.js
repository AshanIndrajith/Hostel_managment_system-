



 function getProperty() {

   
      
    var rid = $('#rnum').val();


    
    $.ajax({
      method: "GET",
      url: "http://localhost:8080/api/auth/get-property?roomNumber=" + rid,
      success: function (data) {
        // Clear existing table rows
        $('#RoomMembers tbody').empty();
  
        // Loop through the array and create table rows dynamically
        for (let i = 0; i < data.length; i++) {
          let pr = data[i];
          let id = pr.propertyId;
          let uid = pr.propertyName;
          let rid = pr.propertyUniqueId;
          let date = pr.roomNumber;
  
          let newRow = '<tr>' +
            '<td>' + uid + '</td>' +
            '<td>' + rid + '</td>' +
            '<td>' + date + '</td>' +
          
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