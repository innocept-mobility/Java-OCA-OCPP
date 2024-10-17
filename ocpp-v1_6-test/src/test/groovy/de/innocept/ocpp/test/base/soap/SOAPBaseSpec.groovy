package de.innocept.ocpp.test.base.soap

import de.innocept.ocpp.test.FakeCentral
import de.innocept.ocpp.test.FakeCentralSystem
import de.innocept.ocpp.test.FakeChargePoint
import spock.lang.Shared
import spock.lang.Specification


abstract class SOAPBaseSpec extends Specification
{
    @Shared
    FakeCentralSystem centralSystem = new FakeCentralSystem(FakeCentral.serverType.SOAP)
    @Shared
    FakeChargePoint chargePoint = new FakeChargePoint(FakeChargePoint.clientType.SOAP)

    def setupSpec() {
        // When a Central System is running
        centralSystem.started()
    }

    def setup() {
        chargePoint.connect()
    }

    def cleanup() {
        chargePoint.disconnect()
    }

    def cleanupSpec() {
        centralSystem.stopped()
    }
}
