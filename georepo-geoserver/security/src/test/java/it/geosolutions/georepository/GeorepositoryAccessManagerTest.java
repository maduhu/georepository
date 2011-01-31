package it.geosolutions.georepository;

import org.geoserver.catalog.LayerInfo;
import org.geoserver.catalog.WorkspaceInfo;
import org.geoserver.data.test.MockData;
import org.geoserver.security.VectorAccessLimits;
import org.geoserver.security.WorkspaceAccessLimits;
import org.geoserver.test.GeoServerTestSupport;
import org.opengis.filter.Filter;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class GeorepositoryAccessManagerTest extends GeoServerTestSupport {

    GeorepositoryAccessManager manager;

    @Override
    protected void setUpInternal() throws Exception {
        super.setUpInternal();

        manager = (GeorepositoryAccessManager) applicationContext
                .getBean("georeposityRuleAccessManager");
    }

    public void testAdmin() {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("admin",
                "geoserver",
                new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_ADMINISTRATOR") });

        // check workspace access
        WorkspaceInfo citeWS = getCatalog().getWorkspaceByName(MockData.CITE_PREFIX);
        WorkspaceAccessLimits wl = manager.getAccessLimits(user, citeWS);
        assertTrue(wl.isReadable());
        assertTrue(wl.isWritable());

        // check layer access
        LayerInfo layer = getCatalog().getLayerByName(getLayerId(MockData.BASIC_POLYGONS));
        VectorAccessLimits vl = (VectorAccessLimits) manager.getAccessLimits(user, layer);
        assertEquals(Filter.INCLUDE, vl.getReadFilter());
        assertEquals(Filter.INCLUDE, vl.getWriteFilter());
        assertNull(vl.getReadAttributes());
        assertNull(vl.getWriteAttributes());
    }
}
