param (
    [Parameter(Mandatory = $true)]
    [string]$projectFolder
)

# Vérifier si le dossier de projet existe
if (-not (Test-Path $projectFolder -PathType Container)) {
    Write-Host "Le chemin du dossier de projet spécifié n'existe pas."
    exit 1
}

# Chemin vers le fichier .java à compiler
$javaFilePath = "C:\S5\FRAMEWORK\Framework-Project\org\framework"
$libFram="C:\S5\FRAMEWORK\Framework-Project\lib"

$lib = Join-Path -Path $projectFolder -ChildPath "lib"
$jarFile = Join-Path -Path $lib -ChildPath "project.jar"

# Copier tous les fichiers .jar du libFram vers le lib
$sourceJarFiles = Join-Path -Path $libFram -ChildPath "*.jar"
Copy-Item -Path $sourceJarFiles -Destination $lib -Force

# Supprimer seulement le fichier project.jar s'il existe
if (Test-Path $jarFile) {
    Remove-Item -Path $jarFile -Force
    Write-Host "Le fichier project.jar existant a été supprimé."
}

# Créer le dossier lib s'il n'existe pas déjà
if (-not (Test-Path $lib -PathType Container)) {
    New-Item -Path $lib -ItemType Directory | Out-Null
    Write-Host "Le sous-dossier lib a été créé."
}

# Crée un dossier temporaire pour les fichiers .class
$tempDir = Join-Path -Path $projectFolder -ChildPath "temp"

if (Test-Path $tempDir -PathType Container) {
    Remove-Item -Path $tempDir -Recurse -Force
}

New-Item -Path $tempDir -ItemType Directory | Out-Null

# Créer un dossier temporaire pour les fichiers .java needed tto change into temperary direc
$tempJavaDir = Join-Path -Path $env:TEMP -ChildPath "tempJavaSource"

if (-Not (Test-Path $tempJavaDir)) {
    New-Item -Path $tempJavaDir -ItemType Directory | Out-Null
}
else {
    # Nettoyer le dossier temporaire s'il existe déjà
    Remove-Item -Path $tempJavaDir\* -Force
}

# Compilation des fichiers .java vers le répertoire temporaire
if (Test-Path $javaFilePath -PathType Container) {
    Set-Location $javaFilePath

    # Rechercher récursivement tous les fichiers .java dans tous les sous-dossiers
    $javaFiles = Get-ChildItem -Recurse -Filter "*.java" -File
    if ($javaFiles) {
        # Copier tous les fichiers .java dans le dossier temporaire
        foreach ($file in $javaFiles) {
            Copy-Item -Path $file.FullName -Destination $tempJavaDir
        }
        
        # Compiler tous les fichiers .java dans le dossier temporaire
        Set-Location $tempJavaDir
        try {
            javac -cp  "$libFram\*" -d $tempDir *.java
            Write-Host "Les fichiers .java ont été compilés vers le répertoire temporaire."
        }
        catch {
            Write-Host "Erreur lors de la compilation des fichiers .java : $($_.Exception.Message)" -ForegroundColor Red
            exit 1
        }
       # finally {
            # Nettoyage du dossier temporaire
            Write-Host "Le dossier temporaire pour les fichiers .java a été supprimé."
        #}
    }
    else {
        Write-Host "Aucun fichier .java trouvé dans le dossier ou ses sous-dossiers."
        exit 1
    }
}
else {
    Write-Host "Le dossier org/framework n'existe pas ou est vide."
    exit 1
}

# Chemin complet vers l'exécutable jar dans votre installation JDK
$jarPath = "C:\Program Files\Java\jdk-21\bin\jar.exe"

# Création du fichier .jar
& $jarPath cvf $jarFile -C $tempDir .
Write-Host "Le fichier JAR a été créé : $jarFile"

# Nettoyage du dossier des fichiers .class
if (Test-Path $tempDir) {
    Remove-Item -Path $tempDir -Recurse -Force
    Write-Host "Le dossier temporaire pour les fichiers .class a été supprimé."
}

# Définition du chemin du dossier nécessaire pour le projet Tomcat  needed tto change into temperary direc
$cheminProjetTomcat = "C:\S5\FRAMEWORK\Framework-Project\script\bin"

# Création de la variable temporaire "temp"
$env:temp = $cheminProjetTomcat

# Vérification en affichant le contenu de la variable temporaire
Write-Host "Le dossier nécessaire pour le projet Tomcat a été assigné à la variable temporaire temp : $env:temp"

# Définition des chemins des sous-dossiers à supprimer et recréer
$webInfPath = Join-Path -Path $env:temp -ChildPath "WEB-INF"
$libPath = Join-Path -Path $webInfPath -ChildPath "lib"
$classesPath = Join-Path -Path $webInfPath -ChildPath "classes"

# Vérification si les sous-dossiers existent déjà et suppression si nécessaire
if (Test-Path $libPath -PathType Container) {
    Remove-Item -Path $libPath -Recurse -Force
    Write-Host "Le sous-dossier lib existant a été supprimé."
}

if (Test-Path $webInfPath -PathType Container) {
    Remove-Item -Path $webInfPath -Recurse -Force
    Write-Host "Le sous-dossier web-inf existant a été supprimé."
}

if (Test-Path $webInfPath -PathType Container) {
    Remove-Item -Path $webInfPath -Recurse -Force
    Write-Host "Le sous-dossier web-inf existant a été supprimé."
}

# Création des sous-dossiers web-inf, lib, et classes dans le répertoire temporaire
New-Item -Path $webInfPath -ItemType Directory | Out-Null
New-Item -Path $libPath -ItemType Directory | Out-Null
New-Item -Path $classesPath -ItemType Directory | Out-Null

