import java.net.MalformedURLException;
import java.io.FileReader;
import java.io.Reader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import oracle.xml.pipeline.controller.*;
import oracle.xml.pipeline.processes.*;

public class PipelineSample
{

  public static void main(String[] args)
  {
    FileReader f;
    PipelineDoc pipe;
    PipelineProcessor proc;

    if (args.length < 1)
    {
      System.out.println("First argument needed, other arguments are optional:");
      System.out.println("pipedoc.xml <output_log> <'seq'>");
      return;
    }
    if (args.length > 1)
      logname = args[1];     
    try
    {
      f = new FileReader(args[0]);

      /* Create the PipelineDocument object using the input pipedoc.xml
         and set in the PipelineProcessor 
       */
      pipe = new PipelineDoc((Reader)f, false);
      proc = new PipelineProcessor();
      proc.setPipelineDoc(pipe);

      /* Set the execution mode */
      String execMode = null;
      if (args.length > 2)
      {
         execMode = args[2];
         if(execMode.startsWith("seq"))
            proc.setExecutionMode(PipelineConstants.PIPELINE_SEQUENTIAL);
         else if (execMode.startsWith("para"))   
            proc.setExecutionMode(PipelineConstants.PIPELINE_PARALLEL);
      }      

      /* Create and set the defined Pipeline Error Handler */
      errHandler = new PipelineSampleErrHdlr(logname);       
      proc.setErrorHandler(errHandler);

      /* Execute the pipeline */
      proc.executePipeline();

    }
    catch(PipelineException e1)
    {
      System.out.println("Pipeline exception: " +  e1.getMessage());
      e1.printStackTrace();
    }
    catch(MalformedURLException e2)
    {
      System.out.println("MalformedURLException: " + e2.getMessage());
      e2.printStackTrace();
    }
    catch(Exception e3)
    {
      System.out.println("Error reading/parsing pipeline doc: " + 
                         e3.getMessage());
      e3.printStackTrace();                   
    }
    finally
    {  
       if (errHandler != null)
          errHandler.closeLog();
    }      
    return;   
  }


  private static String logname = "pipeline.log";
  private static PipelineSampleErrHdlr errHandler;
}

