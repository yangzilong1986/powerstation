strComputer = "."
Set objWMIService = GetObject("winmgmts:" _
 & "{impersonationLevel=impersonate}!\\" & strComputer & "\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_Processor ",,48)
load = 0
n = 0
For Each objItem in colItems
 load = load + objItem.LoadPercentage
 n = n + 1
Next
Wscript.Echo (load/n)
