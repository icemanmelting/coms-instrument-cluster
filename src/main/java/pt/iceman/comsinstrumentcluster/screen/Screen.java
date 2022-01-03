package pt.iceman.comsinstrumentcluster.screen;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 480;
    private List<CustomEntry<Node, AbsolutePositioning>> nodes;

    public Screen() {
        nodes = new ArrayList<>();
    }

    public List<CustomEntry<Node, AbsolutePositioning>> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<CustomEntry<Node, AbsolutePositioning>> nodes) {
        this.nodes = nodes;
    }
}