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
package de.elamx.laminate.addFailureCriteria;

import com.ardor3d.math.Vector3;
import com.ardor3d.renderer.IndexMode;
import com.ardor3d.scenegraph.Mesh;
import com.ardor3d.scenegraph.MeshData;
import com.ardor3d.util.geom.BufferUtils;
import de.elamx.laminate.Layer;
import de.elamx.laminate.Material;
import de.elamx.laminate.StressStrainState;
import de.elamx.laminate.failure.Criterion;
import de.elamx.laminate.failure.ReserveFactor;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 * Diese Klasse ist die Implementierung des maximale Spannungskriteriums.
 *
 * @author Andreas Hauffe
 */
public class MaxStress extends Criterion {

    public static MaxStress getDefault(FileObject obj) {
        MaxStress ms = new MaxStress(obj);

        return ms;
    }

    public MaxStress(FileObject obj) {
        super(obj);
    }

    @Override
    public ReserveFactor getReserveFactor(Material material, Layer l, StressStrainState sss) {
        double[] stresses = sss.getStress();
        ReserveFactor rf = new ReserveFactor();
        double value;

        if (stresses[0] == 0.0 && stresses[1] == 0.0 && stresses[2] == 0.0) {
            rf.setFailureName("");
            rf.setMinimalReserveFactor(Double.POSITIVE_INFINITY);
            return rf;
        }

        if (stresses[0] >= 0.0) {
            rf.setMinimalReserveFactor(material.getRParTen() / stresses[0]);
            rf.setFailureName("FiberFailureTension");
            rf.setFailureType(ReserveFactor.FIBER_FAILURE);
        } else {
            rf.setMinimalReserveFactor(-material.getRParCom() / stresses[0]);
            rf.setFailureName("FiberFailureCompression");
            rf.setFailureType(ReserveFactor.FIBER_FAILURE);
        }

        if (stresses[1] >= 0.0) {
            value = material.getRNorTen() / stresses[1];
            if (value < rf.getMinimalReserveFactor()) {
                rf.setMinimalReserveFactor(value);
                rf.setFailureName("MatrixFailureTension");
                rf.setFailureType(ReserveFactor.MATRIX_FAILURE);
            }
        } else {
            value = -material.getRNorCom() / stresses[1];
            if (value < rf.getMinimalReserveFactor()) {
                rf.setMinimalReserveFactor(value);
                rf.setFailureName("MatrixFailureCompression");
                rf.setFailureType(ReserveFactor.MATRIX_FAILURE);
            }
        }

        value = material.getRShear() / Math.abs(stresses[2]);
        if (value < rf.getMinimalReserveFactor()) {
            rf.setMinimalReserveFactor(value);
            rf.setFailureName("MatrixFailureShear");
            rf.setFailureType(ReserveFactor.MATRIX_FAILURE);
        }

        if (Double.POSITIVE_INFINITY == rf.getMinimalReserveFactor()) {
            rf.setFailureName("");
        } else {
            rf.setFailureName(NbBundle.getMessage(MaxStress.class, "MaxStress." + rf.getFailureName()));
        }

        return rf;
    }

