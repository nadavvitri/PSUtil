package main;

import exceptions.InvalidColumnException;
import tree.PsTree;

public class PsUtil {

    private PsTree psTree;

    public PsUtil(String fileName){
        this.psTree = new PsTree(fileName);
    }

    public static void main(String[] args) {
        String hostName = System.getProperty("user.home");
        PsUtil psUtil = new PsUtil(hostName + "/PsUtil/src/ps.txt");
        try {
            psUtil.psTree.generateTree();
            System.out.println(psUtil.psTree.grep("Applications"));

        }
        catch (InvalidColumnException e){
            System.err.println(e.getMessage());
        }
    }
}
