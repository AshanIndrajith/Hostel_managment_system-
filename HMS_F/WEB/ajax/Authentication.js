getUserProfile();


  
function saveEmployee() {
    var enabled = true; // Change as needed
    var fname = $('#fname').val();
    var lname = $('#lname').val();
    var password = $('#pass').val();
    var role = 'ADMIN'; // Change as needed
    var user_index = 'TG480'; // Change as needed

    var data = {
        "enabled": enabled,
        "fname": fname,
        "lname": lname,
        "password": password,
        "role": role,
        "user_index": user_index
    };

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/auth/register",
        async: true,
        data: JSON.stringify(data),
        success: function (data) {
            alert("Saved");
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








function LogEmployee() {
    function handleLoginSuccess(response) {
        if (response && response.token) {
            // Login successful, store the token
            localStorage.setItem('token', response.token);

            // Check if the token is available
            checkTokenAvailability();

            // Redirect to a dashboard page based on the user's role
            redirectToDashboard();
        } else {
            alert('Login failed. Please try again.');
        }
    }

    function handleLoginError(xhr, status, error) {
        if (xhr.status === 401) {
            alert('Login failed: Invalid credentials.');
        } else {
            alert('Login failed. Please try again later.');
        }
    }

    function checkTokenAvailability() {
        var token = localStorage.getItem('token');
        if (token) {
            console.log('Token is available:', token);
            // Decode the token if needed
            var decodedToken = parseJwt(token);
            console.log('Decoded Token:', decodedToken);
        }
    }

    function parseJwt(token) {
        try {
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace('-', '+').replace('_', '/');
            return JSON.parse(atob(base64));
        } catch (e) {
            return null;
        }
    }

    function redirectToDashboard() {
        // Your code for redirecting to the dashboard based on the user's role
        // ...
    }

    var username = $('#username').val();
    var password = $('#password').val();

    if (!username || !password) {
        alert('Please enter both username and password.');
        return;
    }

    var requestData = {
        email: username,
        password: password
    };

    $.ajax({
        url: 'http://localhost:8080/api/auth/authenticate',
        type: 'POST',
        data: JSON.stringify(requestData),
        contentType: 'application/json',
        success: handleLoginSuccess,
        error: handleLoginError
    });
}



function resetForm() {
    document.getElementById("fname").value = "";
    document.getElementById("lname").value = "";
    document.getElementById("email").value = "";
    document.getElementById("pass").value = "";
  }


  function resetFormLogin() {
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
  }













  function updateCustomer() {
    let username = $('#username').val();
    let password = $('#password').val();

    if (!username || !password) {
        alert('Please enter both username and password.');
        return;
    }

    // Prepare the data for the request
    var requestData = {
        user_index: username,
        password: password
    };

    // AJAX request
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/auth/authenticate",
        async: true,
        data: JSON.stringify(requestData),
        success: function(data) {
            if (data && data.token) {
                var token = data.token;
                console.log('Received Token:', token);
                localStorage.setItem('token', token);
                decodeJwt(token)
            


            } else {
                alert('Authentication succeeded, but no token received.');
            }
        },
        error: function(xhr, exception) {
            console.log('Error:', xhr.status, xhr.statusText);
            alert("Failed to update. See the console for more details.");
        }
    });
}





