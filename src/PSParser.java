import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class PSParser{

    private BufferedReader reader;
    private Map<String, Integer> headerMap = new HashMap<>();
    private String[] values;

    public PSParser(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            reader = new BufferedReader(fileReader);
            this.mapHeader(this.getNextLine().trim());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void mapHeader(String header){
        String[] headerParts = header.split("\\s+");
        for (Integer i = 0; i < headerParts.length; i++) {
            this.headerMap.put(headerParts[i], i);
        }
    }

    public String getNextLine(){
        String line = null;
        try {
            line = this.reader.readLine();
            if (line == null){
                this.reader.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return line;
    }

    public void parseLine(String line){
        this.values = line.trim().split("\\s+");
    }

    public String getValue(String columnName) throws IllegalArgumentException{
        Integer index;
        if ((index = this.headerMap.get(columnName)) != null){
            return this.values[index];
        }
        throw new IllegalArgumentException(columnName + " not found!");
    }

    public static void main(String[] args) {
        String hostName = System.getProperty("user.home");
        PSParser psParser = new PSParser(hostName + "/PSUtil/src/ps.txt");
        String line = psParser.getNextLine();
        psParser.parseLine(line);
        System.out.println(psParser.getValue("CMD"));
    }
}
