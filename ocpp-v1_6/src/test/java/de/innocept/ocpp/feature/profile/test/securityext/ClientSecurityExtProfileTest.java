package de.innocept.ocpp.feature.profile.test.securityext;

import de.innocept.ocpp.feature.Feature;
import de.innocept.ocpp.model.Confirmation;
import de.innocept.ocpp.feature.profile.securityext.ClientSecurityExtEventHandler;
import de.innocept.ocpp.feature.profile.securityext.ClientSecurityExtProfile;
import de.innocept.ocpp.feature.profile.test.ProfileTest;
import de.innocept.ocpp.feature.securityext.CertificateSignedFeature;
import de.innocept.ocpp.feature.securityext.DeleteCertificateFeature;
import de.innocept.ocpp.feature.securityext.ExtendedTriggerMessageFeature;
import de.innocept.ocpp.feature.securityext.GetInstalledCertificateIdsFeature;
import de.innocept.ocpp.feature.securityext.GetLogFeature;
import de.innocept.ocpp.feature.securityext.InstallCertificateFeature;
import de.innocept.ocpp.feature.securityext.LogStatusNotificationFeature;
import de.innocept.ocpp.feature.securityext.SecurityEventNotificationFeature;
import de.innocept.ocpp.feature.securityext.SignCertificateFeature;
import de.innocept.ocpp.feature.securityext.SignedFirmwareStatusNotificationFeature;
import de.innocept.ocpp.feature.securityext.SignedUpdateFirmwareFeature;
import de.innocept.ocpp.model.securityext.CertificateSignedConfirmation;
import de.innocept.ocpp.model.securityext.CertificateSignedRequest;
import de.innocept.ocpp.model.securityext.DeleteCertificateConfirmation;
import de.innocept.ocpp.model.securityext.DeleteCertificateRequest;
import de.innocept.ocpp.model.securityext.ExtendedTriggerMessageConfirmation;
import de.innocept.ocpp.model.securityext.ExtendedTriggerMessageRequest;
import de.innocept.ocpp.model.securityext.GetInstalledCertificateIdsConfirmation;
import de.innocept.ocpp.model.securityext.GetInstalledCertificateIdsRequest;
import de.innocept.ocpp.model.securityext.GetLogConfirmation;
import de.innocept.ocpp.model.securityext.GetLogRequest;
import de.innocept.ocpp.model.securityext.InstallCertificateConfirmation;
import de.innocept.ocpp.model.securityext.InstallCertificateRequest;
import de.innocept.ocpp.model.securityext.LogStatusNotificationRequest;
import de.innocept.ocpp.model.securityext.SecurityEventNotificationRequest;
import de.innocept.ocpp.model.securityext.SignCertificateRequest;
import de.innocept.ocpp.model.securityext.SignedFirmwareStatusNotificationRequest;
import de.innocept.ocpp.model.securityext.SignedUpdateFirmwareConfirmation;
import de.innocept.ocpp.model.securityext.SignedUpdateFirmwareRequest;
import de.innocept.ocpp.model.securityext.types.FirmwareStatusEnumType;
import de.innocept.ocpp.model.securityext.types.UploadLogStatusEnumType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
   ChargeTime.eu - Java-OCA-OCPP

   MIT License

   Copyright (C) 2022 Mathias Oben <mathias.oben@enervalis.com>

   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
*/

@RunWith(MockitoJUnitRunner.class)
public class ClientSecurityExtProfileTest extends ProfileTest {
  private static final UUID SESSION_ID = null;

  private ClientSecurityExtProfile profile;

  @Mock
  private ClientSecurityExtEventHandler handler;

  @Before
  public void setup() {
    profile = new ClientSecurityExtProfile(handler);
  }

  @Test
  public void getFeatureList_containsAllFeature() {
    // When
    Feature[] features = profile.getFeatureList();

    // then
    assertTrue(findFeature(features, "CertificateSigned") instanceof CertificateSignedFeature);
    assertTrue(findFeature(features, "DeleteCertificate") instanceof DeleteCertificateFeature);
    assertTrue(findFeature(features, "ExtendedTriggerMessage") instanceof ExtendedTriggerMessageFeature);
    assertTrue(findFeature(features, "GetInstalledCertificateIds") instanceof GetInstalledCertificateIdsFeature);
    assertTrue(findFeature(features, "GetLog") instanceof GetLogFeature);
    assertTrue(findFeature(features, "InstallCertificate") instanceof InstallCertificateFeature);
    assertTrue(findFeature(features, "LogStatusNotification") instanceof LogStatusNotificationFeature);
    assertTrue(findFeature(features, "SecurityEventNotification") instanceof SecurityEventNotificationFeature);
    assertTrue(findFeature(features, "SignCertificate") instanceof SignCertificateFeature);
    assertTrue(findFeature(features, "SignedFirmwareStatusNotification") instanceof SignedFirmwareStatusNotificationFeature);
    assertTrue(findFeature(features, "SignedUpdateFirmware") instanceof SignedUpdateFirmwareFeature);
  }

  @Test
  public void createLogStatusNotificationRequest_withParams_returnsCompleteRequest() {
    // Given
    UploadLogStatusEnumType status = UploadLogStatusEnumType.Uploaded;

    // When
    LogStatusNotificationRequest actual = profile.createLogStatusNotificationRequest(status);

    // Then
    assertEquals(status, actual.getStatus());
    assertNull(actual.getRequestId());
  }

