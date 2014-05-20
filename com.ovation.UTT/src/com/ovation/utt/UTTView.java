package com.ovation.utt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


import java.awt.event.KeyEvent;
import java.io.IOException;
	import java.io.InputStream;
	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	import javax.swing.JFrame;
	import javax.xml.parsers.DocumentBuilder;
	import javax.xml.parsers.DocumentBuilderFactory;
	import javax.xml.parsers.ParserConfigurationException;


import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
	import org.eclipse.jface.viewers.TableViewer;
	import org.eclipse.jface.viewers.TableViewerColumn;
	import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
	import org.eclipse.swt.widgets.Combo;
	import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
	import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
	import org.eclipse.swt.widgets.Table;
	import org.eclipse.swt.widgets.TableColumn;
	import org.eclipse.swt.widgets.TableItem;
	import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
	import org.eclipse.swt.layout.*;

	


	import com.ovation.utt.domain.UTT;
	import com.ovation.uttdb.UTTDBManager;
	import com.ovation.uttdb.UTTModel;
import com.sabre.edge.cf.common.errors.ErrorCodeFormatter;
import com.sabre.edge.cf.common.service.ContextStatusAdvisor;

	import com.sabre.edge.cf.core.registry.service.ClientResponse;
	import com.sabre.edge.cf.core.registry.service.ISRWCommunication;
import com.sabre.edge.cf.emu.data.EmulatorCommand;
import com.sabre.edge.cf.emu.data.requests.EmulatorCommandRequest;
import com.sabre.edge.cf.emu.external.ShowInEmuServiceClient;
import com.sabre.edge.cf.model.IError;
import com.sabre.edge.cf.model.IRequest;
import com.sabre.edge.cf.model.IService;
import com.sabre.edge.cf.model.IServiceContext;
import com.sabre.edge.cf.model.element.Event;
import com.sabre.edge.cf.model.element.ServiceContext;
	import com.sabre.edge.cf.sws.data.SWSRequest;
	import com.sabre.edge.cf.sws.data.SWSResponse;
import com.sabre.edge.cf.sws.external.SWSServiceClient;


import com.sabre.edge.platform.core.ui.handlers.OpenViewHandler;



public class UTTView extends ViewPart  {

		
		 //private String query;
	   private String sel;
		private String Email;
		UTTModel model;
		
		Button sendToEMU;
		//private Combo combo;
		//private ComboViewer cview;
		private TableViewer tableview ;
		private Table table;
		private final String[] colNames={"ExpDate","CRX","TotalAirVal","DocNum",
				"NameStmtInfo", "CustID","IssDt","RecordLoc","PCC","TktStat",
				"RfndStat","FareBasCode","Proflvl2(PAR)","ProfLvl3(BAR)","OpenSegs","CtyPrs"};
	   private Label lblYearvalue;
	    
