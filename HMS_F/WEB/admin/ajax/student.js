getAllWarden() 




function getAllWarden() {
  $.ajax({
      method: "GET",
      url: "http://localhost:8080/api/user/students", // Update with your actual URL
      success: function (data, status) {
          console.log("Status:", status);
          console.log("Data:", data);

          // Get the table body to populate data
          var tableBody = $('#wardenTable tbody');
          tableBody.empty(); // Clear existing table rows

          // Loop through the data and create table rows
          for (var i = 0; i < data.length; i++) {
              var user = data[i];
              var id = user[0];
              var userIndex = user[1];
              var firstName = user[2];
              var lastName = user[3];
              var email = user[5];

              var newRow = '<tr>' +
                  '<td>' + id + '</td>' +
                  '<td>' + userIndex + '</td>' +
                  '<td>' + firstName + '</td>' +
                  '<td>' + lastName + '</td>' +
                  '<td>' + email + '</td>' +
                  '<td><button type="button" class="update btn btn-success" onclick="getStudentrDetails(' + id + ')" >Update</button> <button type="button" onclick="deleteStudent(' + id + ') " class="delete btn btn-danger">Delete</button></td>' +
                 
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



function getStudentrDetails(id) {


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














  function UpdateStudent() {

   
  
    var id = $('#id').val();
    var index = $('#index').val();
    var fname = $('#fname').val();
    var lname = $('#lname').val();
    var email = $('#email').val();

  
 

    // Send AJAX request
    $.ajax({
        method: "PUT",
        contentType: "application/json",
        url: "http://localhost:8080/api/user/update/" + id,
        async: true,
        data: JSON.stringify({
            "enabled": true,
            "fname": fname,
            "lname": lname,
            "email": email,
            "user_index": index
        }),
        success: function (data) {
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
            window.location.href = 'View_student.html';
          }, 1000);

          
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




// function deleteStudent(empID){

//   alert("hi")
  
//   swal({
//    title: "Are you sure?",
//    text: "Permanently delete selected data ?",
//    icon: "warning",
//    buttons: true,
//    dangerMode: true,
//  })
//  .then((willDelete) => {


 
//    if (willDelete) {
     



//      swal(" User  details  are deleted!", {
//        icon: "success",
//      });
//    } else {
//      swal("Your details  is safe!");
//    }
//  });

 
  
 
//  }


function deleteStudent(empID) {
  Swal.fire({
    title: 'Are you sure?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, delete it!'
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        method: "DELETE",
        url: "http://localhost:8080/api/user/delete/" + empID,
        async: true,
        success: function(data) {
          
          window.location.href = "View_student.html"; // Removed extra ".html"
        },
        error: function(xhr, exception) {
          alert("Error");
        }
      });
    }
  });
}
