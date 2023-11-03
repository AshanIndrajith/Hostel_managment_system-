function saveComplain() {

    alert("hi")
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


