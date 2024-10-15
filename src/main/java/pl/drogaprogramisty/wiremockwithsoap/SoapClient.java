package pl.drogaprogramisty.wiremockwithsoap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import pl.drogaprogramisty.generated.wsdl.CountryCurrency;
import pl.drogaprogramisty.generated.wsdl.CountryCurrencyResponse;

@Service
public class SoapClient {

    private final WebServiceTemplate webServiceTemplate;

    @Autowired
    public SoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public CountryCurrencyResponse callSoapService(CountryCurrency request) {
        return (CountryCurrencyResponse) webServiceTemplate.marshalSendAndReceive(request);
    }
}
