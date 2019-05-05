package tree;

import java.util.ArrayList;

public class PsTreeNode {

    private String uid, pid, ppid, c, stime, tty, time, cmd;
    private PsTreeNode parent = null;
    private ArrayList<PsTreeNode> childes = new ArrayList<>();

    public PsTreeNode(String uid, String pid, String ppid, String c, String sTime, String tty, String time, String cmd){
        this.uid = uid;
        this.pid = pid;
        this.ppid = ppid;
        this.c = c;
        this.stime = sTime;
        this.tty = tty;
        this.time = time;
        this.cmd = cmd;
    }

    public String getPid() {
        return this.pid;
    }

    public String getPpid() {
        return this.ppid;
    }

    public void addChild(PsTreeNode node){
        this.childes.add(node);
    }

    public void setParent(PsTreeNode parent){
        this.parent = parent;
    }

    public PsTreeNode getParent(){
        return this.parent;
    }

    public ArrayList<PsTreeNode> getChildes() {
        return this.childes;
    }

    public String toString(){
        return "pid: " + this.pid + ", ppid: " + this.ppid + ", cmd: " + this.cmd + "\n";
    }
}
