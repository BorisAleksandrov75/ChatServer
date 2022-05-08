public interface TCPConnectoinListner {

    void onConnectoinReady (TCPconnection tcPconnection);
    void onReiceveString (TCPconnection tcPconnection, String message);
    void onDisconnect (TCPconnection tcPconnection);
    void onException (TCPconnection tcPconnection, Exception e);
}
