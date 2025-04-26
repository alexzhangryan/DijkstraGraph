JAVAC = javac
JAVA = java
JUNIT = ../junit5.jar
CLASSES = *.java

startServer:
	$(JAVAC) -cp .:$(JUNIT) $(CLASSES)
$(JAVA) -cp .:$(JUNIT) WebApp

runAllTests:
	$(JAVAC) -cp .:$(JUNIT) $(CLASSES)
	$(JAVA) -cp .:$(JUNIT) org.junit.platform.console.ConsoleLauncher --select-class=FrontendTests

clean:
	rm -f *.class
