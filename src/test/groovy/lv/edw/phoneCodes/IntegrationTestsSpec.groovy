package lv.edw.phoneCodes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@SpringBootTest(classes = PhoneCodesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationTestsSpec extends Specification {

    @Autowired
    PhoneCodesStorage phoneCodesStorage

    RestTemplate restTemplate = new RestTemplate()

    def dataLoadedTest() {
        expect:
        phoneCodesStorage.getCountryNameByCode(371) == 'Latvia'
        phoneCodesStorage.getCountryNameByCode(1) == 'United States'
    }

    def getLatvianCode() {
        def restResponse = restTemplate.getForEntity('http://localhost:8080/api/identifyCountry/+37129240397', ResponseModel.class)
        expect:
        restResponse.statusCodeValue == 200
        restResponse.getBody().response == 'Latvia'
        restResponse.getBody().responseCode == 200

    }

    def getError() {
        def restResponse = restTemplate.getForEntity('http://localhost:8080/api/identifyCountry/+37929240397', ResponseModel.class)
        expect:
        restResponse.statusCodeValue == 200
        restResponse.getBody().response == Utils.INVALID_COUNTRY_CODE_ERROR_MSG
        restResponse.getBody().responseCode == 400

    }

}



