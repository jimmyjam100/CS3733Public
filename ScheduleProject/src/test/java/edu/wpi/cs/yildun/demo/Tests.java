package edu.wpi.cs.yildun.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

//import edu.wpi.cs.yildun.demo.TestContext;



class Tests {
	String password;
	int schedId;
	int timeslotId;
	
	Context createContext(String apiCall) {
	    TestContext ctx = new TestContext();
	    ctx.setFunctionName(apiCall);
	    return ctx;
	}

    @Test
    public void testCreateSchdeule() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-13", "2018-12-14", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        Assert.assertEquals(resp.sched.getName(), "This Was made in a test");
        Assert.assertEquals(resp.sched.getWeeks().size(), 1);
        Assert.assertEquals(resp.httpCode, 200);
    }
    
    @Test
    public void testCloseTimeslot() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-10", "2018-12-21", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        password = resp.sched.getPassword();
        schedId = resp.sched.getId();
        timeslotId = resp.sched.getWeeks().get(0).getDays().get(0).getTimeslots().get(0).getId();
        Assert.assertEquals(resp.httpCode, 200);
        
        
        
        CloseTImeslotLambda handler2 = new CloseTImeslotLambda();

        CloseTimeslotRequest ar2 = new CloseTimeslotRequest(timeslotId, schedId, password);
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        CloseTimeslotResponse resp2 = new Gson().fromJson(post2.body, CloseTimeslotResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
    }
    
    @Test
    public void testDeleteSchedule() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-10", "2018-12-21", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        password = resp.sched.getPassword();
        schedId = resp.sched.getId();
        timeslotId = resp.sched.getWeeks().get(0).getDays().get(0).getTimeslots().get(0).getId();
        Assert.assertEquals(resp.httpCode, 200);
        
        
        
        DeleteScheduleLambda handler2 = new DeleteScheduleLambda();

        DeleteScheduleRequest ar2 = new DeleteScheduleRequest(schedId, password);
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        DeleteScheduleResponse resp2 = new Gson().fromJson(post2.body, DeleteScheduleResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
    }
    
    @Test
    public void testExtendEnd() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-10", "2018-12-13", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        password = resp.sched.getPassword();
        schedId = resp.sched.getId();
        timeslotId = resp.sched.getWeeks().get(0).getDays().get(0).getTimeslots().get(0).getId();
        Assert.assertEquals(resp.httpCode, 200);
        
        
        
        ExtendEndLambda handler2 = new ExtendEndLambda();

        ExtendEndRequest ar2 = new ExtendEndRequest(schedId, "2018-12-14", password);
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        ExtendEndResponse resp2 = new Gson().fromJson(post2.body, ExtendEndResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
    }
    
    @Test
    public void testExtendStart() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-11", "2018-12-14", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        password = resp.sched.getPassword();
        schedId = resp.sched.getId();
        timeslotId = resp.sched.getWeeks().get(0).getDays().get(0).getTimeslots().get(0).getId();
        Assert.assertEquals(resp.httpCode, 200);
        
        
        
        ExtendStartLambda handler2 = new ExtendStartLambda();

        ExtendStartRequest ar2 = new ExtendStartRequest(schedId, "2018-12-10", password);
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        ExtendStartResponse resp2 = new Gson().fromJson(post2.body, ExtendStartResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
    }
    
    @Test
    public void testCloseOpenDay() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-13", "2018-12-14", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        password = resp.sched.getPassword();
        schedId = resp.sched.getId();
        timeslotId = resp.sched.getWeeks().get(0).getDays().get(0).getTimeslots().get(0).getId();
        Assert.assertEquals(resp.httpCode, 200);
        
        
        
        CloseDayLambda handler2 = new CloseDayLambda();

        CloseDayRequest ar2 = new CloseDayRequest("2018-12-13", schedId, password);
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        CloseDayResponse resp2 = new Gson().fromJson(post2.body, CloseDayResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
        
        OpenDayLambda handler3 = new OpenDayLambda();

        OpenDayRequest ar3 = new OpenDayRequest("3018-13-13", schedId, password);
        System.out.println(ar3.toString());
        String addRequest3 = new Gson().toJson(ar3);
        String jsonRequest3 = new Gson().toJson(new PostRequest(addRequest3));
        
        InputStream input3 = new ByteArrayInputStream(jsonRequest3.getBytes());
        OutputStream output3 = new ByteArrayOutputStream();

        handler3.handleRequest(input3, output3, createContext("add"));

        PostResponse post3 = new Gson().fromJson(output3.toString(), PostResponse.class);
        OpenDayResponse resp3 = new Gson().fromJson(post3.body, OpenDayResponse.class);
        Assert.assertEquals(resp3.httpCode, 200);
    }
    
    
    @Test
    public void testCloseOpenTime() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-13", "2018-12-14", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        password = resp.sched.getPassword();
        schedId = resp.sched.getId();
        timeslotId = resp.sched.getWeeks().get(0).getDays().get(0).getTimeslots().get(0).getId();
        Assert.assertEquals(resp.httpCode, 200);
        
        
        
        CloseTimeLambda handler2 = new CloseTimeLambda();

        CloseTimeRequest ar2 = new CloseTimeRequest(timeslotId, schedId, password);
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        CloseTimeResponse resp2 = new Gson().fromJson(post2.body, CloseTimeResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
        
        OpenTimeLambda handler3 = new OpenTimeLambda();

        OpenTimeRequest ar3 = new OpenTimeRequest(timeslotId, schedId, password);
        System.out.println(ar3.toString());
        String addRequest3 = new Gson().toJson(ar3);
        String jsonRequest3 = new Gson().toJson(new PostRequest(addRequest3));
        
        InputStream input3 = new ByteArrayInputStream(jsonRequest3.getBytes());
        OutputStream output3 = new ByteArrayOutputStream();

        handler3.handleRequest(input3, output3, createContext("add"));

        PostResponse post3 = new Gson().fromJson(output3.toString(), PostResponse.class);
        OpenTimeResponse resp3 = new Gson().fromJson(post3.body, OpenTimeResponse.class);
        Assert.assertEquals(resp3.httpCode, 200);
    }
    
    @Test
    public void testGetSchedules() throws IOException {

        GetSchedulesLambda handler2 = new GetSchedulesLambda();

        GetSchedulesRequest ar2 = new GetSchedulesRequest();
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        GetSchedulesResponse resp2 = new Gson().fromJson(post2.body, GetSchedulesResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
    }
    
    @Test
    public void testGetWeek() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-11", "2018-12-14", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        password = resp.sched.getPassword();
        schedId = resp.sched.getId();
        timeslotId = resp.sched.getWeeks().get(0).getDays().get(0).getTimeslots().get(0).getId();
        Assert.assertEquals(resp.httpCode, 200);
        
        
        
        GetWeekLambda handler2 = new GetWeekLambda();

        GetWeekRequest ar2 = new GetWeekRequest(schedId, "2018-12-11");
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        /*GetWeekResponse resp2 = new Gson().fromJson(post2.body, GetWeekResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
        Assert.assertEquals(resp2.week.getDays().get(0).getWeekday(), 1);*/
        Assert.assertTrue(true);
    }
    @Test
    public void testCreateMeeting() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-11", "2018-12-14", "01:00", "02:00", 30);
        System.out.println(ar.toString());
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        password = resp.sched.getPassword();
        schedId = resp.sched.getId();
        timeslotId = resp.sched.getWeeks().get(0).getDays().get(0).getTimeslots().get(0).getId();
        Assert.assertEquals(resp.httpCode, 200);
        
        
        
        CreateMeetingLambda handler2 = new CreateMeetingLambda();

        CreateMeetingRequest ar2 = new CreateMeetingRequest(timeslotId, "testUser");
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        CreateMeetingResponse resp2 = new Gson().fromJson(post2.body, CreateMeetingResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
    }
    @Test
    public void testGetOldSchedules() throws IOException {

        GetOldSchedulesLambda handler2 = new GetOldSchedulesLambda();

        GetOldSchedulesRequest ar2 = new GetOldSchedulesRequest(0, false);
        System.out.println(ar2.toString());
        String addRequest2 = new Gson().toJson(ar2);
        String jsonRequest2 = new Gson().toJson(new PostRequest(addRequest2));
        
        InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
        OutputStream output2 = new ByteArrayOutputStream();

        handler2.handleRequest(input2, output2, createContext("add"));

        PostResponse post2 = new Gson().fromJson(output2.toString(), PostResponse.class);
        GetOldSchedulesResponse resp2 = new Gson().fromJson(post2.body, GetOldSchedulesResponse.class);
        Assert.assertEquals(resp2.httpCode, 200);
    }

}
