<%--
  Created by IntelliJ IDEA.
  User: jakli
  Date: 2025-05-09
  Time: 4:27 PM
  To change this template use File | Settings | File Templates.
--%>
<style>
    .checked{
        color: orange;
    }
    .card-title{
        display: flex;
        justify-content: center;
        font-weight: bold;
    }
    .card-body{
        display: flex;
        justify-content: center;
        text-align: justify;
    }
</style>

<section class="offers-page" id="offersPage">
    <div class="container">
        <h2 class="section-title">Feedbacks</h2>
        <div class="no-offers text-danger text-center h3" id ='noRatings' >
            No Ratings Found
        </div>
        <div class="offer-grid" id ='ratingsGrid'>


        </div>
    </div>
</section>

<script>
    $( document ).ready(function() {
        onStart();
        setInterval( ()=> {
            onStart();
        }, 5000);
    });
    let start = 0;
    let limit = 3;
    function onStart(){
        console.log('start'+start);
        $.ajax({
            url: '<%=request.getContextPath()+"/feedback/get"%>', // Sample API
            type: 'Get',
            data: {
                start:start+"",
                count:limit+""
            },
            success: function(response) {
                console.log(response);
                console.log(JSON.stringify(response));
                let data = response.feedbacks;
                let div = document.getElementById("ratingsGrid");
                div.innerHTML = "";
                if(data.length<1){
                    $("#noRatings").show();
                    $("#ratingsGrid").hide();
                }else{
                    $("#noRatings").hide();
                    $("#ratingsGrid").show();
                    start= response.nextStart;
                    for(let i=0;i<data.length;i++){
                        div.appendChild(createElement(data[i]));
                    }
                }

            },
            error: function(xhr, status, error) {
                console.log(JSON.stringify(error));
                $("#noRatings").show();
                $("#ratingsGrid").hide();
            }
        });
    }



    function createElement(data) {
        const card = document.createElement("div");
        card.className = "offer-card";
        let html = "<div class='offer-card-header'>";
            for(let i=0;i<data.rating;i++){
                html += "<span class='fa fa-star checked'></span>";
            }
            for(let i=data.rating;i<5;i++){
                html += "<span class='fa fa-star'></span>";
            }
            html+="</div>"+
            "<div class='offer-card-body'>"+
            "<div class='card-title'>";
        html+=data.createdByFullUser.firstName+" "+data.createdByFullUser.lastName;
        html+="</div>"+
            "<div class='card-body' title='"+data.comment+"'>";
        html+=data.shortComment;
            html+=    "</div>"+
            "</div>";
        card.innerHTML=html;
        return card;
    }

</script>


