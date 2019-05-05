package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import exceptions.*;

public class PsParser {

    private BufferedReader reader;
    private Map<String, Integer> headerMap = new HashMap<>();
    private String[] values;

    public PsParser(String fileName){
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

    public String getValue(String columnName) throws InvalidColumnException{
        Integer index;
        if ((index = this.headerMap.get(columnName)) != null){
            return this.values[index];
        }
        throw new InvalidColumnException(columnName);
    }
}
