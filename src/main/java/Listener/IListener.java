package Listener;

import Queue.QueueTr;
import Transport.ITransport;
import utils.DBKParams;

public interface IListener {
	public void Initialize(DBKParams params);
	public void setTransport(ITransport transport);
	public void setQueueTr(QueueTr<ITransport> queue);
	public void Start();
	public void Destroy();
}
