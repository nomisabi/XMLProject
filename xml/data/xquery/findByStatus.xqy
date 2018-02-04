xquery version "1.0-ml";
declare namespace nr = "http://www.ftn.uns.ac.rs/naucni_rad";
for $root in fn:collection("/ftn/naucni_rad")
where $root/nr:Naucni_rad/nr:Revizija[@Status = "param"]
return $root/nr:Naucni_rad/nr:Revizija