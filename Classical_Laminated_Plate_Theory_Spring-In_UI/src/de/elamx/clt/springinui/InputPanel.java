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
package de.elamx.clt.springinui;

import de.elamx.clt.springin.SpringInInput;
import de.elamx.clt.springin.SpringInModel;
import de.elamx.core.GlobalProperties;
import de.elamx.utilities.BoundsPopupMenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.NumberFormatter;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author Andreas Hauffe
 */
public class InputPanel extends javax.swing.JPanel implements ItemListener, PropertyChangeListener {
    
    private static final DecimalFormat hygroFormat  = GlobalProperties.getDefault().getFormat(GlobalProperties.FORMAT_HYGROTHERMCOEFF);
    private static final DecimalFormat tempFormat   = GlobalProperties.getDefault().getFormat(GlobalProperties.FORMAT_TEMPERATURE);
    private static final DecimalFormat radiusFormat = GlobalProperties.getDefault().getFormat(GlobalProperties.FORMAT_THICKNESS);
    private static final DecimalFormat angleFormat  = GlobalProperties.getDefault().getFormat(GlobalProperties.FORMAT_ANGLE);
    
    private final SpringInInput input;
    
    private static String[] modelNames;
    private final SpringInModel[] springInModels;
    private final DegActionListener degActionListener = new DegActionListener();
    
    public InputPanel(){
        this(null);
    }
    
