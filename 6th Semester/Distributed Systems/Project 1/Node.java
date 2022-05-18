import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;

public class Node implements Serializable {

	protected ArrayList<Broker> brokers = new ArrayList<Broker>();
	
	transient ServerSocket providerSocket;
	Socket clientSocket;
	int port;
	BigInteger hash_key;


	public void getBrokers(){
        try {
            File myObj = new File("broker_info.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,2).equals("id")){
                    Broker broker = new Broker();
                    broker.id = data.substring(4);
                    data = myReader.nextLine();
                    broker.port =  Integer.parseInt(data.substring(6,10));
					broker.hash_key=hash(String.valueOf(broker.port));
                    brokers.add(broker);
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

	public synchronized void init() {
		getBrokers();
	}

	public synchronized void disconnect() {
		try {
        	clientSocket.close();
		} catch (IOException e) {
			System.out.println("Failed to disconnect.");
			e.printStackTrace();
		}
		
	}

	public synchronized void updateNodes() {
		for (Broker broker: brokers){
			try (Socket updateNodesSocket = new Socket("127.0.0.1", broker.port);
			ObjectOutputStream out = new ObjectOutputStream(updateNodesSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(updateNodesSocket.getInputStream())) {

				out.writeObject("update");
				out.flush();

				out.writeObject(brokers);
				out.flush();


			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		for (Broker broker: brokers) {
			ArrayList<AppNode> visited = new ArrayList<AppNode>();
			for (AppNode user:broker.channels) {
				if(user.port==port) 
					continue;
				if (!visited.contains(user)) {
					try (Socket updateNodesSocket = new Socket("127.0.0.1", user.port);
					ObjectOutputStream out = new ObjectOutputStream(updateNodesSocket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(updateNodesSocket.getInputStream())) {

						out.writeObject("update");
						out.flush();

						out.writeObject(brokers);
						out.flush();

					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			}
		}
	}
	
	public synchronized BigInteger hash(String hashtag) {
		BigInteger no=null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(hashtag.getBytes());
	
			no = new BigInteger(1, messageDigest);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return no;
	}
}