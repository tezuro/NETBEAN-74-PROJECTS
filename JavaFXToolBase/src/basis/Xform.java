/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;

/**
 *
 * @author tezuro
 */
    public class Xform extends Group {
        public final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        public final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        public final Rotate rz = new Rotate(0, Rotate.Z_AXIS);

        
        public Xform() { 
            super(); 
            getTransforms().addAll(rz, ry, rx); 
        }
    }
