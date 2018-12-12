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

class Tests {
	String password;
/*
    @Test
    public void testCreateSchdeule() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        CreateScheduleRequest ar = new CreateScheduleRequest("This Was made in a test", "2018-12-10", "2018-12-14", "01:00", "02:00", 30);
        String addRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(new PostRequest(addRequest));
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("add"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        AddResponse resp = new Gson().fromJson(post.body, AddResponse.class);
        Assert.assertEquals(25.94, resp.value, 0.00001);
    }
*/
}
