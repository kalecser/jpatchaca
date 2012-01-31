/**
 * JPatchacaSoapServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.jpatchaca.jira.ws;

@SuppressWarnings({"unchecked", "rawtypes", "serial" })
public class JPatchacaSoapServiceServiceLocator extends org.apache.axis.client.Service implements org.jpatchaca.jira.ws.JPatchacaSoapServiceService {

    public JPatchacaSoapServiceServiceLocator() {
    }


    public JPatchacaSoapServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public JPatchacaSoapServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for JpatchacaserviceV1
    private java.lang.String JpatchacaserviceV1_address = "https://10.42.12.15:8880/rpc/soap/jpatchacaservice-v1";

    public java.lang.String getJpatchacaserviceV1Address() {
        return JpatchacaserviceV1_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String JpatchacaserviceV1WSDDServiceName = "jpatchacaservice-v1";

    public java.lang.String getJpatchacaserviceV1WSDDServiceName() {
        return JpatchacaserviceV1WSDDServiceName;
    }

    public void setJpatchacaserviceV1WSDDServiceName(java.lang.String name) {
        JpatchacaserviceV1WSDDServiceName = name;
    }

    public org.jpatchaca.jira.ws.JPatchacaSoapService getJpatchacaserviceV1() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(JpatchacaserviceV1_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getJpatchacaserviceV1(endpoint);
    }

    public org.jpatchaca.jira.ws.JPatchacaSoapService getJpatchacaserviceV1(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.jpatchaca.jira.ws.JpatchacaserviceV1SoapBindingStub _stub = new org.jpatchaca.jira.ws.JpatchacaserviceV1SoapBindingStub(portAddress, this);
            _stub.setPortName(getJpatchacaserviceV1WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setJpatchacaserviceV1EndpointAddress(java.lang.String address) {
        JpatchacaserviceV1_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.jpatchaca.jira.ws.JPatchacaSoapService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.jpatchaca.jira.ws.JpatchacaserviceV1SoapBindingStub _stub = new org.jpatchaca.jira.ws.JpatchacaserviceV1SoapBindingStub(new java.net.URL(JpatchacaserviceV1_address), this);
                _stub.setPortName(getJpatchacaserviceV1WSDDServiceName());
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
        if ("jpatchacaservice-v1".equals(inputPortName)) {
            return getJpatchacaserviceV1();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://10.42.12.15:8880/rpc/soap/jpatchacaservice-v1", "JPatchacaSoapServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://10.42.12.15:8880/rpc/soap/jpatchacaservice-v1", "jpatchacaservice-v1"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("JpatchacaserviceV1".equals(portName)) {
            setJpatchacaserviceV1EndpointAddress(address);
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
