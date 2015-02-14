@ECHO OFF

SET WAR_PATH=C:\Users\PCP57\workspace_perso\goot\target
SET PROJECT_GRAILS_PATH=C:\Users\PCP57\workspace_perso\goot
SET GRAILS_PATH="C:\Users\PCP57\Desktop\springsource\grails-2.1.0\bin"
SET USER_GRAILS_PATH=C:\Users\PCP57\.grails\2.1.0\projects

ECHO Remove old war
del %WAR_PATH%\GooT-0.1.war

ECHO ###################
ECHO ### COMPILATION ###
ECHO ###################
ECHO          #
ECHO          #
ECHO ###################
ECHO ### 	GOOT	 ###
ECHO ###################

cd %PROJECT_GRAILS_PATH%
CALL %GRAILS_PATH%\grails clean
md %USER_GRAILS_PATH%\GooT\resources\grails-app
CALL %GRAILS_PATH%\grails prod war

ECHO ##########################
ECHO ### END OF COMPILATION ###
ECHO ##########################

SET TOOLS_PATH=C:\Users\PCP57\Desktop\Perso\Tools
SET SSH_ACCESS=root@goot.outsidethecircle.eu
SET SSH_PATH=/root/DEPLOY
SET PWD=mk3wm8qxB2kA 

START %TOOLS_PATH%/pscp -pw %PWD% %WAR_PATH%\GooT-0.1.war  %SSH_ACCESS%:%SSH_PATH%/ROOT.war

ECHO UPLOADED
ECHO .
ECHO ..
ECHO ...
ECHO press any key to deploy !

PAUSE

%TOOLS_PATH%/plink -ssh -pw %PWD% %SSH_ACCESS% %SSH_PATH%/goot.sh ok-1

ECHO FILE SENT
pause