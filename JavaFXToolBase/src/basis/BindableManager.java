/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import io.IBindable;
import java.util.HashMap;

/**
 *
 * @author tezuro
 */
public class BindableManager {

    final private HashMap<String, IBindable> bindables = new HashMap<>();

    public BindableManager() {
    }

    public void addBindable(IBindable bindable) {
        bindables.put(bindable.getID(), bindable);

    }

    public void processBindable(String[] data) {
        IBindable bindable = bindables.get(data[0]);
        if (bindable != null) {
            bindable.proccessData(data);
        }
    }
}
