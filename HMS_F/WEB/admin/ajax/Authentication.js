


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








function saveStudent() {
  
    var index = $('#index').val();
    var fname = $('#fname').val();
    var lname = $('#lname').val();
    var email = $('#email').val();
    var password = $('#password').val();
    var role = 'STUDENT'; // Modify as needed

    // Send AJAX request
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/auth/register",
        async: true,
        data: JSON.stringify({
            "enabled": true,
            "fname": fname,
            "lname": lname,
            "password": password,
            "email": email,
            "role": role,
            "user_index": index
        }),
        success: function (data) {
           
             alert("saved");
          
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






function saveWarden() {

    alert('hello')
  
    var index = $('#index').val();
    var fname = $('#fname').val();
    var lname = $('#lname').val();
    var email = $('#email').val();
    var password = $('#password').val();
    var role = 'WARDEN'; // Modify as needed
    alert(index)

    // Send AJAX request
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/auth/register",
        async: true,
        data: JSON.stringify({
            "enabled": true,
            "fname": fname,
            "lname": lname,
            "password": password,
            "email": email,
            "role": role,
            "user_index": index
        }),
        success: function (data) {
           
             alert("saved");
          
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
        case 'PURCHASE_COORDINATOR':
            window.location.href = '/purchase-coordinator-panel';
            break;
        // Add more cases for other roles as needed
        default:
            // Redirect to a default page for unknown roles or handle it as per your requirements
            console.log(userRole);
    }
}






function getAllStudent() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/user/students", // Update with your actual URL
        success: function (data, status) {
            console.log("Status:", status);
            console.log("Data:", data);

            // Get the table body to populate data
            var tableBody = $('#UserTable tbody');
            tableBody.empty(); // Clear existing table rows

            // Loop through the data and create table rows
            for (var i = 0; i < data.length; i++) {
                var user = data[i];
                var id = user[0];
                var userIndex = user[1];
                var firstName = user[2];
                var lastName = user[3];
                var email = user[4];

                var newRow = '<tr>' +
                    '<td>' + id + '</td>' +
                    '<td>' + userIndex + '</td>' +
                    '<td>' + firstName + '</td>' +
                    '<td>' + lastName + '</td>' +
                    '<td>' + email + '</td>' +
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

// Call the function to load data

