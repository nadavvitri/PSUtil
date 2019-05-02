package tree;

import exceptions.InvalidColumnException;
import parser.PSParser;
import java.util.ArrayList;
import java.util.function.Function;

public class PSTree {

    private ArrayList<PSTreeNode> roots = new ArrayList<>();
    private PSParser psParser;

    public PSTree(String fileName){
        this.psParser = new PSParser(fileName);
    }

    private PSTreeNode dfs(PSTreeNode root, Function<PSTreeNode, String> method, String valueToSearch){
        if (root == null){
            return null;
        }
        if (method.apply(root).equals(valueToSearch)){
            return root;
        }
        PSTreeNode node = null;
        for (PSTreeNode child: root.getChildes()) {
            if ((node = dfs(child, method, valueToSearch)) != null){
                break;
            }
        }
        return node;
    }

    private PSTreeNode getParent(String ppid){
        PSTreeNode node = null;
        for (PSTreeNode root: this.roots) {
            if ((node = dfs(root, PSTreeNode::getPid, ppid)) != null){
                break;
            }
        }
        return node;
    }

    private void addNode(PSTreeNode node){
        PSTreeNode parent = getParent(node.getPpid());
        if (parent != null){
            node.setParent(parent);
            parent.addChild(node);
        }
        else {
            this.roots.add(node);
        }
    }

    public void generateTree() throws InvalidColumnException {
        String line;
        PSTreeNode node;
        while ((line = this.psParser.getNextLine()) != null){
            psParser.parseLine(line);
            String UID = psParser.getValue("UID");
            String PID = psParser.getValue("PID");
            String PPID = psParser.getValue("PPID");
            String C = psParser.getValue("C");
            String STIME = psParser.getValue("STIME");
            String TTY = psParser.getValue("TTY");
            String TIME = psParser.getValue("TIME");
            String CMD = psParser.getValue("CMD");
            node = new PSTreeNode(UID, PID, PPID, C, STIME, TTY, TIME, CMD);
            addNode(node);
        }
    }

    private String printTreeByRoot(PSTreeNode root, String tab){
        StringBuilder string = new StringBuilder();
        if (root != null){
            string.append(tab).append(root.toString());
            for (PSTreeNode child: root.getChildes()){
                String level = "----";
                string.append(printTreeByRoot(child, tab + level));
            }
        }
        return string.toString();
    }

    public String toString(){
        StringBuilder string = new StringBuilder();
        for (PSTreeNode root: this.roots){
            string.append(printTreeByRoot(root, ""));
        }
        return string.toString();
    }

}
