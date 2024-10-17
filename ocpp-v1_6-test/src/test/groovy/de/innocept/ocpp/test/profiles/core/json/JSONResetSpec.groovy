package de.innocept.ocpp.test.profiles.core.json

import de.innocept.ocpp.model.core.ResetType
import de.innocept.ocpp.test.base.json.JSONBaseSpec
import spock.util.concurrent.PollingConditions

class JSONResetSpec extends JSONBaseSpec {

    def "Central System sends a Reset request and receives a response"() {
        def conditions = new PollingConditions(timeout: 1)
        when:
        centralSystem.sendResetRequest(ResetType.Hard)

        then:
        conditions.eventually {
            assert chargePoint.hasHandledResetRequest()
            assert centralSystem.hasReceivedResetConfirmation("Accepted")
        }
    }
}
