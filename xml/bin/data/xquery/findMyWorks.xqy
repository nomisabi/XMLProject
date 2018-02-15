xquery version "1.0-ml";
declare namespace nr = "http://www.ftn.uns.ac.rs/naucni_rad";
declare namespace ns2 = "http://www.ftn.uns.ac.rs/uloge";
for $root in fn:collection("/ftn/naucni_rad")
where $root/nr:Naucni_rad/nr:Revizija/ns2:Autor[@Id = "param"]
return $root/nr:Naucni_rad