		public UTTView()
		{
			model=new UTTModel();
		}
		
		
		public void createPartControl(Composite parent) {
			// TODO Auto-generated method stub
			parent.setLayout(new GridLayout(2, false));
	new Label(parent, SWT.NONE);

	        
	        //lblYearvalue = new Label(parent, SWT.NONE);
	        //lblYearvalue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	        //Composite composite = new Composite(parent, SWT.NONE);
			
	       ScrolledComposite sc=new ScrolledComposite(parent,SWT.H_SCROLL|SWT.V_SCROLL );
			Composite comp= new Composite(sc,SWT.NONE);
	     comp.setLayout(new GridLayout());
	      
	       sc.setAlwaysShowScrollBars(true);
	       final Button btnsend= new Button(comp,SWT.NONE);
	       btnsend.addSelectionListener(new SelectionAdapter() 
	       {
            @Override
           
            	 public void widgetSelected(SelectionEvent e)
                 {
            		
                        	
                             String command="WETR*T"+ sel;
                             System.out.print(command);
                            ISRWCommunication com =Activator.getDefault().getServiceReference(ISRWCommunication.class);

                            ShowInEmuServiceClient showClient = new ShowInEmuServiceClient(com);

                     

                            EmulatorCommand cmd = new EmulatorCommand(command);

                            cmd.setIsCommand(true);

                            EmulatorCommandRequest request = new EmulatorCommandRequest(cmd);

                            showClient.send(request);

                             
                         }
                     
                 
             });
	      
	       btnsend.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	        btnsend.setText("Send Ticketnumber");
	        btnsend.setEnabled(false);
	       
	      tableview=new TableViewer(comp,SWT.BORDER | SWT.FULL_SELECTION );
	      GridData data =new GridData(SWT.FILL,SWT.TOP,true,true);
	     table= tableview.getTable(); 
	     data.heightHint=10* table.getItemHeight();
	     data.widthHint= 54* table.getItemHeight();
	     table.setLayoutData(data);
		        //GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		       // gd_composite.heightHint = 100;
		       //gd_composite.widthHint = 40;
		      // comp.setLayoutData(gd_composite);
		       // GridLayout gl_composite = new GridLayout();
		      //  gl_composite.marginWidth = 0;
		        //gl_composite.marginHeight = 0;
		        //gl_composite.numColumns=2;
		       
	     sc.setContent(comp);
	     sc.setExpandHorizontal(true);
	     sc.setExpandVertical(true);
		      sc.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		/*	cview= new ComboViewer(parent, SWT.NONE | SWT.READ_ONLY);
	        combo = cview.getCombo();
	        combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	        updateQueryType();*/
			
			
			 
			
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			
			
			
				TableViewerColumn expDt = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColExpDt = expDt.getColumn();
				tableColExpDt.setText(colNames[0]);
				tableColExpDt.setWidth(80);
			
				TableViewerColumn CRX = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColCRX = CRX.getColumn();
				tableColCRX.setText(colNames[1]);
				tableColCRX.setWidth(40);
				
				TableViewerColumn TAV = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColTAV = TAV.getColumn();
				tableColTAV.setText(colNames[2]);
				tableColTAV.setWidth(75);
				
				TableViewerColumn DocNum = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColDocNum = DocNum.getColumn();
				tableColDocNum.setText(colNames[3]);
				tableColDocNum.setWidth(100);
				
				TableViewerColumn Nmst = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColNmst = Nmst.getColumn();
				tableColNmst.setText(colNames[4]);
				tableColNmst.setWidth(50);
				
				TableViewerColumn Cust = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColCust = Cust.getColumn();
				tableColCust.setText(colNames[5]);
				tableColCust.setWidth(40);
				
			
				
				TableViewerColumn issDt = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColIssDt = issDt.getColumn();
				tableColIssDt.setText(colNames[6]);
				tableColIssDt.setWidth(85);
				
				TableViewerColumn Record = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColRecord = Record.getColumn();
				tableColRecord.setText(colNames[7]);
				tableColRecord.setWidth(60);
				
				TableViewerColumn PCC = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColPCC = PCC.getColumn();
				tableColPCC.setText(colNames[8]);
				tableColPCC.setWidth(40);
				
				TableViewerColumn Tktst = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColTktst = Tktst.getColumn();
				tableColTktst.setText(colNames[9]);
				tableColTktst.setWidth(50);
				
				TableViewerColumn Refund = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColRefund = Refund.getColumn();
				tableColRefund.setText(colNames[10]);
				tableColRefund.setWidth(50);
				
				TableViewerColumn FBC = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColFBC = FBC.getColumn();
				tableColFBC.setText(colNames[11]);
				tableColFBC.setWidth(100);
				
				TableViewerColumn Pl2 = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColPl2 = Pl2.getColumn();
				tableColPl2.setText(colNames[12]);
				tableColPl2.setWidth(50);
				
				TableViewerColumn Pl3 = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColPl3 = Pl3.getColumn();
				tableColPl3.setText(colNames[13]);
				tableColPl3.setWidth(50);
				
				TableViewerColumn Open = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColOpen = Open.getColumn();
				tableColOpen.setText(colNames[14]);
				tableColOpen.setWidth(65);
				
				TableViewerColumn City = new TableViewerColumn(tableview, SWT.NONE);
				TableColumn tableColCity = City.getColumn();
				tableColCity.setText(colNames[15]);
				tableColCity.setWidth(100);
				
			tableview.addPostSelectionChangedListener(new ISelectionChangedListener()
			{
				 @Override
		            public void selectionChanged(SelectionChangedEvent e)
		            {
		             
		             
		                btnsend.setEnabled(true);
		               Table tab= tableview.getTable();
		               TableItem it= tab.getItem(tab.getSelectionIndex());
		               	sel=it.getText(3);
		               	
		            }
			});
			
			initDataBindings();
			loadTable();
		}

		
		
		@Override
		public void setFocus() {
			// TODO Auto-generated method stub
			//String viewID="com.ovation.utt.redapp.view";
			//UTTView viewpart=(UTTView)OpenViewHandler.showView(viewID,true);
		}
		
		public void loadTable() 
		{
			try{
			//UTTDBManager utdb= new UTTDBManager();
			Connection Conn=UTTDBManager.openConnection();
			
			//if(Email=="No UDID53 found")
			//{
				
			//}
			//else{
			//utdb.setEmail(Email);
		ResultSet rs=UTTDBManager.executeQuery("{call usp_UTT_Java(?)}",Conn);
		
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();

			int columnNumber=rsmd.getColumnCount();
			TableItem item;
			
			while(rs.next())
			{
				//String[] rowData= new String[columnNumber];
				item=new TableItem(table,SWT.NONE);
				for(int i=1;i<=columnNumber;i++)
				{
					if(rs.getString(i).equals(rs.getString("Refundable")))
					{
						String change= rs.getString(i);
						if(change.equals("0"))
						{
							change="Nonrefundable";
							
						}
						if(change.equals("1"))
						{
							change="Refundable";
							
						}
						item.setText(i-1,change);
					}
					else
					{
						item.setText(i-1,rs.getString(i));
					}
					}
			}
		
	}
			catch(Exception e)
			{e.printStackTrace();}
			
		}
		protected DataBindingContext initDataBindings()
		{
			DataBindingContext bindingContext = new DataBindingContext();
			
			 
			/* UpdateValueStrategy modelToTarget = new UpdateValueStrategy()
		        {
		            @Override
		            public Object convert(Object value)
		            {
		                return Integer.valueOf((String) value);
		            }

		        };*/
		     //  bindingContext.bindValue(modelYearObserveValue, null,
		       //     modelToTarget);

		        WritableList input = new WritableList(model.getUTT(), UTT.class);
		       // tableview.setInput(input);
		      
		        ViewerSupport.bind(tableview, input, BeanProperties.values(colNames));
		        return bindingContext;
			
		}
	
		
		
	}
