package tree;

import exceptions.InvalidColumnException;
import parser.PSParser;
import java.util.ArrayList;

public class PSTree {

    private ArrayList<PSTreeNode> roots = new ArrayList<>();
    private PSParser psParser;

    public PSTree(String fileName){
        this.psParser = new PSParser(fileName);
    }

    private PSTreeNode findParentInRoot(PSTreeNode root, String ppid){
        if (root == null){
            return null;
        }
        if (root.getPid().equals(ppid)){
            return root;
        }
        PSTreeNode node = null;
        for (PSTreeNode child: root.getChildes()) {
            findParentInRoot(child, ppid);
            if ((node = findParentInRoot(child, ppid)) != null){
                break;
            }
        }
        return node;
    }

    private PSTreeNode getParent(String ppid){
        PSTreeNode node = null;
        for (PSTreeNode root: this.roots) {
            if ((node = findParentInRoot(root, ppid)) != null){
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

    private String rootToString(PSTreeNode root, String tab){
        StringBuilder string = new StringBuilder();
        if (root != null){
            string.append(tab).append(root.toString());
            for (PSTreeNode child: root.getChildes()){
                String level = "----";
                string.append(rootToString(child, tab + level));
            }
        }
        return string.toString();
    }

    public String toString(){
        StringBuilder string = new StringBuilder();
        for (PSTreeNode root: this.roots){
            string.append(rootToString(root, ""));
        }
        return string.toString();
    }

}
