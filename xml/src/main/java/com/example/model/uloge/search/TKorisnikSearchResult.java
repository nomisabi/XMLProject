package com.example.model.uloge.search;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.uloge.TKorisnik;
import com.marklogic.client.query.FacetResult;
import com.marklogic.client.query.FacetValue;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Niko Schmuck
 */
@XmlRootElement(name = "searchresult")
@XmlAccessorType(XmlAccessType.NONE)
public class TKorisnikSearchResult {

    private List<TKorisnik> korisnici;

    // Default constructor to keep JAXB happy
    public TKorisnikSearchResult() {
    }

    public TKorisnikSearchResult(List<TKorisnik> korisnici) {
        this.korisnici = korisnici;
       }

    @XmlElementWrapper(name = "korisnici")
    @XmlElement(name = "korisnici")
    public List<TKorisnik> getKorisnici() {
        return korisnici;
    }

}
