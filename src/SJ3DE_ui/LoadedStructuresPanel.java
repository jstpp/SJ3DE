package SJ3DE_ui;

import SJ3DE_environment.Space;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class LoadedStructuresPanel extends JPanel {
    public DefaultMutableTreeNode root = new DefaultMutableTreeNode("Project");
    public DefaultTreeModel treeModel = new DefaultTreeModel(root);

    public LoadedStructuresPanel(List<Space> objects) {
        // Header
        JLabel header = new JLabel("Import/Export");
        add(header);

        // Structures tree
        for (Space obj : objects) {
            root.add(new DefaultMutableTreeNode(obj));
        }
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
                JFileChooser j = new JFileChooser(new File(System.getProperty("user.home")), FileSystemView.getFileSystemView());
                j.showSaveDialog(null);

                String filename = j.getSelectedFile().getAbsolutePath();
                try {
                    FileInputStream fileOut = new FileInputStream(filename);
                    ObjectInputStream objOut = new ObjectInputStream(fileOut);
                    objects.clear();
                    while (true) {
                        try {
                            objects.add((Space) objOut.readObject());
                        } catch (EOFException err) {
                            break;
                        }
                    }
                    objOut.close();

                } catch (Exception ex) {
                    System.out.println("File not found: " + ex.getLocalizedMessage());
                } finally {
                    root.removeAllChildren();
                    for (Space obj : objects) {
                        root.add(new DefaultMutableTreeNode(obj));
                    }
                    treeModel.reload();
                    System.out.println("End.");
                }
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
        saveObjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    JFileChooser j = new JFileChooser(new File(System.getProperty("user.home")), FileSystemView.getFileSystemView());
                    j.showSaveDialog(null);

                    String filename = j.getSelectedFile().getAbsolutePath();
                    try {
                        FileOutputStream fileOut = new FileOutputStream(filename);
                        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                        for(Space spc : objects)
                        {
                            objOut.writeObject(spc);
                        }
                        objOut.close();

                    } catch (Exception ex) {
                        System.out.println("File not found: " + ex.getLocalizedMessage());
                    }
                    System.out.println(filename);
            }
        });

        buttons.add(addObjectButton);
        buttons.add(removeObjectButton);
        buttons.add(saveObjectButton);
        add(buttons);
    }
}
