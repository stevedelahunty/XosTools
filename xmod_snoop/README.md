# xmod_snoop
## Introduction
xmod_snoop is a simple java application which will "snoop" inside on
xmod file and extract various bits of information. xmods are blobs that
must be dissected before they can be used. They consist of multiple
sections. Each section consists of an 80 byte header followed by a "payload"
or "body". The payload can be an executable, a tarball or some other binary
file. The header contains a 'type' field that defines the 'type' of the
payload (tarball, etc), and a 'length' which defines the length of the
payload. Applications can walk through an xmod by looking at the header,
extracting the length of the payload, then either extracting the payload
itself or skipping down to the next section.

## Section header format

 Section headers are defined as follows:
 
  typedef struct extr_img_format {
        uint16_t magic;           byte 0-1   (0xEF00)
        uint16_t len_hdr;         byte 2-3   (Length of header, always 0x50)
        uint32_t img_type;        byte 4-7   (Defines the type of payload)
        uint32_t len_body;        byte 8-b   (Length of the payload)
        uint16_t crc_header;      byte c-d
        uint16_t crc_body;        byte e-f
        uint32_t addr_start;      byte 10-13
        uint32_t addr_load;       byte 14-17
        vers_t version;           byte 18-1b (XOS Version)
        uint32_t timestamp;       byte 1c-1f
        uint32_t platform_class;  byte 20-23
        uint64_t platform;        byte 24-2b
        char     description[28]; byte 2c-47
        uint32_t len_sig;         byte 48-4b
        uint32_t pad;             byte 4c-4f
   } __attribute__ ((packed)) extr_img_format_t;
 
 

## Spec file format
  In the case of xmods, the payload is a tarball within a tarball. The 'outer'
  tarball contains "envelope" information which we are most interested with.
  In particular, the outer tarball contains a file called 'spec' which looks
  like this:
  
  installer=libupgrade.so:
  description="This is the Extreme Optics loadable module image package":
  pkgtype=optics:
  preinstall=/tmp/upgrade check_existence optics:
  filename=exos_version:part=exos:
  filename=./bin/optics:type=bin:part=exos:proc=optics:
  filename=./config/clidef/optics.xml:type=txt:part=exos:
  filename=./hw-config/Extreme/X870-96x-8c/trans.yaml:type=txt:part=boot:
  filename=./hw-config/Extreme/X870-96x-8c/preemph.yaml:type=txt:part=boot:
  filename=./hw-config/Extreme/X870-32c/trans.yaml:type=txt:part=boot:
  filename=./hw-config/Extreme/X870-32c/preemph.yaml:type=txt:part=boot:
  filename=./hw-config/Extreme/X690-48t-4q-2c/trans.yaml:type=txt:part=boot:
  filename=./hw-config/Extreme/X690-48t-4q-2c/preemph.yaml:type=txt:part=boot:
  filename=./hw-config/Extreme/X690-48x-4q-2c/trans.yaml:type=txt:part=boot:
  filename=./hw-config/Extreme/X690-48x-4q-2c/preemph.yaml:type=txt:part=boot:
  filename=./hw-config/common/defaulttrans.yaml:type=txt:part=boot:
  filename=./hw-config/common/defaultpreemph.yaml:type=txt:part=boot:
  version=22.5.0.17:
  linkDate=Wed Mar 21 12-15-14 EDT 2018:
  buildBy=sdelahunty:
  branch=priv_optics_lm:
  platform=onie:
  pkgname=onie-22.5.0.17-optics.xmod:
  require=22.5.0.17:

## Compiling and running this sample
Everything is done from the Makefile. To compile, simply type 'make'. To run the
sample, type 'make run'. To clean up, type 'make clean'.

## Sample output or running this application
-bash-4.2$ make
javac xmod_snoop.java xmod_header.java
-bash-4.2$ make run
java xmod_snoop sample.xmod
XMOD to snoop: sample.xmod
Read 80 bytes

Magic: 0xEF00
LenHdr: 0x50
Type: 0x0006 (IMG_TYPE_EXOS_UPGRADE_PKG)
LenBody: 0x9DA0
Version: 22.5.0.1
Platform Class: 0x10100
Platform Class String: Unknown
Description: ONIE X86
Read 80 bytes

Magic: 0xEF00
LenHdr: 0x50
Type: 0x0004 (IMG_TYPE_TAR_BALL)
LenBody: 0x11B7ED
Version: 22.5.0.1
Platform Class: 0x10100
Platform Class String: Unknown
Description: ONIE X86


=========== Contents of Spec file =======
installer=libupgrade.so:
description="This is the Extreme Optics loadable module image package":
pkgtype=optics:
preinstall=/tmp/upgrade check_existence optics:
filename=exos_version:part=exos:
filename=./bin/optics:type=bin:part=exos:proc=optics:
filename=./config/clidef/optics.xml:type=txt:part=exos:
filename=./hw-config/Extreme/X870-96x-8c/trans.yaml:type=txt:part=boot:
filename=./hw-config/Extreme/X870-96x-8c/preemph.yaml:type=txt:part=boot:
filename=./hw-config/Extreme/X870-32c/trans.yaml:type=txt:part=boot:
filename=./hw-config/Extreme/X870-32c/preemph.yaml:type=txt:part=boot:
filename=./hw-config/Extreme/X690-48t-4q-2c/trans.yaml:type=txt:part=boot:
filename=./hw-config/Extreme/X690-48t-4q-2c/preemph.yaml:type=txt:part=boot:
filename=./hw-config/Extreme/X690-48x-4q-2c/trans.yaml:type=txt:part=boot:
filename=./hw-config/Extreme/X690-48x-4q-2c/preemph.yaml:type=txt:part=boot:
filename=./hw-config/common/defaulttrans.yaml:type=txt:part=boot:
filename=./hw-config/common/defaultpreemph.yaml:type=txt:part=boot:
version=22.5.0.17:
linkDate=Wed Mar 21 12-15-14 EDT 2018:
buildBy=sdelahunty:
branch=priv_optics_lm:
platform=onie:
pkgname=onie-22.5.0.17-optics.xmod:
require=22.5.0.17:
