package lv.edw.phoneCodes;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class PhoneCodesService {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final PhoneCodesStorage storage;

    @Autowired
    public PhoneCodesService(PhoneCodesStorage storage) {
        this.storage = storage;
    }

    public ResponseModel getCountryByPhoneNumber(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber parse = getPhoneNumber(phoneNumber);
            String countryNameByCode = storage.getCountryNameByCode((long) parse.getCountryCode());
            return new ResponseModel(countryNameByCode, 200);
        } catch (NumberParseException e) {
            log.warning("Failed to parse phone number: " + phoneNumber);
            return resolveFailureReason(e.getErrorType());
        }
    }

    private ResponseModel resolveFailureReason(NumberParseException.ErrorType errorType) {
        switch (errorType) {
            case INVALID_COUNTRY_CODE:
                return new ResponseModel(Utils.INVALID_COUNTRY_CODE_ERROR_MSG, 400);
            case TOO_LONG:
                return new ResponseModel(Utils.NUMBER_TOO_LONG_ERROR_MSG, 400);
            case NOT_A_NUMBER:
                return new ResponseModel(Utils.NOT_A_NUMBER_ERROR_MSG, 400);
            default:
                return new ResponseModel(Utils.NUMBER_TOO_SHORT_ERROR_MSG, 400);
        }
    }

    private Phonenumber.PhoneNumber getPhoneNumber(String phoneNumber) throws NumberParseException {
        return phoneNumberUtil.parse(phoneNumber, "");
    }
}
