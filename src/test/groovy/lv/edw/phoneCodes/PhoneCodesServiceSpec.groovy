package lv.edw.phoneCodes

import spock.lang.Specification
import spock.lang.Unroll

class PhoneCodesServiceSpec extends Specification {

    def phoneCodesStorage = Mock(PhoneCodesStorage.class)

    def codesService = new PhoneCodesService(phoneCodesStorage)

    @Unroll
    def "number: #number equals response: #response"() {
        given:
        phoneCodesStorage.getCountryNameByCode(_) >> 'Latvia'
        expect:
        codesService.getCountryByPhoneNumber(number) == response
        where:
        number                    | response
        '+37139319182'            | new ResponseModel("Latvia", 200)
        '+3713931918222222222222' | new ResponseModel(Utils.NUMBER_TOO_LONG_ERROR_MSG, 400)
        '+441'                    | new ResponseModel(Utils.NUMBER_TOO_SHORT_ERROR_MSG, 400)
        'This is String'          | new ResponseModel(Utils.NOT_A_NUMBER_ERROR_MSG, 400)
        '+99939319182'            | new ResponseModel(Utils.INVALID_COUNTRY_CODE_ERROR_MSG, 400)

    }
}
