package pl.drogaprogramisty.wiremockwithsoap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("pl.drogaprogramisty.generated.wsdl");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
//        template.setDefaultUri("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?wsdl");
        template.setDefaultUri("http://localhost:8081");
        template.setInterceptors(new ClientInterceptor[]{new PayloadLoggingInterceptor()});
        return template;
    }
}