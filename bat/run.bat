pushd %JAVA_PATH%
@echo /*----------------------------------------------*/
@echo     java run
@echo /*----------------------------------------------*/
java -classpath %ROOT_PATH%\out\ %1 %2 %3 %4 %5 %6 %7 %8 %9
popd
