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
package de.elamx.export.Abaqus;

import de.elamx.export.Export;
import de.elamx.export.ExportOptions;
import de.elamx.laminate.Laminat;
import de.elamx.laminate.Layer;
import de.elamx.laminate.Material;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Andreas Hauffe
 */
public class AbaqusExport extends Export<AbaqusExportOptions> {

    private final HashMap<Material, String> materialNums = new HashMap<>();

    private String matName = "mat";

    public AbaqusExport(Laminat laminat) {
        super(laminat, new AbaqusExportOptions());
    }

    @Override
    public String exportMaterials() {
        String exportString = "";

        exportString += "** WARNING:\n";
        exportString += "** No material properties for the transverse shear stiffnesses are specified in eLamX.\n";
        exportString += "** Therefore, the inplane-shear stiffness is multiplied by a factor of 5/6 to obtain the transverse shear stiffnesses.\n";
        exportString += "** The resulting values should not be used in FE-analyses\n";
        exportString += "\n";

        List<Material> materials = getMaterialsList();

        int matNum = 0;
        materialNums.clear();

        for (Material m : materials) {

            matNum++;
            materialNums.put(m, matName + matNum);

            exportString += "*MATERIAL, NAME=" + matName + matNum + "\n";
            exportString += "*ELASTIC, TYPE=LAMINA" + "\n";
            exportString += m.getEpar() + ", " + m.getEnor() + ", " + m.getNue12() + ", " + m.getG();
            if (m.getG13() != 0.0){
                exportString +=  ", " + m.getG13();
            }else{
                exportString +=  ", " + (5.0 / 6.0 * m.getG()); 
            }
            if (m.getG23() != 0.0){
                exportString +=  ", " + m.getG23();
            }else{
                exportString +=  ", " + (5.0 / 6.0 * m.getG()); 
            }
            exportString += "\n";
            if (getOptions().isStrength()) {
                exportString += "*FAIL STRESS" + "\n";
                exportString += m.getRParTen() + ", " + m.getRParCom() + ", " + m.getRNorTen() + ", " + m.getRNorCom() + ", " + m.getRShear() + "\n"; // + ", " + m..getF12star();
            }
            if (getOptions().isHygrothermal()) {
                exportString += "*EXPANSION,TYPE=ORTHO" + "\n";
                exportString += m.getAlphaTPar() + ", " + m.getAlphaTNor() + "\n";
            }
            exportString += "*DENSITY" + "\n";
            exportString += Double.toString(m.getRho()) + "\n";
            exportString += "" + "\n";
        }

        return exportString;
    }

    @Override
    public String exportLaminate() {
        String exportString = "";

        // Property
        exportString += "*SHELL GENERAL SECTION, ELSET=SET1, COMPOSITE" + "\n";
        if (getOptions().getOffset() == ExportOptions.OFFSET_TOP) {
            exportString += " OFFSET=SPOS" + "\n";
        } else if (getOptions().getOffset() == ExportOptions.OFFSET_BOT) {
            exportString += " OFFSET=SNEG" + "\n";
        }
        
        ArrayList<Layer> layers = getAllLayers();
        Layer layer;
        
        for (int ii = 0; ii < layers.size(); ii++) {
            layer = layers.get(ii);
            exportString += layer.getThickness() + ", , " + materialNums.get(layer.getMaterial()) + ", " + layer.getAngle() + "\n";
        }
        
        return exportString;
    }

}
