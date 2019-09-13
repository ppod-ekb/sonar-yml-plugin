package org.sonar.plugins.sensor;

import org.sonar.plugins.utils.BlitzFinder;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.LinkedList;
import java.util.Queue;

public final class YmlAnalyzer {

    private Node root;
    private final BlitzFinder blitzFinder;
    private boolean succ = false;

    public YmlAnalyzer(Node root) {
        this.root = root;
        blitzFinder = new BlitzFinder();
    }

    public void analyze(){
        this.root = findRoot();
        if(this.root != null) {
            traverseTree(this.root);
            succ = blitzFinder.isSuccessfull();
        }
    }

    private void traverseTree(Node node){
        if(node instanceof MappingNode) {
            for (NodeTuple tuple : ((MappingNode) node).getValue()) {
                if(blitzFinder.checkValue(((ScalarNode)tuple.getKeyNode()).getValue())){
                    System.out.println(((ScalarNode) tuple.getKeyNode()).getValue());
                    traverseTree(tuple.getValueNode());
                    blitzFinder.up();
                }
            }
        }
    }

    private Node findRoot() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        for (Node node = this.root; !queue.isEmpty(); node = queue.remove()) {
            if(node instanceof MappingNode) {
                for (NodeTuple tuple : ((MappingNode) node).getValue()) {
                    if(blitzFinder.isRoot(((ScalarNode)tuple.getKeyNode()).getValue())){
                        return tuple.getValueNode();
                    } else {
                        if(tuple.getValueNode() instanceof MappingNode) {
                            queue.add(tuple.getValueNode());
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean isBlitzConfigFinded(){
        return succ;
    }
}
