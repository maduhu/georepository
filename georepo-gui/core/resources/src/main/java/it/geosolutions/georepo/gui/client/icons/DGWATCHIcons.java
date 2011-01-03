/**
 * 
 */
package it.geosolutions.georepo.gui.client.icons;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * @author giuseppe
 * 
 */
@SuppressWarnings("deprecation")
public interface DGWATCHIcons extends ImageBundle {

    @Resource("add.gif")
    AbstractImagePrototype add();
    
    @Resource("page_edit.gif")
    AbstractImagePrototype pageEdit();
    
	@Resource("table.png")
	AbstractImagePrototype table();

	@Resource("connect.png")
	AbstractImagePrototype connect();

	@Resource("user_add.png")
	AbstractImagePrototype userAdd();

	@Resource("user_delete.png")
	AbstractImagePrototype userDelete();

	@Resource("delete.gif")
	AbstractImagePrototype delete();
	
	@Resource("user.gif")
	AbstractImagePrototype user();
	
	@Resource("zoom-in.png")
    AbstractImagePrototype zoomIn();

    @Resource("zoom-out.png")
    AbstractImagePrototype zoomOut();
    
    @Resource("dgwatch-about.png")
    AbstractImagePrototype info();
    
    @Resource("draw-feature.png")
    AbstractImagePrototype drawFeature();
    
    @Resource("file_upload_icon.png")
    AbstractImagePrototype uploadSHP();
    
    @Resource("eraser_minus.png")
    AbstractImagePrototype cleanDgWatchMenu();
    
    @Resource("logout.png")
    AbstractImagePrototype logout();
    
    @Resource("aoi_add.png")
    AbstractImagePrototype addAOI();
    
    @Resource("aoi_del.png")
    AbstractImagePrototype deleteAOI();
    
    @Resource("aoi_edit.png")
    AbstractImagePrototype editAOI();
    
    @Resource("rss.png")
    AbstractImagePrototype rss();
    
    @Resource("user_edit.png")
    AbstractImagePrototype editUser();
    
    @Resource("aoi_get.png")
    AbstractImagePrototype getAOIS();
    
    @Resource("features_get.png")
    AbstractImagePrototype getFeatures();
    
    @Resource("find.png")
    AbstractImagePrototype search();

    @Resource("share.png")
    AbstractImagePrototype share();

    @Resource("save.png")
    AbstractImagePrototype save();
    
    @Resource("trigger.png")
    AbstractImagePrototype trigger();
   
    @Resource("watches.png")
    AbstractImagePrototype searchWatches();
}
