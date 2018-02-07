<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:b="http://www.ftn.uns.ac.rs/xpath/examples" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0"
    xmlns:ulog="http://www.ftn.uns.ac.rs/uloge"
    xmlns:rec="http://www.ftn.uns.ac.rs/recenzija"
    xmlns:pp="http://www.ftn.uns.ac.rs/propratnoPismo"
    xmlns:nr="http://www.ftn.uns.ac.rs/naucni_rad"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="nr-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="nr-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="sans-serif" font-size="20px" text-align="center">
                        <xsl:value-of select="pp:ProptatnoPismo/nr:Revizija/nr:Naslov"/>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="20px" text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">
                        <fo:block><xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Ime"/> &#160;  <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Prezime"/></fo:block>
                        <fo:block> <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Naziv"/>
                        </fo:block>
                        <fo:block><xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Adresa/ulog:Broj"/>  &#160;<xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Adresa/ulog:Ulica"/> </fo:block>
                        <fo:block><xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Adresa/ulog:Mesto"/>  &#160;<xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Adresa/ulog:Postanski_broj"/></fo:block>
                        <fo:block><xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Email"/></fo:block>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center"> 
                    &#160;
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">
                        <fo:block><xsl:value-of select="pp:ProptatnoPismo/ulog:Urednik/ulog:Ime"/> &#160;  <xsl:value-of select="pp:ProptatnoPismo/ulog:Urednik/ulog:Prezime"/></fo:block>
                        <fo:block><xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Email"/></fo:block>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">
                        <fo:block><xsl:value-of select="pp:ProptatnoPismo/pp:Datum"/></fo:block>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">
                        
                        <fo:block>
                        Dear <xsl:value-of select="pp:ProptatnoPismo/ulog:Urednik/ulog:Prezime"/>:
                        </fo:block> 
                        <fo:block>
                            I am pleased to submit an original research article entitled 
                            <xsl:value-of select="pp:ProptatnoPismo/nr:Revizija/nr:Naslov"/>
                            by
                            <xsl:for-each select="pp:ProptatnoPismo/nr:Revizija/ulog:Autor">
                                <xsl:value-of select="ulog:Ime"/> &#160; <xsl:value-of select="ulog:Prezime"/> 
                                <xsl:if test="position() != last()" > ,&#160; </xsl:if> 
                            </xsl:for-each>
                            for consideration for publication.  We previously uncovered a role for 
                        <xsl:value-of select="pp:ProptatnoPismo/pp:Sadrzaj/pp:Rad/nr:Tekst/nr:ObicanTekst"/>
                        , and this manuscript builds on our prior study to determine the evolution of this unique enzyme.
                        </fo:block>
                        <fo:block>
                            In this manuscript, we show that 
                            <xsl:for-each select="pp:ProptatnoPismo/pp:Sadrzaj/pp:Rezultat">
                                <xsl:value-of select="nr:Tekst/nr:ObicanTekst"/> 
                            </xsl:for-each>.
                        </fo:block>
                        <fo:block>
                            We believe that this manuscript is appropriate for publication 
                            <xsl:for-each select="pp:ProptatnoPismo/pp:Sadrzaj/pp:Razlog">
                                <xsl:value-of select="nr:Tekst/nr:ObicanTekst"/> 
                            </xsl:for-each>
                        </fo:block>
                        <fo:block>
                            This manuscript has not been published and is not under consideration for publication elsewhere. We have no conflicts of interest to disclose. If you feel that the manuscript is appropriate for your journal, we suggest the following reviewers:
                            <xsl:for-each select="pp:ProptatnoPismo/pp:Sadrzaj/pp:Razlog">
                                <xsl:value-of select="nr:Tekst/nr:ObicanTekst"/> 
                            </xsl:for-each>
                        </fo:block>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">
                        Thank you for your consideration!
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">
                        Sincerely,
                        
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">
                        <xsl:value-of select="pp:ProptatnoPismo/pp:Potpis"/>
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">
                        
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Ime"/> &#160;  <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Prezime"/>
                        
                    </fo:block>
                    <fo:block margin="10px" font-size="12px">                       
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Email"/>
                    </fo:block>
                    
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
</xsl:stylesheet>