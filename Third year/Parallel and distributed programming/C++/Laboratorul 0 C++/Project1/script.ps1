param(
    [string]$executableFile,
    [string]$noOfRuns)

for($i=0; $i -lt $noOfRuns; $i++){
    cmd /c "$executableFile"
}