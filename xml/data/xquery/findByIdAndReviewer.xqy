xquery version "1.0-ml";
declare namespace nr = "http://www.ftn.uns.ac.rs/naucni_rad";
declare namespace ns2 = "http://www.ftn.uns.ac.rs/uloge";
declare namespace ns3 = "http://www.ftn.uns.ac.rs/recenzija";
for $root in fn:collection("/ftn/naucni_rad")
where $root/nr:Naucni_rad/nr:Revizija/ns3:Recenzija[@Status = "status"] and 
      $root/nr:Naucni_rad/nr:Revizija/ns3:Recenzija/ns2:Recenzent[@Id="param"] and 
      $root/nr:Naucni_rad[@Id="id"] and 
      $root/nr:Naucni_rad/nr:Revizija[@Id="revizija"]
return $root/nr:Naucni_rad