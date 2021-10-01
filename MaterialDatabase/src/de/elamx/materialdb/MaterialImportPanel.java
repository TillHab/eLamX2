/*
 *  This program developed in Java is based on the netbeans platform and is used
 *  to design and to analyse composite structures by means of analytical and 
 *  numerical methods.
 * 
 *  Further information can be found here:
 *  http://www.elamx.de
 *    
 *  Copyright (C) 2021 Technische Universität Dresden - Andreas Hauffe
 * 
 *  This file is part of eLamX².
 *
 *  eLamX² is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  eLamX² is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with eLamX².  If not, see <http://www.gnu.org/licenses/>.
 */
package de.elamx.materialdb;

import de.elamx.laminate.Material;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.swing.outline.DefaultOutlineModel;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

/**
 *
 * @author Andreas Hauffe
 */
public class MaterialImportPanel extends javax.swing.JPanel implements ExplorerManager.Provider, PropertyChangeListener {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private Node[] selNodes;
    
    /**
     * Creates new form MaterialImportPanel
     */
    public MaterialImportPanel() {
        initComponents();
        initView();
    }

    private void initView() {
        outlineView1.setPropertyColumns(ExtendedDefaultMaterial.PROP_FIBRE_TYPE, NbBundle.getMessage(ExtendedDefaultMaterialNode.class, "ExtendedDefaultMaterialNode.Fibretype"),
                ExtendedDefaultMaterial.PROP_FIBRE_NAME, NbBundle.getMessage(ExtendedDefaultMaterialNode.class, "ExtendedDefaultMaterialNode.Fibrename"),
                ExtendedDefaultMaterial.PROP_MATRIX_TYPE, NbBundle.getMessage(ExtendedDefaultMaterialNode.class, "ExtendedDefaultMaterialNode.Matrixtype"),
                ExtendedDefaultMaterial.PROP_MATRIX_NAME, NbBundle.getMessage(ExtendedDefaultMaterialNode.class, "ExtendedDefaultMaterialNode.Matrixname"),
                ExtendedDefaultMaterial.PROP_PHI, NbBundle.getMessage(ExtendedDefaultMaterialNode.class, "ExtendedDefaultMaterialNode.Phi"),
                ExtendedDefaultMaterial.PROP_TYPE, NbBundle.getMessage(ExtendedDefaultMaterialNode.class, "ExtendedDefaultMaterialNode.Type"));
        ((DefaultOutlineModel) outlineView1.getOutline().getModel()).setNodesColumnLabel(NbBundle.getMessage(ExtendedDefaultMaterialNode.class, "ExtendedDefaultMaterialNode.Name"));
        outlineView1.getOutline().setRootVisible(false);
        outlineView1.getOutline().getColumnModel().getColumn(0).setPreferredWidth(200);
        outlineView1.getOutline().getColumnModel().getColumn(0).setMinWidth(200);
        explorerManager.addPropertyChangeListener(this);
    }
    
    public void setMaterials(ExtendedDefaultMaterial[] materials){
        AbstractNode root = new AbstractNode(Children.create(new ExtendedDefaultMaterialNodeFactory(materials), true));
        explorerManager.setRootContext(root);
        outlineView1.expandNode(root);
    }
    
    public Material[] getSelectedMaterials(){
        if (selNodes == null){
            return new Material[0];
        }
        Material[] materials = new Material[selNodes.length];
        for (int ii = 0; ii < selNodes.length; ii++){
            materials[ii] = selNodes[ii].getLookup().lookup(ExtendedDefaultMaterial.class);
        }
        return materials;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        outlineView1 = new org.openide.explorer.view.OutlineView();
        propertySheet1 = new org.openide.explorer.propertysheet.PropertySheet();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setDividerSize(6);
        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setLeftComponent(outlineView1);

        propertySheet1.setEnabled(false);
        propertySheet1.setPreferredSize(new java.awt.Dimension(300, 300));
        jSplitPane1.setRightComponent(propertySheet1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(MaterialImportPanel.class, "MaterialImportPanel.jLabel1.text")); // NOI18N
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private org.openide.explorer.view.OutlineView outlineView1;
    private org.openide.explorer.propertysheet.PropertySheet propertySheet1;
    // End of variables declaration//GEN-END:variables

    @Override
    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (opened && evt.getPropertyName().equals(ExplorerManager.PROP_SELECTED_NODES)){
            selNodes = explorerManager.getSelectedNodes();
            if (selNodes.length > 0){
                propertySheet1.setNodes(new Node[]{selNodes[selNodes.length-1]});
            }else{
                propertySheet1.setNodes(new Node[0]);
            }
        }
    }
    
    private boolean opened = true;

    @Override
    public void removeNotify() {
        opened = false;
        super.removeNotify(); //To change body of generated methods, choose Tools | Templates.
    }
}
