package Settings;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class Setting {
	
	//public enum SettingsType{Adapter, Document}
	
	//private SettingsType type;
	private final Listener m_Listener;
	//private final Sender m_Sender;
	private final Process m_Process;
	private final QueueFIFO m_QueueListenerToProcess;
	//private final QueueFIFO m_QueueProcessToSender;
	
	
	public Setting(Listener listener, /* Sender sender,*/ Process process,
                   QueueFIFO queueListenerToProcess/*, QueueFIFO queueProcessToSender*/)
	{
		m_Listener = listener;
		//m_Sender = sender;
		m_Process = process;
		m_QueueListenerToProcess = queueListenerToProcess;
		//m_QueueProcessToSender = queueProcessToSender;
		//type = curType;
	}
	
	/*
	public SettingsType getType() {
		return type;
	}

	public void setType(SettingsType type) {
		this.type = type;
	}
	*/
	
	public Listener getListener() { return m_Listener; }
	//public Sender getSender() { return m_Sender; }
	public Process getProcess() { return m_Process; }
	public QueueFIFO getQueueListenerToProcess() { return m_QueueListenerToProcess; }
	//public QueueFIFO getQueueProcessToSender() { return m_QueueProcessToSender; }
	
	private static ArrayList<Setting> settings = new ArrayList<Setting>();
	
	public static Setting CreateFromXML(String strFullFileName)
	{
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		final Document doc;
		try {					
			InputStream stream = SettingAccess.getInputStream(strFullFileName);
			doc = docBuilder.parse(stream);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
		Listener listener = null;
		//Sender sender = null;
		Process process = null;
		QueueFIFO queueListenerToProcess = null;
		//QueueFIFO queueProcessToSender = null;
		
		final Element docElement = doc.getDocumentElement();
		final NodeList nodeList = docElement.getChildNodes();
		for(int j = 0; j < nodeList.getLength(); j++)
		{
			Node nItem = nodeList.item(j);
			if(nItem.getNodeName().compareTo("adapter") == 0 || nItem.getNodeName().compareTo("docserv") == 0)
			{
				boolean bUseAdapter = true;
				NamedNodeMap attr = nItem.getAttributes();
				for(int l = 0; l < attr.getLength(); l++){
					Node attrItem = attr.item(l);
					if(attrItem.getNodeName().compareTo("enable") == 0){
						bUseAdapter = Boolean.parseBoolean(attrItem.getNodeValue());
					}						
				}
				if(bUseAdapter == false)
					continue;
				NodeList nList = nItem.getChildNodes();
				
				/*
				SettingsType curType = SettingsType.Adapter;
				if(nItem.getNodeName().compareTo("adapter") == 0 ){
					curType = SettingsType.Adapter;
				}
				if(nItem.getNodeName().compareTo("docserv") == 0){
					curType = SettingsType.Document;
				}
				*/
				
				for(int i = 0; i < nList.getLength(); i++)
				{
					Node nodeItem = nList.item(i);
					if(nodeItem.getNodeName().compareTo("Listener") == 0)
					{
						listener = Listener.CreateFromNode(nodeItem);
					}
					else if(nodeItem.getNodeName().compareTo("Sender") == 0)
					{
						//sender = Sender.CreateFromNode(nodeItem);
					}
					else if(nodeItem.getNodeName().compareTo("Process") == 0)
					{
						//process = Process.CreateFromNode(nodeItem);
					}
					else if(nodeItem.getNodeName().compareTo("QueueListenerToProcess") == 0)
					{
						queueListenerToProcess = QueueFIFO.CreateFromNode(nodeItem);
					}
					else if(nodeItem.getNodeName().compareTo("QueueProcessToSender") == 0)
					{
						//queueProcessToSender = QueueFIFO.CreateFromNode(nodeItem);
					}
				} 
				settings.add(new Setting(listener,/* sender,*/ process, queueListenerToProcess/*, queueProcessToSender*/));
			}
			
			
		}
				
		return null;
	}
	
	public static void Create(String strFulFileName)
	{
		CreateFromXML(strFulFileName);
	}
	
	public static Setting getSetting(int index){
		return settings.get(index);
	}
	
	public static int getCountAdapters(){
		return settings.size();
	}
}
