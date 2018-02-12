xquery version "1.0-ml";
declare namespace ko = "http://www.ftn.uns.ac.rs/korisnici";
for $korisnik in doc("korisnici.xml")/ko:korisnici/ko:korisnik
        where $korisnik/ko:uloga = 'RECENZENT' and
        lower-case($korisnik/ko:ime) = lower-case('param') or
        lower-case($korisnik/ko:prezime) = lower-case('param') or 
        lower-case($korisnik/ko:korisnicko_ime) = lower-case('param') or
        lower-case($korisnik/ko:email) = lower-case('param') or
        concat(lower-case($korisnik/ko:ime),' ', lower-case($korisnik/ko:prezime)) = lower-case('param')
        return $korisnik