package is.ru.tictactoe;

import java.io.StringWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import static spark.Spark.*;
import spark.servlet.SparkApplication;
import org.json.simple.JSONObject;


public class TicTacToeWebApp implements SparkApplication {

   private TicTacToeLogic logic;
   String XImage = "/images/tic-tac-toe-X.png";
   String OImage = "/images/tic-tac-toe-O.png";
   boolean isInitialized = false;

   public static void main(String[] args) {
   		
   	  staticFileLocation("/public");
        SparkApplication ticWeb = new TicTacToeWebApp();

        String port = System.getenv("PORT");
        if (port != null) {
            port(Integer.valueOf(port));
        }

        ticWeb.init();
   }

   @Override
    public void init() {

      String layout = "templates/layout.vtl";

        get("/", (req, res) -> {
          logic = null;
          isInitialized = false;
          return getPage("index", new VelocityContext());
        });

        before("/play", (req, res) -> {
          String p1Name = req.queryParams("p1name");
          String p2Name = req.queryParams("p2name");

          if(p1Name == null || p1Name.equals("") || p2Name == null || p2Name.equals(""))
            res.redirect("/", 301);
        });

        get("/play", (req, res) -> {
          String p1Name = req.queryParams("p1name");
          String p2Name = req.queryParams("p2name");
          TicTacToePlayer p1 =  new TicTacToePlayer(p1Name);
          TicTacToePlayer p2 =  new TicTacToePlayer(p2Name);
          logic = new TicTacToeLogic(p1, p2);
          return getPage("game", getGameInfo());
         });

        post("/turn", "application/json",(req, res) -> {
          int gridIndex = Integer.parseInt(req.queryParams("index"));
          JSONObject obj = new JSONObject();
          obj.put("tokenImg", makeMove(gridIndex));
          obj.put("playerWhoHasTurn", logic.getPlayerWhoHasTurn().getName());
          obj.put("isGameOver", logic.isGameOver());
          obj.put("isDraw", logic.isDraw());
          obj.put("isWin", logic.isWin());
          obj.put("p1Score", logic.getPlayer1Score());
          obj.put("p2Score", logic.getPlayer2Score());
          obj.put("numberOfDraws", logic.getNumberOfDraws());
          return obj;
        });

        post("/newgame", "application/json", (req, res) -> {
          logic.newGame();
          JSONObject obj = new JSONObject();
          obj.put("playerWhoHasTurn", logic.getPlayerWhoHasTurn().getName());
          return obj;
        });
    }

    private String getPage(String pageName, VelocityContext context) {
      Template template = getTemplate(pageName);
      StringWriter writer = new StringWriter();
      
      template.merge(context, writer);
      context = new VelocityContext();
      context.put("template", writer.toString());


      template = getTemplate("layout");
      writer = new StringWriter();
      template.merge(context, writer);
      return writer.toString();
    }

    private VelocityContext getGameInfo() {
      VelocityContext context = new VelocityContext();
      getPlayerInfo(context);
      getGridInfo(context);
      return context;
    }

    private void getPlayerInfo(VelocityContext vc) {
      ArrayList playerInfo = new ArrayList();
      Map map = new HashMap();
      map.put("name", logic.getPlayer1Name());
      map.put("score", logic.getPlayer1Score());
      playerInfo.add(map);
      map = new HashMap();
      map.put("name", logic.getPlayer2Name());
      map.put("score", logic.getPlayer2Score());
      playerInfo.add(map);
      vc.put("playerInfo", playerInfo);
      
      ArrayList playerWhoHasTurn = new ArrayList();
      map = new HashMap();
      map.put("player", logic.getPlayerWhoHasTurn().getName());
      playerWhoHasTurn.add(map);
      vc.put("playerWhoHasTurn", playerWhoHasTurn);
    }

    private void getGridInfo(VelocityContext vc) {
      ArrayList gridInfo = new ArrayList();
      Character[] grid = logic.getGrid();
      Map map;

      for(int i = 0; i < grid.length; i += 3) {
         map = new HashMap();
         map.put("token1Img", getTokenImagePath(grid[i]));
         map.put("token2Img", getTokenImagePath(grid[i + 1]));
         map.put("token3Img", getTokenImagePath(grid[i + 2]));
         gridInfo.add(map);
      }

      vc.put("gridInfo", gridInfo);
    }

    private String getTokenImagePath(Character token) {
      if(token == null) return "";
      return (token == logic.getXToken()) ? XImage : OImage;
    }

    private Template getTemplate(String templateName) {
      VelocityEngine ve = new VelocityEngine();
      ve.init();
      return ve.getTemplate("./src/main/resources/templates/" + templateName + ".vm");
    }

    private String makeMove(int gridIndex) {

      try{

        Character token = logic.getPlayerWhoHasTurn().getToken();
        logic.insertNextTokenToGrid(gridIndex);
        return getTokenImagePath(token);

      }catch(RuntimeException e) {
         return "";
      }
    }
}