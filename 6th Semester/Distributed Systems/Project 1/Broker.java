import java.net.*;
import java.io.*;
import java.util.*;

public class Broker extends Node  {
	protected  HashMap<String, ArrayList<AppNode>> registeredUsersChannels = new HashMap<String, ArrayList<AppNode>>();
	protected  HashMap<String, ArrayList<AppNode>> registeredUsersHashtags = new HashMap<String, ArrayList<AppNode>>();
	protected  List<AppNode> registeredPublishers = new ArrayList<AppNode>();
	protected  ArrayList<AppNode> channels = new ArrayList<AppNode>();
	protected  ArrayList<String> hashtags = new ArrayList<String>();
	String id;
	int port;

	public static void main(String[] args) {
		Broker new_broker = new Broker(args[0]);
		new_broker.startBroker();
    }

	public void startBroker() {
		
		Socket connection = null;

        try {
			port = thisBroker(id);
			hash_key=hash(String.valueOf(port));
			System.out.println(id + " " + hash_key + " " + port);
			init();
            providerSocket = new ServerSocket(port , 10);
            System.out.println("Server is on");

			while (true) {
				connection = providerSocket.accept();
                BrokerThread new_broker = new BrokerThread(connection, id);
				new_broker.start();
			}
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } 
	}


	Broker(){}

	Broker(String id) {
    	this.id = id;
    }
	

	public int thisBroker(String id) {
        try {
            File myObj = new File("broker_info.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(4).equals(id)){
                    data = myReader.nextLine().substring(6,10);
                    return Integer.parseInt(data);
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return -1;
    }


	public synchronized String pull(Channel name) throws ClassNotFoundException{
		for(AppNode give: registeredPublishers) {
			if (!give.channel.channelName.equals(name.channelName)) {
				try (Socket connection = new Socket("127.0.1.0", give.port);
				ObjectOutputStream publisher_out = new ObjectOutputStream(connection.getOutputStream());
				ObjectInputStream publisher_in = new ObjectInputStream((connection.getInputStream()))) {
					publisher_out.writeObject("pull");
					publisher_out.flush();

					publisher_out.writeObject(this);
					publisher_out.flush();

					publisher_out.writeObject(name);
					publisher_out.flush();

					File file = new File("videos");
					boolean made = file.mkdir();
					made = file.exists();
					String video_name=(String) publisher_in.readObject();
					try(OutputStream writer = new FileOutputStream("videos\\"+ video_name)) {
						if (made) {
							while(true) {
								File video_chunk = (File) publisher_in.readObject();
								if (video_chunk==null){
									break;
								}
								InputStream reader = new FileInputStream(video_chunk);
								int readByte=0;
								while((readByte = reader.read()) != -1) {
									writer.write(readByte);
								}
								reader.close();
							}
						}
						else
							System.out.println("Failed to create directory for incoming video file - try again.");
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}
		String path = "videos\\";
		return path;
		
	}

	public synchronized Broker findBroker(String topic, String filter) {
		for (Broker broker : brokers) {
			if(filter.equals("channel")){
				for (AppNode publisher : registeredPublishers) {
					if (topic.equals(publisher.channel.channelName))
						return broker;
				}	
			}
			else{
				for (String hashtag : broker.hashtags) {
					if (topic.equals(hashtag))
						return broker;	
				}
			}
		}
		return null;
	}

	public class BrokerThread extends Thread {

		Socket clientSocket;
		String broker_id;
	
		ObjectOutputStream consumer_out;
		ObjectInputStream consumer_in;
	
		BrokerThread(Socket clientSocket, String broker_id) {
			this.clientSocket=clientSocket;
			this.broker_id=broker_id;
			try {
				consumer_out = new ObjectOutputStream(clientSocket.getOutputStream());
				consumer_in = new ObjectInputStream(clientSocket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		@Override
		public void run() {
			try {
				while (true) {
					String action = (String) consumer_in.readObject();

					if (action.equals("6")) {
						Channel name = (Channel) consumer_in.readObject();
						String path = pull(name);
						consumer_out.writeObject(path);
						consumer_out.flush();
					}
					else if (action.equals("update")) {
						brokers = (ArrayList<Broker>) consumer_in.readObject();

						hashtags = brokers.get(Integer.parseInt(id)-1).hashtags;
						channels = brokers.get(Integer.parseInt(id)-1).channels;
						registeredUsersHashtags = brokers.get(Integer.parseInt(id)-1).registeredUsersHashtags;
						registeredUsersChannels = brokers.get(Integer.parseInt(id)-1).registeredUsersChannels;
						registeredPublishers = brokers.get(Integer.parseInt(id)-1).registeredPublishers;
						System.out.println("Nodes updated!");
						System.out.println("New Broker channels" + channels);
						System.out.println("New Broker hashtags" + hashtags);
						System.out.println("New registered Users for hashtags" + registeredUsersHashtags);
						System.out.println("New registered Users for channels" + registeredUsersChannels);
						System.out.println("New registered Publishers" + registeredPublishers);
						
						break;
					}
					else if (action.equals("initialize")) {
						consumer_out.writeObject(brokers);
						consumer_out.flush();
					}
				}
			} catch (IOException e) {
				System.out.println();
			} catch (ClassNotFoundException e ) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	

	
}