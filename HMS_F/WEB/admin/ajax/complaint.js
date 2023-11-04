
getA3llComplaints() 

function saveComplain() {

    
    let pid = $('#pid').val();
    let ptype = $('#type').val();
    let rnum = $('#rnum').val();
    let sid = $('#sid').val();
    let image = $('#image').prop('files')[0];
    let desc = $('#desc').val();
    let status="pending";
    let adminLevel="subwarden";
    let date=getCurrentDate();


  

    let formData = new FormData();
    formData.append("property_uniq_id", pid);
    formData.append("property_type", ptype);
    formData.append("room_number", rnum);
    formData.append("complainant", sid);
    formData.append("image", image);
    formData.append("description", desc);
    formData.append("status", status);
    formData.append("admin_level", adminLevel);
    formData.append("complaint_date", date);

    
  
    $.ajax({
      method: "POST",
      url: "http://localhost:8080/api/complaints/complainSave",
      processData: false,
      contentType: false,
      data: formData,
      success: function (data) {

        
     alert("saved");

      },
      error: function (xhr, exception) {
        alert("Error occurred while saving damage");
      }
    });

}


function getCurrentDate() {
    const currentDate = new Date();

    const year = currentDate.getFullYear();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // Month starts from 0
    const day = currentDate.getDate().toString().padStart(2, '0');

    const formattedDate = `${year}-${month}-${day}`;

    return formattedDate;
}




function getAllComplaints() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/complaints/view",
        success: function(data, status) {
            console.log("Status:", status); // Print the status
            console.log("Data:", data); // Print the data
            // Clear existing table rows
            $('#ComplaintsTable tbody').empty();

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
                let status = complaint.status;
                let adminLevel = complaint.admin_level; // Corrected property name

                let newRow = '<tr>' +
                    '<td>' + id + '</td>' +
                    '<td>' + roomNo + '</td>' +
                    '<td>' + propertyType + '</td>' +
                    '<td>' + property_id + '</td>' +
                    '<td>' + description + '</td>' +
                    '<td>' + img + '</td>' +
                    '<td>' + c_date + '</td>' +
                    '<td>' + status + '</td>' +
                    '<td>' + adminLevel + '</td>' +
                    '</tr>';

                $('#ComplaintsTable tbody').append(newRow);
            }
        },
        error: function(xhr, status, error) {
            // Handle the error response
            console.log("Error:", error);
        }
    });
}
.