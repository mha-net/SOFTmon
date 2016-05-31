package de.tuberlin.cit.softmon.rest.floodlight;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import de.tuberlin.cit.softmon.rest.floodlight.model.stats.*;

import de.tuberlin.cit.softmon.rest.floodlight.model.topo.*;

public class FloodlightClient {
    // Default configuration for testing
	public static final String DEFAULT_HOSTNAME = "127.0.0.1";
	public static final String DEFAULT_PORT = "8080";
	public static final String DEFAULT_BASE_URL = "/wm";
	
	// Version information
	public static final String CLIENT_TYPE = "Floodlight";
	public static final String CLIENT_VERSION = "1.1";
	public static final String OF_VERSION = "OF_13";
	
	private WebResource m_resourceBase;

	public FloodlightClient() {
		m_resourceBase = Client.create().resource("http://" + DEFAULT_HOSTNAME + ":" + DEFAULT_PORT + DEFAULT_BASE_URL);
	}
	
	public FloodlightClient(String url) {
		m_resourceBase = Client.create().resource(url);
	}

	public FloodlightClient(String hostname, String port, String baseUrl) {
		m_resourceBase = Client.create().resource("http://" + hostname + ":" + port + baseUrl);
	}
	
	private String getJsonResource(String path) {
		Builder builder = m_resourceBase.path(path).accept(MediaType.APPLICATION_JSON); 
	    String json = builder.get(String.class);
		return json;
	}
		
	// full data binding
	public Switch[] getSwitches() throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/controller/switches/json");
		Switch[] objects = new ObjectMapper().readValue(json, Switch[].class);
		return objects;
	}
	
	// raw data binding (due to invalid attribute name containing "-") 
	@SuppressWarnings("unchecked")
	public Link[] getLinks() throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("topology/links/json");
		ArrayList<Object> objects = new ObjectMapper().readValue(json, ArrayList.class);
		Link[] links = new Link[objects.size()];
		for (int i = 0; i < objects.size(); i++) {
			Map<String, Object> linkMap = (Map<String, Object>) objects.get(i);
			links[i] = new Link((String) linkMap.get("src-switch"), (int) linkMap.get("src-port"), 
					(String) linkMap.get("dst-switch"), (int) linkMap.get("dst-port"), 
					(String) linkMap.get("type"), (String) linkMap.get("direction"));
		}
		return links;
	}
		
	// full data binding
	public Device[] getDevices() throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("device/");
		Device[] objects = new ObjectMapper().readValue(json, Device[].class);
		return objects;
	}
	
	// raw data binding (due to invalid attribute name "# <attribute>")
	@SuppressWarnings("unchecked")
	public Summary getSummary() throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/controller/summary/json");
		Map<String, Integer> objects = new ObjectMapper().readValue(json, Map.class);
		Summary summary = new Summary(objects.get("# Switches"), objects.get("# quarantine ports"), 
				objects.get("# inter-switch links"), objects.get("# hosts"));
		return summary;
	}

	// full data binding (with wrapper class)
	public Aggregate getSwitchAggregate(String dpid) throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/switch/" + dpid + "/aggregate/json");
		AggregateWrapper objects = new ObjectMapper().readValue(json, AggregateWrapper.class);
		Aggregate aggregate = objects.aggregate;
		return aggregate;
	}
	
	// full data binding (with wrapper class)
	public Table[] getSwitchTable(String dpid) throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/switch/" + dpid + "/table/json");
		TableWrapper objects = new ObjectMapper().readValue(json, TableWrapper.class);
		Table[] table = objects.table;
		return table;
	}

	// full data binding (with wrapper class)
	public Desc getSwitchDesc(String dpid) throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/switch/" + dpid + "/desc/json");
		DescWrapper objects = new ObjectMapper().readValue(json, DescWrapper.class);
		Desc desc = objects.desc;
		return desc;
	}
	
	// full data binding
	public Features getSwitchFeatures(String dpid) throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/switch/" + dpid + "/features/json");
		return new ObjectMapper().readValue(json, Features.class);
	}

	// full data binding
	public Queue getSwitchQueues(String dpid) throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/switch/" + dpid + "/queue/json");
		return new ObjectMapper().readValue(json, Queue.class);
	}

	// full data binding (with wrapper class)
	public PortReply getPortStatistics(String dpid) throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/switch/" + dpid + "/port/json");
		PortReplyWrapper objects = new ObjectMapper().readValue(json, PortReplyWrapper.class);
		return objects.port_reply[0];
	}
	
	// full data binding
	public PortDescReply getPortDesc(String dpid) throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/switch/" + dpid + "/port-desc/json");
		return new ObjectMapper().readValue(json, PortDescReply.class);		
	}
	
	// full data binding (with wrapper class)
	public Flow[] getFlows(String dpid) throws JsonParseException, JsonMappingException, IOException {
		String json = getJsonResource("core/switch/" + dpid + "/flow/json");
		FlowWrapper objects = new ObjectMapper().readValue(json, FlowWrapper.class); 
		return objects.flows;
	}
	
}
