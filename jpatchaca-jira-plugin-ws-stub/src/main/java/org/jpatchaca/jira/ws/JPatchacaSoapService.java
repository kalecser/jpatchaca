/**
 * JPatchacaSoapService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.jpatchaca.jira.ws;

public interface JPatchacaSoapService extends java.rmi.Remote {
    public org.jpatchaca.jira.ws.RemoteMetaAttribute[] getMetaAttributesForIssue(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException, org.jpatchaca.jira.ws.RemoteException;
    public long getRemainingEstimate(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException, org.jpatchaca.jira.ws.RemoteException;
}
