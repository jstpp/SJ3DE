package SJ3DE_ui;

import SJ3DE_engine.Engine;
import SJ3DE_environment.Space;
import SJ3DE_lidar.PCloud;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoadedStructuresPanel extends JPanel {
    public static DefaultMutableTreeNode root = new DefaultMutableTreeNode("Project");
    public static DefaultTreeModel treeModel = new DefaultTreeModel(root);
    public static Engine parent_engine;

    public LoadedStructuresPanel(Engine engine) {
        parent_engine = engine;
        // Layout
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));

        // Header
        JLabel header = new JLabel("Import/Export");
        add(header, BorderLayout.NORTH);

        // Structures tree
        for (Space obj : engine.objects) {
            root.add(new DefaultMutableTreeNode(obj));
        }
        JTree structures_tree = new JTree(treeModel);
        structures_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        add(structures_tree, BorderLayout.CENTER);
        repaint();

        // Modify objects
        JPanel buttons = new JPanel();
        JButton addLAZObjectButton = new JButton("Add new LiDAR points cloud");
        JButton addObjectButton = new JButton("Add new objects");
        JButton removeObjectButton = new JButton("Remove selected node");
        JButton saveObjectButton = new JButton("Save all");

        addLAZObjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser j = new JFileChooser(new File(System.getProperty("user.home")), FileSystemView.getFileSystemView());
                j.showSaveDialog(null);

                String filename = j.getSelectedFile().getAbsolutePath();
                try {
                    Space obj = new PCloud(new SJ3DE_environment.Point(engine.camera.camera_x, engine.camera.camera_y, engine.camera.camera_z), filename, 0.002f);
                    engine.objects.add(obj);
                    root.add(new DefaultMutableTreeNode(obj));
                    treeModel.reload();
                } catch (Exception ex) {
                    System.out.println("File not found: " + ex.getLocalizedMessage());
                }
            }
        });
        addObjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser j = new JFileChooser(new File(System.getProperty("user.home")), FileSystemView.getFileSystemView());
                j.showSaveDialog(null);

                String filename = j.getSelectedFile().getAbsolutePath();
                try {
                    FileInputStream fileOut = new FileInputStream(filename);
                    ObjectInputStream objOut = new ObjectInputStream(fileOut);
                    while (true) {
                        try {
                            Space obj = (Space) objOut.readObject();
                            engine.objects.add(obj);
                            root.add(new DefaultMutableTreeNode(obj));
                        } catch (EOFException err) {
                            break;
                        }
                    }
                    objOut.close();
                    treeModel.reload();


                } catch (Exception ex) {
                    System.out.println("File not found: " + ex.getLocalizedMessage());
                }
            }
        });
        removeObjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreePath[] selectedPaths = structures_tree.getSelectionPaths();

                if(selectedPaths!=null) {
                    for(TreePath path : selectedPaths) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

                        if (selectedNode != null && selectedNode.getParent() != null) {
                            Object userObject = selectedNode.getUserObject();
                            if (userObject instanceof Space) {
                                engine.objects.remove(userObject);
                            }

                            treeModel.removeNodeFromParent(selectedNode);
                        }
                    }
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
                        for(Space spc : engine.objects)
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

        buttons.add(addLAZObjectButton);
        buttons.add(addObjectButton);
        buttons.add(removeObjectButton);
        buttons.add(saveObjectButton);
        add(buttons, BorderLayout.SOUTH);
    }

    public static void update() {
        root.removeAllChildren();
        for (Space obj : parent_engine.objects) {
            root.add(new DefaultMutableTreeNode(obj));
        }
        treeModel.reload();
    }
}
