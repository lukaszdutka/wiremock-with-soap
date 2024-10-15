package pl.drogaprogramisty.wiremockwithsoap;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.drogaprogramisty.generated.wsdl.CountryCurrency;
import pl.drogaprogramisty.generated.wsdl.CountryCurrencyResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
class WiremockWithSoapApplicationTests {
    private WireMockServer wireMockServer;
    @Autowired
    private SoapClient soapClient;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));
        wireMockServer.start();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    void testWithWiremock() {
        setupSoapStubs();
        CountryCurrency request = new CountryCurrency();
        request.setSCountryISOCode("PL");

        CountryCurrencyResponse on = soapClient.callSoapService(request);

        System.out.println(on.getCountryCurrencyResult().getSName());
        System.out.println(on.getCountryCurrencyResult().getSISOCode());
    }


    public void setupSoapStubs() {
//        wireMockServer.stubFor(post(urlMatching("/websamples.countryinfo"))
        wireMockServer.stubFor(any(anyUrl())
//                .withHeader("Content-Type", equalTo("text/xml"))
//                .withRequestBody(containing("<soap:Envelope"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("""
                                <?xml version="1.0" encoding="utf-8"?>
                                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                                    <soap:Body>
                                        <m:CountryCurrencyResponse xmlns:m="http://www.oorsprong.org/websamples.countryinfo">
                                            <m:CountryCurrencyResult>
                                                <m:sISOCode>PLN</m:sISOCode>
                                                <m:sName>Zlotych</m:sName>
                                            </m:CountryCurrencyResult>
                                        </m:CountryCurrencyResponse>
                                    </soap:Body>
                                </soap:Envelope>
                                """)));
    }
}