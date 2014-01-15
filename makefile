# Program packages and files
PACKAGES = patonemattrix 

# Java compiler
JAVAC = javac
JVM = 1.7

# Directory for compiled binaries
# - trailing slash is important!
BIN = ./bin/

# Directory of source files
# - trailing slash is important!
SRC = ./src/

# Java compiler flags
JAVAFLAGS = -g -d $(BIN) -cp "lib/*":src/ -target $(JVM)


# Creating a .class file
COMPILE = $(JAVAC) $(JAVAFLAGS)

EMPTY =

JAVA_FILES = $(subst $(SRC), $(EMPTY), $(wildcard $(SRC)*.java))

PACKAGEDIRS = $(addprefix $(SRC), $(PACKAGES))
PACKAGEFILES = $(subst $(SRC), $(EMPTY), $(foreach DIR, $(PACKAGEDIRS), $(wildcard $(DIR)/*.java)))
ALL_FILES = $(PACKAGEFILES) $(JAVA_FILES)


CLASS_FILES = $(ALL_FILES:.java=.class)


# Compile all .java files as well as the test
all : $(addprefix $(BIN), $(CLASS_FILES))

$(BIN)%.class : $(SRC)%.java
	$(COMPILE) $<

# Run JPieceTest
run:
	java -cp bin/ patonemattrix.PatoneMattrix

clean :
	rm -rf $(BIN)* 
