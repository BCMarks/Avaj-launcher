WINDOWS powershell:
javac *.java
java AvajLauncher .\scenarios\good_hashed_scenario.txt

Get-ChildItem * -Include *.class -Recurse | Remove-Item