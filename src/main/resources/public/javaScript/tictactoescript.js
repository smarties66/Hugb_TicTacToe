$(document).ready(function() {

	$("#newgamebtn").attr({ "disabled" : true });

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
					$(nameTag).append(obj.winnerName);
					$("#playerwhohasturn").html("");
					var p = $("<p></p>");
					$(p).append(nameTag);
					$(p).append(" won!");
					$("#playerwhohasturn").append(p);
				}
				else{
					$("#playerwhohasturn").html("its a Draw!");
					$("#ndraws").html(obj.numberOfDraws);
				}

				$("#newgamebtn").removeAttr("disabled");	
			}
			else{
				$("#pturn").html(obj.playerWhoHasTurn);
			}
		});
	});

	$("#newgamebtn").on("click", function(){
		$.post("/newgame", function(data) {
			var obj = JSON.parse(data);
			$("#newgamebtn").attr({"disabled" : true});
			$(".square").html("");

			var nameTag = $('<span id="pturn"></span>');
			$(nameTag).append(obj.playerWhoHasTurn);
			$("#playerwhohasturn").html("");
			var p = $("<p></p>");
			$(p).append(nameTag);
			$(p).append(" make your move.");
			$("#playerwhohasturn").append(p);
		});
	});

});