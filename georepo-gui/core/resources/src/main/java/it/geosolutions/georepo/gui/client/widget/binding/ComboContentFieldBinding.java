package it.geosolutions.georepo.gui.client.widget.binding;

import it.geosolutions.georepo.gui.client.ContentType;
import it.geosolutions.georepo.gui.client.model.Watch;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;

/**
 * @author Tobia di Pisa
 *
 */
public class ComboContentFieldBinding extends FieldBinding {

	private Object oldValue;

	/**
	 * @param field
	 * @param property
	 */
	@SuppressWarnings("rawtypes")
	public ComboContentFieldBinding(Field field, String property) {
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

		((ComboBox<ContentType>) field).setValue(new ContentType(val
				.toString()));

		if (updateOriginalValue) {
			((ComboBox<ContentType>) field)
					.setOriginalValue(new ContentType(val.toString()));
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
			((Watch) model).setContentType(((ContentType) val).getType());
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void resetValue() {
		oldValue = onConvertFieldValue(field.getValue());

		((ComboBox<ContentType>) field).setValue(new ContentType(oldValue
				.toString()));

		((Watch) model).setContentType(((ContentType) oldValue).getType());
	}
}
