package com.example.model.recenzija.search;

import com.example.model.recenzija.Recenzija;
import com.marklogic.client.query.FacetResult;
import com.marklogic.client.query.FacetValue;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Niko Schmuck
 */
@XmlRootElement(name = "searchresult")
@XmlAccessorType(XmlAccessType.NONE)
public class RecenzijaSearchResult {

    private List<Recenzija> recenzije;

    // Default constructor to keep JAXB happy
    public RecenzijaSearchResult() {
    }

    public RecenzijaSearchResult(List<Recenzija> recenzije) {
        this.recenzije = recenzije;
       }

    @XmlElementWrapper(name = "naucni_radovi")
    @XmlElement(name = "naucni_radovi")
    public List<Recenzija> getRecenzijaovi() {
        return recenzije;
    }

}
