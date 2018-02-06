<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ulog="http://www.ftn.uns.ac.rs/uloge"
    xmlns:rc="http://www.ftn.uns.ac.rs/recenzija"
    xmlns:pp="http://www.ftn.uns.ac.rs/propratnoPismo"
    xmlns:nr="http://www.ftn.uns.ac.rs/naucni_rad"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    exclude-result-prefixes="xs"
    version="2.0">   
    <xsl:output omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <html> 
            <body>
                <div style="text-align: center; font-size: 20px;">
                    <p>
                        <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Naslov"/>
                    </p>
                </div>
                <br></br>
                <div style="text-align: center; font-size: 12px;">
                    <p>
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Ime"/> &#160;  <xsl:value-of select="ulog:Prezime"/>  &#160;  &#160; 
                        </xsl:for-each>
                        <br></br>
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Institucija/ulog:Naziv"/>  &#160;  &#160; 
                        </xsl:for-each>
                        <br></br>
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Institucija/ulog:Adresa/ulog:Broj"/>  &#160;  <xsl:value-of select="ulog:Institucija/ulog:Adresa/ulog:Ulica"/>&#160;  &#160; 
                        </xsl:for-each>
                        <br></br>
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Institucija/ulog:Adresa/ulog:Mesto"/>  &#160;  <xsl:value-of select="ulog:Institucija/ulog:Adresa/ulog:Postanski_broj"/>&#160;  &#160; 
                        </xsl:for-each>
                        <br></br>
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Institucija/ulog:Email"/> &#160;  &#160; 
                        </xsl:for-each>
                    </p>
                </div>
                <div style="font-size: 14px;">
                    <p>
                        Abstract
                    </p> 
                </div>
                <div style="font-size: 12px;">
                    <p>
                        <xsl:choose>
                            <xsl:when test="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt[@xsi:type = 'nr:TStrukturiranAbstrakt']">
                                <b>Purpose: </b>
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:Purpose"/>
                                <br></br>
                                <b>Design Methodology Approach: </b>
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:DesignMethodologyApproach"/>
                                <br></br>
                                <b>Findings: </b>
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:Findings"/>
                                <br></br>
                                <b>Research Limitations Implications: </b>
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:ResearchLimitationsImplications"/>
                                <br></br>
                                <b>Originality Value: </b>
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:OriginalityValue"/>
                                <br></br>
                                <b>Practical Implications: </b>
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:PracticalImplications"/>
                                <br></br>
                                <b>Social Implications: </b>
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:SocialImplications"/>
                                <br></br>
                                
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Tekst"/>
                                <br></br>
                            </xsl:otherwise>
                        </xsl:choose>
                    </p>
                </div>
                
                <div style="font-size: 12px;">
                    <b>Keywords: </b>
                    <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/nr:Kljucna_rec">
                        <xsl:value-of select="."/>
                        <xsl:if test="position() != last()" > ,&#160; </xsl:if>
                    </xsl:for-each>
                    
                </div>
                
                <div style="font-size: 12px;">
                    <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/nr:Sadrzaj/nr:Poglavlje">
                        <div style="font-size: 14px;">
                            <p>
                                <xsl:value-of select="nr:Naziv"/>
                            </p> 
                        </div>
                        <xsl:for-each select="nr:Paragraf">
                            <xsl:for-each select="nr:Tekst">
                                <xsl:value-of select="nr:ObicanTekst/nr:Tekstualni_sadzaj"/> &#160; 
                                <xsl:value-of select="nr:MatematickaForma/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                <xsl:value-of select="nr:Highlight/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                <p style="font-size: 6px;"><xsl:value-of select="nr:Footnote/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; </p>
                                <i><xsl:value-of select="nr:Citat/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; </i>
                                <u style="font-color:blue;"><xsl:value-of select="nr:Link/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; </u>
                            </xsl:for-each>
                            
                            <xsl:choose>
                                <xsl:when test="nr:Lista[@Tip = 'neuređena']">
                                    <ul>
                                        <xsl:for-each select="nr:Lista/nr:Stavke">
                                       <li>
                                           <xsl:value-of select="@Labela"/>
                                       </li> 
                                    </xsl:for-each>
                                    </ul>
                                </xsl:when>
                                <xsl:otherwise>
                                    <ol>
                                        <xsl:for-each select="nr:Lista/nr:Stavke">
                                            <li>
                                                <xsl:value-of select="./@Labela"/>1
                                            </li> 
                                        </xsl:for-each>
                                    </ol>
                                </xsl:otherwise>
                            </xsl:choose>
                            
                            <xsl:for-each select="nr:Tabela">
                                <table>
                                    <xsl:for-each select="nr:Red">
                                        <tr>
                                        <xsl:for-each select="nr:Celija">
                                            <td><xsl:value-of select="."/></td>
                                        </xsl:for-each>
                                        </tr>
                                    </xsl:for-each>
                                </table>
                            </xsl:for-each>
                            
                            <br></br>
                        </xsl:for-each>
                        
                    </xsl:for-each>
                    
                </div>
                
            </body>
        </html>
        
    </xsl:template>    
</xsl:stylesheet>