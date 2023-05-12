
async function upvote(e){
        e.preventDefault();
        console.log("o day")

//         const newRating= await axios.put('/api/question/up/' +id )
//         rating.innerText=newRating.data;
          try {
             var question =  document.getElementById("cur-question");
             var id = question.getAttribute('data-question-id');
             var rating =  document.getElementById("rating");
             const newRating= await axios.put('/api/question/up/' +id );
             rating.innerText=newRating.data;
           } catch (error) {
             // handle error
           }

}

async function downvote(e){
         e.preventDefault();
         console.log("o day")

        //         const newRating= await axios.put('/api/question/up/' +id )
        //         rating.innerText=newRating.data;
        try {
                     var question =  document.getElementById("cur-question");
                     var id = question.getAttribute('data-question-id');
                     var rating =  document.getElementById("rating");
                     const newRating= await axios.put('/api/question/down/' +id );
                     rating.innerText=newRating.data;
        } catch (error) {
                     // handle error
        }

}