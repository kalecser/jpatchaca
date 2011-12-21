/**
 * JPatchacaSoapServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.jpatchaca.jira.ws;

public interface JPatchacaSoapServiceService extends javax.xml.rpc.Service {
    public java.lang.String getJpatchacaServiceAddress();

    public org.jpatchaca.jira.ws.JPatchacaSoapService getJpatchacaService() throws javax.xml.rpc.ServiceException;

    public org.jpatchaca.jira.ws.JPatchacaSoapService getJpatchacaService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
