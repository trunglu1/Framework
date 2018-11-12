
CC = gcc
CFLAGS = -x c++ -fno-exceptions -c
LD = gcc
LDFLAGS = -Wl,--subsystem,windows
LIBS = -lkernel32 -luser32 -lgdi32
RC = windres

maindlg.exe:	maindlg.o maindlgres.o
	$(LD) -o maindlg.exe $(LDFLAGS) maindlg.o maindlgres.o $(LIBS)

maindlg.o:	maindlg.cpp resource.h
	$(CC) $(CFLAGS) maindlg.cpp

maindlgres.o:	maindlg.rc resource.h
	$(RC) -i maindlg.rc -o maindlgres.o
