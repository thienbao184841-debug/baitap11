$ErrorActionPreference = "Stop"

$javaFiles = Get-ChildItem -Path "src\main\java" -Filter *.java -Recurse
$classNames = $javaFiles | ForEach-Object { $_.BaseName }
$classNames = $classNames | Sort-Object Length -Descending

$filesToModify = Get-ChildItem -Path "src" -Include *.java,*.jsp,*.xml -Recurse
foreach ($file in $filesToModify) {
    $content = Get-Content $file.FullName -Raw
    $modified = $false
    foreach ($className in $classNames) {
        if ($content -cmatch "\b$className\b") {
            $content = $content -creplace "\b$className\b", "${className}_24110162"
            $modified = $true
        }
    }
    if ($modified) {
        Set-Content -Path $file.FullName -Value $content -NoNewline
    }
}

# Rename the .java files themselves
foreach ($file in $javaFiles) {
    $newName = "$($file.BaseName)_24110162$($file.Extension)"
    Rename-Item -Path $file.FullName -NewName $newName
}

# Revert the SQL table name Category
$catDao = "src\main\java\com\videoshare\dao\impl\CategoryDaoImpl_24110162.java"
$vidDao = "src\main\java\com\videoshare\dao\impl\VideoDaoImpl_24110162.java"

if (Test-Path $catDao) {
    $c = Get-Content $catDao -Raw
    $c = $c -creplace "INTO Category_24110162", "INTO Category"
    $c = $c -creplace "UPDATE Category_24110162", "UPDATE Category"
    $c = $c -creplace "FROM Category_24110162", "FROM Category"
    Set-Content -Path $catDao -Value $c -NoNewline
}
if (Test-Path $vidDao) {
    $c = Get-Content $vidDao -Raw
    $c = $c -creplace "JOIN Category_24110162", "JOIN Category"
    Set-Content -Path $vidDao -Value $c -NoNewline
}

Write-Host "Refactoring complete!"
