package hhu.propra_2013.gruppe_13;

abstract class NetIO implements Runnable {
	// Status for all figures
	static final int DEAD		= 0;
	static final int FINISHED	= 1;
	static final int PLAYING	= 2;
	
	// Exit codes, needed by the program to know whether to continue execution
	static final int EXIT_SUCCESS					= 0;
	static final int EXIT_FAILURE					= 1;
	
	// Needed for error codes
	static final int SERVER_SOCKET_ERROR 			= 0;
	static final int CONNECTION_SOCKET_ERROR 		= 1;
	static final int CONNECTION_SERVER_OOS			= 2;
	static final int CONNECTION_SERVER_OIS			= 3;
	static final int CONNECTION_SERVER_WRITE		= 4;
	static final int CONNECTION_CLIENT_OIS			= 5;
	static final int CONNECTION_CLIENT_OOS			= 6;
	static final int CONNECTION_CLIENT_WRITE		= 7;
	static final int CLIENT_SOCKET_ERROR			= 8;
	static final int CONNECTION_READER_SERVER_ERROR	= 9;
	
	abstract void setRunning(boolean running);
}
