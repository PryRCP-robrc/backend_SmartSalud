@echo off
REM ===================================================================
REM  Smart Salud - Backend Spring Boot
REM  Doble click para arrancar. Requiere:
REM   - PostgreSQL corriendo con BD policlinico_prueba
REM   - Java 21 instalado
REM
REM  IMPORTANTE: Edita DB_PASSWORD con TU password real.
REM  Por seguridad este archivo se publica con un valor de ejemplo.
REM ===================================================================

REM Variables de entorno para la base de datos — EDITA CON TUS VALORES
set DB_URL=jdbc:postgresql://localhost:5432/policlinico_prueba
set DB_USERNAME=postgres
set DB_PASSWORD=cambiame_con_tu_password

REM Aviso si no editaste el password
if "%DB_PASSWORD%"=="cambiame_con_tu_password" (
    echo.
    echo ============================================================
    echo  ATENCION: Edita start.bat y pon tu password de Postgres
    echo  en la variable DB_PASSWORD antes de arrancar.
    echo ============================================================
    echo.
    pause
    exit /b 1
)

echo ===================================================================
echo  Arrancando Backend Spring Boot...
echo  URL:      %DB_URL%
echo  Usuario:  %DB_USERNAME%
echo  Puerto:   8080
echo ===================================================================
echo.

call gradlew.bat bootRun

echo.
echo Backend detenido. Presiona cualquier tecla para cerrar.
pause >nul