  @Test
  public void createSecurityEventNotificationRequest_withParams_returnsCompleteRequest() {
    // Given
    String type = "type";
    ZonedDateTime timestamp = ZonedDateTime.now();

    // When
    SecurityEventNotificationRequest actual = profile.createSecurityEventNotificationRequest(type, timestamp);

    // Then
    assertEquals(type, actual.getType());
    assertEquals(timestamp, actual.getTimestamp());
    assertNull(actual.getTechInfo());
  }

  @Test
  public void createSignCertificateRequest_withParams_returnsCompleteRequest() {
    // Given
    String csr = "csr";

    // When
    SignCertificateRequest actual = profile.createSignCertificateRequest(csr);

    // Then
    assertEquals(csr, actual.getCsr());
  }

  @Test
  public void createSignedFirmwareStatusNotificationRequest_withParams_returnsCompleteRequest() {
    // Given
    FirmwareStatusEnumType status = FirmwareStatusEnumType.Downloaded;

    // When
    SignedFirmwareStatusNotificationRequest actual = profile.createSignedFirmwareStatusNotificationRequest(status);

    // Then
    assertEquals(status, actual.getStatus());
    assertNull(actual.getRequestId());
  }

  @Test
  public void handleRequest_whenCertificateSignedRequest_callsHandleCertificateSignedRequest() {
    // Given
    CertificateSignedRequest request = mock(CertificateSignedRequest.class);
    CertificateSignedConfirmation confirmation = mock(CertificateSignedConfirmation.class);
    when(handler.handleCertificateSignedRequest(request)).thenReturn(confirmation);

    // When
    Confirmation actual = profile.handleRequest(SESSION_ID, request);

    // Then
    assertEquals(confirmation, actual);
  }

  @Test
  public void handleRequest_whenDeleteCertificateRequest_callsHandleDeleteCertificateRequest() {
    // Given
    DeleteCertificateRequest request = mock(DeleteCertificateRequest.class);
    DeleteCertificateConfirmation confirmation = mock(DeleteCertificateConfirmation.class);
    when(handler.handleDeleteCertificateRequest(request)).thenReturn(confirmation);

    // When
    Confirmation actual = profile.handleRequest(SESSION_ID, request);

    // Then
    assertEquals(confirmation, actual);
  }

  @Test
  public void handleRequest_whenExtendedTriggerMessageRequest_callsHandleExtendedTriggerMessageRequest() {
    // Given
    ExtendedTriggerMessageRequest request = mock(ExtendedTriggerMessageRequest.class);
    ExtendedTriggerMessageConfirmation confirmation = mock(ExtendedTriggerMessageConfirmation.class);
    when(handler.handleExtendedTriggerMessageRequest(request)).thenReturn(confirmation);

    // When
    Confirmation actual = profile.handleRequest(SESSION_ID, request);

    // Then
    assertEquals(confirmation, actual);
  }

  @Test
  public void handleRequest_whenGetInstalledCertificateIdsRequest_callsHandleGetInstalledCertificateIdsRequest() {
    // Given
    GetInstalledCertificateIdsRequest request = mock(GetInstalledCertificateIdsRequest.class);
    GetInstalledCertificateIdsConfirmation confirmation = mock(GetInstalledCertificateIdsConfirmation.class);
    when(handler.handleGetInstalledCertificateIdsRequest(request)).thenReturn(confirmation);

    // When
    Confirmation actual = profile.handleRequest(SESSION_ID, request);

    // Then
    assertEquals(confirmation, actual);
  }

  @Test
  public void handleRequest_whenGetLogRequest_callsHandleGetLogRequest() {
    // Given
    GetLogRequest request = mock(GetLogRequest.class);
    GetLogConfirmation confirmation = mock(GetLogConfirmation.class);
    when(handler.handleGetLogRequest(request)).thenReturn(confirmation);

    // When
    Confirmation actual = profile.handleRequest(SESSION_ID, request);

    // Then
    assertEquals(confirmation, actual);
  }

  @Test
  public void handleRequest_whenInstallCertificateRequest_callsHandleInstallCertificateRequest() {
    // Given
    InstallCertificateRequest request = mock(InstallCertificateRequest.class);
    InstallCertificateConfirmation confirmation = mock(InstallCertificateConfirmation.class);
    when(handler.handleInstallCertificateRequest(request)).thenReturn(confirmation);

    // When
    Confirmation actual = profile.handleRequest(SESSION_ID, request);

    // Then
    assertEquals(confirmation, actual);
  }

  @Test
  public void handleRequest_whenSignedUpdateFirmwareRequest_callsHandleSignedUpdateFirmwareRequest() {
    // Given
    SignedUpdateFirmwareRequest request = mock(SignedUpdateFirmwareRequest.class);
    SignedUpdateFirmwareConfirmation confirmation = mock(SignedUpdateFirmwareConfirmation.class);
    when(handler.handleSignedUpdateFirmwareRequest(request)).thenReturn(confirmation);

    // When
    Confirmation actual = profile.handleRequest(SESSION_ID, request);

    // Then
    assertEquals(confirmation, actual);
  }

}
