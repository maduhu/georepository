package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.configuration.DropdownOption;
import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.DistributionNode;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.DualListField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Panel used to select distribution nodes.
 *
 * @author gmurray
 */
public class DistributionNodeManagementWidget extends ContentPanel {

    private FormPanel formPanel;
    private DualListField<DistributionNode> nodeSelector;

    private List<DistributionNode> sourceNodes;
    private List<DistributionNode> destinationNodes;

    public DistributionNodeManagementWidget() {
        init();
    }

    /**
     * Initialize the panel and all its children.
     */
    private void init() {
        setId("distributionNodeManagementWidget");
        setHeading(I18nProvider.getMessages().memberDistributionNodeManagement());
        setLayout(new FitLayout());

        setLayoutOnChange(true);

        this.formPanel = createFormPanel();
        add(this.formPanel);

        addWidgetListener(new WidgetListener() {
            @Override
            public void widgetResized(ComponentEvent ce) {
                formPanel.setHeight(getHeight());
            }
        });

        setScrollMode(Style.Scroll.AUTOY);
    }

    /**
     * Create and configure the FormPanel that will contain all necessary controls for
     * node selection.
     *
     * @return FormPanel
     */
    private FormPanel createFormPanel() {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);

        this.nodeSelector = new DualListField<DistributionNode>();
        this.nodeSelector.setFieldLabel("Nodes");
        ListField<DistributionNode> source = this.nodeSelector.getFromList();
        source.setDisplayField(DistributionNode.KeyValue.NODE_NAME.getValue());
        source.setValueField(DistributionNode.KeyValue.NODE_ID.getValue());

        // fill up the source list
        ListStore<DistributionNode> store = new ListStore<DistributionNode>();
        store.setStoreSorter(new StoreSorter<DistributionNode>());
        source.setStore(store);

        ListField<DistributionNode> destination = this.nodeSelector.getToList();
        destination.setDisplayField(DistributionNode.KeyValue.NODE_NAME.getValue());
        destination.setValueField(DistributionNode.KeyValue.NODE_ID.getValue());

        // empty destination by default
        // TODO: get these values from the server for the specified member
        store = new ListStore<DistributionNode>();
        store.setStoreSorter(new StoreSorter<DistributionNode>());
        destination.setStore(store);

        fp.add(this.nodeSelector);

        return fp;
    }

    public void setSourceNodes(List<DistributionNode> nodes) {
        this.sourceNodes = nodes;
        fillNodeSelector();
    }

    public List<DistributionNode> getSourceNodes() {
        return this.sourceNodes;
    }

    public void setDestinationNodes(List<DistributionNode> nodes) {
        this.destinationNodes = nodes;
        fillNodeSelector();
    }

    public List<DistributionNode> getDestinationNodes() {
        return this.destinationNodes;
    }

    public List<DistributionNode> getSelectedNodes() {
        return this.nodeSelector.getToList().getStore().getModels();
    }

    private void fillNodeSelector() {

        // make sure we have source and destination lists before filling the control - they may be empty,
        // but cannot be null - null means they haven't been initialized yet
        ListField<DistributionNode> source = this.nodeSelector.getFromList();
        ListStore<DistributionNode> sourceStore = source.getStore();
        sourceStore.removeAll();

        if (this.sourceNodes != null) {
            // fill up the source list
            sourceStore.setStoreSorter(new StoreSorter<DistributionNode>());

            for (DistributionNode node : this.sourceNodes) {
                sourceStore.add(node);
            }
        }


        if ((this.sourceNodes != null) && (this.destinationNodes != null)) {

            // fill up the source list
            ListField<DistributionNode> destination = this.nodeSelector.getToList();
            ListStore<DistributionNode> destStore = destination.getStore();
            destStore.removeAll();
            destStore.setStoreSorter(new StoreSorter<DistributionNode>());

            for (DistributionNode node : this.sourceNodes) {
                if (this.destinationNodes.contains(node)) {
                    sourceStore.remove(node);
                }
            }

            // fill up the destination list
            destStore.add(this.destinationNodes);
        }
    }

}
