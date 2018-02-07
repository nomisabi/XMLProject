<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:b="http://www.ftn.uns.ac.rs/xpath/examples" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0"
    xmlns:ulog="http://www.ftn.uns.ac.rs/uloge"
    xmlns:rc="http://www.ftn.uns.ac.rs/recenzija"
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
                        <xsl:value-of select="rc:Recenzija/nr:Revizija/nr:Naslov"/>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="20px" text-align="center"> 
                        &#160;
                    </fo:block>
                    
                    <fo:block font-family="sans-serif"  text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" margin="10px"> 
                        <fo:inline font-weight="bold">Preporuka: </fo:inline>
                        <fo:inline >
                            <xsl:value-of select="rc:Recenzija/ulog:Recenzent/ulog:Ime"/> &#160;  <xsl:value-of select="rc:Recenzija/ulog:Recenzent/ulog:Prezime"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block font-family="sans-serif"  text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" margin="10px"> 
                        <fo:block font-weight="bold">Pitanje: </fo:block>
                        <xsl:for-each select="rc:Recenzija/rc:Sadrzaj/rc:Pitanja">
                            <fo:block font-style="italic"><xsl:value-of select="rc:TekstPitanja"/></fo:block>
                            <fo:block margin-left="4px" ><xsl:value-of select="rc:Odgovor"/></fo:block>
                        </xsl:for-each>                       
                    </fo:block>
                    <fo:block font-family="sans-serif"  text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" margin="10px"> 
                        <fo:block font-weight="bold">Komentare: </fo:block>
                        <xsl:for-each select="rc:Recenzija/rc:Sadrzaj/rc:KommentarZaAutore/nr:Tekst/nr:ObicanTekst">
                            <fo:block >
                                <xsl:value-of  select="nr:Tekstualni_sadzaj"/>
                            </fo:block>             
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-family="sans-serif"  text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" margin="10px"> 
                        <fo:block font-weight="bold">Dodatak: </fo:block>
                        
                        <fo:block >
                            <xsl:value-of select="rc:Recenzija/rc:Sadrzaj/rc:Dodatak"/>
                        </fo:block>             
                        
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
