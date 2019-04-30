package main;

import exceptions.InvalidColumnException;
import tree.PSTree;

public class PSUtil {

    private PSTree psTree;

    public PSUtil(String fileName){
        this.psTree = new PSTree(fileName);
    }

    public static void main(String[] args) {
        String hostName = System.getProperty("user.home");
        PSUtil psUtil = new PSUtil(hostName + "/PSUtil/src/ps.txt");
        try {
            psUtil.psTree.generateTree();
            System.out.println(psUtil.psTree);
        }
        catch (InvalidColumnException e){
            System.err.println(e.getMessage());
        }
    }
}
