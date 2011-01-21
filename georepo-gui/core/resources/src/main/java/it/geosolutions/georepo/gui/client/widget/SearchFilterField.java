/**
 * 
 */
package it.geosolutions.georepo.gui.client.widget;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.IconSupport;
import com.extjs.gxt.ui.client.widget.form.StoreFilterField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author Alessio
 * 
 */
public abstract class SearchFilterField<T extends ModelData> extends StoreFilterField<T> implements
        IconSupport {

    private String style;

    protected AbstractImagePrototype icon;

    public SearchFilterField() {
        this.style = "x-menu-item";
    }

    @Override
    protected abstract boolean doSelect(Store<T> store, T parent, T record, String property,
            String filter);

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.widget.IconSupport#getIcon()
     */
    public AbstractImagePrototype getIcon() {
        return icon;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.extjs.gxt.ui.client.widget.IconSupport#setIcon(com.google.gwt.user.client.ui.
     * AbstractImagePrototype)
     */
    public void setIcon(AbstractImagePrototype icon) {
        if (rendered) {
            El oldIcon = el().selectNode(style);
            if (oldIcon != null) {
                oldIcon.remove();
            }
            if (icon != null) {
                Element e = icon.createElement().cast();
                El.fly(e).addStyleName(style);
                el().insertChild(e, 0);
            }
        }
        this.icon = icon;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.widget.IconSupport#setIconStyle(java.lang.String)
     */
    public void setIconStyle(String icon) {
        setIcon(IconHelper.create(icon));
    }

    @Override
    protected void afterRender() {
        super.afterRender();
        setIcon(icon);
    }

}