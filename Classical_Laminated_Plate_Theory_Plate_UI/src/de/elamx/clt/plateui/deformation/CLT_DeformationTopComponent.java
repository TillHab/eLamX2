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
package de.elamx.clt.plateui.deformation;

import com.ardor3d.scenegraph.Mesh;
import de.elamx.clt.CLTRefreshListener;
import de.elamx.clt.CLT_Laminate;
import de.elamx.core.RawDataExportService;
import de.elamx.core.SnapshotService;
import de.elamx.laminate.Laminat;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

@TopComponent.Description(
        preferredID = "CLT_DeformationTopComponent",
        iconBase="de/elamx/clt/plateui/resources/deformation.png"
)
public final class CLT_DeformationTopComponent extends TopComponent implements LookupListener, CLTRefreshListener, PropertyChangeListener {

    public final static Set<DeformationModuleData> uniqueDeformationData = new HashSet<DeformationModuleData>();
    private final DeformationModuleData data;
    private final CLT_Laminate clt_lam;
    private final Lookup.Result<DeformationModuleData> result;
    
    public CLT_DeformationTopComponent(DeformationModuleData data) {
        this.data = data;
        setName(this.data.getName() + " - " + this.data.getLaminat().getName());
        setToolTipText(NbBundle.getMessage(CLT_DeformationTopComponent.class, "HINT_CLT_DeformationTopComponent"));
        data.getLaminat().addPropertyChangeListener(this);
        CLT_Laminate tClt_lam = data.getLaminat().getLookup().lookup(CLT_Laminate.class);
        if (tClt_lam == null) {
            clt_lam = new CLT_Laminate(data.getLaminat());
        } else {
            clt_lam = tClt_lam;
        }
        clt_lam.addCLTRefreshListener(this);
        initComponents();
        view3D.setShape3D(new ArrayList<Mesh>(), 1.0);
        result = data.getLaminat().getLookup().lookupResult(DeformationModuleData.class);
        result.addLookupListener(this);
        data.getDeformationInput().addPropertyChangeListener(this);
        data.addPropertyChangeListener(this);
        associateLookup(new ProxyLookup(Lookups.fixed(data, data.getLaminat(), new Snapshot(), new VTKExport()), controlPanel.getLookups()[0], controlPanel.getLookups()[1]));
        refreshed();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        view3D = new de.view3d.View3D();
        jScrollPane1 = new javax.swing.JScrollPane();
        controlPanel = new ControlPanel(data, view3D);

        setLayout(new java.awt.BorderLayout());
        add(view3D, java.awt.BorderLayout.CENTER);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(controlPanel);

        add(jScrollPane1, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.elamx.clt.plateui.deformation.ControlPanel controlPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private de.view3d.View3D view3D;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentClosed() {
        clt_lam.removeCLTRefreshListener(this);
        data.getLaminat().removePropertyChangeListener(this);
        result.removeLookupListener(this);
        data.getDeformationInput().removePropertyChangeListener(this);
        data.removePropertyChangeListener(this);
        controlPanel.cleanup();
        uniqueDeformationData.remove(data);
    }
    
    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void resultChanged(LookupEvent le) {
        if (!result.allInstances().contains(data)) {
            this.close();
        }
    }

    @Override
    public void refreshed() {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof Laminat && evt.getPropertyName().equals(Laminat.PROP_NAME) ||
            evt.getSource() instanceof DeformationModuleData && evt.getPropertyName().equals(DeformationModuleData.PROP_NAME)) {
            setName(this.data.getName() + " - " + this.data.getLaminat().getName());
        }
    }
    
    private class Snapshot implements SnapshotService {

        @Override
        public void saveSnapshot(File file) {
            view3D.saveScreenshot(file, "png");
        }
    }
    
    private class VTKExport implements RawDataExportService {
        @Override
        public void export(FileWriter fw) {
            try {
                view3D.exportQuadArrays(fw);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        @Override
        public String getFileExtension() {
            return "vtk";
        }
    }
}
