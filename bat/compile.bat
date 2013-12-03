pushd %JAVAC_PATH%
@echo /*----------------------------------------------*/
@echo     java compile
@echo /*----------------------------------------------*/
IF %1==toc (
javac -d %ROOT_PATH%\out -sourcepath %ROOT_PATH% @%ROOT_RELATIVE_PATH%\source_BtoC
) ELSE IF %1==mes (
javac -d %ROOT_PATH%\out -sourcepath %ROOT_PATH% @%ROOT_RELATIVE_PATH%\source_MesMan
) ELSE IF %1==help (
@echo Enable Command
@echo   toc
@echo   mes
@echo e.g compile toc
)
popd
