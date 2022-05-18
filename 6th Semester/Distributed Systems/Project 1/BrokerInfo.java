public class BrokerInfo {
    
    protected int id;
    protected int port;

    BrokerInfo(int id, int port) {
        this.id=id;
        this.port=port;
    }

    public void setId(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setPort(int port) {
        this.port=port;
    }

    public int getPort() {
        return port;
    }
}
