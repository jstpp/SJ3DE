package SJ3DE_ui;

import SJ3DE_environment.Space;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class LoadedStructuresPanel extends JPanel {

    public LoadedStructuresPanel(List<Space> objects) {
        // Header
        JLabel header = new JLabel("Import/Export");
        add(header);

        // Structures tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Project");
        for (Space obj : objects) {
            root.add(new DefaultMutableTreeNode(obj));
        }
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        JTree structures_tree = new JTree(treeModel);
        add(structures_tree);
        repaint();

        // Modify objects
        JPanel buttons = new JPanel();
        JButton addObjectButton = new JButton("Add new object to selected node");
        JButton removeObjectButton = new JButton("Remove selected node");
        JButton saveObjectButton = new JButton("Save selected node");
        addObjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser j = new JFileChooser(new File("/home/jstpp/Desktop"), FileSystemView.getFileSystemView());
                j.showSaveDialog(null);

                String filename = j.getSelectedFile().getAbsolutePath();
                System.out.println(filename);
            }
        });
        removeObjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode =
                        (DefaultMutableTreeNode) structures_tree.getLastSelectedPathComponent();

                if (selectedNode != null && selectedNode.getParent() != null) {
                    Object userObject = selectedNode.getUserObject();
                    if (userObject instanceof Space) {
                        objects.remove(userObject);
                    }

                    treeModel.removeNodeFromParent(selectedNode);
                }
            }
        });

        buttons.add(addObjectButton);
        buttons.add(removeObjectButton);
        buttons.add(saveObjectButton);
        add(buttons);
    }
}
