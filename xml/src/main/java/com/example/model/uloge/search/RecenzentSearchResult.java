package com.example.model.uloge.search;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.uloge.Recenzent;
import com.marklogic.client.query.FacetResult;
import com.marklogic.client.query.FacetValue;


import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Niko Schmuck
 */
@XmlRootElement(name = "searchresult")
@XmlAccessorType(XmlAccessType.NONE)
public class RecenzentSearchResult {

    private List<Recenzent> recenzenti;

    // Default constructor to keep JAXB happy
    public RecenzentSearchResult() {
    }

    public RecenzentSearchResult(List<Recenzent> recenzenti) {
        this.recenzenti = recenzenti;
       }

    @XmlElementWrapper(name = "recenzenti")
    @XmlElement(name = "recenzenti")
    public List<Recenzent> getRecenzenti() {
        return recenzenti;
    }

}
