package com.szacs.ferroliconnect.presenter;

import com.p107tb.appyunsdk.Constant;
import com.szacs.ferroliconnect.bean.City;
import com.szacs.ferroliconnect.bean.District;
import com.szacs.ferroliconnect.bean.Province;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParserHandler extends DefaultHandler {
    City city = new City();
    District district = new District();
    Province province = new Province();
    private List<Province> provinceList = new ArrayList();

    public void characters(char[] cArr, int i, int i2) throws SAXException {
    }

    public void startDocument() throws SAXException {
    }

    public List<Province> getDataList() {
        return this.provinceList;
    }

    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        if (str3.equals("province")) {
            this.province = new Province();
            this.province.setName(attributes.getValue(Constant.NAME));
            this.province.setCityList(new ArrayList());
        } else if (str3.equals("city")) {
            this.city = new City();
            this.city.setName(attributes.getValue(Constant.NAME));
            this.city.setOwmId(attributes.getValue("owm_id"));
            this.city.setDistrictList(new ArrayList());
        } else if (str3.equals("district")) {
            this.district = new District();
            this.district.setName(attributes.getValue(Constant.NAME));
            this.district.setZipcode(attributes.getValue("zipcode"));
        }
    }

    public void endElement(String str, String str2, String str3) throws SAXException {
        if (str3.equals("district")) {
            this.city.getDistrictList().add(this.district);
        } else if (str3.equals("city")) {
            this.province.getCityList().add(this.city);
        } else if (str3.equals("province")) {
            this.provinceList.add(this.province);
        }
    }
}
