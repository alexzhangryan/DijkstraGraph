JAVAC = javac
JAVA = java
JUNIT = ../junit5.jar
CLASSES = *.java

startServer:
	$(JAVAC)$(CLASSES)
	$(JAVA) WebApp

runAllTests:
	$(JAVAC) -cp .:$(JUNIT)$(CLASSES)
	$(JAVA) -cp .:$(JUNIT) org.junit.platform.console.ConsoleLauncher \ --class-path . \ --scan-classpath \ --include-classname 'FrontendTests'

clean:
	rm -f *.class
