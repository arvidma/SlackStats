package slackstats;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Settings {

    private static Settings instance;
    
    public final String token;
    public final String baseURL;
    public final List<String> slackChannels;
    public final HashMap<String, Boolean> channelTermSumDo;
    
    public final boolean usingFakeSlackData;
    public final boolean usingFakeMessageSink;
    public final boolean fakeMessageSinkIsConsole;
    public final String fakeSlackDataFile;
    public final String fakeMessageSink;

    
    private Settings() {
    	Properties prop = new Properties();
	
	try {
            ClassLoader classLoader = Main.class.getClassLoader();
            InputStream propsies = classLoader.getResourceAsStream("slackstats/slackstats.properties");
            prop.load(propsies);
        } catch (IOException ex) {
            System.out.println("Could not read slackstats.properties file.");
            System.exit(1);
        }

        token = prop.getProperty("token");
        baseURL = prop.getProperty("base_url");

        slackChannels = Arrays.<String>asList(prop.getProperty("channels").split(";"));
        
        channelTermSumDo = new HashMap<>();
        for (String channel : slackChannels) {
            channelTermSumDo.put(channel, Boolean.parseBoolean(prop.getProperty(channel)));
        }
        
        
        fakeMessageSink = prop.getProperty("fake_message_sink", "");
        if (fakeMessageSink.equals("")) {
            usingFakeMessageSink = false;
            fakeMessageSinkIsConsole = false;
        }
        else if (fakeMessageSink.equals("console")) {
            usingFakeMessageSink = true;
            fakeMessageSinkIsConsole = true;
        }
        else {
            usingFakeMessageSink = true;
            fakeMessageSinkIsConsole = false;
        }
        
        fakeSlackDataFile = prop.getProperty("fake_slack_data", "");
        if (fakeSlackDataFile.equals("")) {
            usingFakeSlackData = false;
        }
        else {
            usingFakeSlackData = true;
        }
        
      }
    
    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        
        return instance;
    }

    @Override
    public String toString() {
        /* Generated by netbeans. */
        return "Settings{" + "token=" + token + ", baseURL=" + baseURL + ", slackChannels=" + slackChannels + ", channelTermSumDo=" + channelTermSumDo + ", usingFakeSlackData=" + usingFakeSlackData + ", usingFakeMessageSink=" + usingFakeMessageSink + ", fakeMessageSinkIsConsole=" + fakeMessageSinkIsConsole + ", fakeSlackDataFile=" + fakeSlackDataFile + ", fakeMessageSink=" + fakeMessageSink + '}';
    }
    

}
