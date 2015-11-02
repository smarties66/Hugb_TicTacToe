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


public class TicTacToeWebApp implements SparkApplication {

   private TicTacToeLogic logic;

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
            return getPage("index", new VelocityContext());
         });

         get("/play", (req, res) -> {
            TicTacToePlayer p1 =  new TicTacToePlayer(req.queryParams("p1name"));
            TicTacToePlayer p2 =  new TicTacToePlayer(req.queryParams("p2name"));
            logic = new TicTacToeLogic(p1, p2);
            return getPage("game", getGameInfo());
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
      return (token == logic.getXToken()) ? "/images/tic-tac-toe-X.png" : "/images/tic-tac-toe-O.png";
    }

    private Template getTemplate(String templateName) {
      VelocityEngine ve = new VelocityEngine();
      ve.init();
      return ve.getTemplate("./src/main/resources/templates/" + templateName + ".vm");
    }
}