# Copie du fichier web.xml vers le répertoire temporaire web-inf
$webXmlPath = Join-Path -Path $projectFolder -ChildPath "web.xml"
Copy-Item -Path $webXmlPath -Destination $webInfPath -Force

# Copie du contenu du dossier "lib" vers le répertoire web-inf/lib
Copy-Item -Path $lib\* -Destination $libPath -Force

$webFolderPath = Join-Path -Path $projectFolder -ChildPath "\views"
Copy-Item -Path $webFolderPath\* -Destination $env:temp -Force

$javaFilesPath = Join-Path -Path $projectFolder -ChildPath "src"  # Chemin des fichiers .java

# Créer un dossier temporaire pour les fichiers .java needed to change into temperary direc
$tempJavaDir1 = Join-Path -Path $javaFilesPath -ChildPath "tempJavaSource1"

New-Item -Path $tempJavaDir1 -ItemType Directory | Out-Null

if (-Not (Test-Path $tempJavaDir1)) {
    New-Item -Path $tempJavaDir1 -ItemType Directory | Out-Null
}
else {
    # Nettoyer le dossier temporaire s'il existe déjà
    Remove-Item -Path $tempJavaDir1\* -Force
}

# Compilation des fichiers .java vers le répertoire temporaire "web-inf/classes" en incluant le JAR dans le classpath
if (Test-Path $javaFilesPath -PathType Container) {
    Set-Location $javaFilesPath

    # Rechercher récursivement tous les fichiers .java dans tous les sous-dossiers
    $javaFile = Get-ChildItem -Recurse -Filter "*.java" -File
    if ($javaFile) {
        # Copier tous les fichiers .java dans le dossier temporaire
        foreach ($file in $javaFile) {
            Copy-Item -Path $file.FullName -Destination $tempJavaDir1
        }
         
        # Compiler tous les fichiers .java dans le dossier temporaire
        Set-Location $tempJavaDir1
        try {
            javac -cp $lib\project.jar -d $classesPath *.java
            Write-Host "Les fichiers .java ont été compilés vers le répertoire temporaire web-inf/classes."         
        }
        catch {
            Write-Host "Erreur lors de la compilation des fichiers .java : $($_.Exception.Message)" -ForegroundColor Red
            exit 1
        }
        #finally {
            # Nettoyage du dossier temporaire
        #    Write-Host "Le dossier temporaire pour les fichiers .java a été supprimé."
        #}
    }
    else {
        Write-Host "Aucun fichier .java trouvé dans le dossier ou ses sous-dossiers."
        exit 1
    } 
}
else {
    Write-Host "Le dossier src n'existe pas ou est vide."
    exit 1
}

# Vérification en affichant les chemins des sous-dossiers créés
Write-Host "Sous-dossier web-inf créé : $webInfPath"
Write-Host "Sous-dossier lib créé : $libPath"
Write-Host "Sous-dossier classes créer: $classesPath"

# Création du fichier .war
& $jarPath -cvf "C:\S5\FRAMEWORK\Framework-Project\script\sprint.war" -C "C:\S5\FRAMEWORK\Framework-Project\script\bin" .

$tomcatBinPath = "C:\xampp\tomcat\bin" # Chemin vers le dossier bin de Tomcat

# Fonction pour arrêter Tomcat
function Stop-Tomcat {
    Write-Host "Arrêt de Tomcat..."
    Set-Location -Path $tomcatBinPath

    ./shutdown.bat

    Start-Sleep -Seconds 20 # Attendre quelques secondes pour assurer l'arrêt complet de Tomcat
}

# Fonction pour démarrer Tomcat
function Start-Tomcat {
    Write-Host "Démarrage de Tomcat..."
    # Naviguer vers le répertoire Tomcat bin
    Set-Location -Path $tomcatBinPath

    # Démarrer Tomcat en exécutant startup.bat
    ./startup.bat

    # Pause de 20 secondes pour laisser Tomcat démarrer
    Start-Sleep -Seconds 20
}

# Vérification si le fichier .war est créé avec succès
if (Test-Path "C:\S5\FRAMEWORK\Framework-Project\script\sprint.war") {
    # Arrêter Tomcat avant les modifications
    #Stop-Tomcat

    #Vérification si dans sprint.war existe déjà et suppression si necessaire        
    if ( Test-Path "C:\xampp\tomcat\webapps\sprint.war" -PathType Container) {
        Remove-Item -Path "C:\xampp\tomcat\webapps\sprint.war" -Recurse -Force
        Write-Host "Le fichier sprint.war dans tomcat a été supprimé."
    }

    # Copier le fichier .war vers le répertoire webapps de Tomcat
    Copy-Item -Path "C:\S5\FRAMEWORK\Framework-Project\script\sprint.war" -Destination "C:\xampp\tomcat\webapps" -Force
    Write-Host "Le fichier .war a été copié avec succès vers le répertoire webapps de Tomcat."

    # Supprimer le fichier sprint.war dans le dossier script
    #Remove-Item -Path "C:\S5\FRAMEWORK\Framework-Project\script\sprint.war" -Force
    #Write-Host "Le fichier sprint.war a été supprimé du dossier script."

    # Supprimer le contenu du dossier bin
    #Remove-Item -Path "C:\S5\FRAMEWORK\Framework-Project\script\bin\*" -Force -Recurse
    #Write-Host "Le contenu du dossier bin a été supprimé."

    # Redémarrer Tomcat après la mise à jour
    #Start-Tomcat
}
else {
    Write-Host "Error: La création du fichier .war a échoué."
}
