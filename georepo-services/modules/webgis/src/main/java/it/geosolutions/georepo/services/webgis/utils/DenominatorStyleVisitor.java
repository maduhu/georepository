/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.geosolutions.georepo.services.webgis.utils;

import org.apache.log4j.Logger;
import org.geotools.styling.AnchorPoint;
import org.geotools.styling.ChannelSelection;
import org.geotools.styling.ColorMap;
import org.geotools.styling.ColorMapEntry;
import org.geotools.styling.ContrastEnhancement;
import org.geotools.styling.Displacement;
import org.geotools.styling.ExternalGraphic;
import org.geotools.styling.FeatureTypeConstraint;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Halo;
import org.geotools.styling.ImageOutline;
import org.geotools.styling.LinePlacement;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.Mark;
import org.geotools.styling.NamedLayer;
import org.geotools.styling.OverlapBehavior;
import org.geotools.styling.PointPlacement;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SelectedChannelType;
import org.geotools.styling.ShadedRelief;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleVisitor;
import org.geotools.styling.StyledLayer;
import org.geotools.styling.StyledLayerDescriptor;
import org.geotools.styling.Symbolizer;
import org.geotools.styling.TextSymbolizer;
import org.geotools.styling.UserLayer;

/**
 * Parses a style to find out the max and min denominators for which at least a
 * Rule is defined.
 *
 * <P>Open upper and lower bounds are represented by NaN.
 *
 * @author etj
 */
