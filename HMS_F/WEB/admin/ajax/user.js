
getAllStudent()


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