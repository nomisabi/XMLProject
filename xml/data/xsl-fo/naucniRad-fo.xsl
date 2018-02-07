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
                        <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Naslov"/>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="20px" text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center">
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Ime"/> &#160;  <xsl:value-of select="ulog:Prezime"/>  &#160;  &#160; 
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center">
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Institucija/ulog:Naziv"/>  &#160;  &#160; 
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center">
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Institucija/ulog:Adresa/ulog:Broj"/>  &#160;  <xsl:value-of select="ulog:Institucija/ulog:Adresa/ulog:Ulica"/>&#160;  &#160; 
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center">
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Institucija/ulog:Adresa/ulog:Mesto"/>  &#160;  <xsl:value-of select="ulog:Institucija/ulog:Adresa/ulog:Postanski_broj"/>&#160;  &#160; 
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px" text-align="center">
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/ulog:Autor">
                            <xsl:value-of select="ulog:Email"/> &#160;  &#160; 
                        </xsl:for-each>
                    </fo:block>
            
                    <fo:block font-family="sans-serif" font-size="20px" text-align="center"> 
                        &#160;
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="14px"> 
                        Abstract
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px"> 
                        
                        <xsl:if test="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:StukturiranAbstakt">
                                <fo:block>
                                    <fo:block font-weight="bold"> Purpose: </fo:block>
                                	<xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:StukturiranAbstakt/nr:Purpose"/>
                                </fo:block>
                                <fo:block> 
                                    <fo:block font-weight="bold">Design Methodology Approach: </fo:block> 
                                    <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:StukturiranAbstakt/nr:DesignMethodologyApproach"/>
                                 </fo:block> 
                                <fo:block> 
                                    <fo:block font-weight="bold">Findings: </fo:block> 
                                    <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:StukturiranAbstakt/nr:Findings"/>
                                </fo:block> 
                                <fo:block> 
                                    <fo:block font-weight="bold">Research Limitations Implications: </fo:block> 
                                    <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:StukturiranAbstakt/nr:ResearchLimitationsImplications"/>
                                </fo:block> 
                                <fo:block> 
                                    <fo:block font-weight="bold">Originality Value:</fo:block> 
                                    <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:StukturiranAbstakt/nr:OriginalityValue"/>
                                </fo:block> 
                                <fo:block> 
                                    <fo:block font-weight="bold">Practical Implications: </fo:block> 
                                    <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:StukturiranAbstakt/nr:PracticalImplications"/>
                                </fo:block> 
                                <fo:block> 
                                    <fo:block font-weight="bold">Social Implications: </fo:block> 
                                    <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:StukturiranAbstakt/nr:SocialImplications"/>
                                </fo:block> 
                                
                            </xsl:if>
                            <xsl:if test="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:NeStrukturiranAbstrakt">
                              
                                <xsl:value-of select="nr:Naucni_rad/nr:Revizija[last()]/nr:Abstrakt/nr:NeStrukturiranAbstrakt/nr:Tekst"/>
                                
                            </xsl:if>                    
                        
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px">  &#160;</fo:block>
                    <fo:block font-family="sans-serif" font-size="12px"  font-weight="bold">Keywords: </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px"> 
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/nr:Kljucna_rec">
                            <xsl:value-of select="."/>
                            <xsl:if test="position() != last()" > ,&#160; </xsl:if>
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px"> 
                        &#160; 
                    </fo:block>
                    <fo:block font-family="sans-serif" font-size="12px"> 
                        <xsl:for-each select="nr:Naucni_rad/nr:Revizija[last()]/nr:Sadrzaj/nr:Poglavlje">
                            <fo:block font-size="14px">
                                <xsl:value-of select="nr:Naziv"/>
                            </fo:block>
                            <fo:block>
                                <xsl:for-each select="nr:Paragraf">
                                    
                                    <xsl:for-each select="nr:Tekst">
                                        <xsl:if test="nr:ObicanTekst">
                                            
                                            <xsl:value-of select="nr:ObicanTekst/nr:Tekstualni_sadzaj"/> &#160; 
                                        </xsl:if>
                                        <xsl:if test="nr:MatematickaForma">
                                            
                                            <xsl:value-of select="nr:MatematickaForma/nr:ObicanTekst/nr:Tekstualni_sadzaj"/> &#160; 
                                        </xsl:if>
                                        <xsl:if test="nr:Highlight">
                                            
                                           <xsl:choose>
                       
                                                <xsl:when test="nr:Highlight[@BojaPozadine='blue']">
                                                
                                                <fo:inline background-color="blue">
                                                    <xsl:value-of select="nr:Highlight/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                                </fo:inline>
                                            </xsl:when> 
                                                <xsl:when test="nr:Highlight[@BojaPozadine='red']">
                                                
                                                <fo:inline background-color="red">
                                                    <xsl:value-of select="nr:Highlight/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                                </fo:inline>
                                            </xsl:when> 
                                                <xsl:when test="nr:Highlight[@BojaPozadine='yellow']">
                                                
                                                <fo:inline background-color="yellow">
                                                    <xsl:value-of select="nr:Highlight/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                                </fo:inline>
                                            </xsl:when> 
                                                <xsl:when test="nr:Highlight[@BojaPozadine='green']">
                                                
                                                <fo:inline background-color="green">
                                                    <xsl:value-of select="nr:Highlight/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                                </fo:inline>
                                            </xsl:when> 
                                                <xsl:otherwise>
                                                    <xsl:value-of select="nr:Highlight/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                          
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </xsl:if>
                                        <xsl:if test="nr:Footnote">
                                            
                                            <fo:inline font-size="6px">
                                            <xsl:value-of select="nr:Footnote/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                            </fo:inline>
                                        </xsl:if>
                                        <xsl:if test="nr:Citat">
                                            
                                            <fo:inline font-style="italic">
                                            <xsl:value-of select="nr:Citat/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                            </fo:inline>
                                        </xsl:if>
                                        <xsl:if test="nr:Link">
                                            
                                            <fo:block  color="blue" text-decoration="underline">
                                                <xsl:value-of select="nr:Link/nr:ObicanTekst/nr:Tekstualni_sadzaj"/>&#160; 
                                            </fo:block  >
                                        </xsl:if>
                                    </xsl:for-each>
                                    
                                    
                               <!--     
                                    <xsl:for-each select="nr:Figura">
                                        fig
                                        <xsl:value-of select="@Url"/>&#160; 
                                        <fo:external-graphic src="url(@Url)"  content-height="scale-to-fit" height="2.00in"  content-width="2.00in" scaling="non-uniform"/>
                                        &#160; 
                                    </xsl:for-each>
                                   -->   
                                    
                                    
                                    <xsl:for-each select="nr:Tabela">
                                        
                                        <fo:table font-family="serif" margin="10px auto 10px auto" border="1px">
                                            <fo:table-body>
                                                <xsl:for-each select="nr:Red">
                                                    <fo:table-row>
                                                        <xsl:for-each select="nr:Celija">
                                                            <fo:table-cell padding="5px" border="1px solid darkgrey">
                                                            	<fo:block>
                                                                	<xsl:value-of select="."/>
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </xsl:for-each>
                                                    </fo:table-row>
                                                </xsl:for-each>
                                            </fo:table-body>
                                        </fo:table>
                                    </xsl:for-each>
                                    
                                    
                                    
                                    <xsl:for-each select="nr:Lista">
                                        
                                        <xsl:choose>
                                        <xsl:when test="@Tip = 'neuređena'">
                                            <fo:list-block >
                                                <!-- list item -->
                                                <xsl:for-each select="nr:Stavke">
                                                <fo:list-item space-after="0.5em">
                                                    <!-- insert a bullet -->
                                                    <fo:list-item-label start-indent="1em" end-indent="label-end()">
                                                        <fo:block>
                                                            <fo:inline>• </fo:inline>
                                                        </fo:block>
                                                    </fo:list-item-label>
                                                    <!-- list text -->
                                                    
                                                    <fo:list-item-body start-indent="body-start()">
                                                        <fo:block>
                                                            <xsl:value-of select="@Labela"/>
                                                        </fo:block>
                                                    </fo:list-item-body>
                                                    
                                                </fo:list-item>
                                                </xsl:for-each>
                                            </fo:list-block>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <fo:list-block >
                                            <!-- list item -->
                                            <xsl:for-each select="nr:Stavke">
                                            <fo:list-item space-after="0.5em">
                                                <fo:list-item-label start-indent="1em"  end-indent="label-end()">
                                                    <fo:block>
                                                        <fo:inline><xsl:number/>.</fo:inline> 
                                                    </fo:block>
                                                </fo:list-item-label>
                                                <fo:list-item-body start-indent="body-start()">
                                                    <fo:block>
                                                        <xsl:value-of select="@Labela"/>
                                                    </fo:block>
                                                </fo:list-item-body>
                                            </fo:list-item>
                                            </xsl:for-each>
                                            </fo:list-block>
                                        </xsl:otherwise>
                                        </xsl:choose>
                                        &#160; 
                                    </xsl:for-each>
                                </xsl:for-each>
                            </fo:block>
                            <fo:block font-family="sans-serif" font-size="12px"> 
                                &#160; 
                            </fo:block>
                        </xsl:for-each>
                    </fo:block>
                    
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
