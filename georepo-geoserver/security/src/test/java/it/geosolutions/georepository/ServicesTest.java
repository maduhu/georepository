package it.geosolutions.georepository;

 import static org.custommonkey.xmlunit.XMLAssert.*;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.w3c.dom.Document;

public class ServicesTest extends GeorepositoryBaseTest {

    public void testAdmin() throws Exception {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("admin",
                "geoserver",
                new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_ADMINISTRATOR") });
        
        // check from the caps he can access everything
        Document dom = getAsDOM("wms?request=GetCapabilities&version=1.1.1&service=WMS");
        // print(dom);
     
        assertXpathEvaluatesTo("11", "count(//Layer[starts-with(Name, 'cite:')])", dom);
        assertXpathEvaluatesTo("3", "count(//Layer[starts-with(Name, 'sf:')])", dom);
        assertXpathEvaluatesTo("8", "count(//Layer[starts-with(Name, 'cdf:')])", dom);
    }
    
    public void testCiteWorkspace() {
        
    }
}
