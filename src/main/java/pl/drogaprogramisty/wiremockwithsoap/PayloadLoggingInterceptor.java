package pl.drogaprogramisty.wiremockwithsoap;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptorAdapter;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.WebServiceMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PayloadLoggingInterceptor extends ClientInterceptorAdapter {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        logSoapMessage("Request", messageContext.getRequest());
        return true; // Continue with the execution chain
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        logSoapMessage("Response", messageContext.getResponse());
        return true; // Continue with the execution chain
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        logSoapMessage("Fault", messageContext.getResponse());
        return true; // Continue with the execution chain
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
        // No additional actions after completion
    }

    private void logSoapMessage(String messageType, WebServiceMessage message) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            String messageContent = outputStream.toString();
            System.out.println(messageType + ": " + messageContent);
        } catch (IOException e) {
            System.err.println("Error logging SOAP " + messageType + ": " + e.getMessage());
        }
    }
}