package lv.edw.phoneCodes;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(method = RequestMethod.GET, path = "api/identifyCountry", produces = MediaType.APPLICATION_JSON_VALUE)
@Log
public class PhoneCodesController {

    private final PhoneCodesService phoneCodesService;

    @Autowired
    public PhoneCodesController(PhoneCodesService phoneCodesService) {
        this.phoneCodesService = phoneCodesService;
    }

    @RequestMapping(path = "{phoneNumber}")
    public ResponseEntity getCountryByPhoneCode(@PathVariable("phoneNumber") String phoneNumber,
                                                HttpServletRequest httpServletRequest) {
        log.info("Request from " + httpServletRequest.getRemoteAddr() + " phone num: " + phoneNumber);
        ResponseModel countryByPhoneNumber = phoneCodesService.getCountryByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(countryByPhoneNumber);
    }

}
