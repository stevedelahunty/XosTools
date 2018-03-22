
public class xmod_header
{
	public byte[] header;
	
	public xmod_header(byte[] hdr) {
		header = hdr;
		
	}

	public short getMagic() {
		int val = ((header[0]&0xff) << 8) | (header[1]&0xff);
		return((short)val);
		
	}
	public short getLenHdr() {
		int val = ((header[2]&0xff) << 8) | (header[3]&0xff);
		return((short)val);
		
	}
	public int getImgType() {
		int val = ((header[4]&0xff) << 24) | ((header[5]&0xff) << 16) | ((header[6]&0xff) << 8) | (header[7]&0xff);
		return(val);
		
	}
	public String getImgTypeString() {
		int val = getImgType();
		switch (val) {
		case 1: return("IMG_TYPE_EWARE_BIN");
		case 2: return("IMG_TYPE_BOOTLOADER_BIN");
		case 3: return("IMG_TYPE_ELF_APP");
		case 4: return("IMG_TYPE_TAR_BALL");
		case 5: return("IMG_TYPE_DIAG_BIN");
		case 6: return("IMG_TYPE_EXOS_UPGRADE_PKG");
		case 7: return("IMG_TYPE_BOOTROM_UPGRADE_PKG");
		case 8: return("IMG_TYPE_BOOTROM_INSTALL_SCRIPT");
		case 9: return("IMG_TYPE_BOOTSTRAP_BIN");
		case 10: return("IMG_TYPE_MARINER_MSM");
		case 11: return("IMG_TYPE_MARINER_IO_VMLINUX");
		case 12: return("IMG_TYPE_MSM_LINUX");
		case 13: return("IMG_TYPE_IO_LINUX");
		case 14: return("IMG_TYPE_OLYMPIC_MSM");
		case 15: return("IMG_TYPE_FPGA_BIN");
		case 16: return("IMG_TYPE_UC_P");
		case 17: return("IMG_TYPE_UC_S");
		case 18: return("IMG_TYPE_BIOS");
		case 19: return("IMG_TYPE_BOOTFLASH_BIN");
		default: return("Unknown");
		}
	}
	public int getLenBody() {
		int val = ((header[8]&0xff) << 24) | ((header[9]&0xff) << 16) | ((header[0xa]&0xff) << 8) | (header[0xb]&0xff);
		return(val);
		
	}
	public String getVersion() {
		String val = String.format("%d.%d.%d.%d",(header[0x18]&0xff),(header[0x19]&0xff),(header[0x20]&0xff),(header[0x21]&0xff));
		return(val);
	}
	public int getPlatformClass() {
		int val = ((header[0x20]&0xff) << 24) | ((header[0x21]&0xff) << 16) | ((header[0x21]&0xff) << 8) | (header[0x22]&0xff);
		return(val);
	}
	public String getPlatformClassString() {
		int val = getPlatformClass();
		switch (val) {
		case 1: return("PLATFORM_CLASS_SUMMIT_300");
		case 0x10000: return("PLATFORM_CLASS_SUMMIT");
		case 0x200000: return("PLATFORM_CLASS_HARPOON");
		case 0x20000: return("PLATFORM_CLASS_ALPINE");
		case 0x30000: return("PLATFORM_CLASS_BLACK_DIAMOND");
		case 0x40000: return("PLATFORM_CLASS_MARINER");
		case 0x50000: return("PLATFORM_CLASS_I386");
		case 0x80000: return("PLATFORM_CLASS_ASPEN");
		case 0x100000: return("PLATFORM_CLASS_OLYMPIC");
			//case 0x200000: return("PLATFORM_CLASS_VOYAGER");
		case 0x400000: return("PLATFORM_CLASS_EVEREST");
		default: return("Unknown");
		}
	}
	public String getDescription() {
		byte[] val = new byte[28];
		System.arraycopy(header, 0x2c, val, 0, 28);
		String s = new String(val);
		return(s);
	}
	
	
}