function decodeJwt(token) {
    // Split the token into its parts (header, payload, and signature)
    const parts = token.split('.');
    
    // Base64 decode the payload (second part)
    const base64Payload = parts[1];
    const decodedPayload = atob(base64Payload);
    
    // Parse the JSON data in the payload
    const payload = JSON.parse(decodedPayload);
    
    // Now you can access the claims, e.g., the role
    const userRole = payload.role;

    console.log('User Role:', userRole);
    redirectToPanel(userRole)

    return userRole;
}
function redirectToPanel(userRole) {
    // Check if userRole is an array
    if (Array.isArray(userRole)) {
        // If it's an array, select the first role
        userRole = userRole[0];
    }

    switch (userRole) {
        case 'ADMIN':
            window.location.href = '../admin/index.html';
            break;
        case 'STUDENT':
            window.location.href = '../admin/Student_index.html';
            break;

        case 'WARDEN':
            window.location.href = '../admin/Warden_Dasboard.html';
            break;

        case 'WARDEN':
            window.location.href = '../admin/subwarden.html';
            break;
        case 'WARDEN':
                window.location.href = '../admin/Dean_Dashboard.html';
                break;
        // Add more cases for other roles as needed
        default:
            // Redirect to a default page for unknown roles or handle it as per your requirements
            console.log(userRole);
    }
}




// function getUserProfile() {
//     const token = localStorage.getItem('token');
//     console.log(token)
//     const headers = {
//         'Authorization':`Bearer ${token}`
//     };
//     console.log(headers)

//     $.ajax({
//         method: 'GET',
//         url: 'http://localhost:8080/api/auth/userprofile',
//         headers: headers,
//         success: function(data) {
//             // Handle the successful response here
//             console.log('User profile data:', data);
//         },
//         error: function(xhr, status, error) {
//             // Handle errors here
//             console.error('Error loading user profile:', error);
//         }
//     });
// }



function getUserProfile() {
    const token = localStorage.getItem('token');

    if (!token) {
        console.error('No token found in localStorage.');
        return;
    }

    const headers = {
        'Authorization': `Bearer ${token}`
    };

    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/api/auth/UserProfile',
        headers: headers,
        success: function(data) {
            // Handle the successful response here
            console.log('User profile data:', data);
            console.log('User index:', data.user_index);
            document.getElementById('userIndex').textContent = data.user_index;
            document.getElementById('userIndex').value = data.user_index;
            const userIndex = data.user_index;

                resolve(userIndex);

            



        },
        error: function(xhr, status, error) {
            // Handle errors here
            console.error('Error loading user profile:', error);
        }
    });
}







// function getComplainByUid(uid) {

//     alert("hello")

//     alert(uid)
//     $.ajax({
//         method: "GET",
//         url: "http://localhost:8080/api/complaints/getcomplain?uid="+ uid,
//         success: function(data, status) {
//             console.log("Status:", status); // Print the status
//             console.log("Data:", data); // Print the data
//             // Clear existing table rows
//             $('#stComplain tbody').empty();
  
//             // Loop through the array and create table rows dynamically
//             for (let i = 0; i < data.length; i++) {
//                 let complaint = data[i];
//                 let id = complaint.id;
//                 let roomNo = complaint.room_number; // Corrected property name
//                 let propertyType = complaint.property_type; // Corrected property name
//                 let property_id = complaint.property_uniq_id; // Corrected property name
//                 let description = complaint.description;
//                 let img = complaint.imageName;
//                 let snum=complaint.complainant;
//                 let c_date = complaint.complaint_date;
//                 let app=complaint.approved_date;
//                 let status = complaint.status;
              
  
//                 let newRow = '<tr>' +
//                     '<td>' + id + '</td>' +
//                     '<td>' + roomNo + '</td>' +
//                     '<td>' + propertyType + '</td>' +
//                     '<td>' + property_id + '</td>' +
//                     '<td>' + snum + '</td>' +
//                     '<td>' + description + '</td>' +
//                     '<td><img src="../../../HMS_BACKEND/images/' + id + '/' + img + '"></td>' +
//                     '<td>' + c_date + '</td>' +
//                     '<td>' + app+ '</td>' +
//                     '<td><button type="button" class="update btn btn-success" onclick="updateComplain(' + id + ')" >' + status + '</button>  </td>' +
          
//                     '</tr>';
  
//                 $('#stComplain tbody').append(newRow);
//             }
//         },
//         error: function(xhr, status, error) {
//             // Handle the error response
//             console.log("Error:", error);
//         }
//     });
//   }






