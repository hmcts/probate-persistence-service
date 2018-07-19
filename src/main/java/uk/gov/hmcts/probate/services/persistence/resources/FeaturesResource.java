package uk.gov.hmcts.probate.services.persistence.resources;

import org.ff4j.FF4j;
import org.ff4j.core.FlippingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeaturesResource {

    @Autowired
    private FF4j ff4j;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
    public String sayHello() {
        StringBuilder response = new StringBuilder("<html><body><h1>Persistence Service FF4j Console</h1><ul>");
        response.append("<li> To access the <b>WebConsole</b> please go to <a href=\"./ff4j-web-console/home\">ff4j-web-console</a>");
        response.append("<li> To access the <b>REST API</b> please go to <a href=\"./api/ff4j\">api/ff4j</a>");
        response.append("</body></html>");
        return response.toString();
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "text/html")
    public String helloWorld() {
        if(ff4j.exist("testFeature")) {
            StringBuilder response = new StringBuilder("<html><body><h1>Persistence Service FF4j Console</h1>");

            String status = ff4j.check("testFeature") ?
                    "<h2>The feature is now live!!</h2>" :
                    "<h2>That feature has been switched off!!</h2>";

            response.append(status);
            response.append("<span>Feature is enabled: ");
            response.append(ff4j.check("testFeature"));
            response.append("</span></body></html>");

            return response.toString();
        }
        return "That feature does not exist. Please try again.";
    }
}