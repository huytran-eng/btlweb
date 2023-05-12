
$(document).ready(function(){
   $("#myInput").keyup(function(){
        var search = $(this).val();
        console.log(search);
        $.ajax({
            url : "http://localhost:8080/api/tag/search",
            type: 'GET',
            dataType: 'json',
            data: {
                input:search
            }
        }).done(function (data) {
           $('#dropdown').empty()
             $('#dropdown').append('<option>',{
                value:null,
                text:"chon"
             })

          $.each(data, function (i, item) {
             $('#dropdown').append($('<option>', {
                value:JSON.stringify( item),
                text : item.tagName
             }));
          });
        });
   });

});


function selectOption(){
       var x = JSON.parse(document.getElementById("dropdown").value);
       console.log(x)
       const node = document.createElement("a");
       node.setAttribute("class","mr-1 bg-light small rounded p-1 text-info")
       node.href="/question/tag/" +x.tagName
       node.innerText=x.tagName
       document.getElementById("tags").append(node)

       var input = document.createElement("input");

        input.setAttribute("type", "hidden");

        input.setAttribute("name", "tags");
        input.setAttribute("value", x.tagId);
        input.setAttribute("id", x.tagId);


        input.setAttribute("th:field", x.tagId);

        //append to form element that you want .
        document.getElementById("tags").appendChild(input);

   }
