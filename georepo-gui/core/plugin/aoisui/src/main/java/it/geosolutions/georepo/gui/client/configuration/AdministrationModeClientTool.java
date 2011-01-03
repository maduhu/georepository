package it.geosolutions.georepo.gui.client.configuration;

import it.geosolutions.georepo.gui.client.AdministrationMode;
import it.geosolutions.georepo.gui.client.DGWATCHEvents;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.Authorization;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;

import java.util.List;

/**
 * Client tool that represents the dropdown that allows the user to change which administration mode they
 * want to use.
 *
 * @author gmurray
 */
public class AdministrationModeClientTool extends DropdownClientTool implements Listener<AppEvent> {

    /**
     * Spring init method to set up the class - a pseudo-constructor.
     */
    public void init() {
        addOption(new DropdownOption(AdministrationMode.NOTIFICATION_DISTRIBUTION.name(),
                AdministrationMode.NOTIFICATION_DISTRIBUTION.name()));
        addOption(new DropdownOption(AdministrationMode.GEOCONSTRAINTS.name(),
                AdministrationMode.GEOCONSTRAINTS.name()));
        addOption(new DropdownOption(AdministrationMode.MEMBER.name(),
                AdministrationMode.MEMBER.name()));
    }

    public void handleEvent(AppEvent e) {
        if (e.getType() == DGWATCHEvents.LOGIN_SUCCESS) {

        }
    }

    @Override
    public String getDropdownOptionDisplayValue(String dataValue) {
        AdministrationMode adminMode = AdministrationMode.valueOf(dataValue);
        switch (adminMode) {
            case NOTIFICATION_DISTRIBUTION:
                return I18nProvider.getMessages().adminModeNotificationDistribution();
            case GEOCONSTRAINTS:
                return I18nProvider.getMessages().adminModeGeoConstraints();
            case MEMBER:
                return I18nProvider.getMessages().adminModeMember();
        }
        return null;
    }

    @Override
    public void injectSecurity(ComboBox combo, List<Authorization> auths) {
        if ((auths == null) || auths.isEmpty()) {
            return;
        }

        ListStore<ModelData> store = combo.getStore();
        ModelData origSelected = combo.getValue();

        // if we remove the currently selected item, we'll also need to send an ADMIN_MODE_CHANGE
        // to the rest of the GUI
        boolean changeSelectedItem = false;

        for (ModelData md : store.getModels()) {
            String value = md.get(DropdownOption.VALUE_KEY);

            if (AdministrationMode.NOTIFICATION_DISTRIBUTION.name().equals(value)) {
                if (!auths.contains(Authorization.NOTIFICATION) && !auths.contains(Authorization.DISTRIBUTION) ) {
                    if (md == origSelected) {
                        changeSelectedItem = true;
                    }
                    store.remove(md);
                }
            }
            else if (AdministrationMode.GEOCONSTRAINTS.name().equals(value)) {
                if (!auths.contains(Authorization.GEOCONSTRAINT)) {
                    if (md == origSelected) {
                        changeSelectedItem = true;
                    }
                    store.remove(md);
                }
            }
            else if (AdministrationMode.MEMBER.name().equals(value)) {
                if (!auths.contains(Authorization.MEMBER_DISTRIBUTION)) {
                    if (md == origSelected) {
                        changeSelectedItem = true;
                    }
                    store.remove(md);
                }
            }
        }

        if (changeSelectedItem) {
            combo.select(0);
            ModelData selectedItem = (ModelData) combo.getSelection().get(0);
            Dispatcher.forwardEvent(DGWATCHEvents.ADMIN_MODE_CHANGE,
                    AdministrationMode.valueOf(selectedItem.<String>get(DropdownOption.VALUE_KEY)));
        }
    }
}
