package it.logostech.mymodule.appmodel;

import it.logostech.mymodule.appmodel.Util;

/**
 * Created by michele.schimd on 26/06/2014.
 */
public class ModelBase {
    private long id;

    public ModelBase() {
        this.id = Util.getRandomId();
    }

    public long getId() {
        return id;
    }
}
