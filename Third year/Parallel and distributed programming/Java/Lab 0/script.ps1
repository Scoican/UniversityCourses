param(
    [string]$executableFile,
    [string]$noOfRuns,
    [string]$min,
    [string]$max)

for($i=0; $i -lt $noOfRuns; $i++){
    cmd /c "$executableFile" $min $max
}