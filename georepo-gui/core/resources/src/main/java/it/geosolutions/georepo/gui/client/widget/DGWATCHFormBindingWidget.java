package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.model.Authorization;
import it.geosolutions.georepo.gui.client.widget.binding.DGWATCHWatchFormBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

import java.util.List;

/**
 * @author Tobia di Pisa
 *
 * @param <T>
 */
public abstract class DGWATCHFormBindingWidget <T extends BaseModel> {

	protected FormPanel formPanel;
	protected DGWATCHWatchFormBinding formBinding;
	private T model;

	/**
	 * 
	 * @param model
	 *            T object to bind
	 */
	public void bindModel(T model) {
		this.model = model;
		this.formBinding.bind(model);
	}

	public void unBindModel() {
		this.formBinding.unbind();
	}

	/**
	 * @return the formBinding
	 */
	public FormBinding getFormBinding() {
		return formBinding;
	}

	/**
	 * @return the model
	 */
	public T getModel() {
		return model;
	}

	public abstract FormPanel createFormPanel();

	/**
	 * @return the formPanel
	 */
	public FormPanel getFormPanel() {
		return formPanel;
	}

    /**
     * Injects security into a form based on the provided Authorization objects.
     *
     * @param auths Authorization objects denoting what is allowed
     */
    public void injectSecurity(List<Authorization> auths) {
        // do nothing for default implementation
    }

}