public class DenominatorStyleVisitor 
        implements StyleVisitor {

    private static final Logger LOGGER = Logger.getLogger(DenominatorStyleVisitor.class);

    private double max = Double.NEGATIVE_INFINITY;
    private double min = Double.POSITIVE_INFINITY;

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public void reset() {
        max = Double.NEGATIVE_INFINITY;
        min = Double.POSITIVE_INFINITY;
    }

    @Override
    public void visit(Style style) {
        reset();

        FeatureTypeStyle[] ftStyles = style.getFeatureTypeStyles();

        for (int i = 0; i < ftStyles.length; i++) {
            ftStyles[i].accept(this);
        }
    }

    @Override
    public void visit(Rule rule) {
        double rulemax = rule.getMaxScaleDenominator();
        double rulemin = rule.getMinScaleDenominator();

        max = Math.max(max, rulemax);
        min = Math.min(min, rulemin);

//        if(rulemax==Double.NaN || rulemax == Double.MAX_VALUE || rulemax == Double.POSITIVE_INFINITY)
//            max = Double.NaN;
//        else {
//            if(max != Double.NaN) { // already maxed out
//                Math.max(max, max);
//            }
//        }

        // flatten to NaN
        if(max == Double.MAX_VALUE || max == Double.POSITIVE_INFINITY)
            max = Double.NaN;

        if(min==0 || min == Double.MIN_VALUE || min == Double.NEGATIVE_INFINITY)
            min = Double.NaN;


//        Filter filter = rule.getFilter();
//        if (filter != null) {
//            filter.accept(this, "");
//        }

        Symbolizer[] symbolizers = rule.getSymbolizers();

        if (symbolizers != null) {
            for (int i = 0; i < symbolizers.length; i++) {
                Symbolizer symbolizer = symbolizers[i];
                symbolizer.accept(this);
            }
        }

        Graphic[] legendGraphics = rule.getLegendGraphic();

        if (legendGraphics != null) {
        }
    }

    @Override
    public void visit(FeatureTypeStyle fts) {
        Rule[] rules = fts.getRules();

        for (int i = 0; i < rules.length; i++) {
            Rule rule = rules[i];
            rule.accept(this);
        }
    }

    @Override
    public void visit(Fill fill) {
        // nothing to do here
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.Stroke)
     */
    @Override
    public void visit(Stroke stroke) {
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.Symbolizer)
     */
    @Override
    public void visit(Symbolizer sym) {
        if (sym instanceof PointSymbolizer) {
            visit((PointSymbolizer) sym);
        }

        if (sym instanceof LineSymbolizer) {
            visit((LineSymbolizer) sym);
        }

        if (sym instanceof PolygonSymbolizer) {
            visit((PolygonSymbolizer) sym);
        }

        if (sym instanceof TextSymbolizer) {
            visit((TextSymbolizer) sym);
        }

        if (sym instanceof RasterSymbolizer) {
            visit((RasterSymbolizer) sym);
        }
    }

    @Override
    public void visit(RasterSymbolizer rs) {
//        if (rs.getGeometryPropertyName() != null) {
//            attributeNames.add(rs.getGeometryPropertyName());
//
//            // FIXME
//            // LiteRenderer2 trhwos an Exception:
//            // Do not know how to deep copy
//            // org.geotools.coverage.grid.GridCoverage2D
//            // attributeNames.add("grid");
//        }

        if (rs.getImageOutline() != null) {
            rs.getImageOutline().accept(this);
        }

//        if (rs.getOpacity() != null) {
//            rs.getOpacity().accept(this,null);
//        }
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.PointSymbolizer)
     */
    @Override
    public void visit(PointSymbolizer ps) {
        if (ps.getGraphic() != null) {
            ps.getGraphic().accept(this);
        }
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.LineSymbolizer)
     */
    @Override
    public void visit(LineSymbolizer line) {
        if (line.getStroke() != null) {
            line.getStroke().accept(this);
        }
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.PolygonSymbolizer)
     */
    @Override
    public void visit(PolygonSymbolizer poly) {
        if (poly.getStroke() != null) {
            poly.getStroke().accept(this);
        }
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.TextSymbolizer)
     */
    @Override
    public void visit(TextSymbolizer text) {
        // nothing to do here
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.Graphic)
     */
    @Override
    public void visit(Graphic gr) {
        // nothing to do here
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.Mark)
     */
    @Override
    public void visit(Mark mark) {
        // nothing to do here
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.ExternalGraphic)
     */
    @Override
    public void visit(ExternalGraphic exgr) {
        // nothing to do
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.PointPlacement)
     */
    @Override
    public void visit(PointPlacement pp) {
        // nothing to do here
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.AnchorPoint)
     */
    @Override
    public void visit(AnchorPoint ap) {
        // nothing to do here
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.Displacement)
     */
    @Override
    public void visit(Displacement dis) {
        // nothing to do here
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.LinePlacement)
     */
    @Override
    public void visit(LinePlacement lp) {
        // nothing to do here
    }

    /**
     * @see org.geotools.styling.StyleVisitor#visit(org.geotools.styling.Halo)
     */
    @Override
    public void visit(Halo halo) {
        // nothing to do here
    }

    @Override
    public void visit(StyledLayerDescriptor sld) {
        StyledLayer[] layers = sld.getStyledLayers();

        for (int i = 0; i < layers.length; i++) {
            if (layers[i] instanceof NamedLayer) {
                ((NamedLayer) layers[i]).accept(this);
            } else if (layers[i] instanceof UserLayer) {
                ((UserLayer) layers[i]).accept(this);
            }
        }
    }

    @Override
    public void visit(NamedLayer layer) {
        Style[] styles = layer.getStyles();

        for (int i = 0; i < styles.length; i++) {
            styles[i].accept(this);
        }
    }

    @Override
    public void visit(UserLayer layer) {
        Style[] styles = layer.getUserStyles();

        for (int i = 0; i < styles.length; i++) {
            styles[i].accept(this);
        }
    }

    @Override
    public void visit(FeatureTypeConstraint ftc) {
       ftc.accept(this);
    }

    @Override
    public void visit(ColorMap map) {
        // nothing to do here
    }

    @Override
    public void visit(ColorMapEntry entry) {
        // nothing to do here
    }

    @Override
	public void visit(ContrastEnhancement contrastEnhancement) {
		// TODO Auto-generated method stub

	}

    @Override
	public void visit(ImageOutline outline) {
	       outline.accept(this);

	}

    @Override
	public void visit(ChannelSelection cs) {
	 // nothing to do here

	}

    @Override
	public void visit(OverlapBehavior ob) {
	    // nothing to do here

	}

    @Override
	public void visit(SelectedChannelType sct) {
	 // nothing to do here

	}

    @Override
	public void visit(ShadedRelief sr) {
	 // nothing to do here

	}
}