getAllComplaints() 

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
