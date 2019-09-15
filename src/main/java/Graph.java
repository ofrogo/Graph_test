import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Graph {
    private String path = "/home/danil/Graph_test/src/main/resources/";

    private List<Node> nodeList;

    public Graph() {
        nodeList = new ArrayList<>();
    }

    public void getGraphFromFile(String fileName) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(path + fileName)) {
            JSONArray arrayNodes = (JSONArray) jsonParser.parse(fileReader);
            arrayNodes.forEach(node -> parseNode(node));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Node parseNode(JSONObject obj) {
        String name = (String) obj.get("name");
        JSONArray nodes = (JSONArray) obj.get("nodes");

    }

    private
}
