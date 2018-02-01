package com.example.model.propratnopismo.search;

import com.example.model.propratnopismo.ProptatnoPismo;
import com.marklogic.client.query.FacetResult;
import com.marklogic.client.query.FacetValue;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Niko Schmuck
 */
@XmlRootElement(name = "searchresult")
@XmlAccessorType(XmlAccessType.NONE)
public class ProptatnoPismoSearchResult {

    private List<ProptatnoPismo> propratnoPisme;

    // Default constructor to keep JAXB happy
    public ProptatnoPismoSearchResult() {
    }

    public ProptatnoPismoSearchResult(List<ProptatnoPismo> propratnoPisme) {
        this.propratnoPisme = propratnoPisme;
       }

    @XmlElementWrapper(name = "naucni_radovi")
    @XmlElement(name = "naucni_radovi")
    public List<ProptatnoPismo> getPropratnoPisme() {
        return propratnoPisme;
    }

}
