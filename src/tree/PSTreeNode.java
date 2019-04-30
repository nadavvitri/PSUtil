package tree;

import java.util.ArrayList;

public class PSTreeNode {

    private String uid, pid, ppid, c, stime, tty, time, cmd;
    private PSTreeNode parent = null;
    private ArrayList<PSTreeNode> childes = new ArrayList<>();

    public PSTreeNode(String uid, String pid, String ppid, String c, String sTime, String tty, String time, String cmd){
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

    public void addChild(PSTreeNode node){
        this.childes.add(node);
    }

    public void setParent(PSTreeNode parent){
        this.parent = parent;
    }

    public PSTreeNode getParent(){
        return this.parent;
    }

    public ArrayList<PSTreeNode> getChildes() {
        return this.childes;
    }

    public String toString(){
        return "pid: " + this.pid + ", ppid: " + this.ppid + ", cmd: " + this.cmd + "\n";
    }
}
