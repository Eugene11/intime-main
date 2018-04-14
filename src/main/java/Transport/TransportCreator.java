package Transport;

//import Transport.Doc.TransportInDoc;
//import Transport.Doc.TransportOutDoc;

public class TransportCreator {
	public static ITransport Create(TransportType type)
	{
		switch(type)
		{
			/*
		case AQORACLE_INPUT:
			return new TransportAQOracle();
		case QUEUE_MESSAGE_GET:
			return new TransportQueueMessageGet();
		case ORACLE_STORPROC_PUT2CLOB:
			return new TransportOracleStorprocPut2CLOB();
		case MQIBM_INPUT:
			return new TransportMQIBMIn();
		case MQIBM_OUTPUT:
			return new TransportMQIBMOut();*/
		case DOC_INPUT:
			return null;//new TransportInDoc();
		case DOC_OUTPUT:
			return null;//new TransportOutDoc();
			/*
		case QUEUE_INPUT_FILES_BOUNDLE_GET:
			return new TransportQueueInputFilesBoundleGet();
		case QUEUE_INPUT_FILE_GET
			return new TransportQueueInputFileGet();*/
		}
		
		return null;
	}
}
