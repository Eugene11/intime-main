package Transport;

//import Queue.DataExTransport;
import utils.DBKParams;
import utils.DBKVariable;

import java.util.List;

public interface ITransport {
	public static final String TRANSPORT_ENCODING = "transport_encoding";
	//public void setDataExTransport(DataExTransport transport);
	public void Initialize(DBKParams params);
	
	public void Create() throws Exception;
	
	public int Connect() throws Exception;
	public List<ITransport> Accept() throws Exception, InterruptedException;
	
	//public DataExTransport Read() throws Exception;
	//public int Write(DataExTransport data) throws Exception, InterruptedException;
	
	public void Close();
	
	public DBKVariable<?> GetParam(String strName);
	public Object getObject(String name);
	
}
