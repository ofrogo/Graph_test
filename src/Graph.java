import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
    public Graph() {
    }

    public void getGraphFromFile(){
        try (FileReader fileReader = new FileReader("graph.json")){

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
