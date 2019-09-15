import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            arrayNodes.forEach(node -> nodeList.add(parseNode((JSONObject) node)));
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
        JSONArray jsonNodes = (JSONArray) obj.get("nodes");
        Map<String, Long> nodes = new HashMap<>();
        String nameCon;
        Long weight;
        for (Object objNode : jsonNodes) {
            JSONObject jsonObject = (JSONObject) objNode;
            nameCon = (String) jsonObject.get("name");
            weight = (Long) jsonObject.get("weight");
            nodes.put(nameCon, weight);
        }
        return new Node(name, nodes);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "path='" + path + '\'' +
                ", nodeList=" + nodeList +
                '}';
    }
}