    @Override
    public Mesh getAsMesh(Material material, double quality) {

        int numPoints = 6 * 4;    // Anzahl der Punkte für die Vernetzung
        Vector3[] vertices = new Vector3[numPoints];
        Vector3[] normals  = new Vector3[numPoints];

        // Erzeugen der sechs Quaderflächen
        vertices[0] = new Vector3((float) material.getRParTen(),
                (float) material.getRNorTen(),
                (float) material.getRShear());
        vertices[1] = new Vector3((float) -material.getRParCom(),
                (float) material.getRNorTen(),
                (float) material.getRShear());
        vertices[2] = new Vector3((float) -material.getRParCom(),
                (float) -material.getRNorCom(),
                (float) material.getRShear());
        vertices[3] = new Vector3((float) material.getRParTen(),
                (float) -material.getRNorCom(),
                (float) material.getRShear());
        normals[0] = new Vector3(0.0f, 0.0f, 1.0f);
        normals[1] = new Vector3(0.0f, 0.0f, 1.0f);
        normals[2] = new Vector3(0.0f, 0.0f, 1.0f);
        normals[3] = new Vector3(0.0f, 0.0f, 1.0f);

        vertices[7] = new Vector3((float) material.getRParTen(),
                (float) material.getRNorTen(),
                (float) -material.getRShear());
        vertices[6] = new Vector3((float) -material.getRParCom(),
                (float) material.getRNorTen(),
                (float) -material.getRShear());
        vertices[5] = new Vector3((float) -material.getRParCom(),
                (float) -material.getRNorCom(),
                (float) -material.getRShear());
        vertices[4] = new Vector3((float) material.getRParTen(),
                (float) -material.getRNorCom(),
                (float) -material.getRShear());
        normals[4] = new Vector3(0.0f, 0.0f, -1.0f);
        normals[5] = new Vector3(0.0f, 0.0f, -1.0f);
        normals[6] = new Vector3(0.0f, 0.0f, -1.0f);
        normals[7] = new Vector3(0.0f, 0.0f, -1.0f);

        vertices[11] = new Vector3((float) material.getRParTen(),
                (float) material.getRNorTen(),
                (float) material.getRShear());
        vertices[10] = new Vector3((float) -material.getRParCom(),
                (float) material.getRNorTen(),
                (float) material.getRShear());
        vertices[9] = new Vector3((float) -material.getRParCom(),
                (float) material.getRNorTen(),
                (float) -material.getRShear());
        vertices[8] = new Vector3((float) material.getRParTen(),
                (float) material.getRNorTen(),
                (float) -material.getRShear());
        normals[8] = new Vector3(0.0f, 1.0f, 0.0f);
        normals[9] = new Vector3(0.0f, 1.0f, 0.0f);
        normals[10] = new Vector3(0.0f, 1.0f, 0.0f);
        normals[11] = new Vector3(0.0f, 1.0f, 0.0f);

        vertices[12] = new Vector3((float) material.getRParTen(),
                (float) -material.getRNorCom(),
                (float) material.getRShear());
        vertices[13] = new Vector3((float) -material.getRParCom(),
                (float) -material.getRNorCom(),
                (float) material.getRShear());
        vertices[14] = new Vector3((float) -material.getRParCom(),
                (float) -material.getRNorCom(),
                (float) -material.getRShear());
        vertices[15] = new Vector3((float) material.getRParTen(),
                (float) -material.getRNorCom(),
                (float) -material.getRShear());
        normals[12] = new Vector3(0.0f, -1.0f, 0.0f);
        normals[13] = new Vector3(0.0f, -1.0f, 0.0f);
        normals[14] = new Vector3(0.0f, -1.0f, 0.0f);
        normals[15] = new Vector3(0.0f, -1.0f, 0.0f);

        vertices[19] = new Vector3((float) material.getRParTen(),
                (float) material.getRNorTen(),
                (float) material.getRShear());
        vertices[18] = new Vector3((float) material.getRParTen(),
                (float) material.getRNorTen(),
                (float) -material.getRShear());
        vertices[17] = new Vector3((float) material.getRParTen(),
                (float) -material.getRNorCom(),
                (float) -material.getRShear());
        vertices[16] = new Vector3((float) material.getRParTen(),
                (float) -material.getRNorCom(),
                (float) material.getRShear());
        normals[16] = new Vector3(1.0f, 0.0f, 0.0f);
        normals[17] = new Vector3(1.0f, 0.0f, 0.0f);
        normals[18] = new Vector3(1.0f, 0.0f, 0.0f);
        normals[19] = new Vector3(1.0f, 0.0f, 0.0f);

        vertices[20] = new Vector3((float) -material.getRParCom(),
                (float) material.getRNorTen(),
                (float) material.getRShear());
        vertices[21] = new Vector3((float) -material.getRParCom(),
                (float) material.getRNorTen(),
                (float) -material.getRShear());
        vertices[22] = new Vector3((float) -material.getRParCom(),
                (float) -material.getRNorCom(),
                (float) -material.getRShear());
        vertices[23] = new Vector3((float) -material.getRParCom(),
                (float) -material.getRNorCom(),
                (float) material.getRShear());
        normals[20] = new Vector3(-1.0f, 0.0f, 0.0f);
        normals[21] = new Vector3(-1.0f, 0.0f, 0.0f);
        normals[22] = new Vector3(-1.0f, 0.0f, 0.0f);
        normals[23] = new Vector3(-1.0f, 0.0f, 0.0f);

        
        final Mesh mesh = new Mesh();
        final MeshData meshData = mesh.getMeshData();

        meshData.setVertexBuffer(BufferUtils.createFloatBuffer(vertices));
        meshData.setNormalBuffer(BufferUtils.createFloatBuffer(normals));
        meshData.setIndexMode(IndexMode.Quads);

        mesh.updateModelBound();

        return mesh;
    }

}
