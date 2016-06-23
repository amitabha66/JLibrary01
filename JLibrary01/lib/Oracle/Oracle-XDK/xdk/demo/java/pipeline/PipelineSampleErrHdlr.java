import oracle.xml.pipeline.controller.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;

public class PipelineSampleErrHdlr implements PipelineErrorHandler
{

  PipelineSampleErrHdlr(String logFile) throws IOException
  {
    log = new PrintWriter(new FileWriter(logFile));
  }

  public void error (String msg, PipelineException e) throws Exception
  {
    log.println("\nError in: " + e.getProcessId());
    log.println("Type: " + e.getExceptionType());
    log.println("Message: " +  e.getMessage());
    log.println("Error message: " + msg);      
    log.flush();
    errCount++;
  }

  public void fatalError (String msg, PipelineException e) throws Exception
  {
    log.println("\nFatalError in: " + e.getProcessId());
    log.println("Type: " + e.getExceptionType());
    log.println("Message: " +  e.getMessage());
    log.println("Error message: " + msg);  
    log.flush();
    errCount++;
  }

  public void warning (String msg, PipelineException e) throws Exception
  {
    log.println("\nWarning in: " + e.getProcessId());
    log.println("Message: " +  e.getMessage());
    log.println("Error message: " + msg);  
    log.flush();
    warnCount++;
  }

  public void info (String msg)
  {
    log.println("\nInfo : " + msg);
    log.flush();
    warnCount++;    
  }
  public void closeLog()
  {
    log.println("\nTotal Errors: " + errCount + "\nTotal Warnings: " + 
                 warnCount);
    log.flush();
    log.close();
  }

  PrintWriter log;
  int errCount = 0;
  int warnCount = 0;
}
