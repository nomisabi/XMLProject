package com.example.model.uloge.search;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.uloge.Autor;
import com.marklogic.client.query.FacetResult;
import com.marklogic.client.query.FacetValue;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Niko Schmuck
 */
@XmlRootElement(name = "searchresult")
@XmlAccessorType(XmlAccessType.NONE)
public class AutorSearchResult {

    private List<Autor> autori;

    // Default constructor to keep JAXB happy
    public AutorSearchResult() {
    }

    public AutorSearchResult(List<Autor> autori) {
        this.autori = autori;
       }

    @XmlElementWrapper(name = "autori")
    @XmlElement(name = "autori")
    public List<Autor> getAutori() {
        return autori;
    }

}
