
function getComplaintsById() {
    var id = 18;
    alert("working");

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/complaints/get/" + id,
        success: function (data, status) {
            console.log("Status:", status); // Print the status
            console.log("Data:", data); // Print the data

            // Clear existing table rows
            $('#complainViewByIdTable tbody').empty();

            // Loop through the array and create table rows dynamically
            for (let i = 0; i < data.length; i++) {
                let complaint = data[i];
                let id = complaint.id;
                let roomNo = complaint.room_number; // Corrected property name
                let propertyType = complaint.property_type; // Corrected property name
                let property_id = complaint.property_uniq_id; // Corrected property name
                let description = complaint.description;
                let img = complaint.imageName;
                let c_date = complaint.complaint_date;
                let app=complaint.approved_date;
                let status = complaint.status;
              
  
                let newRow = '<tr>' +
                    '<td>' + id + '</td>' +
                    '<td>' + roomNo + '</td>' +
                    '<td>' + propertyType + '</td>' +
                    '<td>' + property_id + '</td>' +
                    '<td>' + description + '</td>' +
                    '<td><img src="../../../HMS_BACKEND/images/' + id + '/' + img + '"></td>' +
                    '<td>' + c_date + '</td>' +
                    '<td>' + app+ '</td>' +
                    '<td><button type="button" class="update btn btn-success" onclick="updateComplain(' + id + ')" >' + status + '</button>  </td>' +
          
                    '</tr>';

                $('#complainViewByIdTable tbody').append(newRow);
            }
        },
        error: function (xhr, status, error) {
            // Handle the error response
            console.log("Error:", error);
        }
    });
}




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
        url: "http://localhost:8080/api/room/findById?id=" + id, // Fixed the URL by adding "="
        async: true,
        success: function(data) {
            if (data != null ) { // Check if data is not null and not empty
                let property = data[0]; // Assuming you want to access the first element

                let id = property[0];
                let propertyName = property[1];
                let propertyUniqueId = property[2];
                let roomNumber = property[3];

                // Construct the URL for the new page with query parameters
                var url = "Complain.html" +
                    "?id=" + encodeURIComponent(id) +
                    "&propertyName=" + encodeURIComponent(propertyName) +
                    "&propertyUniqueId=" + encodeURIComponent(propertyUniqueId) +
                    "&roomNumber=" + encodeURIComponent(roomNumber);

                // Redirect the user to the new page
                window.location.href = url;
            } else {
                console.log("Data is empty or null.");
            }
        },
        error: function(xhr, status, error) {
            console.log("Error:", error);
        }
    });
}