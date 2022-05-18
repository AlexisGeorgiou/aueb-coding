import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppNode extends Node {
    int id;
    transient ObjectOutputStream broker_out;
	transient ObjectInputStream broker_in;

    public Channel channel;
	protected  ArrayList<Broker> registeredBrokers = new ArrayList<Broker>();

    public static void main(String[] args) {

        AppNode appNode = new AppNode(args[0], args[1], Integer.parseInt(args[2]));
        appNode.startAppNode();
    }

    public void startAppNode() {
        Socket connection = null;
        Socket requestSocket=null;

        try {
            init();
            providerSocket = new ServerSocket(port, 10);
            Random rand = new Random();
            int br_id = rand.nextInt(3);
            requestSocket = new Socket("127.0.0.1", brokers.get(br_id).port);
            AppNodeThread new_user = new AppNodeThread(requestSocket, channel.channelName, this);
            new_user.start();
            while (true) {
				connection = providerSocket.accept();
                RequestHandler server_mode = new RequestHandler(connection, channel);
                server_mode.start();
			}
            
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }      
    }
    
    AppNode() {}

    AppNode(String name, String port, int id) {
        this.channel = new Channel(name);
        this.port=Integer.parseInt(port);
        this.id = id;
    }
    //Publisher Methods
	public synchronized void addHashTag(String hashtag, VideoFile video) {
        if(!channel.hashtagsPublished.keySet().contains(hashtag)){
            channel.hashtagsPublished.put(hashtag, 1);
        }
        else {
            channel.hashtagsPublished.put(hashtag, channel.hashtagsPublished.get(hashtag) + 1);
        }
        video.getAssociatedHashtags().add(hashtag);
        System.out.println(channel.videosPublished.get(channel.videosPublished.indexOf(video)).getAssociatedHashtags());
		notifyBrokersForAddedHashTags(hashtag);
	}
		
	public synchronized void removeHashTag(String hashtag, VideoFile video) {
        if(channel.hashtagsPublished.keySet().contains(hashtag)){
            System.out.println(channel.hashtagsPublished);
            channel.hashtagsPublished.put(hashtag, channel.hashtagsPublished.get(hashtag)-1);
            if (channel.hashtagsPublished.get(hashtag) == 0){
                channel.hashtagsPublished.remove(hashtag);
            }
            video.getAssociatedHashtags().remove(hashtag);
            notifyBrokersForRemovedHashtags(hashtag);
        } 
	}

	public synchronized Broker hashTopic(String topic){
		BigInteger hash = hash(topic);
		Broker respBroker = new Broker();
		for (Broker broker:brokers) {
            BigInteger x = hash.subtract(broker.hash_key).abs();
            if (x.mod(broker.hash_key).compareTo(hash) <= 0) {
                respBroker = broker;
            }
		}
		return respBroker;
	}
		
	public synchronized void notifyBrokersForAddedHashTags(String hashtag){
        Broker respBroker=hashTopic(hashtag);
        if(!brokers.get(Integer.parseInt(respBroker.id)-1).hashtags.contains(hashtag))
            brokers.get(Integer.parseInt(respBroker.id)-1).hashtags.add(hashtag);
        if(!brokers.get(Integer.parseInt(respBroker.id)-1).registeredPublishers.contains(this))
            brokers.get(Integer.parseInt(respBroker.id)-1).registeredPublishers.add(this);
	}

    public synchronized void notifyBrokersForRemovedHashtags(String hashtag){
        Broker respBroker=hashTopic(hashtag);
        for(String brokerHashtag: respBroker.hashtags){
            if(channel.hashtagsPublished.containsKey(brokerHashtag) && brokerHashtag.equals(hashtag)){
                return;
            }
        }
        brokers.get(Integer.parseInt(respBroker.id) - 1).registeredPublishers.remove(this);
        boolean found = false;
        for (AppNode publisher:respBroker.registeredPublishers) {
            for (String brokerHashtag:respBroker.hashtags) {
                if (publisher.channel.hashtagsPublished.containsKey(brokerHashtag) && brokerHashtag.equals(hashtag)) {
                    found=true;
                    break;
                }
            }
        }
        if (!found) {
            brokers.get(Integer.parseInt(respBroker.id)-1).hashtags.remove(hashtag);
            brokers.get(Integer.parseInt(respBroker.id)-1).registeredUsersHashtags.remove(hashtag);
        }
    }
    
    public synchronized void push(String name, boolean channel) throws IOException {
        List<File> all_videos = new ArrayList<File>();
        try (Stream<Path> paths = Files.walk(Paths.get("User"+this.id+"Videos"))) {
            all_videos = paths.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
            for (File temp: all_videos) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        //pushes a channel's content if channel=true
        if (name.equals(this.channel.channelName) && channel) {
            for(File file:all_videos) {
                broker_out.writeObject(file.getName());
                broker_out.flush();
                generateChunks(file.getName());
                String cleanFileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                try (Stream<Path> vid_path= Files.walk(Paths.get("User"+this.id+"Videos_Split\\"+ cleanFileName))) {
                    all_videos = vid_path.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
                    Stream<File> chunks = all_videos.stream();
                    Iterator<File> it = chunks.iterator();
                    while (it.hasNext()) {
                        broker_out.writeObject(it.next());
                        broker_out.flush();
                    }
                    broker_out.writeObject(null);
                    broker_out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //pushes a hashtag's content if channel=false
        else {
            Iterator mp = this.channel.userVideoFilesMap.entrySet().iterator();
            while(mp.hasNext()) {
                Map.Entry<String, VideoFile> pair = (Map.Entry<String, VideoFile>)mp.next();
                VideoFile this_video = (VideoFile) pair.getValue();
                for (String hashtag: this_video.getAssociatedHashtags()) {
                    if (hashtag.equals(name) && !channel) {
                        for (File file:all_videos) {
                            if (file.getName().equals(this_video.getVideoName()))
                                generateChunks(file.getName());
                            broker_out.writeObject(file.getName());
                            broker_out.flush();
                            String cleanFileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                            try (Stream<Path> vid_path= Files.walk(Paths.get("User"+this.id+"Videos_Split\\"+ cleanFileName))) {
                                List<File> all_chunks = vid_path.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
                                Stream<File> chunks = all_chunks.stream();
                                Iterator<File> it = chunks.iterator();
                                while(it.hasNext()) {
                                    broker_out.writeObject(it.next());
                                    broker_out.flush();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } 
                    }
                }
                mp.remove();
            }
        }
    }

    public synchronized VideoFile upload (String videoName) throws IOException {
        File file = new File("User" +id + "Videos\\" + videoName + ".mp4");
        VideoFile new_video=new VideoFile(file.getName());
        channel.userVideoFilesMap.put(new_video.getVideoName(), new_video);
        channel.videosPublished.add(new_video);
        System.out.println("Video successfully uploaded");
        return new_video;

    }

	public synchronized void generateChunks(String videoName) {
		try {
            File file = new File("User" +id + "Videos\\" + videoName);
            if (file.exists()) {
				String videoFileName = file.getName().substring(0, file.getName().lastIndexOf(".")); 
				File splitFile = new File("User" + id + "Videos_Split\\"+ videoFileName);
            if (!splitFile.exists()) {
                splitFile.mkdirs();
                System.out.println("Directory Created -> "+ splitFile.getAbsolutePath());
            }

            int i = 01;
            InputStream inputStream = new FileInputStream(file);
            String videoFile = splitFile.getAbsolutePath() +"/"+ String.format("%02d", i) +"_"+ file.getName();
            OutputStream outputStream = new FileOutputStream(videoFile);
            System.out.println("File Created Location: "+ videoFile);
            int totalPartsToSplit = 20;
            int splitSize = inputStream.available() / totalPartsToSplit;
            int streamSize = 0;
            int read = 0;
            while ((read = inputStream.read()) != -1) {

                if (splitSize == streamSize) {
                    if (i != totalPartsToSplit) {
                        i++;
                        String fileCount = String.format("%02d", i); 
                        videoFile = splitFile.getAbsolutePath() +"/"+ fileCount +"_"+ file.getName();
                        outputStream = new FileOutputStream(videoFile);
                        streamSize = 0;
                    }
                }
                outputStream.write(read);
                streamSize++;
            }
            inputStream.close();
            outputStream.close();
            } else {
                System.err.println(file.getAbsolutePath() +" File Not Found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
    }

    public synchronized void register(Broker broker, String topic, String filter){     
        
        if(filter.equals("hashtag")){
            if(broker.registeredUsersHashtags.get(topic) != null){
                for (AppNode user: broker.registeredUsersHashtags.get(topic)){
                    if(user.id == id){
                        System.out.println("Already registered for specified hashtag");
                        return;
                    }
                }
            }

            if (!broker.registeredUsersHashtags.containsKey(topic)) {
                ArrayList<AppNode> temp = new ArrayList<AppNode>();
                temp.add(this);
                brokers.get(Integer.parseInt(broker.id)-1).registeredUsersHashtags.put(topic, temp);

            }
            else {
                brokers.get(Integer.parseInt(broker.id) - 1).registeredUsersHashtags.get(topic).add(this);
            }
        }
        else if (filter.equals("channel")){
            if(broker.registeredUsersChannels.get(topic) != null){
                for (AppNode user: broker.registeredUsersChannels.get(topic)){
                    if(user.id == id){
                        System.out.println("Already registered for specified channel");
                        return;
                    }
                }
            }

            if (!broker.registeredUsersChannels.containsKey(topic)) {
                ArrayList<AppNode> temp = new ArrayList<AppNode>();
                temp.add(this);
                brokers.get(Integer.parseInt(broker.id)-1).registeredUsersChannels.put(topic, temp);

            }
            else {
                brokers.get(Integer.parseInt(broker.id) - 1).registeredUsersChannels.get(topic).add(this);
            }
        }
        registeredBrokers.add(broker);
        System.out.println("Succesfully Registered to " + topic);
                        
        updateNodes();
    }
	
	public synchronized void disconnect(Broker broker, String topic){
        try {
            if(!registeredBrokers.contains(broker))
                System.out.println("Not subscribed to that broker");
            else{
                registeredBrokers.remove(broker);

                broker_out.writeObject(this);
                broker_out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public synchronized void playData(String video_path) throws IOException {
        File dir = new File(video_path);
        String[] vid_names = dir.list();
        for (String name:vid_names) {
            String video = video_path+name;
            Runtime.getRuntime().exec("cmd /c start \"%programfiles%\\Windows Media Player\\wmplayer.exe\" " + video);
        }
    }

    public class RequestHandler extends Thread {

        Socket accepted;
        Channel channel;

        RequestHandler(Socket accepted, Channel channel) {
            this.accepted=accepted;
            this.channel=channel;
            try {
                broker_out = new ObjectOutputStream(accepted.getOutputStream());
                broker_in = new ObjectInputStream(accepted.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                String request = (String) broker_in.readObject();
                if (request.equals("update")) {
                    brokers = (ArrayList<Broker>) broker_in.readObject();
                    for (Broker broker:brokers) {
                        if(registeredBrokers.contains(broker)) {
                            boolean flag=false;
                            for (AppNode temp: broker.registeredPublishers) {
                                if(temp.channel.equals(channel))
                                    flag=true;
                            }
                            if(!flag)
                                registeredBrokers.remove(broker); 
                        }
                    }
                }
                else if(request.equals("pull")) {
                    Broker new_connection = (Broker) broker_in.readObject();
                    Channel target = (Channel) broker_in.readObject();
                    Iterator itHashtags = new_connection.registeredUsersHashtags.entrySet().iterator();
                    Iterator itChannels = new_connection.registeredUsersChannels.entrySet().iterator();
                    while (itHashtags.hasNext()) {
                        Map.Entry<String, ArrayList<AppNode>> pair = (Map.Entry)itHashtags.next();
                        for (AppNode next_appnode:pair.getValue()) {
                            if (next_appnode.channel.channelName.equals(target.channelName) && new_connection.hashtags.contains(pair.getKey()) && !next_appnode.channel.equals(this.channel)) {
                                push(pair.getKey(), false);
                            }
                        }
                    }
                    while (itChannels.hasNext()) {
                        Map.Entry<String, ArrayList<AppNode>> pair = (Map.Entry)itChannels.next();
                        for (AppNode next_appnode:pair.getValue()) {
                            AppNode targetPublisher = null;
                            for(AppNode temp: new_connection.channels){
                                if(temp.channel.channelName.equals(pair.getKey())){
                                    targetPublisher = temp;
                                    break;
                                }
                                 
                            }
                            if (next_appnode.channel.channelName.equals(target.channelName) && new_connection.channels.contains(targetPublisher) && !next_appnode.channel.equals(this.channel)) {
                                push(pair.getKey(), true);
                            }
                        }
                    }  
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public class AppNodeThread extends Thread {

        Socket clientSocket;
        String channel_name;
        AppNode self;
        ObjectOutputStream out;
        ObjectInputStream in;
    
        AppNodeThread(Socket clientSocket, String channel_name, AppNode self) {
            this.clientSocket=clientSocket;
            this.channel_name=channel_name;
            this.self=self;
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public void run() {
            try {
                out.writeObject("initialize");
                out.flush();
                brokers = (ArrayList) in.readObject();
                Broker respBroker = hashTopic(channel_name);
                brokers.get(Integer.parseInt(respBroker.id)-1).channels.add(self);
                updateNodes();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Scanner userInput = new Scanner(System.in);
    
            try {   
                while(true) {       
                    System.out.println("\nWhat do you want to do?\n");
                    System.out.println("1. Subscribe to a channel\n");
                    System.out.println("2. Subscribe to a hashtag\n");
                    System.out.println("3. Upload Video\n");
                    System.out.println("4. Add or remove hashtag to/from a video\n");
                    System.out.println("5. Delete a video\n");
                    System.out.println("6. View Feed\n");
                    System.out.println("7. View hashtags subscribed");
                    
                    String action = userInput.nextLine();
                    if(action.equals ("1") || action.equals("2")){
                        ArrayList<String> availableChannels= new ArrayList<String>();
                        ArrayList<String> availableHashtags = new ArrayList<String>();
                        if (action.equals("1")){
                            
                            System.out.println("These are all the available channels:");
                            for (Broker broker: brokers){
                                for (AppNode channel : broker.channels) {
                                    if(channel.channel.channelName.equals(channel_name))
                                        continue;
                                    System.out.println(channel.channel.channelName);
                                    availableChannels.add(channel.channel.channelName);
                                }
                            }

                            if (availableChannels.isEmpty()){
                                System.out.println("No channels to subscribe to!");
                                continue;
                            }

                            System.out.println("\nPlease type the channel you want to subscribe to.");
                        }
                        else if(action.equals("2")){
 
                            for (Broker broker: brokers){
                                for (String hashtag : broker.hashtags) {
                                    System.out.println(hashtag);
                                    availableHashtags.add(hashtag);
                                }
                            }

                            if (availableHashtags.isEmpty()){
                                System.out.println("No hashtags to subscribe to!");
                                continue;
                            }
                            
                            

                            System.out.println("\nPlease type the hashtag you want to subscribe to.");
                        }

                        String input = userInput.nextLine();
                        Broker respBroker= hashTopic(input);

                        if(action.equals("1")){
                            if (!availableChannels.contains(input)){
                                System.out.println("The channel you selected doesn't exist in any broker!");
                                continue;
                            }
                            register(respBroker, input, "channel");
                        }
                        else if (action.equals("2")){
                            if (!availableHashtags.contains(input)){
                                System.out.println("The hashtag you selected doesn't exist in any broker!");
                                continue;
                            }
                            register(respBroker, input, "hashtag");
                        }

                       
                    }
                    else if (action.equals("3")){
                        out.writeObject(action);
                        out.flush();
                      
                        System.out.println("Name of the video you want to upload: ");
                        String input=userInput.nextLine();
                        VideoFile new_vid = upload(input); 
                        System.out.println("Enter the video's hashtags: ");
                        while (true) {
                            input=userInput.nextLine();
                            System.out.println(channel.videosPublished.get(channel.videosPublished.indexOf(new_vid)).getAssociatedHashtags());
                            if(channel.videosPublished.get(channel.videosPublished.indexOf(new_vid)).getAssociatedHashtags().contains(input)){
                                System.out.println("Hashtag already in video");
                                continue;
                            }
                            if (input.isEmpty())
                                break;
                            addHashTag(input, new_vid);
                        }
                        updateNodes();
                    }
                    else if (action.equals("4")){
                        if(channel.videosPublished.isEmpty()){
                            System.out.println("You have no videos!\n");
                            continue;
                        }

                        System.out.println("Your available videos are:");
                        int i = 0;
                        for(VideoFile video: channel.videosPublished){
                            System.out.println(i + ". " + video.getVideoName());
                            i++;
                        }
                        System.out.println("");
    
                        System.out.println("Select the index of the video you want to add or remove a hashtag");
                        int index = userInput.nextInt();
                        userInput.nextLine();
    
                        VideoFile video = channel.videosPublished.get(index);
    
                        System.out.println("These are the hashtags for your selected video: ");
                        
                        for(String hashtag: video.getAssociatedHashtags()){
                            System.out.println(hashtag);
                        }
                        System.out.println("Enter the name hashtag to add or remove (If it already exists, it will be removed): ");
                        String hashtag = userInput.nextLine();
                        
                        if(video.getAssociatedHashtags().contains(hashtag)){
                            removeHashTag(hashtag, video);
                            System.out.println("Successfully removed hashtag " + hashtag);
                        }
                        else {
                            addHashTag(hashtag, video);
                            System.out.println("Successfully added hashtag " + hashtag);
                        }
                        updateNodes();
                    }
                    else if (action.equals("5")) {
                        if(channel.videosPublished.isEmpty()){
                            System.out.println("You have no videos!\n");
                            continue;
                        }
                      
    
                        System.out.println("These are your videos:");
                        for(VideoFile video: channel.videosPublished){
                            System.out.println(channel.videosPublished.indexOf(video) + " " + video.getVideoName() );
                        }
    
                        System.out.println("\nPlease write the number of the video you want to delete: ");
                        int index = userInput.nextInt();
                        VideoFile videoToDelete = channel.videosPublished.get(index);
    
                        for (VideoFile video : channel.videosPublished) {
                            if (channel.videosPublished.get(index).getVideoName().equals(video.getVideoName())) {
                                for (String hashtag : video.getAssociatedHashtags())
                                    removeHashTag(hashtag, video);
                                break;
                            }
                        }
    
                        channel.videosPublished.remove(index);
                        channel.userVideoFilesMap.remove(videoToDelete.getVideoName());
                        updateNodes();
    
                    }
                    else if(action.equals("6")) {
                        String path;
                        for (Broker broker:registeredBrokers) {
                            try (Socket connection=new Socket("127.0.1.0", broker.port);
                                ObjectOutputStream temp_out = new ObjectOutputStream(connection.getOutputStream());
                                ObjectInputStream temp_in = new ObjectInputStream((connection.getInputStream()))) {
                                temp_out.writeObject(action);
                                temp_out.flush();
                                temp_out.writeObject(channel);
                                temp_out.flush();
                                path = (String) temp_in.readObject();
                                playData(path);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if(action.equals("7")){
                        System.out.println("You're subscribed to these hashtags:");
                        for (Broker broker: registeredBrokers){
                            for (String hashtag: broker.hashtags) {
                                System.out.print(hashtag + " ");
                            }
                        }
                        System.out.println();
                    }
                    else{
                        System.out.println("Please choose a valid option");
                    }    
                }
    
            } catch (UnknownHostException unknownHost) {
                System.err.println("You are trying to connect to an unknown host!");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        
    }	
}


