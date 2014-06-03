package com.ovation.utt;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import com.ovation.uttdb.UTTDBManager;
import com.sabre.edge.cf.common.errors.ErrorCodeFormatter;
import com.sabre.edge.cf.common.service.ContextStatusAdvisor;
import com.sabre.edge.cf.core.registry.service.ClientResponse;
import com.sabre.edge.cf.core.registry.service.ISRWCommunication;
import com.sabre.edge.cf.emu.data.EmulatorCommand;
import com.sabre.edge.cf.emu.data.requests.EmulatorCommandRequest;
import com.sabre.edge.cf.emu.data.responses.EmulatorCommandResponse;
import com.sabre.edge.cf.emu.external.ExecuteInEmuServiceClient;
import com.sabre.edge.cf.emu.external.ShowInEmuServiceClient;
import com.sabre.edge.cf.model.*;
import com.sabre.edge.cf.sws.data.SWSRequest;
import com.sabre.edge.cf.sws.data.SWSResponse;
import com.sabre.edge.cf.sws.external.SWSServiceClient;
import com.sabre.edge.platform.core.ui.handlers.OpenViewHandler;


public class UTTCommandInt implements IService{
	
	 private UTTView Viewer= new UTTView();
	private String email=null;
	private static final ISRWCommunication com =Activator.getDefault().getServiceReference(ISRWCommunication.class);
	public UTTCommandInt()
	{}
	@Override
	public void process(IServiceContext context) {
		// TODO Auto-generated method stub
		UTTView v=new UTTView();
		ContextStatusAdvisor contextAdv= new ContextStatusAdvisor(context, getClass());
		IRequest request= context.getRequest();
		IResponse response= context.getResponse();
		if(request instanceof EmulatorCommandRequest)
		{
			EmulatorCommandRequest commandRequest= (EmulatorCommandRequest)request;
			EmulatorCommand command = commandRequest.getEmulatorCommand();
			//EmulatorCommandResponse respond= null;
			if(command.getCommand().startsWith("UTT"))
			{
				email=getEmail();
				try {
					OpenTixView(email);
					v=getUTTView();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			
				 ExecuteInEmuServiceClient exeClient = new ExecuteInEmuServiceClient(com);
				 EmulatorCommand cmd = new EmulatorCommand("*IA");

                cmd.setShowResponse(true);
                 

                 EmulatorCommandRequest req = new EmulatorCommandRequest(cmd);
                 exeClient.send(req);
				
				
			} 
			//else if(command.getCommand().startsWith("UTW"))
			//{
				//OpenWebView();
		//	}
			
			
		}
		
		else

		{

		String errorCode = ErrorCodeFormatter.getInstance("BRIDGE001").format(1);

		String[] parameters = { context.getRequestorId(),

		context.getContextName() };

		IError error = contextAdv.error(errorCode, parameters);
		System.out.println(error);
		}

	}
	private String getEmail() {
		// TODO Auto-generated method stub
		Document payload;
		String act="TravelItineraryReadLLSRQ";
		String ret=null;
		 String err=null;
		 String payloadxml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><TravelItineraryReadRQ xmlns=\"http://webservices.sabre.com/sabreXML/2003/07\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" TimeStamp=\"2012-12-20T16:00:00\" Version=\"1.1.1\"><POS><Source PseudoCityCode=\"IPCC\"/></POS><MessagingDetails><Transaction Code=\"PNR\"/></MessagingDetails></TravelItineraryReadRQ>";
		 payloadxml=payloadxml.trim().replaceFirst("^([\\W]+)<","<");
		 SWSRequest rq= new SWSRequest();
		try{
			
			payload=getDocument(payloadxml);
			
			rq.setAction(act);
			rq.setPayload(payload);
			//ServiceContext ctx = new ServiceContext();
			//ctx.setContextName("com.sabre.edge.cf.sws.SWS");
			//ctx.setRequestorId(Activator.PLUGIN_ID);
			//ctx.setToken(Activator.getDefault().getTokenService().getToken());
			//ctx.setRequest(rq);
			SWSServiceClient client =new SWSServiceClient(com);
			ClientResponse <SWSResponse> rsp=client.send(rq);
			//Activator.getDefault().getSRWRuntime().process(ctx);
			if(rsp.isSuccess())
			{
				SWSResponse response= rsp.getPayload();
				Document docrespond=response.getResponse();
				
				
				ret=getUDID53(docrespond);
			}else{
				err=rsp.getErrors().toString();
				return err;
			}
		
	}
	catch(Exception e)
	{ret=e.getMessage();
	}
		 
	return ret;
			
		}
	 private Document getDocument(String doc)throws ParserConfigurationException, SAXException, IOException
	    {
	        try
	        {
	        	/*InputStream fileInputStream = getClass().getClassLoader()
	    				.getResourceAsStream(doc);*/
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder db;
				db = factory.newDocumentBuilder();
				
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(doc));
				
				Document docPayload = db.parse(is);
	        	//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    		//factory.setNamespaceAware(true);
	    		//DocumentBuilder builder = factory.newDocumentBuilder();
	    		///return builder.parse(fileInputStream, "UTF-8");
	        	
				return docPayload;
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	            return null;
	        }
	    }
	 /*private String getStringFromDoc(Document doc)
	    {
	        try
	        {
	            DOMSource domSource = new DOMSource(doc);
	            StringWriter writer = new StringWriter();
	            StreamResult result = new StreamResult(writer);
	            TransformerFactory tf = TransformerFactory.newInstance();
	            Transformer transformer = tf.newTransformer();
	            transformer.transform(domSource, result);
	            String s = writer.toString();
	            return s;
	        }
	        catch (TransformerException ex)
	        {
	            ex.printStackTrace();
	            return null;
	        }
	    }*/
	 //gets the UDID53 field from an open pnr
	private String getUDID53(Document doc)
	{
		String ClientEmail="";
		String test="";
		String em="";
		String change="";
		String ch="";
		Element docel=doc.getDocumentElement();
		NodeList node = docel.getChildNodes();
		Node an;
		if(node != null && node.getLength()>0)
    	{
    		for(int i =0; i<node.getLength();i++)
    		{
    			an=node.item(i);
    			if(an.getNodeType()== Node.ELEMENT_NODE)
    			{
    				if(an.getNodeName()=="TravelItinerary")
    				{
    					NodeList nl2=an.getChildNodes();
    					for(int x=0;x<nl2.getLength();x++)
    					{
    						
    	    				Element el=(Element)nl2.item(x);
    	    				System.out.print(el);
    	    				if(el.getNodeName().equals("RemarkInfo"))
    	    				{
    	    					NodeList remarkNodes=el.getChildNodes();	
    	    					for(int k=0;k<remarkNodes.getLength();k++)
    	    					{
    	    						Element e=(Element)remarkNodes.item(k);
    	    						System.out.println(e.getAttribute("Type"));
    	    						if(e.getAttribute("Type").equals("Invoice") && e.getElementsByTagName("Text").item(0).getTextContent().startsWith("U*53-") )
    	    						{
    	    							test=e.getElementsByTagName("Text").item(0).getTextContent();
    	    							em=test.substring(5);
    	    							if(em.contains("¤¤U"))
        	    						{
        	    							 ch=em.replaceAll("¤¤U", "_");
        	    							em=ch;
        	    						}
        	    						//em.replace("¤", "@");
        	    						change=em.substring(0, em.indexOf('�'))+'@'+em.substring(em.indexOf('�')+1);
        	    							ClientEmail=change;
    	    							
    	    						}
    	    					}
    	    					
    	    					
    	    				}
    					}
    				}
    				

    				
    			
    			}
    		}
    	}
		if(ClientEmail.equals(""))
		{ClientEmail="No UDID53 found";}
		System.out.println(ClientEmail);
		return ClientEmail;
	}
	

