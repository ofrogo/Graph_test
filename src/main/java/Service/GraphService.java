package Service;

import Entity.Node;
import Graph.GraphAbstract;
import Graph.GraphDirectedUnweighted;
import Graph.GraphDirectedWeighted;
import Graph.GraphUndirectedUnweighted;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphService {
    static String path = "/home/danil/Graph_test/src/main/resources/";

    public static void saveGraphToFile(String fileName, GraphAbstract graph) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("directed", graph.getDirected());
        jsonObject.put("weighted", graph.getWeighted());
        JSONArray array = new JSONArray();
        for (Node node : graph.getNodeList().values()) {
            array.add(node.toJSONObject());
        }
        jsonObject.put("nodeList", array);
        try (FileWriter fileWriter = new FileWriter(path + fileName)) {
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GraphAbstract getGraphFromFile(String fileName) {
        JSONParser jsonParser = new JSONParser();
        GraphAbstract graph = null;
        try (FileReader fileReader = new FileReader(path + fileName)) {
            JSONObject object = (JSONObject) jsonParser.parse(fileReader);
            Boolean weighted = (Boolean) object.get("weighted");
            Boolean directed = (Boolean) object.get("directed");
            if (weighted && directed) {
                graph = new GraphDirectedWeighted();
            } else if (weighted && !directed) {
                graph = new GraphDirectedUnweighted();
            } else if (!weighted && directed) {
                graph = new GraphDirectedUnweighted();
            } else {
                graph = new GraphUndirectedUnweighted();
            }
            Node node;
            for (Object nodeObj : (JSONArray) object.get("nodeList")) {
                JSONObject jsonObject = (JSONObject) nodeObj;
                node = parseNode(jsonObject);
                graph.addNode(node.getName(), node.getNodes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    private static Node parseNode(@NotNull JSONObject obj) {
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
}
