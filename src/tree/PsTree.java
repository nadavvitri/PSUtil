package tree;

import exceptions.InvalidColumnException;
import parser.PsParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PsTree {

    private ArrayList<PsTreeNode> roots = new ArrayList<>();
    private PsParser psParser;

    public PsTree(String fileName){
        this.psParser = new PsParser(fileName);
    }

    private PsTreeNode dfs(PsTreeNode root, Function<PsTreeNode, String> method, String valueToSearch){
        if (root == null){
            return null;
        }
        if (method.apply(root).equals(valueToSearch)){
            return root;
        }
        PsTreeNode node = null;
        for (PsTreeNode child: root.getChildes()) {
            if ((node = dfs(child, method, valueToSearch)) != null){
                break;
            }
        }
        return node;
    }

    private PsTreeNode getParent(String ppid){
        PsTreeNode node = null;
        for (PsTreeNode root: this.roots) {
            if ((node = dfs(root, PsTreeNode::getPid, ppid)) != null){
                break;
            }
        }
        return node;
    }

    private void addNode(PsTreeNode node){
        PsTreeNode parent = getParent(node.getPpid());
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
        PsTreeNode node;
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
            node = new PsTreeNode(UID, PID, PPID, C, STIME, TTY, TIME, CMD);
            addNode(node);
        }
    }

    private String colorSubString(String line, String subString){
        int from = line.indexOf(subString);
        int to = from + subString.length();
        String NORMAL_COLOR = "\033[0m";
        String RED_COLOR = "\033[31m";
        return line.substring(0, from) + RED_COLOR + line.substring(from, to) + NORMAL_COLOR + line.substring(to);
    }

    public String grep(String subString){
        return Arrays.stream(this.toString().split(System.lineSeparator()))
                .filter(line -> line.contains(subString))
                .map(line -> colorSubString(line, subString))
                .collect(Collectors.joining("\n"));
    }

    private String printTreeByRoot(PsTreeNode root, String tab){
        StringBuilder string = new StringBuilder();
        if (root != null){
            string.append(tab).append(root.toString());
            for (PsTreeNode child: root.getChildes()){
                String level = "----";
                string.append(printTreeByRoot(child, tab + level));
            }
        }
        return string.toString();
    }

    public String toString(){
        StringBuilder string = new StringBuilder();
        for (PsTreeNode root: this.roots){
            string.append(printTreeByRoot(root, ""));
        }
        return string.toString();
    }

}
