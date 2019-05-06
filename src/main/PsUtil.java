package main;

import exceptions.InvalidColumnException;
import tree.PsTree;

import java.io.IOException;

public class PsUtil {

    private PsTree psTree;

    public PsUtil(){
        try {
            Runtime r = Runtime.getRuntime();

            String hostName = System.getProperty("user.home");
            String command = "ps -ef > ";
            String fileName = hostName + "/PsUtil/src/ps.txt";

            Process p = r.exec(command + fileName);
            this.psTree = new PsTree(fileName);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public PsUtil(String fileName){
        this.psTree = new PsTree(fileName);
    }

    public static void main(String[] args) {
        String hostName = System.getProperty("user.home");
        PsUtil psUtil = new PsUtil();
        try {
            psUtil.psTree.generateTree();
            System.out.println(psUtil.psTree.grep("Applications"));

        }
        catch (InvalidColumnException e){
            System.err.println(e.getMessage());
        }
    }
}
