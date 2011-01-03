package it.geosolutions.georepo.gui.client.widget.binding;

import it.geosolutions.georepo.gui.client.RetrievalType;
import it.geosolutions.georepo.gui.client.model.Watch;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;

/**
 * @author Tobia di Pisa
 *
 */
public class ComboRetrievalFieldBinding extends FieldBinding {

	private Object oldValue;

	/**
	 * @param field
	 * @param property
	 */
	@SuppressWarnings("rawtypes")
	public ComboRetrievalFieldBinding(Field field, String property) {
		super(field, property);
	}

	/* (non-Javadoc)
	 * @see com.extjs.gxt.ui.client.binding.FieldBinding#updateField(boolean)
	 */
	@Override
    @SuppressWarnings("unchecked")
	public void updateField(boolean updateOriginalValue) {
		Object val = onConvertModelValue(model.get(property));

		if (oldValue == null)
			oldValue = val;

		((ComboBox<RetrievalType>) field).setValue(new RetrievalType(val
				.toString()));

		if (updateOriginalValue) {
			((ComboBox<RetrievalType>) field)
					.setOriginalValue(new RetrievalType(val.toString()));
		}
	}

	/* (non-Javadoc)
	 * @see com.extjs.gxt.ui.client.binding.FieldBinding#updateModel()
	 */
	@Override
    @SuppressWarnings("unchecked")
	public void updateModel() {
		Object val = onConvertFieldValue(field.getValue());
		if (store != null) {
			Record r = store.getRecord(model);
			if (r != null) {
				r.setValid(property, field.isValid());
				r.set(property, val);
			}
		} else {
			((Watch) model).setRetrievalType(((RetrievalType) val).getType());
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void resetValue() {
		oldValue = onConvertFieldValue(field.getValue());

		((ComboBox<RetrievalType>) field).setValue(new RetrievalType(oldValue
				.toString()));

		((Watch) model).setRetrievalType(((RetrievalType) oldValue).getType());
	}
}
