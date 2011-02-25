package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.model.Rule;


import com.extjs.gxt.ui.client.widget.grid.Grid;

public class GridStatus {


    private Rule model;
    private Grid<Rule> grid;

    public GridStatus(Grid<Rule> grid, Rule rule) {
        this.grid = grid;
        this.model = rule;
    }

    public Rule getModel() {
        return model;
    }

    public void setModel(Rule model) {
        this.model = model;
    }

    public Grid<Rule> getGrid() {
        return grid;
    }

    public void setGrid(Grid<Rule> grid) {
        this.grid = grid;
    }
}
