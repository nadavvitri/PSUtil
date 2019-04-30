import exceptions.InvalidColumnException;
import java.util.ArrayList;

public class PSTree {

    private ArrayList<PSTreeNode> roots = new ArrayList<>();
    private final String fileName;
    private final String[] columnsNames = new String[] {"UID", "PID", "PPID", "C", "STIME", "TTY", "TIME", "CMD"};
    private PSParser psParser;

    public PSTree(String fileName){
        this.fileName = fileName;
        this.psParser = new PSParser(this.fileName);
    }

    private PSTreeNode findParentInRoot(PSTreeNode root, String ppid){
        if (root == null){
            return null;
        }
        if (root.getPid() == ppid){
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
        PSTreeNode parent = getParent(node.getPid());
        if (parent != null){
            node.setParent(parent);
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

    public static void main(String[] args) {

    }
}
