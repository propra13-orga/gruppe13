package hhu.propra_2013.gruppe_13;

abstract class NetIO implements Runnable {
	// Exit codes, needed by the program to know whether to continue execution
	static final int EXIT_SUCCESS				= 0;
	static final int EXIT_FAILURE				= 1;
	
	// Needed for error codes
	static final int SERVER_SOCKET_ERROR 		= 0;
	static final int CONNECTION_SOCKET_ERROR 	= 1;
	static final int CONNECTION_OOS_ERROR		= 2;
	
	abstract void setRunning(boolean running);
}
