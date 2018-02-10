interface RevizijaInterface{
naslov: string;
autor: AutorInterface;
abstrakt:AbstraktInterface 
kljucnaRec: string[];
sadrzaj: SadrzajInterface ;
referenca: ReferencaInterface[];
//List<Recenzija> recenzija;
//ProptatnoPismo proptatnoPismo;
status: string;
id:string;
}

interface AbstraktInterface{
    neStrukturiranAbstrakt: NeStrukturiranAbstraktInterface;
    stukturiranAbstakt: StukturiranAbstaktInterface;
}

interface NeStrukturiranAbstraktInterface{
    tekst:string;
}

interface StukturiranAbstaktInterface{
    purpose: string;
    designMethodologyApproach: string;
    findings: string;
    originalityValue: string;
    researchLimitationsImplications: string;
    practicalImplications: string;
    socialImplications: string;
}

interface SadrzajInterface{
    poglavlje: PoglavljeInterface[];
}

interface ReferencaInterface{
     referencaClanak: ReferencaClanakInterface;
     referencaKnjiga: ReferencaKnjigaInterface;
     referencaWeb: ReferencaWebInterface;
     referencaNaucniRad: ReferencaNaucniRadInterface;
     referencaPoglavlja: ReferencaPoglavljaInterface;
     referencaRecenzija: ReferencaRecenzijaInterface;
}

interface PoglavljeInterface{
    naziv: string;
    paragraf: ParagrafInterface;
    poglavlje: PoglavljeInterface[];
    id: string;
}

interface AutorInterface{
    ime:string;
    prezime: string;
    korisnickoIme: string;
    lozinka: string;
    email:string;
    id: string;
}

interface ReferencaClanakInterface{
    naziv:string;
    oznak: string;
    godina: number;
    strana:number;
}
interface ReferencaKnjigaInterface{

}
interface ReferencaNaucniRadInterface{
    naucniRad: string;
}
interface ReferencaWebInterface{
    naziv:string;
    link:string;
    datum:Date;
}
interface ReferencaRecenzijaInterface{
    recenzija:string;
}
interface ReferencaPoglavljaInterface{
    poglavlje:string
}

interface ParagrafInterface{
    tekst: TekstInterface;
    tabela: TabelaInterface;
    lista: ListaInterface;
}

interface TekstInterface{
    obicanTekstOrHighlightOrCitat: ObicanTekstOrHighlightOrCitatInterface[];
    
}
interface ObicanTekstOrHighlightOrCitatInterface{
    obicanTekts: ObicanInterface

}
interface ObicanInterface{
   
}
interface TabelaInterface{
    red: RedInterface[];
}
interface RedInterface{
    celija: string[];
}

interface ListaInterface{
    stavke: StavkeInterface;
    lista: ListaInterface;
    tip: string;
}

interface StavkeInterface{
    labela:string;
    id:string;
}