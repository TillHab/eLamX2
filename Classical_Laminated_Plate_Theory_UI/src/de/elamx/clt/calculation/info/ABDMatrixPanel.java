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
package de.elamx.clt.calculation.info;

import de.elamx.clt.CLT_Laminate;
import de.elamx.core.GlobalProperties;
import de.elamx.utilities.AutoRowHeightTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Andreas Hauffe
 */
public class ABDMatrixPanel extends javax.swing.JPanel {

    /**
     * Creates new form ABDMatrixPanel
     */
    public ABDMatrixPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DecimalFormat largeFormat = GlobalProperties.getDefault().getFormat(GlobalProperties.FORMAT_STIFFNESS);

        Double[][] mat = doubleArrayToDoubleArray(new double[6][6]);
        String[] colName = new String[mat[0].length];
        for (int ii = 0; ii < mat[0].length; ii++){
            colName[ii] = "";
        }
        String[][] captions = new String[][]{{"A", "B"}, {"B", "D"}};
        Color[][]  letterColors = new Color[][]{{new Color(255,200,200), new Color(200,255,200)},
            {new Color(200,255,200), new Color(200,200,255)}};
        Color[][]  bgColors     = new Color[][]{{new Color(255,170,170), new Color(170,255,170)},
            {new Color(170,255,170), new Color(170,170,255)}};
        ABDTable = new MatrixTable(mat,
            colName,
            captions,
            letterColors,
            bgColors,
            largeFormat);

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ABDMatrixPanel.class, "ABDMatrixPanel.border.title"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ABDTable, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ABDTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ABDTable;
    // End of variables declaration//GEN-END:variables

    public void setValues(CLT_Laminate laminate) {
        ((MatrixTable)ABDTable).setMatrix(doubleArrayToDoubleArray(laminate.getABDMatrix()));
    }

    private Double[][] doubleArrayToDoubleArray(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        Double[][] matDou = new Double[m][n];
        for (int ii = 0; ii < m; ii++) {
            for (int jj = 0; jj < n; jj++) {
                matDou[ii][jj] = matrix[ii][jj];
            }
        }
        return matDou;
    }

    private class MatrixTable extends AutoRowHeightTable implements FocusListener {

        private String[][] letter_ = null;
        private Color[][] letterColor_ = null;
        private Color[][] backgroundColor_ = null;
        private DecimalFormat nf_ = null;
        private Object[] columnNames = null;

        public MatrixTable(final Object[][] rowData, final Object[] columnNames, String[][] letter, Color[][] letterColor, Color[][] backgroundColor, DecimalFormat nf) {
            super(new DefaultTableModel(rowData, columnNames) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return rowData[0][columnIndex].getClass();
                }
            });
            addFocusListener(this);
            letter_ = letter;
            letterColor_ = letterColor;
            backgroundColor_ = backgroundColor;
            this.columnNames = columnNames;
            nf_ = nf;
            setDefaultRenderer(Object.class, new ObjectRenderer());
            setDefaultRenderer(Number.class, new NumberRenderer());
            setDefaultRenderer(Double.class, new DoubleRenderer());
            setOpaque(false);
            setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            setCellSelectionEnabled(true);
        }

        public void setMatrix(final Object[][] mat) {
            setModel(new DefaultTableModel(mat, columnNames) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return mat[0][columnIndex].getClass();
                }
            });
        }

        @Override
        public boolean isCellEditable(int a, int c) {
            return false;
        }

        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            // We want renderer component to be transparent so background image is visible
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(false);
            }
            return c;
        }

        @Override
        public void paint(Graphics g) {
            int m = letter_.length;
            int n = letter_[0].length;

            int[] width = new int[n];
            for (int jj = 0; jj < n; jj++) {
                for (int c = 0; c < 3; c++) {
                    width[jj] += getColumnModel().getColumn(c + jj * 3).getWidth();
                }
            }

            int[] height = new int[m];
            for (int ii = 0; ii < m; ii++) {
                height[ii] = 3 * this.getRowHeight();
            }

            int[] startx = new int[n];
            startx[0] = 0;
            for (int jj = 1; jj < n; jj++) {
                startx[jj] = startx[jj - 1] + width[jj - 1];
            }

            int[] starty = new int[m];
            starty[0] = 0;
            for (int ii = 1; ii < m; ii++) {
                starty[ii] = starty[ii - 1] + height[ii - 1];
            }

            for (int ii = 0; ii < m; ii++) {
                for (int jj = 0; jj < n; jj++) {
                    g.setColor(backgroundColor_[ii][jj]);
                    g.fillRect(startx[jj], starty[ii], width[jj], height[ii]);

                    g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), 60));
                    g.setColor(letterColor_[ii][jj]);
                    Rectangle2D bounds = g.getFontMetrics().getStringBounds(letter_[ii][jj], g);
                    g.drawString(letter_[ii][jj], startx[jj] + width[jj] / 2 - (int) bounds.getWidth() / 2, starty[ii] + height[ii] - 3); //- (int)bounds.getHeight()/2+15
                }
            }
            super.paint(g);
        }

        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            this.clearSelection();
        }

        /**
         * Default Renderers
         *
         */
        class NumberRenderer extends ObjectRenderer {

            public NumberRenderer() {
                super();
                setHorizontalAlignment(JLabel.RIGHT);
            }
        }

        class DoubleRenderer extends NumberRenderer {

            public DoubleRenderer() {
                super();
            }

            @Override
            public void setValue(Object value) {
                if (value == null) {
                    setText("");
                    return;
                }
                setText(nf_.format(value));
            }
        }

        class ObjectRenderer extends DefaultTableCellRenderer.UIResource {

            boolean isSelected = false;
            Color selectionColor;
            Color bgColor = new Color(255, 255, 255, 0);

            {
                // we'll use a translucent version of the table's default
                // selection color to paint selections
                Color oldCol = MatrixTable.this.getSelectionBackground();
                selectionColor = new Color(oldCol.getRed(), oldCol.getGreen(), oldCol.getBlue(), 128);

                // need to be non-opaque since we'll be translucent
                setOpaque(false);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                // save the selected state since we'll need it when painting
                this.isSelected = isSelected;
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }

            // since DefaultTableCellRenderer is really just a JLabel, we can override
            // paintComponent to paint the translucent selection when necessary
            @Override
            public void paintComponent(Graphics g) {
                if (isSelected) {
                    g.setColor(selectionColor);
                } else {
                    g.setColor(bgColor);
                }
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
    }
}