	//
	private void OpenTixView(String Email) throws SQLException, ClassNotFoundException
	{
		UTTDBManager utm= new UTTDBManager();
		utm.setEmail(Email);
		//utm.init();
		//String viewid="com.ovation.UTT.redapp.view";
		 //UTTView view=(UTTView)OpenViewHandler.showView(viewid);
		 //setUTTView(view);
		Display.getDefault().asyncExec(new Runnable() {
            // open the view 
            public void run() {
                  try {
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("com.ovation.UTT.redapp.view");
                  } catch (PartInitException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }
            }
      });

      

}

	/*private void OpenWebView()
	{
		
		Display.getDefault().asyncExec(new Runnable() {
            // open the view 
            public void run() {
                  try {
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("com.ovation.UTT.UTTWebView");
                  } catch (PartInitException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }
            }
      });
	}*/
	 private String getStringFromDoc(Document doc)
	    {
	        try
	        {
	            DOMSource domSource = new DOMSource(doc);
	            StringWriter writer = new StringWriter();
	            StreamResult result = new StreamResult(writer);
	            TransformerFactory tf = TransformerFactory.newInstance();
	            Transformer transformer = tf.newTransformer();
	            transformer.transform(domSource, result);
	            String s = writer.toString();
	            return s;
	        }
	        catch (TransformerException ex)
	        {
	            ex.printStackTrace();
	            return null;
	        }
	    }
		
	
	public void setUTTView(UTTView v)
	{
		Viewer=v;
	}
	public UTTView getUTTView()
	{
		return Viewer;
	}
}

