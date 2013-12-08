@echo OFF
pushd %JAVAC_PATH%
@echo /*----------------------------------------------*/
@echo     java compile
@echo /*----------------------------------------------*/
IF %1==toc (
@echo ON
javac -d %ROOT_PATH%\out -sourcepath %ROOT_PATH% @%ROOT_RELATIVE_PATH%\source_BtoC
) ELSE IF %1==mes (
@echo ON
javac -d %ROOT_PATH%\out -sourcepath %ROOT_PATH% @%ROOT_RELATIVE_PATH%\source_MesMan
) ELSE IF %1==help (
@echo ON
javac -help
@echo Enable Command
@echo   toc
@echo   mes
@echo e.g compile toc
)
popd
