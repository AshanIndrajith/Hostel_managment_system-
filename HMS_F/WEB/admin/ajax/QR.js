

function getProperty() {

    
    var uid = $('#qr-data-input').val();

 
   
  
  
    $.ajax({
      method: "GET",
      url: "http://localhost:8080/api/room/properties?propertyName=" + uid ,
      success: function(data) {
        // Clear existing table rows
        $('#Qr tbody').empty();
  
        // Loop through the array and create table rows dynamically
        for (let i = 0; i < data.length; i++) {
            let property = data[i];
            let id = property[0];
            let propertyName = property[1];
            let propertyUniqueId = property[2];
            let roomNumber = property[3];

            let newRow = '<tr>' +
                '<td>' + id + '</td>' +
                '<td>' + propertyName + '</td>' +
                '<td>' + propertyUniqueId + '</td>' +
                '<td>' + roomNumber + '</td>' +
                '<td><button type="button" class="update btn btn-success" onclick="getPropertyDetails(' + id + ')" >Complaint</button>' +
                '</tr>';
            $('#Qr tbody').append(newRow);
        }
  
    
  
      },
      error: function(xhr, status, error) {
        // Handle the error response
        console.log("Error:", error);
      }
    });
  }





  function getPropertyDetails(id) {


    $.ajax({
      method: "GET",
      url: "http://localhost:8080/api/user/get/" + id,
      async: true,
      success: function(data) {
        if (data != null) {
          var id = data.id;
          var index = data.user_index; // Update property name based on your data
          var fname = data.fname; // Update property name based on your data
          var email = data.email;
          var lname = data.lname; // Update property name based on your data

          console.log(index)
          // Construct the URL for the new page with query parameters
          var url = "update_student.html" +
            "?id=" + encodeURIComponent(id) +
            "&fname=" + encodeURIComponent(fname) +
            "&lname=" + encodeURIComponent(lname) +
            "&email=" + encodeURIComponent(email) +
            "&index=" + encodeURIComponent(index) ;
            
  
          // Redirect the user to the new page
          window.location.href = url;
        }
      },
      error: function(xhr, status, error) {
        console.log("Error:", error);
      }
    });
  }








function getAllProperty() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/room/pr", // Update with your actual URL
        success: function (data, status) {
            console.log("Status:", status);
            console.log("Data:", data);
  
            // Get the table body to populate data
            var tableBody = $('#Qr tbody');
            tableBody.empty(); // Clear existing table rows
  
            // Loop through the data and create table rows
            for (var i = 0; i < data.length; i++) {
                var property = data[i];
                var id = property[0];
                var propertyName = property[1];
                var propertyUniqueId = property[2];
                var roomNumber = property[3];
             
  
                var newRow = '<tr>' +
                    '<td>' + id + '</td>' +
                    '<td>' + propertyName + '</td>' +
                    '<td>' + propertyUniqueId + '</td>' +
                    '<td>' + roomNumber + '</td>' +
                    '<td><button type="button" class="update btn btn-success" onclick="getStudentrDetails(' + id + ')" >Update</button> <button type="button" onclick="deleteUser(' + id + ') " class="delete btn btn-danger">Delete</button></td>' +
                   
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
  
      
  
