package lv.edw.phoneCodes

import spock.lang.Specification
import spock.lang.Unroll

class UtilsSpec extends Specification {

    @Unroll
    def "wiki: #wikiStr after removing: #cleaStr"() {
        expect:
        Utils.removeWikiSpecificCharacters(wikiStr) == clearStr
        where:
        wikiStr                           | clearStr
        '+123,+4412,+554'                 | '+123+4412+554'
        '+1234'                           | '+1234'
        '+1 242[notes 1]'                 | '+1242'
        '+1 809, +1 829, +1 849[notes 1]' | '+1809+1829+1849'
        null                              | null
    }
}
