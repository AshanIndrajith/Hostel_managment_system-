
getAllComplaints() ;
getAllApprovedComplaints();


function saveComplain() {

    
    let pid = $('#pid').val();
    let ptype = $('#type').val();
    let rnum = $('#rnum').val();
    let sid = $('#userIndex').val();
    let image = $('#image').prop('files')[0];
    let desc = $('#desc').val();
    let status="pending";
    let approved_date="not assign";
    let date=getCurrentDate();


  

    let formData = new FormData();
    formData.append("property_uniq_id", pid);
    formData.append("property_type", ptype);
    formData.append("room_number", rnum);
    formData.append("complainant", sid);
    formData.append("image", image);
    formData.append("description", desc);
    formData.append("status", status);
    formData.append("admin_level", approved_date);
    formData.append("complaint_date", date);

    
  
    $.ajax({
      method: "POST",
      url: "http://localhost:8080/api/complaints/complainSave",
      processData: false,
      contentType: false,
      data: formData,
      success: function (data) {
        // alert("Room saved successfully.");
        Swal.fire({

            width: '400px', // Set the width of the pop-up box
            heightAuto: false, // Prevent automatic height adjustment
            position: 'center',
            icon: 'success',
            title: 'Your complain has been saved',
            icon: 'success',
            showConfirmButton: false,
            timer: 5000
          });
          
          // Delay the redirection for 3 seconds (3000 milliseconds)
          setTimeout(function() {
            window.location.href = 'QR_code_Scaner.html';
          }, 1000);
        // resetForm();
    },
      error: function (xhr, exception) {
        alert("Error occurred while saving damage");
      }
    });

}








function saveStudentComplain() {

    
  let pid = $('#pid').val();
  let ptype = $('#type').val();
  let rnum = $('#rnum').val();
  let sid = $('#userIndex').val();
  let image = $('#image').prop('files')[0];
  let desc = $('#desc').val();
  let status="pending";
  let approved_date="not assign";
  let date=getCurrentDate();




  let formData = new FormData();
  formData.append("property_uniq_id", pid);
  formData.append("property_type", ptype);
  formData.append("room_number", rnum);
  formData.append("complainant", sid);
  formData.append("image", image);
  formData.append("description", desc);
  formData.append("status", status);
  formData.append("admin_level", approved_date);
  formData.append("complaint_date", date);

  

  $.ajax({
    method: "POST",
    url: "http://localhost:8080/api/complaints/complainSave",
    processData: false,
    contentType: false,
    data: formData,
    success: function (data) {
      // alert("Room saved successfully.");
      Swal.fire({

          width: '400px', // Set the width of the pop-up box
          heightAuto: false, // Prevent automatic height adjustment
          position: 'center',
          icon: 'success',
          title: 'Your complain has been saved',
          icon: 'success',
          showConfirmButton: false,
          timer: 5000
        });
        
        // Delay the redirection for 3 seconds (3000 milliseconds)
        setTimeout(function() {
          window.location.href = 'Student_QR_code_Scaner.html';
        }, 1000);
      // resetForm();
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
              if (complaint.status === 'pending') { // Check if the status is 'pending'
                  let id = complaint.id;
                  let roomNo = complaint.room_number; // Corrected property name
                  let propertyType = complaint.property_type; // Corrected property name
                  let property_id = complaint.property_uniq_id; // Corrected property name
                  let description = complaint.description;
                  let img = complaint.imageName;
                  let snum = complaint.complainant;
                  let c_date = complaint.complaint_date;
                  let status = complaint.status;

                  let newRow = '<tr>' +
                      '<td>' + id + '</td>' +
                      '<td>' + roomNo + '</td>' +
                      '<td>' + propertyType + '</td>' +
                      '<td>' + property_id + '</td>' +
                      '<td>' + snum + '</td>' +
                      '<td>' + description + '</td>' +
                      '<td id="img"><img src="../../../HMS_BACKEND/images/' + id + '/' + img + '"class="custom-image"></td>' +
                      '<td>' + c_date + '</td>' +
                      '<td><button type="button" class="delete btn btn-danger" onclick="updateComplain(' + id + ')" > Acccept</button>  </td>' +
                      '</tr>';

                  $('#ComplaintsTable tbody').append(newRow);
              }
          }
      },
      error: function(xhr, status, error) {
          // Handle the error response
          console.log("Error:", error);
      }
  });
}




function getAllApprovedComplaints() {
  $.ajax({
      method: "GET",
      url: "http://localhost:8080/api/complaints/Aview",
      success: function(data, status) {
          console.log("Status:", status); // Print the status
          console.log("Data:", data); // Print the data
          // Clear existing table rows
          $('#ApprovedComplaintsTable tbody').empty();

          // Loop through the array and create table rows dynamically
          for (let i = 0; i < data.length; i++) {
              let complaint = data[i];
              let id = complaint.id;
              let roomNo = complaint.room_number; // Corrected property name
              let propertyType = complaint.property_type; // Corrected property name
              let property_id = complaint.property_uniq_id; // Corrected property name
              let description = complaint.description;
              let img = complaint.imageName;
              let snum=complaint.complainant;
              let c_date = complaint.complaint_date;
              let app=complaint.approved_date;
              let status = complaint.status;
            

              let newRow = '<tr>' +
                  '<td>' + id + '</td>' +
                  '<td>' + roomNo + '</td>' +
                  '<td>' + propertyType + '</td>' +
                  '<td>' + property_id + '</td>' +
                  '<td>' + snum + '</td>' +
                  '<td>' + description + '</td>' +
                  '<td><img src="../../../HMS_BACKEND/images/' + id + '/' + img + '"></td>' +
                  '<td>' + c_date + '</td>' +
                  '<td>' + app+ '</td>' +
                  '<td><button type="button" class="update btn btn-success" onclick="updateComplain(' + id + ')" >' + status + '</button>  </td>' +
        
                  '</tr>';

              $('#ApprovedComplaintsTable tbody').append(newRow);
          }
      },
      error: function(xhr, status, error) {
          // Handle the error response
          console.log("Error:", error);
      }
  });
}









function updateComplain(id) {
  let cid = id
  let status = "Approved";
  let date = getCurrentDate();
  
 

  $.ajax({
    method: "PUT",
    contentType: "application/json",
    url: "http://localhost:8080/api/complaints/update/" + cid,
    async: true,
    data: JSON.stringify({
      "id": cid,
      "status": status,
      "approved_date": date,
     
    }),
    success: function (data) {



      Swal.fire('Approved!', 'The complain is Approved .', 'success')
          .then(() => {
            window.location.href = "subwarden_complain_Approvedview.html"; // Removed extra ".html"
          });
     
           
      
    
    },
    error: function (xhr, exception) {
      alert("Error");
    }
  });
}






