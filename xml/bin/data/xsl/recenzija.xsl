<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ulog="http://www.ftn.uns.ac.rs/uloge"
    xmlns:rc="http://www.ftn.uns.ac.rs/recenzija"
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
                        <xsl:value-of select="rc:Recenzija/nr:Revizija/nr:Naslov"/>
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p>
                        <b>Recenzent: </b>
                        <xsl:value-of select="rc:Recenzija/ulog:Recenzent/ulog:Ime"/> &#160;  <xsl:value-of select="rc:Recenzija/ulog:Recenzent/ulog:Prezime"/>
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p>
                        <b>Preporuka:</b>
                        <xsl:value-of select="rc:Recenzija/rc:Sadrzaj/rc:Preporuka"/>
                    </p>
                </div>

                <div style="margin:10px; font-size: 12px;">
                    <p>
                        <b>Pitanje:</b>
                        <br></br>
                        <xsl:for-each select="rc:Recenzija/rc:Sadrzaj/rc:Pitanja">
                            <i><xsl:value-of select="rc:TekstPitanja"/></i> 
                            <div style="margin-left:4px;">
                                <xsl:value-of select="rc:Odgovor"/>
                            </div>
                        </xsl:for-each>
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p>
                        <b>Komantare:</b>
                        <br></br>
                        <xsl:for-each select="rc:Recenzija/rc:Sadrzaj/rc:KommentarZaAutore/nr:Tekst/nr:ObicanTekst">
                            <xsl:value-of  select="nr:Tekstualni_sadzaj"/>
                            <br></br>              
                        </xsl:for-each>
                    </p>
                </div>
                <div style="margin:10px; font-size: 12px;">
                    <p>
                        <b>Dodatak:</b>
                        <br></br>
                        <xsl:value-of select="rc:Recenzija/rc:Sadrzaj/rc:Dodatak"/>
                    </p>
                </div>
            </body>
        </html>
    </xsl:template>
    
</xsl:stylesheet>