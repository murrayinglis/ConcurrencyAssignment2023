JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
.SUFFIXES: .java .class

SRCDIR=src/clubSimulation
BINDIR=bin

$(BINDIR)/clubSimulation/%.class: $(SRCDIR)/%.java 
	$(JAVAC) -d $(BINDIR) -cp $(BINDIR):$(SRCDIR) -sourcepath $(SRCDIR) $<

CLASSES=GridBlock.class PeopleCounter.class PeopleLocation.class ClubGrid.class Clubgoer.class CounterDisplay.class ClubView.class ClubSimulation.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/clubSimulation/%.class)

default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/clubSimulation/*.class
run: $(CLASS_FILES)
	$(JAVA) -cp $(BINDIR) clubSimulation.ClubSimulation 10 25 25 5