    /**
     * Creates new form InputPanel
     */
    public InputPanel(SpringInModuleData data) {
        Collection<? extends SpringInModel> c = Lookup.getDefault().lookupAll(SpringInModel.class);
        springInModels = new SpringInModel[c.size()];
        modelNames = new String[c.size()];
        int ii = 0;
        for (SpringInModel cg : c) {
            springInModels[ii] = cg;
            modelNames[ii] = cg.getDisplayName();
            ii++;
        }
        
        initComponents();
        
        input   = data != null ? data.getSpringInInput() : new SpringInInput();
        
        // CutoutGeometry in das PropertySheet einfügen
        propertySheet1.setNodes(new Node[]{new SpringInModelNode(input.getModel())});
        modelComboBox.setSelectedItem(input.getModel().getDisplayName());
        modelComboBox.addItemListener(this);
        
        angleTextField.setValue(input.getAngle());
        radiusTextField.setValue(input.getRadius());
        alphaThickTextField.setValue(input.getAlphat_thick());
        baseTempTextField.setValue(input.getBaseTemp());
        hardTempTextField.setValue(input.getHardeningTemp());
        
        angleTextField.addPropertyChangeListener("value", this);
        radiusTextField.addPropertyChangeListener("value", this);
        alphaThickTextField.addPropertyChangeListener("value", this);
        baseTempTextField.addPropertyChangeListener("value", this);
        hardTempTextField.addPropertyChangeListener("value", this);
        
        if (input.isZeroDegAsCircumDir()){
            zeroDegRadioButton.setSelected(true);
        }else{
            ninetyDegRadioButton.setSelected(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        directionGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        modelComboBox = new javax.swing.JComboBox<String>();
        BoundsPopupMenuListener listener = new BoundsPopupMenuListener(true, false);
        modelComboBox.addPopupMenuListener( listener );
        propertySheet1 = new org.openide.explorer.propertysheet.PropertySheet();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        alphaThickTextField = new javax.swing.JFormattedTextField();
        radiusTextField = new javax.swing.JFormattedTextField();
        angleTextField = new javax.swing.JFormattedTextField();
        baseTempTextField = new javax.swing.JFormattedTextField();
        hardTempTextField = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        alphaThickCheckBox = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        zeroDegRadioButton = new javax.swing.JRadioButton();
        zeroDegRadioButton.addActionListener(degActionListener);
        ninetyDegRadioButton = new javax.swing.JRadioButton();
        ninetyDegRadioButton.addActionListener(degActionListener);
        jLabel9 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel1.text")); // NOI18N

        modelComboBox.setModel(new DefaultComboBoxModel<String>(modelNames));

        propertySheet1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        propertySheet1.setDescriptionAreaVisible(true);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel4.text")); // NOI18N
        jLabel4.setToolTipText(org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel4.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel6.text")); // NOI18N

        alphaThickTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new NumberFormatter(hygroFormat)));
        alphaThickTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        alphaThickTextField.setText(org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.alphaThickTextField.text")); // NOI18N

        radiusTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new NumberFormatter(radiusFormat)));
        radiusTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        radiusTextField.setText(org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.radiusTextField.text")); // NOI18N

        angleTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new NumberFormatter(angleFormat)));
        angleTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        angleTextField.setText(org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.angleTextField.text")); // NOI18N

        baseTempTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new NumberFormatter(tempFormat)));
        baseTempTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        baseTempTextField.setText(org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.baseTempTextField.text")); // NOI18N

        hardTempTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new NumberFormatter(tempFormat)));
        hardTempTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        hardTempTextField.setText(org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.hardTempTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(alphaThickCheckBox, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.alphaThickCheckBox.text")); // NOI18N
        alphaThickCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.alphaThickCheckBox.toolTipText")); // NOI18N
        alphaThickCheckBox.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel8.text")); // NOI18N

        directionGroup.add(zeroDegRadioButton);
        zeroDegRadioButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(zeroDegRadioButton, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.zeroDegRadioButton.text")); // NOI18N

        directionGroup.add(ninetyDegRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(ninetyDegRadioButton, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.ninetyDegRadioButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(InputPanel.class, "InputPanel.jLabel9.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(propertySheet1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(modelComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(zeroDegRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(ninetyDegRadioButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(baseTempTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(alphaThickTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addComponent(radiusTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(hardTempTextField))
                            .addComponent(angleTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alphaThickCheckBox, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(modelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(propertySheet1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(angleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(radiusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alphaThickCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alphaThickTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(baseTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hardTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zeroDegRadioButton)
                    .addComponent(ninetyDegRadioButton)
                    .addComponent(jLabel8))
                .addGap(3, 3, 3)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox alphaThickCheckBox;
    private javax.swing.JFormattedTextField alphaThickTextField;
    private javax.swing.JFormattedTextField angleTextField;
    private javax.swing.JFormattedTextField baseTempTextField;
    private javax.swing.ButtonGroup directionGroup;
    private javax.swing.JFormattedTextField hardTempTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<String> modelComboBox;
    private javax.swing.JRadioButton ninetyDegRadioButton;
    private org.openide.explorer.propertysheet.PropertySheet propertySheet1;
    private javax.swing.JFormattedTextField radiusTextField;
    private javax.swing.JRadioButton zeroDegRadioButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void itemStateChanged(ItemEvent ie) {
        Object o = ie.getSource();
        if (o == modelComboBox) {modifiySpringInModelInput();}
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        Object o = pce.getSource();
        if      (o == alphaThickTextField) {input.setAlphat_thick(((Number) alphaThickTextField.getValue()).doubleValue());}
        else if (o == angleTextField) {input.setAngle(((Number) angleTextField.getValue()).doubleValue());}
        else if (o == baseTempTextField) {input.setBaseTemp(((Number) baseTempTextField.getValue()).doubleValue());}
        else if (o == hardTempTextField) {input.setHardeningTemp(((Number) hardTempTextField.getValue()).doubleValue());}
        else if (o == radiusTextField) {input.setRadius(((Number) radiusTextField.getValue()).doubleValue());}
    }
    
    private void modifiySpringInModelInput(){
        for (SpringInModel cg : springInModels){
            if (modelComboBox.getSelectedItem().equals(cg.getDisplayName())){
                propertySheet1.setNodes(new Node[]{new SpringInModelNode(cg)});
                input.setModel(cg);
                break;
            }
        }
    }
    
    private class DegActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            input.setZeroDegAsCircumDir(zeroDegRadioButton.isSelected());
        }
        
    }
}
