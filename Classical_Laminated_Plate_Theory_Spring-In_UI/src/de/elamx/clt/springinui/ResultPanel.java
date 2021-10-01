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

import de.elamx.clt.springin.SpringInResult;
import de.elamx.core.GlobalProperties;
import java.awt.BasicStroke;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import org.jfree.XYPlotI;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.eLamXChartTheme;
import org.jfree.eLamXNumberTickUnitSource;
import org.openide.util.NbBundle;

/**
 *
 * @author Andreas Hauffe
 */
public class ResultPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private SpringInModuleData data;
    private JFreeChart chart;
    private final XYSeries[] series = new XYSeries[2];
    private XYSeriesCollection seriesCol = new XYSeriesCollection();

    public ResultPanel() {
        this(null, null);
    }

    /**
     * Creates new form ResultPanel
     */
    public ResultPanel(SpringInModuleData data, JFreeChart chart) {
        this.data = data;
        this.chart = chart;

        if (data != null) {
            data.addPropertyChangeListener(SpringInModuleData.PROP_RESULT, this);
        }
        
        if (chart != null){
            createDataset();
        }

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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        deltaAngleLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ResultPanel.class, "ResultPanel.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ResultPanel.class, "ResultPanel.jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(org.openide.util.NbBundle.getMessage(ResultPanel.class, "ResultPanel.jLabel1.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ResultPanel.class, "ResultPanel.jLabel2.text")); // NOI18N

        deltaAngleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(deltaAngleLabel, org.openide.util.NbBundle.getMessage(ResultPanel.class, "ResultPanel.deltaAngleLabel.text")); // NOI18N

        angleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(angleLabel, org.openide.util.NbBundle.getMessage(ResultPanel.class, "ResultPanel.angleLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deltaAngleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(angleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deltaAngleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(angleLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel angleLabel;
    private javax.swing.JLabel deltaAngleLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        SpringInResult result = data.getResult();
        if (result != null) {
            updateResults();
            updateChart();
        }
    }

    private void updateResults() {
        DecimalFormat angleFormat = GlobalProperties.getDefault().getFormat(GlobalProperties.FORMAT_ANGLE);
        
        double deltaAngle = data.getResult().getDeltaAngle();
        double angle = data.getResult().getInput().getAngle();
        
        deltaAngleLabel.setText(angleFormat.format(deltaAngle));
        angleLabel.setText(angleFormat.format(angle+deltaAngle));
    }
    
    private void createDataset() {
        seriesCol = new XYSeriesCollection();
        series[0] = new XYSeries(NbBundle.getMessage(ResultPanel.class, "ResultPanel.chart.undeformedGeometry"),false);
        series[1] = new XYSeries(NbBundle.getMessage(ResultPanel.class, "ResultPanel.chart.deformedGeometry"),false);
        for (XYSeries s : series) {
            seriesCol.addSeries(s);
        }

        ((XYPlotI) chart.getXYPlot()).useQuadraticDataArea(false);
        ((XYPlotI) chart.getXYPlot()).useEqualAxes(true);
        ((XYPlotI) chart.getXYPlot()).useEqualTicks(true);

        chart.getXYPlot().setDomainAxis(new org.jfree.chart.axis.NumberAxis("x"));
        ((NumberAxis)chart.getXYPlot().getDomainAxis()).setStandardTickUnits(new eLamXNumberTickUnitSource());

        chart.getXYPlot().setRangeAxis(new org.jfree.chart.axis.NumberAxis("y"));
        ((NumberAxis)chart.getXYPlot().getRangeAxis()).setStandardTickUnits(new eLamXNumberTickUnitSource());

        chart.getXYPlot().setDataset(seriesCol);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.BLACK);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke((float) 1.5));
        chart.getXYPlot().getRenderer().setDefaultToolTipGenerator(new StandardXYToolTipGenerator());
        
        eLamXChartTheme.getInstance().apply(chart);
    }

    private void updateChart() {
        clearChart();
        
        seriesCol.removeAllSeries();
        
        series[0].setNotify(false);
        series[0].clear();
        
        drawOriginal(series[0]);
        
        series[0].setNotify(true);
        
        seriesCol.addSeries(series[0]);
        
        series[1].setNotify(false);
        series[1].clear();
        
        drawDeformed(series[1]);
        
        series[1].setNotify(true);
        
        seriesCol.addSeries(series[1]);
    }
    
    private void drawOriginal(XYSeries series){
        double[][] points = GlobalSpringInProperties.getInstance().getGeoCalc().getUndeformedGeometry(data.getResult(), data.getLaminat());
        
        for (double[] point : points) {
            series.add(point[0], point[1]);
        }
    }
    
    private void drawDeformed(XYSeries series){
        double[][] points = GlobalSpringInProperties.getInstance().getGeoCalc().getDeformedGeometry(data.getResult(), data.getLaminat());
        
        for (double[] point : points) {
            series.add(point[0], point[1]);
        }
    }

    private void clearChart() {
    }

    public void cleanup() {
        if (data != null) {
            data.removePropertyChangeListener(SpringInModuleData.PROP_RESULT, this);
        }
    }
}
