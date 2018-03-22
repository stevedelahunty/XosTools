EXE=xmod_snoop
SRC=xmod_snoop.java xmod_header.java
SNOOP=xmod_snoop
XMOD=sample.xmod

$(EXE) : $(SRC)
	javac $(SRC)

run:
	java $(SNOOP) $(XMOD)

clean:
	rm -rf tar_contents
	rm -f *.class
	rm -f tarball.tgz
	rm -f *~
