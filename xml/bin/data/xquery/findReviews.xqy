xquery version "1.0-ml";
declare namespace ko = "http://www.ftn.uns.ac.rs/korisnici";
for $korisnik in doc("korisnici.xml")/ko:korisnici/ko:korisnik
        where $korisnik/ko:uloga = 'RECENZENT'
        return $korisnik