package de.innocept.ocpp.test.profiles.core.json

import de.innocept.ocpp.test.base.json.JSONBaseSpec
import spock.util.concurrent.PollingConditions

class JSONStartTransactionSpec extends JSONBaseSpec {

    def "Charge point sends StartTransaction request and receives a response"() {
        def conditions = new PollingConditions(timeout: 1)
        when:
        chargePoint.sendStartTransactionRequest()

        then:
        conditions.eventually {
            assert centralSystem.hasHandledStartTransactionRequest()
        }

        then:
        conditions.eventually {
            assert chargePoint.hasReceivedStartTransactionConfirmation()
        }

    }

}
