package learn.service;

import learn.exception.ServiceException;

public interface MyService {

	public void startService() throws ServiceException;

	public void stopService();
}
