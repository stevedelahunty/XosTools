/*
 * Copyright (c) 2018, Extreme Networks
 * All Rights Reserved
 *
 * xmod_snoop is a simple java application which will "snoop" inside on
 * xmod file and extract various bits of information. xmods are blobs that
 * must be dissected before they can be used. They consist of multiple
 * sections. Each section consists of an 80 byte header followed by a "payload"
 * or "body". The payload can be an executable, a tarball or some other binary
 * file. The header contains a 'type' field that defines the 'type' of the
 * payload (tarball, etc), and a 'length' which defines the length of the
 * payload. Applications can walk through an xmod by looking at the header,
 * extracting the length of the payload, then either extracting the payload
 * itself or skipping down to the next section.
 *
 * Section headers are defined as follows:
 * 
 * typedef struct extr_img_format {
 *       uint16_t magic;           byte 0-1   (0xEF00)
 *       uint16_t len_hdr;         byte 2-3   (Length of header, always 0x50)
 *       uint32_t img_type;        byte 4-7   (Defines the type of payload)
 *       uint32_t len_body;        byte 8-b   (Length of the payload)
 *       uint16_t crc_header;      byte c-d
 *       uint16_t crc_body;        byte e-f
 *       uint32_t addr_start;      byte 10-13
 *       uint32_t addr_load;       byte 14-17
 *       vers_t version;           byte 18-1b (XOS Version)
 *       uint32_t timestamp;       byte 1c-1f
 *       uint32_t platform_class;  byte 20-23
 *       uint64_t platform;        byte 24-2b
 *       char     description[28]; byte 2c-47
 *       uint32_t len_sig;         byte 48-4b
 *       uint32_t pad;             byte 4c-4f
 *  } __attribute__ ((packed)) extr_img_format_t;
 *
 *
 * In the case of xmods, the payload is a tarball within a tarball. The 'outer'
 * tarball contains "envelope" information which we are most interested with.
 * In particular, the outer tarball contains a file called 'spec' which looks
 * like this:
 * 
 * installer=libupgrade.so:
 * description="This is the Extreme Optics loadable module image package":
 * pkgtype=optics:
 * preinstall=/tmp/upgrade check_existence optics:
 * filename=exos_version:part=exos:
 * filename=./bin/optics:type=bin:part=exos:proc=optics:
 * filename=./config/clidef/optics.xml:type=txt:part=exos:
 * filename=./hw-config/Extreme/X870-96x-8c/trans.yaml:type=txt:part=boot:
 * filename=./hw-config/Extreme/X870-96x-8c/preemph.yaml:type=txt:part=boot:
 * filename=./hw-config/Extreme/X870-32c/trans.yaml:type=txt:part=boot:
 * filename=./hw-config/Extreme/X870-32c/preemph.yaml:type=txt:part=boot:
 * filename=./hw-config/Extreme/X690-48t-4q-2c/trans.yaml:type=txt:part=boot:
 * filename=./hw-config/Extreme/X690-48t-4q-2c/preemph.yaml:type=txt:part=boot:
 * filename=./hw-config/Extreme/X690-48x-4q-2c/trans.yaml:type=txt:part=boot:
 * filename=./hw-config/Extreme/X690-48x-4q-2c/preemph.yaml:type=txt:part=boot:
 * filename=./hw-config/common/defaulttrans.yaml:type=txt:part=boot:
 * filename=./hw-config/common/defaultpreemph.yaml:type=txt:part=boot:
 * version=22.5.0.17:
 * linkDate=Wed Mar 21 12-15-14 EDT 2018:
 * buildBy=sdelahunty:
 * branch=priv_optics_lm:
 * platform=onie:
 * pkgname=onie-22.5.0.17-optics.xmod:
 * require=22.5.0.17:
 *
 * This class will extract this file and display it to the standard out.
 * Please feel free to use this as a starting point to create your own
 * applications.
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class xmod_snoop 
{
	public static void main(String[] args) throws IOException {
		String xmod = args[0];
		FileInputStream in = null;
		FileOutputStream out = null;
		int count;
		byte[] hdr = new byte[0x50];
		
		System.out.println("XMOD to snoop: "+xmod);

		// Open the file, extract the first header:
		try {
			in = new FileInputStream(xmod);
			short magic;

			
			count = in.read(hdr, 0, 0x50);
			System.out.println("Read "+count+" bytes\n");
			xmod_header header = new xmod_header(hdr);
			magic = header.getMagic();
			System.out.println("Magic: "+String.format("0x%02X",magic));
			System.out.println("LenHdr: "+String.format("0x%02X",header.getLenHdr()));
			System.out.println("Type: "+String.format("0x%04X",header.getImgType())+" ("+header.getImgTypeString()+")");
			System.out.println("LenBody: "+String.format("0x%04X",header.getLenBody()));
			System.out.println("Version: "+header.getVersion());
			System.out.println("Platform Class: "+String.format("0x%02X", header.getPlatformClass()));
			System.out.println("Platform Class String: "+header.getPlatformClassString());
			System.out.println("Description: "+header.getDescription());

			// Skip over the payload
			in.skip(header.getLenBody());

			// Read in the next header
			count = in.read(hdr, 0, 0x50);
			System.out.println("Read "+count+" bytes\n");

			// Create our class from the header
			header = new xmod_header(hdr);

			// Print out header info
			magic = header.getMagic();
			System.out.println("Magic: "+String.format("0x%02X",magic));
			System.out.println("LenHdr: "+String.format("0x%02X",header.getLenHdr()));
			System.out.println("Type: "+String.format("0x%04X",header.getImgType())+" ("+header.getImgTypeString()+")");
			System.out.println("LenBody: "+String.format("0x%04X",header.getLenBody()));
			System.out.println("Version: "+header.getVersion());
			System.out.println("Platform Class: "+String.format("0x%02X", header.getPlatformClass()));
			System.out.println("Platform Class String: "+header.getPlatformClassString());
			System.out.println("Description: "+header.getDescription());

			// This is the tarball now. Read the whole thing into a byte array
			byte tarball[] = new byte[header.getLenBody()];
			in.read(tarball);

			// Save it in a file.
			out= new FileOutputStream("tarball.tgz");
			out.write(tarball);
			out.close();
			in.close();

			// Extract the tarball.
			File f = new File("tar_contents");
			String cmd = "tar xvfz tarball.tgz -C tar_contents";
			f.mkdir();
			Process p = Runtime.getRuntime().exec(cmd);
			try {
				p.waitFor(); // wait until it completes
			} catch(Exception e) {
			}
			
			
			// Display the spec file.
			String fname = "tar_contents/spec";
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String line = null;
			System.out.println("\n\n=========== Contents of Spec file =======");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} finally {
		}
	}
}
