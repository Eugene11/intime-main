package Listener;

import Queue.QueueTr;
import Transport.ITransport;
import utils.DBKParams;
//import org.apache.log4j.Logger;

import java.util.List;

public class ListenerStandart extends Thread implements IListener {
	//private static Logger logger = Logger.getLogger(ListenerStandart.class);
	
	public void Destroy() {
		this.interrupt();
	}
	
	public void interrupt(){
		super.interrupt();
		isDestroy = true;
	}

	public void Start() {
		start();
	}
	
	public ListenerStandart(){
		setName("Listener.ListenerStandart");
		setDaemon(true);
	}
	
	@SuppressWarnings("unused")
	private DBKParams m_Params = null;
	private ITransport m_Transport = null;
	private QueueTr<ITransport> m_Queue = null;
	private volatile boolean isDestroy = false;
	public void Initialize(DBKParams params)
	{
		m_Params = params;
	}
	public void setTransport(ITransport transport)
	{
		m_Transport = transport;
	}
	public void setQueueTr(QueueTr<ITransport> queue)
	{
		m_Queue = queue;
	}

	private enum State
	{
		NOTHING, CONNECT, PROCESS, END;
	}
	
	private State m_State = State.NOTHING;
	
	public void run()
	{
		if(isDestroy){
			return;					
		}

		
		//logger.info(Thread.currentThread().getName() + " is started");
		while(m_State != State.END && isDestroy == false)
		{
			switch(m_State)
			{
			case NOTHING:
				m_State = State.CONNECT;
				break;
			case CONNECT:
				try {	
					m_Transport.Create();
					m_Transport.Connect();
				}
				catch (Exception e2) {
					e2.printStackTrace();
					break;
				}
				m_State = State.PROCESS;
				break;
			case PROCESS:
			
				List<ITransport> transportNewList = null;
				try {
					transportNewList = m_Transport.Accept();
				}
				catch(InterruptedException ex){
					Thread.currentThread().interrupt();
					m_State = State.END;
					break;
				}
				catch (Exception e1) {
					e1.printStackTrace();
					m_State = State.CONNECT;
					m_Transport.Close();
					try{
						Thread.sleep(300);
					}
					catch(InterruptedException ex){
						Thread.currentThread().interrupt();						
						m_State = State.END;
						break;
					}
					break;
				}
				if(transportNewList != null){
					try{
						for (ITransport transp : transportNewList){
							m_Queue.Push(transp);
						}
					}
					catch(InterruptedException ex){
						Thread.currentThread().interrupt();
						m_State = State.END;
					}
				}
				break;
			case END:
				break;
			}
			
			try {
				Thread.sleep(300);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				m_State = State.END;
			}
		}
		
		m_Transport.Close();
		//logger.info(Thread.currentThread().getName() + " is stopped");
	}
}
