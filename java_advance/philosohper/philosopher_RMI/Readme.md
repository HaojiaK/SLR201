# Instruction

1. Start-Job { rmiregistry 5000 }

2. java -cp bin philosopher_RMI.ForkServerImpl

3. (If address already in use : bin) 
   For Linux/ MacOS: lsof -i :5000; then: kill -9 <PID>; 
   
   For Windows: netstat -ano | findstr :5000; then: taskkill /PID <PID> /F;

4. java -cp bin philosopher_RMI.ForkServerImpl

5. java -cp bin philosopher_RMI.PhilosopherClient

6. Get-Job

7. Stop-Job -Id 1
