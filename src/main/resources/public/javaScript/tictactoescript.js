$(document).ready(function() {

	$(".square").on("click", function(){
		var gridIndex = $(this).attr("id") - 1;
		var tokenSlot = $(this);

		$.post("/turn", { index : gridIndex }, function(data) {
			var obj = JSON.parse(data);
			var tokenImg = obj.tokenImg;

			if(tokenImg.length > 0)
				var img = $('<img alt="tokenImg"/>').attr({"src" : tokenImg});
				$(tokenSlot).append(img);


			if(obj.isGameOver == true){
				//Update Scores
				$("#player1 .pscore").html(obj.p1Score);
				$("#player2 .pscore").html(obj.p2Score);

				if(obj.isWin == true) {
					var nameTag = $('<span id="pturn"></span>');
					$(nameTag).append(getWinnerName());
					$("#playerwhohasturn").html("");
					var p = $("<p></p>");
					$(p).append(nameTag);
					$(p).append(" won!");
					$("#playerwhohasturn").append(p);
				}
			}
			else{
				$("#pturn").html(obj.playerWhoHasTurn);
			}
		});
	});


	function getWinnerName() {
		var p1Score = $("#player1 .pscore").html();
		var p2Score = $("#player2 .pscore").html();
		
		if(parseInt(p1Score) != parseInt(p2Score)) {
			if(parseInt(p1Score) > parseInt(p2Score)) 
				return $("#player1 .pname").text();

			return $("#player2 .pname").text();
		}

		return "";
	}
});