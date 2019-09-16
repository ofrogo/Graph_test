import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private String path = "/home/danil/Graph_test/src/main/resources/";

    private Map<String, Node> nodeList;

    Graph() {
        nodeList = new HashMap<>();
    }

    Graph(Map<String, Node> nodeList) {
        this.nodeList = new HashMap<String, Node>(nodeList);
    }

    private void addNode(String name, Map<String, Long> connections) {
        nodeList.put(name, new Node(name, connections));
    }

    private void addOneCon(String name_node1, String name_node2, Long weight) {
        nodeList.get(name_node1).addConnect(name_node2, weight);
    }

    private void addTwoCon(String name_node1, String name_node2, Long weight) {
        nodeList.get(name_node1).addConnect(name_node2, weight);
        nodeList.get(name_node2).addConnect(name_node1, weight);
    }

    private void deleteNode(String name_model) {
        nodeList.remove(name_model);
        for (Node node : nodeList.values()) {
            if (node.getNodes().containsKey(name_model)) {
                node.getNodes().remove(name_model);
            }
        }
    }

    private void deleteOneCon(String name_model1, String name_model2) throws Exception {
        nodeList.get(name_model1).deleteConnect(name_model2);
    }

    private void deleteTwoCon(String name_model1, String name_model2) throws Exception {
        nodeList.get(name_model1).deleteConnect(name_model2);
        nodeList.get(name_model2).deleteConnect(name_model1);
    }

    void saveGraphToFile(String fileName) {
        JSONArray array = new JSONArray();
        for (Node node : nodeList.values()) {
            array.add(node.toJSONObject());
        }
        try (FileWriter fileWriter = new FileWriter(path + fileName)) {
            fileWriter.write(array.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getGraphFromFile(String fileName) throws Exception {
        JSONParser jsonParser = new JSONParser();

        if (nodeList.size() != 0) {
            throw new Exception("Node list not empty!");
        }

        try (FileReader fileReader = new FileReader(path + fileName)) {
            JSONArray arrayNodes = (JSONArray) jsonParser.parse(fileReader);
            Node node;
            for (Object nodeObj : arrayNodes) {
                JSONObject jsonObject = (JSONObject) nodeObj;
                node = parseNode(jsonObject);
                nodeList.put(node.getName(), node);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private Node parseNode(@NotNull JSONObject obj) {
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
