/**
 * JPatchacaSoapServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.jpatchaca.jira.ws;

@SuppressWarnings({"rawtypes", "unchecked", "serial"})
public class JPatchacaSoapServiceServiceLocator extends org.apache.axis.client.Service implements org.jpatchaca.jira.ws.JPatchacaSoapServiceService {

    public JPatchacaSoapServiceServiceLocator() {
    }


    public JPatchacaSoapServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public JPatchacaSoapServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for JpatchacaService
    private java.lang.String JpatchacaService_address = "https://10.42.12.15:8880/rpc/soap/jpatchaca-service";

    public java.lang.String getJpatchacaServiceAddress() {
        return JpatchacaService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String JpatchacaServiceWSDDServiceName = "jpatchaca-service";

    public java.lang.String getJpatchacaServiceWSDDServiceName() {
        return JpatchacaServiceWSDDServiceName;
    }

    public void setJpatchacaServiceWSDDServiceName(java.lang.String name) {
        JpatchacaServiceWSDDServiceName = name;
    }

    public org.jpatchaca.jira.ws.JPatchacaSoapService getJpatchacaService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(JpatchacaService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getJpatchacaService(endpoint);
    }

    public org.jpatchaca.jira.ws.JPatchacaSoapService getJpatchacaService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.jpatchaca.jira.ws.JpatchacaServiceSoapBindingStub _stub = new org.jpatchaca.jira.ws.JpatchacaServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getJpatchacaServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setJpatchacaServiceEndpointAddress(java.lang.String address) {
        JpatchacaService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.jpatchaca.jira.ws.JPatchacaSoapService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.jpatchaca.jira.ws.JpatchacaServiceSoapBindingStub _stub = new org.jpatchaca.jira.ws.JpatchacaServiceSoapBindingStub(new java.net.URL(JpatchacaService_address), this);
                _stub.setPortName(getJpatchacaServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("jpatchaca-service".equals(inputPortName)) {
            return getJpatchacaService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://10.42.12.15:8880/rpc/soap/jpatchaca-service", "JPatchacaSoapServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://10.42.12.15:8880/rpc/soap/jpatchaca-service", "jpatchaca-service"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("JpatchacaService".equals(portName)) {
            setJpatchacaServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
