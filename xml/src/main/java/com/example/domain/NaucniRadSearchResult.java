package com.example.domain;

import com.example.model.naucni_rad.NaucniRad;
import com.marklogic.client.query.FacetResult;
import com.marklogic.client.query.FacetValue;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Niko Schmuck
 */
@XmlRootElement(name = "searchresult")
@XmlAccessorType(XmlAccessType.NONE)
public class NaucniRadSearchResult {

    private List<NaucniRad> naucniRadovi;

    // Default constructor to keep JAXB happy
    public NaucniRadSearchResult() {
    }

    public NaucniRadSearchResult(List<NaucniRad> naucniRadovi) {
        this.naucniRadovi = naucniRadovi;
       }

    @XmlElementWrapper(name = "naucni_radovi")
    @XmlElement(name = "naucni_radovi")
    public List<NaucniRad> getNaucniRadovi() {
        return naucniRadovi;
    }

}
