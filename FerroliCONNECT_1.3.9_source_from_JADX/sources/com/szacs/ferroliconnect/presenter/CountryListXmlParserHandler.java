package com.szacs.ferroliconnect.presenter;

import com.szacs.ferroliconnect.bean.Country;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CountryListXmlParserHandler extends DefaultHandler {
    private Country country;
    private List<Country> countryList = new ArrayList();
    private String currentQName;

    public List<Country> getCountryList() {
        return this.countryList;
    }

    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        super.startElement(str, str2, str3, attributes);
        if (str3.equals("Country")) {
            this.country = new Country();
        }
        this.currentQName = str3;
    }

    public void endElement(String str, String str2, String str3) throws SAXException {
        super.endElement(str, str2, str3);
        if (str3.equals("Country")) {
            this.countryList.add(this.country);
        }
    }

    public void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        String str = new String(cArr, i, i2);
        if (!str.trim().equals("") && !str.trim().equals("\n")) {
            if (this.currentQName.equals("Country_name")) {
                this.country.setName(str);
            } else if (this.currentQName.equals("Country_name_CN")) {
                this.country.setNameCN(str);
            } else if (this.currentQName.equals("Code")) {
                this.country.setCode(str);
            }
        }
    }
}
