package it.geosolutions.georepo.gui.client.widget.dialog;

import it.geosolutions.georepo.gui.client.model.Profile;
import it.geosolutions.georepo.gui.client.service.ProfilesManagerServiceRemoteAsync;
import it.geosolutions.georepo.gui.client.widget.SaveStaus;
import it.geosolutions.georepo.gui.client.widget.rule.detail.ProfileDetailsTabItem;
import it.geosolutions.georepo.gui.client.widget.tab.TabWidget;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;

/**
 * ProfileDetailsEditDialog class.
 * 
 * @author Tobia di Pisa
 *
 */
public class ProfileDetailsEditDialog  extends Dialog {

    /** The Constant PROFILE_DETAILS_DIALOG_ID. */
    public static final String PROFILE_DETAILS_DIALOG_ID = "profileDetailsDialog";

    /** The save status. */
    private SaveStaus saveStatus;

    /** The done. */
    private Button done;
    
    /** The model. */
    private Profile profile;

    /** The rules manager service remote. */
    private ProfilesManagerServiceRemoteAsync profilesManagerServiceRemoteAsync;

    /** The tab widget. */
    private TabWidget tabWidget;

    /**
     * Instantiates a new rule details edit dialog.
     * 
     * @param rulesManagerServiceRemote
     *            the rules manager service remote
     */
    public ProfileDetailsEditDialog(ProfilesManagerServiceRemoteAsync profilesManagerServiceRemoteAsync) {
        this.profilesManagerServiceRemoteAsync = profilesManagerServiceRemoteAsync;

        setTabWidget(new TabWidget());
        
        setResizable(false);
        setButtons("");
        setClosable(true);
        setModal(true);
        setWidth(700);
        setHeight(427);
        
        add(this.getTabWidget());
    }

    /* (non-Javadoc)
     * @see com.extjs.gxt.ui.client.widget.Dialog#createButtons()
     */
    @Override
    protected void createButtons() {
        super.createButtons();

        this.saveStatus = new SaveStaus();
        this.saveStatus.setAutoWidth(true);

        getButtonBar().add(saveStatus);

        getButtonBar().add(new FillToolItem());

        this.done = new Button("Done", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                hide();
            }
        });

        addButton(done);
    }

    /* (non-Javadoc)
     * @see com.extjs.gxt.ui.client.widget.Window#show()
     */
    @Override
    public void show() {
        super.show();

        if (getModel() != null) {
            setHeading("Editing Profile Details for Rule #" + profile.getId());
            this.tabWidget.add(new ProfileDetailsTabItem(PROFILE_DETAILS_DIALOG_ID, profile,
                    profilesManagerServiceRemoteAsync));

        }

    }

    /**
     * Reset.
     */
    public void reset() {
        this.tabWidget.removeAll();
        this.saveStatus.clearStatus("");
    }

    /**
     * Sets the model.
     * 
     * @param model
     *            the new model
     */
    public void setModel(Profile profile) {
        this.profile = profile;
    }

    /* (non-Javadoc)
     * @see com.extjs.gxt.ui.client.widget.Component#getModel()
     */
    public Profile getModel() {
        return this.profile;
    }

    /**
     * Sets the tab widget.
     * 
     * @param tabWidget
     *            the new tab widget
     */
    public void setTabWidget(TabWidget tabWidget) {
        this.tabWidget = tabWidget;
    }

    /**
     * Gets the tab widget.
     * 
     * @return the tab widget
     */
    public TabWidget getTabWidget() {
        return tabWidget;
    }

}
