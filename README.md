# AWS-Cloud-Project--CP-AP-System-Implementation
<ul>
<li> Implemented CA and AP systems (CAP Theorem - Consistency, Availability, Network Partition) </li>
<li> Used master-slave architecture and three EC2 instances to demonstrate CA and AP System.</li>
<li> Technologies: Java, AWS, EC2 Instances.</li>
</ul>

<b>Setup:</b>

<br><b>Configuration:</b></br><p>
  CAP_configuration=AP
  <p>#CAP_configuration=AP </p>
  master=true <br/>
  slave1=false <br/>
  slave2=false <br/>

  master_ip=172.30.0.251 <br/>
  master_internal_port=8080 <br/>
  master_external_port=80 <br/>

  slave1_ip=172.30.1.88 <br/>
  slave1_internal_port=8080 <br/>
  slave1_external_port=80 <br/>

  slave2_ip=172.30.2.125 <br/>
  slave2_internal_port=8080 <br/>
  slave2_external_port=80 <br/>
</p>
