<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ulog="http://www.ftn.uns.ac.rs/uloge"
    xmlns:rec="http://www.ftn.uns.ac.rs/recenzija"
    xmlns:pp="http://www.ftn.uns.ac.rs/propratnoPismo"
    xmlns:nr="http://www.ftn.uns.ac.rs/naucni_rad"
    exclude-result-prefixes="xs"
    version="2.0">   
    <xsl:output omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <html> 
            <body>
                <div style="text-align: center; font-size: 14px;">
                    <p>
                        <xsl:value-of select="pp:ProptatnoPismo/nr:Revizija/nr:Naslov"/>
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Ime"/> &#160;  <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Prezime"/>
                        <br></br>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Naziv"/>
                        <br></br>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Adresa/ulog:Broj"/>  &#160;<xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Adresa/ulog:Ulica"/> 
                        <br></br>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Adresa/ulog:Mesto"/>  &#160;<xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Institucija/ulog:Adresa/ulog:Postanski_broj"/> 
                        <br></br>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Email"/>
                        <br></br>
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Urednik/ulog:Ime"/> &#160;  <xsl:value-of select="pp:ProptatnoPismo/ulog:Urednik/ulog:Prezime"/>
                        <br></br>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Email"/>
                        <br></br>
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p>
                        <xsl:value-of select="pp:ProptatnoPismo/pp:Datum"/>
                        <br></br>
                       
                    </p>
                </div>
                
                <div style="margin:10px; font-size: 12px;">
                    <p> Dear <xsl:value-of select="pp:ProptatnoPismo/ulog:Urednik/ulog:Prezime"/>:
                        <br></br>
                        
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p> I am pleased to submit an original research article entitled <xsl:value-of select="pp:ProptatnoPismo/nr:Revizija/nr:Naslov"/>
                        by
                        <xsl:for-each select="pp:ProptatnoPismo/nr:Revizija/ulog:Autor">
                            <xsl:value-of select="ulog:Ime"/> &#160; <xsl:value-of select="ulog:Prezime"/> 
                            <xsl:if test="position() != last()" > ,&#160; </xsl:if>
                  
                        </xsl:for-each>
                        for consideration for publication.  We previously uncovered a role for 
                        <xsl:value-of select="pp:ProptatnoPismo/pp:Sadrzaj/pp:Rad/nr:Tekst/nr:ObicanTekst"/>
                        , and this manuscript builds on our prior study to determine the evolution of this unique enzyme.
                        <br></br>
                        In this manuscript, we show that 
                        <xsl:for-each select="pp:ProptatnoPismo/pp:Sadrzaj/pp:Rezultat">
                            <xsl:value-of select="nr:Tekst/nr:ObicanTekst"/> 
                        </xsl:for-each>
                        . <br></br>
                        We believe that this manuscript is appropriate for publication 
                        <xsl:for-each select="pp:ProptatnoPismo/pp:Sadrzaj/pp:Razlog">
                           <xsl:value-of select="nr:Tekst/nr:ObicanTekst"/> 
                        </xsl:for-each><br></br>
                        This manuscript has not been published and is not under consideration for publication elsewhere. We have no conflicts of interest to disclose. If you feel that the manuscript is appropriate for your journal, we suggest the following reviewers:
                        <xsl:for-each select="pp:ProptatnoPismo/pp:Sadrzaj/pp:Razlog">
                            <xsl:value-of select="nr:Tekst/nr:ObicanTekst"/> 
                        </xsl:for-each><br></br>                  
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p>
                        Thank you for your consideration!
                    </p>
                </div>                <div style="margin:10px; font-size: 12px;">
                    <p>
                        Sincerely,
                        <br></br>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Ime"/> &#160;  <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Prezime"/>
                        <br></br>
                        <xsl:value-of select="pp:ProptatnoPismo/ulog:Autor/ulog:Email"/>
                        <br></br>
                    </p>
                </div>

            </body>
        </html>
    </xsl:template>
    
</xsl:stylesheet>

