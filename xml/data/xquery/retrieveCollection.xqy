xquery version "1.0-ml";

for $korisnici in fn:doc("korisnici.xml")
return $korisnici