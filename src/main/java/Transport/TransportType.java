package Transport;

public enum TransportType {
	/*AQORACLE_INPUT("AQOracleInput"), QUEUE_MESSAGE_GET("QueueMessageGet"), ORACLE_STORPROC_PUT2CLOB("ORACLE_STORPROC_PUT2CLOB"),
	MQIBM_INPUT("MQIBM_INPUT"), MQIBM_OUTPUT("MQIBM_OUTPUT"), QUEUE_INPUT_FILES_BOUNDLE_GET("QueueInputFilesBoundleGet"), QUEUE_INPUT_FILE_GET("QueueInputFileGet"),*/ DOC_INPUT("DOC_INPUT"), DOC_OUTPUT("DOC_OUTPUT");
	
	private final String m_strValue;
	private TransportType(String strType)
	{
		m_strValue = strType;
	}
	public String getValue() { return m_strValue; }
}
