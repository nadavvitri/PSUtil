import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class PSTree {

    private BufferedReader reader;
    private Map<String, Integer> headerMap;

    public PSTree(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            reader = new BufferedReader(fileReader);
            this.mapHeader(this.getNextLine());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void mapHeader(String header){
        String[] headerParts = header.split(" ");
        for (int i = 0; i < headerParts.length; i++) {
            this.headerMap.put(headerParts[i], i);
        }
    }

    public String getNextLine(){
        String line = null;
        try {
            line = this.reader.readLine();
            if (line == null){
                reader.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return line;
    }

    public void parseLine(String line){

    }
}
